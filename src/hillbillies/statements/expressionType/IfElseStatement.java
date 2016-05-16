package hillbillies.statements.expressionType;

import hillbillies.exceptions.IllegalVariableTypeException;
import hillbillies.expressions.IBooleanVariableExpression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.statements.Statement;
import hillbillies.statements.Status;

public class IfElseStatement<E extends IBooleanVariableExpression>
		extends ExtendedExpressionStatement<E> {

	public IfElseStatement(E condition, Statement ifBody, Statement elseBody, SourceLocation sourceLocation) {
		super(condition, ifBody, sourceLocation);
		if (elseBody != null)
			elseBody.setSuperText(this);
	}

	@Override
	public void execute() throws IllegalVariableTypeException{
		if ((Boolean) this.getExpression().evaluate())
			this.getStatement().execute();
		else if (this.getElseBody() != null)
			this.getElseBody().execute();
		this.setStatus(Status.DONE);
	}
	
	private Statement elseBody;
	
	public Statement getElseBody() {
		return this.elseBody;
	}
	
	protected void setElseBody(Statement elseBody) {
		this.elseBody = elseBody;
	}

}
