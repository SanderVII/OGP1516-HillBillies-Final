package hillbillies.expressions.booleanType;

import hillbillies.exception.IllegalVariableTypeException;
import hillbillies.expressions.IUnitVariableExpression;
import hillbillies.expressions.unitType.UnitExpression;
import hillbillies.part3.programs.SourceLocation;

public class CarriesItemExpression<E extends IUnitVariableExpression> 
		extends SingleBooleanUnitExpression<E> {

	public CarriesItemExpression(E unit, SourceLocation sourceLocation) {
		super(unit, sourceLocation);
	}

	@Override
	public Boolean evaluate() throws IllegalVariableTypeException {
		return this.getExpressionEvaluation().hasItem();
	}
}
