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
		if (this.getExpression().evaluate()) {
			this.getStatement().execute();
			this.setStatus(Status.WHILE);
		}
		else
			this.setStatus(Status.DONE);
	}
}
