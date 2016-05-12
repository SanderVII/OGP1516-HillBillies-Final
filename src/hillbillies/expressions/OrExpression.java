package hillbillies.expressions;

import java.util.List;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public class OrExpression extends CombinedBooleanExpression{


	public OrExpression(Expression left, Expression right, SourceLocation sourceLocation) {
		super(left, right, sourceLocation);
	}

	@Override
	public Boolean evaluate() {
		boolean leftBoolean = ((Boolean) this.getLeft().evaluate()).booleanValue();
		boolean rightBoolean = ((Boolean) this.getRight().evaluate()).booleanValue();
		if (leftBoolean || rightBoolean)
			return true;
		else
			return false;
	}
}
