package hillbillies.expressions.positionType;

import hillbillies.expressions.IExpression;
import hillbillies.part3.programs.SourceLocation;

public abstract class ExtendedPositionExpression<E extends IExpression> extends PositionExpression {

	public ExtendedPositionExpression(E expression, SourceLocation sourceLocation) {
		super(sourceLocation);
		expression.setSuperText(this);
		this.setExpression(expression);
	}
	
	private E expression;
	
	public E getExpression() {
		return this.expression;
	}
	
	protected void setExpression(E expression) {
		this.expression = expression;
	}

}
