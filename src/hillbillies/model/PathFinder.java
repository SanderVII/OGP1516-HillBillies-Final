package hillbillies.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hillbillies.exceptions.MaxIterationException;
import hillbillies.positions.Position;

/**
 * A helper class for pathfinding.
 * This class is used for the moveTo method in Unit but it is easily generalisble.
 * 
 * @author Sander Mergan, Thomas Vrancken
 * @version 3.0
 */
public class PathFinder {
	
	/**
	 * A variable that stores maximal number of iterations. Prevents the path finding from searching forever or a really long time.
	 */
	public static final int MAX_ITERATION = 400;
	
	/**
	 * A variable that stores whether diagonal movement is allowed.
	 */
	public static final boolean ALLOW_DIAGONAL = true;
	
	/**
	 * Searches a path in the given world from the given start to the given destination.
	 * If diagonal moves are allowed then the path will have diagonal moves.
	 * If diagonal moves are not allowed then the path will only have straight corners in it.
	 * 
	 * @param start
	 *				The start of the path.
	 * @param destination
	 *				The destination of the path.
	 * @param world
	 *				The world to find the path in.
	 *
	 * @return	A list of cube coordinates that represents the path from the given start to the given destination. 
	 *				| result == getPath(start, destination, world, ALLOW_DIAGONAL)
	 */
	public static List<int[]> getPath (int[] start, int[] destination, World world) 
			throws MaxIterationException {
		return getPath(start, destination, world, ALLOW_DIAGONAL);
	}
	
	/**
	 * Searches a path in the given world from the given start to the given destination.
	 * If diagonal moves are allowed then the path will have diagonal moves.
	 * If diagonal moves are not allowed then the path will only have straight corners in it.
	 * 
	 * @param start
	 *				The start of the path.
	 * @param destination
	 *				The destination of the path.
	 * @param world
	 *				The world to find the path in.
	 * @param diagonalMovesAllowed
	 *				Whether diagonal moves are allowed.
	 *
	 * @returnA list of cube coordinates that represents the path from the given start to the given destination. 
	 */
	public static List<int[]> getPath (int[] start, int[] destination, World world, boolean diagonalMovesAllowed)
		throws MaxIterationException {
		
		// counter for amount of iterations
		int iteration = 0;
		// The set of nodes already evaluated.
		Set<int[]>closedSet = new HashSet<>();
		// The set of currently discovered nodes still to be evaluated.
		// Initially, only the start node is known.
		Set<int[]>openSet = new HashSet<>();
		openSet.add(start);
		
		// For each node, which node it can most efficiently be reached from.
		// If a node can be reached from many nodes, cameFrom will eventually contain the
		// most efficient previous step.
		HashMap<int[], int[]> cameFrom=new HashMap<>();
		
		// For each node, the cost of getting from the start node to that node.
		HashMap<int[], Double> gScore=new HashMap<>();
		// The cost of going from start to start is zero.
		gScore.put(start, 0.0);
		
		// For each node, the total cost of getting from the start node to the goal
	    // by passing by that node. That value is partly known, partly heuristic.
		HashMap<int[], Double> fScore=new HashMap<>();
		// For the first node, that value is completely heuristic.
		fScore.put(start, heuristicCostEstimate(start, destination));
		
		while ( openSet.size() != 0){
			if (iteration >= MAX_ITERATION)
				throw new MaxIterationException();
			iteration ++;
			// current is the position in openSet having the lowest fScore[] value
			int[] current = getCoordinatesWithLowestFScoreFrom(openSet, fScore);
			
			if (Position.equals(current, destination)){
				return reconstruct_path(cameFrom, current);
			}
			
			openSet.remove(current);
			closedSet.add(current);
			for(int[] neighbour:  (diagonalMovesAllowed) ? getPassableNeighbourCoordinates(current, world) : getPassableDirectlyAdjacentCoordinates(current, world)){
				/* If no diagonal moves are allowed, then only directly adjecant positions should be explored as a next move.
				 	This means that the next nodes to explore should be picked from the second set. 
				 	If diagonal moves are allowed, then the next nodes to explore should be picked from the first set.*/
				if (closedSet.contains(neighbour)){
					continue;		// Ignore the neighbours which are already evaluated.
				}
				if ( ! hasSolidNeighbours(neighbour, world)){
					closedSet.add(neighbour);
					continue;
				}
				// The distance from start to a neighbour.
	            double tentative_gScore = gScore.get(current) + Position.getDistance(Position.getCubeCenter(current), Position.getCubeCenter(neighbour));
			
				if ( ! openSet.contains(neighbour) ) { // Discover a new node
					openSet.add(neighbour);
				}
				else if (tentative_gScore >= gScore.get(neighbour)){
					continue; // This is not a better path.
				}
				
				// This path is the best until now. If it doesn't kill the unit: Record it!
				cameFrom.put(neighbour, current);
				gScore.put(neighbour, tentative_gScore);
				fScore.put(neighbour, tentative_gScore + heuristicCostEstimate(neighbour, destination));
				
			}
		}
		// If no path is found.
		return null;
	}
	
	/**
	 * Checks if the cube with the given coordinates is adjacent
	 * to at least one cube which is solid.
	 * 
	 * @param	coordinates
	 *				The coordinates to check neighbours for.
	 * @param	world
	 *				The world in which to check.
	 *
	 * @return	False if none of the neighbouring cubes is solid.
	 *				| for (int[] position: neighbours)
	 *				|	if ( ! world.getCube(position).isPassable())
	 *				|		then result == true
	 *				| result == false
	 */
	private static boolean hasSolidNeighbours(int[] coordinates, World world) {
		Set<int[]> neighbours = world.getNeighbours(coordinates);
		for (int[] neighbour: neighbours)
			if ( ! world.getCube(neighbour).isPassable())
				return true;
		return false;
	}

