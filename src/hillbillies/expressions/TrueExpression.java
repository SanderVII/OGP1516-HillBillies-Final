package hillbillies.expressions;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public class TrueExpression extends BooleanExpression {
	
	public TrueExpression(SourceLocation sourceLocation) {
		super(sourceLocation);
	}
	
	@Override
	public Boolean evaluate() {
		return true;
	}

}
