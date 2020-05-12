package pl.mihome.toDoApp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

@Entity
@Table(name = "project_steps")
public class ProjektKroki {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Opis kroku nie może być pusty")
	private String description;
	
	@NotNull
	@Max(value = 0, message = "Ilość dni do deadline musi być podana w formie ujemnej - np. \"-2\".")
	private int daysToDeadline;
	
	
	@ManyToOne
	@JoinColumn(name = "project_id")
	private Projekt projekt;

	public ProjektKroki() {
	}
	
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDaysToDeadline() {
		return daysToDeadline;
	}

	public void setDaysToDeadline(int daysToDeadline) {
		this.daysToDeadline = daysToDeadline;
	}

	public Projekt getProjekt() {
		return projekt;
	}

	public void setProjekt(Projekt projekt) {
		this.projekt = projekt;
	}

	public Long getId() {
		return id;
	}
	
	
	
}
