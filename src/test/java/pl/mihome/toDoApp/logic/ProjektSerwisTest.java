package pl.mihome.toDoApp.logic;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import pl.mihome.toDoApp.ZadanieConfigurationProperties;
import pl.mihome.toDoApp.model.Projekt;
import pl.mihome.toDoApp.model.ProjektKroki;
import pl.mihome.toDoApp.model.ProjektRepo;
import pl.mihome.toDoApp.model.ZadanieBaza;
import pl.mihome.toDoApp.model.ZadanieGrupa;
import pl.mihome.toDoApp.model.ZadanieGrupaRepo;
import pl.mihome.toDoApp.model.DTO.ZadanieGrupaOdczyt;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.anyLong;


class ProjektSerwisTest {

	@Test
	@DisplayName("Throws IllegalStateExeption kiedy w konfiguracji jest multiple tasks allowed jest false "
			+ "oraz w projekcie jest już grupa z niedokończonymi zadaniami.")
	void stworzGrupeZadan_noMultipleGroupsAllowed_and_undoneHroupExists_throwsIllegalStateExeption() {
		
		//given - czyli przygotowanie danych do testu
		var mockZadanieGrupaRepo = mock(ZadanieGrupaRepo.class);
		when(mockZadanieGrupaRepo.existsByDoneIsFalseAndProjekt_Id(anyLong())).thenReturn(true);
		
		var mockZadanieConfigurationPropTemplate = mock(ZadanieConfigurationProperties.Template.class);
		when(mockZadanieConfigurationPropTemplate.isAllowMultipleTasks()).thenReturn(false);
		
		var mockZadanieConfigurationProp = mock(ZadanieConfigurationProperties.class);
		when(mockZadanieConfigurationProp.getTemplate()).thenReturn(mockZadanieConfigurationPropTemplate);
		
		//system under test:
		var toTest = new ProjektSerwis(null, mockZadanieGrupaRepo, mockZadanieConfigurationProp, null);
		
		
		
		//when + then
//		Assertions.assertThatThrownBy(() -> {
//			toTest.stworzGrupeZadan(0L, LocalDateTime.now());	
//		}).isInstanceOf(IllegalStateException.class);
		
		//lub jeszcze łatwiej:
//		Assertions.assertThatIllegalStateException().isThrownBy(() -> toTest.stworzGrupeZadan(0L, LocalDateTime.now()));
		
		//lub jeszcze pewniej, bo ze sprawdzeniem treści message wyjątku:
//		Assertions.assertThatThrownBy(() -> toTest.stworzGrupeZadan(0L, LocalDateTime.now())).hasMessageContaining("tylko jedna");
		
		//lub czytelniej i wyklucza, że padnie IllegalStateexception inny niż ten pożądany:
		//when - wołanie testowanej metody
		var ex = Assertions.catchThrowable(() -> toTest.stworzGrupeZadan(0L, LocalDateTime.now()));
		
		//then - sprawdzamy czy metoda zrobiła tak jak chcieliśmy
		Assertions.assertThat(ex)
			.isInstanceOf(IllegalStateException.class)
			.hasMessageContaining("tylko jedna");
		
	}
	
	@Test
	@DisplayName("Throws IllegalArgumentExeption kiedy nie ma projektu o podanym ID")
	void stworzGrupeZadan_noProjectFoundById_and_noGroupsById_throwsIllegalArgumentExeption() {
		
		//given - czyli przygotowanie danych do testu
		var mockZadanieGrupaRepo = mock(ZadanieGrupaRepo.class);
		when(mockZadanieGrupaRepo.existsByDoneIsFalseAndProjekt_Id(anyLong())).thenReturn(false);
		
		var mockProjektRepo = mock(ProjektRepo.class);
		when(mockProjektRepo.findById(anyLong())).thenReturn(Optional.empty());
		
		var mockZadanieConfigurationPropTemplate = mock(ZadanieConfigurationProperties.Template.class);
		when(mockZadanieConfigurationPropTemplate.isAllowMultipleTasks()).thenReturn(false);
		
		var mockZadanieConfigurationProp = mock(ZadanieConfigurationProperties.class);
		when(mockZadanieConfigurationProp.getTemplate()).thenReturn(mockZadanieConfigurationPropTemplate);
		
		//system under test:
		var toTest = new ProjektSerwis(mockProjektRepo, mockZadanieGrupaRepo, mockZadanieConfigurationProp, null);
		
		
		//when - wołanie testowanej metody
		var ex = Assertions.catchThrowable(() -> toTest.stworzGrupeZadan(0L, LocalDateTime.now()));
		
		//then - sprawdzamy czy metoda zrobiła tak jak chcieliśmy
		Assertions.assertThat(ex)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("Nie ma takiego");
		
	}
	
