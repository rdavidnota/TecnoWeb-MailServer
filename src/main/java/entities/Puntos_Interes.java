package entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Puntos_Interes {
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
	private float latitud;
	@Getter
	@Setter
	private float longitud;
	@Getter
	@Setter
	private float distancia;
	@Getter
	@Setter
	private char estado;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente_id")
	@Getter
	@Setter
	private Cliente originator;

	@ManyToMany(mappedBy = "puntos_interes", fetch = FetchType.LAZY)
	@Getter
	@Setter
	private List<Cliente> clientes;

	public void AddClientes(Cliente cliente) {
		this.clientes.add(cliente);

		boolean sw = false;
		for (Puntos_Interes puntos_Interes : cliente.getPuntos_interes()) {
			if (puntos_Interes.getId() == this.getId()) {
				sw = true;
				break;
			}
		}
		if (!sw) {
			cliente.AddPuntos_Interes(this);
		}
	}
}
