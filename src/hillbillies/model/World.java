package hillbillies.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.positions.Position;
import hillbillies.positions.UnitPosition;
import hillbillies.util.ConnectedToBorder;
import hillbillies.model.Cube;
import hillbillies.model.Terrain;

/**
 * @version 2.11
 */

/**
 * A class of worlds involving...
 * 	
 * @invar   Each world must have proper factions.
 * 			| hasProperFactions()
 * @invar   Each world must have proper entities.
 * 			| hasProperEntities()
 * @invar  The maximum x-value of each world must be a valid maximum x-value for any
 *         world.
 *      	| isValidMaximumXValue(getMaximumXValue())
 * @invar  The maximum y-value of each world must be a valid maximum y-value for any
 *         world.
 *       	| isValidMaximumYValue(getMaximumYValue())
 * @invar  The maximum z-value of each world must be a valid maximum z-value for any
 *         world.
 *       	| isValidMaximumZValue(getMaximumZValue())
 * 
 * @author Sander Mergan, Thomas Vranken
 */
public class World {

	/**
	 * Initialize this new world as a non-terminated world with 
	 * no units yet and set the terrains of this world to the given terrain.
	 * 
	 * @param	terrain
	 * 					The terrain for this new world.
	 * @param	modelListener
	 * 					The terrainChangeListener for this new world
	 * @post	This new world has no units yet.
	 *				| new.getNbUnits() == 0
	 * @post	The dimensions of this new world are equal to those of the given terrain.
	 *				| this.getMaximumXValue() == terrain.length &&
	 *				|	this.getMaximumYValue() == terrain[0].length &&
	 *				|	this.getMaximumZValue() == terrain[0][0].length
	 *
	 *	@throws IllegalArgumentException
	 *				The given terrain does not have valid dimensions.
	 *				| ( (!isValidMaximumXValue(maxXValue)) || (!isValidMaximumYValue(maxYValue)) || (!isValidMaximumZValue(maxZValue)) )
	 */
	@Raw
	public World(int[][][] terrain, TerrainChangeListener modellistener) throws IllegalArgumentException {
		int maxXValue = terrain.length;
		int maxYValue = terrain[0].length;
		int maxZValue = terrain[0][0].length;
		if ( (!isValidMaximumXValue(maxXValue)) || (!isValidMaximumYValue(maxYValue)) || (!isValidMaximumZValue(maxZValue)) )
			throw new IllegalArgumentException();
		
		// Impossible to use setters because maximumXValue, maximumYValue and maximumZValue are final and final variables can only be set in the constructor.
		if (! isValidMaximumXValue(maxXValue))
			throw new IllegalArgumentException();
		this.maximumXValue = maxXValue;
		if (! isValidMaximumYValue(maxYValue))
			throw new IllegalArgumentException();
		this.maximumYValue = maxYValue;
		if (! isValidMaximumZValue(maxZValue))
			throw new IllegalArgumentException();
		this.maximumZValue = maxZValue;
		
		this.connectedToBorder = new ConnectedToBorder(this.getMaximumXValue(), this.getMaximumYValue(), this.getMaximumZValue());
		
		this.setTerrainChangeListener(modellistener);
		
		cubes = new Cube[this.getMaximumXValue()][this.getMaximumYValue()][this.getMaximumZValue()];
		
		for (int x = 0; x < this.getMaximumXValue(); x++){
			for (int y = 0; y < this.getMaximumYValue(); y++){
				for (int z = 0; z < this.getMaximumZValue(); z++){
					// Set the terrain type for all cubes in the world.
					cubes[x][y][z] = new Cube(terrain[x][y][z], this, new int[]{x,y,z});
					// Initialize connectedToBorder correctly.
					List<int[]> temp = new ArrayList<>();
					if ( isPassable(new int[]{x,y,z})){
						temp = this.changeSolidToPassable(x, y, z);
						if ( ! temp.isEmpty())
							this.collapsingCubes.addAll(temp);
						
						temp.clear();
					}
				}
			}
		}
		this.caveInCollapsingCubes();
	}		
	
	/**
	 * Returns a textual representation of this faction.
	 */
	@Override
	public String toString() {
		String string = "<<< World >>> ";
		return string;
	}
	
	/**
	 * Sets the terrainChangeListener of this world to the given terrainCangeListener.
	 * @param terrainChangeListener
	 *				The new terrainChangeListener of this world.
	 *	@post	The new terrainChangeListener is equal to the given terrainChangeListener.
	 */
	@Basic
	@Raw
	private void setTerrainChangeListener(TerrainChangeListener terrainChangeListener){
		this.terrainChangeListener = terrainChangeListener;
	}
	
	/**
	 * Returns the terrainChangeListener of this world.
	 */
	@Basic
	@Raw
	public TerrainChangeListener getTerrainChangeListener(){
		return this.terrainChangeListener;
	}
	
	/**
	 *	A variable that references the terrainChangeListener of this world.
	 */
	private TerrainChangeListener terrainChangeListener;
	
	/**
	 * An instance of the connected-to-border interface.
	 */
	private  final ConnectedToBorder connectedToBorder;
	
	// ==================================================================================
	// Destructor for worlds.
	// ==================================================================================
	
	/**
	 * Terminate this world.
	 *
	 * @post   This world  is terminated.
	 *       | new.isTerminated() == true
	 * @effect   All the entities in this world are terminated.
	 */
	public void terminate() {
		HashSet<Entity> dummy = new HashSet<Entity>();
		dummy.addAll(this.entities);
		for (Entity entity: dummy){
			entity.terminate();
		}
		this.isTerminated = true;
	 }
	 
	 /**
	  * Return a boolean indicating whether or not this world
	  * is terminated.
	  */
	 @Basic @Raw
	 public boolean isTerminated() {
		 return this.isTerminated;
	 }
	 
	 /**
	  * Variable registering whether this person is terminated.
	  */
	 private boolean isTerminated = false;
	 
	
	// ==================================================================================
	// Methods concerning the factions of this world.
	// ==================================================================================
	
	/**
	 * Check whether this world has the given faction as one of its
	 * factions.
	 * 
	 * @param  faction
	 *         The faction to check.
	 */
	@Basic
	@Raw
	public boolean hasAsFaction(@Raw Faction faction) {
		return factions.contains(faction);
	}

