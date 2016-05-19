package hillbillies.expressions.booleanType;

import hillbillies.part3.programs.SourceLocation;

/**
 * Combined boolean expressions have a left and right expression.
 * Their evaluation depends upon these subexpressions.
 */
public abstract class CombinedBooleanExpression<E extends BooleanExpression> 
		extends BooleanExpression {

	public CombinedBooleanExpression(E left, E right, SourceLocation sourceLocation) {
		super(sourceLocation);
		left.setSuperText(this);
		this.setLeft(left);
		right.setSuperText(this);
		this.setRight(right);
	}
	
	private E left;
	private E right;
	
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
	
	public CombinedBooleanExpression<E> clone() throws CloneNotSupportedException {
		CombinedBooleanExpression<E> cloned = (CombinedBooleanExpression<E>) super.clone();
		E leftCloned = (E) this.getLeft().clone();
		E rightCloned = (E) this.getRight().clone();
		leftCloned.setSuperText(cloned);
		cloned.setLeft(leftCloned);
		rightCloned.setSuperText(cloned);
		cloned.setRight(rightCloned);
		return cloned;
	}

}
