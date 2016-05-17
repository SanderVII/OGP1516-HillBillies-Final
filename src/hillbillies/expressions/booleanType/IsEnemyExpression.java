package hillbillies.expressions.booleanType;

import hillbillies.expressions.unitType.UnitExpression;
import hillbillies.part3.programs.SourceLocation;

public class IsEnemyExpression<E extends UnitExpression> 
		extends SingleBooleanUnitExpression<E> {

	
	public IsEnemyExpression(E unit, SourceLocation sourceLocation) {
		super(unit, sourceLocation);
	}

	/*
	 * TODO Liskov: method throws can only be narrowed => should all super evaluates
	 * also throw an exception?
	 */
	@Override
	public Boolean evaluate() {
			return this.getUnit().getFaction() != (this.getExpression().evaluate()).getFaction();
	}

}