	/**
	 * Check whether this world can have the given faction
	 * as one of its factions.
	 * 
	 * @param  faction
	 *         The faction to check.
	 * @return True if and only if the given faction is effective
	 *         and that faction is a valid faction for a world.
	 *       | result ==
	 *       |   (faction != null) &&
	 *       |   Faction.canHaveAsWorld(this)
	 */
	@Raw
	public boolean canHaveAsFaction(Faction faction) {
		return (faction != null) && (faction.canHaveAsWorld(this));
	}

	/**
	 * Check whether this world has proper factions attached to it.
	 * 
	 * @return True if and only if this world can have each of the
	 *         factions attached to it as one of its factions,
	 *         and if each of these factions references this world as
	 *         the world to which they are attached.
	 *       | for each faction in Faction:
	 *       |   if (hasAsFaction(faction))
	 *       |     then canHaveAsFaction(faction) &&
	 *       |          (faction.getWorld() == this)
	 */
	public boolean hasProperFactions() {
		for (Faction faction : factions) {
			if (!canHaveAsFaction(faction))
				return false;
			if (faction.getWorld() != this)
				return false;
		}
		return true;
	}
	
	/**
	 * Returns the number of active (non-empty) factions associated with this world0
	 * @return	The total number of factions in this world which have
	 * 			at least one unit associated with them.
	 */
	public int getNbActiveFactions() {
		int count = 0;
		for (Faction faction: factions)
			if (faction.getNbUnits() > 0)
				count++;
		return count;
	}

	/**
	 * Add the given faction to the set of factions of this world.
	 * 
	 * @param  faction
	 *         The faction to be added.
	 * @pre    The given faction is effective and already references
	 *         this world.
	 *       | (faction != null) && (faction.getWorld() == this)
	 * @post   This world has the given faction as one of its factions.
	 *       | new.hasAsFaction(faction)
	 */
	void addFaction(@Raw Faction faction) throws IllegalArgumentException{
		if ((faction == null) || (faction.getWorld() != this))
			throw new IllegalArgumentException();
		factions.add(faction);
	}

	/**
	 * Remove the given faction from the set of factions of this world.
	 * 
	 * @param  faction
	 *         The faction to be removed.
	 * @pre    This world has the given faction as one of
	 *         its factions, and the given faction does not
	 *         reference any world.
	 *       | this.hasAsFaction(faction) &&
	 *       | (faction.getWorld() == null)
	 * @post   This world no longer has the given faction as
	 *         one of its factions.
	 *       | ! new.hasAsFaction(faction)
	 */
	@Raw
	void removeFaction(Faction faction) {
		assert this.hasAsFaction(faction) && (faction.getWorld() == null);
		factions.remove(faction);
	}

	/**
	 * Variable referencing a set collecting all the factions
	 * of this world.
	 * 
	 * @invar  The referenced set is effective.
	 *       | factions != null
	 * @invar  Each faction registered in the referenced list is
	 *         effective and not yet terminated.
	 *       | for each faction in factions:
	 *       |   ( (faction != null) &&
	 *       |     (! faction.isTerminated()) )
	 */
	private final Set<Faction> factions = new HashSet<Faction>();
	
	/**
	 * Returns a set collecting all the factions of this world. 
	 * 
	 * @return	The resulting set does not contain a null reference.
	 * @return	Each faction in the resulting set is attached to this world,
	 * 			and vice versa.
	 */
	// NOTE: this is the formal way (same as in textbook) to return all objects of the set.
	public Set<Faction> getFactions() {
		return new HashSet<Faction>(this.factions);
	}
	
	/**
	 * Checks if this world is at its maximum active faction capacity.
	 * @return	True if the amount of acitve factions is less than the maximum.
	 */
	public boolean hasRoomForFaction() {
		return (this.getNbActiveFactions() < World.MAX_FACTIONS);
	}
	
	/**
	 * Returns the active faction of this world with the least members. 
	 * The returned faction must have less members than the maximum amount of members.
	 * 
	 * @return 	The faction with the least amount of units and free space for new units.
	 * 			If no such faction exists, return null.
	 */
	private Faction getAvailableFactionWithLeastMembers() {
		Faction availableFactionWithLeastMembers = null;
		for (Faction faction: this.getFactions()){
			if (faction.getNbUnits() < Faction.MAX_UNITS_FACTION){
				if ( (availableFactionWithLeastMembers == null) || (faction.getNbUnits() < availableFactionWithLeastMembers.getNbUnits()) ){
					availableFactionWithLeastMembers = faction;
				}
			}
		}
		return availableFactionWithLeastMembers;
	}

	/**
	 * Symbolic constant denoting the probability of
	 * creating a boulder or rock after a cube collapses.
	 */
	public static final double DROP_CHANCE = 0.25;
	
	/**
	 * Symbolic constant denoting the maximum amount of units in this world.
	 */
	public static final int MAX_UNITS_WORLD = 100;
	
	/**
	 * Symbolic constant denoting the maximum amount of factions in this world.
	 */
	public static final int MAX_FACTIONS = 5;
	
	/**
	 * Return the maximum x-value of this world.
	 */
	@Basic @Raw @Immutable
	public int getMaximumXValue() {
		return this.maximumXValue;
	}
	
	/**
	 * Check whether the given maximum x-value is a valid maximum x-value for
	 * any world.
	 *  
	 * @param  	maximumXValue
	 *         	The maximum x-value to check.
	 * @return 	The dimension must be greater than 0.
	 *       	| result == (maximumXValue > 0)
	*/
	@Raw
	public static boolean isValidMaximumXValue(int maximumXValue) {
		return (maximumXValue > CUBE_COORDINATE_MIN);
	}
	
	/**
	 * A variable registering the amount of cubes in the x-direction.
	 */
	private final int maximumXValue;
	
	/**
	 * Return the maximum y-value of this world.
	 */
	@Basic @Raw @Immutable
	public int getMaximumYValue() {
		return this.maximumYValue;
	}
	
