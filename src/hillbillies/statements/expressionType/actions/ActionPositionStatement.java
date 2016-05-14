package hillbillies.statements.expressionType.actions;

import hillbillies.expressions.positionType.PositionExpression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.statements.Statement;

public abstract class ActionPositionStatement<E extends PositionExpression>
		extends ActionStatement<E> {
	
	/*
	 * TODO should be able to read variables.
	 */
	public ActionPositionStatement(E expression, SourceLocation sourceLocation) {
		super(expression, sourceLocation);
	}

}
