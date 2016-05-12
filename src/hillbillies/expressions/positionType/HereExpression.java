package hillbillies.expressions.positionType;

import java.util.List;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public class HereExpression extends CubePositionExpression {
	
	public HereExpression(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public int[] evaluate() {
		return this.getUnit().getCubeCoordinates();
	}
}
