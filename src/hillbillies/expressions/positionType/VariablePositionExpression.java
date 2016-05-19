package hillbillies.expressions.positionType;

import hillbillies.part3.programs.SourceLocation;

public class VariablePositionExpression extends PositionExpression {

	public VariablePositionExpression(String variableName, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.setVariableName(variableName);
	}
	
	private String variableName;
	
	@Override
	public int[] evaluate() {
		if (this.isWellFormed())
			return (int[]) (this.getSuperTask().getValue(this.getVariableName()).evaluate());
		else
			return null;
	}

	public String getVariableName() {
		return this.variableName;
	}
	
	private void setVariableName(String name) {
		this.variableName = name;
	}
	
	/**
	 * Return true if the task has already registered a value for the variable.
	 */
	@Override
	public boolean isWellFormed() {
		return this.getSuperTask().hasAsVariable(this.getVariableName());
	}
	
	@Override
	public VariablePositionExpression clone() throws CloneNotSupportedException {
		VariablePositionExpression cloned = (VariablePositionExpression) super.clone();
		cloned.setVariableName(this.getVariableName());
		return cloned;
	}

}
