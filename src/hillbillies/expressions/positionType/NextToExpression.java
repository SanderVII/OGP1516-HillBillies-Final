package hillbillies.expressions.positionType;

import java.util.Random;
import java.util.Set;

import hillbillies.part3.programs.SourceLocation;

public class NextToExpression<E extends PositionExpression> 
		extends ExtendedPositionExpression<E> {

	public NextToExpression(E position, SourceLocation sourceLocation) {
		super(position, sourceLocation);
	}

	@Override
	public int[] evaluate() {
		int[] dummy = (int[]) this.getExpression().evaluate();
		
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

}
