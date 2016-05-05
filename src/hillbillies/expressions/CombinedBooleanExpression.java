package hillbillies.model.expression;

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
		this.setLeft(left);
		this.setRight(right);
	}
	
	private Expression left;
	private Expression right;
	
	public Expression getLeft() {
		return this.left;
	}
	
	public void setLeft(Expression left) {
		this.left = left;
	}
	
	public Expression getRight() {
		return this.right;
	}
	
	public void setRight(Expression right) {
		this.right = right;
	}

}
