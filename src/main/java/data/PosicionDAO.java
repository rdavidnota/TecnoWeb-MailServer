package data;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entities.Posicion;
import entities.Utils;

public class PosicionDAO {
	private Context context;

	public PosicionDAO() {
		context = new Context();
	}
	
	@SuppressWarnings("unchecked")
	public List<Posicion> Listar(){
		Query query = context.getEntityManager().createQuery("select c from Posicion c");

		List<Posicion> listPosicion = query.getResultList();

		return listPosicion;
	}
	
	public void Guardar(Posicion c) {
		context.getEntityManager().getTransaction().begin();
		context.getEntityManager().persist(c);
		context.getEntityManager().getTransaction().commit();
	}

	public void Actualizar(Posicion c) {
		context.getEntityManager().getTransaction().begin();
		context.getEntityManager().merge(c);
		context.getEntityManager().getTransaction().commit();
	}

	public void Eliminar(Posicion c) {
		context.getEntityManager().getTransaction().begin();
		c.setEstado(Utils.Estado.Eliminado);
		context.getEntityManager().merge(c);
		context.getEntityManager().getTransaction().commit();
	}

	public Posicion Obtener(int id) {
		TypedQuery<Posicion> query = context.getEntityManager()
				.createQuery("select u from Posicion u where u.id := id ", Posicion.class).setParameter("id", id);
		Posicion u = query.getSingleResult();

		return u;
	}
	
	public Posicion ObtenerUltimaPosicion(String ci) {
		TypedQuery<Posicion> query = context.getEntityManager()
				.createQuery("select u from Posicion u where u.cliente.ci := ci order by u.id desc", Posicion.class).setParameter("ci", ci);
		Posicion u = query.getSingleResult();

		return u;
	}
}
