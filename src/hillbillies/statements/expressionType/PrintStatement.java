package hillbillies.statements.expressionType;

import hillbillies.expressions.Expression;
import hillbillies.part3.programs.SourceLocation;

public class PrintStatement<E extends Expression> extends ExpressionStatement<E> {

	public PrintStatement(E value, SourceLocation sourceLocation) {
		super(value,sourceLocation);
	}

	@Override
	public void execute() {
		System.out.println(this.getExpression().evaluate());
	}

}
