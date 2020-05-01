package pl.mihome.toDoApp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("task") 					//task - to prefiks z pliku application.yml
public class ZadanieConfigurationProperties {
	
	private Template template;
	
	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}



	public static class Template {
		
		private boolean allowMultipleTasks;

		public boolean isAllowMultipleTasks() {
			return allowMultipleTasks;
		}

		public void setAllowMultipleTasks(boolean allowMultipleTasks) {
			this.allowMultipleTasks = allowMultipleTasks;
		}
		
	}

	
}
