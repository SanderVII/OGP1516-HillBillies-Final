package hillbillies.expressions.positionType;

import hillbillies.expressions.unitType.UnitExpression;
import hillbillies.part3.programs.SourceLocation;

public class PositionOfExpression<E extends UnitExpression> 
		extends ExtendedPositionExpression<E> {

	public PositionOfExpression(E unit, SourceLocation sourceLocation) {
		super(unit,sourceLocation);
	}

	@Override
	public int[] evaluate() {
		return this.getExpression().evaluate().getCubeCoordinates();
	}
}
