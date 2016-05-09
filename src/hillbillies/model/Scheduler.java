package hillbillies.model;

import java.util.*;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of schedulers associated with a faction.
 * 
 * @invar	Each scheduler must have a proper faction.
 * 				| hasProperFaction()
 * @invar   Each scheduler must have proper tasks.
 *        		| hasProperTasks()
 * 
 * @author 	Sander Mergan, Thomas Vranken
 * @version	2.2
 * 
 * @note	Must be documented both formally and informally.
 */
public class Scheduler {
	//TODO keuze implementatie tasks: LinkedList voor sorteren priority.
	
	/**
	 * Creates a new scheduler.
	 * @param	faction
	 * 			The faction of this new scheduler.
	 * @post	This scheduler has the given faction as its faction.
	 * 			| this.getFaction() == faction
	 * @post	The faction has this scheduler as its scheduler.
	 * 			| faction.getScheduler() == this
	 * @throws	IllegalArgumentException
	 * 			The faction is invalid.
	 */
	public Scheduler(Faction faction) throws IllegalArgumentException {
		this.setFaction(faction);
		faction.setScheduler(this);
	}
	
	// =================================================================================================
	// Termination of scheduler
	// =================================================================================================
	
	/**
	 * Terminate this scheduler.
	 *
	 * @post   This scheduler  is terminated.
	 *       | new.isTerminated()
	 * @post   ...
	 *       | ...
	 */
	//TODO finish
	 public void terminate() {
		 if (! this.isTerminated()) {
			 Faction formerFaction = this.getFaction();
			 this.isTerminated = true;
			 this.setFaction(null);
			 formerFaction.setScheduler(null);
		 }

	 }
	 
	 /**
	  * Return a boolean indicating whether or not this scheduler
	  * is terminated.
	  */
	 @Basic @Raw
	 public boolean isTerminated() {
		 return this.isTerminated;
	 }
	 
	 /**
	  * Variable registering whether this scheduler is terminated.
	  */
	 private boolean isTerminated = false;
	 
	// =================================================================================================
	// Methods concerning the faction.
	// =================================================================================================
	 
	/**
	 * Return the faction of this scheduler.
	 */
	@Basic @Raw
	public Faction getFaction() {
		return this.faction;
	}
	
	/**
	 * Checks whether this scheduler can have the given faction as its faction.
	 * 
	 * @param 	faction
	 *			The faction to check.
	 * @return	If this scheduler is terminated, true if the given faction
	 * 			is not effective.
	 * 			| if (isTerminated())
	 * 			|	then result == (faction == null)
	 * 			Else, true if the given faction is effective,
	 * 			and not yet terminated.
	 * 			| else
	 * 			|	then result == (faction != null) && (!faction.isTerminated())
	 */
	@Raw
	public boolean canHaveAsFaction(@Raw Faction faction) {
		if (this.isTerminated)
			return faction == null;
		else
			return (faction != null) && (! faction.isTerminated());
	}
	
	/**
	 * Checks whether this scheduler has a proper faction to which it is attached.
	 * 
	 * @return	True if and only if this scheduler can have the faction to which it
	 * 			is attached as its faction, and if that faction is either not
	 * 			effective or has this scheduler as one of its schedulers.
	 * 			| (this.canHaveAsFaction(this.getFaction()) 
	 *			|  && ( (this.getFaction() == null) || (this.getFaction().hasAsScheduler(this))) )
	 */
	public boolean hasProperFaction() {
		return ( this.canHaveAsFaction(this.getFaction()) 
				&& ( ( this.getFaction() == null) || (this.getFaction().getScheduler() == this) ) );
	}
	
	/**
	 * Sets the faction to which this scheduler is attached to to the given faction.
	 * 
	 * @param	faction
	 * 			The faction to attach this scheduler to.
	 * @post	This scheduler references the given faction as the faction to which it is attached.
	 * 			| new.getFaction() == faction
	 * @throws	IllegalArgumentException
	 * 			This scheduler cannot have the given faction as its faction,
	 * 			or the faction is effective and already has a scheduler.
	 * 			| (! canHaveAsFaction(faction)) ||
	 * 			| ((faction != null) && (faction.getScheduler() != null))
	 */
	private void setFaction(@Raw Faction faction) throws IllegalArgumentException {
		if (! this.canHaveAsFaction(faction))
			throw new IllegalArgumentException();
		if (faction != null)
			if (faction.hasScheduler())
				throw new IllegalArgumentException();
		this.faction = faction;
	}
	
	/**
	 * A variable referencing the faction to which this scheduler is attached.
	 */
	private Faction faction; 
	
	// =================================================================================================
	// Methods concerning the tasks.
	// =================================================================================================

