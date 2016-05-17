package hillbillies.expressions.unitType;

import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class VariableUnitExpression extends UnitExpression {

	public VariableUnitExpression(String variableName, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.variableName = variableName;
	}
	
	private final String variableName;
	
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
	
	/**
	 * Return true if the task has already registered a value for the variable.
	 */
	@Override
	public boolean isWellFormed() {
		return this.getSuperTask().hasAsVariable(this.getVariableName());
	}

}
