package pl.mihome.toDoApp.report;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.mihome.toDoApp.model.Zadanie;
import pl.mihome.toDoApp.model.ZadanieRepo;

@RestController
@RequestMapping("/reports")
public class ReportController {

	private ZadanieRepo zadanieRepo;
	private PersistedZadanieEventRepo eventRepo;
	
	
	
	public ReportController(ZadanieRepo zadanieRepo, PersistedZadanieEventRepo eventRepo) {
		this.zadanieRepo = zadanieRepo;
		this.eventRepo = eventRepo;
	}

	@GetMapping("/taskchange/{id}")
	ResponseEntity<ZadanieWithChangesCount> taskStateChangesCount(@PathVariable Long id) {
		return zadanieRepo.findById(id)
		.map(zadanie -> new ZadanieWithChangesCount(zadanie, eventRepo.findByTaskId(zadanie.getId())))
		.map(ResponseEntity::ok)
		.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/donebeforedeadline/{id}")
	ResponseEntity<ZadanieWithDoneDateCheck> taskDoneBeforeDeadline(@PathVariable Long id) {
		return zadanieRepo.findById(id)
			.map(zadanie -> new ZadanieWithDoneDateCheck(zadanie, eventRepo.findByTaskId(zadanie.getId())))
			.map(ResponseEntity::ok)
			.orElse(ResponseEntity.notFound().build());
	}
	
	
	private static class ZadanieWithChangesCount {
		
		public String description;
		public boolean done;
		public int changeCounter;
		
		ZadanieWithChangesCount(Zadanie task, List<PersistedZadanieEvent> events) {
			description = task.getDescription();
			done = task.isDone();
			changeCounter = events.size();
		}
		
		
	}
	
	private static class ZadanieWithDoneDateCheck {
		
		public String description;
		public LocalDateTime deadline;
		public boolean done;
		public boolean doneB4Deadline;
		
		public ZadanieWithDoneDateCheck(Zadanie task, List<PersistedZadanieEvent> events) {
			description = task.getDescription();
			deadline = task.getDeadline();
			done = task.isDone();
			
			if(deadline == null)
				doneB4Deadline = true;
			else if(!done)
				doneB4Deadline = false;
			else {
				doneB4Deadline = events.stream()
						.map(PersistedZadanieEvent::getOccurance)
						.max(LocalDateTime::compareTo)
						.filter(t -> t.isBefore(task.getDeadline()))
						.isPresent();
			}
			
		}
	}
}
