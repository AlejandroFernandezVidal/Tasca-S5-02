package cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.model.domain.JugadorEntity;
import cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.model.repository.IJugadorRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private IJugadorRepository jugadorRepository;

	@Override
	public UserDetails loadUserByUsername(String nombre) throws UsernameNotFoundException {
		JugadorEntity jugadorEntity = jugadorRepository.findOneByNombre(nombre)
				.orElseThrow(() -> new UsernameNotFoundException("El jugador con nombre " + nombre + " no existe."));

		return new UserDetailsImpl(jugadorEntity);
	}
}