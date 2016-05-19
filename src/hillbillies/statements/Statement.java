package hillbillies.statements;

import java.util.List;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.expressions.Expression;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.statements.expressionType.ExpressionStatement;
import hillbillies.tasks.TextObject;

public abstract class Statement extends TextObject {

	public Statement(SourceLocation sourceLocation) {
		super(sourceLocation);
	}

	public abstract void execute();

	private Status status = Status.NOTSTARTED;

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	/**
	 * Resets the status of this statement and all of its substatements to their default value.
	 */
	public void resetStatus() {
		this.setStatus(Status.NOTSTARTED);
		if (this instanceof ISubStatement) {
			List<Statement> subStatements = ((ISubStatement) this).getSubStatements();
			for (Statement sub: subStatements)
				sub.resetStatus();
		}	
	}
	
	/**
	 * Check if this statements and all of its sub-textobjects are correctly formed.
	 */
	public boolean isWellFormed() {
		if (! this.isWellFormed())
			return false;
		if (this instanceof ISubStatement) {
			List<Statement> subStatements = ((ISubStatement) this).getSubStatements();
			for (Statement sub: subStatements)
				if (! sub.isWellFormed())
					return false;
		}
		if (this instanceof ExpressionStatement<?>) {
			if (! ((ExpressionStatement<?>) this).getExpression().isWellFormed())
				return false;
		}
		return true;
	}
	
	/*
	 * NOTE: Statements can only have another statement as its superstatement.
	 */
	
	private Statement superText;
	
	public Statement getSuperText() {
		return this.superText;
	}
	
	public boolean hasSuperText() {
		return this.superText != null;
	}
	
	public void setSuperText(Statement superText) {
		this.superText = superText;
	}

	/**
	 * Returns the super task of this statement. This is either the associated
	 * task, or the super task of the super statement.
	 * 
	 * @return
	 */
	public Task getSuperTask() {
		if (this.hasTask())
			return this.getTask();
		else
			return this.getSuperText().getSuperTask();
	}
	
	public Unit getUnit() {
		return this.getSuperTask().getUnit();
	}
	
	/**
	 * Creates a copy of this statement. However, no task is copied.
	 * All status types are reset.
	 */
	@Override
	public Statement clone() throws CloneNotSupportedException {
		Statement cloned = (Statement) super.clone();
		cloned.resetStatus();
		return cloned;
	}

	// =================================================================================================
	// Methods concerning the task which holds this statement.
	// =================================================================================================

	/**
	 * Return the task which executes this statement.
	 */
	@Basic
	@Raw
	public Task getTask() {
		return this.task;
	}

	/**
	 * Check if this statement is being executed by a task.
	 */
	public boolean hasTask() {
		return this.getTask() != null;
	}

	/**
	 * Checks whether this statement can have the given task as its task.
	 */
	@Raw
	public boolean canHaveAsTask(@Raw Task task) {
		return true;
	}

	/**
	 * Checks whether this statement has a proper task to which it is attached.
	 */
	public boolean hasProperTask() {
		return (this.canHaveAsTask(this.getTask())
				&& ((this.getTask() == null) || (this.getTask().getStatement() == this)));
	}

	/**
	 * Sets the task to which this statement is attached to to the given task.
	 */
	public void setTask(@Raw Task task) throws IllegalArgumentException {
		if (!this.canHaveAsTask(task))
			throw new IllegalArgumentException();
		this.task = task;
	}

	/**
	 * A variable referencing the task to which this statement is attached.
	 */
	private Task task;

}