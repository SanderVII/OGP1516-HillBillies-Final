package hillbillies.expressions;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public abstract class SingleBooleanUnitExpression extends SingleBooleanExpression {

	public SingleBooleanUnitExpression(Expression expression, SourceLocation sourceLocation) {
		super(expression, sourceLocation);
	}
	
	@Override
	protected void setExpression(Expression unit) {
		if (! (unit instanceof UnitExpression))
			throw new IllegalArgumentException();
		super.setExpression(unit);
	}

}
