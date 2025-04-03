package edu.upc.dsa;

import edu.upc.dsa.models.Avion;
import edu.upc.dsa.models.Vuelo;
import edu.upc.dsa.models.Maleta;
import org.apache.log4j.Logger;

import java.util.*;

public class GestorAeroespacialImpl implements GestorAeroespacial {
    final static Logger logger = Logger.getLogger(GestorAeroespacialImpl.class);
    private static GestorAeroespacial instance;

    // Estructuras de datos
    private Map<String, Avion> aviones;
    private Map<String, Vuelo> vuelos;
    // Mapa que asocia un vuelo a la lista de maletas facturadas (en orden de facturación)
    private Map<String, List<Maleta>> maletasFacturadas;
    // Contador para generar identificadores de maletas
    private int contadorMaletas;

    // Constructor privado para Singleton
    private GestorAeroespacialImpl() {
        this.aviones = new HashMap<>();
        this.vuelos = new HashMap<>();
        this.maletasFacturadas = new HashMap<>();
        this.contadorMaletas = 1;
    }

    // Método para obtener la instancia única
    public static GestorAeroespacial getInstance() {
        if (instance == null) instance = new GestorAeroespacialImpl();
        return instance;
    }

    @Override
    public void addAvion(String id, String modelo, String compania) {
        logger.info("addAvion - Inicio: id=" + id + ", modelo=" + modelo + ", compania=" + compania );
        Avion avion = aviones.get(id);
        if (avion == null) {
            avion = new Avion(id, modelo, compania);
            aviones.put(id, avion);
        } else {
            // Si ya existe, se actualizan sus datos
            avion.setModelo(modelo);
            avion.setCompania(compania);
        }
        logger.info("addAvion - Fin: " + avion);
    }

    @Override
    public Avion obtenerAvion(String id) {
        logger.info("obtenerAvion - Inicio: id=" + id);
        Avion avion = aviones.get(id);
        logger.info("obtenerAvion - Fin: " + avion);
        return avion;
    }

    @Override
    public void addVuelo(String idVuelo, String horaSalida, String horaLlegada, String idAvion, String origen, String destino) throws IllegalArgumentException {
        logger.info("addVuelo - Inicio: idVuelo=" + idVuelo + ", horaSalida=" + horaSalida + ", horaLlegada=" + horaLlegada +
                ", idAvion=" + idAvion + ", origen=" + origen + ", destino=" + destino);
        // Verificar que el avión existe
        if (!aviones.containsKey(idAvion)) {
            logger.error("addVuelo - Error: El avión con id " + idAvion + " no existe.");
            throw new IllegalArgumentException("El avión con id " + idAvion + " no existe.");
        }
        Vuelo vuelo = vuelos.get(idVuelo);
        if (vuelo == null) {
            vuelo = new Vuelo(idVuelo, horaSalida, horaLlegada, idAvion, origen, destino);
            vuelos.put(idVuelo, vuelo);
        } else {
            // Actualizar datos del vuelo existente
            vuelo.setHoraSalida(horaSalida);
            vuelo.setHoraLlegada(horaLlegada);
            vuelo.setIdAvion(idAvion);
            vuelo.setOrigen(origen);
            vuelo.setDestino(destino);
        }
        logger.info("addVuelo - Fin: " + vuelo);
    }

    @Override
    public Vuelo obtenerVuelo(String idVuelo) {
        logger.info("obtenerVuelo - Inicio: idVuelo=" + idVuelo);
        Vuelo vuelo = vuelos.get(idVuelo);
        logger.info("obtenerVuelo - Fin: " + vuelo);
        return vuelo;
    }

    @Override
    public Maleta facturarMaleta(String idVuelo, String idUsuario) throws IllegalArgumentException {
        logger.info("facturarMaleta - Inicio: idVuelo=" + idVuelo + ", idUsuario=" + idUsuario);
        // Verificar que el vuelo existe
        if (!vuelos.containsKey(idVuelo)) {
            logger.error("facturarMaleta - Error: El vuelo " + idVuelo + " no existe.");
            throw new IllegalArgumentException("El vuelo " + idVuelo + " no existe.");
        }
        // Generar id de maleta automáticamente
        String idMaleta = "M" + contadorMaletas++;
        Maleta maleta = new Maleta(idMaleta, idUsuario);
        // add la maleta a la lista de facturación del vuelo
        List<Maleta> lista = maletasFacturadas.get(idVuelo);
        if (lista == null) {
            lista = new ArrayList<>();
            maletasFacturadas.put(idVuelo, lista);
        }
        // Se añade la maleta al final (la que se facturó primero estará en el fondo)
        lista.add(maleta);
        logger.info("facturarMaleta - Fin: " + maleta);
        return maleta;
    }

    @Override
    public List<Maleta> obtenerMaletasFacturadas(String idVuelo) throws IllegalArgumentException {
        logger.info("obtenerMaletasFacturadas - Inicio: idVuelo=" + idVuelo);
        if (!vuelos.containsKey(idVuelo)) {
            logger.error("obtenerMaletasFacturadas - Error: El vuelo " + idVuelo + " no existe.");
            throw new IllegalArgumentException("El vuelo " + idVuelo + " no existe.");
        }
        List<Maleta> lista = maletasFacturadas.get(idVuelo);
        if (lista == null) {
            lista = new ArrayList<>();
        }
        // Para obtener el orden de descarga: la última maleta facturada se descarga primero.
        List<Maleta> listaInvertida = new ArrayList<>(lista);
        Collections.reverse(listaInvertida);
        logger.info("obtenerMaletasFacturadas - Fin: " + listaInvertida);
        return listaInvertida;
    }

    @Override
    public void limpiar() {
        logger.info("limpiar - Inicio");
        aviones.clear();
        vuelos.clear();
        maletasFacturadas.clear();
        contadorMaletas = 1;
        logger.info("limpiar - Fin");
    }
}
