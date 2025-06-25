package model.repository;

import model.model.Trabajo;

import java.util.List;
import java.util.Optional;

public interface TrabajoRepository {
    void guardar(Trabajo trabajo);
    Optional<Trabajo> buscarPorId(int id);
    List<Trabajo> listarTodos();
}
