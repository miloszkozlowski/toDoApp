package pl.mihome.toDoApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import pl.mihome.toDoApp.model.Zadanie;
import pl.mihome.toDoApp.model.ZadanieRepo;

@Configuration
public class TestConfiguration {
	
	@Bean
	@Primary
	@Profile("!integration")
	DataSource e2eTestDataSource() {
		var result = new DriverManagerDataSource("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "");
		result.setDriverClassName("org.h2.Driver");
		return result;
	}
	
	
	@Bean //dodawanie do konfiguracji testowej podmianki repozytorium Zadanie - samo wstawienie @Bean daje konflikt, bo pojawiają się dwa miejsca inicjacji Bean TaskRepo
	@Primary
	@Profile("integration") //unikamy konfliktu, bo nadajemy priorytet użyty jest profil integration. Można użyć @Priority, ale to
							//wtedy wymusza używanie zawsze tego Bean w testach, a nie zawsze się tego chce, bo np. robiąc testy integracyjne chce się używać DB.
	ZadanieRepo testZadanieRepo() {
		return new ZadanieRepo() {
			
			Map<Long, Zadanie> inMemoryRepo = new HashMap<Long, Zadanie>();
			
			
			@Override
			public Zadanie save(Zadanie entity) {
				try {
				var field = Zadanie.class.getSuperclass().getDeclaredField("id");
				field.setAccessible(true);
				field.set(entity, Long.valueOf(inMemoryRepo.size()));
				inMemoryRepo.put(Long.valueOf(inMemoryRepo.size()), entity);
				return entity;
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
			
			@Override
			public Optional<Zadanie> findById(Long id) {
				return Optional.ofNullable(inMemoryRepo.get(id));
			}
			
			@Override
			public Page<Zadanie> findAll(Pageable page) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<Zadanie> findAll() {
				
				return new ArrayList<Zadanie>(inMemoryRepo.values());
			}
			
			@Override
			public boolean existsById(Long id) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean existsByDoneIsFalseAndGrupa_Id(Long taskGroupId) {
				// TODO Auto-generated method stub
				return false;
			}
		};
	}

}
