package hillbillies.model;

import java.util.*;

import be.kuleuven.cs.som.annotate.*;
import hillbillies.expressions.Expression;
import hillbillies.statements.Statement;
import hillbillies.statements.Status;
import hillbillies.statements.expressionType.actions.ActionStatement;

/**
 * A class of tasks, involving a name, a priority and 
 * a non-empty list of activities. Each task can be assigned to multiple schedulers,
 * but only one unit can execute a certain task at a time.
 * 
 * @invar	The name of each task must be a valid name for any task.
 *				| isValidName(getName())
 * @invar  The priority of each task must be a valid priority for any task.
 *				| isValidPriority(getPriority())
 * @invar  Each task must have a proper unit.
 *				| hasProperUnit()
 * @invar   Each task must have proper schedulers.
 *				| hasProperSchedulers()
 * @invar   Each task must have proper variables.
 *				| hasProperVariables()
 *
 * @author 	Sander Mergan, Thomas Vranken
 * @version	3.0
 * 
 * @note	Must be documented both formally and informally.
 */
public class Task implements Comparable<Task> {
	
	/**
	 * Initializes this task with the given name, priority, activities and selected cube 
	 * 
	 * @param	name
	 *				The name for this new task.
	 * @param	priority
	 *				The priority for this new task.
	 * @param	activities
	 *				The activities for this new task.
	 * @param	selectedCube
	 * 				The selected cube for this new task.
	 * 
	 * @effect	Sets the name of this new task to the given name.
	 *				| this.setName(name)
	 * @effect	Sets the priority of this new task to the given priority.
	 *				| this.setPriority(priority)
	 * @effect	Sets the task of the given activities to this new task.
	 *				| activities.setTask(this)
	 * @effect	Sets the statement of this new task to the given activities.
	 *				| this.setStatement(activities)
	 * @effect	Sets the position of this new task to the given position.
	 *				| this.setCoordinates(selectedCube)
	 */
	public Task(String name, int priority, Statement activities, int[] selectedCube) throws IllegalArgumentException {
		//NOTE if multiple statements exist, these should be put together in one sequence statement.
		this.setName(name);
		this.setPriority(priority);
		activities.setTask(this);
		this.setStatement(activities);
		this.setCoordinates(selectedCube);
	}
	
	/**
	 * Initializes this task with the given name , priority, activities and no selected cube.
	 * 
	 * @param	name
	 *				The name for this new task.
	 * @param	priority
	 *				The priority for this new task.
	 * @param	activities
	 *				The activities for this new task.
	 *
	 * @effect Initializes this task with the given name , priority, activities and null as selected cube.
	 *				| this(name, priority, activities, null)
	 */
	public Task(String name, int priority, Statement activities) {
		this(name, priority, activities, null);
	}
	
	// =================================================================================================
	// Methods concerning the termination of this task.
	// =================================================================================================
	
	/**
	 * Terminate this task.
	 *
	 * @post   This task  is terminated.
	 *       | new.isTerminated()
	 * @post   ...
	 *       | ...
	 */
	//TODO finish
	 public void terminate() {
		 this.isTerminated = true;
		 for (Scheduler scheduler: this.getSchedulers()) {
			 this.removeScheduler(scheduler);
			 scheduler.removeTask(this);
		 }
		 Unit unit = this.getUnit();
		 this.setUnit(null);
		 unit.setTask(null);
	 }
	 
	 /**
	  * Return a boolean indicating whether or not this task
	  * is terminated.
	  */
	 @Basic @Raw
	 public boolean isTerminated() {
		 return this.isTerminated;
	 }
	 
	 /**
	  * Variable registering whether this task is terminated.
	  */
	 private boolean isTerminated = false;
	 
	
	// =================================================================================================
	// Methods concerning the name.
	// =================================================================================================
	
	/**
	 * Return the name of this task.
	 */
	@Basic @Raw
	public String getName() {
		return this.name;
	}
	
	/**
	 * Check whether the given name is a valid name for
	 * any task.
	 *  
	 * @param  name
	 *         The name to check.
	 * @return Always true, any string is allowed.
	 *       | result == true
	*/
	public static boolean isValidName(String name) {
		return true;
	}
	
