package pl.mihome.toDoApp.logic;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import pl.mihome.toDoApp.model.Zadanie;
import pl.mihome.toDoApp.model.ZadanieRepo;

//	stworzony na potrzebę zaprezentowania ansynchroniczności - zapytanie do bazy danych o wszystkie Zadania
//	może trwać dłuższy czas, dlatego wtedy warto zrobić to w osobnym wątku.
@Service
public class ZadanieService {

	private ZadanieRepo repo;
	
	public ZadanieService(ZadanieRepo repo) {
		this.repo = repo;
	}
	
	
	
	@Async
	public CompletableFuture<List<Zadanie>> findAllAsync() {
		return CompletableFuture.supplyAsync(repo::findAll);
	}
	
}
