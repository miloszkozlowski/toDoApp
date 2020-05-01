package pl.mihome.toDoApp.model.DTO;

import java.util.Set;
import java.util.stream.Collectors;

import pl.mihome.toDoApp.model.ZadanieGrupa;

public class ZadanieGrupaZapis {
	
	private String description;
	private Set<ZadanieWGrupieZapis> zadania;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<ZadanieWGrupieZapis> getZadania() {
		return zadania;
	}

	public void setZadania(Set<ZadanieWGrupieZapis> zadania) {
		this.zadania = zadania;
	}
	
	public ZadanieGrupa zapiszDoZadanieGrupa()
	{
		var result = new ZadanieGrupa();
		result.setDescription(description);
		result.setZadania(zadania.stream()
				.map(z -> z.zapiszDoZadanie(result))
				.collect(Collectors.toSet())
				);
		return result;
		
	}

	
}
