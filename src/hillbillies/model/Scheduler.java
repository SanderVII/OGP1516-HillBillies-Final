package hillbillies.model;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of schedulers associated with a faction.
 * 
 * @invar	Each scheduler must have a proper faction.
 *				| hasProperFaction()
 * @invar	Each scheduler must have proper tasks.
 *				| hasProperTasks()
 * 
 * @author	Sander Mergan, Thomas Vranken
 * @version	3.0
 * 
 * @note	Must be documented both formally and informally.
 */
public class Scheduler {
	
	/**
	 * Creates a new scheduler.
	 * 
	 * @param	faction
	 *				The faction of this new scheduler.
	 * 
	 * @post	This scheduler has the given faction as its faction.
	 *				| this.getFaction() == faction
	 * @post	The faction has this scheduler as its scheduler.
	 *				| faction.getScheduler() == this
	 *
	 * @throws	IllegalArgumentException
	 *				The faction is invalid.
	 */
	public Scheduler(Faction faction) throws IllegalArgumentException {
		this.setFaction(faction);
		faction.setScheduler(this);
	}
	
	
	// =================================================================================================
	// Termination of scheduler
	// =================================================================================================
	
	/**
	 * Terminates this scheduler.
	 *
	 * @post	This scheduler  is terminated.
	 *				| new.isTerminated()
	 * @effect	The scheduler of this scheduler's faction is set to null.
	 *				| this.getFaction().setScheduler(null)
	 * @effect	The faction of this scheduler is set to null.
	 *				| this.setFaction(null)
	 */
	 public void terminate() {
		 if (! this.isTerminated()) {
			 Faction formerFaction = this.getFaction();
			 this.isTerminated = true;
			 this.setFaction(null);
			 formerFaction.setScheduler(null);
		 }
	 }
	 
	 /**
	  * Returns a boolean indicating whether or not this scheduler is terminated.
	  */
	 @Basic @Raw
	 public boolean isTerminated() {
		 return this.isTerminated;
	 }
	 
	 /**
	  * A variable that stores whether this scheduler is terminated.
	  */
	 private boolean isTerminated = false;
	 
	 
	// =================================================================================================
	// Methods concerning the faction.
	// =================================================================================================
	 
	/**
	 * Returns the faction of this scheduler.
	 */
	@Basic @Raw
	public Faction getFaction() {
		return this.faction;
	}
	
	/**
	 * Checks whether this scheduler can have the given faction as its faction.
	 * 
	 * @param	faction
	 *				The faction to check.
	 *
	 * @return	If this scheduler is terminated, true if the given faction is not effective.
	 *				| if (isTerminated())
	 *				|	then result == (faction == null)
	 *				Else, true if the given faction is effective and not yet terminated.
	 *				| else
	 *				|	then result == (faction != null) && (!faction.isTerminated())
	 */
	@Raw
	public boolean canHaveAsFaction(@Raw Faction faction) {
		if (this.isTerminated)
			return faction == null;
		else
			return (faction != null) && (! faction.isTerminated());
	}
	
	/**
	 * Checks whether this scheduler has a proper faction.
	 * 
	 * @return	True if and only if this scheduler can have the faction to which it
	 *				is attached as its faction, and if that faction is either not
	 *				effective or has this scheduler as one of its schedulers.
	 *				| (this.canHaveAsFaction(this.getFaction()) 
	 *				|  && ( (this.getFaction() == null) || (this.getFaction().hasAsScheduler(this))) )
	 */
	public boolean hasProperFaction() {
		return ( this.canHaveAsFaction(this.getFaction()) 
				&& ( ( this.getFaction() == null) || (this.getFaction().getScheduler() == this) ) );
	}
	
	/**
	 * Sets the faction of this scheduler to the given faction.
	 * 
	 * @param	faction
	 *				The faction to attach this scheduler to.
	 * 
	 * @post	This scheduler references the given faction as the faction to which it is attached.
	 *				| new.getFaction() == faction
	 * 
	 * @throws	IllegalArgumentException
	 *				This scheduler cannot have the given faction as its faction,
	 *				or the faction is effective and already has a scheduler.
	 *				| (! canHaveAsFaction(faction)) ||
	 *				| ((faction != null) && (faction.getScheduler() != null))
	 */
	public void setFaction(@Raw Faction faction) throws IllegalArgumentException {
		if (! this.canHaveAsFaction(faction))
			throw new IllegalArgumentException();
		if (faction != null)
			if (faction.hasScheduler())
				throw new IllegalArgumentException();
		this.faction = faction;
	}
	