	/**
	 * Set the name of this task to the given name.
	 * 
	 * @param  name
	 *         The new name for this task.
	 * @post   The name of this new task is equal to
	 *         the given name.
	 *       | new.getName() == name
	 * @throws IllegalArgumentException
	 *         The given name is not a valid name for any
	 *         task.
	 *       | ! isValidName(getName())
	 */
	@Raw
	public void setName(String name) 
			throws IllegalArgumentException {
		if (! isValidName(name))
			throw new IllegalArgumentException();
		this.name = name;
	}
	
	/**
	 * Variable registering the name of this task.
	 */
	private String name;
	
	// =================================================================================================
	// Methods concerning the priority.
	// =================================================================================================
	
	/**
	 * Return the priority of this task.
	 */
	@Basic @Raw
	public int getPriority() {
		return this.priority;
	}
	
	/**
	 * Check whether the given priority is a valid priority for
	 * any task.
	 *  
	 * @param  priority
	 *         The priority to check.
	 * @return Always true, any integer (even negative) is allowed.
	 *       | result == true
	 * @note	Values above the maximal integer value and below the minimal integer
	 * 			value behave as usual in java.
	 */
	public static boolean isValidPriority(int priority) {
		return true;
	}
	
	/**
	 * Set the priority of this task to the given priority.
	 * 
	 * @param  priority
	 *         The new priority for this task.
	 * @post   The priority of this new task is equal to
	 *         the given priority.
	 *       | new.getPriority() == priority
	 * @throws IllegalArgumentException
	 *         The given priority is not a valid priority for any
	 *         task.
	 *       | ! isValidPriority(getPriority())
	 */
	@Raw
	private void setPriority(int priority) 
			throws IllegalArgumentException {
		if (! isValidPriority(priority))
			throw new IllegalArgumentException();
		this.priority = priority;
	}
	
	/**
	 * Variable registering the priority of this task.
	 */
	private int priority;
	
	// =================================================================================================
	// Methods concerning the selected position.
	// =================================================================================================
	
	//TODO finish doc and code
	/** TO BE ADDED TO CLASS HEADING
	 * @invar  The position of each task must be a valid position for any
	 *         task.
	 *       | isValidPosition(getPosition())
	 */
	
	/**
	 * Return the position of this task.
	 */
	@Basic @Raw
	public int[] getPosition() {
		return this.position;
	}
	
	/**
	 * Check whether the given position is a valid position for
	 * any task.
	 *  
	 * @param  coordinates
	 *         The position to check.
	 * @return 
	 *       | result == 
	 */
	//TODO doc
	public static boolean isValidCoordinates(int[] coordinates) {
		return true;
	}
	
	/**
	 * Set the position of this task to the given position.
	 * 
	 * @param  position
	 *         The new position for this task.
	 * @post   The position of this new task is equal to
	 *         the given position.
	 *       | new.getPosition() == position
	 * @throws IllegalArgumentException
	 *         The given position is not a valid position for any
	 *         task.
	 *       | ! isValidPosition(getPosition())
	 */
	@Raw
	public void setCoordinates(int[] position) 
			throws IllegalArgumentException {
		if (! isValidCoordinates(position))
			throw new IllegalArgumentException();
		this.position = position;
	}
	
	/**
	 * Variable registering the position of this task.
	 */
	private int[] position;
	
	// =================================================================================================
	// Methods concerning whether this task is finished.
	// =================================================================================================
	
	/**
	 * Return the finished property of this task.
	 */
	@Basic @Raw
	public boolean getFinished() {
		return this.finished;
	}
	
	/**
	 * Set the finished property of this task to the given finished property.
	 * 
	 * @param  finished
	 *         The new finished property for this task.
	 * @post   The finished property of this new task is equal to
	 *         the given finished property.
	 *       | new.getFinished() == finished
	 */
	@Raw
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	
	/**
	 * Variable registering the finished property of this task.
	 * The default value is false.
	 */
	private boolean finished = false;
	
	// =================================================================================================
	// Methods concerning the unit which executes this task.
	// =================================================================================================
	
