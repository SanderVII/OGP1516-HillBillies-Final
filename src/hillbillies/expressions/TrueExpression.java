package hillbillies.expressions;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public class TrueExpression extends BooleanExpression {
	
	public TrueExpression(SourceLocation sourceLocation) {
		super(sourceLocation);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Boolean evaluate() {
		// TODO Auto-generated method stub
		return true;
	}

}
