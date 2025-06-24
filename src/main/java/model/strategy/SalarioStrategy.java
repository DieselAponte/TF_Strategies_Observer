package model.strategy;

import model.model.Trabajo;

import java.util.List;

public interface SalarioStrategy {
    List<Trabajo> filter(List<Trabajo> jobVacancies);
}