	/**
	 * Check whether the given maximum y-value is a valid maximum y-value for
	 * any world.
	 *  
	 * @param  	maximumYValue
	 *         	The maximum y-value to check.
	 * @return 	The dimension must be greater than 0.
	 *       	| result == (maximumYValue > 0)
	*/
	@Raw
	public static boolean isValidMaximumYValue(int maximumYValue) {
		return (maximumYValue > CUBE_COORDINATE_MIN);
	}
	
	/**
	 * A variable registering the amount of cubes in the y-direction.
	 */
	private final int maximumYValue;
	
	/**
	 * Return the maximum z-value of this world.
	 */
	@Basic @Raw @Immutable
	public int getMaximumZValue() {
		return this.maximumZValue;
	}
	
	/**
	 * Check whether the given maximum z-value is a valid maximum z-value for
	 * any world.
	 *  
	 * @param  	maximumZValue
	 *         	The maximum z-value to check.
	 * @return 	The dimension must be greater than 0.
	 *       	| result == (maximumZValue > 0)
	*/
	@Raw
	public static boolean isValidMaximumZValue(int maximumZValue) {
		return (maximumZValue > CUBE_COORDINATE_MIN);
	}
	
	/**
	 * A variable registering the amount of cubes in the z-direction.
	 */
	private final int maximumZValue;
	
	
	//================================================================================
	// Methods concerning the cubes this world is built of.
	//================================================================================
	
	/**
	 * Symbolic constant denoting the minimal value of a cube coordinate.
	 * A cube can have this value as one of its coordinates.
	 */
	public static final int CUBE_COORDINATE_MIN = 0;
	
	/**
	 * Returns the cube object at the given coordinates.
	 * @param 	x
	 * 			The x-coordinate
	 * @param 	y
	 * 			The y-coordinate
	 * @param 	z
	 * 			The z-coordinate
	 * @return	The cube in the array of cubes with the given coordinates.
	 * 			result == world[x][y][z]
	 * @throws	IllegalArgumentException
	 * 			The given coordinates are invalid.
	 */
	public Cube getCube(int x, int y, int z) throws IllegalArgumentException {
		if (! this.canHaveAsCoordinates(x, y, z))
			throw new IllegalArgumentException();
		return cubes[x][y][z];
	}
	
	/**
	 * Returns the cube object at the given coordinates.
	 * @param 	coordinates
	 * 			The given coordinates.
	 * @return	The cube in the array of cubes with the given coordinates.
	 * 			result == getCube(coordinates[0],coordinates[1],coordinates[2])
	 */
	public Cube getCube(int[] coordinates) {
		return this.getCube(coordinates[0],coordinates[1],coordinates[2]);
	}
	
	/**
	 * An array storing all the cubes this world consists of.
	 */
	private Cube[][][] cubes = new Cube[this.getMaximumXValue()]
										[this.getMaximumYValue()]
										[this.getMaximumZValue()];
	
	/**
	 * Checks if the given coordinates are valid in this world.
	 * 
	 * @param	x
	 * 			The x-coordinate of the coordinates.
	 * @param 	y
	 * 			The y-coordinate of the coordinates.
	 * @param 	z
	 * 			The z-coordinate of the coordinates.
	 * @return	True if all coordinates are within the boundaries
	 * 			of this world.
	 */
	public boolean canHaveAsCoordinates(int x, int y, int z) {
		int[] cubeCoordinates = new int[]{x,y,z};
		Position temp = new Position(this,new int[]{0,0,0});
		return temp.canHaveAsCoordinates(Position.getCubeCenter(cubeCoordinates));
	}
	
	/**
	 * Checks if the given coordinates are valid in this world.
	 * 
	 * @param	coordinates
	 * 			The given coordinates.
	 * @return	True if the size equals 3, and if all 
	 * 				coordinates are within the boundaries of this world.
	 */
	public boolean canHaveAsCoordinates(int[] coordinates) {
		return ( (this.canHaveAsCoordinates(Position.getCubeCenter(coordinates))) && (coordinates.length == 3) );
	}
	
	/**
	 * Checks if the given coordinates are valid in this world.
	 * 
	 * @param	coordinates
	 * 			The given coordinates.
	 * @return	True if the size equals 3, and if all 
	 * 				coordinates are within the boundaries of this world.
	 */
	public boolean canHaveAsCoordinates(double[] coordinates) {
		Position temp = new Position(this,new int[]{0,0,0});
		return ( (temp.canHaveAsCoordinates(coordinates)) && (coordinates.length == 3) );
	}
	
	/**
	 * Returns a set of coordinates which are neighbours of the cube with
	 * the given coordinates.
	 * 
	 * @param	x
	 * 				The x-coordinate of the cube.
	 * @param 	y
	 * 				The y-coordinate of the cube.
	 * @param 	z
	 * 				The z-coordinate of the cube.
	 * @return	A set of cube coordinates which are neighbours of the given cube
	 * 				and have valid coordinates for this world.
	 */
	public Set<int[]> getNeighbours(int x, int y, int z) {
		return (this.getNeighbours(new int[]{x,y,z}));
	}
	
	/**
	 * Returns a set of coordinates which are neighbours of the cube with
	 * the given coordinates.
	 * 
	 * @param	coordinates
	 * 				The given coordinates
	 * @return	A set of cube coordinates which are neighbours of the given cube
	 * 				and have valid coordinates for this world.
	 */
	public Set<int[]> getNeighbours(int[] coordinates) {
		return (this.getValidCubeCoordinatesInRange(coordinates, 1));
	}
	
	/**
	 * Returns the cube right below the given cube coordinates in this world.
	 * @param 	cubeCoordinates
	 * 			An array with given coordinates (x,y,z).
	 * @return	The cube with coordinates (x,y,z-1) in this world.
	 * @throws 	IllegalArgumentException
	 * 			The given array of coordinates is invalid.
	 */
	Cube getCubeBelow(int[] cubeCoordinates) throws IllegalArgumentException {
		return this.getCube(cubeCoordinates[0], cubeCoordinates[1], cubeCoordinates[2]-1);
	}
	
	/**
	 * Returns the terrain type of the cube with the given coordinates.
	 * @param 	x
	 * 			The x-coordinate of the cube.
	 * @param 	y
	 * 			The y-coordinate of the cube.
	 * @param 	z
	 * 			The z-coordinate of the cube.
	 * @return	the terrain value of the cube.
	 * 			| result == cubes.get(x).get(y).get(z).getTerrainType();
	 * 
	 */
	public Terrain getTerrain(int x, int y, int z) {
		return cubes[x][y][z].getTerrainType();
	}
	
