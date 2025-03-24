package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.ejb.VentaService;
import py.com.progweb.prueba.model.DetalleVentaEntity;
import py.com.progweb.prueba.model.VentaEntity;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/venta")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VentaRest {

    @Inject
    private VentaService ventaService;

    @POST
    public Response registrarVenta(@QueryParam("idCliente") Integer idCliente, List<DetalleVentaEntity> detalles) {
        try {
            VentaEntity venta = ventaService.registrarVenta(idCliente, detalles);
            return Response.ok(venta).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
