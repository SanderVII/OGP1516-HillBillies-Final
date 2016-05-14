package hillbillies.expressions.booleanType;

import hillbillies.expressions.Expression;
import hillbillies.expressions.IExpression;
import hillbillies.part3.programs.SourceLocation;

public abstract class SingleBooleanExpression<E extends Expression> extends BooleanExpression
		implements IExpression<E>{

	public SingleBooleanExpression(E expression, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.expression = expression;
	}
	
	private final E expression;
	
	public E getExpression() {
		return this.expression;
	}
}
