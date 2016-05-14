package hillbillies.expressions;

public interface ICombinedExpression<L extends Expression,R extends Expression> {
	
	public abstract L getLeft();
	
	public abstract void setLeft(L left);
	
	public abstract R getRight();
	
	public abstract void setRight(R right);
}
