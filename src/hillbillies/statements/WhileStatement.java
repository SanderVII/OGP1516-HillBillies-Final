package hillbillies.statements;

import hillbillies.expressions.Expression;
import hillbillies.part3.programs.SourceLocation;

//TODO finish
public class WhileStatement extends ExtendedExpressionStatement {

	public WhileStatement(Expression condition, Statement body, SourceLocation sourceLocation) {
		super(condition, body, sourceLocation);
	}

	@Override
	public void execute() {
		
		if ((boolean) this.getExpression().evaluate()) {
			this.getStatement().execute();
			this.setStatus(Status.WHILE);
		}
		else
			this.setStatus(Status.DONE);
	}
}
