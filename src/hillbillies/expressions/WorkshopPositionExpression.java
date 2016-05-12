package hillbillies.expressions;

import java.util.List;
import java.util.Set;

import hillbillies.model.Log;
import hillbillies.model.Terrain;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.util.Position;

public class WorkshopPositionExpression extends CubePositionExpression{
	
	public WorkshopPositionExpression(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public int[] evaluate() {
		int[] result = null;
		World unitWorld = this.getUnit().getWorld();
		double distance = Integer.MAX_VALUE;
		for (int x = World.CUBE_COORDINATE_MIN; x < unitWorld.getMaximumXValue(); x++) {
			for (int y = World.CUBE_COORDINATE_MIN; y < unitWorld.getMaximumYValue(); y++) {
				for (int z = World.CUBE_COORDINATE_MIN; z < unitWorld.getMaximumZValue(); z++) {
					int[] newCoordinates = new int[]{x,y,z};
					double newDistance = Position.getDistance(this.getUnit().getCubeCenter(), 
							Position.getCubeCenter(newCoordinates));
					if ((newDistance < distance) && 
							(unitWorld.getCube(x, y, z).getTerrainType() == Terrain.WORKSHOP)) {
						result = newCoordinates;
						distance = newDistance;
					}
				}
			}
		}
		return result;
	}
}
