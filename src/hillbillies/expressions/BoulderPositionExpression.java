package hillbillies.expressions;

import java.util.List;

import hillbillies.model.Boulder;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public class BoulderPositionExpression extends CubePositionExpression {

	private SourceLocation sourceLocation;

	public BoulderPositionExpression(SourceLocation sourceLocation){
		this.sourceLocation = sourceLocation;
	}
	
	@Override
	public int[] evaluate(World world, Unit unit, int[] selectedCubes, SourceLocation sourceLocation) {
		return null;
		// TODO Auto-generated method stub
	}

}
