package hillbillies.expressions.positionType;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import hillbillies.part3.programs.SourceLocation;
import hillbillies.positions.UnitPosition;

public class NextToExpression<E extends PositionExpression> 
		extends ExtendedPositionExpression<E> {

	public NextToExpression(E position, SourceLocation sourceLocation) {
		super(position, sourceLocation);
	}

	@Override
	public int[] evaluate() {
		int[] dummy = new int[3];
		dummy =  this.getExpression().evaluate();
		
		Set<int[]> directlyAdjacentCoordinates = this.getUnit().getWorld().
				getDirectlyAdjacentCoordinates(dummy[0], dummy[1], dummy[2]);
		Set<int[]> copy = new HashSet<>();
		for (int[] coordinate: directlyAdjacentCoordinates)
//			if ( this.getUnit().canHaveAsCoordinates(coordinate))
				copy.add(coordinate);
		
		//TODO remove unit check? currently blocks an all solid cube.
		int random = new Random().nextInt(copy.size());
		int i = 0;
		for (int[] cubeCoordinate: copy) {
			if (random == i)
				return cubeCoordinate;
			i += 1;
		}
		
		/*
		 * special case: if no passable cubes can be found,
		 * the position itself is returned. Other methods should
		 * be able to deal with this.
		 */
		
		return dummy;
	}

}
