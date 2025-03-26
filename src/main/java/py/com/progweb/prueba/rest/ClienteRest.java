package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.ejb.ClienteDAO;
import py.com.progweb.prueba.model.ClienteEntity;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/cliente")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteRest {

    @Inject
    private ClienteDAO clienteDAO;

    @GET
    public Response listar() {
        List<ClienteEntity> clientes = clienteDAO.listar();
        return Response.ok(clientes).build();
    }

    @POST
    public Response agregar(ClienteEntity cliente) {
        try {
            clienteDAO.insertar(cliente);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al insertar el cliente: " + cliente.getNombre()).build();
        }
        return Response.ok(cliente).build();
    }

    @POST
    @Path("/bulk")
    public Response agregarEnMasa(List<ClienteEntity> clientes) {
        StringBuilder errores = new StringBuilder();
        for (ClienteEntity cliente : clientes) {
            try {
                clienteDAO.insertar(cliente);
            } catch (Exception e) {
                errores.append("Error al insertar el cliente: ").append(cliente.getNombre()).append(". ")
                        .append(e.getMessage()).append("\n");
            }
        }
        if (errores.length() > 0) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(errores.toString().trim()).build();
        }
        return Response.ok(clientes).build();
    }

    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") Integer id, ClienteEntity cliente) {
        ClienteEntity existente = clienteDAO.obtener(id);
        if (existente == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        if (cliente.getNombre() != null) {
            existente.setNombre(cliente.getNombre());
        }
        if (cliente.getApellido() != null) {
            existente.setApellido(cliente.getApellido());
        }
        if (cliente.getCedula() != null) {
            existente.setCedula(cliente.getCedula());
        }
        if (cliente.getEmail() != null) {
            existente.setEmail(cliente.getEmail());
        }
        clienteDAO.actualizar(existente);

        return Response.ok(existente).build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Integer id) {
        try {
            clienteDAO.eliminar(id);
            return Response.ok().build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: Cliente relacionado a ventas no puede ser eliminado ")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error inesperado al intentar eliminar el cliente. Detalles: " + e.getMessage())
                    .build();
        }
    }
}
