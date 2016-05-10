package hillbillies.expressions;

import hillbillies.model.*;
import hillbillies.part3.programs.SourceLocation;

public abstract class Expression {


	public Expression(SourceLocation sourceLocation) {
		this.setSourceLocation(sourceLocation);
	}
	
	public abstract Object evaluate(World world, Unit unit, int[] selectedCubes, SourceLocation sourceLocation);
	
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
	
	public void setSourceLocation(SourceLocation sourceLocation) {
		this.sourceLocation = sourceLocation;
	}
	
	private SourceLocation sourceLocation;
}
