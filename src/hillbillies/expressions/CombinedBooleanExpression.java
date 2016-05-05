package hillbillies.expressions;

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
		if ((! (left instanceof BooleanExpression)) || (! (right instanceof BooleanExpression)))
			throw new IllegalArgumentException();
		else {
			this.setLeft((BooleanExpression)left);
			this.setRight((BooleanExpression)right);
		}
	}
	
	private BooleanExpression left;
	private BooleanExpression right;
	
	public Expression getLeft() {
		return this.left;
	}
	
	private void setLeft(BooleanExpression left) {
		this.left = left;
	}
	
	public Expression getRight() {
		return this.right;
	}
	
	private void setRight(BooleanExpression right) {
		this.right = right;
	}

}
