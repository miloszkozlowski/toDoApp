package pl.mihome.toDoApp.adapters;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pl.mihome.toDoApp.model.ZadanieGrupa;
import pl.mihome.toDoApp.model.ZadanieGrupaRepo;

@Repository
interface ZadanieGrupaSQLRepo extends ZadanieGrupaRepo, JpaRepository<ZadanieGrupa, Long> {

/*
 * 	Trzeba nadpisać metodę findAll, bo w przypadku jej wywolania
 * 	zostanie wygenerowane N + 1 zapytań do bazy danych (1 - zapytanie
 * 	o listę grup zadan, a potem do każdej grupy w liczbie N zapytanie 
 * 	o listę przypisanych zadań (pole {@Code private Set<Zadanie> zadania;}
 * 
 * 	{@Code select distinc g} pobiera z bazy listę ZadanieGrupa, które:
 * 	1. nie potarzają się (oddaje jeden ZadanieHrupa nawet jeżeli ma kilka zadan),
 * 	2. ma przypisane jakieś zadania (nie pobiera pustych)
 */
	@Override
	@Query("select distinct g from ZadanieGrupa g join fetch g.zadania")
	List<ZadanieGrupa> findAll();

	@Override
	boolean existsByDoneIsFalseAndProjekt_Id(Long projektId);
	
}
