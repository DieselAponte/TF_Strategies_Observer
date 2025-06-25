package model.repository;

import model.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {
    boolean guardar(Usuario usuario);
    Optional<Usuario> buscarPorId(int id);
    Optional<Usuario> buscarPorNombreUsuario(String username);
    List<Usuario> listarTodos();
    boolean verificarCredenciales(String username, String password);
}
