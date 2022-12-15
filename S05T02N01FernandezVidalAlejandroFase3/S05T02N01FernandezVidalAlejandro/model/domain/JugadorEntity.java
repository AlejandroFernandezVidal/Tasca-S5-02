package cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.model.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "Jugador")
@Getter
@Setter
@ToString
public class JugadorEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private ObjectId _id;
	private String nombre;
	private String password;
	private LocalDate localDate;
	private List<TiradaDadosEntity> tiradaDados;

	public JugadorEntity() {
		this.tiradaDados = new ArrayList<>();
	}

	public JugadorEntity(String nombre, String password, LocalDate localDate) {

		this.nombre = nombre;
		this.password = password;
		this.localDate = localDate;
		this.tiradaDados = new ArrayList<>();

	}
}