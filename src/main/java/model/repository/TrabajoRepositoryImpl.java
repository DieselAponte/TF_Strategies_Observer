package model.repository;

import model.model.Trabajo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TrabajoRepositoryImpl implements TrabajoRepository {
    private final Connection connection;

    public TrabajoRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void guardar(Trabajo trabajo) {
        String sql = "INSERT INTO trabajo (titulo, descripcion, categoria, experiencia_requerida, sueldo) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, trabajo.getTitulo());
            stmt.setString(2, trabajo.getDescripcion());
            stmt.setString(3, trabajo.getTipo()); // categoria
            stmt.setString(4, trabajo.getExperienciaRequerida());
            stmt.setDouble(5, trabajo.getSueldo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Trabajo> buscarPorId(int id) {
        String sql = "SELECT * FROM trabajo WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Trabajo> listarTodos() {
        List<Trabajo> trabajos = new ArrayList<>();
        String sql = "SELECT * FROM trabajo";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                trabajos.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trabajos;
    }

    private Trabajo mapRow(ResultSet rs) throws SQLException {
        return new Trabajo(
                rs.getInt("id"),
                rs.getString("titulo"),
                rs.getString("descripcion"),
                rs.getString("categoria"), // tipo
                rs.getString("experiencia_requerida"),
                rs.getDouble("sueldo")
        );
    }
}
