package data;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entities.Perfil;
import entities.Utils;

public class PerfilDAO {
	private Context context;

	public PerfilDAO() {
		context = new Context();
	}
	
	@SuppressWarnings("unchecked")
	public List<Perfil> Listar(){
		Query query = context.getEntityManager().createQuery("select c from Perfil c");

		List<Perfil> listPerfil = query.getResultList();

		return listPerfil;
	}
	
	public void Guardar(Perfil c) {
		context.getEntityManager().getTransaction().begin();
		context.getEntityManager().persist(c);
		context.getEntityManager().getTransaction().commit();
	}

	public void Actualizar(Perfil c) {
		context.getEntityManager().getTransaction().begin();
		context.getEntityManager().merge(c);
		context.getEntityManager().getTransaction().commit();
	}

	public void Eliminar(Perfil c) {
		context.getEntityManager().getTransaction().begin();
		c.setEstado(Utils.Estado.Eliminado);
		context.getEntityManager().merge(c);
		context.getEntityManager().getTransaction().commit();
	}

	public Perfil Obtener(int id) {
		TypedQuery<Perfil> query = context.getEntityManager()
				.createQuery("select u from Perfil u where u.id := id ", Perfil.class).setParameter("id", id);
		Perfil u = query.getSingleResult();

		return u;
	}
}
