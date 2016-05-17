package hillbillies.expressions;

import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.statements.IBooleanVariableStatement;
import hillbillies.statements.IPositionVariableStatement;
import hillbillies.statements.IUnitVariableStatement;
import hillbillies.statements.Statement;

public class ReadVariableExpression extends Expression 
		implements IUnitVariableExpression, IPositionVariableExpression, 
		IBooleanVariableExpression {

	private String variableName;

	public ReadVariableExpression(String variableName, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.variableName = variableName;
	}
	
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
	
	/**
	 * Check if the given statement can have this variable as an argument.
	 * @param statement
	 * @return True if the evaluation of this variable expression is of an appropriate type
	 * 			for the statement.
	 */
	//TODO use liskov?
	public boolean isValidVariableFor(Statement statement) {
		Object evaluation = this.evaluate();
		if ((evaluation instanceof Unit) && (statement instanceof IUnitVariableStatement))
			return true;
		else if ((evaluation instanceof int[]) && (statement instanceof IPositionVariableStatement))
			return true;
		else if ((evaluation instanceof Boolean) && (statement instanceof IBooleanVariableStatement))
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
