package hillbillies.statements;

import hillbillies.expressions.Expression;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public class AttackStatement extends ActionUnitStatement {
	
	public AttackStatement(Expression unit, SourceLocation sourceLocation) {
		super(unit, sourceLocation);
	}

	@Override
	public void execute() {
		this.getUnit().attack((Unit) this.getExpression().evaluate());
	}

}
