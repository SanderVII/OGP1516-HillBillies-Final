package hillbillies.expressions.booleanType;

import hillbillies.exceptions.IllegalVariableTypeException;
import hillbillies.expressions.IPositionVariableExpression;
import hillbillies.part3.programs.SourceLocation;

public class IsSolidExpression<E extends IPositionVariableExpression> 
		extends SingleBooleanPositionExpression<E> {

	public IsSolidExpression(E position, SourceLocation sourceLocation) {
		super(position, sourceLocation);
	}

	@Override
	public Boolean evaluate() throws IllegalVariableTypeException {
		return ! (this.getUnit().getWorld().getCube(this.getExpressionEvaluation()).isPassable());
	}
}
