package hillbillies.statements;

import hillbillies.expressions.Expression;
import hillbillies.statements.expressionType.ExpressionStatement;

public interface ISuperExpressionStatement<S extends ExpressionStatement, E extends Expression> 
		extends ISuperStatement<S> {

}
