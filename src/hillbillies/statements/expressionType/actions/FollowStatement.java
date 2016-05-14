package hillbillies.statements.expressionType.actions;

import hillbillies.expressions.unitType.UnitExpression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.statements.Statement;

public class FollowStatement<E extends UnitExpression> 
		extends ActionUnitStatement<E>{

	public FollowStatement(E unit, SourceLocation sourceLocation) {
		super(unit, sourceLocation);
	}

	@Override
	public void execute() {
		// TODO IMPLEMENT UNIT FOLLOW METHOD
		
	}

}
