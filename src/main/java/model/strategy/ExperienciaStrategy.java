package model.strategy;

import model.model.Trabajo;

import java.util.List;

public interface ExperienciaStrategy {
    List<Trabajo> filter(List<Trabajo> trabajos);
}

