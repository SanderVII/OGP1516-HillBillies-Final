package hillbillies.expressions.unitType;

import java.util.Set;

import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.positions.Position;

public class AnyExpression extends UnitExpression {

	public AnyExpression(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public Unit evaluate() {
		Set<Unit> units = this.getUnit().getWorld().getUnits();
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
