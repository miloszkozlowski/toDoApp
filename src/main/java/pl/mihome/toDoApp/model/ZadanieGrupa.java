package pl.mihome.toDoApp.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "task_groups")
public class ZadanieGrupa extends ZadanieBaza {
	
	@JsonIgnore
	@OneToMany(mappedBy = "grupa", cascade = CascadeType.ALL)
	private Set<Zadanie> zadania;
	
	@ManyToOne
	@JoinColumn(name = "project_id")
	private Projekt projekt;
	
	@Embedded
	private Audit audit = new Audit();
	
	public ZadanieGrupa() {
	}
	
	public ZadanieGrupa(String description, Set<Zadanie> zadania)
	{
		super.setDescription(description);
		this.zadania = zadania;
	}


	public Set<Zadanie> getZadania() {
		return zadania;
	}

	public void setZadania(Set<Zadanie> zadania) {
		this.zadania = zadania;
	}

	public Projekt getProjekt() {
		return projekt;
	}

	public void setProjekt(Projekt projekt) {
		this.projekt = projekt;
	}
	
	

}
