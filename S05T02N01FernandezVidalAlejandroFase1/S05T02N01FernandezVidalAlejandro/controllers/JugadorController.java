package cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.model.dto.JugadorDTO;
import cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.model.dto.TiradaDadosDTO;
import cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.model.services.JugadorTiradaDadosServiceImpl;

@RestController
public class JugadorController {

	@Autowired
	JugadorTiradaDadosServiceImpl jugadorTiradaDadosServiceImpl;

	@PostMapping("/players")
	public ResponseEntity<?> crearJugador(@RequestBody JugadorDTO jugadorDTO) {

		JugadorDTO jugadorDTOGuardar = jugadorTiradaDadosServiceImpl.añadirJugador(jugadorDTO);
		if (jugadorDTOGuardar != null) {
			return ResponseEntity.status(HttpStatus.OK).body(jugadorDTO);
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("Ya hay un jugador registrado con nombre " + jugadorDTO.getNombre());
		}

	}

	@PutMapping("/players/{id}")
	public ResponseEntity<?> cambiarNombreJugador(@PathVariable int id, @RequestBody JugadorDTO jugadorDTO) {
		ResponseEntity<?> resultado;
		JugadorDTO jugadorDTOBuscar = jugadorTiradaDadosServiceImpl.obtenerJugadorById(id);
		if (jugadorDTOBuscar != null) {
			JugadorDTO jugadorDTOActualizar = jugadorTiradaDadosServiceImpl.actualizarJugador(id, jugadorDTO);
			if (jugadorDTOActualizar != null) {
				resultado = ResponseEntity.status(HttpStatus.OK).body(jugadorDTO);
			} else {

				resultado = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error con el nombre indicado");
			}
		} else {
			resultado = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error con el Id indicado.");
		}
		return resultado;
	}

	@PostMapping("/players/{id}/games/")
	public ResponseEntity<?> realizarTiradaJugador(@PathVariable int id) {
		ResponseEntity<?> resultado;
		TiradaDadosDTO tiradaDadosDTO = jugadorTiradaDadosServiceImpl.realizarTiradaJugador(id);
		if (tiradaDadosDTO != null) {
			resultado = ResponseEntity.status(HttpStatus.OK).body(tiradaDadosDTO);
		} else {
			resultado = ResponseEntity.status(HttpStatus.NOT_FOUND).body("La Id indicada no es correcta.");
		}
		return resultado;
	}

	@DeleteMapping("/players/{id}/games")
	public ResponseEntity<String> eliminarTiradasJugador(@PathVariable int id) {
		ResponseEntity<String> resultado;
		JugadorDTO jugadorDTO = jugadorTiradaDadosServiceImpl.obtenerJugadorById(id);
		if (jugadorDTO != null) {
			boolean condicion = jugadorTiradaDadosServiceImpl.borrarTiradasJugador(id);
			if (condicion) {
				resultado = ResponseEntity.status(HttpStatus.OK)
						.body("Partidas del jugador con Id " + id + " borradas correctamente.");
			} else {
				resultado = ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("La Id indicada no tiene partidas para borrar.");
			}
		} else {
			resultado = ResponseEntity.status(HttpStatus.NOT_FOUND).body("La Id indicada no existe.");
		}
		return resultado;
	}

	@GetMapping("/players/")
	public ResponseEntity<?> obtenerJugadores() {
		ResponseEntity<?> resultado;
		List<JugadorDTO> listadoJugadoresDTO = jugadorTiradaDadosServiceImpl.obtenerJugadores();
		if (listadoJugadoresDTO.size() > 0) {
			resultado = ResponseEntity.status(HttpStatus.OK).body(listadoJugadoresDTO);
		} else {
			resultado = ResponseEntity.status(HttpStatus.NOT_FOUND).body("La lista de jugadores está vacía.");
		}
		return resultado;
	}

	@GetMapping("/players/{id}/games")
	public ResponseEntity<?> obtenerListadoTiradas(@PathVariable int id) {
		ResponseEntity<?> resultado;
		List<TiradaDadosDTO> listado = jugadorTiradaDadosServiceImpl.obtenerTiradasJugador(id);
		if (listado != null) {
			if (listado.size() > 0) {
				resultado = ResponseEntity.status(HttpStatus.OK).body(listado);
			} else {
				resultado = ResponseEntity.status(HttpStatus.NOT_FOUND).body("La lista está vacía.");
			}
		} else {

			resultado = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error con el Id.");
		}

		return resultado;
	}

	@GetMapping("/players/ranking")
	public ResponseEntity<String> obtenerRankingMedioApp() {
		ResponseEntity<String> resultado;
		try {
			resultado = ResponseEntity.status(HttpStatus.OK).body(jugadorTiradaDadosServiceImpl.obtenerRanking());
		} catch (Exception e) {
			resultado = ResponseEntity.status(HttpStatus.NOT_FOUND).body("La lista está vacía.");
		}
		return resultado;
	}

	@GetMapping("/players/ranking/loser")
	public ResponseEntity<?> obtenerRankingPerdedor() {
		ResponseEntity<?> resultado;
		try {
			resultado = ResponseEntity.status(HttpStatus.OK).body(jugadorTiradaDadosServiceImpl.obtenerPerdedor());
		} catch (Exception e) {
			resultado = ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay jugador.");
		}
		return resultado;
	}

	@GetMapping("/players/ranking/winner")
	public ResponseEntity<?> obtenerRankingGanador() {
		ResponseEntity<?> resultado;
		try {
			resultado = ResponseEntity.status(HttpStatus.OK).body(jugadorTiradaDadosServiceImpl.obtenerGanador());
		} catch (Exception e) {
			resultado = ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay jugador.");
		}
		return resultado;
	}
}