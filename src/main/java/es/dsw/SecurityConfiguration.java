package es.dsw;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import es.dsw.models.Usuario;
import es.dsw.services.RolService;
import es.dsw.services.UsuarioService;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	@Autowired 
	private UsuarioService servicioUsuarios;
	@Autowired 
	private RolService servicioRoles;
	public static InMemoryUserDetailsManager inMemory = new InMemoryUserDetailsManager();
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http
		.authorizeHttpRequests((authorize) -> authorize
				.requestMatchers("/js/**").permitAll()
				.requestMatchers("/styles/**").permitAll()
				.requestMatchers("/img/**").permitAll()
				.requestMatchers("/bootstrap/**").permitAll()
				.requestMatchers("/register").permitAll()
				.requestMatchers("/addUser").permitAll()
				.anyRequest().authenticated()
				)
		.httpBasic(withDefaults())
		.formLogin(form -> form
				.loginPage("/login")
				.defaultSuccessUrl("/index", true)
				.loginProcessingUrl("/loginprocess")
				.permitAll()
				)
		.logout((logout) -> logout.logoutSuccessUrl("/login").permitAll())
		;
		
		return http.build();
		
	}
	
	@SuppressWarnings("deprecation")
	@Bean
	InMemoryUserDetailsManager UserDetailsService() {
		for (Usuario usuario: servicioUsuarios.GetAll()) {
			String rol = servicioRoles.getRoleById(usuario.getId_rol());
			UserDetails user = User.withDefaultPasswordEncoder().username(usuario.getNombreUsuario()).password(usuario.getContra()).roles(rol).build();
			inMemory.createUser(user);
		}
		
		return inMemory;
	}
}
