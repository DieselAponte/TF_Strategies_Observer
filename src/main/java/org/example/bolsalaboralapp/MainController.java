package org.example.bolsalaboralapp;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.model.Trabajo;
import model.observer.FiltroObservableService;
import model.repository.ExperienciaRepository;
import model.repository.TrabajoRepository;
import model.strategy.*;
import model.observer.TrabajoFiltradoService;

import java.net.URL;
import java.util.*;

public class MainController {

    // Filtros
    @FXML private CheckBox cbConstruccion, cbVentas, cbTransporte, cbModernas;
    @FXML private CheckBox cbUnAnio, cbDosCinco, cbSeisOMas;
    @FXML private CheckBox cbSueldo950, cbSueldo1025, cbSueldo1200;
    @FXML private Button btnActualizarBusqueda;

    // Panel trabajos y detalle
    @FXML private ListView<Trabajo> jobsListView;
    @FXML private Label lblTitulo, lblEmpresa, lblUbicacion;
    @FXML private TextArea txtDescripcion;
    @FXML private VBox jobInfoBox;

    // Servicios
    private FiltroObservableService filtroService;
    private TrabajoFiltradoService trabajoFiltradoService;
    private TrabajoRepository trabajoRepository;
    private ExperienciaRepository experienciaRepository;
    private int perfilId;

    public void setRepositorios(TrabajoRepository trabajoRepo,
                                ExperienciaRepository experienciaRepo,
                                int perfilIdUsuario) {
        this.trabajoRepository = trabajoRepo;
        this.experienciaRepository = experienciaRepo;
        this.perfilId = perfilIdUsuario;

        filtroService = new FiltroObservableService();

        trabajoFiltradoService = new TrabajoFiltradoService(
                trabajoRepository,
                experienciaRepository,
                perfilId,
                this::actualizarVistaConTrabajos
        );

        filtroService.agregarObserver(trabajoFiltradoService);

//        configurarFiltros();
        configurarListView();
        cargarTodosLosTrabajos(); // Carga inicial al abrir la vista
    }

//    private void configurarFiltros() {
//        cbConstruccion.setOnAction(e -> toggleCategoria(cbConstruccion, new ConstruccionStrategy()));
//        cbVentas.setOnAction(e -> toggleCategoria(cbVentas, new VentasStrategy()));
//        cbTransporte.setOnAction(e -> toggleCategoria(cbTransporte, new TransporteStrategy()));
//        cbModernas.setOnAction(e -> toggleCategoria(cbModernas, new MudanzaStrategy()));
//
//        cbUnAnio.setOnAction(e -> toggleExperiencia(cbUnAnio, new MaxUnAnioTrabajoStrategy()));
//        cbDosCinco.setOnAction(e -> toggleExperiencia(cbDosCinco, new DosACincoAniosTrabajoStrategy()));
//        cbSeisOMas.setOnAction(e -> toggleExperiencia(cbSeisOMas, new SeisOMasAniosTrabajoStrategy()));
//
//        cbSueldo950.setOnAction(e -> toggleSalario(cbSueldo950, new SueldoBajoStrategy()));
//        cbSueldo1025.setOnAction(e -> toggleSalario(cbSueldo1025, new SueldoMedioStrategy()));
//        cbSueldo1200.setOnAction(e -> toggleSalario(cbSueldo1200, new SueldoAltoStrategy()));
//    }

