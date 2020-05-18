package pl.mihome.toDoApp.model.customEvents;

import java.time.Clock;

import pl.mihome.toDoApp.model.Zadanie;

public class ZadanieUndone extends ZadanieEvent {
	
	ZadanieUndone(Zadanie source) {
		super(source.getId(), Clock.systemDefaultZone());
	}

}
