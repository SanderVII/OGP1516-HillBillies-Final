package hillbillies.statements;

import hillbillies.expressions.Expression;
import hillbillies.part3.programs.SourceLocation;

public class AssignmentStatement extends ExpressionStatement {
	
	public AssignmentStatement(String variableName, Expression value, SourceLocation sourceLocation) {
		super(value, sourceLocation);
		this.setVariableName(variableName);
	}

	public void execute() {
		this.getSuperTask().addVariable(this.getVariableName(),this.getExpression());
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
