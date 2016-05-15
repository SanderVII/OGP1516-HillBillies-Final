package hillbillies.expressions;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.tasks.TextObject;

/**
 * The general Expression interface which all expressions implement.
 */
public interface IExpression {
	
//	/**
//	 * Mainly used for determining the type of variable expressions.
//	 * @return
//	 */
//	public default Class<? extends Expression> getType() {
//		return Expression.class;
//	}
	
	public abstract Object evaluate();
	
	/**
	 * The superText is the super object which has this expression as an argument. 
	 * For expressions, this can be a Statement or another expression.
	 */
	public abstract TextObject getSuperText();
	
	public default boolean hasSuperText() {
		return this.getSuperText() != null;
	}
	
	public void setSuperText(TextObject superText);
	
	/**
	 * Return the general task(program) this expression is part of.
	 */
	public abstract Task getSuperTask();
	
	/**
	 * Return the unit which is executing the task(program) of this expression.
	 */
	public default Unit getUnit() {
		return this.getSuperTask().getUnit();
	}
}
