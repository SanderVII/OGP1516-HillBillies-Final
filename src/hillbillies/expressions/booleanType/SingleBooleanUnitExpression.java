package hillbillies.expressions.booleanType;

import hillbillies.exception.IllegalVariableTypeException;
import hillbillies.expressions.IUnitVariableExpression;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public abstract class SingleBooleanUnitExpression<E extends IUnitVariableExpression> 
		extends SingleBooleanExpression<E> {

	public SingleBooleanUnitExpression(E expression, SourceLocation sourceLocation) {
		super(expression, sourceLocation);
	}
	
	/**
	 * Helper method to get the appropriate evaluation to use, if possible.
	 * @return
	 */
	@Override
	protected Unit getExpressionEvaluation() throws IllegalVariableTypeException {
//		if (this.getExpression() instanceof ReadVariableExpression) {
//			if (((ReadVariableExpression) this.getExpression()).isValidVariableFor(this))
//				return (Unit) this.getExpression().evaluate();
//			else
//				throw new IllegalVariableTypeException();
//		}
//		else
//			return ((UnitExpression)this.getExpression()).evaluate();
		return (Unit) super.getExpressionEvaluation();
	}

}
