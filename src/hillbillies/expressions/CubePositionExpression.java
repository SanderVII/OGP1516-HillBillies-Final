package hillbillies.expressions;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public abstract class CubePositionExpression extends Expression {

	public CubePositionExpression(SourceLocation sourceLocation) {
		super(sourceLocation);
		// TODO Auto-generated constructor stub
	}

	public abstract int[] evaluate(World world, Unit unit, int[] selectedCubes);
}