	/**
	 * Returns a list of all units standing in the given cube.
	 * @param 	x
	 * 			The x-coordinate of the cube.
	 * @param 	y
	 * 			The y-coordinate of the cube.
	 * @param 	z
	 * 			The z-coordinate of the cube.
	 * @return	A list containing all units which currently occupy this cube.
	 */
	public Set<Unit> getUnitsInCube(int x, int y, int z) {
		Set<Unit> unitsOnCube = new HashSet<>();
		int[]cubeCoordinates = {x,y,z};
		
		for (Unit unit: this.getUnits()){
			if (Position.equals(unit.getPosition().getCubeCoordinates(), cubeCoordinates))
				unitsOnCube.add(unit);
		}
		return unitsOnCube;
	}
	
	/**
	 * Return a set of cubes which are the directly adjacent cubes
	 * (as defined in the assignment) of the given cube.
	 * @param 	x
	 * 			The x-coordinate of the cube.
	 * @param 	y
	 * 			The y-coordinate of the cube.
	 * @param 	z
	 * 			The z-coordinate of the cube.
	 * @return	A set of cubes which are directly adjacent.
	 * @throws	IllegalArgumentException
	 * 			The given coordinates are illegal for this world.
	 */
	public Set<int[]> getDirectlyAdjacentCoordinates(int x, int y, int z) throws IllegalArgumentException{
		if (! this.canHaveAsCoordinates(x, y, z))
			throw new IllegalArgumentException();
		Set<int[]> directlyAdjacentCoordinates = new HashSet<>();
		
		if (this.canHaveAsCoordinates(x-1,y,z))
			directlyAdjacentCoordinates.add(new int[]{x-1,y,z});
		if (this.canHaveAsCoordinates(x+1,y,z))
			directlyAdjacentCoordinates.add(new int[]{x+1,y,z});
		
		if (this.canHaveAsCoordinates(x,y-1,z))
			directlyAdjacentCoordinates.add(new int[]{x,y-1,z});
		if (this.canHaveAsCoordinates(x,y+1,z))
			directlyAdjacentCoordinates.add(new int[]{x,y+1,z});
		
		if (this.canHaveAsCoordinates(x,y,z-1))
			directlyAdjacentCoordinates.add(new int[]{x,y,z-1});
		if (this.canHaveAsCoordinates(x,y,z+1))
			directlyAdjacentCoordinates.add(new int[]{x,y,z+1});
		
		return directlyAdjacentCoordinates;
	}
	
	/**
	 * Return the list of coordinates that are no longer connected to a border of the
	 * world due to changing the cube at the given coordinates to passable.
	 * @param 	x
	 * 			The x-coordinate of the cube.
	 * @param 	y
	 * 			The y-coordinate of the cube.
	 * @param 	z
	 * 			The z-coordinate of the cube.
	 * @return A list of coordinates where there is solid terrain that is no longer connected to the border.
	 * @throws IllegalArgumentException
	 * 			The given coordinates are invalid for this world.
	 */
	private List<int[]> changeSolidToPassable(int x, int y, int z) throws IllegalArgumentException {
		if (! this.canHaveAsCoordinates(x, y, z))
			throw new IllegalArgumentException();
		
		return this.connectedToBorder.changeSolidToPassable(x, y, z);
	}
	
	/**
	 * Returns whether the cube at the given coordinates is a solid cube that is
	 * connected to a border of the world through other directly adjacent solid
	 * cubes.
	 * 
	 * @param x
	 *            The x-coordinate of the cube to test
	 * @param y
	 *            The y-coordinate of the cube to test
	 * @param z
	 *            The z-coordinate of the cube to test
	 * @return true if the cube is connected; false otherwise
	 */
	public boolean isSolidConnectedToBorder(int x, int y, int z) throws IllegalArgumentException {
		if (! this.canHaveAsCoordinates(x, y, z))
			throw new IllegalArgumentException();
		return this.connectedToBorder.isSolidConnectedToBorder(x, y, z);
	}
	
	/**
	 * Collapses the cube at the given cube coordinates.
	 * @param cubeCoordinates
	 *				The cube coordinates to collaps a cube at.
	 */
	protected void collapsCube(int[] cubeCoordinates){
		int x =cubeCoordinates[0];
		int y =cubeCoordinates[1];
		int z =cubeCoordinates[2];
		
		Cube cube = this.getCube(x, y, z);
		
		this.dropItem(DROP_CHANCE, cubeCoordinates);
		
		cube.setTerrainType(Terrain.AIR);
		
		collapsingCubes.addAll(this.changeSolidToPassable(x, y, z));
		this.terrainChangeListener.notifyTerrainChanged(x, y, z);
	}
	
	/**
	 * Drops an item in accordance with the terrain of the given cube with a given chance at the given coordinates.
	 * @param chance
	 *				The drop chance of an item.
	 */
	private void dropItem(double chance, int[] cubeCoordinates) throws NullPointerException,IllegalStateException{
		Cube cube = this.getCube(cubeCoordinates);
		double dropChance = World.DROP_CHANCE;
		double succesRate = Math.random();
		if (succesRate <= dropChance){
			if (cube.getTerrainType() == Terrain.WOOD)
				this.addEntity(new Log(this, cubeCoordinates));
			if (cube.getTerrainType() == Terrain.ROCK)
				this.addEntity(new Boulder(this, cubeCoordinates));
		}
	}
	
	/**
	 * Makes the cubes that are listed for caving in actually cave in.
	 * 
	 * @effect The cubes in the list collapsingCubes cave in.
	 */
	private void caveInCollapsingCubes() throws NullPointerException, IllegalStateException{
		for (int[] collapsingCube: this.collapsingCubes){
			this.dropItem(DROP_CHANCE, collapsingCube);
			this.getCube(collapsingCube).setTerrainType(Terrain.AIR);;
			this.terrainChangeListener.notifyTerrainChanged(collapsingCube[0], collapsingCube[1], collapsingCube[2]);
		}
		collapsingCubes.clear();
	}
	
