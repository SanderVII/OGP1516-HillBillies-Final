package hillbillies.expressions.unitType;

import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class ThisExpression extends UnitExpression {
	
	public ThisExpression(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	@Override
	public Unit evaluate() {
		return this.getUnit();
	}

}
