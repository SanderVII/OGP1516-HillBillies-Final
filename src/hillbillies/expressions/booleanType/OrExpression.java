package hillbillies.expressions.booleanType;

import hillbillies.part3.programs.SourceLocation;

public class OrExpression<E extends BooleanExpression> 
		extends CombinedBooleanExpression<E> {

	public OrExpression(E left, E right, SourceLocation sourceLocation) {
		super(left, right, sourceLocation);
	}
	
	public Boolean evaluate() throws IllegalArgumentException {
		boolean leftBoolean = this.getLeft().evaluate();
		boolean rightBoolean = this.getRight().evaluate();
		if (leftBoolean || rightBoolean)
			return true;
		else
			return false;
	}
}
