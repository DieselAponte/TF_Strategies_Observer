package org.example.bolsalaboralapp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.repository.UsuarioRepository;
import model.repository.UsuarioRepositoryImpl;
import model.repository.DataBaseConecction;

import java.sql.Connection;

public class LoginController {

    /* ---------- FXML ---------- */
    @FXML private TextField      campoUsuario;
    @FXML private PasswordField  campoContrasena;

    /* ---------- Repositorio de usuarios ---------- */
    private final UsuarioRepository usuarioRepo;

    public LoginController() {
        Connection conn = DataBaseConecction.getInstance().getConnection();
        this.usuarioRepo = new UsuarioRepositoryImpl(conn);
    }

    /* ------------------------------------------------------------------
       MÉTODOS VINCULADOS DESDE login-view.fxml
       ------------------------------------------------------------------ */

    /** Botón “Ingresar” → validar y pasar al menú principal */
    @FXML
    private void mostrarMainMenu() {
        String user = campoUsuario.getText();
        String pwd  = campoContrasena.getText();

        if (usuarioRepo.verificarCredenciales(user, pwd)) {
            // ⇒ Navegar a la pantalla principal
            SceneManager.cambiarVista(
                    "/org/example/bolsalaboralapp/main-view.fxml",         // Ruta constante
                    "Bolsa Laboral – Inicio");
        } else {
            new Alert(Alert.AlertType.ERROR,
                    "Usuario o contraseña incorrectos").showAndWait();
        }
    }

    /** Botón “Crear cuenta” → cambiar a pantalla de registro */
    @FXML
    private void mostrarCreateAccount() {
        SceneManager.cambiarVista(
                "/org/example/bolsalaboralapp/createAccount-view.fxml",        // Ruta a createAccount-view.fxml
                "Crear nueva cuenta");
    }
}