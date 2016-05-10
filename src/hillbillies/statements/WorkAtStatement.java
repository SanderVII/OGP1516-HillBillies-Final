package hillbillies.statements;

import java.util.List;

import hillbillies.expressions.*;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public class WorkAtStatement extends Statement {

	public WorkAtStatement(Expression position, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.setPosition(position);
	}
	
	//TODO fix lists
	@Override
	public void execute(World world, Unit unit, List<int[]> selectedCubes, SourceLocation sourceLocation) {
		int[] position = (int[]) this.getPosition().evaluate(world, unit, selectedCubes.get(0), sourceLocation);
		unit.workAt(position);
	}
	
	public Expression getPosition() {
		return this.position;
	}
	
	private void setPosition(Expression position) throws IllegalArgumentException {
		if (! (position instanceof CubePositionExpression))
			throw new IllegalArgumentException();
		this.position = position;
	}
	
	private Expression position;
}
