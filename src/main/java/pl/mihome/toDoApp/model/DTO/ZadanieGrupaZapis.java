package pl.mihome.toDoApp.model.DTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import pl.mihome.toDoApp.model.Projekt;
import pl.mihome.toDoApp.model.ZadanieGrupa;

public class ZadanieGrupaZapis {
	
	@NotBlank(message = "Grupa musi mieć opis")
	private String description;
	@Valid
	private List<ZadanieWGrupieZapis> zadania = new ArrayList<ZadanieWGrupieZapis>();

	public ZadanieGrupaZapis() {
		this.zadania.add(new ZadanieWGrupieZapis());
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ZadanieWGrupieZapis> getZadania() {
		return zadania;
	}

	public void setZadania(List<ZadanieWGrupieZapis> zadania) {
		this.zadania = zadania;
	}
	
	public ZadanieGrupa zapiszDoZadanieGrupa(Projekt projekt)
	{
		var result = new ZadanieGrupa();
		result.setProjekt(projekt);
		result.setDescription(description);
		result.setZadania(zadania.stream()
				.map(z -> z.zapiszDoZadanie(result))
				.collect(Collectors.toSet())
				);
		return result;
		
	}

	
}
