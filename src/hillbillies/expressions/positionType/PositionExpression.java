package hillbillies.expressions.positionType;

import hillbillies.expressions.Expression;
import hillbillies.expressions.IPositionVariableExpression;
import hillbillies.part3.programs.SourceLocation;

public abstract class PositionExpression extends Expression implements IPositionVariableExpression {

	public PositionExpression(SourceLocation sourceLocation) {
		super(sourceLocation);
	}
	
	@Override
	public abstract int[] evaluate();
}
