package hillbillies.expressions;

import java.util.List;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public class IsSolidExpression extends SingleBooleanPositionExpression {

	public IsSolidExpression(Expression position, SourceLocation sourceLocation) {
		super(position, sourceLocation);
	}

	@Override
	public Boolean evaluate(World world, Unit unit, int[] selectedCube) {
		return ! this.getUnit().getWorld().getCube(
				(int[]) this.getExpression().evaluate(world, unit, selectedCube)).isPassable();
	}
}
