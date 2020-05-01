package pl.mihome.toDoApp.logic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pl.mihome.toDoApp.ZadanieConfigurationProperties;
import pl.mihome.toDoApp.model.ProjektRepo;
import pl.mihome.toDoApp.model.ZadanieGrupaRepo;

@Configuration
public class LogicConfiguration {

	@Bean
	ProjektSerwis projektService(ProjektRepo repository, ZadanieGrupaRepo grupaRepository, ZadanieConfigurationProperties config, ZadanieGrupaSerwis taskGroupService) {
		return new ProjektSerwis(repository, grupaRepository, config, taskGroupService);
	}
	
}
