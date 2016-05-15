package hillbillies.expressions.positionType;

import hillbillies.exception.IllegalVariableTypeException;
import hillbillies.expressions.IUnitVariableExpression;
import hillbillies.expressions.ReadVariableExpression;
import hillbillies.expressions.booleanType.BooleanExpression;
import hillbillies.expressions.unitType.UnitExpression;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class PositionOfExpression<E extends IUnitVariableExpression> 
		extends ExtendedPositionExpression<E> {

	public PositionOfExpression(E unit, SourceLocation sourceLocation) {
		super(unit,sourceLocation);
	}
	
	@Override
	public int[] evaluate() {
		if (this.getExpression() instanceof ReadVariableExpression) {
			if (((ReadVariableExpression) this.getExpression()).isValidVariableFor(this))
				return ((Unit) this.getExpression().evaluate()).getCubeCoordinates();
			else
				throw new IllegalVariableTypeException();
		}
		else
			return ((UnitExpression)this.getExpression()).evaluate().getCubeCoordinates();
	}
}
