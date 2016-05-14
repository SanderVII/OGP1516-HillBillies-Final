package hillbillies.expressions.booleanType;

import hillbillies.expressions.positionType.PositionExpression;
import hillbillies.part3.programs.SourceLocation;

public abstract class SingleBooleanPositionExpression<E extends PositionExpression> 
		extends SingleBooleanExpression<E> {

	public SingleBooleanPositionExpression(E position, SourceLocation sourceLocation) {
		super(position, sourceLocation);
	}
}