	/**
	 * Return the unit which executes this task.
	 */
	@Basic @Raw
	public Unit getUnit() {
		return this.unit;
	}
	
	/**
	 * Check if this task is being executed by a unit.
	 * @return	True if the unit executing this task is effective.
	 * 			| result == (getUnit() != null)
	 */
	public boolean hasUnit() {
		return this.getUnit() != null;
	}
	
	/**
	 * Checks whether this task can have the given unit as its unit.
	 * 
	 * @param 	unit
	 *			The unit to check.
	 * @return	If this task is terminated, true if the given unit
	 * 			is not effective.
	 * 			| if (isTerminated())
	 * 			|	then result == (unit == null)
	 * 			Else if the unit is effective, true if the given unit 
	 * 			is not yet terminated.
	 * 			| else if (unit!= null)
	 * 			|	then result == (!unit.isTerminated())
	 * 			Else, return true.
	 * 			| else
	 * 			|	then result == true
	 */
	@Raw
	public boolean canHaveAsUnit(@Raw Unit unit) {
		if (this.isTerminated)
			return unit == null;
		else if (unit != null)
			return !unit.isTerminated();
		else
			return true;
	}
	
	/**
	 * Checks whether this task has a proper unit to which it is attached.
	 * 
	 * @return	True if and only if this task can have the unit to which it
	 * 			is attached as its unit, and if that unit is either not
	 * 			effective or has this task as one of its tasks.
	 * 			| (this.canHaveAsUnit(this.getUnit()) 
	 *			|  && ( (this.getUnit() == null) || (this.getUnit().hasAsTask(this))) )
	 */
	public boolean hasProperUnit() {
		return ( this.canHaveAsUnit(this.getUnit()) 
				&& ( ( this.getUnit() == null) || (this.getUnit().getTask() == this) ) );
	}
	
	/**
	 * Sets the unit to which this task is attached to to the given unit.
	 * 
	 * @param	unit
	 * 			The unit to attach this task to.
	 * @post	This task references the given unit as the unit to which it is attached.
	 * 			| new.getUnit() == unit
	 * @throws	IllegalArgumentException
	 * 			This task cannot have the given unit as its unit.
	 * 			| (! canHaveAsUnit(unit))
	 */
	public void setUnit(@Raw Unit unit) throws IllegalArgumentException {
		if (! this.canHaveAsUnit(unit))
			throw new IllegalArgumentException();
		this.unit = unit;
	}
	
	/**
	 * A variable referencing the unit to which this task is attached.
	 */
	private Unit unit;
	
	/**
	 * Assigns this task to the given unit.
	 * 
	 * @param 	unit
	 * 			The unit to assign this task to.
	 * @effect	This task references the unit as its unit.
	 * 			| setUnit(unit)
	 * 			The unit references this task as its task.
	 * 			| unit.setTask(this)
	 */
	public void assignTo(Unit unit) throws IllegalArgumentException {
		this.setUnit(unit);
		unit.setTask(this);
	}
	
	/**
	 * Stop the execution of this task, if necessary.
	 * The task is send back to the scheduler of the unit faction.
	 * 
	 * @post	This task no longer references a unit.
	 * 			| ! new.hasUnit()
	 * @post	The unit of this task no longer references this task.
	 * 			| ! this.getUnit().hasTask()
	 * @effect	The status of this task's statement and its substatements are reset.
	 * 			| this.getStatement().resetStatus()
	 */
	//NOTE: total programming is allowed.
	public void stopExecuting() throws IllegalArgumentException {
		if (this.hasUnit()) {
			Unit unit = this.getUnit();
			this.setUnit(null);
			unit.setTask(null);
			this.getStatement().resetStatus();
		}
	}
	
	// =================================================================================================
	// Methods concerning the statement of this task.
	// =================================================================================================
	
	/**
	 * Return the statement of this task.
	 */
	@Basic @Raw
	public Statement getStatement() {
		return this.statement;
	}
	
	/**
	 * Check whether this task has a statement.
	 * 
	 * @return	True if the statement of this task is effective.
	 * 				| result == (getStatement() != null)
	 */
	public boolean hasStatement() {
		return this.getStatement() != null;
	}
	
