package hillbillies.statements.expressionType.actions;

import hillbillies.expressions.positionType.PositionExpression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.statements.Statement;

public class WorkAtStatement<E extends PositionExpression> 
		extends ActionPositionStatement<E> {

	public WorkAtStatement(E position, SourceLocation sourceLocation) {
		super(position, sourceLocation);
	}
	
	@Override
	public void execute() {
		int[] dummy = this.getExpression().evaluate();
		this.getSuperTask().getUnit().workAt(dummy,true);
	}
}
