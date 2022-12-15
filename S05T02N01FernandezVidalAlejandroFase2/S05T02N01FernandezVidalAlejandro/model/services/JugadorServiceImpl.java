package cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.model.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.model.domain.JugadorEntity;
import cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.model.domain.TiradaDadosEntity;
import cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.model.dto.JugadorDTO;
import cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.model.dto.TiradaDadosDTO;
import cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.model.repository.IJugadorRepository;

@Service
public class JugadorServiceImpl implements IJugadorService {

	@Autowired
	IJugadorRepository jugadorRepository;

	@Override
	public JugadorDTO añadirJugador(JugadorDTO jugadorDTO) {
		JugadorDTO resultado = null;

		if (jugadorDTO.getNombre().equals("ANÒNIM") || !jugadorRepository.existsByNombre(jugadorDTO.getNombre())) {

			JugadorEntity jugadorEntity = new JugadorEntity();
			jugadorEntity.setNombre(jugadorDTO.getNombre());
			jugadorEntity.setLocalDate(LocalDate.now());
			jugadorRepository.save(jugadorEntity);
			jugadorDTO.setLocalDate(jugadorEntity.getLocalDate());
			resultado = jugadorDTO;
		}

		return resultado;
	}

	@Override
	public JugadorDTO actualizarJugador(ObjectId id, JugadorDTO jugadorDTO) {
		JugadorDTO jugadorDTOReturn = null;
		Optional<JugadorEntity> jugadorEntityActualizar = jugadorRepository.findById(id);
		boolean jugadorEntityNombre = jugadorRepository.existsByNombre(jugadorDTO.getNombre());
		if (jugadorEntityActualizar.isPresent()
				&& (jugadorDTO.getNombre().equalsIgnoreCase("ANÒNIM") || !jugadorEntityNombre)) {
			jugadorEntityActualizar.get().setNombre(jugadorDTO.getNombre());
			jugadorDTO.setLocalDate(jugadorEntityActualizar.get().getLocalDate());
			jugadorDTO.setListadoTiradasDados(jugadorEntityActualizar.get().getTiradaDados());
			jugadorDTO.setPorcentajeExito(obtenerPorcentajeExito(jugadorEntityActualizar.get(), jugadorDTO));
			jugadorRepository.save(jugadorEntityActualizar.get());
			jugadorDTOReturn = jugadorDTO;
		}
		return jugadorDTOReturn;
	}

	@Override
	public TiradaDadosDTO realizarTiradaJugador(ObjectId id) {
		Optional<JugadorEntity> jugadorTiradaDados = jugadorRepository.findById(id);
		TiradaDadosDTO tiradaDadosDTO = new TiradaDadosDTO();
		if (jugadorTiradaDados.isPresent()) {
			TiradaDadosEntity tiradaDadosEntity = new TiradaDadosEntity();
			jugadorTiradaDados.get().getTiradaDados().add(tiradaDadosEntity);
			jugadorRepository.save(jugadorTiradaDados.get());
			tiradaDadosDTO.setDado1(tiradaDadosEntity.getDado1());
			tiradaDadosDTO.setDado2(tiradaDadosEntity.getDado2());
			tiradaDadosDTO.setResultado(tiradaDadosEntity.getResultado());
			tiradaDadosDTO.setStatus(tiradaDadosEntity.getStatus());
		} else {
			tiradaDadosDTO = null;
		}
		return tiradaDadosDTO;
	}

	@Override
	public String borrarTiradasJugador(ObjectId id) {
		String resultado = null;
		Optional<JugadorEntity> jugadorEliminarTiradas = jugadorRepository.findById(id);

		if (jugadorEliminarTiradas.isPresent()) {
			if (!jugadorEliminarTiradas.get().getTiradaDados().isEmpty()) {
				jugadorEliminarTiradas.get().setTiradaDados(new ArrayList<>());
				jugadorRepository.save(jugadorEliminarTiradas.get());
				resultado = "Partidas eliminadas correctamente para el usuario llamado "
						+ jugadorEliminarTiradas.get().getNombre();
			} else {
				resultado = "No hay registros de partidas para el usuario " + jugadorEliminarTiradas.get().getNombre();
			}
		}

		return resultado;
	}

	@Override
	public List<JugadorDTO> obtenerJugadores() {
		List<JugadorEntity> listadoJugadoresEntity = jugadorRepository.findAll();
		List<JugadorDTO> listadoJugadoresDTO = new ArrayList<>();
		if (!listadoJugadoresEntity.isEmpty()) {
			for (JugadorEntity jugadorEntity : listadoJugadoresEntity) {
				JugadorDTO jugadorDTO = new JugadorDTO();
				jugadorDTO.setNombre(jugadorEntity.getNombre());
				jugadorDTO.setLocalDate(jugadorEntity.getLocalDate());
				jugadorDTO.setListadoTiradasDados(jugadorEntity.getTiradaDados());
				jugadorDTO.setPorcentajeExito(obtenerPorcentajeExito(jugadorEntity, jugadorDTO));
				listadoJugadoresDTO.add(jugadorDTO);
			}
		} else {
			listadoJugadoresDTO = null;
		}
		return listadoJugadoresDTO;
	}

