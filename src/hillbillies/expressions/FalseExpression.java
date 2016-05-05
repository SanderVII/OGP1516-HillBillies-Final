package hillbillies.expressions;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public class FalseExpression extends BooleanExpression {
	
	public FalseExpression(SourceLocation sourceLocation) {
		// TODO Auto-generated constructor stub
		super(sourceLocation);
	}
	
	@Override
	public Boolean evaluate() {
		// TODO Auto-generated method stub
		return false;
	}
}
