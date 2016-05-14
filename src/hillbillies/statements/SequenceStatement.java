package hillbillies.statements;

import java.util.List;

import hillbillies.part3.programs.SourceLocation;

public class SequenceStatement extends Statement {

	private List<Statement> statements;

	public SequenceStatement(List<Statement> statements, SourceLocation sourceLocation) {
		super(sourceLocation);
		for (Statement statement: statements)
			statement.setSuperText(this);	
		this.setStatements(statements);
	}
	
	@Override
	public void execute() {
		this.getStatementAt(getCursor()).execute();
		
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
	
	/**
	 * Used to control which part of the sequence needs to be executed.
	 */
	private int cursor=0;
	
	public int getCursor() {
		return this.cursor;
	}
	
	protected void setCursor(int cursor) {
		this.cursor = cursor;
	}
}