	/**
	 * A variable that stores the faction of this scheduler.
	 */
	private Faction faction; 
	
	// =================================================================================================
	// Methods concerning the tasks.
	// =================================================================================================

	/**
	 * Returns the task associated with this scheduler at the given index.
	 * 
	 * @param	index
	 *				The index of the task to return.
	 *
	 * @throws	IndexOutOfBoundsException
	 *				The given index is not positive or it exceeds the
	 *				number of tasks for this scheduler.
	 *				| (index < 1) || (index > getNbTasks())
	 */
	@Basic
	@Raw
	public Task getTaskAt(int index) throws IndexOutOfBoundsException {
		return tasks.get(index - 1);
	}

	/**
	 * Returns the number of tasks associated with this scheduler.
	 */
	@Basic
	@Raw
	public int getNbTasks() {
		return tasks.size();
	}

	/**
	 * Checks whether this scheduler can have the given task as one of its tasks.
	 * 
	 * @param	task
	 *				The task to check.
	 *
	 * @return True if and only if the given task is effective
	 *				and that task can have this scheduler as its scheduler.
	 *				| result ==
	 *				|   (task != null) &&
	 *				|   Task.isValidScheduler(this)
	 */
	@Raw
	public boolean canHaveAsTask(Task task) {
		return (task != null) && (task.canHaveAsScheduler(this));
	}

	/**
	 * Checks whether this scheduler can have the given task as one of its tasks at the given index.
	 * 
	 * @param	task
	 *				The task to check.
	 *
	 * @return False if the given index is not positive or exceeds the number of tasks for this scheduler + 1.
	 *				| if ( (index < 1) || (index > getNbTasks()+1) )
	 *				|   then result == false
	 *				Otherwise, false if this scheduler cannot have the given task as one of its tasks.
	 *				| else if ( ! this.canHaveAsTask(task) )
	 *				|   then result == false
	 *				Otherwise, true if and only if the given task is not registered at another index than the given index.
	 *				| else result ==
	 *				|   for each I in 1..getNbTasks():
	 *				|     (index == I) || (getTaskAt(I) != task)
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
	 * Checks whether this scheduler has proper tasks attached to it.
	 * 
	 * @return True if and only if this scheduler can have each of the
	 *				tasks attached to it as a task at the given index,
	 *				and if each of these tasks references this scheduler as one of its schedulers.
	 *				| result ==
	 *				|   for each I in 1..getNbTasks():
	 *				|     ( this.canHaveAsTaskAt(getTaskAt(I) &&
	 *				|       (getTaskAt(I).hasAsScheduler(this) )
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
	 * Checks whether this scheduler has the given task as one of its tasks.
	 * 
	 * @param	task
	 *				The task to check.
	 *
	 * @return The given task is registered at some position as a task of this scheduler.
	 *				| for some I in 1..getNbTasks():
	 *				|   getTaskAt(I) == task
	 */
	public boolean hasAsTask(@Raw Task task) {
		return tasks.contains(task);
	}

	/**
	 * Adds the given task to the list of tasks of this scheduler.
	 * The task is inserted in the list according to its priority.
	 * Higher priorities will be inserted earlier.
	 * 
	 * @param	task
	 *				The task to be added.
	 *         
	 * @post	The number of tasks of this scheduler is incremented by 1.
	 *				| new.getNbTasks() == getNbTasks() + 1
	 * @post	This scheduler has the given task as its task.
	 *				| new.hasAsTask(this)
	 * 
	 * @throws	IllegalArgumentException
	 *				The given task is either not effective, does not already reference 
	 *				this scheduler, or this scheduler already references the task.
	 *				| (task == null) || (! task.hasAsScheduler(this)) || 
	 *				|	(this.hasAsTask(task))
	 */
	//NOTE: Task controls the association!
	public void addTask(@Raw Task task) throws IllegalArgumentException {
		if ( (task == null) || (! task.hasAsScheduler(this)) || (this.hasAsTask(task)) )
			throw new IllegalArgumentException();
		int size = this.getNbTasks();
		if (size == 0)
			tasks.add(task);
		else {
			for (int count = 1; count <= size; count++) {
				int compare = task.compareTo(this.getTaskAt(count));
				if (compare >= 0) {
					tasks.add(count-1, task);
					count = size;
				}
				else if (count == size)
					tasks.add(task);
				else
					count ++;
			}
		}	
	}

