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
		// TODO Auto-generated constructor stub
	}

	@Override
	public int[] evaluate(World world, Unit unit,int[] selectedCubes, SourceLocation sourceLocation) {
		int[] result = null;
		double distance = Integer.MAX_VALUE;
		for (int x = World.CUBE_COORDINATE_MIN; x < world.getMaximumXValue(); x++) {
			for (int y = World.CUBE_COORDINATE_MIN; y < world.getMaximumYValue(); y++) {
				for (int z = World.CUBE_COORDINATE_MIN; z < world.getMaximumZValue(); z++) {
					int[] newCoordinates = new int[]{x,y,z};
					double newDistance = Position.getDistance(Position.getCubeCenter(selectedCubes), 
							Position.getCubeCenter(newCoordinates));
					if ((newDistance < distance) && 
							(world.getCube(x, y, z).getTerrainType() == Terrain.WORKSHOP)) {
						result = newCoordinates;
						distance = newDistance;
					}
				}
			}
		}
		return result;
	}
}
