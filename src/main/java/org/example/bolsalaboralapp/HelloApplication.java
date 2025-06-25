package org.example.bolsalaboralapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.repository.*;
import model.service.StartupService;
import org.example.bolsalaboralapp.MainController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

import static javafx.application.Application.launch;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Crear conexión y repositorio
        Connection connection = DataBaseConecction.getInstance().getConnection();
        TrabajoRepository trabajoRepo = new TrabajoRepositoryImpl(connection);
        ExperienciaRepository experienciaRepo = new ExperienciaRepositoryImpl(connection);

        // ⚠️ Sembrar datos iniciales
        StartupService startup = new StartupService(trabajoRepo);
        startup.iniciarAplicacion();

        // Obtener controlador e inyectar dependencias
        MainController controller = fxmlLoader.getController();
        controller.setRepositorios(trabajoRepo, experienciaRepo, 1); // ID de perfil hardcodeado por ahora

        stage.setTitle("Bolsa Laboral – Inicio");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
