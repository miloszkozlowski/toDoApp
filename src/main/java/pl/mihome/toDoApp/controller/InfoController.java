package pl.mihome.toDoApp.controller;

import javax.annotation.security.RolesAllowed;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.mihome.toDoApp.ZadanieConfigurationProperties;

@RestController
@RequestMapping("/info")
public class InfoController {
	
	private ZadanieConfigurationProperties myAppConfig;
	private DataSourceProperties dataSource;
	
	
public InfoController(ZadanieConfigurationProperties myAppConfig, DataSourceProperties dataSource) {
		this.myAppConfig = myAppConfig;
		this.dataSource = dataSource;
	}

/*
 * 	poniższe jest zamienione na stworzoną klasę ZadanieConfig...
 * 	dzięki czemu nie trzeba wpisywać ścieżki właściwości, a jest
 * 	ona umieszczona w klasie, która jest tutaj wstrzykiwana konsktruktorem
 *
 *	@Value("${task.allowMultipleTasksFromTemplate}")
 *	private Boolean prop;
*/	
	@RolesAllowed("ROLE_ADMIN") //adnotacja pochodząca z JEE
	@GetMapping("/url")
	String showDBUrl() {
		return dataSource.getUrl();
	}
	
	@Secured("ROLE_ADMIN") //admontacja Springowa
	@GetMapping("/prop")
	Boolean showProp() {
		return myAppConfig.getTemplate().isAllowMultipleTasks();
	}

} 
