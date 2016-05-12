package hillbillies.statements;

import hillbillies.expressions.Expression;
import hillbillies.part3.programs.SourceLocation;

public abstract class ExtendedExpressionStatement extends ExpressionStatement {

	private Statement statement;

	public ExtendedExpressionStatement(Expression expression, Statement body, SourceLocation sourceLocation) {
		super(expression, sourceLocation);
		this.setStatement(body);
		body.setSuperStatement(this);
	}
	
	public Statement getStatement() {
		return this.statement;
	}
	
	protected void setStatement(Statement body) {
		this.statement = body;
	}

}
