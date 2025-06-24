package model.observer;

import model.model.Experiencia;
import model.model.Trabajo;
import model.observer.FiltroObserver;
import model.repository.ExperienciaRepository;
import model.repository.TrabajoRepository;
import model.strategy.CategoryStrategy;
import model.strategy.ExperienciaStrategy;
import model.strategy.SalarioStrategy;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Consumer;

public class TrabajoFiltradoService implements FiltroObserver {

    private final TrabajoRepository trabajoRepository;
    private final ExperienciaRepository experienciaRepository;
    private final int perfilId; // ID del perfil del usuario actual
    private final Consumer<List<Trabajo>> mostrarEnVista;

    public TrabajoFiltradoService(TrabajoRepository trabajoRepository,
                                  ExperienciaRepository experienciaRepository,
                                  int perfilId,
                                  Consumer<List<Trabajo>> mostrarEnVista) {
        this.trabajoRepository = trabajoRepository;
        this.experienciaRepository = experienciaRepository;
        this.perfilId = perfilId;
        this.mostrarEnVista = mostrarEnVista;
    }

    @Override
    public void update(List<CategoryStrategy> categorias,
                       List<SalarioStrategy> salarios,
                       List<ExperienciaStrategy> experiencias) {

        List<Trabajo> trabajos = trabajoRepository.listarTodos();
        List<Experiencia> experienciasUsuario = experienciaRepository.listarPorUsuario(perfilId);

        // Aplicar filtros de categoría
        for (CategoryStrategy cs : categorias)
            trabajos = cs.filter(trabajos);

        // Aplicar filtros de salario
        for (SalarioStrategy ss : salarios)
            trabajos = ss.filter(trabajos);

        // Aplicar filtros de experiencia basada en estrategias
        for (ExperienciaStrategy es : experiencias)
            trabajos = es.filter(trabajos);

        // Filtro adicional: validar si usuario cumple la experiencia mínima requerida por cada trabajo
        trabajos = trabajos.stream()
                .filter(t -> experienciaCumple(t, experienciasUsuario))
                .toList();

        mostrarEnVista.accept(trabajos);
    }

    /**
     * Valida si el usuario tiene la experiencia suficiente para postular al trabajo.
     */
    private boolean experienciaCumple(Trabajo trabajo, List<Experiencia> experienciasUsuario) {
        int totalAnios = experienciasUsuario.stream()
                .mapToInt(exp -> {
                    LocalDate inicio = exp.getFechaInicio();
                    LocalDate fin = exp.getFechaFin();
                    if (inicio != null && fin != null && !fin.isBefore(inicio)) {
                        return (int) ChronoUnit.YEARS.between(inicio, fin);
                    }
                    return 0;
                })
                .sum();

        int aniosRequeridos = extraerAniosRequeridos(trabajo.getExperienciaRequerida());
        return totalAnios >= aniosRequeridos;
    }

    /**
     * Convierte el texto de experiencia requerida (ej. "2 años", "6+ años") a un número de años.
     */
    private int extraerAniosRequeridos(String texto) {
        if (texto == null) return 0;
        String lower = texto.toLowerCase();

        if (lower.contains("sin")) return 0;
        if (lower.contains("6") || lower.contains("más") || lower.contains("mas") || lower.contains("6+")) return 6;

        for (String palabra : lower.split("\\s+")) {
            if (palabra.matches("\\d+")) {
                return Integer.parseInt(palabra);
            }
        }

        return 0;
    }
}
