package hillbillies.expressions;

import java.util.List;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public class IsEnemyExpression extends SingleBooleanExpression{

	
	public IsEnemyExpression(Expression expression, SourceLocation sourceLocation) {
		super(expression, sourceLocation);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Boolean evaluate(World world, Unit unit, int[] selectedCubes) {
//		return ! (unit.getFaction() == ((Unit) this.getExpression().evaluate(
//				world, unit, selectedCubes, sourceLocation)).getFaction());
		return false;
	}
	
	@Override
	protected void setExpression(Expression expression) {
		if (!(expression instanceof UnitExpression))
			throw new IllegalArgumentException();
		super.setExpression(expression);
	}

}
