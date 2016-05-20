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
			switch (status) {
				case NOTSTARTED:
					statement.execute();
					break;
				case DONE:
					// reset status so this while can be reused.
					this.resetStatus();
					break;
				case FAILED:
					this.setStatus(Status.FAILED);
					break;
				case EXECUTING:
					statement.execute();
					break;
				default:
					break;
			}
		}
		else
			this.setStatus(Status.DONE);
	}
}
