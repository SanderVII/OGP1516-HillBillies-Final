package hillbillies.expressions;

import java.util.List;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public class NotExpression extends SingleBooleanExpression{

	public NotExpression(Expression expression, SourceLocation sourceLocation) {
		super(expression, sourceLocation);
	}

	@Override
	public Boolean evaluate() {
		return (! ((Boolean) getExpression().evaluate()));
	}
	
	@Override
	protected void setExpression(Expression booleanExpression) {
		if (! (booleanExpression instanceof BooleanExpression))
			throw new IllegalArgumentException();
		super.setExpression(booleanExpression);
	}
}
