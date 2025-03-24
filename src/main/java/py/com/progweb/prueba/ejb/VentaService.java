package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.model.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.*;
import javax.mail.internet.*;
import java.time.LocalDateTime;
import java.util.*;

@Stateless
public class VentaService {

    @Inject private ProductoDAO productoDAO;
    @Inject private ClienteDAO clienteDAO;
    @Inject private VentaDAO ventaDAO;

    public VentaEntity registrarVenta(Integer idCliente, List<DetalleVentaEntity> detalles) throws Exception {
        ClienteEntity cliente = clienteDAO.obtener(idCliente);
        if (cliente == null) throw new Exception("Cliente no encontrado");

        int total = 0;

        for (DetalleVentaEntity detalle : detalles) {
            ProductoEntity producto = productoDAO.obtener(detalle.getProducto().getIdProducto());
            if (producto == null) throw new Exception("Producto no encontrado");

            if (producto.getCantidadExistente() < detalle.getCantidad())
                throw new Exception("No hay stock suficiente para el producto: " + producto.getNombre());

            detalle.setProducto(producto);
            detalle.setPrecio(producto.getPrecioVenta());
            total += producto.getPrecioVenta() * detalle.getCantidad();

            producto.setCantidadExistente(producto.getCantidadExistente() - detalle.getCantidad());
            productoDAO.actualizar(producto);
        }

        VentaEntity venta = new VentaEntity();
        venta.setCliente(cliente);
        venta.setFecha(LocalDateTime.now());
        venta.setTotal(total);

        for (DetalleVentaEntity detalle : detalles) {
            detalle.setVenta(venta);
        }
        venta.setDetalles(detalles);

        ventaDAO.insertar(venta);

        enviarCorreo(cliente, venta);
        return venta;
    }

    private void enviarCorreo(ClienteEntity cliente, VentaEntity venta) {
        // MOCK: Imprimir en consola (puedes integrar JavaMail si querés enviar realmente)
        System.out.println("Enviando correo a " + cliente.getEmail());
        System.out.println("Fecha: " + venta.getFecha());
        System.out.println("Total: " + venta.getTotal());
        for (DetalleVentaEntity d : venta.getDetalles()) {
            System.out.println("- " + d.getProducto().getNombre() +
                               " x " + d.getCantidad() +
                               " @ " + d.getPrecio() +
                               " = " + (d.getCantidad() * d.getPrecio()));
        }
    }
}
