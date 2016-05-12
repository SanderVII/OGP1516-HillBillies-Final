package hillbillies.expressions;

import java.util.List;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public class CarriesItemExpression extends SingleBooleanExpression {

	public CarriesItemExpression(Expression unit, SourceLocation sourceLocation) {
		super(unit, sourceLocation);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Boolean evaluate() {
		return this.getUnit().hasItem();
	}
	
	@Override
	protected void setExpression(Expression unit) {
		if (! (unit instanceof UnitExpression))
			throw new IllegalArgumentException();
		super.setExpression(unit);
	}

}
