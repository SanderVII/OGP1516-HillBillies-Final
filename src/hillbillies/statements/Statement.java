package hillbillies.statements;

import java.util.List;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public abstract class Statement {
	
	public Statement(SourceLocation sourceLocation) {
		this.setSourceLocation(sourceLocation);
	}
	
	public abstract void execute(World world, Unit unit,List<int[]> selectedCubes, SourceLocation sourceLocation);
	
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
	
	public void setSourceLocation(SourceLocation sourceLocation) {
		this.sourceLocation = sourceLocation;
	}
	
	private SourceLocation sourceLocation;
}