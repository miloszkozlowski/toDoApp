package pl.mihome.toDoApp.logic;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


import pl.mihome.toDoApp.ZadanieConfigurationProperties;
import pl.mihome.toDoApp.exeptions.NoSuchGroupException;
import pl.mihome.toDoApp.model.Projekt;
import pl.mihome.toDoApp.model.ProjektRepo;
import pl.mihome.toDoApp.model.ZadanieGrupaRepo;
import pl.mihome.toDoApp.model.DTO.ProjektZapis;
import pl.mihome.toDoApp.model.DTO.ZadanieGrupaOdczyt;
import pl.mihome.toDoApp.model.DTO.ZadanieGrupaZapis;
import pl.mihome.toDoApp.model.DTO.ZadanieWGrupieZapis;

// @Service też jest Service, ale skonfigurowany jako Bean w :logicConfiguration - robi się tak, żeby móc się pozbyć Springa bez problemu
public class ProjektSerwis {

	ProjektRepo repository;
	ZadanieGrupaRepo grupaRepository;
	ZadanieConfigurationProperties config;
	ZadanieGrupaSerwis taskGroupService;
	
	public ProjektSerwis(ProjektRepo repository, ZadanieGrupaRepo grupaRepository, ZadanieConfigurationProperties config, ZadanieGrupaSerwis taskGroupService) {
		this.repository = repository;
		this.grupaRepository = grupaRepository;
		this.config = config;
		this.taskGroupService = taskGroupService;
	}
	
	public List<Projekt> pokazWszystkie() {
		return repository.findAll();
	}
	
	public Projekt zapisz(ProjektZapis entity) {
		return repository.save(entity.zapiszDoProjektu());
	}

	public ZadanieGrupaOdczyt stworzGrupeZadan(Long idProjektu, LocalDateTime deadline) {
		if(!config.getTemplate().isAllowMultipleTasks() && grupaRepository.existsByDoneIsFalseAndProjekt_Id(idProjektu))
			throw new IllegalStateException("Dozwolona jest tylko jedna nie wykonana grupa w projekcie");
		
		ZadanieGrupaOdczyt result = repository.findById(idProjektu)
			.map(projekt -> {
								var targetGroup = new ZadanieGrupaZapis();
								targetGroup.setDescription(projekt.getDescription());
								targetGroup.setZadania(
										projekt.getProjektKroki().stream()
											.map(projektKroki -> {
													var zadanie = new ZadanieWGrupieZapis();
													zadanie.setDescription(projektKroki.getDescription()); 
													zadanie.setDeadline(deadline.plusDays(projektKroki.getDaysToDeadline()));
													return zadanie;
											}
										).collect(Collectors.toList())
								);
								return taskGroupService.zapiszZadanieGrupa(targetGroup, projekt);
							}).orElseThrow(() -> new NoSuchGroupException());
		
		return result;
			
	}
	 
}
