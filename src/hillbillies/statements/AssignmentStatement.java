package hillbillies.statements;

import java.util.List;

import hillbillies.expressions.Expression;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public class AssignmentStatement extends ExpressionStatement {
	
	public AssignmentStatement(String variableName, Expression value, SourceLocation sourceLocation) {
		super(value, sourceLocation);
		this.setVariableName(variableName);
	}

	@Override
	public void execute(World world, Unit unit, int[] selectedCubes) {
		this.getTask().addVariable(this.getVariableName(),this.getExpression());
		this.setStatus(Status.DONE);
	}
	
	private String variableName;
	
	public String getVariableName() {
		return this.variableName;
	}
	
	protected void setVariableName(String name) {
		this.variableName = name;
	}

}
