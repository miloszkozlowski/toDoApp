package pl.mihome.toDoApp.logic;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import pl.mihome.toDoApp.ZadanieConfigurationProperties;
import pl.mihome.toDoApp.model.ZadanieGrupa;
import pl.mihome.toDoApp.model.ZadanieGrupaRepo;
import pl.mihome.toDoApp.model.ZadanieRepo;
import pl.mihome.toDoApp.model.DTO.ZadanieGrupaOdczyt;
import pl.mihome.toDoApp.model.DTO.ZadanieGrupaZapis;

/*
 * 	{@code @Service} definiuje serwis, czyli pośrednika między kontrolerem a repozytorium. 
 * 	Dzięki użyciu serwisu w kontrolerze można skupić się na treści odpowiedzi na żądania 
 * 	nie gmatwając kodu historyjkami użytkownika. 
 */
@Service
/*
 * 	{@code @Scope} definiuje jak ma być używany serwis w wielu wątkach. W sytuacji gdy serwis
 * 	jest używany w jednym miejscu (np. w jednym tylko kontrolerze) wówczas wartość domyślna 
 * 	{@code ConfigurableBeanFactory.SCOPE_SINGLETON} jest ok - wtedy Spring wstrzykuje przy użyciu
 * 	tą samą instancję. Gdy użyte jest natomiast {@code ConfigurableBeanFactory.SCOPE_PROTOTYPE} 
 * 	Spring strzykuje za każdym razem nową instancję. 
 * 
 * 	Zamiast {@code @Scope} można użyć {@code @RequestScope}, jest to scope dla aplikacji weobwych.
 * 	Działa to tak, że przy każdym żądaniu http wpadającym do controllera tworzona jest nowa instancja. 
 *  I tak dla całego żądania wykorzystywana jest ta sama instancja. 
 *  
 *  Istnieje jeszcze {@code @SessionScope} - jedna instancja dla jednej sesji użytkownika.
 *  
 *  Natomiast {@code @ApplicationScope} to jedna instancja serwisu dla całego servletu (czyli nawet
 *  dla kilku aplikacji).
 */
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ZadanieGrupaSerwis {
	
	ZadanieGrupaRepo repository;
	ZadanieRepo taskRepository;
	ZadanieConfigurationProperties config;
	
	public ZadanieGrupaSerwis(ZadanieGrupaRepo repo, ZadanieRepo taskRepository) {
		this.repository = repo;
		this.taskRepository = taskRepository;
		//this.config = config;
	}
	
	public ZadanieGrupaOdczyt zapiszZadanieGrupa(ZadanieGrupaZapis grupa) {
		ZadanieGrupa zg = repository.save(grupa.zapiszDoZadanieGrupa());
		return new ZadanieGrupaOdczyt(zg);
	}
	
	public List<ZadanieGrupaOdczyt> pokazWszsytkie() {
		List<ZadanieGrupa> zg = repository.findAll();
		return zg.stream()
		.map(g -> new ZadanieGrupaOdczyt(g))
		.collect(Collectors.toList());
	}
	
	public void toggleGroup(Long id) {
		if(taskRepository.existsByDoneIsFalseAndGrupa_Id(id))
			throw new IllegalStateException("Nie można zmienić stanu grupy gdy istnieje niewykonane zadanie. Zakończ wszystkie zadania w grupie.");
		ZadanieGrupa zg = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Nie ma takiej grupy."));
		zg.setDone(!zg.isDone());
		repository.save(zg);
	}

}
