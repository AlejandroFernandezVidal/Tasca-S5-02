package cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.model.services;

import java.util.List;

import cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.model.dto.JugadorDTO;
import cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.model.dto.TiradaDadosDTO;

public interface IJugadorTiradaDadosService {

	public JugadorDTO añadirJugador(JugadorDTO jugadorDTO);

	public JugadorDTO actualizarJugador(int id, JugadorDTO jugadorDTO);

	public TiradaDadosDTO realizarTiradaJugador(int id);

	public boolean borrarTiradasJugador(int id);

	public List<JugadorDTO> obtenerJugadores();

	public List<TiradaDadosDTO> obtenerTiradasJugador(int id);

	public String obtenerRanking();

	public JugadorDTO obtenerPerdedor();

	public JugadorDTO obtenerGanador();

}