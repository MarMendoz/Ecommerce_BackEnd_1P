package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.model.CategoriaEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CategoriaDAO {

    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;

    public void insertar(CategoriaEntity c) {
        this.em.persist(c);
    }

    public CategoriaEntity obtener(Integer id) {
        CategoriaEntity entity = this.em.find(CategoriaEntity.class, id);
        if (entity == null) {
            throw new IllegalArgumentException("CategoriaEntity con id " + id + " no encontrada");
        }
        return entity;
    }

    public List<CategoriaEntity> listar() {
        return this.em.createQuery("SELECT c FROM CategoriaEntity c", CategoriaEntity.class).getResultList();
    }

    public void actualizar(CategoriaEntity c) {
        this.em.merge(c);
    }

    public void eliminar(Integer id) throws Exception {
        CategoriaEntity c = this.obtener(id);
        if (c != null) {
            if (!this.em.createQuery("SELECT p FROM ProductoEntity p WHERE p.categoria.id = :id")
                .setParameter("id", id)
                .getResultList()
                .isEmpty()) {
                throw new IllegalArgumentException("No se puede eliminar la categoria porque está relacionada con productos.");
            }
            this.em.remove(c);
        }
    }
}
