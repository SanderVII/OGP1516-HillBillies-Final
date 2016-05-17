package hillbillies.expressions.booleanType;

import hillbillies.expressions.unitType.UnitExpression;
import hillbillies.part3.programs.SourceLocation;

public class IsAliveExpression<E extends UnitExpression> 
		extends SingleBooleanUnitExpression<E> {

	public IsAliveExpression(E unit, SourceLocation sourceLocation) {
		super(unit, sourceLocation);
	}

	@Override
	public Boolean evaluate() {
		return ! (this.getExpression().evaluate().isTerminated());
	}

}
