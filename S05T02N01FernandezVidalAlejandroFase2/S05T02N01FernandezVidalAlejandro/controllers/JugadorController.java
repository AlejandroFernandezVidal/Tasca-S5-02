package cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.controllers;

import java.util.List;

import org.bson.types.ObjectId;
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
import cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.model.services.JugadorServiceImpl;

@RestController
public class JugadorController {

	@Autowired
	JugadorServiceImpl jugadorServiceImpl;

	@PostMapping("/players")
	public ResponseEntity<?> crearJugador(@RequestBody JugadorDTO jugadorDTO) {
		ResponseEntity<?> responseEntity;
		JugadorDTO jugadorDTOGuardar = jugadorServiceImpl.añadirJugador(jugadorDTO);
		if (jugadorDTOGuardar != null) {
			responseEntity = ResponseEntity.status(HttpStatus.OK).body(jugadorDTOGuardar);
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Ya hay un usuario con nombre " + jugadorDTO.getNombre());
		}
		return responseEntity;
	}

	@PutMapping("/players/{id}")
	public ResponseEntity<?> cambiarNombreJugador(@PathVariable ObjectId id, @RequestBody JugadorDTO jugadorDTO) {
		ResponseEntity<?> responseEntity;
		boolean jugadorBuscar = jugadorServiceImpl.obtenerJugadorById(id);
		if(jugadorBuscar) {
			JugadorDTO jugadorDTOActualizar = jugadorServiceImpl.actualizarJugador(id, jugadorDTO);
			if (jugadorDTOActualizar != null) {
				responseEntity = ResponseEntity.status(HttpStatus.OK).body(jugadorDTOActualizar);
			} else {
				responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ya hay un jugador con el nombre indicado.");
			}
		}else {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error con ID indicado.");
		}
		return responseEntity;
	}

	@PostMapping("/players/{id}/games/")
	public ResponseEntity<?> realizarTiradaJugador(@PathVariable ObjectId id) {
		ResponseEntity<?> responseEntity;
		TiradaDadosDTO tiradaDadosDTO = jugadorServiceImpl.realizarTiradaJugador(id);
		if (tiradaDadosDTO != null) {
			responseEntity = ResponseEntity.status(HttpStatus.OK).body(tiradaDadosDTO);
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error con ID indicado.");
		}
		return responseEntity;

	}

	@DeleteMapping("/players/{id}/games")
	public ResponseEntity<?> eliminarTiradasJugador(@PathVariable ObjectId id) {
		ResponseEntity<?> responseEntity;
		String respuesta = jugadorServiceImpl.borrarTiradasJugador(id);
		if (respuesta != null) {
			responseEntity = ResponseEntity.status(HttpStatus.OK).body(respuesta);
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error con ID indicado.");
		}
		return responseEntity;
	}

	@GetMapping("/players/")
	public ResponseEntity<?> obtenerJugadores() {
		List<JugadorDTO> listadoJugadoresDTO = jugadorServiceImpl.obtenerJugadores();
		ResponseEntity<?> responseEntity;
		if(listadoJugadoresDTO != null) {
			responseEntity = ResponseEntity.status(HttpStatus.OK).body(listadoJugadoresDTO);
		}else {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body("La lista está vacía.");
		}
		return responseEntity;
	}

	@GetMapping("/players/{id}/games")
	public ResponseEntity<?> obtenerListadoTiradas(@PathVariable ObjectId id) {
		ResponseEntity<?> responseEntity;
		List<TiradaDadosDTO> listadoDTO = jugadorServiceImpl.obtenerTiradasJugador(id);
		if(listadoDTO != null && listadoDTO.size()>0) {
			responseEntity = ResponseEntity.status(HttpStatus.OK).body(listadoDTO);
		}else if (listadoDTO != null && listadoDTO.size() == 0){
			responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body("La lista está vacía.");
		}else {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error con Id indicado.");
		}
		return responseEntity;
	}

	@GetMapping("/players/ranking")
	public String obtenerRankingMedioApp() {
		String respuesta;
		int ranking = jugadorServiceImpl.obtenerRanking();
		if(ranking != -1) {
			respuesta = "El ranking medio de éxito de todos los jugadores es " + ranking + "%";
		}else {
			respuesta = "No hay jugadores registrados para poder obtener un ranking";
		}
		return respuesta;
	}

	@GetMapping("/players/ranking/loser")
	public ResponseEntity<?> obtenerRankingPerdedor() {
		JugadorDTO jugadorLooser = jugadorServiceImpl.obtenerPerdedor();
		ResponseEntity<?> responseEntity;
		if(jugadorLooser != null) {
			responseEntity = ResponseEntity.status(HttpStatus.OK).body(jugadorLooser);
		}else {
		responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay jugadores registrados.");
		}
		return responseEntity;
	}

	@GetMapping("/players/ranking/winner")
	public ResponseEntity<?> obtenerRankingGanador() {
		JugadorDTO jugadorWinner = jugadorServiceImpl.obtenerGanador();
		ResponseEntity<?> responseEntity;
		if(jugadorWinner != null) {
			responseEntity = ResponseEntity.status(HttpStatus.OK).body(jugadorWinner);
		}else {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay jugadores registrados.");
		}
		return responseEntity;
	}
}
