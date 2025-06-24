package model.strategy;

import model.model.Trabajo;

import java.util.List;

public class SueldoMedioStrategy implements SalarioStrategy {
    @Override
    public List<Trabajo> filter(List<Trabajo> data) {
        return data.stream()
                .filter(j -> j.getSueldo() >= 1200 && j.getSueldo() <= 2000)
                .toList();
    }
}