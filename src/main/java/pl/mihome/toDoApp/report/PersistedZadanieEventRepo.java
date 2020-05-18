package pl.mihome.toDoApp.report;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface PersistedZadanieEventRepo extends JpaRepository<PersistedZadanieEvent, Long> {
	
	List<PersistedZadanieEvent> findByTaskId(Long taskId);

}
