package business;

import java.util.ArrayList;
import java.util.List;

import data.ClienteDAO;
import entities.Cliente;
import entities.Geocerca;
import entities.Geocerca_Detalle;
import entities.Puntos_Interes;
import entities.Utils;

public class ClienteBL {
	ClienteDAO daoCliente;
	MailBL mail;

	public ClienteBL() {
		daoCliente = new ClienteDAO();
		mail = new MailBL();
	}

	public void Guardar(Cliente a) {
		daoCliente.Guardar(a);
	}

	public void Actualizar(Cliente a) {
		daoCliente.Actualizar(a);
	}

	public void Eliminar(Cliente a) {
		daoCliente.Eliminar(a);
	}

	public Cliente Obtener(int id) {
		return daoCliente.Obtener(id);
	}

	public List<Cliente> Listar() {
		return daoCliente.Listar();
	}

	public Cliente ObtenerByCI(String ci) {
		return daoCliente.ObtenerByCI(ci);
	}

	public List<Cliente> ObtenerHijos(String ci) {
		return daoCliente.ObtenerHijos(ci);
	}

	public Cliente ObtenerPadre(String ci) {
		return daoCliente.ObtenerPadre(ci);
	}

	public List<Geocerca> ObtenerGeocercasAsignadas(String ci) {
		return daoCliente.ObtenerGeocercasAsignadas(ci);
	}

	public List<Geocerca> ObtenerGeocercasCreadas(String ci) {
		return daoCliente.ObtenerGeocercasCreadas(ci);
	}

	public List<Puntos_Interes> ObtenerPuntos_InteresAsignadas(String ci) {
		return daoCliente.ObtenerPuntos_InteresAsignadas(ci);
	}

	public List<Puntos_Interes> ObtenerPuntos_InteresCreadas(String ci) {
		return daoCliente.ObtenerPuntos_InteresCreadas(ci);
	}

	public void SetPadre(String cadena){
		String[] lista = cadena.split(",");

		String ci= lista[0];
		String hijo= lista[1];
		
		Cliente c = this.ObtenerByCI(ci);
		Cliente c2 = this.ObtenerByCI(hijo);
		
		c2.setPadre(c);
		
		this.Actualizar(c2);
	}
	public void Analizar(String Opcion, String mensaje, String sCorreo) throws Exception {
		String[] aux = Opcion.split(" ");
		String op = "";

		for (int i = 1; i < aux.length && i < 2; i++) {
			op += aux[i];
		}

		switch (op) {
		case Utils.Accion.Cliente_Crear:
			this.Guardar(this.CrearCliente(mensaje));
			break;
		case Utils.Accion.Cliente_Actualizar:

			this.Actualizar(this.ActualizarCliente(mensaje));

			break;
		case Utils.Accion.Cliente_Eliminar:
			Cliente c = this.Obtener(Integer.parseInt(mensaje));
			this.Eliminar(c);
			break;
		case Utils.Accion.Cliente_Ver:
			Cliente eCliente = this.Obtener(Integer.parseInt(mensaje));
			mail.SendMail(sCorreo, Utils.Accion.Cliente_Ver, ObjectToString(eCliente));
			break;
		case Utils.Accion.Cliente_Listar:
			List<Cliente> eListCliente = this.Listar();
			mail.SendMail(sCorreo, Utils.Accion.Cliente_Listar, ListObjectToString(eListCliente));
			break;
		case Utils.Accion.Cliente_ObtenerGeocercasAsignadas:
			List<Geocerca> eListGeocerca= this.ObtenerGeocercasAsignadas(mensaje);
			mail.SendMail(sCorreo, Utils.Accion.Cliente_ObtenerGeocercasAsignadas, ListGeocercas(eListGeocerca));
			break;
		case Utils.Accion.Cliente_ObtenerGeocercasCreadas:
			List<Geocerca> algo2 = this.ObtenerGeocercasCreadas(mensaje);
			mail.SendMail(sCorreo, Utils.Accion.Cliente_ObtenerGeocercasCreadas, ListGeocercas(algo2));
			break;
		case Utils.Accion.Cliente_ObtenerHijos:
			List<Cliente> eListHijos = this.ObtenerHijos(mensaje);
			mail.SendMail(sCorreo, Utils.Accion.Cliente_ObtenerHijos, ListObjectToString(eListHijos));
			break;
		case Utils.Accion.Cliente_ObtenerPadre:
			Cliente ePadre = this.ObtenerPadre(mensaje);
			mail.SendMail(sCorreo, Utils.Accion.Cliente_ObtenerPadre, ObjectToString(ePadre));
			break;
		case Utils.Accion.Cliente_ObtenerPuntos_InteresAsignadas:
			List<Puntos_Interes> ePuntos = this.ObtenerPuntos_InteresAsignadas(mensaje);
			mail.SendMail(sCorreo, Utils.Accion.Cliente_ObtenerPuntos_InteresAsignadas, ListPuntos_Interes(ePuntos));	
			break;
		case Utils.Accion.Cliente_ObtenerPuntos_InteresCreadas:
			List<Puntos_Interes> ePuntosC = this.ObtenerPuntos_InteresCreadas(mensaje);
			mail.SendMail(sCorreo, Utils.Accion.Cliente_ObtenerPuntos_InteresCreadas, ListPuntos_Interes(ePuntosC));
			break;
		case Utils.Accion.Cliente_ObtenerByCI:
			Cliente eObtenerByCI = this.ObtenerByCI(mensaje);
			mail.SendMail(sCorreo, Utils.Accion.Cliente_ObtenerByCI, ObjectToString(eObtenerByCI));
			break;
		case Utils.Accion.Cliente_SetPadre:
			this.SetPadre(mensaje);
			break;
		default:
			throw new Exception("Error no se reconoce la opcion que se envio");
		}
	}

