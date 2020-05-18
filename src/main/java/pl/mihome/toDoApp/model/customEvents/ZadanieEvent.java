package pl.mihome.toDoApp.model.customEvents;

import java.time.Clock;
import java.time.Instant;

import pl.mihome.toDoApp.model.Zadanie;


/*
 * Ta klasa definiuje mój własny event, który potem można wykorzystać przy użyciu ApplicationEventPublishera. 
 * Taki event przjdzie przez wszystkie beany i ten, który będzie nasłuchiwał, zareaguje. 
 * To zdarzenie ma zakomunikować zmianę stanu Zadania done/not done.
 */
public abstract class ZadanieEvent {
	
	public static ZadanieEvent changed(Zadanie source) {
		return source.isDone() ? new ZadanieDone(source) : new ZadanieUndone(source);
	}
	
	private Long taskId;
	private Instant occurrance; //określenie w czasie, kiedy zdarzenie wystapiło.  
	
	
	ZadanieEvent(Long taskId, Clock clock) {
		this.taskId = taskId;
		this.occurrance = Instant.now(clock);
	}
	
	public Long getTaskId() {
		return taskId;
	}
	public Instant getOccurrance() {
		return occurrance;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + " [taskId=" + taskId + ", occurrance=" + occurrance + "]";
	}

}
