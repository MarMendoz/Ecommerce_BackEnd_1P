package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.ejb.CategoriaDAO;
import py.com.progweb.prueba.model.CategoriaEntity;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/categoria")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoriaRest {

    @Inject
    private CategoriaDAO categoriaDAO;

    @GET
    public Response listar() {
        List<CategoriaEntity> lista = categoriaDAO.listar();
        return Response.ok(lista).build();
    }

    @POST
    public Response agregar(CategoriaEntity c) {
        categoriaDAO.insertar(c);
        return Response.ok(c).build();
    }

    @POST
    @Path("/bulk")
    public Response agregarEnMasa(List<CategoriaEntity> categorias) {
        for (CategoriaEntity categoria : categorias) {
            try {
                categoriaDAO.insertar(categoria);
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error al insertar la categoría: " + categoria.getNombre()).build();
            }
        }
        return Response.ok(categorias).build();
    }

    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") Integer id, CategoriaEntity categoria) {
        CategoriaEntity existente = categoriaDAO.obtener(id);
        if (existente == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        existente.setNombre(categoria.getNombre());
        categoriaDAO.actualizar(existente);
        return Response.ok(existente).build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Integer id) {
        try {
            categoriaDAO.eliminar(id);
            return Response.ok().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("No se puede eliminar la categoria porque esta relacionada con otros registros.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Categoría con id " + id + " no encontrada.").build();
        }
    }
}
