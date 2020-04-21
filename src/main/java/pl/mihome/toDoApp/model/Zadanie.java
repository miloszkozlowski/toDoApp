package pl.mihome.toDoApp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;






@Entity
@Table(name = "tasks")
public class Zadanie {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "Treść zadania nie może być pusta")
	private String desc;
	@NotNull(message = "Done musi mieć wartość")
	private Boolean done;
	
	
	Zadanie(){ //pusty konstruktor jest potrzebny na wypadek zmiany rodzaju Repository Hibernate mógłby mieć problemy 
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Boolean getDone() {
		return done;
	}
	public void setDone(Boolean done) {
		this.done = done;
	}

	
}
