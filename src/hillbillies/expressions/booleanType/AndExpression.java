package hillbillies.expressions.booleanType;

import hillbillies.part3.programs.SourceLocation;

public class AndExpression<E extends BooleanExpression> 
		extends CombinedBooleanExpression<E> {

	public AndExpression(E left, E right, SourceLocation sourceLocation) {
		super(left, right, sourceLocation);
	}

	public Boolean evaluate() {
		boolean leftBoolean = this.getLeft().evaluate();
		boolean rightBoolean = this.getRight().evaluate();
		if (leftBoolean && rightBoolean)
			return true;
		else
			return false;
	}
}
