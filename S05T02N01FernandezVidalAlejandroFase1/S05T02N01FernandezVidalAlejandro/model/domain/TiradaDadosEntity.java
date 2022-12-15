package cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.model.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tiradas")
@Getter
@Setter
@ToString
public class TiradaDadosEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) 																									//permite que el proveedor de persistencia elegir la estrategia de generación
	@Column
	private int id;

	@Column
	private int dado1;

	@Column
	private int dado2;

	@Column
	private int resultado;

	@Column
	private String status;

	@ManyToOne(fetch = FetchType.LAZY) 																													//@ManyToOne mapear una entidad con otra y fectch tipo Lazy para definir modo de carga (solo leída por 1a vez)
	@JoinColumn(name = "jugador_id")
	private JugadorEntity jugadorEntity;

	public TiradaDadosEntity() {

	}

	public TiradaDadosEntity(JugadorEntity jugadorEntity) {

		this.dado1 = obtenerNumeroAleatorio();
		this.dado2 = obtenerNumeroAleatorio();
		this.resultado = this.dado1 + this.dado2;
		this.status = obtenerStatus(this.resultado);
		this.jugadorEntity = jugadorEntity;

	}

	private int obtenerNumeroAleatorio() {
		return (int) (Math.random() * 6 + 1);
	}

	private String obtenerStatus(int resultado) {
		String status = "Perdida";
		if (resultado == 7) {
			status = "Ganada";
		}

		return status;
	}
}