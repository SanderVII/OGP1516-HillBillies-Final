package hillbillies.expressions;

import java.util.List;
import java.util.Set;

import hillbillies.model.Boulder;
import hillbillies.model.Log;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.util.Position;

public class BoulderPositionExpression extends CubePositionExpression {
	
	public BoulderPositionExpression(SourceLocation sourceLocation) {
		super(sourceLocation);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int[] evaluate(World world, Unit unit, int[] selectedCubes, SourceLocation sourceLocation) {
		Set<Boulder> boulders = world.getBoulders();
		double distance = Integer.MAX_VALUE;
		int[] result = null;
		for (Boulder element : boulders) {
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
