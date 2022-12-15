package cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.model.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "jugadores")
@Getter
@Setter
@ToString
public class JugadorEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)																									//inremento a cargo de la base de datos
	@Column
	private int id;

	@Column
	private String nombre;

	@Column
	private LocalDate dataRegistro;

	public JugadorEntity() {

	}

	public JugadorEntity(String nombre, LocalDate dataRegistro) {

		this.nombre = nombre;
		this.dataRegistro = dataRegistro;

	}
}