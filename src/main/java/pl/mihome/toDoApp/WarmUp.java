package pl.mihome.toDoApp;

import java.util.Set;

import org.slf4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import pl.mihome.toDoApp.model.Zadanie;
import pl.mihome.toDoApp.model.ZadanieGrupa;
import pl.mihome.toDoApp.model.ZadanieGrupaRepo;

@Component
public class WarmUp implements ApplicationListener<ContextRefreshedEvent> {

	private final Logger log = org.slf4j.LoggerFactory.getLogger(WarmUp.class);
	private final ZadanieGrupaRepo repo;
	
	
	public WarmUp(ZadanieGrupaRepo repo) {
		this.repo = repo;
	}


	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		log.info("Rozgrzewanie - czyli na przykład zapylanie cache'szu, a w tym wypadku tworzenie grupy zadań, które MUSZĄ zawsze być");
		
		String description = "ApplicationContextEvents";
		
		if(!repo.existsByDescription(description)) {
			log.info("Grupa o nazwie \"" + description + "\" nie istnieje. Będę tworzył...");
			var grupa = new ZadanieGrupa();
			grupa.setDescription(description);
			grupa.setZadania(Set.of(
				new Zadanie("ContextClosedEvent", null, grupa),
				new Zadanie("ContextRefreshedEvent", null, grupa),
				new Zadanie("ContextStartedEvent", null, grupa),
				new Zadanie("ContextStoppedEvent", null, grupa)				
			)); 
			repo.save(grupa);
		}
		
	}

}
