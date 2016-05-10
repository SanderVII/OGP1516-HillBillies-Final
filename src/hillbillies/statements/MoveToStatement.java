package hillbillies.statements;

import java.util.List;

import hillbillies.expressions.Expression;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public class MoveToStatement extends ActionPositionStatement {

	public MoveToStatement(Expression expression, SourceLocation sourceLocation) {
		super(expression, sourceLocation);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @deprecated Use {@link #execute(World,Unit,int[])} instead
	 */
	@Override
	public void execute(World world, Unit unit, List<int[]> selectedCubes) {
		execute(world, unit, selectedCubes);
	}

	@Override
	public void execute(World world, Unit unit, int[] selectedCubes) {
		// TODO Auto-generated method stub
		
	}

}