	private String ObjectToString(Cliente eCliente) {
		String result = "";
		if (eCliente != null) {
			result = result + eCliente.getId() + ", ";
			result = result + eCliente.getNombre() + ", ";
			result = result + eCliente.getCi() + ", ";
			result = result + eCliente.getCorreo();
		}else{
			result += "<null>";
		}
		return result;
	}

	public Cliente CrearCliente(String cadena) {
		Cliente c = new Cliente();
		String[] lista = cadena.split(",");

		c.setNombre(lista[0]);
		c.setCi(lista[1]);
		c.setCorreo(lista[2]);

		c.setEstado(Utils.Estado.Activo);

//		c.setPerfil(new Perfil());
//		c.getPerfil().setTelefono(lista[3]);
//		c.getPerfil().setEstado(Utils.Estado.Activo);

		return c;
	}

	public Cliente ActualizarCliente(String cadena) {
		String[] lista = cadena.split(",");
		int id = Integer.parseInt(lista[0]);
		Cliente cliente = this.Obtener(id);

		cliente.setNombre(lista[1]);
		cliente.setCi(lista[2]);
		cliente.setCorreo(lista[3]);

		return cliente;
	}

	private String ObjectToString(Puntos_Interes puntos_Interes) {
		String result = "";
		if (puntos_Interes != null) {
			result = result + puntos_Interes.getId() + ", ";
			result = result + puntos_Interes.getNombre() + ", ";
			result = result + puntos_Interes.getLatitud() + ", ";
			result = result + puntos_Interes.getLongitud() + ", ";
			result = result + puntos_Interes.getEstado() + ", ";
			result = result + puntos_Interes.getDistancia();
		}else{
			result += "<null>";
		}
		return result;
	}

	private String ObjectToString(Geocerca eGeocerca) {
		String result = "";
		if (eGeocerca != null) {
			result = result + eGeocerca.getId() + ", ";
			result = result + eGeocerca.getNombre() + ", ";
			result = result + eGeocerca.getEstado() + ", ";
			result = result + eGeocerca.getAction() + ", ";
			result = result + eGeocerca.getOriginator().getNombre() + ", ";
			result = result + "[";

			if (eGeocerca.getDetalle() != null) {
				for (Geocerca_Detalle detalle : eGeocerca.getDetalle()) {
					result = result + detalle.getOrden() + "; ";
					result = result + detalle.getLatitud() + "; ";
					result = result + detalle.getLongitud() + "; ";
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

	private String ListGeocercas(List<Geocerca> listGeocerca) {
		String result = "";
		listGeocerca = listGeocerca == null ?  new ArrayList<Geocerca>():listGeocerca;
		for (Geocerca geocerca : listGeocerca) {
			result = result + ObjectToString(geocerca) + "\n";
		}

		return result;
	}

	private String ListPuntos_Interes(List<Puntos_Interes> listPuntos_Interes) {
		String result = "";
		listPuntos_Interes = listPuntos_Interes == null ?  new ArrayList<Puntos_Interes>():listPuntos_Interes;
		for (Puntos_Interes puntos_Interes : listPuntos_Interes) {
			result = result + ObjectToString(puntos_Interes) + "\n";
		}
		return result;
	}

	private String ListObjectToString(List<Cliente> eListCliente) {
		String result = "";
		eListCliente = eListCliente == null ?  new ArrayList<Cliente>():eListCliente;
		for (Cliente eCliente : eListCliente) {
			result = result + ObjectToString(eCliente) + "\n";
		}
		return result;
	}
}
