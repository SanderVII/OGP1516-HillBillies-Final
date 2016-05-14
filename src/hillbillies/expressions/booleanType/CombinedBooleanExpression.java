package hillbillies.expressions.booleanType;

import hillbillies.part3.programs.SourceLocation;

/**
 * Combined boolean expressions have a left and right expression.
 * Their evaluation depends upon these subexpressions.
 * @author Thomas
 *
 */
public abstract class CombinedBooleanExpression<E extends BooleanExpression> 
		extends BooleanExpression {

	public CombinedBooleanExpression(E left, E right, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.setLeft(left);
		this.setRight(right);
	}
	
	private BooleanExpression left;
	private BooleanExpression right;
	
	public E getLeft() {
		return (E) this.left;
	}
	
	public void setLeft(E left) {
		this.left = left;
	}
	
	public E getRight() {
		return (E) this.right;
	}
	
	public void setRight(E right) {
		this.right = right;
	}

}
