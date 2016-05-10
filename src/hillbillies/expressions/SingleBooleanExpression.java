package hillbillies.expressions;

import hillbillies.part3.programs.SourceLocation;

public abstract class SingleBooleanExpression extends BooleanExpression {

	public SingleBooleanExpression(Expression expression, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.setExpression(expression);
	}

	private Expression expression;
	
	public Expression getExpression() {
		return this.expression;
	}
	
	protected void setExpression(Expression expression) {
		this.expression = expression;
	}

}
