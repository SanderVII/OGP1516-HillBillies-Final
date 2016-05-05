package hillbillies.expressions;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public class AndExpression extends CombinedBooleanExpression {

	public AndExpression(Expression left, Expression right, SourceLocation sourceLocation) {
		super(left, right, sourceLocation);
	}
	
	public Boolean evaluate() {
		if (this.getLeft() instanceof BooleanExpression && this.getRight() instanceof BooleanExpression) {
			boolean leftBoolean = ((Boolean) this.getLeft().evaluate()).booleanValue();
			boolean rightBoolean = ((Boolean) this.getRight().evaluate()).booleanValue();
			if (leftBoolean && rightBoolean)
				return true;
			else
				return false;
		} else
			// TODO wat als slechte expressie? misschien beter checken bij aanmaken constructor?
			return false;
			
	}
	
}
