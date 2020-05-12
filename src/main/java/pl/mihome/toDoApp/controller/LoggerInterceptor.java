package pl.mihome.toDoApp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoggerInterceptor implements HandlerInterceptor {

	private static final Logger log = LoggerFactory.getLogger(LoggerInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.info("[preHandle] " + request.getMethod() + " " + request.getRequestURI());
		return true; //kiedy zwraca true, request jest procesowany dalej
	}
	
	//w przeciwieństwie do Filtra nie ma tutaj możliwości zmiany requesta i przekazania go dalej

	
}
