package hillbillies.expressions.booleanType;

import hillbillies.expressions.Expression;
import hillbillies.part3.programs.SourceLocation;

public abstract class SingleBooleanExpression<E extends Expression> extends BooleanExpression {

	public SingleBooleanExpression(E expression, SourceLocation sourceLocation) {
		super(sourceLocation);
		expression.setSuperText(this);
		this.expression = expression;
	}
	
	private final E expression;
	
	public E getExpression() {
		return this.expression;
	}

}
