package pl.mihome.toDoApp.model;

import java.util.List;
import java.util.Optional;

public interface ProjektRepo {
	
	List<Projekt> findAll();
	
	Optional<Projekt> findById(Long id);
	
	Projekt save(Projekt entity);
}