	/**
	 * Checks whether this task has a proper statement attached to it.
	 * 
	 * @return	True if and only if the statement of this task does not reference
	 * 				an effective statement, or that statement references this task
	 * 				as its task.
	 * 				| result == ( (this.getStatement() == null) || 
	 * 				|				(this.getStatement().getTask() == this) )
	 */
	public boolean hasProperStatement() {
		return ( (this.getStatement() == null) || (this.getStatement().getTask() == this) );
	}
	
	/**
	 * Sets the statement attached to this task to to the given statement.
	 * 
	 * @param	statement
	 * 				The statement to attach to this task.
	 * @post	This task references the given statement as its statement.
	 * 				| new.getStatement() == statement
	 * @throws	IllegalArgumentException
	 * 				The given statement is effective but does not references
	 * 				this task as its task.
	 * 				| (statement != null) && (statement.getTask() != this)
	 * 				Or, the statement is not effective and this task references
	 * 				a statement which still references this task as its task.
	 * 				| (statement == null) && (this.hasStatement() && (this.getStatement().getTask() == this))
	 */
	public void setStatement(@Raw Statement statement) throws IllegalArgumentException {
		if ( (statement != null) && (statement.getTask() != this)  )
			throw new IllegalArgumentException("Statement does not properly associate this task.");
		if ( (statement == null) && (this.hasStatement() && (this.getStatement().getTask() == this)))
			throw new IllegalArgumentException("Link with current statement not properly broken.");
		this.statement = statement;
	}
	
	/**
	 * A variable referencing the statement attached to this task.
	 */
	private Statement statement;
	
	/**
	 * Check if the statement of this task is correctly formed.
	 * 
	 * @return 	True if the statement of this task, and all of its sub-text objects
	 *  		(if it has any) are well formed.
	 *  		| result == this.getStatement().isWellFormed()
	 */
	public boolean isWellFormed() {
		return this.getStatement().isWellFormed();
	}
	
	// =================================================================================================
	// Methods concerning the variables used in this task.
	// =================================================================================================

	/**
	 * Check whether this task has the given variable as one of its
	 * variables.
	 * 
	 * @param  	variable
	 *         		The variable to check.
	 */
	@Basic
	@Raw
	public boolean hasAsVariable(@Raw String variable) {
		return variables.containsKey(variable);
	}

	/**
	 * Check whether this task can have the given variable with value
	 * as one of its variables.
	 * 
	 * @param  	variable
	 *         		The variable to check.
	 * @return 	True if and only if the given value is effective.
	 *       		| result == (value != null)
	 */
	@Raw
	public boolean canHaveAsVariable(String variable, Expression value) {
		return value != null;
	}

	/**
	 * Check whether this task has proper variables attached to it.
	 * 
	 * @return 	True if and only if this task can have each of the
	 *         		variables attached to it as one of its variables,
	 *        	 	and if the value(expression) of each variable references this task as
	 *        	 	the task to which they are attached.
	 *       		| for each variable in Variable:
	 *       		|   if (hasAsVariable(variable))
	 *       		|     then canHaveAsVariable(variable) &&
	 *       		|          (variables.get(variable).getStatement().getTask() == this)
	 */
	public boolean hasProperVariables() {
		for (String variable : variables.keySet()) {
			if (! canHaveAsVariable(variable,variables.get(variable)))
				return false;
			if (variables.get(variable).getSuperTask() != this)
				return false;
		}
		return true;
	}

	/**
	 * Return the number of variables associated with this task.
	 *
	 * @return  The total number of variables collected in this task.
	 *        		| result == variables.size()
	 */
	public int getNbVariables() {
		return variables.size();
	}

