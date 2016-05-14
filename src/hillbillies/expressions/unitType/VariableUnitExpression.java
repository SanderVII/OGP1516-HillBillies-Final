package hillbillies.expressions.unitType;

import hillbillies.expressions.IReadVariable;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class VariableUnitExpression extends UnitExpression implements IReadVariable {

	public VariableUnitExpression(String variableName, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.variableName = variableName;
	}
	
	private String variableName;

	@Override
	public String getVariableName() {
		return this.variableName;
	}

	@Override
	public Unit evaluate() {
		return (Unit) this.getSuperTask().getValue(this.getVariableName()).evaluate();
	}
	
	/**
	 * Check if the given variable has already been initialized.
	 */
	@Override
	public boolean isWellFormed() {
		return this.getSuperTask().hasAsVariable(this.getVariableName());
	}

}