	/**
	 * Removes the given task from the list of tasks of this scheduler.
	 * 
	 * @param	task
	 *					The task to be removed.
	 *
	 * @post	The number of tasks of this scheduler is
	 *				decremented by 1.
	 *				| new.getNbTasks() == getNbTasks() - 1
	 * @post	This scheduler no longer has the given task as one of its tasks.
	 *				| ! new.hasAsTask(task)
	 * @post	All tasks registered at an index beyond the index at
	 *					which the given task was registered, are shifted
	 *					one position to the left.
	 *				| for each I,J in 1..getNbTasks():
	 *				|   if ( (getTaskAt(I) == task) and (I < J) )
	 *				|     then new.getTaskAt(J-1) == getTaskAt(J)
	 *
	 * @throws	IllegalArgumentException
	 *				The given task is not effective, or this scheduler does not
	 *				reference that task, or that task still references this
	 *				scheduler as one of its scheduler.
	 *				| (task == null) || (! this.hasAsTask(task)) || 
	 *				|	(task.hasAsScheduler(this))
	 */
	@Raw
	//NOTE: Task controls the association!
	public void removeTask(Task task) throws IllegalArgumentException {
		if ((task == null) || (! this.hasAsTask(task)) || (task.hasAsScheduler(this)))
			throw new IllegalArgumentException();
		tasks.remove(task);
	}

	/**
	 * A variable that stores a list collecting all the tasks of this scheduler.
	 * 
	 * @invar	The referenced list is effective.
	 *				| tasks != null
	 * @invar	Each task registered in the referenced list is
	 *				effective and not yet terminated.
	 *				| for each task in tasks:
	 *				|   ( (task != null) &&
	 *				|     (! task.isTerminated()) )
	 * @invar	No task is registered at several positions 	in the referenced list.
	 *				| for each I,J in 0..tasks.size()-1:
	 *				|   ( (I == J) ||
	 *				|     (tasks.get(I) != tasks.get(J))
	 * @invar	All tasks in the list are sorted in descending order.
	 *				| for each I in 0..tasks.size()-1:
	 *				| 	for each J in 0..I:
	 *				|   	tasks.getTaskAt(J).getPriority() >= 
	 *				|			tasks.getTaskAt(I).getPriority()
	 */
	private final List<Task> tasks = new ArrayList<Task>();
	
	/**
	 * Returns a list collecting all the tasks of this scheduler. 
	 * 
	 * @return	A list in which each task is effective  and not yet terminated.
	 *				| for each task in result:
	 *				|   ( (task != null) &&
	 *				|     (! task.isTerminated()) )
	 *				No task is registered at several positions in the referenced list.
	 *				| for each I,J in 1..tasks.size():
	 *				|   ( (I == J) ||
	 *				|     (getTaskAt(I) != getTaskAt(J))
	 */
	public List<Task> getTasks() {
		return new ArrayList<Task>(this.tasks);
	}
	
	// =================================================================================================
	// Iterator and stream methods for all tasks of this scheduler.
	// =================================================================================================
	
	/**
	 * An iterator to iterate over the tasks of this scheduler.
	 */
	public Iterator<Task> priorityIterator() {
        return new Iterator<Task>() {

            public boolean hasNext() {
                return pos < getNbTasks();
            }

            public Task next() throws NoSuchElementException {
                if (! hasNext())
                    throw new NoSuchElementException();
                pos = pos + 1;
                return getTaskAt(pos);
            }
            private int pos = 0;

        };
    }
	
	/**
     * Returns a stream that delivers all tasks of this scheduler.
     *    
     * @return	An effective stream that delivers all the elements in
     *				this tree in the same order as the iterator for this tree.
     */
    public Stream<Task> streamTasks() {
    	Stream.Builder<Task> builder = Stream.builder();
    	for (Task element: this.getTasks())
    		builder.accept(element);
    	return builder.build();    		
    }
    
