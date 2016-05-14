package hillbillies.expressions.booleanType;

import hillbillies.expressions.unitType.UnitExpression;
import hillbillies.part3.programs.SourceLocation;

public class IsEnemyExpression<E extends UnitExpression> 
		extends SingleBooleanUnitExpression<E> {

	
	public IsEnemyExpression(E unit, SourceLocation sourceLocation) {
		super(unit, sourceLocation);
	}

	@Override
	public Boolean evaluate() {
		return this.getUnit().getFaction() != (this.getExpression().evaluate()).getFaction();
	}

}