	/**
	 * Returns a set of all integer coordinates which are at most r cubes away from
	 * the given coordinates. Only valid coordinates in this world are included.
	 * The given coordinates are also included if it is valid.
	 * @param 	coordinates
	 * 				The given start coordinates.
	 * @param 	range
	 * 				The given range.
	 * @return	A set of coordinates which are valid. Their integer coordinates 
	 * 				are at most r away from the given coordinates.
	 */
	public Set<int[]> getValidCubeCoordinatesInRange(int[] coordinates, int range) {
		Set<int[]> result = new HashSet<>();
		Set<int[]> allCubes = Position.getCubeCoordinatesInRange(coordinates, range);
		for (int[] cubeCoordinates: allCubes)
			if (this.canHaveAsCoordinates(cubeCoordinates) && ( ! Position.equals(coordinates, cubeCoordinates)))
				result.add(cubeCoordinates);
		return result ;
	}
	
	/**
	 * Return  integer coordinates which are at most x cubes away from the given coordinates,
	 * where x is the given range, and which is passable and valid for this world.
	 * If no such coordinates can be found, return the cube coordinates of the original coordinates.
	 * @param 	coordinates
	 * 				The start coordinates.
	 * @param 	range
	 * 				How far the new coordinates may be from the given coordinates (in 3 dimensions).
	 * @return	Passable, valid coordinates which is at most 'range' cubes away from the given coordinates,
	 * 				or the original coordinates.	
	 * @throws	IllegalArgumentException
	 * 			There is no valid coordinates in range.
	 */
	public int[] getRandomValidCubeCoordinatesInRange(double[] coordinates, int range) {
		Set<int[]> cubeSet = this.getValidCubeCoordinatesInRange(Position.getCubeCoordinates(coordinates), range);
		int size = cubeSet.size();
		if (size == 0)
			throw new IllegalArgumentException("No valid coordinates within range.");
		int randomIndex = new Random().nextInt(size);
		int count = 0;
		for (int[] cubeCoordinates: cubeSet) {
			if (count == randomIndex) 
				return cubeCoordinates;
			else
				count++;
		}
		// If for some reason no coordinates are found, return the original cube.
		// (This should almost never happen)
		return Position.getCubeCoordinates(coordinates);
	}
	
	/**
	 * Return an integer array of coordinates which are at most x cubes away from the given coordinates,
	 * where x is the given range, and which are solid and valid for this world.
	 * @param 	coordinates
	 * 				The start coordinates.
	 * @param 	range
	 * 				How far the new coordinates may be from the given coordinates (in 3 dimensions).
	 * @return	Passable, valid coordinates which is at most 'range' cubes away from the given coordinates,
	 * 				or the original coordinates.	
	 * @throws	IllegalArgumentException
	 * 			There is no valid coordinates in range.
	 */
	public int[] getRandomSolidCubeCoordinatesInRange(double[] coordinates, int range) {
		Set<int[]> cubeCoordinatesInRange = this.getValidCubeCoordinatesInRange(Position.getCubeCoordinates(coordinates), range);
		for (int[] cubeCoordinates: cubeCoordinatesInRange){
			if ( ! this.getCube(cubeCoordinates).isPassable())
				return cubeCoordinates;
		}
		
		// If this point is reached, then there are no solid adjacent positions.
		// This should never happen because that would mean the entity is falling and work cannot be started while falling.
		return null;
	}
	
	/**
	 * Return integer coordinates which are at most x cubes away from the given coordinates,
	 * where x is the given range, and which are valid coordinates for a unit of this world.
	 * If no such coordinates can be found, return the cube coordinates of the original coordinates.
	 * @param 	coordinates
	 * 				The start coordinates.
	 * @param 	range
	 * 				How far the new coordinates may be from the given coordinates (in 3 dimensions).
	 * @return	Valid unit coordinates which is at most 'range' cubes away from the given coordinates,
	 * 				or the original coordinates.	
	 * @throws	IllegalArgumentException
	 * 			There are no valid coordinates in range.
	 */
	public int[] getRandomUnitCoordinatesInRange(double[] coordinates, int range) {
		Set<int[]> cubeSet = this.getValidCubeCoordinatesInRange(Position.getCubeCoordinates(coordinates), range);
		Set<int[]> reducedSet = new HashSet<>();
		int size = cubeSet.size();
		if (size == 0)
			throw new IllegalArgumentException("No valid coordinates within range.");
		
		UnitPosition temp = new UnitPosition(this, coordinates);
		for (int[] cubeCoordinates: cubeSet){
			if (temp.canHaveAsCoordinates(cubeCoordinates))
				reducedSet.add(cubeCoordinates);
		}
		int randomIndex = new Random().nextInt(reducedSet.size());
		int count = 0;
		for (int[] cubeCoordinates: reducedSet) {
			if (count == randomIndex) 
				return cubeCoordinates;
			else
				count++;
		}
		// If for some reason no coordinates are found, return the original cube.
		return Position.getCubeCoordinates(coordinates);
	}
	
	
	// =================================================================================================
	// Methods concerning the entities in this world. (bidirectional association)
	// =================================================================================================

	/**
	 * Check whether this world has the given entity as one of its
	 * entities.
	 * 
	 * @param  entity
	 *         The entity to check.
	 */
	@Basic
	@Raw
	public boolean hasAsEntity(@Raw Entity entity) {
		return entities.contains(entity);
	}

	/**
	 * Check whether this world can have the given entity
	 * as one of its entities.
	 * 
	 * @param  entity
	 *         The entity to check.
	 * @return True if and only if the given entity is effective
	 *         and that entity is a valid entity for a world.
	 */
	@Raw
	public boolean canHaveAsEntity(Entity entity) {
		return (entity != null) && (entity.canHaveAsWorld(this));
	}

	/**
	 * Check whether this world has proper entities attached to it.
	 * 
	 * @return True if and only if this world can have each of the
	 *         entities attached to it as one of its entities,
	 *         and if each of these entities references this world as
	 *         the world to which they are attached.
	 */
	public boolean hasProperEntities() {
		for (Entity entity : entities) {
			if (!canHaveAsEntity(entity))
				return false;
			if (entity.getWorld() != this)
				return false;
		}
		return true;
	}

