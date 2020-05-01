package pl.mihome.toDoApp.adapters;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.mihome.toDoApp.model.ProjektKroki;
import pl.mihome.toDoApp.model.ProjektKrokiRepo;

@Repository
interface ProjektStepsSQLRepo extends ProjektKrokiRepo, JpaRepository<ProjektKroki, Long> {

}
