package hillbillies.expressions;

import java.util.List;
import java.util.Random;
import java.util.Set;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.util.Position;

public class AnyExpression extends UnitExpression {


	public AnyExpression(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public Unit evaluate(World world, Unit unit, int[] selectedCubes) {
		
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