	/**
	 * Add the given variable with expression to the map of variables of this task.
	 * 
	 * @param  	variable
	 *         		The variable to be added.
	 * @param	value
	 * 				The value to store for the variable.
	 * @post   	This task has the given variable as one of its variables.
	 *       		| new.hasAsVariable(variable)
	 * @throws	This task cannot have the given variable
	 * 				as one of its variables.
	 * 				| ! this.canHaveAsVariable(variable, value)
	 * 				Or, the superTask of the given value is not equal to this task.
	 * 				| value.getSuperTask() != this
	 */
	public void addVariable(String variable, Expression value) throws IllegalArgumentException {
		if (! this.canHaveAsVariable(variable, value))
			throw new IllegalArgumentException();
		if (value.getSuperTask() != this)
			throw new IllegalArgumentException();
		variables.put(variable, value);
	}

	/**
	 * Remove the given variable from the set of variables of this task.
	 * 
	 * @param  	variable
	 *         		The variable to be removed.
	 * @post   	This task no longer has the given variable as
	 *         		one of its variables.
	 *      		| ! new.hasAsVariable(variable)
	 * @throws	IllegalArgumentException
	 * 				This task does no have the given variable.
	 * 				| ! this.hasAsVariable(variable)
	 * @note	Because failed tasks must reset their variables,
	 * 				It is allowed to remove a variable even if it has
	 * 				this task as its (super)task.
	 */
	@Raw
	public void removeVariable(String variable) {
		if (! this.hasAsVariable(variable))
			throw new IllegalArgumentException();
		variables.remove(variable);
	}

	/**
	 * Variable referencing a map collecting all the variables
	 * of this task and their values.
	 * 
	 * @invar  	The referenced map is effective.
	 *       		| variables != null
	 * @invar  	Each variable registered in the referenced map holds an
	 * 				expression which is effective and not yet terminated.
	 *       		| for each variable in variables:
	 *       		|   ( (variables.get(variable) != null) &&
	 *       		|     (! (variables.get(variable).isTerminated()) )
	 */
	private final Map<String, Expression> variables = new HashMap<>();
	
	/**
	 * Returns a map collecting all the variables of this task. 
	 * 
	 * @return	A map in which each variable value is effective 
	 * 				and not yet terminated.
	 *       		| for each variable in result:
	 *       		|   ( (variables.get(variable) != null) &&
	 *       		|     (! (variables.get(variable).isTerminated()) )
	 */
	public Map<String, Expression> getVariables() {
		return new HashMap<>(variables);
	}
	
	/**
	 * Return the value of the given variable.
	 * 
	 * @param 	variable
	 * 				The variable which holds the value.
	 * @return	The corresponding value of the variable.
	 * 				| result == variables.get(variable)
	 */
	public Expression getValue(String variable) {
		return variables.get(variable);
	}
	
	// =================================================================================================
	// Methods concerning the explicit statement being executed.
	// This is a statement which directly influences the unit upon execution.
	// SequenceStatements, whilestatements etc. should not be used for this.
	// =================================================================================================
	
	/**
	 * A variable which holds an explicit statement.
	 * Explicit statements are statements which directly 
	 * influence the unit upon execution.
	 * If this task holds an explicit statement, the unit will 
	 * prioritize it over executing other statements of the task.
	 */
	private Statement explicitStatement;
	
	/**
	 * Returns the explicit statement of this task.
	 */
	@Basic
	@Raw
	public Statement getExplicitStatement() {
		return this.explicitStatement;
	}
	
	/**
	 * Checks if this task has an explicit statement attached.
	 * @return	True if the explicit statement is effective.
	 * 				| result == (this.getExplicitStatement() != null)
	 */
	public boolean hasExplicitStatement() {
		return this.getExplicitStatement() != null;
	}
	
	/**
	 * Checks if the given statement is a valid explicit statement
	 * for any task.
	 * 
	 * @param 	statement
	 * 				The statement to check.
	 * @return	True if the statement is an instance of ActionStatement,
	 * 				or the statement is not effective.
	 */
	public static boolean isValidExplicitStatement(Statement statement) {
		return (statement instanceof ActionStatement<?>) || (statement == null);
	}
	
	/**
	 * Sets the explicit statement of this task to the given statement.
	 * 
	 * @param 	statement
	 * 				The new explicit statement.
	 * @post	This task has the new statement as its explicit statement.
	 * @throws	IllegalArgumentException
	 * 				The statement is not of the appropriate type.
	 */
	private void setExplicitStatement(Statement statement) {
		if (! Task.isValidExplicitStatement(statement))
			throw new IllegalArgumentException("statement not of appropriate type.");
		this.explicitStatement = statement;
	}
	
