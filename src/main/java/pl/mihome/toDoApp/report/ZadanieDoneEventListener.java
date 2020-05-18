package pl.mihome.toDoApp.report;

import org.slf4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import pl.mihome.toDoApp.model.customEvents.ZadanieDone;
import pl.mihome.toDoApp.model.customEvents.ZadanieEvent;
import pl.mihome.toDoApp.model.customEvents.ZadanieUndone;

@Service
public class ZadanieDoneEventListener {

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(ZadanieDoneEventListener.class);
	private final PersistedZadanieEventRepo repo;
	
	
	public ZadanieDoneEventListener(PersistedZadanieEventRepo repo) {
		this.repo = repo;
	}

	@Async //jest, ponieważ chcemy, aby to co dzieje się po usłyszeniu zdarzenia działo się w osobnym wątku
	@EventListener
	void on(ZadanieDone event) {
		onChange(event);
	}
	
	@Async
	@EventListener
	void on(ZadanieUndone event) {
		onChange(event);
	}
	
	
	void onChange(ZadanieEvent event) {
		log.info("Got " + event.toString());
		repo.save(new PersistedZadanieEvent(event));
	}
}
