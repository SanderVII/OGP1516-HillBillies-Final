package hillbillies.statements.expressionType.actions;

import hillbillies.expressions.Expression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.statements.expressionType.ExpressionStatement;

// TODO this class can probably be removed
public abstract class ActionStatement<E extends Expression> 
		extends ExpressionStatement<E> {

	public ActionStatement(E expression, SourceLocation sourceLocation) {
		super(expression, sourceLocation);
	}
}
