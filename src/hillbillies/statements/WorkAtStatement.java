package hillbillies.statements;

import hillbillies.expressions.*;
import hillbillies.part3.programs.SourceLocation;

public class WorkAtStatement extends ActionPositionStatement {

	public WorkAtStatement(Expression position, SourceLocation sourceLocation) {
		super(position, sourceLocation);
//		this.setExpression(position);
	}

	//TODO fix lists
	@Override
	public void execute() {
		int[] dummy = (int[]) this.getExpression().evaluate();
		this.getTask().getUnit().workAt(dummy,true);
	}
	
	@Override
	protected void setExpression(Expression position) throws IllegalArgumentException {
		if (! (position instanceof CubePositionExpression))
			throw new IllegalArgumentException();
		super.setExpression(position);
	}
}
