package hillbillies.expressions.booleanType;

import hillbillies.expressions.unitType.UnitExpression;
import hillbillies.part3.programs.SourceLocation;

public class IsFriendExpression<E extends UnitExpression> 
		extends SingleBooleanUnitExpression<E> {


	public IsFriendExpression(E unit, SourceLocation sourceLocation) {
		super(unit, sourceLocation);
	}

	@Override
	public Boolean evaluate() {
		return this.getUnit().getFaction() == (this.getExpression().evaluate()).getFaction();
	}

}
