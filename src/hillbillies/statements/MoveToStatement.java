package hillbillies.statements;

import java.util.List;

import hillbillies.expressions.Expression;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public class MoveToStatement extends ActionPositionStatement {

	public MoveToStatement(Expression position, SourceLocation sourceLocation) {
		super(position, sourceLocation);
	}


	@Override
	public void execute() {
		this.getUnit().moveTo((int[]) this.getExpression().evaluate());
		
	}

}