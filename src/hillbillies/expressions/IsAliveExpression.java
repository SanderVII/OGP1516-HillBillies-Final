package hillbillies.expressions;

import java.util.List;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public class IsAliveExpression extends SingleBooleanUnitExpression {


	public IsAliveExpression(Expression unit, SourceLocation sourceLocation) {
		super(unit, sourceLocation);
	}

	@Override
	public Boolean evaluate() {
		return ! ((Unit) this.getExpression().evaluate()).isTerminated();
	}

}
