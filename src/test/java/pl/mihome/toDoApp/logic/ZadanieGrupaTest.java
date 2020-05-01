package pl.mihome.toDoApp.logic;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import pl.mihome.toDoApp.model.ZadanieGrupa;
import pl.mihome.toDoApp.model.ZadanieGrupaRepo;
import pl.mihome.toDoApp.model.ZadanieRepo;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;


import org.assertj.core.api.Assertions;

import static org.mockito.ArgumentMatchers.anyLong;

class ZadanieGrupaTest {

	
	@Test
	@DisplayName("Test if IllegalStateEx is thrown when group has unfinished tasks")
	void toggleGroup_whenThereAreUnfinishedTasksInGroup() {
		//given
		var mockZadanieRepo = returnsZadanieRepo(true);
		
		//under test:
		var toTest = new ZadanieGrupaSerwis(null, mockZadanieRepo);
		
		//when
		var ex = Assertions.catchThrowable(() -> toTest.toggleGroup(5L));
		
		//then
		Assertions.assertThat(ex)
		.isInstanceOf(IllegalStateException.class)
		.hasMessageContaining("niewykonane");
				
	}
	
	@Test
	@DisplayName("Test if throws IllegalArgumentEx when pointed task group does not exist")
	void toggleGroup_whenPointedGroupIdIsMissing() {
		//given
		var mockZadanieRepo = returnsZadanieRepo(false);
		
		//and
		var mockZadanieGrupaRepo = returnsZadanieGrupaRepo(Optional.empty());
		
		//under test:
		var toTest = new ZadanieGrupaSerwis(mockZadanieGrupaRepo, mockZadanieRepo);
		
		//when
		var ex = Assertions.catchThrowable(() -> toTest.toggleGroup(5L));
		
		//then
		Assertions.assertThat(ex)
		.isInstanceOf(IllegalArgumentException.class)
		.hasMessageContaining("takiej");
		
	}
	
	@Test
	@DisplayName("test when all is correct")
	void toggleGroup_isOk() {
		//given
		var mockZadanieRepo = returnsZadanieRepo(false);
		
		//and
		var grupa = new ZadanieGrupa("foo", null);
		var toggleBeforeTest = grupa.isDone();		
		var mockZadanieGrupaRepo = returnsZadanieGrupaRepo(Optional.of(grupa));
		
		//under test:
		var toTest = new ZadanieGrupaSerwis(mockZadanieGrupaRepo, mockZadanieRepo);
		
		//when
		toTest.toggleGroup(5L);
		
		//then
		
		Assertions.assertThat(grupa.isDone()).isEqualTo(!toggleBeforeTest);
	}
	

	
	private ZadanieGrupaRepo returnsZadanieGrupaRepo(Optional<ZadanieGrupa> findById) {
		var mockZadanieGrupaRepo = mock(ZadanieGrupaRepo.class);
		when(mockZadanieGrupaRepo.findById(anyLong())).thenReturn(findById);
		return mockZadanieGrupaRepo;
	}
	
	private ZadanieRepo returnsZadanieRepo(boolean existsByDoneIsFalseAndGrupa_Id) {
		var mockZadanieRepo = mock(ZadanieRepo.class);
		when(mockZadanieRepo.existsByDoneIsFalseAndGrupa_Id(anyLong())).thenReturn(existsByDoneIsFalseAndGrupa_Id);
		return mockZadanieRepo;
	}

}
