package model.strategy;

import model.model.Trabajo;

import java.util.List;


public class MudanzaStrategy implements CategoryStrategy {
    @Override
    public List<Trabajo> filter(List<Trabajo> data) {
        return data.stream()
                .filter(v -> "Mudanza".equalsIgnoreCase(v.getTipo()))
                .toList();
    }
}