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
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Geocerca {
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
	private char action;
	@Getter
	@Setter
	private char estado;

	@OneToMany(mappedBy = "geocerca", fetch = FetchType.LAZY)
	@Getter
	@Setter
	private List<Geocerca_Detalle> detalle;

	public void AddDetalle(Geocerca_Detalle detalle) {
		detalle.setGeocerca(this);
		this.detalle.add(detalle);
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente_id")
	@Getter
	@Setter
	private Cliente originator;

	@ManyToMany(mappedBy = "geocercas", fetch = FetchType.LAZY)
	@Getter
	@Setter
	private List<Cliente> clientes;

	public void AddClientes(Cliente cliente) {
		this.clientes.add(cliente);

		boolean sw = false;
		for (Geocerca geocerca : cliente.getGeocercas()) {
			if (geocerca.getId() == this.getId()) {
				sw = true;
				break;
			}
		}
		if (!sw) {
			cliente.AddGeocerca(this);
		}
	}
}
