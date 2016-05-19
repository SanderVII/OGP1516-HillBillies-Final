package hillbillies.expressions.booleanType;

import hillbillies.part3.programs.SourceLocation;

public class VariableBooleanExpression extends BooleanExpression {

	public VariableBooleanExpression(String variableName, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.setVariableName(variableName);
	}
	
	private String variableName;
	
	@Override
	public Boolean evaluate() {
		if (this.isWellFormed())
			return (Boolean) (this.getSuperTask().getValue(this.getVariableName()).evaluate());
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
	public VariableBooleanExpression clone() throws CloneNotSupportedException {
		VariableBooleanExpression cloned = (VariableBooleanExpression) super.clone();
		cloned.setVariableName(this.getVariableName());
		return cloned;
	}
}

