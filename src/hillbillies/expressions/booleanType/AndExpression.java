package hillbillies.expressions.booleanType;

import hillbillies.expressions.Expression;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public class AndExpression extends CombinedBooleanExpression {

	public AndExpression(Expression left, Expression right, SourceLocation sourceLocation) {
		super(left, right, sourceLocation);
	}
	
	public Boolean evaluate() {
		boolean leftBoolean = ((Boolean) this.getLeft().evaluate()).booleanValue();
		boolean rightBoolean = ((Boolean) this.getRight().evaluate()).booleanValue();
		if (leftBoolean && rightBoolean)
			return true;
		else
			return false;
	}
}
