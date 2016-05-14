package hillbillies.expressions.booleanType;

import hillbillies.part3.programs.SourceLocation;

public class TrueExpression extends BooleanExpression {
	
	public TrueExpression(SourceLocation sourceLocation) {
		super(sourceLocation);
	}
	
	@Override
	public Boolean evaluate() {
		return true;
	}

}
