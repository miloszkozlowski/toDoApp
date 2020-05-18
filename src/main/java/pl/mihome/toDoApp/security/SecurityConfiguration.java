package pl.mihome.toDoApp.security;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@KeycloakConfiguration
public class SecurityConfiguration extends KeycloakWebSecurityConfigurerAdapter {
	
	/*
	 * Bean, który musi być dodany, bo tak jest opisane w dokumentacji, a potrzebne jest,
	 * żeby działało to razem ze Spring Bootem
	 */
	@Bean
	KeycloakSpringBootConfigResolver keycloakConfigResolver() {
		return new KeycloakSpringBootConfigResolver();
	}
	
	
	/*
	 * Ta metoda jest potrzebna do swobodnego zarządzania rolami. Role użytkowników w Spring
	 * określane są zawsze wielkimi literami i zaczynają się prefiksem {@code ROLE_}.
	 * 
	 * {@code SimpleAuthorityMapper} jest potrzebny, żeby móc korzystać z ról, których używa
	 * Keycloak. Role z Keycloak zamienia na wielkie litery i dodaje prefiks.
	 * 
	 * {@code KeycloakAuthenticationProvider} rozszerza Springowe {@code AuthenticationProvider}
	 * i zawuera informacje, co dostarcza dane do autentykacji - w tym wypadku Keycloak.
	 * 
	 * Do providera jest przypisany jeszcze maper {@code SimpleAuthorityMapper}, który zmienia
	 * w locie nazwy roli.  
	 */
	@Autowired
	void globalConfigurer(AuthenticationManagerBuilder auth) {
		var authorityMapper = new SimpleAuthorityMapper();
		authorityMapper.setPrefix("ROLE_");
		authorityMapper.setConvertToUpperCase(true);
		
		KeycloakAuthenticationProvider authProvider = keycloakAuthenticationProvider();
		authProvider.setGrantedAuthoritiesMapper(authorityMapper);
		auth.authenticationProvider(authProvider);
		
	}

	/*
	 * Tu jest okreslone, czy sesje użytkowników mają być zapamiętywane i gdzie zapamiętywane.
	 * Zamiast można uzyć {@code NullAuthenticatedSessionStrategy} - wtedy sesje nie są 
	 * rejestrowane.
	 */
	@Override
	protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
		return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);
		http.authorizeRequests()
			.antMatchers("/info/*")
			.hasRole("USER")
			.antMatchers("/projekt/*")
			.hasRole("ADMIN")
			.anyRequest()
			.permitAll();
	}
	
	
	
	

	
	
	
	
}
