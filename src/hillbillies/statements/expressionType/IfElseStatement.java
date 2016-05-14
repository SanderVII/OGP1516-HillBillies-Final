package hillbillies.statements.expressionType;

import hillbillies.expressions.booleanType.BooleanExpression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.statements.Statement;
import hillbillies.statements.Status;

public class IfElseStatement<E extends BooleanExpression>
		extends ExtendedExpressionStatement<E> {

	public IfElseStatement(E condition, Statement ifBody, Statement elseBody, SourceLocation sourceLocation) {
		super(condition, ifBody, sourceLocation);
		if (elseBody != null)
			elseBody.setSuperText(this);
	}

	@Override
	public void execute() {
		if (this.getExpression().evaluate())
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
