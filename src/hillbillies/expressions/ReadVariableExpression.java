package hillbillies.expressions;

import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class ReadVariableExpression extends Expression 
		implements IUnitVariableExpression, IPositionVariableExpression, 
		IBooleanVariableExpression {

	private String variableName;

	public ReadVariableExpression(String variableName, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.variableName = variableName;
	}
	
//	/**
//	 * Get the type of the variable.
//	 * Uses the Liskov principle: if it is not one of the three main types,
//	 * it must implement this method itself.
//	 */
//	@Override
//	public Class<? extends Expression> getType() {
//		Expression variableType = this.getSuperTask().getValue(this.getVariableName());
//		if (variableType instanceof BooleanExpression)
//			return BooleanExpression.class;
//		if (variableType instanceof PositionExpression)
//			return PositionExpression.class;
//		if (variableType instanceof UnitExpression)
//			return UnitExpression.class;
//		else
//			return variableType.getType();
//	}
	
	/**
	 * Check if the given expression can have this variable as an argument.
	 * @param expression
	 * @return True if the evaluation of this variable expression is of an appropriate type
	 * 			for the expression.
	 */
	//TODO use liskov?
	public boolean isValidVariableFor(Expression expression) {
		Object evaluation = this.evaluate();
		if ((evaluation instanceof Unit) && (expression instanceof IUnitVariableExpression))
			return true;
		else if ((evaluation instanceof int[]) && (expression instanceof IPositionVariableExpression))
			return true;
		else if ((evaluation instanceof Boolean) && (expression instanceof IBooleanVariableExpression))
			return true;
		else
			return false;
	}

	@Override
	public Object evaluate() {
		return (this.getSuperTask().getValue(this.getVariableName()).evaluate());
	}

	public String getVariableName() {
		return this.variableName;
	}
	
	/**
	 * Return true if the task has already registered a value for the variable.
	 */
	@Override
	public boolean isWellFormed() {
		return this.getSuperTask().hasAsVariable(this.getVariableName());
	}
}
