package hillbillies.expressions;


import java.util.List;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public abstract class UnitExpression extends Expression {

	public UnitExpression(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	public abstract Unit evaluate(World world, Unit unit, int[] selectedCubes);
}
