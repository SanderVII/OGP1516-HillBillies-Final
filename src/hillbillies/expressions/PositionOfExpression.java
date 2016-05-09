package hillbillies.expressions;

import java.util.List;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public class PositionOfExpression extends CubePositionExpression {


	public PositionOfExpression(Unit unit, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.setUnit(unit);
	}

	@Override
	public int[] evaluate(World world, Unit unit, int[] selectedCubes, SourceLocation sourceLocation) {
		return this.getUnit().getPosition().getCubeCoordinates();
	}
	
	private Unit unit;
	
	public Unit getUnit() {
		return this.unit;
	}
	private void setUnit(Unit unit) {
		this.unit = unit;
	}
}
