package hillbillies.expressions.positionType;

import hillbillies.expressions.Expression;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public abstract class ExtendedPositionExpression extends CubePositionExpression {

	public ExtendedPositionExpression(Expression expression, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.setExpression(expression);
	}
	
	private Expression expression;
	
	public Expression getExpression() {
		return this.expression;
	}
	
	protected void setExpression(Expression expression) {
		this.expression = expression;
	}

}
