package hillbillies.statements.expressionType;

import hillbillies.expressions.Expression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.statements.Statement;

public abstract class ExtendedExpressionStatement<E extends Expression> extends ExpressionStatement<E> {

	private Statement statement;

	public ExtendedExpressionStatement(E expression, Statement body, SourceLocation sourceLocation) {
		super(expression, sourceLocation);
		this.setStatement(body);
		body.setSuperText((Statement) this);
	}
	
	public Statement getStatement() {
		return this.statement;
	}
	
	protected void setStatement(Statement body) {
		this.statement = body;
	}

}
