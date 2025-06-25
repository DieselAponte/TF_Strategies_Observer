package model.factory;

import model.model.Trabajo;

public class TrabajoVentasFactory implements TrabajoFactory {

    @Override
    public Trabajo crearTrabajo(String titulo, String descripcion, String experiencia, double sueldo) {
        return new Trabajo(0, titulo, descripcion, "Venta", experiencia, sueldo);
    }
}
