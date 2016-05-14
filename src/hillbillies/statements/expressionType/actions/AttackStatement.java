package hillbillies.statements.expressionType.actions;

import hillbillies.expressions.unitType.UnitExpression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.statements.Statement;

public class AttackStatement<E extends UnitExpression> 
		extends ActionUnitStatement<E> {
	
	public AttackStatement(E unit, SourceLocation sourceLocation) {
		super(unit, sourceLocation);
	}

	@Override
	public void execute() {
		this.getUnit().attack(this.getExpression().evaluate());
	}

}
