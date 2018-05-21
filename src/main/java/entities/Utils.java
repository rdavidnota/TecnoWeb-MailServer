package entities;

public class Utils {
	public class Estado {
		public static final char Eliminado = 'E';
		public static final char Activo = 'A';

	}

	public class Geocerca {
		public static final char Salir = 'O';
		public static final char Entrar = 'I';
		public static final char Ambos = 'A';
	}

	public class Accion {
		/**Acciones**/
		public static final String Cliente_Crear = "Crear";
		public static final String Cliente_Listar = "Listar";
		public static final String Cliente_Actualizar = "Actualizar";
		public static final String Cliente_Eliminar = "Eliminar";
		public static final String Cliente_Ver = "Ver";
		public static final String Cliente_ObtenerGeocercasAsignadas = "ObtenerGeocercasAsignadas";
		public static final String Cliente_ObtenerGeocercasCreadas = "ObtenerGeocercasCreadas";
		public static final String Cliente_ObtenerHijos = "ObtenerHijos";
		public static final String Cliente_ObtenerPadre = "ObtenerPadre";
		public static final String Cliente_ObtenerPuntos_InteresAsignadas = "ObtenerPuntos_InteresAsignados";
		public static final String Cliente_ObtenerPuntos_InteresCreadas = "ObtenerPuntos_InteresCreados";
		public static final String Cliente_ObtenerByCI = "ObtenerPorCI";
		public static final String Cliente_SetPadre = "SetPadre";
		
		public static final String Geocerca_Crear = "Crear";
		public static final String Geocerca_Listar = "Listar";
		public static final String Geocerca_Actualizar = "Actualizar";
		public static final String Geocerca_Eliminar = "Eliminar";
		public static final String Geocerca_Ver = "Ver";
		
		
		public static final String Perfil_Crear = "Crear";
		public static final String Perfil_Listar = "Listar";
		public static final String Perfil_Actualizar = "Actualizar";
		public static final String Perfil_Eliminar = "Eliminar";
		public static final String Perfil_Ver = "Ver";
		
		public static final String Posicion_Crear = "Crear";
		public static final String Posicion_Listar = "Listar";
		public static final String Posicion_ObtenerUltimaPosicion = "ObtenerUltimaPosicion";
		public static final String Posicion_GoogleMaps = "GoogleMaps";
		
		public static final String Puntos_Interes_Crear = "Crear";
		public static final String Puntos_Interes_Listar = "Listar";
		public static final String Puntos_Interes_Actualizar = "Actualizar";
		public static final String Puntos_Interes_Eliminar = "Eliminar";
		public static final String Puntos_Interes_Ver = "Ver";
		

		/**Modulos**/
		public static final String Cliente = "Cliente";
		public static final String Geocerca = "Geocerca";
		public static final String Perfil = "Perfil";
		public static final String Posicion = "Posicion";
		public static final String Puntos_Interes = "Puntos";
		
		/**Menu de Ayuda**/
		public static final String MenuAyuda = "***********Menu de Ayuda************"+ "\n"
				+ "*************Cliente****************" + "\n"
				+ "1.- Cliente Crear <nombre>,<ci>,<correo>,<telefono>" + "\n"
				+ "2.- Cliente Listar"+ "\n"
				+ "3.- Cliente Actualizar <id>,<nombre>,<ci>,<correo>" + "\n"
				+ "3.1.- Cliente SetPadre <ci padre>,<ci hijo>" + "\n"
				+ "4.- Cliente Eliminar <id>" + "\n"
				+ "5.- Cliente Ver <id>" + "\n"
				+ "6.- Cliente ObtenerGeocercasAsignadas <ci>" + "\n"
				+ "7.- Cliente ObtenerGeocercasCreadas <ci>" + "\n"
				+ "8.- Cliente ObtenerHijos <ci>" + "\n"
				+ "9.- Cliente ObtenerPadre <ci>" + "\n"
				+ "10.- Cliente ObtenerPuntos_InteresAsignados <ci>" + "\n"
				+ "11.- Cliente ObtenerPuntos_InteresCreados <ci>" + "\n"
				+ "12.- Cliente ObtenerPorCI <ci>" + "\n"
				+ "*************Geocerca****************"+ "\n"
				+ "13.- Geocerca Crear <nombre>,<accion>,<ci del creador>,<ci de hijos separados por ;>,<detalle orden;latitud;longitud>" + "\n"
				+ "14.- Geocerca Listar" + "\n"
				+ "15.- Geocerca Actualizar <id>,<nombre>" + "\n"
				+ "16.- Geocerca Eliminar <id>"+ "\n"
				+ "*************Perfil*********"+ "\n" 
				+ "17.- Perfil Crear <telefono>,<ci>"+ "\n"
				+ "18.- Perfil Listar" + "\n"
				+ "19.- Perfil Actualizar <id>,<telefono>" + "\n"
				+ "20.- Perfil Eliminar <id>"+ "\n"
				+ "*************Posicion********"+ "\n" 
				+ "21.- Posicion Crear <ci,<latitud>,<longitud>>"+ "\n"
				+ "22.- Posicion Listar"+ "\n" 
				+ "23.- Posicion Obtener <ci>" + "\n"
				+ "24.- Posicion GoogleMaps <ci>" + "\n"
				+ "***********Puntos de Interes**************"+ "\n" 
				+ "25.- Puntos Crear <nombre>,<latitud>,<longitud>"+ "\n"
				+ "26.- Puntos Listar"+ "\n" 
				+ "27.- Puntos Actualizar <id>,<nombre>,<latitud>,<longitud>,<distancia>"+ "\n" 
				+ "28.- Puntos Eliminar <id>"+ "\n";
		
		
		
		
		
		
		
		
		
		
		
	}
}
