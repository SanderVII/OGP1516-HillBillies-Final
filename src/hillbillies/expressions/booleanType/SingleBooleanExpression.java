package hillbillies.expressions.booleanType;

import hillbillies.exceptions.IllegalVariableTypeException;
import hillbillies.expressions.IExpression;
import hillbillies.expressions.ReadVariableExpression;
import hillbillies.part3.programs.SourceLocation;

public abstract class SingleBooleanExpression<E extends IExpression> extends BooleanExpression {

	public SingleBooleanExpression(E expression, SourceLocation sourceLocation) {
		super(sourceLocation);
		expression.setSuperText(this);
		this.expression = expression;
	}
	
	private final E expression;
	
	public E getExpression() {
		return this.expression;
	}
	
	/**
	 * Helper method to get the appropriate evaluation to use, if possible.
	 */
	protected Object getExpressionEvaluation() throws IllegalVariableTypeException {
		if (this.getExpression() instanceof ReadVariableExpression) {
			if (((ReadVariableExpression) this.getExpression()).isValidVariableFor(this))
				return this.getExpression().evaluate();
			else
				throw new IllegalVariableTypeException();
		}
		else
			return this.getExpression().evaluate();
	}
}
