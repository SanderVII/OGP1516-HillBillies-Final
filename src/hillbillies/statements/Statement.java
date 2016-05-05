package hillbillies.statements;

import java.util.List;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public abstract class Statement {
	
	public abstract void execute(World world, Unit unit,List<int[]> selectedCubes, SourceLocation sourceLocation);

}