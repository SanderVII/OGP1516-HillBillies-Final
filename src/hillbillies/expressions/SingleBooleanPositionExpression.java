package hillbillies.expressions;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public abstract class SingleBooleanPositionExpression extends SingleBooleanExpression {

	public SingleBooleanPositionExpression(Expression position, SourceLocation sourceLocation) {
		super(position, sourceLocation);
	}
	
	protected void setExpression(Expression position) {
		if (! (position instanceof CubePositionExpression))
			throw new IllegalArgumentException();
		super.setExpression(position);
	}

}
