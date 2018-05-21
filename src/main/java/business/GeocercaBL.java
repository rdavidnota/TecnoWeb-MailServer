package business;

import java.util.ArrayList;
import java.util.List;

import data.GeocercaDAO;
import entities.Cliente;
import entities.Geocerca;
import entities.Geocerca_Detalle;
import entities.Utils;

public class GeocercaBL {
	GeocercaDAO daoGeocerca;
	MailBL mail;

	public GeocercaBL() {
		daoGeocerca = new GeocercaDAO();
		mail = new MailBL();
	}

	public void Guardar(Geocerca a) {
		daoGeocerca.Guardar(a);
	}

	public void Actualizar(Geocerca a) {
		daoGeocerca.Actualizar(a);
	}

	public void Eliminar(Geocerca a) {
		daoGeocerca.Eliminar(a);
	}

	public Geocerca Obtener(int id) {
		return daoGeocerca.Obtener(id);
	}

	public List<Geocerca> Listar() {
		return daoGeocerca.Listar();
	}

	public void Analizar(String Opcion, String mensaje, String sCorreo) throws Exception {
		String[] aux = Opcion.split(" ");
		String op = "";

		for (int i = 1; i < aux.length && i < 2; i++) {
			op += aux[i];
		}

		switch (op) {
		 case Utils.Accion.Geocerca_Crear:
			 this.Guardar(this.CrearGeoCerca(mensaje));
			 break;
		 case Utils.Accion.Geocerca_Actualizar:
			 this.Actualizar(this.ActualizarGeocerca(mensaje));
			 break;
		 case Utils.Accion.Geocerca_Eliminar:
			 Geocerca g = this.Obtener(Integer.parseInt(mensaje));
			 this.Eliminar(g);
			 break;
		 case Utils.Accion.Geocerca_Ver:
			 Geocerca eGeocerca = this.Obtener(Integer.parseInt(mensaje));
			 mail.SendMail(sCorreo, Utils.Accion.Geocerca_Ver, ObjectToString(eGeocerca));
			 break;
		 case Utils.Accion.Geocerca_Listar:
			 List<Geocerca> eListGeocerca = this.Listar();
			 mail.SendMail(sCorreo, Utils.Accion.Geocerca_Listar, ListObjectToString(eListGeocerca));
			 break;
		default:
			throw new Exception("Error no se reconoce la opcion que se envio");
		}
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

			if (eGeocerca.getClientes() != null) {
				for (Cliente cliente : eGeocerca.getClientes()) {
					result = result + cliente.getNombre() + "; ";
				}
			}else{
				result += "<null>";
			}

			result = result + "], [";

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
	
	public Geocerca ActualizarGeocerca(String cadena) {
		String[] lista = cadena.split(",");
		int id = Integer.parseInt(lista[0]);
		Geocerca geocerca = this.Obtener(id);

		geocerca.setNombre(lista[1]);

		return geocerca;
	}

	public Geocerca CrearGeoCerca(String cadena) throws Exception {
		Geocerca geocerca = new Geocerca();
		String[] lista = cadena.split(",");

		geocerca.setNombre(lista[0]);
		geocerca.setEstado(Utils.Estado.Activo);
		geocerca.setAction(lista[1].charAt(0));

		Cliente originator = new ClienteBL().ObtenerByCI(lista[2]);
		geocerca.setOriginator(originator);

		String[] hijos = lista[3].split(";");

		for (String hijo : hijos) {
			Cliente eHijo = new ClienteBL().ObtenerByCI(hijo);
			
			if(originator.getCi() != eHijo.getPadre().getCi()){
				throw new Exception("Error: El cliente asignado no corresponde a la juridiccion del que lo crea.");
			}
			
			if (geocerca.getClientes() == null) {
				geocerca.setClientes(new ArrayList<Cliente>());
			}

			geocerca.AddClientes(eHijo);
		}

		String[] detalles = lista[4].split(";");
		int cont = 0;
		while (cont < detalles.length) {
			Geocerca_Detalle geocerca_Detalle = new Geocerca_Detalle();

			geocerca_Detalle.setOrden(Integer.parseInt(detalles[cont]));
			geocerca_Detalle.setLatitud(Float.parseFloat(detalles[cont + 1]));
			geocerca_Detalle.setLongitud(Float.parseFloat(detalles[cont + 2]));

			if (geocerca.getDetalle() == null) {
				geocerca.setDetalle(new ArrayList<Geocerca_Detalle>());
			}

			geocerca.AddDetalle(geocerca_Detalle);

			cont += 3;
		}
		return geocerca;
	}

	private String ListObjectToString(List<Geocerca> listGeocerca) {
		String result = "";
		listGeocerca = listGeocerca == null ?  new ArrayList<Geocerca>():listGeocerca;
		for (Geocerca geocerca : listGeocerca) {
			result = result + ObjectToString(geocerca) + "\n";
		}

		return result;
	}
}
