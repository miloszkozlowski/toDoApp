package pl.mihome.toDoApp.model;

import java.util.List;
import java.util.Optional;

public interface ZadanieGrupaRepo {
	
	ZadanieGrupa save(ZadanieGrupa zadanieGrupa);
	
	List<ZadanieGrupa> findAll();
	
	Optional<ZadanieGrupa> findById(Long id);
	
	boolean existsByDoneIsFalseAndProjekt_Id(Long projektId);

}
