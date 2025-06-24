package org.example.bolsalaboralapp;

import model.builder.PostulacionNotificacionBuilder;
import model.builder.NotificacionBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.model.Notificacion;
import model.model.Trabajo;
import model.repository.NotificacionRepository;

import java.time.LocalDate;
import java.util.List;

public class NotificacionesController {

    @FXML
    private ListView<String> listaNotificaciones;

    private NotificacionRepository notificacionRepository;
    private int userId;

    public void setDatos(NotificacionRepository repo, int userId) {
        this.notificacionRepository = repo;
        this.userId = userId;
        cargarNotificaciones();
    }

    private void cargarNotificaciones() {
        listaNotificaciones.getItems().clear();
        List<Notificacion> notifs = notificacionRepository.listarPorUsuario(userId);
        for (Notificacion n : notifs) {
            String texto = String.format("[%s] %s", n.getFecha(), n.getMensaje());
            listaNotificaciones.getItems().add(texto);
        }
    }

    public void crearNotificacionCambioEstado(String tituloTrabajo, String nuevoEstado) {
        NotificacionBuilder builder = new PostulacionNotificacionBuilder();
        builder.setUserId(userId);
        builder.setFecha(LocalDate.now());
        builder.setLeido(false);
        builder.setMensaje("Tu postulación a \"" + tituloTrabajo + "\" cambió de estado a: " + nuevoEstado);

        Notificacion noti = builder.build();
        notificacionRepository.guardar(noti);
        cargarNotificaciones();
    }

    @FXML
    private void mostrarMainMenu() {
        SceneManager.cambiarVista("main-view.fxml", "Menú Principal");
    }

    @FXML
    private void mostrarPerfil() {
        SceneManager.cambiarVista("perfil-view.fxml", "Perfil de Usuario");
    }

    @FXML
    private void mostrarNotificaciones() {
        SceneManager.cambiarVista("notificaciones-view.fxml", "Notificaciones");
    }

    @FXML
    private void mostrarPostulaciones() {
        SceneManager.cambiarVista("postulaciones-view.fxml", "Mis Postulaciones");
    }
}
