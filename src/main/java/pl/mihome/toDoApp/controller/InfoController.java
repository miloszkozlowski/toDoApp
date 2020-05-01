package pl.mihome.toDoApp.controller;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.mihome.toDoApp.ZadanieConfigurationProperties;

@RestController
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
	@GetMapping("/info/url")
	String showDBUrl() {
		return dataSource.getUrl();
	}
	
	@GetMapping("/info/prop")
	Boolean showProp() {
		return myAppConfig.getTemplate().isAllowMultipleTasks();
	}

} 
