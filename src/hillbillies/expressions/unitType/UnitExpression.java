package hillbillies.expressions.unitType;


import hillbillies.expressions.Expression;
import hillbillies.expressions.IUnitVariableExpression;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;

public abstract class UnitExpression extends Expression implements IUnitVariableExpression {

	public UnitExpression(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	public abstract Unit evaluate();
	
//	@Override
//	public Class<? extends UnitExpression> getType() {
//		return this.getClass();
//	}
}
