package hillbillies.statements.expressionType;

import java.util.Arrays;

import hillbillies.expressions.Expression;
import hillbillies.expressions.positionType.PositionExpression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.statements.Status;

public class PrintStatement<E extends Expression> extends ExpressionStatement<E> {

	public PrintStatement(E value, SourceLocation sourceLocation) {
		super(value,sourceLocation);
	}

	@Override
	public void execute() {
		if (this.getStatus() == Status.NOTSTARTED) {
			this.setStatus(Status.EXECUTING);
			if (this.getExpression() instanceof PositionExpression) {
				System.out.print(Arrays.toString(
						(int[]) this.getExpression().evaluate())+ "\n");
			}
			else
				System.out.print(this.getExpression().evaluate() + "\n");
			this.setStatus(Status.DONE);
		}
	}

}
