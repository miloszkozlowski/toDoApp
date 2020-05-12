package pl.mihome.toDoApp.exeptions;

public class GroupDoneAgainstPolicyException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1553625822608905769L;

	public GroupDoneAgainstPolicyException() {
		super("Nie można zmienić stanu grupy gdy istnieje niewykonane zadanie. Zakończ wszystkie zadania w grupie.");
	}
	
}
