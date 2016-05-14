package hillbillies.expressions;

import hillbillies.expressions.booleanType.BooleanExpression;

public interface IBooleanExpression<E extends BooleanExpression> extends IExpression<E> {
	
	public abstract E getExpression();
	
	public default boolean hasExpression() {
		return this.getExpression() != null;
	}
	
	abstract void setExpression(E expression);
}
