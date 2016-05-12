package hillbillies.expressions;

import java.util.List;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public class PositionOfExpression extends ExtendedPositionExpression {


	public PositionOfExpression(Expression unit, SourceLocation sourceLocation) {
		super(unit,sourceLocation);
	}

	@Override
	public int[] evaluate(World world, Unit unit, int[] selectedCubes) {
		return this.getUnit().getPosition().getCubeCoordinates();
	}
	
	@Override
	protected void setExpression(Expression unit) {
		if (! (unit instanceof UnitExpression))
			throw new IllegalArgumentException();
		super.setExpression(unit);
	}
}
