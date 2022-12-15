package cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.model.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JugadorDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nombre;
	private LocalDate localDate;
	private int porcentajeExito;

	public JugadorDTO() {
		this.nombre = "ANÒNIM";
	}

	public JugadorDTO(String nombre) {

		this.nombre = nombre;

	}
}