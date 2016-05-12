package hillbillies.expressions;

import java.util.List;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public class ReadVariableExpression extends Expression {
	
	public ReadVariableExpression(String variableName, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.setVariableName(variableName);
	}
	
	//TODO finish
	@Override
	public Object evaluate(World world, Unit unit, int[] selectedCubes) {
		return null;
	}
	
	private String variableName;
	
	public String getVariableName() {
		return this.variableName;
	}
	
	private void setVariableName(String variableName) {
		this.variableName = variableName;
	}

}
