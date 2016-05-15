package hillbillies.expressions.booleanType;

import hillbillies.exception.IllegalVariableTypeException;
import hillbillies.expressions.IBooleanVariableExpression;
import hillbillies.expressions.ReadVariableExpression;
import hillbillies.part3.programs.SourceLocation;

/**
 * Combined boolean expressions have a left and right expression.
 * Their evaluation depends upon these subexpressions.
 * @author Thomas
 *
 */
public abstract class CombinedBooleanExpression<E extends IBooleanVariableExpression> 
		extends BooleanExpression {

	public CombinedBooleanExpression(E left, E right, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.setLeft(left);
		this.setRight(right);
	}
	
	/**
	 * Helper method to get the appropriate evaluation to use, if possible.
	 */
	protected Boolean getLeftExpressionEvaluation() throws IllegalVariableTypeException {
		if (this.getLeft() instanceof ReadVariableExpression) {
			if (((ReadVariableExpression) this.getLeft()).isValidVariableFor(this))
				return (boolean)this.getLeft().evaluate();
			else
				throw new IllegalVariableTypeException();
		}
		else
			return (boolean) this.getLeft().evaluate();
	}
	
	/**
	 * Helper method to get the appropriate evaluation to use, if possible.
	 */
	protected Boolean getRightExpressionEvaluation() throws IllegalVariableTypeException {
		if (this.getRight() instanceof ReadVariableExpression) {
			if (((ReadVariableExpression) this.getRight()).isValidVariableFor(this))
				return (boolean)this.getRight().evaluate();
			else
				throw new IllegalVariableTypeException();
		}
		else
			return (boolean) this.getRight().evaluate();
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

}
