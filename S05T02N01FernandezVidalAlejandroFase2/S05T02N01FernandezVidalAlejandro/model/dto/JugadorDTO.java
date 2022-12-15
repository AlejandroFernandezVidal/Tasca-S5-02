package cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.model.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.model.domain.TiradaDadosEntity;
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
	private List<TiradaDadosEntity> listadoTiradasDados;

	public JugadorDTO() {
		this.nombre = "ANÒNIM";
	}

	public JugadorDTO(String nombre) {

		this.nombre = nombre;
		this.listadoTiradasDados = new ArrayList<TiradaDadosEntity>();

	}
}