	/**
	 * Starts the execution of this explicit statement.
	 * 
	 * @param 	statement
	 * 				The explicit statement to execute.
	 * @effect	If this unit does nothing,
	 * 				the statement becomes the explicit statement of this task
	 * 				and its status is set to EXECUTING.
	 * 				| if (unit.getCurrentActivity() == Activity.NOTHING)
	 *				| 	then this.setExplicitStatement(statement) &&
	 *				| 		 statement.setStatus(Status.EXECUTING)
	 *				If the statement is invalid, nothing happens.
	 */
	public void startExplicitStatement(Statement statement) {
		try {
			Unit unit = this.getUnit();
			if (unit.getCurrentActivity() == Activity.NOTHING) {
				this.setExplicitStatement(statement);
				statement.setStatus(Status.EXECUTING);
			}
		} catch (IllegalArgumentException e) {	
		}
	}
	
	/**
	 * Finish the execution of this task's current explicit statement.
	 * 
	 * @effect	If this task has an explicit statement,
	 * 				its status is set to DONE and this task no longer
	 * 				directly references it.
	 * 				| if (this.hasExplicitStatement())
	 *				|  then this.getExplicitStatement().setStatus(Status.DONE) &&
	 *				| 		this.setExplicitStatement(null);
	 */
	public void finishExplicitStatement() {
		if (this.hasExplicitStatement()) {
			this.getExplicitStatement().setStatus(Status.DONE);
			this.setExplicitStatement(null);
		}
	}
	
	// =================================================================================================
	// Methods concerning the schedulers who have this task.
	// =================================================================================================
	
	/**
	 * Check whether this task has the given scheduler as one of its
	 * schedulers.
	 * 
	 * @param  scheduler
	 *         The scheduler to check.
	 */
	@Basic
	@Raw
	public boolean hasAsScheduler(@Raw Scheduler scheduler) {
		return schedulers.contains(scheduler);
	}

	/**
	 * Check whether this task can have the given scheduler
	 * as one of its schedulers.
	 * 
	 * @param  	scheduler
	 *         	The scheduler to check.
	 * @return 	True if and only if the given scheduler is effective.
	 *       	| result ==
	 *       	|   (scheduler != null)
	 */
	@Raw
	//NOTE: cannot use canHaveAsTask
	public boolean canHaveAsScheduler(Scheduler scheduler) {
		return (scheduler != null);
	}

	/**
	 * Check whether this task has proper schedulers attached to it.
	 * 
	 * @return True if and only if this task can have each of the
	 *         schedulers attached to it as one of its schedulers,
	 *         and if each of these schedulers references this task as
	 *         one of its tasks.
	 *       | for each scheduler in Scheduler:
	 *       |   if (hasAsScheduler(scheduler))
	 *       |     then canHaveAsScheduler(scheduler) &&
	 *       |          scheduler.hasAsTask(this)
	 */
	public boolean hasProperSchedulers() {
		for (Scheduler scheduler : schedulers) {
			if (!canHaveAsScheduler(scheduler))
				return false;
			if (! scheduler.hasAsTask(this))
				return false;
		}
		return true;
	}

	/**
	 * Return the number of schedulers associated with this task.
	 *
	 * @return  The total number of schedulers collected in this task.
	 *        | result ==
	 *        |   card({scheduler:Scheduler | hasAsScheduler({scheduler)})
	 */
	public int getNbSchedulers() {
		return schedulers.size();
	}

	/**
	 * Add the given scheduler to the set of schedulers of this task.
	 * 
	 * @param  scheduler
	 *         The scheduler to be added.
	 * @post   This task has the given scheduler as one of its schedulers.
	 *       | new.hasAsScheduler(scheduler)
	 * @throws	IllegalStateException
	 * 			The given scheduler is terminated.
	 * @throws	IllegalArgumentException
	 * 			This task cannot have the given scheduler as its scheduler.
	 */
	//NOTE: task controls the connection!
	public void addScheduler(@Raw Scheduler scheduler) 
			throws IllegalArgumentException, IllegalStateException {
		
		if (scheduler.isTerminated())
			throw new IllegalStateException();
		if (! canHaveAsScheduler(scheduler))
			throw new IllegalArgumentException();
		schedulers.add(scheduler);
	}

