package hillbillies.expressions.booleanType;

import hillbillies.expressions.Expression;
import hillbillies.part3.programs.SourceLocation;

public abstract class BooleanExpression extends Expression {
	
	public BooleanExpression(SourceLocation sourceLocation) {
		super(sourceLocation);
	}
	
	@Override
	public abstract Boolean evaluate();
	
}
