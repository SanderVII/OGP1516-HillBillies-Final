package hillbillies.expressions.positionType;

import java.util.Random;
import java.util.Set;

import hillbillies.exception.IllegalVariableTypeException;
import hillbillies.expressions.IPositionVariableExpression;
import hillbillies.expressions.ReadVariableExpression;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class NextToExpression<E extends IPositionVariableExpression> 
		extends ExtendedPositionExpression<E> {

	public NextToExpression(E position, SourceLocation sourceLocation) {
		super(position, sourceLocation);
	}

	@Override
	public int[] evaluate() {
		int[] dummy = new int[3];
		if (this.getExpression() instanceof ReadVariableExpression) {
			if (((ReadVariableExpression) this.getExpression()).isValidVariableFor(this))
				dummy =  (int[]) this.getExpression().evaluate();
			else
				throw new IllegalVariableTypeException();
		}
		else
			dummy =  ((PositionExpression)this.getExpression()).evaluate();
		
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
