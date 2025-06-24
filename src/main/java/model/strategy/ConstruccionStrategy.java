package model.strategy;
import model.model.Trabajo;

import java.util.List;

public class ConstruccionStrategy implements CategoryStrategy {
    @Override
    public List<Trabajo> filter(List<Trabajo> data) {
        return data.stream()
                .filter(v -> "Construccion".equalsIgnoreCase(v.getTipo()))
                .toList();
    }
}