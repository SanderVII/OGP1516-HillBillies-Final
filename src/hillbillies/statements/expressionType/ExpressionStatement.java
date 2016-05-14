package hillbillies.statements.expressionType;

import hillbillies.expressions.Expression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.statements.Statement;

public abstract class ExpressionStatement<E extends Expression>
		extends Statement {

	public ExpressionStatement(E expression, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.setExpression(expression);
		expression.setSuperText(this);
	}
	
	public E getExpression() {
		return this.expression;
	}
	
	/*
	 * NOTE: expressions are created without a statement.
	 * setExpression assumes this and creates a bidirectional association.
	 */
	protected void setExpression(E expression) throws IllegalArgumentException {
		this.expression = expression;
	}
	
	private E expression;

}
