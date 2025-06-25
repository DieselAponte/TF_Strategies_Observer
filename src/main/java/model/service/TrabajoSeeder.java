package model.service;

import model.factory.*;
import model.model.Trabajo;
import model.repository.TrabajoRepository;

public class TrabajoSeeder {

    private final TrabajoRepository trabajoRepository;

    public TrabajoSeeder(TrabajoRepository trabajoRepository) {
        this.trabajoRepository = trabajoRepository;
    }

    public void cargarDatosIniciales(){
        TrabajoFactory construccionFactory = new TrabajoContruccionFactory();
        TrabajoFactory ventasFactory = new TrabajoVentasFactory();
        TrabajoFactory transporteFactory = new TrabajoTransporteFactory();
        TrabajoFactory mudanzaFactory = new TrabajoMudanzaFactory();

        trabajoRepository.guardar(construccionFactory.crearTrabajo("Ayudante de obra", "Asistencia en obras civiles", "1 año", 950));
        trabajoRepository.guardar(ventasFactory.crearTrabajo("Vendedor ambulante", "Venta de productos en la calle", "Sin experiencia", 1000));
        trabajoRepository.guardar(transporteFactory.crearTrabajo("Repartidor en moto", "Entrega de pedidos", "2 - 5 años", 1400));
        trabajoRepository.guardar(mudanzaFactory.crearTrabajo("Cargador de mudanza", "Apoyo en mudanzas", "1 año", 1000));
    }

}
