package hillbillies.expressions;

import hillbillies.model.*;
import hillbillies.part3.programs.SourceLocation;

public abstract class Expression {


	public Expression(SourceLocation sourceLocation) {
		
	}
	
	public abstract Object evaluate();
	
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
	
	public void setSourceLocation(SourceLocation sourceLocation) {
		this.sourceLocation = sourceLocation;
	}
	
	private SourceLocation sourceLocation;
}
