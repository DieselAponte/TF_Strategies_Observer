package model.strategy;

import model.model.Trabajo;

import java.util.List;

public class SueldoBajoStrategy implements SalarioStrategy {
    @Override
    public List<Trabajo> filter(List<Trabajo> data) {
        return data.stream()
                .filter(j -> j.getSueldo() <= 1140)
                .toList();
    }
}
