package hillbillies.expressions;

public interface IBooleanExpression extends IExpression {
	
	@Override
	public abstract Boolean evaluate();
}
