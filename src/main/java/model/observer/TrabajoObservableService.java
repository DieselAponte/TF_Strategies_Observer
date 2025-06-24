package model.observer;

// SUJETO OBSERVABLE
import model.model.Trabajo;

import java.util.ArrayList;
import java.util.List;

public class TrabajoObservableService {
    private final List<TrabajoObserver> observadores = new ArrayList<>();

    public void agregarObservador(TrabajoObserver observer) {
        observadores.add(observer);
    }

    public void eliminarObservador(TrabajoObserver observer) {
        observadores.remove(observer);
    }

    public void nuevoTrabajoCreado(Trabajo trabajo) {
        for (TrabajoObserver o : observadores) {
            o.onNuevoTrabajo(trabajo);
        }
    }
}
