package hillbillies.expressions.unitType;

import java.util.Set;

import hillbillies.model.Faction;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.positions.Position;

public class FriendExpression extends UnitExpression {

	public FriendExpression(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public Unit evaluate() {
		Faction faction = this.getUnit().getFaction();
		Set<Unit> units = faction.getUnits();
		double distance = Integer.MAX_VALUE;
		Unit result = null;
		for (Unit element : units) {
			double newDistance = Position.getDistance(element.getPosition().getCoordinates(), 
					this.getUnit().getPosition().getCoordinates());
			if ((element != this.getUnit()) && (newDistance < distance)) {
				result = element;
				distance = newDistance;
			}
		}
		return result;
	}

}
