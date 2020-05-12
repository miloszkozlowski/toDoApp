package pl.mihome.toDoApp.model.DTO;


import pl.mihome.toDoApp.model.Zadanie;

public class ZadanieWGrupieOdczyt {
	
	private String description;
	private boolean done;
	
	public ZadanieWGrupieOdczyt(Zadanie zadanie) {
		this.description = zadanie.getDescription();
		this.done = zadanie.isDone();		
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}
	
	

}
