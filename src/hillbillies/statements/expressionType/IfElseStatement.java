package hillbillies.statements.expressionType;

import java.util.ArrayList;
import java.util.List;

import hillbillies.expressions.booleanType.BooleanExpression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.statements.Statement;
import hillbillies.statements.Status;

public class IfElseStatement<E extends BooleanExpression>
		extends ExtendedExpressionStatement<E> {

	public IfElseStatement(E condition, Statement ifBody, Statement elseBody, SourceLocation sourceLocation) {
		super(condition, ifBody, sourceLocation);
		if (elseBody != null) {
			elseBody.setSuperText(this);
			this.setElseBody(elseBody);
		}
	}

	@Override
	public void execute() {
//		if (this.getStatus() == Status.NOTSTARTED) {
			Status ifStatus = null;
			Status elseStatus = null;
			
			if (this.getExpression().evaluate()) {
				this.getStatement().execute();
				ifStatus = this.getStatement().getStatus();
				elseStatus = Status.DONE;
			}
			else if (this.getElseBody() != null) {
				this.getElseBody().execute();
				ifStatus = Status.DONE;
				elseStatus = this.getElseBody().getStatus();
			}
			else {
				ifStatus = Status.DONE;
				elseStatus = Status.DONE;
			}
			
			if ((ifStatus == Status.DONE) && (elseStatus == Status.DONE))
				this.setStatus(Status.DONE);
			else if((ifStatus == Status.FAILED) || (elseStatus == Status.FAILED))
				this.setStatus(Status.FAILED);
			else
				this.setStatus(Status.EXECUTING);
//		}
	}
	
	private Statement elseBody;
	
	public Statement getElseBody() {
		return this.elseBody;
	}
	
	protected void setElseBody(Statement elseBody) {
		this.elseBody = elseBody;
	}
	
	@Override
	public List<Statement> getSubStatements() {
		List<Statement> result = new ArrayList<Statement>();
		result.add(this.getStatement());
		if (this.getElseBody() != null)
			result.add(this.getElseBody());
		return result;
	}

}
