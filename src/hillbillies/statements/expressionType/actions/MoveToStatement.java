package hillbillies.statements.expressionType.actions;

import hillbillies.expressions.positionType.PositionExpression;
import hillbillies.part3.programs.SourceLocation;

public class MoveToStatement<E extends PositionExpression> 
		extends ActionPositionStatement<E> {

	public MoveToStatement(E position, SourceLocation sourceLocation) {
		super(position, sourceLocation);
	}

	@Override
	public void execute() {
		this.getUnit().moveTo(this.getExpression().evaluate());
	}

}