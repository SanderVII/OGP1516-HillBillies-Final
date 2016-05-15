package hillbillies.expressions.booleanType;

import hillbillies.expressions.IBooleanVariableExpression;
import hillbillies.part3.programs.SourceLocation;

public class OrExpression<E extends IBooleanVariableExpression> 
		extends CombinedBooleanExpression<E> {

	public OrExpression(E left, E right, SourceLocation sourceLocation) {
		super(left, right, sourceLocation);
	}
	
	public Boolean evaluate() throws IllegalArgumentException {
		boolean leftBoolean = this.getLeftExpressionEvaluation();
		boolean rightBoolean = this.getRightExpressionEvaluation();
		if (leftBoolean || rightBoolean)
			return true;
		else
			return false;
	}
}
