package pl.mihome.toDoApp.controller;


import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.mihome.toDoApp.logic.ZadanieService;
import pl.mihome.toDoApp.model.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


import org.slf4j.*;

//@RepositoryRestController - tego używa się, gdy jest bezpośredni dostęp przez @RepositoryRestResource
@RestController
@RequestMapping("/taski")
public class ZadanieController {
	
	private final ZadanieRepo repository;
	private static final Logger logger = LoggerFactory.getLogger(Zadanie.class);
	private final ZadanieService service;
	private final ApplicationEventPublisher aep;
	

	public ZadanieController(ZadanieRepo repository, ZadanieService service, ApplicationEventPublisher aep) {
		this.repository = repository;
		this.service = service;
		this.aep = aep;
	}
	
	// @RequestMapping(method = RequestMethod.GET, path = "/taski") lub prościej:
	@GetMapping(params = {"!sort", "!page", "!size"})
//	ResponseEntity<List<Zadanie>> readAllZadania()
	CompletableFuture<ResponseEntity<List<Zadanie>>> readAllZadania()
	{
		logger.warn("Wybrano wszystkie zadania!");
//		return ResponseEntity.ok(repository.findAll());
		return service.findAllAsync().thenApply(ResponseEntity::ok);
	}
	
	@GetMapping
	ResponseEntity<Page<Zadanie>> readAllZadania(Pageable page)
	{
		logger.info("Z wyborem stron");
		return ResponseEntity.ok(repository.findAll(page));
	}
	
	@Transactional
	@PutMapping("/{id}")
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
	@PatchMapping("/{id}")
	public ResponseEntity<?> toggleZadanie(@PathVariable Long id) {
		if(!repository.existsById(id))
			return ResponseEntity.notFound().build();
		logger.info("Zmienianie Done...");
		repository.findById(id)
		.map(Zadanie::toggle)
		.ifPresent(aep::publishEvent);
		
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping //lub w starszych wersjach (poniżej 4.3):
//	@RequestMapping(method = RequestMethod.POST, path = "/taski")
	ResponseEntity<Zadanie> zapiszZadanie(@RequestBody @Valid Zadanie entity)
	{
		logger.info("Nowe zadanie zapisane");
		Zadanie stworzone = repository.save(entity);
		return ResponseEntity.created(URI.create("/Taski/"+stworzone.getId())).body(stworzone);
	}
	
	@GetMapping("/{id}")
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
	
	@GetMapping("/search/today")
	ResponseEntity<List<Zadanie>> readZadaniaNaDzisiaj() {
		return ResponseEntity.ok(repository.findByDoneIsFalseAndDeadlineBeforeKiedyOrNull(LocalDate.now().plusDays(1).atStartOfDay()));
		
		
	}
	
	@GetMapping("/search/done")
	ResponseEntity<?> readZadaniaByDone(@RequestParam(defaultValue = "true", value = "state") String stan) {
		if(stan.equals("true") || stan.equals("false")) {
		Boolean oczekiwanyStan = Boolean.valueOf(stan);
		List<Zadanie> lista = repository.findAll().stream()
				.filter(zad -> zad.isDone() == oczekiwanyStan)
				.collect(Collectors.toList());
		return ResponseEntity.ok(lista);
		}
		
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/test")
	void oldFashionWay(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		resp.getWriter().print("Test text");
	}

}