	@Test
	@DisplayName("Throws IllegalArgumentExeption kiedy konfiguracja mówi, że dozwolona jest jedna grupa"
			+ "ale nie ma żadnej grupy i nie ma takiego projektu")
	void stworzGrupeZadan_noProjectFoundById_throwsIllegalArgumentExeption() {
		
		//given - czyli przygotowanie danych do testu
		var mockProjektRepo = mock(ProjektRepo.class);
		when(mockProjektRepo.findById(anyLong())).thenReturn(Optional.empty());
		
		var mockZadanieConfigurationPropTemplate = mock(ZadanieConfigurationProperties.Template.class);
		when(mockZadanieConfigurationPropTemplate.isAllowMultipleTasks()).thenReturn(true);
		
		var mockZadanieConfigurationProp = mock(ZadanieConfigurationProperties.class);
		when(mockZadanieConfigurationProp.getTemplate()).thenReturn(mockZadanieConfigurationPropTemplate);
		
		//system under test:
		var toTest = new ProjektSerwis(mockProjektRepo, null, mockZadanieConfigurationProp, null);
		
		
		//when - wołanie testowanej metody
		var ex = Assertions.catchThrowable(() -> toTest.stworzGrupeZadan(0L, LocalDateTime.now()));
		
		//then - sprawdzamy czy metoda zrobiła tak jak chcieliśmy
		Assertions.assertThat(ex)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("Nie ma takiego");
		
	}
	
	@Test
	@DisplayName("Zapisuje nową grupę tasków poprawnie")
	void stworzGrupeZadan_poprawnie() {
		
		//given - czyli przygotowanie danych do testu
		var today = LocalDate.now().atStartOfDay();
		//and
		var projekt = projektZe("bar", Set.of(-1, -2));
		var mockProjektRepo = mock(ProjektRepo.class);
		when(mockProjektRepo.findById(anyLong())).thenReturn(Optional.of(projekt));
		//and
		var inMemoryGroupRepo = new InMemoryGroupRepository();	
		var serviceWithInMemRepo = new ZadanieGrupaSerwis(inMemoryGroupRepo, null);
		int countBeforeCall = inMemoryGroupRepo.count();
		//and
		var mockZadanieConfigurationPropTemplate = mock(ZadanieConfigurationProperties.Template.class);
		when(mockZadanieConfigurationPropTemplate.isAllowMultipleTasks()).thenReturn(true);
		
		var mockZadanieConfigurationProp = mock(ZadanieConfigurationProperties.class);
		when(mockZadanieConfigurationProp.getTemplate()).thenReturn(mockZadanieConfigurationPropTemplate);
		
		//system under test:
		var toTest = new ProjektSerwis(mockProjektRepo, inMemoryGroupRepo, mockZadanieConfigurationProp, serviceWithInMemRepo);
		
		//when
		ZadanieGrupaOdczyt result = toTest.stworzGrupeZadan(1L, today);
		
		//then
		Assertions.assertThat(countBeforeCall).isEqualTo(inMemoryGroupRepo.count()-1);
		Assertions.assertThat(result).hasFieldOrPropertyWithValue("description", "bar");
		Assertions.assertThat(result.getDeadline()).isEqualTo(today.minusDays(1));
		Assertions.assertThat(result.getZadania()).allMatch(zad -> zad.getDescription().equals("foo"));
		
	}
	
	Projekt projektZe(String description, Set<Integer> daysToDeadline) {
		List<ProjektKroki> kroki = daysToDeadline.stream()
				.map(dtd -> {
					var krok = mock(ProjektKroki.class);
					when(krok.getDescription()).thenReturn("foo");
					when(krok.getDaysToDeadline()).thenReturn(dtd);
					return krok;
				})
				.collect(Collectors.toList());
		
		var proj = mock(Projekt.class);
		when(proj.getDescription()).thenReturn(description);
		when(proj.getProjektKroki()).thenReturn(kroki);
		
		return proj;
	}
	
	private static class InMemoryGroupRepository implements ZadanieGrupaRepo {
			
		private Long index = 0L;
		private Map<Long, ZadanieGrupa> repo = new HashMap<Long, ZadanieGrupa>();
		
		public int count() {
			return repo.size();
		}
		
		@Override
		public ZadanieGrupa save(ZadanieGrupa zadanieGrupa) {
			if(zadanieGrupa.getId() == null) {
				try {
					
					var field = ZadanieBaza.class.getDeclaredField("id");
					field.setAccessible(true);
					field.set(zadanieGrupa, ++index);
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
			repo.put(zadanieGrupa.getId(), zadanieGrupa);
			return zadanieGrupa;
		}
		
		@Override
		public Optional<ZadanieGrupa> findById(Long id) {
			return Optional.ofNullable(repo.get(id));
		}
		
		@Override
		public List<ZadanieGrupa> findAll() {
			return new ArrayList<ZadanieGrupa>(repo.values());
		}
		
		@Override
		public boolean existsByDoneIsFalseAndProjekt_Id(Long projektId) {
			var result = repo.values().stream()
			.filter(gr -> gr.isDone())
			.filter(gr -> gr.getProjekt() != null && gr.getProjekt().getId() == projektId)
			.findAny();

			return result.isPresent();
		}

		@Override
		public boolean existsByDescription(String description) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
	

}
