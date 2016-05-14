package hillbillies.statements;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
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

	private Status status;

	public Status getStatus() {
		return this.status;
	}

	protected void setStatus(Status status) {
		this.status = status;
	}
	
//	private Statement superStatement;
//
//	@Override
//	public Statement getSuperStatement() {
//		return this.superStatement;
//	}
//
//	@Override
//	public void setSuperStatement(Statement statement) {
//		this.superStatement = statement;
//	}
	
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
	 * 
	 * @return True if the task executing this statement is effective. | result
	 *         == (getTask() != null)
	 */
	public boolean hasTask() {
		return this.getTask() != null;
	}

	/**
	 * Checks whether this statement can have the given task as its task.
	 * 
	 * @param task
	 *            The task to check.
	 * @return If this statement is terminated, true if the given task is not
	 *         effective. | if (isTerminated()) | then result == (task == null)
	 *         Else if the task is effective, true if the given task is not yet
	 *         terminated. | else if (task!= null) | then result ==
	 *         (!task.isTerminated()) Else, return true. | else | then result ==
	 *         true
	 */
	@Raw
	public boolean canHaveAsTask(@Raw Task task) {
		// if (this.isTerminated)
		// return task == null;
		// else if (task != null)
		// return !task.isTerminated();
		// else
		return true;
	}

	/**
	 * Checks whether this statement has a proper task to which it is attached.
	 * 
	 * @return True if and only if this statement can have the task to which it
	 *         is attached as its task, and if that task is either not effective
	 *         or has this statement as one of its statements. |
	 *         (this.canHaveAsTask(this.getTask()) | && ( (this.getTask() ==
	 *         null) || (this.getTask().hasAsStatement(this))) )
	 */
	public boolean hasProperTask() {
		return (this.canHaveAsTask(this.getTask())
				&& ((this.getTask() == null) || (this.getTask().getStatement() == this)));
	}

	/**
	 * Sets the task to which this statement is attached to to the given task.
	 * 
	 * @param task
	 *            The task to attach this statement to.
	 * @post This statement references the given task as the task to which it is
	 *       attached. | new.getTask() == task
	 * @throws IllegalArgumentException
	 *             This statement cannot have the given task as its task. | (!
	 *             canHaveAsTask(task))
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