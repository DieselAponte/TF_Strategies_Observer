package model.strategy;
import model.model.Trabajo;

import java.util.List;

public class VentasStrategy implements CategoryStrategy {
    @Override
    public List<Trabajo> filter(List<Trabajo> data) {
        return data.stream()
                .filter(v -> "Venta".equalsIgnoreCase(v.getTipo()))
                .toList();
    }
}

