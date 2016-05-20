package hillbillies.statements.expressionType.actions;

import hillbillies.expressions.Expression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.statements.expressionType.ExpressionStatement;

/**
 * A specific type of expression statement which directly influences
 * their unit upon execution.
 * @author Thomas
 *
 * @param <E>
 */
public abstract class ActionStatement<E extends Expression> 
		extends ExpressionStatement<E> {

	public ActionStatement(E expression, SourceLocation sourceLocation) {
		super(expression, sourceLocation);
	}
}
