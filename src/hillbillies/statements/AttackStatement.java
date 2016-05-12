package hillbillies.statements;

import java.util.List;

import hillbillies.expressions.EnemyExpression;
import hillbillies.expressions.Expression;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public class AttackStatement extends ActionUnitStatement {
	
	public AttackStatement(Expression unit, SourceLocation sourceLocation) {
		super(unit, sourceLocation);
	}

	@Override
	public void execute(World world, Unit unit,int[] selectedCubes) {
		this.getUnit().attack((Unit) this.getExpression().evaluate(world, unit, selectedCubes));
	}

}
