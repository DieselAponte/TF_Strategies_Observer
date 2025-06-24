package model.builder;

import model.model.Notificacion;

import java.time.LocalDate;

public class PostulacionNotificacionBuilder implements NotificacionBuilder {

    private int userId;
    private String mensaje;
    private LocalDate fecha;
    private boolean leido;

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public void setLeido(boolean leido) {
        this.leido = leido;
    }

    @Override
    public Notificacion build() {
        return new Notificacion(0, userId, mensaje, fecha, leido);
    }
}
