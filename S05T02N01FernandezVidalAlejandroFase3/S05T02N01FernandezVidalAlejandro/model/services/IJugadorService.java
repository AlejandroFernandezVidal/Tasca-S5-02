package cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.model.services;

import java.util.List;

import org.bson.types.ObjectId;

import cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.model.dto.JugadorDTO;
import cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.model.dto.TiradaDadosDTO;
//import cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.security.AuthCredentials;

public interface IJugadorService {

	public JugadorDTO añadirJugador(JugadorDTO jugadorDTO/*AuthCredentials authCredentials*/);

	public JugadorDTO actualizarJugador(ObjectId id, JugadorDTO jugadorDTO);

	public TiradaDadosDTO realizarTiradaJugador(ObjectId id);

	public String borrarTiradasJugador(ObjectId id);

	public List<JugadorDTO> obtenerJugadores();

	public List<TiradaDadosDTO> obtenerTiradasJugador(ObjectId id);

	public int obtenerRanking();

	public JugadorDTO obtenerPerdedor();

	public JugadorDTO obtenerGanador();

}
