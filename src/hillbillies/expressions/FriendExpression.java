package hillbillies.expressions;

import java.util.List;
import java.util.Set;

import hillbillies.model.Faction;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.util.Position;

public class FriendExpression extends UnitExpression {

	public FriendExpression(Unit unit, SourceLocation sourceLocation) {
		super(unit, sourceLocation);
	}

	@Override
	public Unit evaluate(World world, Unit unit, int[] selectedCubes, SourceLocation sourceLocation) {
		Faction faction = unit.getFaction();
		Set<Unit> units = faction.getUnits();
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