	/**
	 * Remove the given scheduler from the set of schedulers of this task.
	 * 
	 * @param  scheduler
	 *         The scheduler to be removed.
	 * @post   This task no longer has the given scheduler as
	 *         one of its schedulers.
	 *       | ! new.hasAsScheduler(scheduler)
	 * @throws	IllegalArgumentException
	 * 			This task does not have the given scheduler as one of its schedulers.    		
	 */
	@Raw
	//NOTE: task controls the connection!
	public void removeScheduler(Scheduler scheduler) {
		if (! hasAsScheduler(scheduler))
			throw new IllegalArgumentException();
		schedulers.remove(scheduler);
	}

	/**
	 * Variable referencing a set collecting all the schedulers
	 * of this task.
	 * 
	 * @invar  The referenced set is effective.
	 *       | schedulers != null
	 * @invar  Each scheduler registered in the referenced list is
	 *         effective and not yet terminated.
	 *       | for each scheduler in schedulers:
	 *       |   ( (scheduler != null) &&
	 *       |     (! scheduler.isTerminated()) )
	 */
	private final Set<Scheduler> schedulers = new HashSet<Scheduler>();
	
	/**
	 * Returns a set collecting all the schedulers of this task. 
	 * 
	 * @return	A set in which each scheduler is effective 
	 * 			and not yet terminated.
	 *       	| for each scheduler in result:
	 *       	|   ( (scheduler != null) &&
	 *       	|     (! scheduler.isTerminated()) )
	 */
	public Set<Scheduler> getSchedulers() {
		return new HashSet<Scheduler>(this.schedulers);
	}
	
	/**
	 * Removes this task from its unit and lowers its priority.
	 * All schedulers which hold this task are updated.
	 * 
	 * @effect	This task is detached from its unit and
	 *				the priority is lowered by one. The statement status,
	 *				variables and explicit statement are reset.
	 *				| this.setPriority(this.getPriority()-1)
	 *				| this.setUnit(null)
	 *				| this.getUnit().setTask(null)
	 *				| this.getStatement().resetStatus()
	 *				| this.variables.clear()
	 *				| this.setExplicitStatement(null)
	 * @throws	IllegalStateException
	 * 				This task does not have a unit.
	 * 				| ! this.hasUnit()
	 */
	public void returnFailedTask() throws IllegalStateException {
		if (! this.hasUnit())
			throw new IllegalStateException();
		this.setPriority(this.getPriority()-1);
		Unit unit = this.getUnit();
		this.setUnit(null);
		unit.setTask(null);
		this.getStatement().resetStatus();
		this.setExplicitStatement(null);
		this.variables.clear();
		for (Scheduler scheduler : this.getSchedulers())
			scheduler.replaceTask(this, this);
	}
	
	// =================================================================================================
	// Compare method based on priority for tasks.
	// =================================================================================================
	
	/**
	 * Compares this task to the given task.
	 * 
	 * @param 	task
	 * 			The task to compare this task to.
	 * @return	An integer based on the difference in priority between the tasks.
	 * 			If the priorities are equal, return 0.
	 * 			| if (this.getPriority() == task.getPriority())
	 * 			|	then result == 0
	 * 			If this task has a lower priority than the given task, return -1.
	 * 			| if (this.getPriority() < task.getPriority())
	 * 			|	then result == -1
	 * 			Else, return 1.
	 * 			| else
	 * 			|	then result == 1
	 * @note	This class compares by using only PRIORITIES, and as such is incosistent
	 * 			with the method 'equals'.
	 */
	@Override
	public int compareTo(Task task) {
		int myPriority = this.getPriority();
		int otherPriority = task.getPriority();
		if (myPriority == otherPriority)
			return 0;
		if (myPriority < otherPriority)
			return -1;
		else
			return 1;
	}
}
