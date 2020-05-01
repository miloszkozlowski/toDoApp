package pl.mihome.toDoApp.controller;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import pl.mihome.toDoApp.model.Zadanie;
import pl.mihome.toDoApp.model.ZadanieRepo;

import org.assertj.core.api.Assertions;

//@ActiveProfiles("integration")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ZadanieControllerE2ETest {

	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private ZadanieRepo repo;
	
	@Test
	void httpGet_findAllTasks() {
		//given
		var beforeTest = repo.findAll().size();
		repo.save(new Zadanie("foo", LocalDateTime.now()));
		repo.save(new Zadanie("bar", LocalDateTime.now()));
		
		//when
		Zadanie[] result = restTemplate.getForObject("http://localhost:" + port + "/taski", Zadanie[].class);
		
		//then
		Assertions.assertThat(result.length).isEqualTo(beforeTest + 2);
	}

}