	/**
	 * Reconstructs the path hidden in cameFrom.
	 * 
	 * @param cameFrom
	 *				The map to reconstruct the path from
	 *
	 * @param current
	 *				The last part of the path.
	 */
	private static List<int[]> reconstruct_path(HashMap<int[], int[]> cameFrom, int[] current) {
		List<int[]> totalPath = new ArrayList<int[]>();
		totalPath.add(current);
		while (cameFrom.containsKey(current)){
			current = cameFrom.get(current);
			totalPath.add(current);
		}
		Collections.reverse(totalPath);
		return totalPath;
	}

	/**
	 * Returns the coordinates with the lowest fScore in openSet.
	 * 
	 * @param openSet
	 *				The set of coordinates of wich we want the one with the lowest fScore.
	 * @param fScore
	 *				A HashMap of coordinates and their fScores.
	 *
	 * @return	The coordinates with the lowest fScore in openSet.
	 */
	private static int[] getCoordinatesWithLowestFScoreFrom(Set<int[]> openSet, HashMap<int[], Double> fScore) {
		int[] LowestFScoreCoordinates = null;
		Double lowestFScore = null;
		for (int[] coordinates : openSet){
			if (lowestFScore == null){
				LowestFScoreCoordinates = coordinates;
				lowestFScore = fScore.get(coordinates);
			} else if (lowestFScore > fScore.get(coordinates)){
				LowestFScoreCoordinates = coordinates;
				lowestFScore = fScore.get(coordinates);
			}
		}
		return LowestFScoreCoordinates;
	}
	
	/**
	 * Returns a heuristic cost estimate for the path from start to destination.
	 * 
	 * @param start
	 *				The start position.
	 * @param destination
	 *				The destination position.
	 *
	 * @return The distance between the start position and the destination position.
	 *				| Position.getDistance(Position.getCubeCenter(start), Position.getCubeCenter(destination))
	 */
	private static Double heuristicCostEstimate(int[] start, int[] destination) {
		return Position.getDistance(Position.getCubeCenter(start), Position.getCubeCenter(destination));
	}
	
	/**
	 * Returns a set of coordinates which are directly adjacent to the given coordinates.
	 * 
	 * @param	coordinates
	 *				The coordinates to find directly adjacent coordinates for.
	 *
	 * @return	A set of cubes which are directly adjacent and not passable.
	 * 
	 * @throws	IllegalArgumentException
	 *				The given coordinates are illegal for this world.
	 */
	private static Set<int[]> getPassableDirectlyAdjacentCoordinates(int[] coordinates, World world) throws IllegalArgumentException{
		int x = coordinates[0];
		int y = coordinates[1];
		int z = coordinates[2];
		
		if ( ! world.canHaveAsCoordinates(x, y, z))
			throw new IllegalArgumentException();
		Set<int[]> directlyAdjacentCoordinates = new HashSet<>();
		
		if ( (world.canHaveAsCoordinates(x-1,y,z)) && (world.getCube(new int[]{x-1,y,z}).isPassable()) ) 
			directlyAdjacentCoordinates.add(new int[]{x-1,y,z});
		if (world.canHaveAsCoordinates(x+1,y,z) && (world.getCube(new int[]{x+1,y,z}).isPassable()) )
			directlyAdjacentCoordinates.add(new int[]{x+1,y,z});
		
		if (world.canHaveAsCoordinates(x,y-1,z) && (world.getCube(new int[]{x,y-1,z}).isPassable()) )
			directlyAdjacentCoordinates.add(new int[]{x,y-1,z});
		if (world.canHaveAsCoordinates(x,y+1,z) && (world.getCube(new int[]{x,y+1,z}).isPassable()) )
			directlyAdjacentCoordinates.add(new int[]{x,y+1,z});
		
		if (world.canHaveAsCoordinates(x,y,z-1) && (world.getCube(new int[]{x,y,z-1}).isPassable()) )
			directlyAdjacentCoordinates.add(new int[]{x,y,z-1});
		if (world.canHaveAsCoordinates(x,y,z+1) && (world.getCube(new int[]{x,y,z+1}).isPassable()) )
			directlyAdjacentCoordinates.add(new int[]{x,y,z+1});
		
		return directlyAdjacentCoordinates;
	}
	
	/**
	 * Returns a set of coordinates which are directly adjacent to the given coordinates.
	 * 
	 * @param	coordinates
	 *				The coordinates to find directly adjacent coordinates for.
	 *
	 * @return	A set of cubes which are directly adjacent and not passable.
	 * 
	 * @throws	IllegalArgumentException
	 *				The given coordinates are illegal for this world.
	 */
	private static Set<int[]> getPassableNeighbourCoordinates(int[] coordinates, World world) throws IllegalArgumentException{
		int x = coordinates[0];
		int y = coordinates[1];
		int z = coordinates[2];
		
		if ( ! world.canHaveAsCoordinates(x, y, z))
			throw new IllegalArgumentException();
		
		Set<int[]> passableNeighbourCoordinates = new HashSet<>();
		Set<int[]> dummy = world.getNeighbours(x, y, z);
		for (int[] neighbourCoordinates: dummy){
			if (world.getCube(neighbourCoordinates).isPassable())
				passableNeighbourCoordinates.add(neighbourCoordinates);
		}
		return passableNeighbourCoordinates;
	}
	
}
	
	