	/**
	 * Return the number of entities associated with this world.
	 *
	 * @return  The total number of entities collected in this world.
	 */
	public int getNbEntities() {
		return entities.size();
	}

	/**
	 * Add the given entity to the set of entities of this world.
	 * 
	 * @param  entity
	 *         The entity to be added.
	 * @pre    The given entity is effective and already references
	 *         this world.
	 *       | (entity != null) && (entity.getWorld() == this)
	 * @post   This world has the given entity as one of its entities.
	 *       | new.hasAsEntitie(entity)
	 */
	public void addEntity(@Raw Entity entity) {
		assert (entity != null) && (entity.getWorld() == this);
		entities.add(entity);
	}

	/**
	 * Remove the given entity from the set of entities of this world.
	 * 
	 * @param  entity
	 *         The entity to be removed.
	 * @pre    This world has the given entity as one of
	 *         its entities, and the given entity does not
	 *         reference any world.
	 *       | this.hasAsEntity(entity) &&
	 *       | (entity.getWorld() == null)
	 * @post   This world no longer has the given entity as
	 *         one of its entities.
	 *       | ! new.hasAsEntity(entity)
	 */
	@Raw
	public void removeEntity(Entity entity) {
		assert this.hasAsEntity(entity) && (entity.getWorld() == null);
		entities.remove(entity);
		entity.setWorld(null);
	}

	/**
	 * Variable referencing a set collecting all the entities
	 * of this world.
	 * 
	 * @invar  The referenced set is effective.
	 *       | entities != null
	 * @invar  Each entity registered in the referenced list is
	 *         effective and not yet terminated.
	 *       | for each entity in entities:
	 *       |   ( (entity != null) &&
	 *       |     (! entity.isTerminated()) )
	 */
	private final Set<Entity> entities = new HashSet<Entity>();
	
	/**
	 * Returns a set collecting all the entities of this world. 
	 * 
	 * @return	The resulting set does not contain a null reference.
	 * @return	Each entities in the resulting set is attached to this world,
	 * 			and vice versa.
	 */
	// NOTE: this is the formal way (same as in textbook) to return all objects of the set.
	public Set<Entity> getEntities() {
		return new HashSet<Entity>(this.entities);
	}
	
	/**
	 * Returns an item of this world that has a position equal to the given position.
	 * @param 	position
	 *				The position to find an item at.
	 * @return	An item which occupies the cube with the given position.
	 * 				Return null if no such item exists.
	 */
	public Item getItemAt(int[] position){
		for (Entity entity: this.getEntities()){
			if (entity instanceof Item){
				int[] entityPosition = entity.getPosition().getCubeCoordinates();
				if (Position.equals(position, entityPosition))
					return (Item) entity;
			}
		}
		return null;
	}
	
	/**
	 * Checks if their are any items at the given position.
	 * @param position
	 *				The position to check.
	 * @return	True if there is an item in this world which cube position is equal to the given position.	
	 */
	public boolean hasItemAt(int[] position){
		Item item = this.getItemAt(position);
		if (item == null)
			return false;
		else
			return true;
	}
	
	
	// =================================================================================================
	// Specific methods for units of this world.
	// =================================================================================================
	
	/**
	 * Returns a set collecting all the units of this world. 
	 * 
	 * @return	The resulting set does not contain a null reference.
	 * @return	Each unit in the resulting set is attached to this world,
	 * 			and vice versa.
	 */
	public Set<Unit> getUnits() {
		Set<Entity> allEntities = this.getEntities();
		Set<Unit> result = new HashSet<>();
		for (Entity entity : allEntities)
			if (entity instanceof Unit)
				result.add((Unit)entity);
		return result;	
	}
	
	/**
	 * Return the number of units associated with this world.
	 *
	 * @return  The total number of units collected in this world.
	 */
	public int getNbUnits() {
		return this.getUnits().size();
	}
	
	/**
	 * Returns a unit of this world that has a position equal to the given position.
	 * @param position
	 *				The position to find a unit at.
	 * @return	A unit which occupies the cube with the given position.
	 * 				Return null if no such unit exists.
	 */
	public Unit getUnitAt(int[] position){
		for (Unit unit: this.getUnits()){
			int[] unitPosition = unit.getPosition().getCubeCoordinates();
			if (Position.equals(position, unitPosition))
				return unit;
		}
		return null;
	}
	
	/**
	 * Checks if their are any units at the given position.
	 * @param position
	 *				The position to check.
	 * @return	True if there is a unit in this world which cube position is equal to the given position.	
	 */
	public boolean hasUnitAt(int[] position){
		Unit unit = this.getUnitAt(position);
		if (unit == null)
			return false;
		else
			return true;
	}
	
	// =================================================================================================
	// Specific methods for logs of this world.
	// =================================================================================================
	
	/**
	 * Returns a set collecting all the logs of this world. 
	 * 
	 * @return	The resulting set does not contain a null reference.
	 * @return	Each log in the resulting set is attached to this world,
	 * 			and vice versa.
	 */
	public Set<Log> getLogs() {
		Set<Entity> allEntities = this.getEntities();
		Set<Log> result = new HashSet<>();
		for (Entity entity : allEntities)
			if (entity instanceof Log)
				result.add((Log)entity);
		return result;	
	}
	
	/**
	 * Return the number of logs associated with this world.
	 *
	 * @return  The total number of logs collected in this world.
	 */
	public int getNbLogs() {
		return this.getLogs().size();
	}
	
	/**
	 * Returns a log of this world that has a position equal to the given position.
	 * @param position
	 *				The position to find a log at.
	 * @return	A log which occupies the cube with the given position.
	 * 				Return null if no such log exists.
	 */
	public Log getLogAt(int[] position){
		for (Log log: this.getLogs()){
			int[] logPosition = log.getPosition().getCubeCoordinates();
			if (Position.equals(position, logPosition))
				return log;
		}
		return null;
	}
	
	/**
	 * Checks if their are any logs at the given position.
	 * @param position
	 *				The position to check.
	 * @return	True if there is a log in this world which cube position is equal to the given position.	
	 */
	public boolean hasLogAt(int[] position){
		Log log = this.getLogAt(position);
		if (log == null)
			return false;
		else
			return true;
	}
	
