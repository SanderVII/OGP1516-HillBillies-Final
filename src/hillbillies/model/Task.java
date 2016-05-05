package hillbillies.model;

import java.util.*;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of tasks, involving a name, a priority and 
 * a non-empty list of activities.
 * 
 * @invar  The name of each task must be a valid name for any
 *         task.
 *       | isValidName(getName())
 * @invar  The priority of each task must be a valid priority for any
 *         task.
 *       | isValidPriority(getPriority())
 * @invar  Each task must have a proper unit.
 * 		 | hasProperUnit()
 *     		
 * @author 	Sander Mergan, Thomas Vranken
 * @version	2.2
 * 
 * @note	Must be documented both formally and informally.
 */
public class Task {
	
	public Task(String name, int priority, List<Activity> activities) {
		
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
	public void setPriority(int priority) 
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
	
	// =================================================================================================
	// Methods concerning the schedulers who have this task.
	// =================================================================================================
	
	/** TO BE ADDED TO THE CLASS INVARIANTS
	 * @invar   Each task must have proper schedulers.
	 *        | hasProperSchedulers()
	 */
	
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
}
