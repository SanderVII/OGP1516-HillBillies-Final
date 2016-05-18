package hillbillies.exceptions;

public class MaxIterationException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public MaxIterationException() {
		super();
	}

	public MaxIterationException(String message) {
		super(message);
	}

	public MaxIterationException(Throwable cause) {
		super(cause);
	}

	public MaxIterationException(String message, Throwable cause) {
		super(message, cause);
	}
}
