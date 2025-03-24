package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.ejb.ProductoDAO;
import py.com.progweb.prueba.ejb.CategoriaDAO;
import py.com.progweb.prueba.model.ProductoEntity;
import py.com.progweb.prueba.model.CategoriaEntity;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/producto")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductoRest {

    @Inject
    private ProductoDAO productoDAO;

    @Inject
    private CategoriaDAO categoriaDAO;

    @GET
    public Response listar(@QueryParam("nombre") String nombre, @QueryParam("idCategoria") Integer idCategoria) {
        List<ProductoEntity> productos = productoDAO.listarPorNombreYCategoria(nombre, idCategoria);
        return Response.ok(productos).build();
    }

    @POST
    public Response agregar(ProductoEntity producto) {
        CategoriaEntity categoria = categoriaDAO.obtener(producto.getCategoria().getIdCategoria());
        if (categoria == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Categoría no encontrada").build();
        }

        producto.setCategoria(categoria);
        productoDAO.insertar(producto);
        return Response.ok(producto).build();
    }

    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") Integer id, ProductoEntity producto) {
        ProductoEntity existente = productoDAO.obtener(id);
        if (existente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        CategoriaEntity categoria = categoriaDAO.obtener(producto.getCategoria().getIdCategoria());
        if (categoria == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Categoría no encontrada").build();
        }

        existente.setNombre(producto.getNombre());
        existente.setCategoria(categoria);
        existente.setPrecioVenta(producto.getPrecioVenta());
        existente.setCantidadExistente(producto.getCantidadExistente());

        productoDAO.actualizar(existente);
        return Response.ok(existente).build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Integer id) {
        productoDAO.eliminar(id);
        return Response.ok().build();
    }
}
