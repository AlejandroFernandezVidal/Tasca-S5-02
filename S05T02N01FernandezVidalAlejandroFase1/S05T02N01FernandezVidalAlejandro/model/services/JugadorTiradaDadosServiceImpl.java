package cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.model.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.model.domain.JugadorEntity;
import cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.model.domain.TiradaDadosEntity;
import cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.model.dto.JugadorDTO;
import cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.model.dto.TiradaDadosDTO;
import cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.model.repository.JugadorRepository;
import cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.model.repository.TiradaDadosRepository;

@Service
public class JugadorTiradaDadosServiceImpl implements IJugadorTiradaDadosService {

	@Autowired
	JugadorRepository jugadorRepository;
	@Autowired
	TiradaDadosRepository tiradaDadosRepository;

	@Override
	@Transactional
	public JugadorDTO añadirJugador(JugadorDTO jugadorDTO) {
		JugadorEntity jugadorEntity = new JugadorEntity();
		JugadorDTO resultado = null;

		if (jugadorDTO.getNombre().equalsIgnoreCase("ANÒNIM") || !jugadorRepository.existsByNombre(jugadorDTO.getNombre())) {
			jugadorEntity.setNombre(jugadorDTO.getNombre());
			jugadorEntity.setDataRegistro(LocalDate.now());
			jugadorRepository.save(jugadorEntity);
			jugadorDTO.setLocalDate(jugadorEntity.getDataRegistro());
			resultado = jugadorDTO;
		}

		return resultado;
	}

	@Override
	@Transactional
	public JugadorDTO actualizarJugador(int id, JugadorDTO jugadorDTO) {
		JugadorDTO resultado = null;
		JugadorEntity jugadorEntity = jugadorRepository.findById(id);
		boolean jugadorEntityNombre = jugadorRepository.existsByNombre(jugadorDTO.getNombre());
		if (jugadorEntity != null && (jugadorDTO.getNombre().equalsIgnoreCase("ANÒNIM") || !jugadorEntityNombre)) {
			jugadorEntity.setNombre(jugadorDTO.getNombre());
			jugadorDTO.setLocalDate(jugadorEntity.getDataRegistro());
			jugadorRepository.save(jugadorEntity);
			resultado = jugadorDTO;
		}
		return resultado;
	}

	@Override
	@Transactional
	public TiradaDadosDTO realizarTiradaJugador(int id) {
		TiradaDadosDTO resultado = null;
		if (jugadorRepository.existsById(id)) {
			JugadorEntity jugadorEntity = jugadorRepository.findById(id);
			TiradaDadosEntity tiradaDadosEntity = new TiradaDadosEntity(jugadorEntity);
			TiradaDadosDTO tiradaDadosDTO = new TiradaDadosDTO();
			tiradaDadosRepository.save(tiradaDadosEntity);
			tiradaDadosDTO.setDado1(tiradaDadosEntity.getDado1());
			tiradaDadosDTO.setDado2(tiradaDadosEntity.getDado2());
			tiradaDadosDTO.setResultado(tiradaDadosEntity.getResultado());
			tiradaDadosDTO.setStatus(tiradaDadosEntity.getStatus());

			resultado = tiradaDadosDTO;
		}
		return resultado;
	}

	@Override
	@Transactional
	public boolean borrarTiradasJugador(int id) {
		List<TiradaDadosEntity> listadoTiradaDadosEntity = tiradaDadosRepository.findAll();
		boolean condicion = false;
		for (TiradaDadosEntity tiradaDadosEntity : listadoTiradaDadosEntity) {
			if (tiradaDadosEntity.getJugadorEntity().getId() == id) {
				tiradaDadosRepository.delete(tiradaDadosEntity);
				condicion = true;
			}
		}
		return condicion;
	}

