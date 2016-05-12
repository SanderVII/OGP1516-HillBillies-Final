package hillbillies.statements;

import java.util.List;

import hillbillies.expressions.Expression;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

//TODO finish
public class WhileStatement extends ExtendedExpressionStatement {

	public WhileStatement(Expression condition, Statement body, SourceLocation sourceLocation) {
		super(condition, body, sourceLocation);
	}

	@Override
	public void execute(World world, Unit unit,int[] selectedCubes) {
		
		if ((boolean) this.getExpression().evaluate(world, unit, selectedCubes)) {
			this.getStatement().execute(world, unit, selectedCubes);
			this.setStatus(Status.WHILE);
		}
		else
			this.setStatus(Status.DONE);
	}
}
