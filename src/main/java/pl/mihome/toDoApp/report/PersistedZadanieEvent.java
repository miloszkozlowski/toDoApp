package pl.mihome.toDoApp.report;

import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import pl.mihome.toDoApp.model.customEvents.ZadanieEvent;

@Entity
@Table(name = "taskevent")
public class PersistedZadanieEvent {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		private Long taskId;
		private LocalDateTime occurrence;
		private String name;
		
		public PersistedZadanieEvent() {
		}
		
		public PersistedZadanieEvent(ZadanieEvent event) {
			this.taskId = event.getTaskId();
			this.name = event.getClass().getSimpleName();
			this.occurrence = LocalDateTime.ofInstant(event.getOccurrance(), ZoneId.systemDefault());
		}

		public Long getTaskId() {
			return taskId;
		}

		public void setTaskId(Long taskId) {
			this.taskId = taskId;
		}

		public LocalDateTime getOccurance() {
			return occurrence;
		}

		public void setOccurrence(LocalDateTime occurrence) {
			this.occurrence = occurrence;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Long getId() {
			return id;
		}
		
		
}
