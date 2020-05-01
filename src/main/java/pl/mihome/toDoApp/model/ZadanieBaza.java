package pl.mihome.toDoApp.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@MappedSuperclass
abstract public class ZadanieBaza {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "Treść zadania zadań nie może być pusta")
	private String description;
	@NotNull(message = "Done musi mieć wartość")
	private Boolean done = false;

	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean isDone() {
		return done;
	}
	public void setDone(Boolean done) {
		this.done = done;
	}
	public Long getId() {
		return id;
	}
	

}
