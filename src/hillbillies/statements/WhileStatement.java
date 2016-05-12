package hillbillies.statements;

import java.util.List;

import hillbillies.expressions.Expression;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

//TODO finish
public class WhileStatement extends ExtendedExpressionStatement {
	
	private Statement setStatement;

	public WhileStatement(Expression condition, Statement body, SourceLocation sourceLocation) {
		super(condition, body, sourceLocation);
		
	}

	@Override
	public void execute(World world, Unit unit,int[] selectedCubes) {
		// TODO Auto-generated method stub
		
	}
}
