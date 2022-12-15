package cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {

	private static final Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		logger.error("token not found or invalid");
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "unauthorized");

	}

	public String getToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if (header != null && header.startsWith("Bearer "))
			return header.replace("Bearer ", "");
		return null;
	}
}