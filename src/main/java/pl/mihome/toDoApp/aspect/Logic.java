package pl.mihome.toDoApp.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;


import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

@Component
@Aspect
public class Logic {
	private final Timer projectCreateGroupTimer;
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(Logic.class);
	
	public Logic(final MeterRegistry reg) {
		this.projectCreateGroupTimer = reg.timer("logic.project.createGroup");
	}
	
	@Pointcut("execution(* pl.mihome.toDoApp.logic.ProjektSerwis.stworzGrupeZadan(..))")
	void projectServiceCreateGroup() {
		
	}
	
	// @Before("execution(* pl.mihome.toDoApp.logic.ProjektSerwis.stworzGrupeZadan(..))") - gdyby nie było wyżej @Pointcut
	@Before("projectServiceCreateGroup()")
	void logBeforeMethod(JoinPoint jp) { //ProceedingJoinPoint tylko w @Around
		log.info("Before {} with {}", jp.getSignature().getName(), jp.getArgs());
	}
	
	// @Around("execution(* pl.mihome.toDoApp.logic.ProjektSerwis.stworzGrupeZadan(..))") - gdyby nie było wyżej @Pointcut
	@Around("projectServiceCreateGroup()")
	Object aroundProjectCreateGroup(ProceedingJoinPoint jp) {
		return projectCreateGroupTimer.record(() -> {
			try {
				return jp.proceed();
			} catch (Throwable e) {
				if(e instanceof RuntimeException)
					throw (RuntimeException) e;
				else
					throw new RuntimeException();
			}
		});
	}	
}
