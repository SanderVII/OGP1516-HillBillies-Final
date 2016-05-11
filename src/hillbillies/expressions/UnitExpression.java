package hillbillies.expressions;


import java.util.List;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public abstract class UnitExpression extends Expression {
	
//	private Unit unit;

	public UnitExpression(SourceLocation sourceLocation) {
		super(sourceLocation);
//		this.setUnit(unit);
		// TODO Auto-generated constructor stub
	}

	public abstract Unit evaluate(World world, Unit unit, int[] selectedCubes);
	
//	public Unit getUnit() {
//		return this.unit;
//	}
//	
//	private void setUnit(Unit unit) {
//		this.unit = unit;
//	}
}
