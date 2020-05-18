package pl.mihome.toDoApp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import pl.mihome.toDoApp.exeptions.NoSuchGroupException;

@ControllerAdvice
public class IllegalExceptionAdvice {
	@ExceptionHandler(NoSuchGroupException.class)
	ResponseEntity<?> obsluzIllArgEx() {
		return ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler(IllegalStateException.class)
	ResponseEntity<?> obsluzIllStateEx(IllegalStateException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
}
