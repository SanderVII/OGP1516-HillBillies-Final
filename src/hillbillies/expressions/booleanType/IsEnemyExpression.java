package hillbillies.expressions.booleanType;

import hillbillies.exceptions.IllegalVariableTypeException;
import hillbillies.expressions.IUnitVariableExpression;
import hillbillies.part3.programs.SourceLocation;

public class IsEnemyExpression<E extends IUnitVariableExpression> 
		extends SingleBooleanUnitExpression<E> {

	
	public IsEnemyExpression(E unit, SourceLocation sourceLocation) {
		super(unit, sourceLocation);
	}

	/*
	 * TODO Liskov: method throws can only be narrowed => should all super evaluates
	 * also throw an exception?
	 */
	@Override
	public Boolean evaluate() throws IllegalVariableTypeException {
			return this.getUnit().getFaction() != (this.getExpressionEvaluation()).getFaction();
	}

}
