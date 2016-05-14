package hillbillies.expressions.positionType;

import hillbillies.expressions.IReadVariable;
import hillbillies.part3.programs.SourceLocation;

public class VariablePositionExpression extends PositionExpression implements IReadVariable {

	public VariablePositionExpression(String variableName, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.variableName = variableName;
	}
	
	private final String variableName;

	@Override
	public String getVariableName() {
		return this.variableName;
	}

	/*
	 * NOTE: To remove this cast, you would probably have to parametrize the Task class,
	 * or reorganize the expression hierarchy.
	 */
	@Override
	public int[] evaluate() {
		return (int[]) this.getSuperTask().getValue(this.getVariableName()).evaluate();
	}
	
	/**
	 * Check if the given variable has already been initialized.
	 */
	@Override
	public boolean isWellFormed() {
		return this.getSuperTask().hasAsVariable(this.getVariableName());
	}

}
