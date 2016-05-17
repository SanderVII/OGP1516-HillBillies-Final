package hillbillies.expressions.booleanType;

import hillbillies.expressions.unitType.UnitExpression;
import hillbillies.part3.programs.SourceLocation;

public abstract class SingleBooleanUnitExpression<E extends UnitExpression> 
		extends SingleBooleanExpression<E> {

	public SingleBooleanUnitExpression(E expression, SourceLocation sourceLocation) {
		super(expression, sourceLocation);
	}

}
