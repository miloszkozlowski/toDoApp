package pl.mihome.toDoApp.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pl.mihome.toDoApp.model.ZadanieGrupa;
import pl.mihome.toDoApp.model.ZadanieGrupaRepo;

@RestController
public class ZadanieGrupaController {

	private ZadanieGrupaRepo repository;
	
	public ZadanieGrupaController(ZadanieGrupaRepo repository) {
		this.repository = repository;
	}
	
	@PostMapping("/grupazadan")
	public ResponseEntity<ZadanieGrupa> dodajGrupę(@RequestBody @Valid ZadanieGrupa zadanieGrupa) {
		ZadanieGrupa dodane = repository.save(zadanieGrupa);
		return ResponseEntity.created(URI.create("/grupazadan/" + dodane.getId())).body(dodane);
	}
	
	@GetMapping("/grupazadan")
	public ResponseEntity<List<ZadanieGrupa>> pokazGrupy() {
		return ResponseEntity.ok(repository.findAll());
		
	}
}
