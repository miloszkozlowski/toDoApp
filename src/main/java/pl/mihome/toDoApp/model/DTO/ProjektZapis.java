package pl.mihome.toDoApp.model.DTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import pl.mihome.toDoApp.model.Projekt;
import pl.mihome.toDoApp.model.ProjektKroki;

/*
 * 	Walidacje w tej klasie są zrobione pod ekrany, tak, żeby bezpośrednio w widoku 
 * 	(bez zapisu jeszcze) można było walidować pola.
 */
public class ProjektZapis {
	
	@NotBlank(message = "Opis projektu nie może być pusty")
	private String description;
	
	@Valid
	private List<ProjektKroki> kroki = new ArrayList<ProjektKroki>();
	
	public ProjektZapis() {
		this.kroki.add(new ProjektKroki());
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ProjektKroki> getKroki() {
		return kroki;
	}

	public void setKroki(List<ProjektKroki> kroki) {
		this.kroki = kroki;
	}

	public Projekt zapiszDoProjektu() {
		var result = new Projekt();
		this.kroki.forEach(krok -> krok.setProjekt(result));
		result.setDescription(this.description);
		result.setProjektKroki(new HashSet<ProjektKroki>(kroki));
		return result;		
	}
	
}
