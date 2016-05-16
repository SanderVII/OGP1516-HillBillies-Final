package hillbillies.expressions.booleanType;

import hillbillies.exceptions.IllegalVariableTypeException;
import hillbillies.expressions.IBooleanVariableExpression;
import hillbillies.part3.programs.SourceLocation;

public class NotExpression<E extends IBooleanVariableExpression> extends SingleBooleanExpression<E> {

	public NotExpression(E expression, SourceLocation sourceLocation) {
		super(expression, sourceLocation);
	}
	
	@Override
	protected Boolean getExpressionEvaluation() throws IllegalVariableTypeException {
		return (boolean)super.getExpressionEvaluation();
	}

	@Override
	public Boolean evaluate() {
		return (! (this.getExpressionEvaluation()));
	}
}
