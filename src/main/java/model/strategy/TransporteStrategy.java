package model.strategy;
import model.model.Trabajo;

import java.util.List;

public class TransporteStrategy implements CategoryStrategy {
    @Override
    public List<Trabajo> filter(List<Trabajo> data) {
        return data.stream()
                .filter(v -> "Transporte".equalsIgnoreCase(v.getTipo()))
                .toList();
    }
}