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
	public static boolean canDoRandomDefault(Unit unit) {
		return (!unit.hasTask()) && (unit.getDefaultBehaviorEnabled());
	}
	
	/**
	 * Starts the random default behavior of the given unit.
	 */
	public static void startRandomDefaultBehavior(Unit unit) {
		
	}
	
	/**
	 * Return a list of activities which can currently be performed by the given unit.
	 * @param unit
	 */
	//TODO
	public static List<Activity> getAvailableRandomActivities(Unit unit) {
		List<Activity> result = new ArrayList<>();
		if (DefaultManager.getAvailableEnemies(unit).size() != 0)
			result.add(Activity.ATTACK);
		if (DefaultManager.getAvailableMoveCubes(unit).size() != 0)
			result.add(Activity.MOVE);
		if (DefaultManager.getAvailableWorkCubes(unit).size() != 0)
			result.add(Activity.WORK);
		if (unit.getCurrentHealth() < unit.getMaxPoints() || unit.getCurrentStamina() < unit.getMaxPoints())
			result.add(Activity.REST);
		return result;
	}
	
	/**
	 * Return a list of enmy units which are next to the given unit.
	 * @param 	unit
	 * 			The unit to search for enemies around it for.
	 * @return	A set of units in which each unit is an enemy of the given unit
	 * 			and is at most one cube away from him.
	 */
	public static Set<Unit> getAvailableEnemies(Unit unit) {
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
	public static Set<int[]> getAvailableWorkCubes(Unit unit) {
		return unit.getWorld().getValidCubeCoordinatesInRange(unit.getCubeCoordinates(), 1);
	}
	
	/**
	 * Return a set of cubes which the given unit can move to.
	 * @param 	unit
	 * 			The unit to search for destinations for.
	 * @return	All cubes which the unit can move to according to the canMoveTo-method.
	 */
	public static Set<int[]> getAvailableMoveCubes(Unit unit) {
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
	 * Return a set of cubes which the given unit can move to.
	 * @param 	unit
	 * 			The unit to search for destinations for.
	 * @return	All cubes which the unit can move to according to the canMoveTo-method.
	 */
	public static Set<int[]> getAvailableMoveCubes(Unit unit, int range) {
		Set<int[]> result = new HashSet<>();
		World unitWorld = unit.getWorld();
		Set<int[]> cubes = unitWorld.getValidCubeCoordinatesInRange(unit.getCubeCoordinates(), range);
		for (int[] cube: cubes) {
			if (unit.canMoveTo(cube))
					result.add(cube);
		}
		return result;
	}
}
