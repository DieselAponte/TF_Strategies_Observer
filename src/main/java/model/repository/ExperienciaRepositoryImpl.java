package model.repository;

import model.model.Experiencia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExperienciaRepositoryImpl implements ExperienciaRepository {

    private final Connection connection;

    public ExperienciaRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void guardar(Experiencia experiencia) {
        String sql = "INSERT INTO experiencia (usuario_id, puesto, empresa, descripcion, fecha_inicio, fecha_fin) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, experiencia.getProfileId());
            stmt.setString(2, experiencia.getPuesto());
            stmt.setString(3, experiencia.getEmpresa());
            stmt.setString(4, experiencia.getDescripcion());
            stmt.setDate(5, Date.valueOf(experiencia.getFechaInicio()));
            stmt.setDate(6, Date.valueOf(experiencia.getFechaFin()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Experiencia> listarPorUsuario(int usuarioId) {
        List<Experiencia> lista = new ArrayList<>();
        String sql = "SELECT * FROM experiencia WHERE usuario_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void eliminar(int experienciaId) {
        String sql = "DELETE FROM experiencia WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, experienciaId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Experiencia mapRow(ResultSet rs) throws SQLException {
        return new Experiencia(
                rs.getInt("id"),
                rs.getInt("usuario_id"),
                rs.getString("puesto"),
                rs.getString("empresa"),
                rs.getString("descripcion"),
                rs.getDate("fecha_inicio").toLocalDate(),
                rs.getDate("fecha_fin").toLocalDate()
        );
    }
}
