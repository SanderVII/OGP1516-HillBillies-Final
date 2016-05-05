package hillbillies.expressions;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public class AndExpression extends CombinedBooleanExpression {

	public AndExpression(Expression left, Expression right, SourceLocation sourceLocation) {
		super(left, right, sourceLocation);
	}
	
	public Boolean evaluate(World world, Unit unit, int[] selectedCubes, SourceLocation sourceLocation) {
		boolean leftBoolean = ((Boolean) this.getLeft().evaluate(world, unit, selectedCubes, sourceLocation)).booleanValue();
		boolean rightBoolean = ((Boolean) this.getRight().evaluate(world, unit, selectedCubes, sourceLocation)).booleanValue();
		if (leftBoolean && rightBoolean)
			return true;
		else
			return false;
	}
}
