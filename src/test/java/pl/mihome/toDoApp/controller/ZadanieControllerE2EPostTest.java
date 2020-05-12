package pl.mihome.toDoApp.controller;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;

import pl.mihome.toDoApp.model.Zadanie;
import pl.mihome.toDoApp.model.ZadanieRepo;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ZadanieControllerE2EPostTest {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate trs;
	
	@Autowired
	private ZadanieRepo repo;
	
	@Test
	void httpPost_zapiszZadanie() {
		//given
		var beforeTest = repo.findAll().size();
		var noweZadanie = new Zadanie("foo", LocalDateTime.now());
		var request = new HttpEntity<Zadanie>(noweZadanie);
		
		
		//when
		//Zadanie result = trs.postForObject("http://localhost:" + port + "/taski", request, Zadanie.class);
		var result = trs.postForLocation("http://localhost:" + port + "/taski", request);
		
		//then
		Assertions.assertThat(repo.findAll().size()-1).isEqualTo(beforeTest);
		Assertions.assertThat(result.getPath()).containsIgnoringCase("/taski/");
		
	}

}
