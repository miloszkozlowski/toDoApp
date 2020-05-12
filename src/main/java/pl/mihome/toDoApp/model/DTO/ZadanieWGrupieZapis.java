package pl.mihome.toDoApp.model.DTO;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import pl.mihome.toDoApp.model.Zadanie;
import pl.mihome.toDoApp.model.ZadanieGrupa;

public class ZadanieWGrupieZapis {

	@NotBlank(message = "Treść opisu zadania nie może być pusta")
	private String description;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime deadline;
	
	
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
	
	public Zadanie zapiszDoZadanie(ZadanieGrupa grupa) {
		return new Zadanie(description, deadline, grupa);
	}
	
	
}
