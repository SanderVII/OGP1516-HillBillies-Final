package hillbillies.statements;

public interface ISuperStatement<S extends Statement> {
	
	/*
	 * TODO Is it possible for a statement to have a non-expression statement as
	 * its superstatement? YES => use this interface for general purpose 
	 * and another for Expression statements.
	 */
	
	public abstract S getSuperStatement();
	
	public default boolean hasSuperStatement() {
		return this.getSuperStatement() != null;
	}
	
	abstract void setSuperStatement(S statement);
}
