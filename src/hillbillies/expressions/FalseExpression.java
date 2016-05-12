package hillbillies.expressions;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public class FalseExpression extends BooleanExpression {
	
	public FalseExpression(SourceLocation sourceLocation) {
		super(sourceLocation);
	}
	
	@Override
	public Boolean evaluate(World world, Unit unit, int[] selectedCubes) {
		return false;
	}
}
