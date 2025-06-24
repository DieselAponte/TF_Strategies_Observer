package model.observer;

import model.model.Trabajo;

public interface TrabajoObserver {
    void onNuevoTrabajo(Trabajo nuevoTrabajo);
}
