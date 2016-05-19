package hillbillies.statements.expressionType;

import java.util.ArrayList;
import java.util.List;

import hillbillies.expressions.Expression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.statements.ISubStatement;
import hillbillies.statements.Statement;

public abstract class ExtendedExpressionStatement<E extends Expression> extends ExpressionStatement<E>
	implements ISubStatement {

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
	
	@Override
	public List<Statement> getSubStatements() {
		List<Statement> result = new ArrayList<Statement>();
		result.add(this.getStatement());
		return result;
	}
	
	@Override
	public ExtendedExpressionStatement<E> clone() throws CloneNotSupportedException {
		ExtendedExpressionStatement<E> cloned = (ExtendedExpressionStatement<E>) super.clone();
		Statement clonedBody = this.getStatement().clone();
		cloned.setStatement(clonedBody);
		clonedBody.setSuperText(cloned);
		return cloned;
	}

}