	// =================================================================================================
	// Specific methods for boulders of this world.
	// =================================================================================================
	
	/**
	 * Returns a set collecting all the boulders of this world. 
	 * 
	 * @return	The resulting set does not contain a null reference.
	 * @return	Each boulder in the resulting set is attached to this world,
	 * 			and vice versa.
	 */
	public Set<Boulder> getBoulders() {
		Set<Entity> allEntities = this.getEntities();
		Set<Boulder> result = new HashSet<>();
		for (Entity entity : allEntities)
			if (entity instanceof Boulder)
				result.add((Boulder)entity);
		return result;	
	}
	
	/**
	 * Return the number of boulders associated with this world.
	 *
	 * @return  The total number of boulders collected in this world.
	 */
	public int getNbBoulders() {
		return this.getBoulders().size();
	}
	
	/**
	 * Returns a boulder of this world that has a position equal to the given position.
	 * @param position
	 *				The position to find a boulder at.
	 * @return	A boulder which occupies the cube with the given position.
	 * 				Return null if no such boulder exists.
	 */
	public Boulder getBoulderAt(int[] position){
		for (Boulder boulder: this.getBoulders()){
			int[] boulderPosition = boulder.getPosition().getCubeCoordinates();
			if (Position.equals(position, boulderPosition))
				return boulder;
		}
		return null;
	}
	
	/**
	 * Checks if their are any boulders at the given position.
	 * @param position
	 *				The position to check.
	 * @return	True if there is a boulder in this world which cube position is equal to the given position.	
	 */
	public boolean hasBoulderAt(int[] position){
		Boulder boulder = this.getBoulderAt(position);
		if (boulder == null)
			return false;
		else
			return true;
	}
	
	// ===============================================================================================
	// Methods for creating a new random unit.
	// ===============================================================================================
	
	/**
	 * Creates a new unit with random attributes at a random location.
	 * If the maximal amount of units for a world is reached, then null is returned
	 * 
	 * @return	A new unit with this world as its world and random valid name, weight, strength, agility, toughness and initial coordinates.
	 *				If the maximal amount of units for a world is reched, then null is returned.
	 */
	public Unit spawnUnit(boolean enableDefaultBehavior) throws IllegalStateException, IllegalArgumentException, NullPointerException {
		// Units should be silently rejected if there are to many.
		if (this.getNbUnits()<World.MAX_UNITS_WORLD)	{
			String randomName = nameGenerator();
			int[] randomCoordinates = this.getRandomAvailableUnitCoordinates();
			int randomWeight = new Random().nextInt(Unit.getMaxInitialBaseStat()-Unit.getMinInitialBaseStat()+1) + Unit.getMinInitialBaseStat();
			int randomStrength = new Random().nextInt(Unit.getMaxInitialBaseStat()-Unit.getMinInitialBaseStat()+1) + Unit.getMinInitialBaseStat();
			int randomAgility = new Random().nextInt(Unit.getMaxInitialBaseStat()-Unit.getMinInitialBaseStat()+1) + Unit.getMinInitialBaseStat();
			int randomToughness = new Random().nextInt(Unit.getMaxInitialBaseStat()-Unit.getMinInitialBaseStat()+1) + Unit.getMinInitialBaseStat();
			
			Unit newRandomUnit = null;
			if (this.hasRoomForFaction()) {
				newRandomUnit = new Unit(this, randomName, randomCoordinates, randomWeight, randomStrength, randomAgility, randomToughness);
			}
			else {
				Faction faction = this.getAvailableFactionWithLeastMembers();
				newRandomUnit = new Unit(this, faction, randomName, randomCoordinates, randomWeight, randomStrength, randomAgility, randomToughness);
			}
			if (enableDefaultBehavior)
				newRandomUnit.startDefaultBehavior();
			
	//		System.out.println(newRandomUnit.toString());
			return newRandomUnit;
		}
		else{
			return null;
		}
	}
	
	/**
	 * Generates a random valid unit name of length 5.
	 * @return	A random valid unit name of length 5.
	 *				| result == nameGenerator(5)
	 */
	private static String nameGenerator(){
		int defaultLength = 5;
		return nameGenerator(defaultLength);
	}
	
	/**
	 * Returns a random legal unit name of the given size.
	 * @param length
	 * @return A random capital letter followed by a random string of allowed name characters of the given length minus 1.
	 *				| result == getRandomCapitalLetter() + getRandomStringOfAllowedNameCharacters(length-1)
	 */
	private static String nameGenerator(int length){
		String randomCapitalLetter = getRandomCapitalLetter();
		String randomRestOfTheName = getRandomStringOfAllowedNameCharacters(length-1);
		return randomCapitalLetter + randomRestOfTheName;
	}
	
	/**
	 * Returns a string with legal name characters of the given length.
	 * @param	length
	 *				The length of the string that has to be generated.
	 * @return	A string of the given length, consisting of allowed name characters .
	 * @throws	IllegalStateException
	 *				The generated string is not the given length.
	 */
	private static String getRandomStringOfAllowedNameCharacters(int length) throws IllegalStateException {
		String string = "";
		while (string.length() < length){
			int choice = new Random().nextInt(ALLOWED_NAME_CHARACTERS.length());
			string += Character.toString(ALLOWED_NAME_CHARACTERS.charAt(choice));
		}
		
		if (string.length() != length)
			throw new IllegalStateException("The string that was generated is not the given length.");
		
		return string;
	}

	/**
	 * A variable storing all the allowed name characters.
	 */
	private static String ALLOWED_NAME_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKMNOPQRSTUVWXYZ\"\' ";
	
	/**
	 * Returns a random capital letter from the roman alphabet.
	 * @return A random character that is in ALL_CAPITAL_LETTERS..
	 */
	private static String getRandomCapitalLetter() {
		int choice = new Random().nextInt(ALL_CAPITAL_LETTERS.length());
		return Character.toString(ALL_CAPITAL_LETTERS.charAt(choice));
	}
	
	/**
	 * A variable containing all capital letters of the roman alphabet.
	 */
	private static String ALL_CAPITAL_LETTERS = "ABCDEFGHIJKMNOPQRSTUVWXYZ";
	
