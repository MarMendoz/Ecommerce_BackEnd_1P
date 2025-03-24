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
        return this.em.find(CategoriaEntity.class, id);
    }

    public List<CategoriaEntity> listar() {
        return this.em.createQuery("SELECT c FROM CategoriaEntity c", CategoriaEntity.class).getResultList();
    }

    public void actualizar(CategoriaEntity c) {
        this.em.merge(c);
    }

    public void eliminar(Integer id) {
        CategoriaEntity c = this.obtener(id);
        if (c != null) {
            this.em.remove(c);
        }
    }
}
