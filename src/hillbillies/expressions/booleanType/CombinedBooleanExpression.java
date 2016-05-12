package hillbillies.expressions.booleanType;

import hillbillies.expressions.Expression;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

/**
 * Combined boolean expressions have a left and right expression.
 * Their evaluation depends upon these subexpressions.
 * @author Thomas
 *
 */
public abstract class CombinedBooleanExpression extends BooleanExpression {

	public CombinedBooleanExpression(Expression left, Expression right, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.setLeft((BooleanExpression)left);
		this.setRight((BooleanExpression)right);
	}
	
	private BooleanExpression left;
	private BooleanExpression right;
	
	public Expression getLeft() {
		return this.left;
	}
	
	private void setLeft(BooleanExpression left) {
		if (! (left instanceof BooleanExpression))
			throw new IllegalArgumentException();
		this.left = left;
	}
	
	public Expression getRight() {
		return this.right;
	}
	
	private void setRight(BooleanExpression right) {
		if (! (right instanceof BooleanExpression))
			throw new IllegalArgumentException();
		this.right = right;
	}

}