    /**
     * Returns a list of tasks which satisfy a certain condition,
     * using the task stream of this scheduler to build it.
     * 
     * @param	predicate
     *				The filter condition.
     *
     * @return	A list of task in which each task satisfies the condition.
     *				| for each task in result:
     *				|	predicate(task) == true
     *
     * @note	This method does not change the order of elements.
     *				The resulting list will have the same order as the original list.
     */
    public List<Task> getTasksWithPredicate(Predicate<Task> predicate) {
		return this.streamTasks().filter(predicate).collect(Collectors.toList());
    }
    
    /**
     * Returns a list of tasks which are unassigned, using the task stream of this scheduler to build it.
	 *
     * @return	A list of task in which each task
	 *				has no unit attached.
     *				| for each task in result:
     *				|	! task.hasUnit()
     */
    public List<Task> getUnassignedTasks() {
    	return getTasksWithPredicate(x -> (! x.hasUnit()));
    }

	// =================================================================================================
	// Extra methods as defined in the assignment.
	// =================================================================================================
	
	/**
	 * Replaces the original task by the given replacement task.
	 * If the original task is being executed, it will stop executing first.
	 * 
	 * @param	original
	 *				The task to replace.
	 * @param	replacement
	 *				The new task.
	 *
	 * @effect	The original task stops executing.
	 *				| original.stopExecuting()
	 * @post	The tasks list has the replacement task as its task on the location of the original task.
	 *				| new.getTaskAt(this.getTasks.indexOf(original) == replacement
	 * @post	This scheduler no longer has the original task as its task.
	 *				| ! new.hasAsTask(original)
	 *
	 * @throws	IllegalArgumentException
	 *				The original task does not exists in this scheduler,
	 *				or the replacement task is invalid.
	 *				| (! this.hasAsTask(original)) || (! canHaveAsTask(replacement))
	 */
	public void replaceTask(Task original, Task replacement) 
			throws IllegalArgumentException, IllegalStateException {
		if( (! this.hasAsTask(original)) || (! canHaveAsTask(replacement)) )
			throw new IllegalArgumentException();
		original.stopExecuting();
		original.removeScheduler(this);
		this.removeTask(original);
		replacement.addScheduler(this);
		this.addTask(replacement);
	}
	
	/**
	 * Schedules the given task for this scheduler.
	 * The task does not need to reference this scheduler already.
	 * 
	 * @param	task
	 *				The task to scheduler
	 *
	 * @effect 	The task adds this scheduler to its schedulers.
	 *				| task.addSchedulers(this)
	 * @effect	This scheduler adds the given task to its tasks.
	 *				| this.addTask(task)
	 */
	public void schedule(Task task) throws IllegalStateException, IllegalArgumentException {
		task.addScheduler(this);
		this.addTask(task);
	}
	
	/**
	 * Checks if all tasks of the given collection can be found in this scheduler.
	 * 
	 * @param	tasks
	 *				The collection of tasks to check.
	 * @return	False if at least one task does not exist in
	 *				this scheduler.
	 *				| for (task in tasks)
	 *				|	if (! hasAsTask(task))
	 *				|		then result == false
	 *				Else, return true.
	 *				| else
	 *				| then result == true
	 */
	public boolean hasAllTasks(Collection<Task> tasks) {
		for (Task task: tasks)
			if (! this.hasAsTask(task))
				return false;
		return true;
	}
	
	/**
	 * Adds the given collection of tasks to this scheduler.
	 * Only valid tasks will be added, with respect to the 
	 * priority order of the task list. Invalid tasks are ignored.
	 * 
	 * @param	tasks
	 *				The collection of tasks to add.
	 * @effect	Each appropiate task of the collection is added  to the task list of this scheduler.
	 *				| for each task in tasks:
	 *				|	this.addTask(task)
	 */
	public void addAllTasks(Collection<Task> tasks) {
		for (Task task: tasks) {
			try {
				this.addTask(task);
			} catch(Exception e) {
				
			}
		}
	}
	
	/**
	 * Removes the given collection of tasks from this scheduler.
	 * Only valid tasks will be removed. Invalid tasks are ignored.
	 * 
	 * @param	tasks
	 *				The collection of tasks to remove.
	 * @effect	Each appropiate task of the collection is removed 
	 *				from the task list of this scheduler.
	 *				| for each task in tasks:
	 *				|	this.removeTask(task)
	 */
	public void removeAllTasks(Collection<Task> tasks) {
		for (Task task: tasks) {
			try {
				this.removeTask(task);
			} catch(Exception e) {
				
			}
		}
	}
}
