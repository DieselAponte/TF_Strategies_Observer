package model.observer;

import model.strategy.CategoryStrategy;
import model.strategy.ExperienciaStrategy;
import model.strategy.SalarioStrategy;

import java.util.List;

public interface FiltroObserver {
    void update(List<CategoryStrategy> categorias,
                List<SalarioStrategy> salarios,
                List<ExperienciaStrategy> experiencias);
}

