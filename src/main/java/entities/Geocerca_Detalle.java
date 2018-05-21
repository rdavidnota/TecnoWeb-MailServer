package entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Geocerca_Detalle {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	private int id;

	@Getter
	@Setter
	private int orden;
	@Getter
	@Setter
	private float latitud;
	@Getter
	@Setter
	private float longitud;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "geocerca_id")
	@Getter
	@Setter
	private Geocerca geocerca;
}
