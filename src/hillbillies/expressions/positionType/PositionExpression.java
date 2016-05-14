package hillbillies.expressions.positionType;

import hillbillies.expressions.Expression;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.tasks.TextObject;

public abstract class PositionExpression extends Expression {

	public PositionExpression(SourceLocation sourceLocation) {
		super(sourceLocation);
	}
	
	@Override
	public abstract int[] evaluate();
}
