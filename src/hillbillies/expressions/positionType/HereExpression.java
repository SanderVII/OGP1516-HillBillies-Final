package hillbillies.expressions.positionType;

import hillbillies.part3.programs.SourceLocation;

public class HereExpression extends PositionExpression {
	
	public HereExpression(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public int[] evaluate() {
		return this.getUnit().getCubeCoordinates();
	}
}
