package hillbillies.expressions.booleanType;

import hillbillies.expressions.IPositionVariableExpression;
import hillbillies.expressions.positionType.PositionExpression;
import hillbillies.part3.programs.SourceLocation;

public abstract class SingleBooleanPositionExpression<E extends IPositionVariableExpression> 
		extends SingleBooleanExpression<E> {

	public SingleBooleanPositionExpression(E position, SourceLocation sourceLocation) {
		super(position, sourceLocation);
	}
	
	protected int[] getExpressionEvaluation() {
		return (int[]) super.getExpressionEvaluation();
	}
}
