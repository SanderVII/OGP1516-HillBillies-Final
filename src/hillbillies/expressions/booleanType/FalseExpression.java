package hillbillies.expressions.booleanType;

import hillbillies.part3.programs.SourceLocation;

public class FalseExpression extends BooleanExpression {
	
	public FalseExpression(SourceLocation sourceLocation) {
		super(sourceLocation);
	}
	
	@Override
	public Boolean evaluate() {
		return false;
	}
}
