package cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.model.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TiradaDadosEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private int dado1;
	private int dado2;
	private int resultado;
	private String status;

	public TiradaDadosEntity() {

		this.dado1 = obtenerNumeroAleatorio();
		this.dado2 = obtenerNumeroAleatorio();
		this.resultado = this.dado1 + this.dado2;
		this.status = obtenerStatus(this.resultado);
	}

	private int obtenerNumeroAleatorio() {
		return (int) (Math.random() * 6 + 1);
	}

	private String obtenerStatus(int resultado) {
		return resultado == 7 ? "Ganada" : "Perdida";
	}
}