package hillbillies.expressions;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public abstract class BooleanExpression extends Expression {
	
	public BooleanExpression(SourceLocation sourceLocation) {
		super(sourceLocation);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public abstract Boolean evaluate(World world, Unit unit, int[] selectedCubes);
	
}
