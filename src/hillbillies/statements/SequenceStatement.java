package hillbillies.statements;

import java.util.List;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public class SequenceStatement extends Statement {

	private List<Statement> statements;

	public SequenceStatement(List<Statement> statements, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.setStatements(statements);
	}
	
	@Override
	public void execute(World world, Unit unit, int[] selectedCubes) {
		this.getStatementAt(getCursor()).execute(world, unit, selectedCubes);
		
		this.setCursor(getCursor()+1);
	}
	
	public int getNbStatements() {
		return this.statements.size();
	}
	
	public Statement getStatementAt(int index) {
		return statements.get(index);
	}

	protected void setStatements(List<Statement> statements) {
		this.statements = statements;
	}
	
	private int cursor=0;
	
	public int getCursor() {
		return this.cursor;
	}
	
	protected void setCursor(int cursor) {
		this.cursor = cursor;
	}
}
