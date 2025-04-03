package edu.upc.dsa.services;

import edu.upc.dsa.GestorAeroespacial;
import edu.upc.dsa.GestorAeroespacialImpl;
import edu.upc.dsa.models.Avion;
import edu.upc.dsa.models.Vuelo;
import edu.upc.dsa.models.Maleta;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/aeroespacial", description = "Servicio REST para la gestión aeronáutica")
@Path("/aeroespacial")
public class AeroespacialService {

    private GestorAeroespacial gestor;

    public AeroespacialService() {
        // Utilizamos el Singleton de la fachada
        this.gestor = GestorAeroespacialImpl.getInstance();
    }

    // Endpoint para agregar o modificar un avión
    @POST
    @Path("/avion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Agregar o modificar un avión", notes = "Agrega o modifica un avión en el sistema")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Avión agregado o modificado correctamente"),
            @ApiResponse(code = 400, message = "Datos incorrectos")
    })
    public Response agregarAvion(Avion avion) {
        if(avion.getId() == null || avion.getModelo() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        gestor.addAvion(avion.getId(), avion.getModelo(), avion.getCompania());
        return Response.status(Response.Status.CREATED).entity(avion).build();
    }

    // Endpoint para agregar o modificar un vuelo
    @POST
    @Path("/Vuelo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Agregar o modificar un vuelo", notes = "Agrega o modifica un vuelo en el sistema")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Vuelo agregado o modificado correctamente"),
            @ApiResponse(code = 400, message = "Datos incorrectos o avión inexistente")
    })
    public Response agregarVuelo(Vuelo vuelo) {
        try {
            gestor.addVuelo(vuelo.getIdVuelo(), vuelo.getHoraSalida(), vuelo.getHoraLlegada(),
                    vuelo.getIdAvion(), vuelo.getOrigen(), vuelo.getDestino());
            return Response.status(Response.Status.CREATED).entity(vuelo).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    // Endpoint para facturar una maleta para un vuelo
    @POST
    @Path("/maleta")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Facturar una maleta", notes = "Factura una maleta para un vuelo dado y un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Maleta facturada correctamente"),
            @ApiResponse(code = 400, message = "Datos incorrectos o vuelo inexistente")
    })
    public Response facturarMaleta(MaletaRequest request) {
        try {
            Maleta maleta = gestor.facturarMaleta(request.getIdVuelo(), request.getIdUsuario());
            return Response.status(Response.Status.CREATED).entity(maleta).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    // Endpoint para obtener la lista de maletas facturadas en un vuelo (orden de descarga)
    @GET
    @Path("/maleta/{idVuelo}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Obtener maletas facturadas", notes = "Obtiene la lista de maletas facturadas en un vuelo, en orden de descarga")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de maletas obtenida"),
            @ApiResponse(code = 404, message = "Vuelo no encontrado")
    })
    public Response obtenerMaletas(@PathParam("idVuelo") String idVuelo) {
        try {
            List<Maleta> maletas = gestor.obtenerMaletasFacturadas(idVuelo);
            return Response.status(Response.Status.OK).entity(maletas).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
