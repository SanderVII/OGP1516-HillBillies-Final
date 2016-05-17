package hillbillies.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DefaultManager {
	
	/**
	 * Check if the given unit can perform random default behavior.
	 * @param 	unit
	 * 			The unit to check.
	 * @return	True if the unit does not have a task and its default behavior is enabled.
	 */
	public boolean canDoRandomDefault(Unit unit) {
		return (!unit.hasTask()) && (unit.getDefaultBehaviorEnabled());
	}
	
	/**
	 * Starts the random default behavior of the given unit.
	 */
	public void startRandomDefaultBehavior(Unit unit) {
		
	}
	
	/**
	 * Return a list of activities which can currently be performed by the given unit.
	 * @param unit
	 */
	//TODO
	public List<ActivityData> getAvailableRandomActivities(Unit unit) {
		List<ActivityData> result = new ArrayList<>();
		return null;
	}
	
	/**
	 * Return a list of enmy units which are next to the given unit.
	 * @param 	unit
	 * 			The unit to search for enemies around it for.
	 * @return	A set of units in which each unit is an enemy of the given unit
	 * 			and is at most one cube away from him.
	 */
	public Set<Unit> getAvailableEnemies(Unit unit) {
		Set<Unit> result = new HashSet<>();
		Set<int[]>neighbourCubes = unit.getWorld().getValidCubeCoordinatesInRange(unit.getCubeCoordinates(), 1);
		for(int[] cube: neighbourCubes) {
			Set<Unit> units = unit.getWorld().getUnitsInCube(cube[0], cube[1], cube[2]);
			for (Unit dummy: units)
				if (dummy.getFaction() != unit.getFaction())
					result.add(unit);
		}
		return result;
	}
	
	/**
	 * Return a set of coordinates on which the unit can work.
	 * @param 	unit
	 * 			The unit to search for working cubes for.
	 * @return	A set of coordinates which are only one cube away and are valid
	 * 			for the world of the unit.
	 */
	public Set<int[]> getAvailableWorkCubes(Unit unit) {
		return unit.getWorld().getValidCubeCoordinatesInRange(unit.getCubeCoordinates(), 1);
	}
	
	/**
	 * Return a set of cubes which the given unit can move to.
	 * @param 	unit
	 * 			The unit to search for destinations for.
	 * @return	All cubes which the unit can move to according to the canMoveTo-method.
	 */
	public Set<int[]> getAvailableMoveCubes(Unit unit) {
		Set<int[]> result = new HashSet<>();
		World unitWorld = unit.getWorld();
		for (int x = World.CUBE_COORDINATE_MIN; x < unitWorld.getMaximumXValue(); x++) {
			for (int y = World.CUBE_COORDINATE_MIN; y < unitWorld.getMaximumYValue(); y++) {
				for (int z = World.CUBE_COORDINATE_MIN; z < unitWorld.getMaximumZValue(); z++) {
					int[] dummy = new int[]{x,y,z};
					if (unit.canMoveTo(dummy))
						result.add(dummy);
				}
			}
		}
		return result;
	}
	
	/**
	 * Return a random activity which the unit can execute.
	 * @param unit
	 * @return
	 */
	//TODO
	public ActivityData getRandomActivity(Unit unit) {
		return null;
	}
	
	/**
	 * Starts the random default behavior of the given unit.
	 */
	//TODO
	public void advanceTimeDefaultBehavior(Unit unit) throws IllegalArgumentException, NullPointerException {
//		this.setDefaultBehaviorEnabled(true);
//		// TODO default behaviour volgens part 3 implementeren.
//		Scheduler scheduler = this.getFaction().getScheduler();
//		List<Task> availableTasks = scheduler.getUnassignedTasks();
//		if (availableTasks.size() != 0) {
//			availableTasks.get(0).assignTo(this);
//			this.getTask().getStatement().execute();
//		}
//		else {
////		try {
//			int choice =  new Random().nextInt(4);
//			if (choice == 0){
//				// The unit chose to work. 
//				this.workAt(this.getWorld().getRandomValidCubeCoordinatesInRange(this.getPosition().getCoordinates(), 1), true);
//			}
//			else if (choice == 1){
//				// The unit chose to rest.
//				this.rest(true);
//			}
//			else if (choice == 2){
//				// The unit chose to go to a random position. Once the unit is moving, it can decide to start sprinting until it runs out of stamina.
//				int[] randomPosition = this.getWorld().getRandomUnitCoordinatesInRange(this.getPosition().getCoordinates(), MAX_RANGE_DEFAULTMOVE);
//				this.moveTo(randomPosition, true);
//			}
//			else if (choice == 3){
//				//	The unit chose to attack a random unit in its reach.
//				// If there are no attackable units in its reach, then a new behaviour is chosen.
//				Unit combattant = this.getUnitThatCanBeAtacked();
//				if (combattant == null)
//					this.startDefaultBehavior();
//				else 
//					this.fight(combattant, true); 
//			}
////		} catch (Exception e){
////			// Do nothing
////			System.out.println("I do nothing now because I failed at default behaviour.");
////		}
//		}
	}
}
