package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.model.ProductoEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class ProductoDAO {

    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;

    public void insertar(ProductoEntity p) {
        this.em.persist(p);
    }

    public ProductoEntity obtener(Integer id) {
        return this.em.find(ProductoEntity.class, id);
    }

    public void actualizar(ProductoEntity p) {
        this.em.merge(p);
    }

    public void eliminar(Integer id) {
        ProductoEntity p = this.obtener(id);
        if (p != null) {
            this.em.remove(p);
        }
    }

    public List<ProductoEntity> listar() {
        return this.em.createQuery("SELECT p FROM ProductoEntity p", ProductoEntity.class).getResultList();
    }

    public List<ProductoEntity> listarPorNombreYCategoria(String nombre, Integer idCategoria) {
        String jpql = "SELECT p FROM ProductoEntity p WHERE 1=1";
        if (nombre != null && !nombre.trim().isEmpty()) {
            jpql += " AND LOWER(p.nombre) LIKE LOWER(:nombre)";
        }
        if (idCategoria != null) {
            jpql += " AND p.categoria.idCategoria = :idCategoria";
        }

        TypedQuery<ProductoEntity> query = em.createQuery(jpql, ProductoEntity.class);
        if (nombre != null && !nombre.trim().isEmpty()) {
            query.setParameter("nombre", "%" + nombre + "%");
        }
        if (idCategoria != null) {
            query.setParameter("idCategoria", idCategoria);
        }

        return query.getResultList();
    }
}
