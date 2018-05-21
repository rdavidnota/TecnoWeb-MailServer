package data;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entities.Cliente;
import entities.Geocerca;
import entities.Puntos_Interes;
import entities.Utils;

public class ClienteDAO {
	private Context context;

	public ClienteDAO() {
		context = new Context();
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> Listar() {
		Query query = context.getEntityManager().createQuery("select c from Cliente c");

		List<Cliente> listCliente = query.getResultList();

		return listCliente;
	}

	public void Guardar(Cliente c) {
		context.getEntityManager().getTransaction().begin();
		context.getEntityManager().persist(c);
		context.getEntityManager().getTransaction().commit();
	}

	public void Actualizar(Cliente c) {
		context.getEntityManager().getTransaction().begin();
		context.getEntityManager().merge(c);
		context.getEntityManager().getTransaction().commit();
	}

	public void Eliminar(Cliente c) {
		context.getEntityManager().getTransaction().begin();
		c.setEstado(Utils.Estado.Eliminado);
		context.getEntityManager().merge(c);
		context.getEntityManager().getTransaction().commit();
	}

	public Cliente Obtener(int id) {
		TypedQuery<Cliente> query = context.getEntityManager()
				.createQuery("select u from Cliente u where u.id := id ", Cliente.class).setParameter("id", id);
		Cliente u = query.getSingleResult();

		return u;
	}

	public Cliente ObtenerByCI(String ci) {
		TypedQuery<Cliente> query = context.getEntityManager()
				.createQuery("select u from Cliente u where u.ci := ci ", Cliente.class).setParameter("ci", ci);
		Cliente u = query.getSingleResult();

		return u;
	}

	public List<Cliente> ObtenerHijos(String ci) {
		TypedQuery<Cliente> query = context.getEntityManager()
				.createQuery("select u from Cliente u where u.padre.ci := ci ", Cliente.class).setParameter("ci", ci);
		List<Cliente> u = query.getResultList();

		return u;
	}
	
	public Cliente ObtenerPadre(String ci) {
		TypedQuery<Cliente> query = context.getEntityManager()
				.createQuery("select u from Cliente u where u.ci := ci ", Cliente.class).setParameter("ci", ci);
		Cliente u = query.getSingleResult();

		return u.getPadre();
	}
	
	public List<Geocerca> ObtenerGeocercasAsignadas(String ci) {
		TypedQuery<Cliente> query = context.getEntityManager()
				.createQuery("select u from Cliente u where u.ci := ci ", Cliente.class).setParameter("ci", ci);
		Cliente g = query.getSingleResult();

		return g.getGeocercas();
	}
	
	public List<Geocerca> ObtenerGeocercasCreadas(String ci) {
		TypedQuery<Cliente> query = context.getEntityManager()
				.createQuery("select u from Cliente u where u.ci := ci ", Cliente.class).setParameter("ci", ci);
		Cliente g = query.getSingleResult();

		return g.getGeocercas_originator();
	}
	
	public List<Puntos_Interes> ObtenerPuntos_InteresAsignadas(String ci) {
		TypedQuery<Cliente> query = context.getEntityManager()
				.createQuery("select u from Cliente u where u.ci := ci ", Cliente.class).setParameter("ci", ci);
		Cliente g = query.getSingleResult();

		return g.getPuntos_interes();
	}
	
	public List<Puntos_Interes> ObtenerPuntos_InteresCreadas(String ci) {
		TypedQuery<Cliente> query = context.getEntityManager()
				.createQuery("select u from Cliente u where u.ci := ci ", Cliente.class).setParameter("ci", ci);
		Cliente g = query.getSingleResult();

		return g.getPuntos_interes_originator();
	}
}
