package hillbillies.exceptions;

public class TerminatedException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public TerminatedException() {
		super();
	}

	public TerminatedException(String message) {
		super(message);
	}

	public TerminatedException(Throwable cause) {
		super(cause);
	}

	public TerminatedException(String message, Throwable cause) {
		super(message, cause);
	}
}
