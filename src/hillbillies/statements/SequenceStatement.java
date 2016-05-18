package hillbillies.statements;

import java.util.ArrayList;
import java.util.List;

import hillbillies.part3.programs.SourceLocation;

public class SequenceStatement extends Statement implements ISubStatement {

	private List<Statement> statements;

	public SequenceStatement(List<Statement> statements, SourceLocation sourceLocation) {
		super(sourceLocation);
		for (Statement statement: statements)
			statement.setSuperText(this);	
		this.setStatements(statements);
	}
	
	@Override
	public void execute() {
		this.setStatus(Status.SEQUENCE);
		Status status = this.getStatementAt(getCursor()).getStatus();
		if (status == Status.DONE) {
			if (this.getCursor() == (getNbStatements() - 1))
				this.setStatus(Status.DONE);
			else
				this.setCursor(getCursor()+1);
		}
		else if (status == Status.FAILED)
			this.setStatus(Status.FAILED);
		else
			this.getStatementAt(getCursor()).execute();
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
		assert cursor < this.getNbStatements();
		this.cursor = cursor;
	}

	@Override
	public List<Statement> getSubStatements() {
		return new ArrayList<>(this.statements);
	}
}