    private void configurarListView() {
        jobsListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Trabajo item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null :
                        item.getTitulo() + " - " + item.getTipo() + " - S/. " + item.getSueldo());
            }
        });

        jobsListView.setOnMouseClicked(this::mostrarDetalleTrabajo);
    }

    private void toggleCategoria(CheckBox cb, CategoryStrategy estrategia) {
        if (cb.isSelected()) filtroService.agregarFiltroCategoria(estrategia);
        else filtroService.eliminarFiltroCategoria(estrategia);
    }

    private void toggleSalario(CheckBox cb, SalarioStrategy estrategia) {
        if (cb.isSelected()) filtroService.agregarFiltroSalario(estrategia);
        else filtroService.eliminarFiltroSalario(estrategia);
    }

    private void toggleExperiencia(CheckBox cb, ExperienciaStrategy estrategia) {
        if (cb.isSelected()) filtroService.agregarFiltroExperiencia(estrategia);
        else filtroService.eliminarFiltroExperiencia(estrategia);
    }

    private void actualizarVistaConTrabajos(List<Trabajo> trabajos) {
        jobsListView.getItems().setAll(trabajos);
        if (!trabajos.isEmpty()) {
            jobsListView.getSelectionModel().selectFirst();
            mostrarDetalleTrabajo(null);
        } else {
            limpiarDetalleTrabajo();
        }
    }

    private void mostrarDetalleTrabajo(MouseEvent event) {
        Trabajo seleccionado = jobsListView.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            lblTitulo.setText(seleccionado.getTitulo());
            lblEmpresa.setText("Empresa: " + seleccionado.getDescripcion()); // Cambia si hay campo empresa
            lblUbicacion.setText("Ubicación: " + seleccionado.getTipo());    // Cambia si hay campo ubicación
            txtDescripcion.setText(seleccionado.getDescripcion());
        }
    }

    private void limpiarDetalleTrabajo() {
        lblTitulo.setText("Título del trabajo");
        lblEmpresa.setText("Empresa");
        lblUbicacion.setText("Ubicación");
        txtDescripcion.clear();
    }

    private void cargarTodosLosTrabajos() {
        List<Trabajo> trabajos = trabajoRepository.listarTodos();
        actualizarVistaConTrabajos(trabajos);
    }

    @FXML
    private void actualizarBusqueda() {
        filtroService.limpiarFiltros(); // Limpiar filtros anteriores

        // Aplicar filtros seleccionados manualmente
        if (cbConstruccion.isSelected()) filtroService.agregarFiltroCategoria(new ConstruccionStrategy());
        if (cbVentas.isSelected()) filtroService.agregarFiltroCategoria(new VentasStrategy());
        if (cbTransporte.isSelected()) filtroService.agregarFiltroCategoria(new TransporteStrategy());
        if (cbModernas.isSelected()) filtroService.agregarFiltroCategoria(new MudanzaStrategy());

        if (cbUnAnio.isSelected()) filtroService.agregarFiltroExperiencia(new MaxUnAnioTrabajoStrategy());
        if (cbDosCinco.isSelected()) filtroService.agregarFiltroExperiencia(new DosACincoAniosTrabajoStrategy());
        if (cbSeisOMas.isSelected()) filtroService.agregarFiltroExperiencia(new SeisOMasAniosTrabajoStrategy());

        if (cbSueldo950.isSelected()) filtroService.agregarFiltroSalario(new SueldoBajoStrategy());
        if (cbSueldo1025.isSelected()) filtroService.agregarFiltroSalario(new SueldoMedioStrategy());
        if (cbSueldo1200.isSelected()) filtroService.agregarFiltroSalario(new SueldoAltoStrategy());

        // 🔔 Solo aquí se notifica a los observers (filtrado y refresco)
        filtroService.notificar();
    }


    @FXML
    private void postularme() {
        Trabajo seleccionado = jobsListView.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Postulación enviada");
            alerta.setHeaderText(null);
            alerta.setContentText("Te has postulado a: " + seleccionado.getTitulo());
            alerta.showAndWait();
        }
    }

    // Botones navegación
    @FXML private void mostrarPerfil() {
        SceneManager.cambiarVista("perfil-view.fxml", "Mi Perfil");
    }

    @FXML private void mostrarNotificaciones() {
        SceneManager.cambiarVista("notificaciones-view.fxml", "Notificaciones");
    }

    @FXML private void mostrarPostulaciones() {
        SceneManager.cambiarVista("postulaciones-view.fxml", "Mis Postulaciones");
    }
}
