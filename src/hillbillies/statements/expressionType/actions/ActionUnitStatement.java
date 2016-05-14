package hillbillies.statements.expressionType.actions;

import hillbillies.expressions.unitType.UnitExpression;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.statements.Statement;

public abstract class ActionUnitStatement<E extends UnitExpression> 
		extends ActionStatement<E> {

	public ActionUnitStatement(E expression, SourceLocation sourceLocation) {
		super(expression, sourceLocation);
	}

}
