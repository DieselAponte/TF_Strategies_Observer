package model.factory;

import model.model.Trabajo;

public interface TrabajoFactory {
    Trabajo crearTrabajo(String titulo, String descripcion, String experiencia, double sueldo);
}
