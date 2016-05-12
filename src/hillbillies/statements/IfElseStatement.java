package hillbillies.statements;

import java.util.List;

import hillbillies.expressions.Expression;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public class IfElseStatement extends ExtendedExpressionStatement {

	public IfElseStatement(Expression condition, Statement ifBody, Statement elseBody, SourceLocation sourceLocation) {
		super(condition, ifBody, sourceLocation);
	}

	@Override
	public void execute(World world, Unit unit,int[] selectedCubes) {
		if ((boolean) this.getExpression().evaluate(world, unit, selectedCubes))
			this.getStatement().execute(world, unit, selectedCubes);
		else
			this.getElseBody().execute(world, unit, selectedCubes);
		this.setStatus(Status.DONE);
	}
	
	private Statement elseBody;
	
	public Statement getElseBody() {
		return this.elseBody;
	}
	
	protected void setElseBody(Statement elseBody) {
		this.elseBody = elseBody;
	}

}
