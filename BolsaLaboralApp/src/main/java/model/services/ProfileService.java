package model.services;
import model.builders.*;
import model.models.*;
import model.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfileService {
    private final ProfileRepository profileRepo;
    private final UserRepository userRepo;
    private final SkillRepository skillRepo;
    private final ExperienciaRepository experienciaRepo; // Añadido
    private final UserProfileDirector director;

    public ProfileService(ProfileRepository profileRepo, UserRepository userRepo, SkillRepository skillRepo, ExperienciaRepository experienciaRepo, UserProfileDirector director) {
        this.profileRepo = profileRepo;
        this.userRepo = userRepo;
        this.skillRepo = skillRepo;
        this.experienciaRepo = experienciaRepo;
        this.director = director;
    }


    /**
     * Crea un nuevo usuario + perfil “tech” validado por el Builder/Director.
     * @param id             ID del usuario
     * @param username       Nombre de usuario
     * @param email          Email
     * @param rawPassword    Contraseña
     * @param initialSkills  Lista de skills iniciales
     * @return Usuario ya persistido con su perfil
     */
    public User createTechCandidate(int id,
                                    String username,
                                    String email,
                                    String rawPassword,
                                    List<String> initialSkills) {
        // Opción: podrías validar aquí que el ID o el email no estén ya en uso...
        ProfileBuilder builder = new ConcreteProfileBuilder();
        // 1) Montar User+Profile
        User candidate = director.createTechCandidate(
                builder,
                id,
                username,
                email,
                initialSkills
        );
        // 2) Asignar contraseña
        builder.withUserPassword(rawPassword);

        // 3) Persistir User (y obtenemos el ID real si fuera autogenerado)
        User savedUser = userRepo.saveUser(candidate);

        // 4) Persistir Profile básico (sin skills aún)
        Profile toSave = savedUser.getProfile();
        toSave.setUserId(savedUser.getId());
        Profile savedProfile = profileRepo.saveProfile(toSave);

        // 5) Asignar skills en la tabla intermedia
        assignSkillsToProfile(savedProfile.getId(), initialSkills);

        // 6) Devolver el usuario con perfil completo
        savedUser.setProfile(getProfileById(savedProfile.getId()).orElse(savedProfile));
        return savedUser;
    }

    private void assignSkillsToProfile(int profileId, List<String> skills) {
        skills.forEach(skillName -> {
            int skillId = skillRepo.getOrCreateSkill(skillName);
            skillRepo.assignSkillToProfile(profileId, skillId);
        });
    }

    /**
     * Reconstruye y valida un Profile existente antes de actualizar.
     */
    public Profile updateProfile(Profile rawProfile,
                                 String newDescription,
                                 List<String> newSkills) {
        // 1) Asegurarnos de que existe
        Profile existing = profileRepo.findByProfileId(rawProfile.getId())
                .orElseThrow(() -> new IllegalArgumentException("Perfil no existe"));

        // 2) Usar el Builder en modo “solo Profile”
        ProfileBuilder builder = new ConcreteProfileBuilder();
        Profile rebuilt = builder.reset()
                .withProfession(rawProfile.getProfession())
                .withDescription(newDescription)
                .withSkills(newSkills)
                .buildProfileOnly();

        // 3) Persistir cambios básicos
        Profile updated = profileRepo.updateProfile(rebuilt);

        // 4) Reasignar skills (eliminar + volver a asignar)
        skillRepo.deleteSkillsForProfile(updated.getId());
        assignSkillsToProfile(updated.getId(), newSkills);

        // 5) Cargar skills y devolver
        return getProfileById(updated.getId()).orElse(updated);
    }

    /* ---------------------- Métodos de consulta “puros” ---------------------- */

    public Optional<Profile> getProfileById(int id) {
        Optional<Profile> opt = profileRepo.findByProfileId(id);
        opt.ifPresent(p -> p.setJobSkills(skillRepo.findSkillsByProfile(id)));
        return opt;
    }

    public Optional<Profile> getProfileByUser(int userId) {
        Optional<Profile> opt = profileRepo.findByUserId(userId);
        opt.ifPresent(p -> p.setJobSkills(skillRepo.findSkillsByProfile(p.getId())));
        return opt;
    }

    public List<Profile> getAllProfiles() {
        List<Profile> list = profileRepo.findAllProfiles();
        list.forEach(p -> p.setJobSkills(skillRepo.findSkillsByProfile(p.getId())));
        return list;
    }

    public List<Profile> searchProfilesByProfession(String profession) {
        List<Profile> list = profileRepo.findProfileByProfession(profession);
        list.forEach(p -> p.setJobSkills(skillRepo.findSkillsByProfile(p.getId())));
        return list;
    }

    public List<Profile> searchProfilesBySkills(List<String> skills) {
        // 1) Obtener IDs de skills
        List<Integer> skillIds = skills.stream()
                .map(skillRepo::getSkillIdByName)
                .flatMap(Optional::stream)
                .toList();

        if (skillIds.isEmpty()) return List.of();

        // 2) Buscar perfiles que tienen todas esas skills
        List<Integer> profileIds = skillRepo.findProfileIdsWithAllSkills(skillIds);

        // 3) Cargar cada Profile completo
        return profileIds.stream()
                .map(this::getProfileById)
                .flatMap(Optional::stream)
                .toList();
    }

    public void deleteProfile(int id) {
        skillRepo.deleteSkillsForProfile(id);
        profileRepo.deleteProfiles(id);
    }

    public SkillRepository getSkillRepository() {
        return skillRepo;
    }

    public void agregarExperiencia(int profileId, ExperienciaLaboral experiencia) {
        experienciaRepo.guardarExperiencia(experiencia, profileId);

        // Actualizar el perfil en memoria si está cargado
        getProfileById(profileId).ifPresent(profile -> {
            profile.addExperiencia(experiencia);
        });
    }

    public List<ExperienciaLaboral> obtenerExperiencias(int profileId) {
        // Validación adicional
        if (experienciaRepo == null) {
            throw new IllegalStateException("ExperienciaRepository no ha sido inicializado");
        }

        List<ExperienciaLaboral> experiencias = experienciaRepo.obtenerExperienciasPorPerfil(profileId);

        // Actualización más segura del perfil
        Optional<Profile> perfilOpt = getProfileById(profileId);
        perfilOpt.ifPresent(profile -> {
            if (profile.getExperiencias() == null) {
                profile.setExperiencias(new ArrayList<>());
            }
            profile.getExperiencias().clear();
            profile.getExperiencias().addAll(experiencias);
        });

        return experiencias;
    }
}
