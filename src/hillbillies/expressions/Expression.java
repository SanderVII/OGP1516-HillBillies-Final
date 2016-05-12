package hillbillies.expressions;

import hillbillies.model.*;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.statements.ExpressionStatement;
import hillbillies.statements.Statement;

public abstract class Expression {


	public Expression(SourceLocation sourceLocation) {
		this.setSourceLocation(sourceLocation);
	}
	
	public abstract Object evaluate();
	
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
	
	public void setSourceLocation(SourceLocation sourceLocation) {
		this.sourceLocation = sourceLocation;
	}
	
	private SourceLocation sourceLocation;
	
	public ExpressionStatement getStatement() {
		return this.statement;
	}
	
	public boolean hasStatement() {
		return this.statement != null;
	}
	
	/**
	 * NOTE: expressions are created without a statement.
	 * setExpression assumes this and creates a bidirectional association.
	 */
	public void setStatement(ExpressionStatement statement) throws IllegalArgumentException {
		assert (this.statement.getExpression() == this);
		this.statement = statement;
	}
	
	private ExpressionStatement statement;
	
	/**
	 * Helper method for quickly getting the executing unit.
	 * @throws	IllegalArgumentException
	 * 			No unit connected.
	 */
	public Unit getUnit() {
		Unit result = this.getStatement().getTask().getUnit();
		if (result == null)
			throw new IllegalStateException("not connected to unit.");
		return result;
	}
}
