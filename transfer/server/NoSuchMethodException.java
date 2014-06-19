package transfer.server;

public class NoSuchMethodException extends Exception {

	// ID generated through Eclipse
	private static final long serialVersionUID = 6786120257280912939L;
	
	public NoSuchMethodException(String methodName) {
		super("Method '" + methodName + "' does not exist.");
	}

}
