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

	public EnemyExpression(SourceLocation sourceLocation) {
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
			if ((element.getFaction() != this.getUnit().getFaction()) && (newDistance < distance)) {
				result = element;
				distance = newDistance;
			}
		}
		return result;
	}

}
