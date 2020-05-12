package pl.mihome.toDoApp.adapters;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
//import org.springframework.data.repository.query.Param;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;
//import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import pl.mihome.toDoApp.model.Zadanie;
import pl.mihome.toDoApp.model.ZadanieRepo;

/* Rozszerza ZadanieRepo - publiczny interfejs z listą metod dopuszczonych publicznie
 * 
 * 
 * Całość jest nieużywana, gdy nie chcę dostępu do encji bezpośrednio przez repozytorium
@RepositoryRestResource(path = "taski") //path to ścieżka do zasobu przez http (domyślnie "zadanies") 
*/

@Repository
/*
 * 	@Profile("!integration") 
 *	doprecyzowanie, że gdy profil inny używać tego repo (gdy inaczej: będzie beanem metoda z TestConfiguration,
 *	ale nie jest w tym momncie potrzebne, bo test ma adnotacje @Primary przy beanie, wiec przy odpalaniu testu 
 *	ładuje się bean testowy, a bez testu ten poniżej.
 * 
 */
interface ZadanieSQLRepo extends ZadanieRepo, JpaRepository<Zadanie, Long> {

	/*	
	 * Kod poniżej jest niepotrzebny w sytuacji, gdy nie korzystam z adnotacji @RepositoryRestResource
	 * w zamian za @Repository po prostu przez co nie ma dostepu do Zadań bezpośrednio z repozytorium, 
	 * a zawsze zapytania idą przez kontroler @RestController (a nie @RepositoryRestController
	@Override
	@RestResource(exported = false)
	default void delete(Zadanie entity) {
	}

	@Override
	@RestResource(exported = false) 
	default void deleteById(Long id) {		
	}
	
	 */
	
//	@RestResource(path = "zrobione", rel = "done")
//	List<Zadanie> findByDoneIsTrue();
	
//	@RestResource(path = "zrobione", rel = "czyZrobione")
//	List<Zadanie> findByDone(@Param("state") boolean done);
	
	@Override
	boolean existsByDoneIsFalseAndGrupa_Id(Long taskGroupId);
	
	@Override
	List<Zadanie> findByGrupa_Id(Long taskGroupId);
	
	@Override
	@Query("select z from Zadanie z where done = false and z.deadline < :kiedy")
	List<Zadanie> findByDoneIsFalseAndDeadlineBeforeKiedyOrNull(@Param("kiedy") LocalDateTime kiedy);
	
	
	


	
	
}