	/**
	 * Return the task associated with this scheduler at the
	 * given index.
	 * 
	 * @param  index
	 *         The index of the task to return.
	 * @throws IndexOutOfBoundsException
	 *         The given index is not positive or it exceeds the
	 *         number of tasks for this scheduler.
	 *       | (index < 1) || (index > getNbTasks())
	 */
	@Basic
	@Raw
	public Task getTaskAt(int index) throws IndexOutOfBoundsException {
		return tasks.get(index - 1);
	}

	/**
	 * Return the number of tasks associated with this scheduler.
	 */
	@Basic
	@Raw
	public int getNbTasks() {
		return tasks.size();
	}

	/**
	 * Check whether this scheduler can have the given task
	 * as one of its tasks.
	 * 
	 * @param  task
	 *         The task to check.
	 * @return True if and only if the given task is effective
	 *         and that task can have this scheduler as its scheduler.
	 *       | result ==
	 *       |   (task != null) &&
	 *       |   Task.isValidScheduler(this)
	 */
	@Raw
	public boolean canHaveAsTask(Task task) {
		return (task != null) && (task.canHaveAsScheduler(this));
	}

	/**
	 * Check whether this scheduler can have the given task
	 * as one of its tasks at the given index.
	 * 
	 * @param  task
	 *         The task to check.
	 * @return False if the given index is not positive or exceeds the
	 *         number of tasks for this scheduler + 1.
	 *       | if ( (index < 1) || (index > getNbTasks()+1) )
	 *       |   then result == false
	 *         Otherwise, false if this scheduler cannot have the given
	 *         task as one of its tasks.
	 *       | else if ( ! this.canHaveAsTask(task) )
	 *       |   then result == false
	 *         Otherwise, true if and only if the given task is
	 *         not registered at another index than the given index.
	 *       | else result ==
	 *       |   for each I in 1..getNbTasks():
	 *       |     (index == I) || (getTaskAt(I) != task)
	 */
	@Raw
	public boolean canHaveAsTaskAt(Task task, int index) {
		if ((index < 1) || (index > getNbTasks() + 1))
			return false;
		if (!this.canHaveAsTask(task))
			return false;
		for (int i = 1; i < getNbTasks(); i++)
			if ((i != index) && (getTaskAt(i) == task))
				return false;
		return true;
	}

	/**
	 * Check whether this scheduler has proper tasks attached to it.
	 * 
	 * @return True if and only if this scheduler can have each of the
	 *         tasks attached to it as a task at the given index,
	 *         and if each of these tasks references this scheduler as
	 *         one of its schedulers.
	 *       | result ==
	 *       |   for each I in 1..getNbTasks():
	 *       |     ( this.canHaveAsTaskAt(getTaskAt(I) &&
	 *       |       (getTaskAt(I).hasAsScheduler(this) )
	 */
	public boolean hasProperTasks() {
		for (int i = 1; i <= getNbTasks(); i++) {
			if (!canHaveAsTaskAt(getTaskAt(i), i))
				return false;
			if (! getTaskAt(i).hasAsScheduler(this))
				return false;
		}
		return true;
	}

	/**
	 * Check whether this scheduler has the given task as one of its
	 * tasks.
	 * 
	 * @param  task
	 *         The task to check.
	 * @return The given task is registered at some position as
	 *         a task of this scheduler.
	 *       | for some I in 1..getNbTasks():
	 *       |   getTaskAt(I) == task
	 */
	public boolean hasAsTask(@Raw Task task) {
		return tasks.contains(task);
	}
	
	/**
	 * Check if all tasks of the given collection can be found in
	 * this scheduler.
	 * @param 	tasks
	 * 			The collection of tasks to check.
	 * @return	False if at least one task does not exist in
	 * 			this scheduler.
	 * 			| for (task in tasks)
	 *			|	if (! hasAsTask(task))
	 *			|		then result == false
	 *			Else, return true.
	 *			| else
	 *			| then result == true
	 */
	public boolean hasAllTasks(Collection<Task> tasks) {
		for (Task task: tasks)
			if (! this.hasAsTask(task))
				return false;
		return true;
	}

	/**
	 * Add the given task to the list of tasks of this scheduler.
	 * 
	 * @param  task
	 *         The task to be added.
	 * @pre    The given task is effective and already references
	 *         this scheduler, and this scheduler does not yet have the given
	 *         task as one of its tasks.
	 *       | (task != null) && (task.getScheduler() == this) &&
	 *       | (! this.hasAsTask(task))
	 * @post   The number of tasks of this scheduler is
	 *         incremented by 1.
	 *       | new.getNbTasks() == getNbTasks() + 1
	 * @post   This scheduler has the given task as its very last task.
	 *       | new.getTaskAt(getNbTasks()+1) == task
	 * @throws	IllegalArgumentException
	 * 			The given task is either not effective, does not already reference 
	 * 			this scheduler, or this scheduler already references the task.
	 */
	//NOTE: Task controls the association!
	public void addTask(@Raw Task task) throws IllegalArgumentException {
		if ( (task == null) || (! task.hasAsScheduler(this)) || (this.hasAsTask(task)) )
			throw new IllegalArgumentException();
		int size = this.getNbTasks();
		if (size == 0)
			tasks.add(task);
		else {
			int count = 0;
			while (count < size) {
				int highPriority = this.getTaskAt(count).getPriority();
//				int lowPriority = this.getTaskAt(count+1).getPriority();
				if (task.getPriority() >= highPriority)
					tasks.add(count, task);
				else if (count+1 == size)
					tasks.add(task);
				else
					count ++;
			}
		}	
	}

