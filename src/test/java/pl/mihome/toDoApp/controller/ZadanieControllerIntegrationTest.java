package pl.mihome.toDoApp.controller;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import pl.mihome.toDoApp.model.Zadanie;
import pl.mihome.toDoApp.model.ZadanieRepo;

/*
 * Tutaj też jest używany {@code @SpringBootTest} ale bez {@code webEnvironment = }, więc przyjmuje wartość domyślną
 * {@code WebEnvironment.MOCK} przez co nie stawia serwera, a korzystając z {@code @AutoConfigureMockMvc} można od razu 
 * testować warstwę web bez stawiania serwera. 
 */
@SpringBootTest
@ActiveProfiles("integration")
@AutoConfigureMockMvc 
public class ZadanieControllerIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ZadanieRepo repo;
	
	@Test
	void httpGet_returnsTaskById() throws Exception {
		//given
		repo.save(new Zadanie("foo", LocalDateTime.now()));
		Long id = repo.save(new Zadanie("bar", LocalDateTime.now())).getId();
		
		//when + then
		
		mockMvc.perform(MockMvcRequestBuilders.get("/taski/" + id))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		

		
		
	}

}