	@Override
	public List<TiradaDadosDTO> obtenerTiradasJugador(ObjectId id) {
		Optional<JugadorEntity> jugadorEntityBuscar = jugadorRepository.findById(id);
		List<TiradaDadosDTO> listadoDTO = new ArrayList<>();
		if (jugadorEntityBuscar.isPresent()) {
			List<TiradaDadosEntity> listadoEntity = jugadorEntityBuscar.get().getTiradaDados();
			for (TiradaDadosEntity tiradaDadosEntity : listadoEntity) {
				TiradaDadosDTO tiradaDadosDTO = new TiradaDadosDTO();
				tiradaDadosDTO.setDado1(tiradaDadosEntity.getDado1());
				tiradaDadosDTO.setDado2(tiradaDadosEntity.getDado2());
				tiradaDadosDTO.setResultado(tiradaDadosEntity.getResultado());
				tiradaDadosDTO.setStatus(tiradaDadosEntity.getStatus());
				listadoDTO.add(tiradaDadosDTO);
			}
		} else {
			listadoDTO = null;
		}
		return listadoDTO;
	}

	@Override
	public int obtenerRanking() {
		List<JugadorEntity> listadoJugadorEntity = jugadorRepository.findAll();
		int ranking = -1;
		int totalPorcentaje = 0;

		// Porcentaje medio partidas ganadas entre total partidas
		/*
		 * if(!listadoJugadorEntity.isEmpty()) { int numExito = 0; int totalPartidas =
		 * 0; for(JugadorEntity jugadorEntity : listadoJugadorEntity) {
		 * List<TiradaDadosEntity> listadoTiradaDados = jugadorEntity.getTiradaDados();
		 * for(TiradaDadosEntity tiradaDadosEntity : listadoTiradaDados) {
		 * if(tiradaDadosEntity.getStatus().equalsIgnoreCase("Ganada")) { numExito ++; }
		 * totalPartidas++; } } ranking = numExito*100/totalPartidas; }
		 */

		// Porcentaje medio jugadores por porcentaje de éxito
		if (listadoJugadorEntity != null) {
			boolean condicion = false;
			for (JugadorEntity jugadorEntity : listadoJugadorEntity) {
				JugadorDTO jugadorDTO = new JugadorDTO();
				jugadorDTO.setListadoTiradasDados(jugadorEntity.getTiradaDados());
				jugadorDTO.setPorcentajeExito(obtenerPorcentajeExito(jugadorEntity, jugadorDTO));
				totalPorcentaje += jugadorDTO.getPorcentajeExito();
				condicion = true;
			}
			if (condicion) {
				ranking = totalPorcentaje / listadoJugadorEntity.size();
			} else {
				ranking = 0;
			}
		}
		return ranking;
	}

	@Override
	public JugadorDTO obtenerPerdedor() {
		List<JugadorDTO> listadoJugadorDTO = obtenerJugadores();
		JugadorDTO jugadorLooser = null;
		if (listadoJugadorDTO != null) {
			Collections.sort(listadoJugadorDTO, new Comparator<JugadorDTO>() {

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
			jugadorLooser = listadoJugadorDTO.get(0);
		}
		return jugadorLooser;
	}

	@Override
	public JugadorDTO obtenerGanador() {
		List<JugadorDTO> listadoJugadorDTO = obtenerJugadores();
		JugadorDTO jugadorWinner = null;
		if (listadoJugadorDTO != null) {
			Collections.sort(listadoJugadorDTO, new Comparator<JugadorDTO>() {

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
			jugadorWinner = listadoJugadorDTO.get(0);
		}
		return jugadorWinner;

	}

	public int obtenerPorcentajeExito(JugadorEntity jugadorEntity, JugadorDTO jugadorDTO) {
		int porcentajeExito = 0;
		if (!jugadorDTO.getListadoTiradasDados().isEmpty()) {
			int numExito = 0;
			int divisor = jugadorEntity.getTiradaDados().size();
			for (TiradaDadosEntity tiradaDadosEntity : jugadorEntity.getTiradaDados()) {
				if (tiradaDadosEntity.getStatus().equalsIgnoreCase("Ganada")) {
					numExito++;
				}
			}
			porcentajeExito = numExito * 100 / divisor;
			jugadorDTO.setPorcentajeExito(porcentajeExito);
		}
		return porcentajeExito;
	}

	public boolean obtenerJugadorById(ObjectId Id) {
		return jugadorRepository.existsById(Id);
	}
}