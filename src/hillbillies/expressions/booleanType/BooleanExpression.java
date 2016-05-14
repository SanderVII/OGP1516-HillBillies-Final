package hillbillies.expressions.booleanType;

import hillbillies.expressions.Expression;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.tasks.TextObject;

public abstract class BooleanExpression extends Expression {
	
	public BooleanExpression(SourceLocation sourceLocation) {
		super(sourceLocation);
	}
	
	@Override
	public abstract Boolean evaluate();
	
}
