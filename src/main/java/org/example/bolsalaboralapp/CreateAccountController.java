package org.example.bolsalaboralapp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.model.Usuario;
import model.repository.DataBaseConecction;
import model.repository.UsuarioRepository;
import model.repository.UsuarioRepositoryImpl;


import java.sql.Connection;

public class CreateAccountController {

    @FXML private TextField     txtNuevoUsuario;
    @FXML private TextField     txtCorreo;
    @FXML private PasswordField txtNuevaContraseña;
    @FXML private PasswordField txtConfirmarContraseña;

    private final UsuarioRepository usuarioRepo;

    public CreateAccountController() {
        Connection conn = DataBaseConecction.getInstance().getConnection();
        this.usuarioRepo = new UsuarioRepositoryImpl(conn);
    }

    @FXML
    private void registrarse() {

        /* --- validaciones mínimas --- */
        if (txtNuevoUsuario.getText().isBlank() ||
                txtCorreo.getText().isBlank()       ||
                txtNuevaContraseña.getText().isBlank() ||
                txtConfirmarContraseña.getText().isBlank()) {

            mostrarError("Completa todos los campos obligatorios.");
            return;
        }

        if (!txtNuevaContraseña.getText().equals(txtConfirmarContraseña.getText())) {
            mostrarError("Las contraseñas no coinciden.");
            return;
        }

        /* --- crear y guardar usuario --- */
        Usuario nuevo = new Usuario(
                0,
                txtNuevoUsuario.getText(),
                txtCorreo.getText(),
                txtNuevaContraseña.getText()      // solo los 4 campos que tiene tu POJO
        );

        usuarioRepo.guardar(nuevo);

        new Alert(Alert.AlertType.INFORMATION,
                "Cuenta creada correctamente").showAndWait();

        SceneManager.cambiarVista(
                "/org/example/bolsalaboralapp/login-view.fxml",
                "Iniciar sesión");
    }

    @FXML
    private void volverALogin() {
        SceneManager.cambiarVista(
                "/org/example/bolsalaboralapp/login-view.fxml",
                "Iniciar sesión");
    }

    private void mostrarError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).showAndWait();
    }
}