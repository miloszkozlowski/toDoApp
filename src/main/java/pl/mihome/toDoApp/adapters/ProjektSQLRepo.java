package pl.mihome.toDoApp.adapters;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pl.mihome.toDoApp.model.Projekt;
import pl.mihome.toDoApp.model.ProjektRepo;

@Repository
interface ProjektSQLRepo extends ProjektRepo, JpaRepository<Projekt, Long>  {
	
	@Override
	@Query("select distinct p from Projekt p join fetch p.projektKroki")
	//moja wersja:
	//@Query("select distinct p from Projekt p join fetch p.grupyZadan join fetch p.projektKroki")
	List<Projekt> findAll();
}
