package data;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entities.Puntos_Interes;
import entities.Utils;

public class Puntos_InteresDAO {
	private Context context;

	public Puntos_InteresDAO() {
		context = new Context();
	}
	
	@SuppressWarnings("unchecked")
	public List<Puntos_Interes> Listar(){
		Query query = context.getEntityManager().createQuery("select c from Puntos_Interes c");

		List<Puntos_Interes> listPuntos_Interes = query.getResultList();

		return listPuntos_Interes;
	}
	
	public void Guardar(Puntos_Interes c) {
		context.getEntityManager().getTransaction().begin();
		context.getEntityManager().persist(c);
		context.getEntityManager().getTransaction().commit();
	}

	public void Actualizar(Puntos_Interes c) {
		context.getEntityManager().getTransaction().begin();
		context.getEntityManager().merge(c);
		context.getEntityManager().getTransaction().commit();
	}

	public void Eliminar(Puntos_Interes c) {
		context.getEntityManager().getTransaction().begin();
		c.setEstado(Utils.Estado.Eliminado);
		context.getEntityManager().merge(c);
		context.getEntityManager().getTransaction().commit();
	}

	public Puntos_Interes Obtener(int id) {
		TypedQuery<Puntos_Interes> query = context.getEntityManager()
				.createQuery("select u from Puntos_Interes u where u.id := id ", Puntos_Interes.class).setParameter("id", id);
		Puntos_Interes u = query.getSingleResult();

		return u;
	}
}
