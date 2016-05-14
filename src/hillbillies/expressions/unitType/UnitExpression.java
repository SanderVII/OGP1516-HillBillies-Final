package hillbillies.expressions.unitType;


import hillbillies.expressions.Expression;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.tasks.TextObject;

public abstract class UnitExpression extends Expression {

	public UnitExpression(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	public abstract Unit evaluate();
}
