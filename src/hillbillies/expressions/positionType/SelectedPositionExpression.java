package hillbillies.expressions.positionType;

import hillbillies.part3.programs.SourceLocation;

public class SelectedPositionExpression extends PositionExpression {
	
	public SelectedPositionExpression(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public int[] evaluate() {
		return this.getSuperTask().getCoordinates();
	}
	
	@Override
	public boolean isWellFormed() {
		return this.getSuperTask().getPosition() != null;
	}

}
