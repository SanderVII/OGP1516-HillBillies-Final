package hillbillies.expressions;

import java.util.List;
import java.util.Set;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.util.Position;

public class IsPassableExpression extends SingleBooleanPositionExpression{


	public IsPassableExpression(Expression position, SourceLocation sourceLocation) {
		super(position, sourceLocation);
	}

	@Override
	public Boolean evaluate() {
		return this.getUnit().getWorld().getCube(
				(int[]) this.getExpression().evaluate()).isPassable();
	}
}
