package model.observer;

import model.strategy.CategoryStrategy;
import model.strategy.ExperienciaStrategy;
import model.strategy.SalarioStrategy;

import java.util.ArrayList;
import java.util.List;

public class FiltroObservableService {

    private final List<FiltroObserver> observers = new ArrayList<>();

    private final List<CategoryStrategy> categoriaFilters = new ArrayList<>();
    private final List<SalarioStrategy> salarioFilters = new ArrayList<>();
    private final List<ExperienciaStrategy> experienciaFilters = new ArrayList<>();

    // Observadores
    public void agregarObserver(FiltroObserver o) {
        observers.add(o);
    }

    public void eliminarObserver(FiltroObserver o) {
        observers.remove(o);
    }

    // Filtros de categoría
    public void agregarFiltroCategoria(CategoryStrategy filtro) {
        if (!categoriaFilters.contains(filtro)) {
            categoriaFilters.add(filtro);
//            notificar();
        }
    }

    public void eliminarFiltroCategoria(CategoryStrategy filtro) {
        categoriaFilters.remove(filtro);
        notificar();
    }

    // Filtros de salario
    public void agregarFiltroSalario(SalarioStrategy filtro) {
        if (!salarioFilters.contains(filtro)) {
            salarioFilters.add(filtro);
            notificar();
        }
    }

    public void eliminarFiltroSalario(SalarioStrategy filtro) {
        salarioFilters.remove(filtro);
        notificar();
    }

    // Filtros de experiencia
    public void agregarFiltroExperiencia(ExperienciaStrategy filtro) {
        if (!experienciaFilters.contains(filtro)) {
            experienciaFilters.add(filtro);
            notificar();
        }
    }

    public void eliminarFiltroExperiencia(ExperienciaStrategy filtro) {
        experienciaFilters.remove(filtro);
        notificar();
    }

    // Limpia todos los filtros activos
    public void limpiarFiltros() {
        categoriaFilters.clear();
        salarioFilters.clear();
        experienciaFilters.clear();
        notificar();
    }

    public void aplicarFiltros(List<CategoryStrategy> cat, List<SalarioStrategy> sal, List<ExperienciaStrategy> exp) {
        this.categoriaFilters.clear();
        this.categoriaFilters.addAll(cat);

        this.salarioFilters.clear();
        this.salarioFilters.addAll(sal);

        this.experienciaFilters.clear();
        this.experienciaFilters.addAll(exp);

        notificar();
    }

    // Notifica a todos los observadores del cambio de estado
    public void notificar() {
        for (FiltroObserver o : observers) {
            o.update(categoriaFilters, salarioFilters, experienciaFilters);
        }
    }
}
