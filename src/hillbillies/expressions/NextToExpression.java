package hillbillies.expressions;

import java.util.List;
import java.util.Random;
import java.util.Set;

import org.hamcrest.core.IsInstanceOf;

import hillbillies.model.Cube;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.util.Position;

public class NextToExpression extends ExtendedPositionExpression{


	public NextToExpression(Expression position, SourceLocation sourceLocation) {
		super(position, sourceLocation);
	}

	@Override
	public int[] evaluate(World world, Unit unit, int[] selectedCubes) {
		int[] dummy = (int[]) this.getExpression().evaluate(world, unit, selectedCubes);
		
		Set<int[]> directlyAdjacentCoordinates = this.getUnit().getWorld().
				getDirectlyAdjacentCoordinates(dummy[0], dummy[1], dummy[2]);
		int random = new Random().nextInt(directlyAdjacentCoordinates.size());
		int i = 0;
		for (int[] cubeCoordinate: directlyAdjacentCoordinates) {
			if (random == i)
				return cubeCoordinate;
			i += 1;
		}
		// Only happens in a 1x1x1 world.
		return null;
	}
	
	@Override
	protected void setExpression(Expression position) {
		if (! (position instanceof CubePositionExpression))
			throw new IllegalArgumentException();
		super.setExpression(position);
	}

}
