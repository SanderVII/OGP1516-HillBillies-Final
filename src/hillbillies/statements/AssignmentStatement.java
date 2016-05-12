package hillbillies.statements;

import hillbillies.expressions.Expression;
import hillbillies.part3.programs.SourceLocation;

public class AssignmentStatement extends ExpressionStatement {
	
	public AssignmentStatement(String variableName, Expression value, SourceLocation sourceLocation) {
		super(value, sourceLocation);
		this.setVariableName(variableName);
	}

	@Override
	public void execute() {
		//TODO what to execute?
		this.getExpression().evaluate();
	}
	
	private String variableName;
	
	public String getVariableName() {
		return this.variableName;
	}
	
	protected void setVariableName(String name) {
		this.variableName = name;
	}

}
