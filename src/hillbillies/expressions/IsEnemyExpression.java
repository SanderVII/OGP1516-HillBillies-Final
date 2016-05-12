package hillbillies.expressions;

import java.util.List;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public class IsEnemyExpression extends SingleBooleanUnitExpression{

	
	public IsEnemyExpression(Expression unit, SourceLocation sourceLocation) {
		super(unit, sourceLocation);
	}

	@Override
	public Boolean evaluate(World world, Unit unit, int[] selectedCubes) {
		return this.getUnit().getFaction() != ((Unit) this.getExpression().
				evaluate(world, unit, selectedCubes)).getFaction();
	}

}
