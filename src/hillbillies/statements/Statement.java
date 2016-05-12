package hillbillies.statements;

import java.util.List;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public abstract class Statement {
	
	public Statement(SourceLocation sourceLocation) {
		this.setSourceLocation(sourceLocation);
	}
	
//	public abstract void execute(World world, Unit unit,List<int[]> selectedCubes, SourceLocation sourceLocation);
	
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
	
	public void setSourceLocation(SourceLocation sourceLocation) {
		this.sourceLocation = sourceLocation;
	}
	
	private SourceLocation sourceLocation;

	public abstract void execute(World world, Unit unit, int[] selectedCubes);
	
	public Unit getUnit() {
		return this.getTask().getUnit();
	}
	
	// =================================================================================================
	// Methods concerning the task which holds this statement.
	// =================================================================================================
	
	/**
	 * Return the task which executes this statement.
	 */
	@Basic @Raw
	public Task getTask() {
		return this.task;
	}
	
	/**
	 * Check if this statement is being executed by a task.
	 * @return	True if the task executing this statement is effective.
	 * 			| result == (getTask() != null)
	 */
	public boolean hasTask() {
		return this.getTask() != null;
	}
	
	/**
	 * Checks whether this statement can have the given task as its task.
	 * 
	 * @param 	task
	 *			The task to check.
	 * @return	If this statement is terminated, true if the given task
	 * 			is not effective.
	 * 			| if (isTerminated())
	 * 			|	then result == (task == null)
	 * 			Else if the task is effective, true if the given task 
	 * 			is not yet terminated.
	 * 			| else if (task!= null)
	 * 			|	then result == (!task.isTerminated())
	 * 			Else, return true.
	 * 			| else
	 * 			|	then result == true
	 */
	@Raw
	public boolean canHaveAsTask(@Raw Task task) {
//		if (this.isTerminated)
//			return task == null;
//		else if (task != null)
//			return !task.isTerminated();
//		else
			return true;
	}
	
	/**
	 * Checks whether this statement has a proper task to which it is attached.
	 * 
	 * @return	True if and only if this statement can have the task to which it
	 * 			is attached as its task, and if that task is either not
	 * 			effective or has this statement as one of its statements.
	 * 			| (this.canHaveAsTask(this.getTask()) 
	 *			|  && ( (this.getTask() == null) || (this.getTask().hasAsStatement(this))) )
	 */
	public boolean hasProperTask() {
		return ( this.canHaveAsTask(this.getTask()) 
				&& ( ( this.getTask() == null) || (this.getTask().getStatement() == this) ) );
	}
	
	/**
	 * Sets the task to which this statement is attached to to the given task.
	 * 
	 * @param	task
	 * 			The task to attach this statement to.
	 * @post	This statement references the given task as the task to which it is attached.
	 * 			| new.getTask() == task
	 * @throws	IllegalArgumentException
	 * 			This statement cannot have the given task as its task.
	 * 			| (! canHaveAsTask(task))
	 */
	public void setTask(@Raw Task task) throws IllegalArgumentException {
		if (! this.canHaveAsTask(task))
			throw new IllegalArgumentException();
		this.task = task;
	}
	
	/**
	 * A variable referencing the task to which this statement is attached.
	 */
	private Task task;
	
}