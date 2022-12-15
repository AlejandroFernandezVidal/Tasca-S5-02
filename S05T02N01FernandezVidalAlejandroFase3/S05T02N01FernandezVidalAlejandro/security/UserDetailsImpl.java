package cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.model.domain.JugadorEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor 																																	//Anotacion para inicializar mediante mismo constructor y no requiere @Autowired para la inyeccion de dependencias
public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;
	private final JugadorEntity jugadorEntity;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return Collections.emptyList();
	}

	@Override
	public String getPassword() {

		return jugadorEntity.getPassword();
	}

	@Override
	public String getUsername() {

		return jugadorEntity.getNombre();
	}

	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	@Override
	public boolean isAccountNonLocked() {

		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	public boolean isEnabled() {

		return true;
	}

	public String getNombre() {
		return jugadorEntity.getNombre();
	}

}
