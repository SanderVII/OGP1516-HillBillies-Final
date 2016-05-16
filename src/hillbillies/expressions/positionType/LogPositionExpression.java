package hillbillies.expressions.positionType;

import java.util.Set;

import hillbillies.model.Log;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.positions.Position;

public class LogPositionExpression extends PositionExpression {

	public LogPositionExpression(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public int[] evaluate() {
		Set<Log> logs = this.getUnit().getWorld().getLogs();
		double distance = Integer.MAX_VALUE;
		int[] result = null;
		for (Log element : logs) {
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
