package hillbillies.statements.expressionType.actions;

import hillbillies.expressions.positionType.PositionExpression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.statements.Status;

public class MoveToStatement<E extends PositionExpression> 
		extends ActionPositionStatement<E> {

	public MoveToStatement(E position, SourceLocation sourceLocation) {
		super(position, sourceLocation);
	}

	@Override
	public void execute() {
		if (this.getStatus() == Status.NOTSTARTED) {
			int[] position = this.getExpression().evaluate();
			this.getSuperTask().startExplicitStatement(this);
			this.getSuperTask().getUnit().moveTo(position,true);
		}
	}

}