package cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.model.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TiradaDadosDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int dado1;
	private int dado2;
	private int resultado;
	private String status;

	public TiradaDadosDTO() {

	}

	public TiradaDadosDTO(int dado1, int dado2, int resultado, String status) {

		this.dado1 = dado1;
		this.dado2 = dado2;
		this.resultado = resultado;
		this.status = status;
	}

}
