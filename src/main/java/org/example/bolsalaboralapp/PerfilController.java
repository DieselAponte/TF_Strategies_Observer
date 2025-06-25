package org.example.bolsalaboralapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.model.Experiencia;
import model.model.Usuario;
import model.repository.ExperienciaRepository;
import model.repository.ExperienciaRepositoryImpl;
import model.repository.DataBaseConecction;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class PerfilController {

    /* ---------- FXML ---------- */
    @FXML private ImageView profileImage;
    @FXML private Label lblNombreUsuario, lblDistritoUsuario, lblCorreo, lblTelefono;
    @FXML private VBox contenedorExperiencias;

    /* ---------- Repositorio ---------- */
    private final ExperienciaRepository experienciaRepo;

    /* ---------- Usuario actual ---------- */
    private Usuario usuarioActual;

    public PerfilController() {
        Connection conn = DataBaseConecction.getInstance().getConnection();
        this.experienciaRepo = new ExperienciaRepositoryImpl(conn);
    }

    /* ---------- Inicialización ---------- */
    @FXML
    private void initialize() {
        usuarioActual = obtenerUsuarioDesdeSesion();      // reemplaza por tu sesión real
        cargarDatosPerfil();
        actualizarListaExperiencias();
    }

    /* =======================================================================
       MÉTODOS VINCULADOS EN EL FXML (barra superior + botones laterales)
       ======================================================================= */

    @FXML private void mostrarMainMenu() {

    }

    @FXML private void mostrarNotificaciones() {
        // TODO: cargar vista de notificaciones
        System.out.println("Abrir notificaciones");
    }

    @FXML private void mostrarPostulaciones() {
        // TODO: cargar vista de postulaciones
        System.out.println("Abrir postulaciones");
    }

    @FXML private void mostrarVentanaAdjuntarArchivo() {
        try{
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/org/example/bolsalaboralapp/adjuntar--view.fxml"));
            Parent root =loader.load();

            AñadirArchivoController popup = loader.getController();
            popup.initialize();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /* ---------- Popup “+ Añadir experiencia” ---------- */
    @FXML
    private void mostrarVentanaExperiencia() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/org/example/bolsalaboralapp/añadir-experiencia-view.fxml"));
            Parent root = loader.load();

            AñadirExperienciaController popup = loader.getController();
            popup.init(usuarioActual.getId(), experienciaRepo, this);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Nueva experiencia");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* =======================================================================
       REFRESCAR EXPERIENCIAS
       ======================================================================= */
    public void actualizarListaExperiencias() {
        contenedorExperiencias.getChildren().clear();
        List<Experiencia> lista = experienciaRepo.listarPorUsuario(usuarioActual.getId());
        for (Experiencia exp : lista) {
            contenedorExperiencias.getChildren().add(crearTarjetaExperiencia(exp));
        }
    }

    private HBox crearTarjetaExperiencia(Experiencia exp) {
        HBox tarjeta = new HBox(10);
        tarjeta.getStyleClass().add("experience-box");

        Label lblPuesto  = new Label(exp.getPuesto() + " - " + exp.getEmpresa());
        lblPuesto.getStyleClass().add("job-title");

        Label lblPeriodo = new Label(exp.getFechaInicio() + " a " + exp.getFechaFin());
        lblPeriodo.getStyleClass().add("job-period");

        Button btnEliminar = new Button();
        btnEliminar.getStyleClass().add("delete-icon-button");
        btnEliminar.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("/imagenes/trash-icon.png"))));
        btnEliminar.setOnAction(e -> {
            experienciaRepo.eliminar(exp.getId());
            actualizarListaExperiencias();
        });

        tarjeta.getChildren().addAll(lblPuesto, lblPeriodo, btnEliminar);
        return tarjeta;
    }

    /* =======================================================================
       DATOS DE PERFIL
       ======================================================================= */
    private void cargarDatosPerfil() {
        lblNombreUsuario.setText(usuarioActual.getNombreCompleto());
        lblCorreo.setText(usuarioActual.getCorreo());
    }

    private Usuario obtenerUsuarioDesdeSesion() {
        // Mock temporal
        return new Usuario(
                1,
                "Frank Figueroa Rodriguez",
                "frankfrodriguez12@gmail.com",
                "+51-950626070"
        );
    }
}
