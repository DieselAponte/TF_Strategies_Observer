package org.example.bolsalaboralapp;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.builder.ConcreteExperienciaBuilder;
import model.repository.ExperienciaRepository;

public class AñadirExperienciaController {

    /* ---------- FXML ---------- */
    @FXML private TextField  campoPuesto;
    @FXML private TextField  campoEmpresa;
    @FXML private TextArea   campoDescripcion;
    @FXML private DatePicker campoFechaInicio;
    @FXML private DatePicker campoFechaFin;

    /* ---------- Inyectados desde PerfilController ---------- */
    private int usuarioId;
    private ExperienciaRepository repo;
    private PerfilController perfilController;

    /** Llamado por PerfilController inmediatamente después de cargar el FXMLLoader */
    public void init(int usuarioId, ExperienciaRepository repo, PerfilController perfilController) {
        this.usuarioId        = usuarioId;
        this.repo             = repo;
        this.perfilController = perfilController;
    }

    /* ---------- Acciones ---------- */
    @FXML
    private void guardarExperiencia() {
        if (!validar()) return;

        repo.guardar(new ConcreteExperienciaBuilder()
                .setProfileId(usuarioId)                       // nombre correcto
                .setPosition(campoPuesto.getText())
                .setCompany(campoEmpresa.getText())
                .setDescription(campoDescripcion.getText())
                .setFechaInicio(campoFechaInicio.getValue())
                .setFechaFin(campoFechaFin.getValue())
                .build());

        perfilController.actualizarListaExperiencias();        // refresca vista principal
        cerrarVentana();
    }

    @FXML private void cancelar() { cerrarVentana(); }

    /* ---------- Validación simple ---------- */
    private boolean validar() {
        if (campoPuesto.getText().isBlank() || campoEmpresa.getText().isBlank()) {
            new Alert(Alert.AlertType.WARNING, "Puesto y Empresa son obligatorios").showAndWait();
            return false;
        }
        if (campoFechaInicio.getValue() == null || campoFechaFin.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "Selecciona fechas válidas").showAndWait();
            return false;
        }
        if (campoFechaFin.getValue().isBefore(campoFechaInicio.getValue())) {
            new Alert(Alert.AlertType.WARNING, "La fecha fin no puede ser anterior a la de inicio").showAndWait();
            return false;
        }
        return true;
    }

    private void cerrarVentana() {
        ((Stage) campoPuesto.getScene().getWindow()).close();
    }
}
