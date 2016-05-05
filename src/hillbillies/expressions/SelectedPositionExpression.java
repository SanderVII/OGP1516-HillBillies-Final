package hillbillies.expressions;

import java.util.List;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public class SelectedPositionExpression extends CubePositionExpression {
	
	@Override
	public int[] evaluate(World world, Unit unit, int[] selectedCubes,SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return selectedCubes;
	}

}
