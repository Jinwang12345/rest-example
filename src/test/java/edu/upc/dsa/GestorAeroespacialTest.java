package edu.upc.dsa;

import edu.upc.dsa.models.Avion;
import edu.upc.dsa.models.Vuelo;
import edu.upc.dsa.models.Maleta;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class GestorAeroespacialTest {

    // Variable que representa el componente de negocio
    GestorAeroespacial gestor;

    @Before
    public void setUp() {
        // Obtenemos la instancia (singleton) y reiniciamos el estado
        gestor = GestorAeroespacialImpl.getInstance();
        gestor.limpiar();

        // Añadimos aviones
        gestor.addAvion("A1", "Boeing 737", "Boeing" );
        gestor.addAvion("A2", "Airbus A320", "Airbus");

        // Añadimos un vuelo utilizando el avión A1
        gestor.addVuelo("V1", "10:00", "12:00", "A1", "MAD", "BCN");
    }

    @After
    public void tearDown() {
        // Reiniciamos las estructuras
        gestor.limpiar();
    }

    @Test
    public void testAgregarAvionYModificar() {
        // Si se añade un avión con un identificador ya existente, se deben modificar sus datos.
        gestor.addAvion("A1", "Boeing 737 Actualizado", "Boeing");
        Avion avion = gestor.obtenerAvion("A1");
        Assert.assertEquals("Boeing 737 Actualizado", avion.getModelo());
    }

    @Test
    public void testAgregarVueloConAvionExistente() {
        // Verificamos que el vuelo "V1" agregado en el setUp existe y utiliza el avión "A1"
        Vuelo vuelo = gestor.obtenerVuelo("V1");
        Assert.assertNotNull(vuelo);
        Assert.assertEquals("A1", vuelo.getIdAvion());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAgregarVueloConAvionInexistente() {
        // Se intenta agregar un vuelo con un avión inexistente ("A3") y se espera excepción
        gestor.addVuelo("V2", "13:00", "15:00", "A3", "MAD", "BCN");
    }

    @Test
    public void testFacturarMaleta() throws Exception {
        // Facturamos una maleta para el vuelo "V1" para el usuario "U1"
        Maleta maleta1 = gestor.facturarMaleta("V1", "U1");
        Assert.assertNotNull(maleta1);
        Assert.assertNotNull(maleta1.getIdMaleta());

        // Facturamos otra maleta para el mismo vuelo para el usuario "U2"
        Maleta maleta2 = gestor.facturarMaleta("V1", "U2");

        // Obtenemos la lista de maletas facturadas en "V1"
        List<Maleta> maletas = gestor.obtenerMaletasFacturadas("V1");
        // Suponemos que se cargan en la bodega desde el fondo hacia el exterior;
        // por tanto, la primera maleta facturada (maleta1) estará en el fondo y será la última en descargar,
        // y la segunda (maleta2) se descargará primero.
        Assert.assertEquals(maleta2.getIdMaleta(), maletas.get(0).getIdMaleta());
        Assert.assertEquals(maleta1.getIdMaleta(), maletas.get(1).getIdMaleta());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFacturarMaletaVueloInexistente() throws Exception {
        // Se intenta facturar una maleta para un vuelo inexistente "V99" y se espera excepción.
        gestor.facturarMaleta("V99", "U1");
    }
}
