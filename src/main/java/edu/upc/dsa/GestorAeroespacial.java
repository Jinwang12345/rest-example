package edu.upc.dsa;

import edu.upc.dsa.models.Avion;
import edu.upc.dsa.models.Vuelo;
import edu.upc.dsa.models.Maleta;
import java.util.List;

public interface GestorAeroespacial {

    // agregar o modificar un avión.
    void addAvion(String id, String modelo, String compania);

    // Obtener un avión por su identificador.
    Avion obtenerAvion(String id);

    // agregar o modificar un vuelo. Lanza excepción si el avión no existe.
    void addVuelo(String idVuelo, String horaSalida, String horaLlegada, String idAvion, String origen, String destino) throws IllegalArgumentException;

    // Obtener un vuelo por su identificador.
    Vuelo obtenerVuelo(String idVuelo);

    // Facturar una maleta para un vuelo. Se asigna automáticamente un identificador a la maleta y se carga en la bodega.
    // Lanza excepción si el vuelo no existe.
    Maleta facturarMaleta(String idVuelo, String idUsuario) throws IllegalArgumentException;

    // Obtener la lista de maletas facturadas en un vuelo, en el orden en que serían descargadas.
    List<Maleta> obtenerMaletasFacturadas(String idVuelo) throws IllegalArgumentException;

    // Método para limpiar todas las estructuras (útil para tests).
    void limpiar();
}
