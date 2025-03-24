package py.com.progweb.prueba.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "detalle_venta")
public class DetalleVentaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_venta")
    private Integer idDetalleVenta;

    @ManyToOne
    @JoinColumn(name = "id_venta", nullable = false)
    private VentaEntity venta;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private ProductoEntity producto;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private Integer precio;

    // Getters y Setters
    public Integer getIdDetalleVenta() {
        return idDetalleVenta;
    }
    
    public void setIdDetalleVenta(Integer idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
    }
    
    public VentaEntity getVenta() {
        return venta;
    }
    
    public void setVenta(VentaEntity venta) {
        this.venta = venta;
    }
    
    public ProductoEntity getProducto() {
        return producto;
    }
    
    public void setProducto(ProductoEntity producto) {
        this.producto = producto;
    }
    
    public Integer getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    
    public Integer getPrecio() {
        return precio;
    }
    
    public void setPrecio(Integer precio) {
        this.precio = precio;
    }
    
}
