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
        CategoriaEntity categoria = null;
        try {
            categoria = getCategoria(producto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (categoria == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Categoría no encontrada").build();
        }

        producto.setCategoria(categoria);
        productoDAO.insertar(producto);
        return Response.ok(producto).build();
    }

    private CategoriaEntity getCategoria(ProductoEntity producto) throws Exception {
        CategoriaEntity categoria;
        try {
            categoria = categoriaDAO.obtener(producto.getCategoria().getIdCategoria());
        } catch (Exception e) {
            throw new WebApplicationException("Error al obtener la categoria: " + e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
        return categoria;
    }

    @POST
    @Path("/bulk")
    public Response agregarEnMasa(List<ProductoEntity> productos) {
        for (ProductoEntity producto : productos) {
            CategoriaEntity categoria = null;
            try {
                categoria = getCategoria(producto);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (categoria == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Categoría no encontrada para el producto: " + producto.getNombre()).build();
            }

            producto.setCategoria(categoria);
            try {
                productoDAO.insertar(producto);
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error al insertar el producto: " + producto.getNombre()).build();
            }
        }
        return Response.ok(productos).build();
    }

    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") Integer id, ProductoEntity producto) {
        ProductoEntity existente = productoDAO.obtener(id);
        if (existente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (producto.getCategoria() != null && producto.getCategoria().getIdCategoria() != null) {
            CategoriaEntity categoria = categoriaDAO.obtener(producto.getCategoria().getIdCategoria());
            if (categoria == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Categoría no encontrada").build();
            }
            existente.setCategoria(categoria);
        }

        if (producto.getNombre() != null) {
            existente.setNombre(producto.getNombre());
        }
        if (producto.getPrecioVenta() != null) {
            existente.setPrecioVenta(producto.getPrecioVenta());
        }
        if (producto.getCantidadExistente() != null) {
            existente.setCantidadExistente(producto.getCantidadExistente());
        }

        productoDAO.actualizar(existente);
        return Response.ok(existente).build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Integer id) {
        try {
            productoDAO.eliminar(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().build();
    }
}
