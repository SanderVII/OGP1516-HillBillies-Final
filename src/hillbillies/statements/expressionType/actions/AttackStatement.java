package hillbillies.statements.expressionType.actions;

import hillbillies.expressions.unitType.UnitExpression;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.statements.Status;

public class AttackStatement<E extends UnitExpression> 
		extends ActionUnitStatement<E> {
	
	public AttackStatement(E unit, SourceLocation sourceLocation) {
		super(unit, sourceLocation);
	}

	@Override
	public void execute() {
		if (this.getStatus() == Status.NOTSTARTED) {
			this.setStatus(Status.EXECUTING);
			Unit unit = this.getExpression().evaluate();
			this.getSuperTask().startExplicitStatement(this);
			this.getSuperTask().getUnit().attack(unit);
		}
	}

}
