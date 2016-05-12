package hillbillies.statements;

import hillbillies.expressions.Expression;
import hillbillies.part3.programs.SourceLocation;

public abstract class ExpressionStatement extends Statement {

	public ExpressionStatement(Expression expression, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.setExpression(expression);
		expression.setStatement(this);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}
	
	public Expression getExpression() {
		return this.expression;
	}
	
	/*
	 * NOTE: expressions are created without a statement.
	 * setExpression assumes this and creates a bidirectional association.
	 */
	protected void setExpression(Expression expression) throws IllegalArgumentException {
		this.expression = expression;
		expression.setStatement(this);
	}
	
	private Expression expression;

}
