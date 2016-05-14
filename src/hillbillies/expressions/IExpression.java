package hillbillies.expressions;

public interface IExpression<E extends Expression> {
	
	public abstract E getExpression();
	
	public default boolean hasExpression() {
		return this.getExpression() != null;
	}
}
