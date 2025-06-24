package model.model;

import java.time.LocalDate;

public class Notificacion {
    private int id;
    private String mensaje;
    private LocalDate fecha;
    private boolean leido;

    public Notificacion(int id, String mensaje, LocalDate fecha, boolean leido) {
        this.id = id;
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.leido = leido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isLeido() {
        return leido;
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
    }
}
