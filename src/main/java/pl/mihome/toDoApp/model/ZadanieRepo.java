package pl.mihome.toDoApp.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/*	Ten interefejs jest publiczny (w przeciwieństwie do ZadanieSQLRepo), dzięki czemu
 * 	tutaj można określić metody, które będą dostępne z zewnątrz (np. z kontrolera), bo 
 * 	ZadanieSQLRepo nie jest public
 */
public interface ZadanieRepo {
	
	List<Zadanie> findAll();
	
	Page<Zadanie> findAll(Pageable page);
	
	Optional<Zadanie> findById(Long id);
	
	Zadanie save(Zadanie entity);
	
	boolean existsById(Long id);
	
	boolean existsByDoneIsFalseAndGrupa_Id(Long taskGroupId);
	
	List<Zadanie> findByGrupa_Id(Long id);

	//List<Zadanie> findByDoneIsTrueAndDeadlineIsBeforeKiedy(LocalDateTime kiedy);

	List<Zadanie> findByDoneIsFalseAndDeadlineBeforeKiedyOrNull(LocalDateTime kiedy);
}
