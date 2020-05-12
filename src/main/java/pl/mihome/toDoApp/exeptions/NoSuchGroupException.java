package pl.mihome.toDoApp.exeptions;


public class NoSuchGroupException extends RuntimeException {

	private static final long serialVersionUID = 7369954764683070402L;

	public NoSuchGroupException() {
		super("Podana grupa nie istnieje");
	}
}
