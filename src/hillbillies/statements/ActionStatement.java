package hillbillies.statements;

import java.util.List;

import hillbillies.expressions.Expression;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public abstract class ActionStatement extends SingleExpressionStatement {

	public ActionStatement(Expression expression, SourceLocation sourceLocation) {
		super(expression, sourceLocation);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(World world, Unit unit, int[] selectedCubes) {
		// TODO Auto-generated method stub

	}

}
