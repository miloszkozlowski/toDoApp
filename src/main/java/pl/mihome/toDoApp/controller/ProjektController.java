package pl.mihome.toDoApp.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.mihome.toDoApp.exeptions.NoSuchGroupException;
import pl.mihome.toDoApp.logic.ProjektSerwis;
import pl.mihome.toDoApp.model.Projekt;
import pl.mihome.toDoApp.model.ProjektKroki;
import pl.mihome.toDoApp.model.DTO.ProjektZapis;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')") //tutaj jest abrdziej elastycznie niż {@code @Secured} albo {@code @RolesAllowed}, bo używa się Spring-EL, więc można robić or i inne
@RequestMapping("/projekt")
public class ProjektController {
	
	private ProjektSerwis serwis;
	
	public ProjektController(ProjektSerwis serwis) {
		this.serwis = serwis;
	}
	@GetMapping
	String showProjects(Model model) {
		model.addAttribute("projekt", new ProjektZapis());
		return "projekty";
	}
	
	@PostMapping(params = "addStep")
	String dodajKrokForm(@ModelAttribute("projekt") ProjektZapis projektZapis) {
		projektZapis.getKroki().add(new ProjektKroki());
		return "projekty";
	}
	
	@PostMapping
	String dodajNowyProjekt(
			@ModelAttribute("projekt") @Valid ProjektZapis projektZapis, 
			BindingResult bindingResult, //trzeba to dodać zaraz po DTO, żeby dostać infomrację o walidacji obiektu przed w sygnaturze
			Model model
			) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("wiadomosc", "Formularz zwiera błędy");
			return "projekty";
		}
		model.addAttribute("projekt", new ProjektZapis());
		model.addAttribute("wiadomosc", "Projekt został dodany");
		//	{@code @ModelAttribute} korzysta z cache i przez to się nie odświeża, dlatego poniżej wymuszenie
		model.addAttribute("wszystkieProjekty", serwis.pokazWszystkie());
		serwis.zapisz(projektZapis);
		return "projekty";
	}
	
	@PostMapping("/{id}")
	String dodajTaskiDoProjektu(
			@ModelAttribute("projekt") ProjektZapis projektZapis,
			@PathVariable(name = "id") Long projektId, 
			Model model,
			@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime deadline //pattern obrazuje jakie dane dostajemy z formualrza, a Spring zamieni to w LocalDateTime
			) {
		try {
			serwis.stworzGrupeZadan(projektId, deadline);
			model.addAttribute("wiadomosc", "Dodano grupę zadań");
		}
		catch (IllegalStateException | NoSuchGroupException e) {
			model.addAttribute("wiadomosc", "Błąd żądania - nie ma takiego projektu albo w projekcie nie może być więcej niż jedna grupa");
		}
	
		return "projekty";
		
		
	}
	
	
	
	/*
	 * 	Atrybut modelu, który jest dodwany do wszystkich mappingów w kontrolerze
	 */
	@ModelAttribute("wszystkieProjekty")
	List<Projekt> wszystkieProjekty() {
		return serwis.pokazWszystkie();
	}

}
