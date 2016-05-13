package hillbillies.expressions.unitType;

import java.util.List;

import hillbillies.model.Unit;
import hillbillies.model.World;
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
