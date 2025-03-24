package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.ejb.VentaDAO;
import py.com.progweb.prueba.model.VentaEntity;
import py.com.progweb.prueba.model.ClienteEntity;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Path("/consulta-venta")
@Produces(MediaType.APPLICATION_JSON)
public class ConsultaVentaRest {

    @Inject
    private VentaDAO ventaDAO;

    @GET
    public Response listar(@QueryParam("idCliente") Integer idCliente,
                           @QueryParam("fecha") String fechaStr) {
        LocalDateTime fecha = null;
        if (fechaStr != null) {
            fecha = LocalDate.parse(fechaStr).atStartOfDay();
        }

        List<VentaEntity> ventas = ventaDAO.listarVentas(idCliente, fecha);
        List<Map<String, Object>> resultado = ventas.stream().map(v -> {
            ClienteEntity cliente = v.getCliente();
            Map<String, Object> clienteInfo = new HashMap<>();
            clienteInfo.put("id", cliente.getIdCliente());
            clienteInfo.put("nombre", cliente.getNombre());
            clienteInfo.put("apellido", cliente.getApellido());

            Map<String, Object> ventaInfo = new HashMap<>();
            ventaInfo.put("idVenta", v.getIdVenta());
            ventaInfo.put("fecha", v.getFecha());
            ventaInfo.put("total", v.getTotal());
            ventaInfo.put("cliente", clienteInfo);

            return ventaInfo;
        }).collect(Collectors.toList());

        return Response.ok(resultado).build();
    }
}
