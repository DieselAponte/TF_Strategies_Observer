package model.strategy;

import model.model.Trabajo;

import java.util.List;

public class MaxUnAnioTrabajoStrategy implements ExperienciaStrategy {
    @Override
    public List<Trabajo> filter(List<Trabajo> trabajos) {
        return trabajos.stream()

                .filter(t -> t.getExperienciaRequerida().equalsIgnoreCase("1 año") ||
                        t.getExperienciaRequerida().equalsIgnoreCase("Sin experiencia"))
                .toList();
    }
}
