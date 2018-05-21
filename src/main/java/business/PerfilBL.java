package business;

import java.util.ArrayList;
import java.util.List;

import data.PerfilDAO;
import entities.Cliente;
import entities.Perfil;
import entities.Utils;

public class PerfilBL {
	PerfilDAO daoPerfil;
	MailBL mail;

	public PerfilBL() {
		daoPerfil = new PerfilDAO();
		mail = new MailBL();
	}

	public void Guardar(Perfil a) {
		daoPerfil.Guardar(a);
	}

	public void Actualizar(Perfil a) {
		daoPerfil.Actualizar(a);
	}

	public void Eliminar(Perfil a) {
		daoPerfil.Eliminar(a);
	}

	public Perfil Obtener(int id) {
		return daoPerfil.Obtener(id);
	}

	public List<Perfil> Listar() {
		return daoPerfil.Listar();
	}

	public void Analizar(String Opcion, String mensaje, String sCorreo) throws Exception {
		String[] aux = Opcion.split(" ");
		String op = "";

		for (int i = 1; i < aux.length && i < 2; i++) {
			op += aux[i];
		}

		switch (op) {
		// case Utils.Accion.Cliente_Crear:
		// this.Guardar(new Cliente(mensaje));
		// break;
		// case Utils.Accion.Cliente_Actualizar:
		// String[] auxUpdate = mensaje.split(",");
		// Cliente au = this.Obtener(Long.parseLong(auxUpdate[0]));
		// au.setNombre(auxUpdate[1]);
		//
		// this.Actualizar(au);
		// break;
		// case Utils.Accion.Cliente_Eliminar:
		// Cliente c = this.Obtener(Long.parseLong(mensaje));
		// this.Eliminar(c);
		// break;
		// case Utils.Accion.Cliente_Ver:
		// Cliente eCliente = this.Obtener(Long.parseLong(mensaje));
		// mail.SendMail(sCorreo, Utils.Accion.Almacen_Ver,
		// ObjectToString(eCliente));
		// break;
		case Utils.Accion.Perfil_Crear:
			this.Guardar(this.CrearPerfil(mensaje));
			break;
		case Utils.Accion.Perfil_Actualizar:
			this.Actualizar(this.ActualizarPerfil(mensaje));
			break;
		case Utils.Accion.Perfil_Eliminar:
			Perfil p = this.Obtener(Integer.parseInt(mensaje));
			this.Eliminar(p);
			break;
		case Utils.Accion.Perfil_Ver:
			Perfil ePerfil = this.Obtener(Integer.parseInt(mensaje));
			mail.SendMail(sCorreo, Utils.Accion.Perfil_Ver, ObjectToString(ePerfil));
			break;
		case Utils.Accion.Perfil_Listar:
			List<Perfil> eListPerfil = this.Listar();
			mail.SendMail(sCorreo, Utils.Accion.Perfil_Listar, ListObjectToString(eListPerfil));
			break;
		default:
			throw new Exception("Error no se reconoce la opcion que se envio");
		}
	}

	private String ObjectToString(Perfil perfil) {
		String result = "";
		if (perfil != null) {
			result = result + perfil.getId() + ", ";
			result = result + perfil.getTelefono() + ", ";
			result = result + perfil.getEstado() + ", ";
		}else{
			result += "<null>";
		}
		return result;
	}

	public Perfil CrearPerfil(String cadena) {
		Perfil perfil = new Perfil();
		String[] lista = cadena.split(",");

		perfil.setTelefono(lista[0]);
		perfil.setEstado(Utils.Estado.Activo);

		Cliente cliente = new ClienteBL().ObtenerByCI(lista[1]);

		cliente.AddPerfil(perfil);

		return perfil;
	}
	
	public Perfil ActualizarPerfil(String cadena) {
		String[] lista = cadena.split(",");
		int id = Integer.parseInt(lista[0]);
		Perfil perfil = this.Obtener(id);

		perfil.setTelefono(lista[1]);

		return perfil;
	}

	private String ListObjectToString(List<Perfil> listPerfil) {
		String result = "";
		listPerfil = listPerfil == null ?  new ArrayList<Perfil>():listPerfil;
		for (Perfil perfil : listPerfil) {
			result = result + ObjectToString(perfil) + "\n";
		}
		return result;
	}

}
