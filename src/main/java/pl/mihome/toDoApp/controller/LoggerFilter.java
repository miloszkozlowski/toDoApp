package pl.mihome.toDoApp.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/*
 * 	To jest filtr, który służy do filtorwania zapytań wysyłanych do servletu.
 * 	Stosuje się go, aby wyciągnąć coś z każdego żądania przesyłanego do aplikacji. 
 * 	Aby filtr był rozpoznany przez spirnga musi impementować inter. {@code Filter}.
 * 
 * 	Aby ustalić łatwo kolejność/priorytet łańcucha filtrów {@code FilterChain}
 * 	poprzez Spring, trzeba implementować interfejs {@code Ordered}. Zamiast tego,
 * 	można nie implementować interfejsu, a użyć Springowej adnotacji {@code @Order}
 * 
 * 	Zamiast filtra można używać interceptora (np. do autoryzacji użytkownika)
 */

@Component
@Order(0)
public class LoggerFilter implements Filter {
	
	private static final Logger log = LoggerFactory.getLogger(LoggerFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if(request instanceof HttpServletRequest) {
			var httpReq = (HttpServletRequest) request;
			log.info("[doFilter] " + httpReq.getMethod() + " " + httpReq.getRequestURI());
		}
		
		// do tego miejsca gdyby nie było nic dalej request zostałby pochłonięty
		
		chain.doFilter(request, response);
		
	}


/*
 * Zamiast nadpisywać metodę poniżej, można użyć adnotacji Springowej {@code @Order}
 *
 *	@Override
 *	public int getOrder() {
 *		
 *		return 0; // im niższa wartość zwracana, tym filtr zostanie wykonany wcześniej w kolejności
 *		możliwe do użycia także:
 *		return Ordered.HIGHEST_PRECEDENCE;
 *		return Ordered.LOWEST_PRECEDENCE;
 *	}
 */	

}
