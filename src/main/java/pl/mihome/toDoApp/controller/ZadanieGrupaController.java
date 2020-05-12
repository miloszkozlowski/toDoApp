package pl.mihome.toDoApp.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.mihome.toDoApp.exeptions.GroupDoneAgainstPolicyException;
import pl.mihome.toDoApp.exeptions.NoSuchGroupException;
import pl.mihome.toDoApp.logic.ZadanieGrupaSerwis;
import pl.mihome.toDoApp.model.DTO.ZadanieGrupaOdczyt;
import pl.mihome.toDoApp.model.DTO.ZadanieGrupaZapis;
import pl.mihome.toDoApp.model.DTO.ZadanieWGrupieOdczyt;
import pl.mihome.toDoApp.model.DTO.ZadanieWGrupieZapis;

@Controller
@RequestMapping("/grupazadan")
public class ZadanieGrupaController {
	

	//private ZadanieGrupaRepo repository;
	private ZadanieGrupaSerwis service;
	private static final Logger log = LoggerFactory.getLogger(ZadanieGrupaSerwis.class);
	
	public ZadanieGrupaController(/*ZadanieGrupaRepo repository,*/ ZadanieGrupaSerwis service) {
		//this.repository = repository;
		this.service = service;
	}
	
	@GetMapping(produces = MediaType.TEXT_HTML_VALUE)
	public String pokazWszystkie(Model model) {
		model.addAttribute("nowaGrupa", new ZadanieGrupaZapis());
		return "grupy";
	}
	
	@PostMapping(params = "addZadanie", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.TEXT_HTML_VALUE)
	String dodajZadanieForm(@ModelAttribute("nowaGrupa") ZadanieGrupaZapis grupaZapis) {
		grupaZapis.getZadania().add(new ZadanieWGrupieZapis());
		return "grupy";
	}
	
	@PostMapping(params = "remZadanie", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.TEXT_HTML_VALUE)
	String odejmijZadanieForm(@ModelAttribute("nowaGrupa") ZadanieGrupaZapis grupaZapis) {
		var zad = grupaZapis.getZadania();
		zad.remove(zad.size()-1);
		return "grupy";
	}
	
	@PostMapping(params = "groupId", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.TEXT_HTML_VALUE)
	String zmianaDone(@ModelAttribute("nowaGrupa") ZadanieGrupaZapis grupaZapis,
			@RequestParam("groupId") Long id,
			Model model) {
		log.info("[zmianaDone] Zmieniam status grupy");
		try {
		service.toggleGroup(id);
		model.addAttribute("wszystkieGrupy", service.pokazWszsytkie());
		}
		catch(GroupDoneAgainstPolicyException ex) {
			model.addAttribute("msg", ex.getMessage());
		}
		return "grupy";
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.TEXT_HTML_VALUE)
	String dodajZadanie(@ModelAttribute("nowaGrupa") @Valid ZadanieGrupaZapis source,
			BindingResult bindingResult,
			Model model) {
		log.info("[dodajZadanie] Dodawanie grupy zadań");
		if(bindingResult.hasErrors()) {
			model.addAttribute("msg", "Formularz zawiera błędy. Należy go poprawić.");
		}
		else {
			service.zapiszZadanieGrupa(source);
			model.addAttribute("msg", "Nowa grupa została utworzona");
		}
		return "grupy";
	}
	
	
	///// JSON
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ZadanieGrupaOdczyt> dodajGrupę(@RequestBody @Valid ZadanieGrupaZapis zadanieGrupaZapisDTO) {
		var dodane = service.zapiszZadanieGrupa(zadanieGrupaZapisDTO);
		return ResponseEntity.created(URI.create("/grupazadan/" + dodane.getId())).body(dodane);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<ZadanieGrupaOdczyt>> pokazGrupy() {
		return ResponseEntity.ok(service.pokazWszsytkie());
		
	}
	
	
	@PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> togglujGrupe(@PathVariable Long id) {
			service.toggleGroup(id);
			return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/{id}/taski", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<ZadanieWGrupieOdczyt>> pokazTaskiZadania(@PathVariable Long id) {
		return ResponseEntity.ok(service.pokazTaskiWGrupie(id));
	}
	
	@ExceptionHandler(NoSuchGroupException.class)
	ResponseEntity<?> obsluzIllArgEx() {
		return ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler(IllegalStateException.class)
	ResponseEntity<?> obsluzIllStateEx(IllegalStateException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
	
//	@ExceptionHandler(GroupDoneAgainstPolicyException.class)
//	String obsluzGroupDoneEx(GroupDoneAgainstPolicyException ex, Model model) {
//		model.addAttribute("msg", ex.getMessage());
//		return "warn";
//	}
	
	@ModelAttribute
	void wszystkieZadania(Model model) {
		model.addAttribute("wszystkieGrupy", service.pokazWszsytkie());
	}
	
}
