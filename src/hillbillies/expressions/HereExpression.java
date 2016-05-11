package hillbillies.expressions;

import java.util.List;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public class HereExpression extends CubePositionExpression {
	
	public HereExpression(SourceLocation sourceLocation) {
		super(sourceLocation);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int[] evaluate(World world, Unit unit,int[] selectedCubes) {
		return this.getUnit().getCubeCoordinates();
	}
}
