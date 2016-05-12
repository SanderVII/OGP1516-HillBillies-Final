package hillbillies.statements.expressionType;

import java.util.List;

import hillbillies.expressions.Expression;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.statements.ExtendedExpressionStatement;
import hillbillies.statements.Statement;
import hillbillies.statements.Status;

public class IfElseStatement extends ExtendedExpressionStatement {

	public IfElseStatement(Expression condition, Statement ifBody, Statement elseBody, SourceLocation sourceLocation) {
		super(condition, ifBody, sourceLocation);
		if (elseBody != null)
			elseBody.setSuperStatement(this);
	}

	@Override
	public void execute() {
		if ((boolean) this.getExpression().evaluate())
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
