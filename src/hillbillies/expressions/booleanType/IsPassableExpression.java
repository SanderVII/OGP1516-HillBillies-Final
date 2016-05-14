package hillbillies.expressions.booleanType;

import hillbillies.expressions.positionType.PositionExpression;
import hillbillies.part3.programs.SourceLocation;

public class IsPassableExpression<E extends PositionExpression> 
		extends SingleBooleanPositionExpression<E> {


	public IsPassableExpression(E position, SourceLocation sourceLocation) {
		super(position, sourceLocation);
	}

	@Override
	public Boolean evaluate() {
		return this.getUnit().getWorld().getCube(this.getExpression().evaluate()).isPassable();
	}
}
