package hillbillies.statements.expressionType;

import hillbillies.expressions.Expression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.statements.Statement;
import hillbillies.statements.Status;

public class AssignmentStatement<E extends Expression> 
		extends ExpressionStatement<E> {
	
	public AssignmentStatement(String variableName, E value, SourceLocation sourceLocation) {
		super(value, sourceLocation);
		this.variableName = variableName;
	}

	public void execute() {
		this.getSuperTask().addVariable(this.getVariableName(),this.getExpression());
		this.setStatus(Status.DONE);
	}
	
	private final String variableName;
	
	public String getVariableName() {
		return this.variableName;
	}
	
}
