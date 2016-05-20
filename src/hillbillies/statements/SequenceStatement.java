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
		this.setStatus(Status.EXECUTING);
		Status status = this.getStatementAt(getCursor()).getStatus();
		switch (status) {
			case NOTSTARTED:
				this.getStatementAt(getCursor()).execute();
				break;
			case DONE:
				if (this.getCursor() == (getNbStatements() - 1))
					this.setStatus(Status.DONE);
				else
					this.setCursor(getCursor()+1);
				break;
			case FAILED:
				this.setStatus(Status.FAILED);
				break;
			case EXECUTING:
				this.getStatementAt(getCursor()).execute();
				break;
			default:
				break;
		}
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
	
	@Override
	public SequenceStatement clone() throws CloneNotSupportedException {
		SequenceStatement cloned = (SequenceStatement) super.clone();
		List<Statement> clonedList = new ArrayList<>();
		for (Statement statement: this.getSubStatements()) {
			Statement clonedStatement = statement.clone();
			clonedList.add(clonedStatement);
			clonedStatement.setSuperText(cloned);
			
		}
		cloned.setStatements(clonedList);
		return cloned;
	}
}
