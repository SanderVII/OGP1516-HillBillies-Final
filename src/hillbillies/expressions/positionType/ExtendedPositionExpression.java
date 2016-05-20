package hillbillies.expressions.positionType;

import hillbillies.expressions.Expression;
import hillbillies.part3.programs.SourceLocation;

public abstract class ExtendedPositionExpression<E extends Expression> extends PositionExpression {

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
	
	public ExtendedPositionExpression<E> clone() throws CloneNotSupportedException {
		@SuppressWarnings("unchecked")
		ExtendedPositionExpression<E> cloned = (ExtendedPositionExpression<E>) super.clone();
		@SuppressWarnings("unchecked")
		E clonedExpression = (E) this.getExpression().clone();
		clonedExpression.setSuperText(cloned);
		cloned.setExpression(clonedExpression);
		return cloned;
	}

}
