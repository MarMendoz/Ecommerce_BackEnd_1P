package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.model.ClienteEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ClienteDAO {

    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;

    public void insertar(ClienteEntity c) {
        this.em.persist(c);
    }

    public ClienteEntity obtener(Integer id) {
        return this.em.find(ClienteEntity.class, id);
    }

    public List<ClienteEntity> listar() {
        return this.em.createQuery("SELECT c FROM ClienteEntity c", ClienteEntity.class).getResultList();
    }

    public void actualizar(ClienteEntity c) {
        this.em.merge(c);
    }

    public void eliminar(Integer id) {
        ClienteEntity c = this.obtener(id);
        if (c != null) {
            this.em.remove(c);
        }
    }
}
