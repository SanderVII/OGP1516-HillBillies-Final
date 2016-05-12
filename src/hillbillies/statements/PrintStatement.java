package hillbillies.statements;

import java.util.List;

import hillbillies.expressions.Expression;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public class PrintStatement extends ExpressionStatement{

	public PrintStatement(Expression value, SourceLocation sourceLocation) {
		super(value,sourceLocation);
	}

	@Override
	public void execute(World world, Unit unit, int[] selectedCubes) {
		System.out.println(this.getExpression().evaluate(world, unit, selectedCubes));
	}

}
