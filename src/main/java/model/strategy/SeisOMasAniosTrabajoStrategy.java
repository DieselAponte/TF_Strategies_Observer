package model.strategy;

import model.model.Trabajo;

import java.util.List;

public class SeisOMasAniosTrabajoStrategy implements ExperienciaStrategy {
    @Override
    public List<Trabajo> filter(List<Trabajo> trabajos) {
        return trabajos.stream()
                .filter(t -> {
                    String exp = t.getExperienciaRequerida().toLowerCase();
                    return exp.contains("6") || exp.contains("más") || exp.contains("6+");
                }).toList();
    }
}
