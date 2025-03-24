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
        clienteDAO.insertar(cliente);
        return Response.ok(cliente).build();
    }

    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") Integer id, ClienteEntity cliente) {
        ClienteEntity existente = clienteDAO.obtener(id);
        if (existente == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        existente.setNombre(cliente.getNombre());
        existente.setApellido(cliente.getApellido());
        existente.setCedula(cliente.getCedula());
        existente.setEmail(cliente.getEmail());
        clienteDAO.actualizar(existente);

        return Response.ok(existente).build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Integer id) {
        clienteDAO.eliminar(id);
        return Response.ok().build();
    }
}
