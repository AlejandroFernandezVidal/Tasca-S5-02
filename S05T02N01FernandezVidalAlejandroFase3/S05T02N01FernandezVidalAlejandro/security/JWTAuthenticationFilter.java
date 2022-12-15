package cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.security;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

//import cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.model.domain.JugadorEntity;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		// JugadorEntity jugadorEntity = new JugadorEntity();
		AuthCredentials authCredentials = new AuthCredentials();
		try {
			/* jugadorEntity */authCredentials = new ObjectMapper().readValue(request.getReader(),
					/* JugadorEntity.class */AuthCredentials.class);
		} catch (IOException e) {
		}

		UsernamePasswordAuthenticationToken usernamePAT = new UsernamePasswordAuthenticationToken(
				/* jugadorEntity.getNombre() */authCredentials.getNombre(),
				/* jugadorEntity.getPassword() */authCredentials.getPassword(), Collections.emptyList());

		return getAuthenticationManager().authenticate(usernamePAT);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
		String token = TokenUtils.createToken(userDetails.getUsername(), userDetails.getPassword());

		response.addHeader("Authorization", "Bearer " + token);
		response.getWriter().flush();

		super.successfulAuthentication(request, response, chain, authResult);
	}
}