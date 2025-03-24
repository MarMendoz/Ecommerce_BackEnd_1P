package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.ejb.VentaDAO;
import py.com.progweb.prueba.model.DetalleVentaEntity;
import py.com.progweb.prueba.model.ProductoEntity;
import py.com.progweb.prueba.model.CategoriaEntity;
import py.com.progweb.prueba.model.VentaEntity;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.*;
import java.util.stream.Collectors;

@Path("/consulta-detalle-venta")
@Produces(MediaType.APPLICATION_JSON)
public class ConsultaDetalleVentaRest {

    @Inject
    private VentaDAO ventaDAO;

    @GET
    @Path("/{idVenta}")
    public Response detalles(@PathParam("idVenta") Integer idVenta) {
        VentaEntity venta = ventaDAO.obtenerConDetalles(idVenta);
        if (venta == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        List<DetalleVentaEntity> detalles = venta.getDetalles();

        List<Map<String, Object>> resultado = detalles.stream().map(d -> {
            ProductoEntity p = d.getProducto();
            CategoriaEntity c = p.getCategoria();

            Map<String, Object> categoriaInfo = new HashMap<>();
            categoriaInfo.put("id", c.getIdCategoria());
            categoriaInfo.put("nombre", c.getNombre());

            Map<String, Object> productoInfo = new HashMap<>();
            productoInfo.put("id", p.getIdProducto());
            productoInfo.put("nombre", p.getNombre());
            productoInfo.put("categoria", categoriaInfo);

            Map<String, Object> detalleInfo = new HashMap<>();
            detalleInfo.put("producto", productoInfo);
            detalleInfo.put("cantidad", d.getCantidad());
            detalleInfo.put("precioUnitario", d.getPrecio());
            detalleInfo.put("precioTotalDetalle", d.getCantidad() * d.getPrecio());

            return detalleInfo;
        }).collect(Collectors.toList());

        return Response.ok(resultado).build();
    }
}
