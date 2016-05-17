package hillbillies.expressions.positionType;

import hillbillies.expressions.Expression;
import hillbillies.part3.programs.SourceLocation;

public abstract class PositionExpression extends Expression {

	public PositionExpression(SourceLocation sourceLocation) {
		super(sourceLocation);
	}
	
	@Override
	public abstract int[] evaluate();
}
