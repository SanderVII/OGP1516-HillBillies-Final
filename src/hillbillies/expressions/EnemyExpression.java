package hillbillies.expressions;

import java.util.List;
import java.util.Set;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.util.Position;

import java.util.List;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public class EnemyExpression extends UnitExpression {

	public EnemyExpression(Unit unit, SourceLocation sourceLocation) {
		super(unit, sourceLocation);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Unit evaluate(World world, Unit unit, int[] selectedCubes, SourceLocation sourceLocation) {
		Set<Unit> units = world.getUnits();
		double distance = Integer.MAX_VALUE;
		Unit result = null;
		for (Unit element : units) {
			double newDistance = Position.getDistance(element.getPosition().getCoordinates(), 
					unit.getPosition().getCoordinates());
			if ((element.getFaction() != unit.getFaction()) && (newDistance < distance)) {
				result = element;
				distance = newDistance;
			}
		}
		return result;
	}

}
