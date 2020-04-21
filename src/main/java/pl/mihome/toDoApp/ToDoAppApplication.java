package pl.mihome.toDoApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

// {code}implements RepositoryRestConfigurer{/code} powinno być gdy chcę korzystać z dostepu do danych bezpośrednio
// z RepositoryRestResource

@SpringBootApplication
public class ToDoAppApplication  {

	public static void main(String[] args) {
		SpringApplication.run(ToDoAppApplication.class, args);
	}

	@Bean
	Validator validator() {
		return new LocalValidatorFactoryBean();
	}

	/* to jest konfiguracja dostępu do danych bezpośrednio z repozytorium implementując RepositoryRestConfigurer
	@Override
	public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
		validatingListener.addValidator("beforeCreate", validator());
		validatingListener.addValidator("beforeSave", validator());
	}
	*/
	

}
