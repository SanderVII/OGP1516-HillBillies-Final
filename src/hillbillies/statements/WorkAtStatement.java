package hillbillies.statements;

import java.util.List;

import hillbillies.expressions.*;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public class WorkAtStatement extends ActionPositionStatement {

	public WorkAtStatement(Expression position, SourceLocation sourceLocation) {
		super(position, sourceLocation);
//		this.setExpression(position);
	}

	//TODO fix lists
	@Override
	public void execute(World world, Unit unit, int[] selectedCubes) {
		int[] dummy = (int[]) this.getExpression().evaluate(world, unit, selectedCubes);
		this.getTask().getUnit().workAt(dummy,true);
//		int[] position = (int[]) this.getExpression().evaluate(world, unit, selectedCubes.get(0));
////		unit.workAt(position);
	}
	
	@Override
	protected void setExpression(Expression position) throws IllegalArgumentException {
		if (! (position instanceof CubePositionExpression))
			throw new IllegalArgumentException();
		super.setExpression(position);
	}
}
