package pl.mihome.toDoApp.controller;


import java.time.LocalDateTime;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import pl.mihome.toDoApp.model.Zadanie;
import pl.mihome.toDoApp.model.ZadanieRepo;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyLong;


@WebMvcTest(ZadanieController.class)
@ActiveProfiles("integration")
class ZadanieControllerLightIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	//@Autowired - nie da się tutaj tego zastosowac, bo test obejmuje tylko wskazaną klasę - patrz @WebMvcTest
	@MockBean
	private ZadanieRepo repo;
	
	@Test
	void httpGet_returnsTaskById() throws Exception {
		//given
		when(repo.findById(anyLong())).thenReturn(
				Optional.of(new Zadanie("foo", LocalDateTime.now())));
	;
		
		//when + then
		
		mockMvc.perform(MockMvcRequestBuilders.get("/taski/666"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("foo")));
		
	}
}
