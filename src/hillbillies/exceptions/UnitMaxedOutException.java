package hillbillies.exceptions;

public class UnitMaxedOutException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnitMaxedOutException() {
		super();
	}

	public UnitMaxedOutException(String message) {
		super(message);
	}

	public UnitMaxedOutException(Throwable cause) {
		super(cause);
	}

	public UnitMaxedOutException(String message, Throwable cause) {
		super(message, cause);
	}

}
