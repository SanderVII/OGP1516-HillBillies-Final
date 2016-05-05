package hillbillies.part3.facade;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import hillbillies.model.Faction;
import hillbillies.model.Scheduler;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.ITaskFactory;
import ogp.framework.util.ModelException;

public class Facade  extends hillbillies.part2.facade.Facade implements hillbillies.part3.facade.IFacade{

	@Override
	public ITaskFactory<?, ?, Task> createTaskFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWellFormed(Task task) throws ModelException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Scheduler getScheduler(Faction faction) throws ModelException {
		return faction.getScheduler();
	}

	@Override
	public void schedule(Scheduler scheduler, Task task) throws ModelException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void replace(Scheduler scheduler, Task original, Task replacement) throws ModelException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean areTasksPartOf(Scheduler scheduler, Collection<Task> tasks) throws ModelException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<Task> getAllTasksIterator(Scheduler scheduler) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Scheduler> getSchedulersForTask(Task task) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Unit getAssignedUnit(Task task) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Task getAssignedTask(Unit unit) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName(Task task) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPriority(Task task) throws ModelException {
		// TODO Auto-generated method stub
		return 0;
	}

}
