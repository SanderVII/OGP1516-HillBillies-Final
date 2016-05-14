package hillbillies.expressions;

import hillbillies.expressions.booleanType.BooleanExpression;

public interface ICombinedBooleanExpression<L extends BooleanExpression, R extends BooleanExpression> 
		extends ICombinedExpression<L, R> {
	
}