	/**
	 * Generates random coordinates that valid initial coordinates for a unit.
	 * 
	 * @return	Random cube coordinates that are passable terrain and lie directly above a cube that is not passable terrain.
	 */
	public int[] getRandomAvailableUnitCoordinates(){
		int[] randomAvailableCoordinates = this.getRandomCoordinates();
		while (!this.isValidInitialUnitCoordinates(randomAvailableCoordinates)){
			randomAvailableCoordinates = this.getRandomCoordinates();
		}
		return randomAvailableCoordinates.clone();
	}

	/**
	 * Returns random coordinates within the worlds bounderies.
	 * 
	 * @return Random coordinates within the worlds bounderies.
	 */
	int[] getRandomCoordinates() {
		int randomXCoordinate = CUBE_COORDINATE_MIN-1;
		while (!this.isValidXCoordinate(randomXCoordinate))
			randomXCoordinate = new Random().nextInt( this.getMaximumXValue());
		
		int randomYCoordinate = CUBE_COORDINATE_MIN-1;
		while (!this.isValidYCoordinate(randomYCoordinate))
			randomYCoordinate = new Random().nextInt( this.getMaximumYValue());
		
		int randomZCoordinate = CUBE_COORDINATE_MIN-1;
		while (!this.isValidZCoordinate(randomZCoordinate))
			randomZCoordinate = new Random().nextInt( this.getMaximumZValue());
		
		int[] randomCoordinates = {randomXCoordinate, randomYCoordinate, randomZCoordinate};
		return randomCoordinates ;
	}
	
	/**
	 * Check whether the given coordinates are valid initial unit coordinates.
	 *  
	 * @param	coordinates
	 *         			The coordinates to check.
	 * @return	True if and only if the given coordinates are passable and the cube right underneath is not passable or the lowest possable coordinates.
	 *       			| result ==  ( this.isPassable(coordinates) && 
	 *       			|		( (coordinates[2] == CUBE_COORDINATE_MIN) || (!this.isPassable(coordinatesRightUnderneath)) ) )
	*/
	public boolean isValidInitialUnitCoordinates(int[] coordinates){
		int[] coordinatesRightUnderneath = {coordinates[0], coordinates[1], coordinates[2]-1};
		return ( this.isPassable(coordinates) && ((coordinates[2] == CUBE_COORDINATE_MIN) || (!this.isPassable(coordinatesRightUnderneath))) 
				&& this.canHaveAsCoordinates(coordinates));
	}
	
	/**
	 * Returns whether the cube in the given coordinates is passable.
	 * 
	 * @param	coordinates	
	 *            		The coordinates to be checked.
	 * @return	Returns true if and only if the cube in the given coordinates is passable. A cube is passable when the terrain is AIR or WORKSHOP.
	 * 					|result == cubes[coordinates[0]][coordinates[1]][coordinates[2]].isPassable();
	 */
	private boolean isPassable(int[] coordinates){
		return cubes[coordinates[0]][coordinates[1]][coordinates[2]].isPassable();
	}
	
	/**
	 * Check whether the given x-coordinate is a valid x-coordinate.
	 *  
	 * @param	xCoordinate
	 *         			The x-coordinate to check.
	 * @return	True if and only if the given value is higher than or equal to the minimal coordinate value
	 * 					and less than the maximal value.
	 *       			| result == ((xCoordinate >= this.CUBE_COORDINATE_MIN) && (xCoordinate < this.getMaximumXValue()))
	*/
	public boolean isValidXCoordinate(int xCoordinate){
		return (xCoordinate < this.getMaximumXValue()) &&  xCoordinate >= World.CUBE_COORDINATE_MIN;
	}
	
	/**
	 * Check whether the given y-coordinate is a valid ycoordinate.
	 *  
	 * @param	yCoordinate
	 *         			The y-coordinate to check.
	 * @return	True if and only if the given value is higher than or equal to the minimal coordinate value
	 * 					and less than the maximal value.
	 *       			| result == ((yCoordinate >= this.CUBE_COORDINATE_MIN) && (yCoordinate < this.getMaximumYValue()))
	*/
	public boolean isValidYCoordinate(int yCoordinate){
		return yCoordinate < this.getMaximumYValue() &&  yCoordinate >= World.CUBE_COORDINATE_MIN;
	}
	
	/**
	 * Check whether the given z-coordinate is a valid z-coordinate.
	 *  
	 * @param	zCoordinate
	 *         			The z-coordinate to check.
	 * @return	True if and only if the given value is higher than or equal to the minimal coordinate value
	 * 					and less than the maximal value.
	 *       			| result == ((zCoordinate >= this.CUBE_COORDINATE_MIN) && (zCoordinate < this.getMaximumZValue()))
	*/
	public boolean isValidZCoordinate(int zCoordinate){
		return zCoordinate < this.getMaximumZValue() &&  zCoordinate >= World.CUBE_COORDINATE_MIN;
	}
	
	
	// ===============================================================================================
	// Methods for advancing the time for all the entities in this world.
	// ===============================================================================================
	
	/**
	 * A variable storing the cube coordinates of all the collapsing cubes.
	 */
	private List<int[]> collapsingCubes = new ArrayList<int[]>();
	
	/**
	 * Advances the time for the world and all game objects inside it.
	 * 
	 * @param 	deltaT
	 * 			The given game time to advance.
	 */
	public void advanceTime(double deltaT) throws NullPointerException, IllegalStateException, IllegalArgumentException{
		if  (deltaT < 0)
			throw new IllegalArgumentException("This deltaT is invalid, because it is negative.");
		if  (deltaT > 0.2)
			throw new IllegalArgumentException("This deltaT is invalid, because it is to big.");
		if (this.isTerminated()){
			throw new IllegalStateException("This world is terminated.");
		}
		
		if (this.collapsingCubes.size() != 0){
			this.caveInCollapsingCubes();
		}
		
		for(Unit unit: this.getUnits()){
			try{unit.advanceTime(deltaT);}
			catch(Exception e){}
		}
		
		for(Log log: this.getLogs()){
			log.advanceTime(deltaT);
		}
		
		for(Boulder boulder: this.getBoulders()){
			boulder.advanceTime(deltaT);
		}
	}
}
