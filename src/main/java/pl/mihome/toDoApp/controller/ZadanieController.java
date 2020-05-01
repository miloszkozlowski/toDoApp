package pl.mihome.toDoApp.controller;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.mihome.toDoApp.model.*;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;


import org.slf4j.*;

//@RepositoryRestController - tego używa się, gdy jest bezpośredni dostęp przez @RepositoryRestResource
@RestController
public class ZadanieController {
	
	private final ZadanieRepo repository;
	private static final Logger logger = LoggerFactory.getLogger(Zadanie.class);
	

	public ZadanieController(ZadanieRepo repository) {
		this.repository = repository;
	}
	
	// @RequestMapping(method = RequestMethod.GET, path = "/taski") lub prościej:
	@GetMapping(value = "/taski", params = {"!sort", "!page", "!size"})
	ResponseEntity<?> readAllZadania()
	{
		logger.warn("Wybrano wszystkie zadania!");
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping(value = "/taski")
	ResponseEntity<Page<Zadanie>> readAllZadania(Pageable page)
	{
		logger.info("Z wyborem stron");
		return ResponseEntity.ok(repository.findAll(page));
	}
	
	@Transactional
	@PutMapping("/taski/{id}")
	public ResponseEntity<?> zmienZadanie(@PathVariable("id") Long id, @RequestBody @Valid Zadanie doAktualizacji) //@RequestBody - spring wypełni z ciała żądania PUT
	{
		if(repository.existsById(id))
		{
			Optional<Zadanie> oryginal = repository.findById(id);
			logger.info("Zadanie aktualizowane");
			oryginal.ifPresent(zad -> zad.updateFrom(doAktualizacji));
			oryginal.ifPresent(zad -> System.out.println(zad.getDescription()));
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
		
	}
	
	
	/*
	 * żeby zadziała się zmiana w bazie danych na podstawie zmiany pola obiektu
	 * ta metoda musi miec code{@Transactional} oraz być publiczna.
	 */
	@Transactional
	@PatchMapping("/taski/{id}")
	public ResponseEntity<?> toggleZadanie(@PathVariable Long id) {
		if(!repository.existsById(id))
			return ResponseEntity.notFound().build();
		logger.info("Zmienianie Done...");
		repository.findById(id).ifPresent(zadanie -> {
			zadanie.setDone(!zadanie.isDone());
			logger.info("Nowy status done dla id "+id+": "+zadanie.isDone());
		});
		
		return ResponseEntity.noContent().build();
	}
	
//	@PostMapping("/taski") lub w starszych wersjach (poniżej 4.3):
	@RequestMapping(method = RequestMethod.POST, path = "/taski")
	ResponseEntity<Zadanie> zapiszZadanie(@RequestBody @Valid Zadanie entity)
	{
		logger.info("Nowe zadanie zapisane");
		Zadanie stworzone = repository.save(entity);
		return ResponseEntity.created(URI.create("/Taski/"+stworzone.getId())).body(stworzone);
	}
	
	@GetMapping("/taski/{id}")
	ResponseEntity<Zadanie> readZadanieById(@PathVariable Long id)
	{
		Optional<Zadanie> opcja = repository.findById(id);
		if(opcja.isPresent())
		{
			logger.info("Wczytany task o id " + id);
			return ResponseEntity.ok(opcja.get());
		}
		else
		{
			logger.warn("Brak tasku do wczytania - id: " + id);
			return ResponseEntity.notFound().build();
		}
		
	}

}
