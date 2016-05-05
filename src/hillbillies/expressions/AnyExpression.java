package hillbillies.expressions;

import java.util.List;
import java.util.Random;
import java.util.Set;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.util.Position;

public class AnyExpression extends UnitExpression {


	public AnyExpression(Unit unit, SourceLocation sourceLocation) {
		super(unit, sourceLocation);
	}

	@Override
	public Unit evaluate(World world, Unit unit, int[] selectedCubes, SourceLocation sourceLocation) {
		Set<Unit> units = world.getUnits();
		double distance = Integer.MAX_VALUE;
		Unit result = null;
		for (Unit element : units) {
			double newDistance = Position.getDistance(element.getPosition().getCoordinates(), 
					unit.getPosition().getCoordinates());
			if ((element != unit) && (newDistance < distance)) {
				result = element;
				distance = newDistance;
			}
		}
		return result;
	}

}
