package hillbillies.expressions;

import java.util.List;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public class IsSolidExpression extends BooleanExpression {

	public IsSolidExpression(SourceLocation sourceLocation) {
		super(sourceLocation);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Boolean evaluate(World world, Unit unit, int[] selectedCubes, SourceLocation sourceLocation) throws ClassCastException {
		return ! world.getCube(selectedCubes).isPassable();
	}
}
