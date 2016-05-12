package hillbillies.expressions;

import java.util.List;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public class SelectedPositionExpression extends CubePositionExpression {
	
	public SelectedPositionExpression(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public int[] evaluate() {
		return this.getStatement().getTask().getPosition();
	}

}
