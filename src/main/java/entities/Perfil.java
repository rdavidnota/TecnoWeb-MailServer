package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Perfil {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	private int id;
	
	@Getter
	@Setter
	private char estado;
	
	@Getter
	@Setter
	private String telefono;

	@OneToOne(mappedBy = "perfil")
	@Getter
	@Setter
	private Cliente cliente;
}
