package hillbillies.statements;

public interface ISuperStatement {
	
	public abstract Statement getSuperStatement();
	
	public default boolean hasSuperStatement() {
		return this.getSuperStatement() != null;
	}
	
	abstract void setSuperStatement(Statement statement);
}
