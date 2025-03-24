package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.model.DetalleVentaEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class DetalleVentaDAO {

    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;

    public void insertar(DetalleVentaEntity detalle) {
        this.em.persist(detalle);
    }
}
