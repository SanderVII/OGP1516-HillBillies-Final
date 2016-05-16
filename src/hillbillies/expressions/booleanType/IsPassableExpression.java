package hillbillies.expressions.booleanType;

import hillbillies.expressions.IPositionVariableExpression;
import hillbillies.part3.programs.SourceLocation;

public class IsPassableExpression<E extends IPositionVariableExpression> 
		extends SingleBooleanPositionExpression<E> {


	public IsPassableExpression(E position, SourceLocation sourceLocation) {
		super(position, sourceLocation);
	}

	@Override
	public Boolean evaluate() {
		return this.getUnit().getWorld().getCube(this.getExpressionEvaluation()).isPassable();
	}
}
