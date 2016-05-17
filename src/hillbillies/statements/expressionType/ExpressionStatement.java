package hillbillies.statements.expressionType;

import hillbillies.expressions.Expression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.statements.Statement;

public abstract class ExpressionStatement<E extends Expression>
		extends Statement {

	public ExpressionStatement(E expression, SourceLocation sourceLocation) {
		super(sourceLocation);
		expression.setSuperText(this);
		this.setExpression(expression);
	}
	
	public E getExpression() {
		return this.expression;
	}

	protected void setExpression(E expression) throws IllegalArgumentException {
		assert (expression.getSuperText() == this);
		this.expression = expression;
	}
	
	private E expression;

}
