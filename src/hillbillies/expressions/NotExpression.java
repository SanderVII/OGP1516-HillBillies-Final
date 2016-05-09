package hillbillies.expressions;

import java.util.List;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public class NotExpression extends SingleBooleanExpression{

	public NotExpression(Expression expression, SourceLocation sourceLocation) {
		super(expression, sourceLocation);
		if (! (expression instanceof BooleanExpression))
			throw new IllegalArgumentException();
	}

	@Override
	public Boolean evaluate(World world, Unit unit, int[] selectedCubes, SourceLocation sourceLocation) {
		return (! ((Boolean) getExpression().evaluate(world, unit, selectedCubes, sourceLocation)));
	}

}
