package cat.itacademy.barcelonactiva.fernandezvidal.alejandro.s05.t02.n01.S05T02N01FernandezVidalAlejandro.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {
	
	private final UserDetailsService userDetailsService;
	private final JWTAuthorizationFilter jwtAuthorizationFilter;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {
		
		JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter();
		jwtAuthenticationFilter.setAuthenticationManager(authManager);
		jwtAuthenticationFilter.setFilterProcessesUrl("/login");
		
		return http
				.csrf().disable()
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/players").permitAll()
				.anyRequest()
				.authenticated()
				/*.and()
				.httpBasic()*/
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.addFilter(jwtAuthenticationFilter)
				.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	
	@Bean
	AuthenticationManager authManager(HttpSecurity http) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder())
				.and()
				.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	/*protected void configure(HttpSecurity http) throws Exception {
	http.csrf().disable()
		.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
		.authorizeRequests()
		.antMatchers(HttpMethod.POST, "/players").permitAll()
		.anyRequest().authenticated();
}*/
	
	/*
	@Bean
	UserDetailsService userDetailsService() {
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(User.withUsername("admin")
		.password(passwordEncoder().encode("admin"))
		.roles()
		.build());
		return manager;
	}
	*/
	
	/*public static void main (String[] args) {
		System.out.println("pass : " + new BCryptPasswordEncoder().encode("Pepe"));
	}*/

}