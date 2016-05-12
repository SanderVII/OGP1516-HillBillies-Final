package hillbillies.statements;

import hillbillies.expressions.Expression;
import hillbillies.part3.programs.SourceLocation;

public abstract class ActionStatement extends SingleExpressionStatement {

	public ActionStatement(Expression expression, SourceLocation sourceLocation) {
		super(expression, sourceLocation);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}
