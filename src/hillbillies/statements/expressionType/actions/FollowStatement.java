package hillbillies.statements.expressionType.actions;

import hillbillies.expressions.unitType.UnitExpression;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class FollowStatement<E extends UnitExpression> 
		extends ActionUnitStatement<E>{

	public FollowStatement(E unit, SourceLocation sourceLocation) {
		super(unit, sourceLocation);
	}

	@Override
	public void execute() {
		Unit unit = this.getExpression().evaluate();
		this.getSuperTask().startExplicitStatement(this);
		this.getSuperTask().getUnit().follow(unit, true);
	}

}