	@Override
	@Transactional(readOnly = true)
	public List<JugadorDTO> obtenerJugadores() {
		List<JugadorEntity> listadoJugadoresEntity = jugadorRepository.findAll();
		List<JugadorDTO> listadoJugadoresDTO = new ArrayList<>();
		for (JugadorEntity jugadorEntity : listadoJugadoresEntity) {
			JugadorDTO jugadorDTO = new JugadorDTO();
			jugadorDTO.setNombre(jugadorEntity.getNombre());
			jugadorDTO.setLocalDate(jugadorEntity.getDataRegistro());
			List<TiradaDadosDTO> listado = obtenerTiradasJugador(jugadorEntity.getId());
			Double cantidadWin = 0.00;
			for (TiradaDadosDTO tiradaDados : listado) {
				if (tiradaDados.getStatus().equalsIgnoreCase("Ganada")) {
					cantidadWin++;
				}
			}
			int total = (int) (cantidadWin * 100 / listado.size());
			jugadorDTO.setPorcentajeExito(total);
			listadoJugadoresDTO.add(jugadorDTO);
		}
		return listadoJugadoresDTO;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TiradaDadosDTO> obtenerTiradasJugador(int id) {
		List<TiradaDadosDTO> listadoJugador = new ArrayList<>();
		if (jugadorRepository.existsById(id)) {
			List<TiradaDadosEntity> listadoTiradaDados = tiradaDadosRepository.findAll();

			for (TiradaDadosEntity tiradaDadosEntity : listadoTiradaDados) {
				if (tiradaDadosEntity.getJugadorEntity().getId() == id) {
					TiradaDadosDTO tiradaDadosDTO = new TiradaDadosDTO();
					tiradaDadosDTO.setDado1(tiradaDadosEntity.getDado1());
					tiradaDadosDTO.setDado2(tiradaDadosEntity.getDado2());
					tiradaDadosDTO.setResultado(tiradaDadosEntity.getResultado());
					tiradaDadosDTO.setStatus(tiradaDadosEntity.getStatus());
					listadoJugador.add(tiradaDadosDTO);
				}
			}
		} else {
			listadoJugador = null;
		}
		return listadoJugador;
	}

	@Override
	@Transactional(readOnly = true)
	public String obtenerRanking() {
		int cantidadWin = 0;
		int resultado;
		List<TiradaDadosEntity> listadoTotalTiradas = tiradaDadosRepository.findAll();
		for (TiradaDadosEntity tiradaDadosEntity : listadoTotalTiradas) {
			if (tiradaDadosEntity.getStatus().equalsIgnoreCase("Ganada")) {
				cantidadWin++;
			}
		}
		resultado = cantidadWin * 100 / listadoTotalTiradas.size();

		return "El porcentaje medio de éxito de todos los jugadores es " + resultado + "%";
	}

	@Override
	@Transactional(readOnly = true)
	public JugadorDTO obtenerPerdedor() {
		List<JugadorDTO> listadoJugadores = obtenerJugadores();
		JugadorDTO jugadorLooser;
		Collections.sort(listadoJugadores, new Comparator<JugadorDTO>() {

			@Override
			public int compare(JugadorDTO o1, JugadorDTO o2) {
				int resultado;
				if (o1.getPorcentajeExito() > o2.getPorcentajeExito()) {
					resultado = 1;
				} else {
					resultado = -1;
				}
				return resultado;
			}
		});
		jugadorLooser = listadoJugadores.get(0);
		return jugadorLooser;
	}

	@Override
	@Transactional(readOnly = true)
	public JugadorDTO obtenerGanador() {

		List<JugadorDTO> listadoJugadores = obtenerJugadores();
		JugadorDTO jugadorWinner;
		Collections.sort(listadoJugadores, new Comparator<JugadorDTO>() {

			@Override
			public int compare(JugadorDTO o1, JugadorDTO o2) {
				int resultado;
				if (o1.getPorcentajeExito() < o2.getPorcentajeExito()) {
					resultado = 1;
				} else {
					resultado = -1;
				}
				return resultado;
			}
		});
		jugadorWinner = listadoJugadores.get(0);
		return jugadorWinner;
	}

	public JugadorDTO obtenerJugadorById(int id) {
		JugadorEntity jugadorEntity = jugadorRepository.findById(id);
		JugadorDTO jugadorDTO = new JugadorDTO();
		if (jugadorEntity != null) {
			jugadorDTO.setNombre(jugadorEntity.getNombre());
			jugadorDTO.setLocalDate(jugadorEntity.getDataRegistro());
		} else {
			jugadorDTO = null;
		}
		return jugadorDTO;
	}
}