	/**
	 * Remove the given task from the list of tasks of this scheduler.
	 * 
	 * @param  task
	 *         The task to be removed.
	 * @post   The number of tasks of this scheduler is
	 *         decremented by 1.
	 *       | new.getNbTasks() == getNbTasks() - 1
	 * @post   This scheduler no longer has the given task as
	 *         one of its tasks.
	 *       | ! new.hasAsTask(task)
	 * @post   All tasks registered at an index beyond the index at
	 *         which the given task was registered, are shifted
	 *         one position to the left.
	 *       | for each I,J in 1..getNbTasks():
	 *       |   if ( (getTaskAt(I) == task) and (I < J) )
	 *       |     then new.getTaskAt(J-1) == getTaskAt(J)
	 * @throws	IllegalArgumentException
	 * 			The given task is not effective, or this scheduler does not
	 * 			reference that task, or that task still references this
	 * 			scheduler as one of its scheduler.
	 */
	@Raw
	//NOTE: Task controls the association!
	public void removeTask(Task task) throws IllegalArgumentException {
		if ((task == null) || (! this.hasAsTask(task)) || (task.hasAsScheduler(this)))
			throw new IllegalArgumentException();
		tasks.remove(task);
	}
	
	/**
	 * Replace the original task by the given replacement task.
	 * If the original task is being executed, it will stop executing first.
	 * 
	 * @param 	original
	 * 			The task to replace.
	 * @param 	replacement
	 * 			The new task.
	 * @effect	The original task stops executing.
	 * 			| original.stopExecuting()
	 * @post	The tasks list has the replacement task as its task
	 * 			on the location of the original task.
	 * 			| new.getTaskAt(this.getTasks.indexOf(original) == replacement
	 * @post	This scheduler no longer has the original task as its task.
	 * 			| ! new.hasAsTask(original)
	 * @throws	IllegalArgumentException
	 * 			The original task does not exists in this scheduler,
	 * 			or the replacement task is invalid.
	 * 			| (! this.hasAsTask(original)) || (! canHaveAsTask(replacement))
	 */
	public void replaceTask(Task original, Task replacement) throws IllegalArgumentException {
		if( (! this.hasAsTask(original)) || (! canHaveAsTask(replacement)) )
			throw new IllegalArgumentException();
		original.stopExecuting();
		//NOTE: tasks.set() can throw many exceptions, but these are already checked beforehand.
		replacement.addScheduler(this);
//		tasks.set(tasks.indexOf(original), replacement);
		this.addTask(replacement);
		original.removeScheduler(this);
		this.removeTask(original);
	}
	

	/**
	 * Variable referencing a list collecting all the tasks
	 * of this scheduler.
	 * 
	 * @invar  The referenced list is effective.
	 *       | tasks != null
	 * @invar  Each task registered in the referenced list is
	 *         effective and not yet terminated.
	 *       | for each task in tasks:
	 *       |   ( (task != null) &&
	 *       |     (! task.isTerminated()) )
	 * @invar  No task is registered at several positions
	 *         in the referenced list.
	 *       | for each I,J in 0..tasks.size()-1:
	 *       |   ( (I == J) ||
	 *       |     (tasks.get(I) != tasks.get(J))
	 */
	//TODO search priorityqueue
	private final LinkedList<Task> tasks = new LinkedList<Task>();
	
	/**
	 * Returns a list collecting all the tasks of this scheduler. 
	 * 
	 * @return	A list in which each task is effective 
	 * 			and not yet terminated.
	 *       	| for each task in result:
	 *       	|   ( (task != null) &&
	 *       	|     (! task.isTerminated()) )
	 * 			No task is registered at several positions
	 *         	in the referenced list.
	 *       	| for each I,J in 0..tasks.size()-1:
	 *       	|   ( (I == J) ||
	 *       	|     (tasks.get(I) != tasks.get(J))
	 */
	public LinkedList<Task> getTasks() {
		return new LinkedList<Task>(this.tasks);
	}
	
	// =================================================================================================
	// Iterator for schedulers.
	// =================================================================================================
	
	public Iterator<Task> priorityIterator() {
        return new Iterator<Task>() {

            public boolean hasNext() {
                return pos < getNbTasks();
            }

            public Task next() throws NoSuchElementException {
                if (!hasNext())
                    throw new NoSuchElementException();
                return getTaskAt(pos++);
            }
            private int pos = 0;

        };
    }
}
