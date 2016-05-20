package hillbillies.expressions.booleanType;

import hillbillies.expressions.Expression;
import hillbillies.part3.programs.SourceLocation;

public abstract class SingleBooleanExpression<E extends Expression> extends BooleanExpression {

	public SingleBooleanExpression(E expression, SourceLocation sourceLocation) {
		super(sourceLocation);
		expression.setSuperText(this);
		this.setExpression(expression);
	}
	
	private E expression;
	
	public E getExpression() {
		return this.expression;
	}
	
	private void setExpression(E expression) {
		this.expression = expression;
	}
	
	public SingleBooleanExpression<E> clone() throws CloneNotSupportedException {
		@SuppressWarnings("unchecked")
		SingleBooleanExpression<E> cloned = (SingleBooleanExpression<E>) super.clone();
		@SuppressWarnings("unchecked")
		E clonedExpression = (E) this.getExpression().clone();
		clonedExpression.setSuperText(cloned);
		cloned.setExpression(clonedExpression);
		return cloned;
	}

}
