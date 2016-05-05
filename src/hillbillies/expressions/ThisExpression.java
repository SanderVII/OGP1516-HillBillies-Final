package hillbillies.expressions;

import java.util.List;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public class ThisExpression extends UnitExpression {
	
	public ThisExpression(Unit unit, SourceLocation sourceLocation) {
		super(unit, sourceLocation);
	}

	@Override
	public Unit evaluate(World world, Unit unit, int[] selectedCubes, SourceLocation sourceLocation) {
		return this.getUnit();
	}

}
