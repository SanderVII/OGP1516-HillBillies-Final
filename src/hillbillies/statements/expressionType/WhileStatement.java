package hillbillies.statements.expressionType;

import hillbillies.expressions.booleanType.BooleanExpression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.statements.Statement;
import hillbillies.statements.Status;

public class WhileStatement<E extends BooleanExpression> 
		extends ExtendedExpressionStatement<E> {

	public WhileStatement(E condition, Statement body, SourceLocation sourceLocation) {
		super(condition, body, sourceLocation);
	}
	
	@Override
	public void execute() {
		this.setStatus(Status.EXECUTING);
		if (this.getExpression().evaluate()) {
			Statement statement = this.getStatement();
			Status status = statement.getStatus();
			// Only execute a statement when it is not started or done (can be re-executed).
			if ((status == Status.NOTSTARTED) || (status == Status.DONE)) {
				statement.execute();
				// Reset status so the while statement can be used again.
				this.setStatus(Status.NOTSTARTED);
			}
		}
		else
			this.setStatus(Status.DONE);
	}
}
