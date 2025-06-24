package model.strategy;

import model.model.Trabajo;

import java.util.List;

public interface CategoryStrategy {
    List<Trabajo> filter(List<Trabajo> jobVacancy);
}
