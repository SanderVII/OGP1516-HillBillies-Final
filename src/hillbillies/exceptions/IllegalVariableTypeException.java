package hillbillies.exceptions;

/**
 * This exception is thrown whenever a readVariableExpression evaluates to an inappropriate type.
 * 
 * @author Thomas Vranken
 *
 */
public class IllegalVariableTypeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IllegalVariableTypeException() {
		super();
	}
	
	public IllegalVariableTypeException(String message) {
		super(message);
	}

	public IllegalVariableTypeException(Throwable cause) {
		super(cause);
	}

	public IllegalVariableTypeException(String message, Throwable cause) {
		super(message, cause);
	}

}
