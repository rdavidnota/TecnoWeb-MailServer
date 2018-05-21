package entities;

import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	private int id;

	@Getter
	@Setter
	private String nombre;
	@Getter
	@Setter
	private String correo;
	@Getter
	@Setter
	private String ci;

	@Getter
	@Setter
	private char estado;

	@ManyToMany
	@Getter
	@Setter
	private List<Geocerca> geocercas;

	@OneToMany(mappedBy = "originator")
	@Getter
	@Setter
	private List<Geocerca> geocercas_originator;

	@OneToMany(mappedBy = "originator")
	@Getter
	@Setter
	private List<Puntos_Interes> puntos_interes_originator;

	@OneToMany(mappedBy = "cliente")
	@Getter
	@Setter
	private List<Posicion> posiciones;

	@ManyToMany
	@Getter
	@Setter
	private List<Puntos_Interes> puntos_interes;

	@OneToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "perfil_id")
	@Getter
	@Setter
	private Perfil perfil;

	@OneToMany(mappedBy = "padre")
	@Getter
	@Setter
	private List<Cliente> Hijos;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "padre_id")
	@Getter
	@Setter
	private Cliente padre;

	public void AddPerfil(Perfil perfil) {
		perfil.setCliente(this);
		this.setPerfil(perfil);
	}

	public void AddGeocercaOriginator(Geocerca geocerca) {
		geocerca.setOriginator(this);
		this.geocercas_originator.add(geocerca);
	}

	public void AddGeocerca(Geocerca geocerca) {
		this.geocercas.add(geocerca);

		boolean sw = false;
		for (Cliente cliente : geocerca.getClientes()) {
			if (cliente.getId() == this.getId()) {
				sw = true;
				break;
			}
		}

		if (!sw) {
			geocerca.AddClientes(this);
		}

	}

	public void AddPuntos_Interes_Originator(Puntos_Interes puntos_Interes) {
		puntos_Interes.setOriginator(this);
		this.puntos_interes_originator.add(puntos_Interes);
	}

	public void AddPuntos_Interes(Puntos_Interes puntos_Interes) {
		this.getPuntos_interes().add(puntos_Interes);
		boolean sw = false;
		for (Cliente cliente : puntos_Interes.getClientes()) {
			if (cliente.getId() == this.getId()) {
				sw = true;
				break;
			}
		}

		if (!sw) {
			puntos_Interes.AddClientes(this);
		}
	}

	public void AddPosicion(Posicion posicion) {
		posicion.setCliente(this);
		this.posiciones.add(posicion);
	}

}
