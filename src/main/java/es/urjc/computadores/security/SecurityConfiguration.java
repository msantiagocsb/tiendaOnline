package es.urjc.computadores.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	public UserRepositoryAuthProvider authenticationProvider;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
		
		//Public pages
		http.authorizeRequests().antMatchers("/registrar").permitAll();
		http.authorizeRequests().antMatchers("/login").permitAll();
		http.authorizeRequests().antMatchers("/loginerror").permitAll();
		http.authorizeRequests().antMatchers("/").permitAll();
		http.authorizeRequests().antMatchers("/usuario/{id}").permitAll();
		http.authorizeRequests().antMatchers("/producto/{num}").permitAll();
		http.authorizeRequests().antMatchers("/inputuser").permitAll();
		
		
		
		//Private pages
		http.authorizeRequests().antMatchers("/listausuarios").hasAnyRole("ADMIN");
		http.authorizeRequests().antMatchers("/comprarproducto/inputpedido").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/comprarproducto").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/gestionenvios").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/subirproducto").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/modificarproducto").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/comprarproducto/{num}").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/chats/{chatid}/inputmensaje").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/inputchat").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/{userid}/chats").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/chats/{id}").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/comprarproducto/inputpedido").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/inputpedido").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/chats/inputmensaje").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/eliminarproducto/{productoid}").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/generarfactura").hasAnyRole("USER");
		
		
		http.authorizeRequests().anyRequest().authenticated();
		
		
		//login form
		http.formLogin().loginPage("/login");
		http.formLogin().usernameParameter("username");
		http.formLogin().passwordParameter("password");
		http.formLogin().defaultSuccessUrl("/");//TODO: hay que ver a donde enviamos al usuairo cuando intenta acceder a partes privadas
		http.formLogin().failureForwardUrl("/loginerror");//hay que cambiar esto para que salga mensaje de error
		
		//logout
		
		http.logout().logoutUrl("/logout");
		http.logout().logoutSuccessUrl("/");
		
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	// User
		auth.authenticationProvider(authenticationProvider);
	}
}
