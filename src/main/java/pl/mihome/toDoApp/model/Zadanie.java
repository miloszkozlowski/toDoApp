package pl.mihome.toDoApp.model;

import java.time.LocalDateTime;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import pl.mihome.toDoApp.model.customEvents.ZadanieEvent;



@Entity
@Table(name = "tasks")
public class Zadanie extends ZadanieBaza {
		
	private LocalDateTime deadline;
	
	/*
	 * 	@AttributeOverrides({
		@AttributeOverride(column = @Column(name = "InnaNazwaKolumnnyNizWDataAudit"), name = "nazwaPolaZDataAudit")
	})
	 * 	Można tego użyć kiedy w danej encji jednorazowo 
	 */
	@Embedded
	private Audit audit = new Audit();
	
	@ManyToOne
	@JoinColumn(name = "task_groups_id")
	private ZadanieGrupa grupa;

	
	Zadanie(){ //pusty konstruktor jest potrzebny na wypadek zmiany rodzaju Repository Hibernate mógłby mieć problemy 
	}
	
	public Zadanie(String description, LocalDateTime deadline) {
		this(description, deadline, null);
	}
	
	public Zadanie(String description, LocalDateTime deadline, ZadanieGrupa grupa) {
		super.setDescription(description);
		this.deadline = deadline;
		if(grupa != null)
			this.grupa = grupa;
	
	}
	

	public LocalDateTime getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
	}
	
	public ZadanieGrupa getGrupa() {
		return grupa;
	}

	public void setGrupa(ZadanieGrupa grupa) {
		this.grupa = grupa;
	}
	
	public ZadanieEvent toggle() { //metoda, która zastepuję setDone (tutaj w ZadanieBaza i dlatego tam został setDone(..), a korzysta z własnego Eventu
		super.setDone(!super.isDone());
		return ZadanieEvent.changed(this);
	}

	public void updateFrom(Zadanie zrodlo) {
		super.setDescription(zrodlo.getDescription());
		super.setDone(zrodlo.isDone());
		this.deadline = zrodlo.getDeadline();
		this.grupa = zrodlo.getGrupa();
	}
	


	
}
