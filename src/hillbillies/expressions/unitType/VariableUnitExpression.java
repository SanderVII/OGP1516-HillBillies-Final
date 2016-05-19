package hillbillies.expressions.unitType;

import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class VariableUnitExpression extends UnitExpression {

	public VariableUnitExpression(String variableName, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.setVariableName(variableName);
	}
	
	private String variableName;
	
	@Override
	public Unit evaluate() {
		if (this.isWellFormed())
			return (Unit) (this.getSuperTask().getValue(this.getVariableName()).evaluate());
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
	public VariableUnitExpression clone() throws CloneNotSupportedException {
		VariableUnitExpression cloned = (VariableUnitExpression) super.clone();
		cloned.setVariableName(this.getVariableName());
		return cloned;
	}

}
