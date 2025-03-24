package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.model.VentaEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class VentaDAO {

    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;

    public void insertar(VentaEntity venta) {
        this.em.persist(venta);
    }

    public VentaEntity obtener(Integer id) {
        return this.em.find(VentaEntity.class, id);
    }

    public List<VentaEntity> listar() {
        return this.em.createQuery("SELECT v FROM VentaEntity v", VentaEntity.class).getResultList();
    }

    public List<VentaEntity> listarVentas(Integer idCliente, LocalDateTime fecha) {
        String jpql = "SELECT v FROM VentaEntity v WHERE 1=1";
        if (idCliente != null) jpql += " AND v.cliente.idCliente = :idCliente";
        if (fecha != null) jpql += " AND DATE(v.fecha) = :fecha";

        TypedQuery<VentaEntity> query = em.createQuery(jpql, VentaEntity.class);
        if (idCliente != null) query.setParameter("idCliente", idCliente);
        if (fecha != null) query.setParameter("fecha", fecha.toLocalDate());

        return query.getResultList();
    }
    public VentaEntity obtenerConDetalles(Integer id) {
        return this.em.createQuery(
            "SELECT v FROM VentaEntity v LEFT JOIN FETCH v.detalles d LEFT JOIN FETCH d.producto p LEFT JOIN FETCH p.categoria WHERE v.idVenta = :id",
            VentaEntity.class
        ).setParameter("id", id)
         .getSingleResult();
    }
    
}
