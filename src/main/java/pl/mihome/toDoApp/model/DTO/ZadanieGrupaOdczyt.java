package pl.mihome.toDoApp.model.DTO;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import pl.mihome.toDoApp.model.Zadanie;
import pl.mihome.toDoApp.model.ZadanieGrupa;

public class ZadanieGrupaOdczyt {
	
	private String description;
	private LocalDateTime deadline; // pochodzi od ostatniego (najpóźniejszego) deadline zadań w grupie
	private Set<ZadanieWGrupieOdczyt> zadania;
	
	public ZadanieGrupaOdczyt(ZadanieGrupa grupa) {
		this.description = grupa.getDescription();
		
		grupa.getZadania().stream()
		.map(Zadanie::getDeadline)
		.max(LocalDateTime::compareTo)
		.ifPresent(data -> this.deadline = data);
		
		this.zadania = grupa.getZadania().stream()
				.map(ZadanieWGrupieOdczyt::new)
				.collect(Collectors.toSet());
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
	}

	public Set<ZadanieWGrupieOdczyt> getZadania() {
		return zadania;
	}

	public void setZadania(Set<ZadanieWGrupieOdczyt> zadania) {
		this.zadania = zadania;
	}
	

}
