package hillbillies.statements.expressionType.actions;

import hillbillies.expressions.positionType.PositionExpression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.statements.Status;

public class WorkAtStatement<E extends PositionExpression> 
		extends ActionPositionStatement<E> {

	public WorkAtStatement(E position, SourceLocation sourceLocation) {
		super(position, sourceLocation);
	}
	
	@Override
	public void execute() {
//		if (this.getStatus() == Status.NOTSTARTED) {
			int[] position = this.getExpression().evaluate();
			this.getSuperTask().startExplicitStatement(this);
			this.getSuperTask().getUnit().workAt(position,true);
//		}
		
	}
}
