package model.models;

import model.observer.postulation.PostulationEvent;
import model.observer.postulation.PostulationSubject;
import model.util.PostulationStatus;

import java.time.LocalDate;

public class Postulation extends PostulationSubject {
    private int idPostulation;
    private User user;
    private JobVacancy jobVacancy;
    private PostulationStatus status;
    private LocalDate postulationDate;

    public Postulation(int idPostulation, User user, JobVacancy jobVacancy,
                       PostulationStatus status, LocalDate postulationDate) {
        this.idPostulation = idPostulation;
        this.user = user;
        this.jobVacancy = jobVacancy;
        this.status = status;
        this.postulationDate = postulationDate;
    }

    public void isPending(){
        setStatus(PostulationStatus.POSTULADO);
    }

    public void isBeingProcessed(){
        status = PostulationStatus.EN_GESTION;
    }

    public void isApprove(){
        setStatus(PostulationStatus.ACEPTADO);
    }

    public void isRejected(){
        setStatus(PostulationStatus.RECHAZADO);
    }

    public void goInterview(){
        setStatus(PostulationStatus.ENTREVISTA);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public JobVacancy getJobVacancies() {
        return jobVacancy;
    }

    public void setJobVacancies(JobVacancy jobVacancy) {
        this.jobVacancy = jobVacancy;
    }

    public PostulationStatus getStatus() {
        return status;
    }

    public void setStatus(PostulationStatus newStatus) {
        PostulationStatus oldStatus = this.status;
        this.status = newStatus;
        notifyObservers(new PostulationEvent(this, oldStatus));
    }

    public LocalDate getPostulationDate() {
        return postulationDate;
    }

    public void setPostulationDate(LocalDate postulationDate) {
        this.postulationDate = postulationDate;
    }

    public int getId() {
        return idPostulation;
    }

    public void setId(int id) {
        this.idPostulation = id;
    }

    // Métodos para acceder a IDs anidados
    public int getUserId() {
        return user != null ? user.getId() : 0;
    }

    public int getJobVacancyId() {
        return jobVacancy != null ? jobVacancy.getId() : 0;
    }

    //Observer


    // Modificar todos los métodos de estado para usar setStatus()

}
