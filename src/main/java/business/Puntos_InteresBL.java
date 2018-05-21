package business;

import java.util.ArrayList;
import java.util.List;

import data.Puntos_InteresDAO;
import entities.Cliente;
import entities.Puntos_Interes;
import entities.Utils;

public class Puntos_InteresBL {
	Puntos_InteresDAO daoPuntos_Interes;
	MailBL mail;

	public Puntos_InteresBL() {
		daoPuntos_Interes = new Puntos_InteresDAO();
		mail = new MailBL();
	}

	public void Guardar(Puntos_Interes a) {
		daoPuntos_Interes.Guardar(a);
	}

	public void Actualizar(Puntos_Interes a) {
		daoPuntos_Interes.Actualizar(a);
	}

	public void Eliminar(Puntos_Interes a) {
		daoPuntos_Interes.Eliminar(a);
	}

	public Puntos_Interes Obtener(int id) {
		return daoPuntos_Interes.Obtener(id);
	}

	public List<Puntos_Interes> Listar() {
		return daoPuntos_Interes.Listar();
	}

	public void Analizar(String Opcion, String mensaje, String sCorreo) throws Exception {
		String[] aux = Opcion.split(" ");
		String op = "";

		for (int i = 0; i < aux.length && i < 2; i++) {
			if (i == 0) {
				op = op + aux[i];
			} else {
				op = op + " " + aux[i];
			}
		}

		switch (op) {
		case Utils.Accion.Puntos_Interes_Crear:
			this.Guardar(this.CrearPuntos_Interes(mensaje));
			break;
		case Utils.Accion.Puntos_Interes_Actualizar:

			this.Actualizar(this.ActualizarPuntos_Interes(mensaje));

			break;
		case Utils.Accion.Puntos_Interes_Eliminar:
			Puntos_Interes pi = this.Obtener(Integer.parseInt(mensaje));
			this.Eliminar(pi);
			break;
		case Utils.Accion.Puntos_Interes_Ver:
			Puntos_Interes ePuntos = this.Obtener(Integer.parseInt(mensaje));
			mail.SendMail(sCorreo, Utils.Accion.Puntos_Interes_Ver, ObjectToString(ePuntos));
			break;
		case Utils.Accion.Puntos_Interes_Listar:
			List<Puntos_Interes> eListPuntos = this.Listar();
			mail.SendMail(sCorreo, Utils.Accion.Puntos_Interes_Listar, ListObjectToString(eListPuntos));
			break;
		default:
			throw new Exception("Error no se reconoce la opcion que se envio");
		}
	}

	private String ObjectToString(Puntos_Interes puntos_Interes) {
		String result = "";
		if (puntos_Interes != null) {
			result = result + puntos_Interes.getId() + ", ";
			result = result + puntos_Interes.getNombre() + ", ";
			result = result + puntos_Interes.getLatitud() + ", ";
			result = result + puntos_Interes.getLongitud() + ", ";
			result = result + puntos_Interes.getEstado() + ", ";
			result = result + puntos_Interes.getDistancia() + ", ";
			result = result + puntos_Interes.getOriginator().getNombre() + ", ";
			result = result + "[";
			if (puntos_Interes.getClientes() != null) {
				for (Cliente cliente : puntos_Interes.getClientes()) {
					result = result + cliente.getNombre() + "; ";
				}
			}else{
				result += "<null>";
			}
			result = result + "]";
		}else{
			result += "<null>";
		}
		return result;
	}

	public Puntos_Interes CrearPuntos_Interes(String cadena) {
		Puntos_Interes puntos_Interes = new Puntos_Interes();
		String[] lista = cadena.split(",");

		puntos_Interes.setNombre(lista[0]);
		puntos_Interes.setLatitud(Float.parseFloat(lista[1]));
		puntos_Interes.setLongitud(Float.parseFloat(lista[2]));
		puntos_Interes.setEstado(Utils.Estado.Activo);
		puntos_Interes.setDistancia(Float.parseFloat(lista[3]));

		Cliente cliente = new ClienteBL().ObtenerByCI(lista[4]);
		cliente.AddPuntos_Interes_Originator(puntos_Interes);

		String[] hijos = lista[5].split(";");

		for (String hijo : hijos) {
			Cliente eHijo = new ClienteBL().ObtenerByCI(hijo);
			if (puntos_Interes.getClientes() == null) {
				puntos_Interes.setClientes(new ArrayList<Cliente>());
			}

			puntos_Interes.AddClientes(eHijo);
		}

		return puntos_Interes;
	}
	
	public Puntos_Interes ActualizarPuntos_Interes(String cadena) {
		String[] lista = cadena.split(",");
		int id = Integer.parseInt(lista[0]);
		Puntos_Interes puntos_Interes = this.Obtener(id);

		puntos_Interes.setNombre(lista[1]);
		puntos_Interes.setLatitud(Float.parseFloat(lista[2]));
		puntos_Interes.setLongitud(Float.parseFloat(lista[3]));
		puntos_Interes.setDistancia(Float.parseFloat(lista[4]));

		return puntos_Interes;
	}

	private String ListObjectToString(List<Puntos_Interes> listPuntos_Interes) {
		String result = "";
		listPuntos_Interes = listPuntos_Interes == null ?  new ArrayList<Puntos_Interes>():listPuntos_Interes;
		for (Puntos_Interes puntos_Interes : listPuntos_Interes) {
			result = result + ObjectToString(puntos_Interes) + "\n";
		}
		return result;
	}
}
