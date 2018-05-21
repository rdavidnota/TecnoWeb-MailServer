package business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import data.PosicionDAO;
import entities.Cliente;
import entities.Posicion;
import entities.Utils;

public class PosicionBL {
	PosicionDAO daoPosicion;
	MailBL mail;

	public PosicionBL() {
		daoPosicion = new PosicionDAO();
		mail = new MailBL();
	}

	public void Guardar(Posicion a) {
		daoPosicion.Guardar(a);
	}

	public void Actualizar(Posicion a) {
		daoPosicion.Actualizar(a);
	}

	public void Eliminar(Posicion a) {
		daoPosicion.Eliminar(a);
	}

	public Posicion Obtener(int id) {
		return daoPosicion.Obtener(id);
	}

	public List<Posicion> Listar() {
		return daoPosicion.Listar();
	}

	public Posicion ObtenerUltimaPosicion(String ci) {
		return daoPosicion.ObtenerUltimaPosicion(ci);
	}
	
	public String MostrarGoogleMaps(Posicion pos){
		String url = String.format("https://www.google.com/maps/search/?api=1&query=%f,%f", pos.getLatitud(),pos.getLongitud());
		return url;
	}

	public void Analizar(String Opcion, String mensaje, String sCorreo) throws Exception {
		String[] aux = Opcion.split(" ");
		String op = "";

		for (int i = 1; i < aux.length && i < 2; i++) {
			op += aux[i];
		}

		switch (op) {
		case Utils.Accion.Posicion_Crear:
			this.Guardar(this.CrearPosicion(mensaje));
			break;
		case Utils.Accion.Posicion_Listar:
			List<Posicion> listPosicion = this.Listar();
			mail.SendMail(sCorreo, Utils.Accion.Cliente_Listar, ListObjectToString(listPosicion));
			break;
		case Utils.Accion.Posicion_ObtenerUltimaPosicion:
			Posicion pos = this.ObtenerUltimaPosicion(mensaje);
			mail.SendMail(sCorreo,Utils.Accion.Posicion_ObtenerUltimaPosicion, ObjectToString(pos));
			break;
		case Utils.Accion.Posicion_GoogleMaps:
			Posicion pos2 = this.ObtenerUltimaPosicion(mensaje);
			mail.SendMail(sCorreo,Utils.Accion.Posicion_GoogleMaps, this.MostrarGoogleMaps(pos2));
			break;
		default:
			throw new Exception("Error no se reconoce la opcion que se envio");
		}
	}

	private String ObjectToString(Posicion posicion) {
		String result = "";
		if (posicion != null) {
			result = result + posicion.getId() + ", ";
			result = result + posicion.getCliente().getNombre() + ", ";
			result = result + posicion.getLatitud() + ", ";
			result = result + posicion.getLongitud() + ", ";
			result = result + posicion.getFecha() + ", ";
			result = result + posicion.getEstado();
		}else{
			result += "<null>";
		}
		return result;
	}

	public Posicion CrearPosicion(String cadena) {
		Posicion posicion = new Posicion();
		String[] lista = cadena.split(",");

		Cliente cliente = new ClienteBL().ObtenerByCI(lista[0]);
		cliente.AddPosicion(posicion);

		posicion.setFecha(new Date());
		posicion.setLatitud(Float.parseFloat(lista[1]));
		posicion.setLongitud(Float.parseFloat(lista[2]));
		posicion.setEstado(Utils.Estado.Activo);

		return posicion;
	}

	private String ListObjectToString(List<Posicion> listPosicion) {
		String result = "";
		listPosicion = listPosicion == null ?  new ArrayList<Posicion>():listPosicion;
		for (Posicion eCliente : listPosicion) {
			result = result + ObjectToString(eCliente) + "\n";
		}
		return result;
	}
	
}
