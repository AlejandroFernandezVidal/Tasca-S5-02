package cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.security;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenUtils {
	private final static String ACCES_TOKEN_SECRET = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";
	private final static Long ACCES_TOKEN_VALIDITY_SECONDS = 3600L;

	public static String createToken(String nombre, String password) {
		long expirationTime = ACCES_TOKEN_VALIDITY_SECONDS * 100;
		Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

		Map<String, Object> extra = new HashMap<>();
		extra.put("password", password);// añadimos password encriptadp, pero se podría añadir más información o nada

		return Jwts.builder().setSubject(nombre).setExpiration(expirationDate).addClaims(extra)
				.signWith(Keys.hmacShaKeyFor(ACCES_TOKEN_SECRET.getBytes())).compact();

	}

	public static UsernamePasswordAuthenticationToken getAuthentication(String token) {
		try {
			Claims claims = Jwts.parserBuilder().setSigningKey(ACCES_TOKEN_SECRET.getBytes()).build()
					.parseClaimsJws(token).getBody();

			String nombre = claims.getSubject();

			return new UsernamePasswordAuthenticationToken(nombre, null, Collections.emptyList());
		} catch (JwtException e) {
			return null;
		}
	}

	public static String getUsernameFromToken(String token) {

		return Jwts.parserBuilder().setSigningKey(ACCES_TOKEN_SECRET.getBytes()).build().parseClaimsJws(token).getBody()
				.getSubject();
	}
}