package hillbillies.expressions;

import java.util.List;
import java.util.Set;

import hillbillies.model.Log;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.util.Position;

public class LogPositionExpression extends CubePositionExpression {

	public LogPositionExpression(SourceLocation sourceLocation) {
		super(sourceLocation);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int[] evaluate(World world, Unit unit, int[] selectedCubes, SourceLocation sourceLocation) {
		Set<Log> logs = world.getLogs();
		double distance = Integer.MAX_VALUE;
		int[] result = null;
		for (Log element : logs) {
			double newDistance = Position.getDistance(element.getPosition().getCoordinates(), 
					unit.getPosition().getCoordinates());
			if (newDistance < distance) {
				result = element.getPosition().getCubeCoordinates();
				distance = newDistance;
			}
		}
		return result;
	}

}
