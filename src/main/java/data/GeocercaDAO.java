package data;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entities.Geocerca;
import entities.Utils;

public class GeocercaDAO {
	private Context context;

	public GeocercaDAO() {
		context = new Context();
	}

	@SuppressWarnings("unchecked")
	public List<Geocerca> Listar() {
		Query query = context.getEntityManager().createQuery("select g from geocerca g");

		List<Geocerca> listGeocerca = query.getResultList();

		return listGeocerca;
	}

	public void Guardar(Geocerca g) {
		context.getEntityManager().getTransaction().begin();
		context.getEntityManager().persist(g);
		context.getEntityManager().getTransaction().commit();
	}

	public void Actualizar(Geocerca g) {
		context.getEntityManager().getTransaction().begin();
		context.getEntityManager().merge(g);
		context.getEntityManager().getTransaction().commit();
	}

	public void Eliminar(Geocerca g) {
		context.getEntityManager().getTransaction().begin();
		g.setEstado(Utils.Estado.Eliminado);
		context.getEntityManager().merge(g);
		context.getEntityManager().getTransaction().commit();
	}

	public Geocerca Obtener(int id) {
		TypedQuery<Geocerca> query = context.getEntityManager()
				.createQuery("select u from geocerca u where u.id = id ", Geocerca.class).setParameter("id", id);
		Geocerca g = query.getSingleResult();

		return g;
	}
}
