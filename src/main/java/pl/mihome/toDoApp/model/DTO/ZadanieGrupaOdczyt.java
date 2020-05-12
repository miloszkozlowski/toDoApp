package pl.mihome.toDoApp.model.DTO;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.mihome.toDoApp.model.Zadanie;
import pl.mihome.toDoApp.model.ZadanieGrupa;

public class ZadanieGrupaOdczyt {
	private static final Logger log = LoggerFactory.getLogger(ZadanieGrupaOdczyt.class);
	
	private Long id;
	private String description;
	private LocalDateTime deadline; // pochodzi od ostatniego (najpóźniejszego) deadline zadań w grupie
	private Boolean done;
	private List<ZadanieWGrupieOdczyt> zadania;
	
	public ZadanieGrupaOdczyt(ZadanieGrupa grupa) {
		
		this.id = grupa.getId();
		this.description = grupa.getDescription();
		this.done = grupa.isDone();
		
		log.info("[constructor]: Tworzę " + this.description);
		
		
		grupa.getZadania().stream()
		.map(Zadanie::getDeadline)		
		.filter(Objects::nonNull)
		.max(LocalDateTime::compareTo)
		.ifPresent(data -> this.deadline = data);
		
		this.zadania = grupa.getZadania().stream()
				.map(ZadanieWGrupieOdczyt::new)
				.collect(Collectors.toList());
	}
	
	public Long getId() {
		return id;
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

	public Boolean getDone() {
		return done;
	}

	public void setDone(Boolean done) {
		this.done = done;
	}

	public List<ZadanieWGrupieOdczyt> getZadania() {
		return zadania.stream()
				.sorted(Comparator.comparing(ZadanieWGrupieOdczyt::getDescription))
				.collect(Collectors.toList());
	}

	public void setZadania(List<ZadanieWGrupieOdczyt> zadania) {
		this.zadania = zadania;
	}
	

}
