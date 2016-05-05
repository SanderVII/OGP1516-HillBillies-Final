package hillbillies.expressions;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public abstract class Expression {

	public abstract Object evaluate(World world, Unit unit, int[] selectedCubes, SourceLocation sourceLocation);
	
}