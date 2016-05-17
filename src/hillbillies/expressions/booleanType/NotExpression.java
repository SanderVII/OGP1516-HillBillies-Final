package hillbillies.expressions.booleanType;

import hillbillies.part3.programs.SourceLocation;

public class NotExpression<E extends BooleanExpression> extends SingleBooleanExpression<E> {

	public NotExpression(E expression, SourceLocation sourceLocation) {
		super(expression, sourceLocation);
	}

	@Override
	public Boolean evaluate() {
		return (! (this.getExpression().evaluate()));
	}
}
