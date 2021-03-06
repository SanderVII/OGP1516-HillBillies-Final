package hillbillies.expressions.positionType;

import java.util.Set;

import hillbillies.model.Boulder;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.positions.Position;

public class BoulderPositionExpression extends PositionExpression {
	
	public BoulderPositionExpression(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public int[] evaluate() {
		Set<Boulder> boulders = this.getUnit().getWorld().getBoulders();
		double distance = Integer.MAX_VALUE;
		int[] result = null;
		for (Boulder element : boulders) {
			double newDistance = Position.getDistance(element.getPosition().getCoordinates(), 
					this.getUnit().getPosition().getCoordinates());
			if (newDistance < distance) {
				result = element.getPosition().getCubeCoordinates();
				distance = newDistance;
			}
		}
		return result;
	}
}
