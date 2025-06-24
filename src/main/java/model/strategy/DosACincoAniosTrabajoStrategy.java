package model.strategy;

import model.model.Trabajo;

import java.util.List;

public class DosACincoAniosTrabajoStrategy implements ExperienciaStrategy {
    @Override
    public List<Trabajo> filter(List<Trabajo> trabajos) {
        return trabajos.stream()
                .filter(t -> t.getExperienciaRequerida().equalsIgnoreCase("2 años") ||
                        t.getExperienciaRequerida().equalsIgnoreCase("3 años") ||
                        t.getExperienciaRequerida().equalsIgnoreCase("4 años") ||
                        t.getExperienciaRequerida().equalsIgnoreCase("5 años"))
                .toList();
    }
}

