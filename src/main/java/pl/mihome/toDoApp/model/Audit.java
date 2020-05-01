package pl.mihome.toDoApp.model;

import java.time.LocalDateTime;

import javax.persistence.Embeddable;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Embeddable
class Audit {
	
	private LocalDateTime dateOfCreation;
	private LocalDateTime dateOfLastChange;
	
	
	
	public LocalDateTime getDateOfCreation() {
		return dateOfCreation;
	}



	public void setDateOfCreation(LocalDateTime dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}



	public LocalDateTime getDateOfLastChange() {
		return dateOfLastChange;
	}



	public void setDateOfLastChange(LocalDateTime dateOfLastChange) {
		this.dateOfLastChange = dateOfLastChange;
	}
	
	@PreUpdate
	public void markChangeDate() {
		this.dateOfLastChange = LocalDateTime.now();
	}

	@PrePersist
	public void markCreationDate() {
		this.dateOfCreation = LocalDateTime.now();
	}

}
