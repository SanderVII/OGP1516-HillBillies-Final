package hillbillies.expressions;

import java.util.List;
import java.util.Random;
import java.util.Set;

import hillbillies.model.Cube;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.util.Position;

public class NextToExpression extends CubePositionExpression{


	public NextToExpression(CubePositionExpression position, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.setPosition(position);
	}

	@Override
	public int[] evaluate(World world, Unit unit, int[] selectedCubes, SourceLocation sourceLocation) {
		int[] dummy = this.getPosition().evaluate(world, unit, selectedCubes, sourceLocation);
		
		Set<Cube> directlyAdjacent = world.getDirectlyAdjacentCubes(dummy[0], dummy[1], dummy[2]);
		int random = new Random().nextInt(directlyAdjacent.size());
		int i = 0;
		for (Cube cube: directlyAdjacent) {
			if (random == i)
				return cube.getPosition().getCubeCoordinates();
			i += 1;
		}
		// Only happens in a 1x1x1 world.
		return null;
	}
	
	private CubePositionExpression position;
	
	public CubePositionExpression getPosition() {
		return this.position;
	}
	
	private void setPosition(CubePositionExpression position) {
		this.position = position;
	}

}
