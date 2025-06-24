package model.builder;

import model.model.Notificacion;

import java.time.LocalDate;

public interface NotificacionBuilder {
    void setUserId(int userId);
    void setMensaje(String mensaje);
    void setFecha(LocalDate fecha);
    void setLeido(boolean leido);
    Notificacion build();
}
