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
 * A class of worlds involving units, factions, logs, boulders.
 * 	
 * @invar	Each world must have proper factions.
 *				| hasProperFactions()
 * @invar	Each world must have proper entities.
 *				| hasProperEntities()
 * @invar	The maximum x-value of each world must be a valid maximum x-value for any world.
 *				| isValidMaximumXValue(getMaximumXValue())
 * @invar	The maximum y-value of each world must be a valid maximum y-value for any world.
 *				| isValidMaximumYValue(getMaximumYValue())
 * @invar	The maximum z-value of each world must be a valid maximum z-value for any world.
 *				| isValidMaximumZValue(getMaximumZValue())
 * 
 * @author Sander Mergan, Thomas Vranken
 * @version 3.0
 */
public class World {

	/**
	 * Initializes this new world as a non-terminated world with 
	 * no units yet and sets the terrains of this world to the given terrain.
	 * 
	 * @param	terrain
	 *				The terrain for this new world.
	 * @param	modelListener
	 *				The terrainChangeListener for this new world
	 *
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
	public World(int[][][] terrain, TerrainChangeListener modellistener) throws IllegalArgumentException, NullPointerException {
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
	 * Returns a textual representation of this world.
	 */
	@Override
	public String toString() {
		String string = "<<< World >>> ";
		return string;
	}
	
	/**
	 * Sets the terrainChangeListener of this world to the given terrainCangeListener.
	 * 
	 * @param	terrainChangeListener
	 *				The new terrainChangeListener of this world.
	 *
	 *	@post	The new terrainChangeListener is equal to the given terrainChangeListener.
	 */
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
	 *	A variable that stores the terrainChangeListener of this world.
	 */
	private TerrainChangeListener terrainChangeListener;
	
	/**
	 * A variable that stores an instance of the connected-to-border interface.
	 */
	private final ConnectedToBorder connectedToBorder;
	
	
	// ==================================================================================
	// Destructor for worlds.
	// ==================================================================================
	
	/**
	 * Terminates this world.
	 *
	 * @post	This world  is terminated.
	 *				| new.isTerminated() == true
	 *
	 * @effect	All the entities in this world are terminated.
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
	  * Returns whether or not this world is terminated.
	  */
	 @Basic @Raw
	 public boolean isTerminated() {
		 return this.isTerminated;
	 }
	 
	 /**
	  * A variable that stores whether this world is terminated.
	  */
	 private boolean isTerminated = false;
	 
	
	// ==================================================================================
	// Methods concerning the factions of this world.
	// ==================================================================================
	
	/**
	 * Checks whether this world has the given faction as one of its factions.
	 * 
	 * @param	faction
	 *				The faction to check.
	 *
	 * @return True if this world's list of factions contains the given faction.
	 *				| this.factions.contains(faction)
	 */
	@Basic
	@Raw
	public boolean hasAsFaction(@Raw Faction faction) {
		return this.factions.contains(faction);
	}

	/**
	 * Checks whether this world can have the given faction as one of its factions.
	 * 
	 * @param	faction
	 *				The faction to check.
	 *
	 * @return True if and only if the given faction is effective
	 *				and that faction is a valid faction for a world.
	 *				| result == (faction != null) && faction.canHaveAsWorld(this)
	 */
	@Raw
	public boolean canHaveAsFaction(Faction faction) {
		return (faction != null) && (faction.canHaveAsWorld(this));
	}

	/**
	 * Checks whether this world has proper factions attached to it.
	 * 
	 * @return True if and only if this world can have each of the
	 *				factions attached to it as one of its factions,
	 *				and if each of these factions references this world as
	 *				the world to which they are attached.
	 *				| for each faction in Faction:
	 *				|   if (hasAsFaction(faction))
	 *				|     then canHaveAsFaction(faction) &&
	 *				|          (faction.getWorld() == this)
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
	 * Returns the number of active (non-empty) factions associated with this world.
	 * 
	 * @return	The total number of factions in this world which have at least one unit associated with them.
	 */
	public int getNbActiveFactions() {
		int count = 0;
		for (Faction faction: factions)
			if (faction.getNbUnits() > 0)
				count++;
		return count;
	}

	/**
	 * Adds the given faction to the set of factions of this world.
	 * 
	 * @param	faction
	 *				The faction to be added.
	 *
	 * @post	This world has the given faction as one of its factions.
	 *				| new.hasAsFaction(faction)
	 *
	 * @throws IllegalArgumentException
	 *				The given faction is not active or the given faction doesn't have this world as its world.
	 *				| ((faction == null) || (faction.getWorld() != this))
	 */
	void addFaction(@Raw Faction faction) throws IllegalArgumentException{
		if ((faction == null) || (faction.getWorld() != this))
			throw new IllegalArgumentException();
		factions.add(faction);
	}

	/**
	 * Removes the given faction from the set of factions of this world.
	 * 
	 * @param	faction
	 *				The faction to be removed.
	 *         
	 * @post	This world no longer has the given faction as one of its factions.
	 *				| ! new.hasAsFaction(faction)
	 *
	 * @throws	IllegalArgumentException
	 *				The given faction still references this world as its world or 
	 *				this world doesn't have the given faction as one of tis factions.
	 *				| ( (faction.getWorld() != null) || ( ! this.hasAsFaction(faction)) )
	 */
	@Raw
	void removeFaction(Faction faction) throws IllegalArgumentException{
		if ( (faction.getWorld() != null) || ( ! this.hasAsFaction(faction)) )
			throw new IllegalArgumentException();
		
		factions.remove(faction);
	}

	/**
	 * A variable that stores all the factions of this world.
	 * 
	 * @invar	The referenced set is effective.
	 *				| factions != null
	 * @invar	Each faction registered in the referenced list is
	 *				effective and not yet terminated.
	 *				| for each faction in factions:
	 *				|   ( (faction != null) &&
	 *				|     (! faction.isTerminated()) )
	 */
	private final Set<Faction> factions = new HashSet<Faction>();
	
	/**
	 * Returns a set collecting all the factions of this world. 
	 * 
	 * @note	The resulting set does not contain a null reference.
	 * 
	 * @return	Each faction in the resulting set is attached to this world and vice versa.
	 */
	// NOTE: this is the formal way (same as in textbook) to return all objects of the set.
	public Set<Faction> getFactions() {
		return new HashSet<Faction>(this.factions);
	}
	
	/**
	 * Checks whether this world is at its maximum active faction capacity.
	 * 
	 * @return	True if the amount of active factions is less than the maximal amount.
	 *				| (this.getNbActiveFactions() < MAX_FACTIONS);
	 */
	public boolean hasRoomForFaction() {
		return (this.getNbActiveFactions() < MAX_FACTIONS);
	}
	
	/**
	 * Returns the active faction of this world with the least members. 
	 * The returned faction must have less members than the maximal amount of members.
	 * 
	 * @return	The faction with the least amount of units and room for new units.
	 *				If no such faction exists, return null.
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
	 * A symbolic constant that stores the probability of
	 * creating a boulder or rock after a cube collapses.
	 */
	public static final double DROP_CHANCE = 1.0;
	
	/**
	 * A symbolic constant that stores the maximal amount of units in this world.
	 */
	public static final int MAX_UNITS_WORLD = 100;
	
	/**
	 * A symbolic constant that stores the maximal amount of factions in this world.
	 */
	public static final int MAX_FACTIONS = 5;
	
	/**
	 * Returns the maximal x-value of this world.
	 */
	@Basic @Raw @Immutable
	public int getMaximumXValue() {
		return this.maximumXValue;
	}
	
	/**
	 * Checks whether the given maximal x-value is a valid maximal x-value for any world.
	 *  
	 * @param	maximumXValue
	 *				The maximum x-value to check.
	 *
	 * @return	The dimension must be greater than 0.
	 *				| result == (maximumXValue > 0)
	*/
	@Raw
	public static boolean isValidMaximumXValue(int maximumXValue) {
		return (maximumXValue > CUBE_COORDINATE_MIN);
	}
	
	/**
	 * A variable that stores the amount of cubes in the x-direction.
	 */
	private final int maximumXValue;
	
	/**
	 * Returns the maximal y-value of this world.
	 */
	@Basic @Raw @Immutable
	public int getMaximumYValue() {
		return this.maximumYValue;
	}
	
	/**
	 * Checks whether the given maximal y-value is a valid maximal y-value for any world.
	 *  
	 * @param	maximumYValue
	 *				The maximal y-value to check.
	 *
	 * @return	The dimension must be greater than 0.
	 *				| result == (maximumYValue > 0)
	*/
	@Raw
	public static boolean isValidMaximumYValue(int maximumYValue) {
		return (maximumYValue > CUBE_COORDINATE_MIN);
	}
	
	/**
	 * A variable that stores the amount of cubes in the y-direction.
	 */
	private final int maximumYValue;
	
	/**
	 * Returns the maximal z-value of this world.
	 */
	@Basic @Raw @Immutable
	public int getMaximumZValue() {
		return this.maximumZValue;
	}
	
	/**
	 * Checks whether the given maximal z-value is a valid maximal z-value for any world.
	 *  
	 * @param	maximumZValue
	 *				The maximal z-value to check.
	 *         
	 * @return	The dimension must be greater than 0.
	 *				| result == (maximumZValue > 0)
	*/
	@Raw
	public static boolean isValidMaximumZValue(int maximumZValue) {
		return (maximumZValue > CUBE_COORDINATE_MIN);
	}
	
	/**
	 * A variable that stores the amount of cubes in the z-direction.
	 */
	private final int maximumZValue;
	
	
	//================================================================================
	// Methods concerning the cubes this world is built of.
	//================================================================================
	
	/**
	 * A symbolic constant that stores the minimal value of a cube coordinate.
	 * Cube coordinates can have this value as one of their coordinates.
	 */
	public static final int CUBE_COORDINATE_MIN = 0;
	
	/**
	 * Returns the cube object at the given coordinates.
	 * 
	 * @param	x
	 *				The x-coordinate
	 * @param	y
	 *				The y-coordinate
	 * @param	z
	 *				The z-coordinate
	 *
	 * @return	The cube in the array of cubes with the given coordinates.
	 *				result == cubes[x][y][z]
	 *
	 * @throws	IllegalArgumentException
	 *				The given coordinates are invalid.
	 *@throws	IllegalStateException
	 *				This world is terminated..
	 */
	public Cube getCube(int x, int y, int z) throws IllegalArgumentException, IllegalStateException {
		if (this.isTerminated())
			throw new IllegalStateException();
		if (! this.canHaveAsCoordinates(x, y, z))
			throw new IllegalArgumentException();
		return cubes[x][y][z];
	}
	
	/**
	 * Returns the cube object at the given coordinates.
	 * 
	 * @param	coordinates
	 *				The coordinates to retrieve a cube from.
	 * 
	 * @return	The cube in the array of cubes with the given coordinates.
	 *				result == getCube(coordinates[0],coordinates[1],coordinates[2])
	 */
	public Cube getCube(int[] coordinates) {
		return this.getCube(coordinates[0],coordinates[1],coordinates[2]);
	}
	
	/**
	 * An array that stores all the cubes this world consists of.
	 */
	private Cube[][][] cubes = new Cube[this.getMaximumXValue()][this.getMaximumYValue()][this.getMaximumZValue()];
	
	/**
	 * Checks whether the given coordinates are valid in this world.
	 * 
	 * @param	x
	 *				The x-coordinate of the coordinates.
	 * @param 	y
	 *				The y-coordinate of the coordinates.
	 * @param 	z
	 *				The z-coordinate of the coordinates.
	 *
	 * @return	True if all coordinates are within the boundaries of this world.
	 *				| this.canHaveAsCoordinates(new int[]{x, y, z})
	 */
	public boolean canHaveAsCoordinates(int x, int y, int z) {
		return this.canHaveAsCoordinates(new int[]{x, y, z});
	}
	
	/**
	 * Checks whether the given coordinates are valid in this world.
	 * 
	 * @param	coordinates
	 *				The given coordinates.
	 *
	 * @return	True if the size equals 3, and if all 
	 *				coordinates are within the boundaries of this world.
	 */
	public boolean canHaveAsCoordinates(int[] coordinates) {
		return this.canHaveAsCoordinates(Position.getCubeCenter(coordinates));
	}
	
	/**
	 * Checks whether the given coordinates are valid in this world.
	 * 
	 * @param	coordinates
	 *				The given coordinates.
	 *
	 * @return	True if the size equals 3, and if all coordinates are within the boundaries of this world.
	 */
	public boolean canHaveAsCoordinates(double[] coordinates) {
		Position temp = new Position(this,new int[]{0,0,0});
		return temp.canHaveAsCoordinates(coordinates);
	}
	
	/**
	 * Returns a set of coordinates which are neighbours to the cube with the given coordinates.
	 * 
	 * @param	x
	 *				The x-coordinate of the cube.
	 * @param	y
	 *				The y-coordinate of the cube.
	 * @param	z
	 *				The z-coordinate of the cube.
	 *
	 * @return	A set of cube coordinates which are neighbours of the given cube
	 *				and have valid coordinates for this world.
	 *				| (his.getNeighbours(new int[]{x,y,z})
	 */
	public Set<int[]> getNeighbours(int x, int y, int z) {
		return (this.getNeighbours(new int[]{x,y,z}));
	}
	
	/**
	 * Returns a set of coordinates which are neighbours to the cube with the given coordinates.
	 * 
	 * @param	coordinates
	 *				The given coordinates
	 * 
	 * @return	A set of cube coordinates which are neighbours of the given cube
	 *				and have valid coordinates for this world.
	 *				| this.getValidCubeCoordinatesInRange(coordinates, 1)
	 */
	public Set<int[]> getNeighbours(int[] coordinates) throws IllegalArgumentException {
		return (this.getValidCubeCoordinatesInRange(coordinates, 1));
	}
	
	/**
	 * Returns the cube in this world right below the given cube coordinates. 
	 * 
	 * @param	cubeCoordinates
	 *				The cube coordinates to retrieve the cubebelow at.
	 * 
	 * @return	The cube with coordinates (x ,y, z-1) in this world.
	 * 
	 * @throws	IllegalArgumentException
	 *				The given coordinates are invalid.
	 */
	Cube getCubeBelow(int[] cubeCoordinates) throws IllegalArgumentException {
		return this.getCube(cubeCoordinates[0], cubeCoordinates[1], cubeCoordinates[2]-1);
	}
	
	/**
	 * Returns the terrain type of the cube at the given coordinates.
	 * 
	 * @param	x
	 *				The x-coordinate of the cube.
	 * @param	y
	 *				The y-coordinate of the cube.
	 * @param 	z
	 *				The z-coordinate of the cube.
	 *
	 * @return	the terrain value of the cube.
	 *				| result == cubes.get(x).get(y).get(z).getTerrainType();
	 * 
	 */
	public Terrain getTerrain(int x, int y, int z) {
		return cubes[x][y][z].getTerrainType();
	}
	
	/**
	 * Returns a list of all units standing in the given cube.
	 * 
	 * @param	x
	 *			The x-coordinate of the cube.
	 * @param	y
	 *			The y-coordinate of the cube.
	 * @param	z
	 *			The z-coordinate of the cube.
	 * 
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
	 * Returns a set of cubes which are the directly adjacent cubes
	 * (as defined in the assignment) of the given cube.
	 * 
	 * @param	x
	 *				The x-coordinate of the cube.
	 * @param	y
	 *				The y-coordinate of the cube.
	 * @param	z
	 *				The z-coordinate of the cube.
	 *
	 * @return	A set of cubes which are directly adjacent.
	 * 
	 * @throws	IllegalArgumentException
	 *				The given coordinates are illegal for this world.
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
	 * Returns the list of coordinates that are no longer connected to a border of the
	 * world due to changing the cube at the given coordinates to passable.
	 * 
	 * @param	x
	 *				The x-coordinate of the cube.
	 * @param	y
	 *				The y-coordinate of the cube.
	 * @param	z
	 *				The z-coordinate of the cube.
	 *
	 * @return A list of coordinates where there is solid terrain that is no longer connected to the border.
	 *				| this.connectedToBorder.changeSolidToPassable(x, y, z)
	 * 
	 * @throws IllegalArgumentException
	 *				The given coordinates are invalid for this world.
	 */
	private List<int[]> changeSolidToPassable(int x, int y, int z) throws IllegalArgumentException {
		if (! this.canHaveAsCoordinates(x, y, z))
			throw new IllegalArgumentException();
		
		return this.connectedToBorder.changeSolidToPassable(x, y, z);
	}
	
	/**
	 * Returns whether the cube at the given coordinates is a solid cube that is
	 * connected to a border of the world through other directly adjacent solid cubes.
	 * 
	 * @param	x
	 *				The x-coordinate of the cube to test
	 * @param	y
	 *				The y-coordinate of the cube to test
	 * @param	z
	 *				The z-coordinate of the cube to test
	 *
	 * @return True if the cube is solid and connected to the border of this world, false otherwise.
	 *				| return this.connectedToBorder.isSolidConnectedToBorder(x, y, z)
	 */
	public boolean isSolidConnectedToBorder(int x, int y, int z) throws IllegalArgumentException {
		if (! this.canHaveAsCoordinates(x, y, z))
			throw new IllegalArgumentException();
		
		return this.connectedToBorder.isSolidConnectedToBorder(x, y, z);
	}
	
	/**
	 * Collapses the cube at the given cube coordinates.
	 * 
	 * @param	cubeCoordinates
	 *				The cube coordinates to collaps a cube at.
	 *
	 * @effect	Drops an item at the given coordinates. Items are dropped with a chance of World.DROP_CHANCE
	 *				| this.dropItem(cubeCoordinates);
	 * @effect	The terrain type of the cube at the given coordinates is set to AIR.
	 *				| this.getCube(x, y, z).setTerrainType(Terrain.AIR)
	 */
	protected void collapsCube(int[] cubeCoordinates) throws IllegalStateException,IllegalArgumentException {
		int x =cubeCoordinates[0];
		int y =cubeCoordinates[1];
		int z =cubeCoordinates[2];
		
		
		Terrain terrain = this.getCube(x, y, z).getTerrainType();
		this.getCube(x, y, z).setTerrainType(Terrain.AIR);
		this.dropItem(cubeCoordinates, terrain);
		
		collapsingCubes.addAll(this.changeSolidToPassable(x, y, z));
		this.terrainChangeListener.notifyTerrainChanged(x, y, z);
	}
	
	/**
	 * Drops an item in accordance with the given terrain with a chance of World.DROP_CHANCE at the given coordinates.
	 * 
	 * @param cubeCoordinates
	 *				The coordinates to drop an item at.
	 *
	 * @effect Adds an item to this world at the given coordinates with a chance of World.DROP_CHANCE. 
	 *				If the given terrain is WOOD, then a new log is added.
	 *				| if (terrain == Terrain.WOOD)
	 *				|	then new Log(this, cubeCoordinates)
	 *				If the given terrain is ROCK, then a new Boulder is added.
	 *				| if (terrain == Terrain.ROCK)
	 *				|	then new Boulder(this, cubeCoordinates)
	 *
	 */
	private void dropItem(int[] cubeCoordinates, Terrain terrain) throws NullPointerException,IllegalStateException{
		double dropChance = World.DROP_CHANCE;
		double succesRate = Math.random();
		if (succesRate <= dropChance){
			if (terrain == Terrain.WOOD)
				new Log(this, cubeCoordinates);
			if (terrain == Terrain.ROCK)
				new Boulder(this, cubeCoordinates);
		}
	}
	
	/**
	 * Makes the cubes that are listed for caving in actually cave in.
	 * 
	 * @effect The cubes in the list collapsingCubes collaps.
	 *				| for each collapsingCube in this.collapsingCubes
	 *				|	this.collapsCube(collapsingCube)
	 */
	private void caveInCollapsingCubes() throws NullPointerException, IllegalStateException{
		for (int[] collapsingCube: this.collapsingCubes){
			collapsCube(collapsingCube);
		}
		collapsingCubes.clear();
	}
	
	/**
	 * Returns a set of all integer coordinates which are at most r cubes away from
	 * the given coordinates. Only valid coordinates in this world are included.
	 * The given coordinates are also included if it is valid.
	 * 
	 * @param	coordinates
	 *				The given start coordinates.
	 * @param	range
	 *				The given range.
	 * 
	 * @return	A set of coordinates which are valid. Their integer coordinates 
	 *				are at most r cubes away from the given coordinates.
	 * 
	 * @throws IllegalArgumentException
	 *				The given coordinates are not valid coordinates for this world.
	 */
	public Set<int[]> getValidCubeCoordinatesInRange(int[] coordinates, int range) throws IllegalArgumentException{
		if ( ! this.canHaveAsCoordinates(coordinates))
			throw new IllegalArgumentException();
		
		Set<int[]> result = new HashSet<>();
		Set<int[]> allCubes = Position.getCubeCoordinatesInRange(coordinates, range);
		for (int[] cubeCoordinates: allCubes)
			if (this.canHaveAsCoordinates(cubeCoordinates) && ( ! Position.equals(coordinates, cubeCoordinates)))
				result.add(cubeCoordinates);
		return result ;
	}
	
	/**
	 * Returns  integer coordinates which are at most x cubes away from the given coordinates,
	 * where x is the given range, and that are passable and valid for this world.
	 * If no such coordinates can be found, return the cube coordinates of the original coordinates.
	 * 
	 * @param	coordinates
	 *				The start coordinates.
	 * @param	range
	 *				How far the new coordinates may be from the given coordinates (in 3 dimensions).
	 *
	 * @return	Passable, valid coordinates which are at most 'range' cubes away from the given coordinates.
	 *				When no such coordinates are found, return the original coordinates.
	 * @throws	IllegalArgumentException
	 *				There is no valid coordinates in range.
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
		// This should never happen.
		return Position.getCubeCoordinates(coordinates);
	}
	
	/**
	 * Returns coordinates that are at most x cubes away from the given coordinates,
	 * where x is the given range, and that are solid and valid for this world.
	 * 
	 * @param	coordinates
	 *				The start coordinates.
	 * @param	range
	 *				How far the new coordinates may be from the given coordinates (in 3 dimensions).
	 *
	 * @return	Passable, valid coordinates that are at most 'range' cubes away from the given coordinates.
	 *				When no such coordinates are found, return the original coordinates.	
	 * 
	 * @throws	IllegalArgumentException
	 *				There is no valid coordinates in range.
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
	 * Returns integer coordinates which are at most x cubes away from the given coordinates,
	 * where x is the given range, and which are valid coordinates for a unit of this world.
	 * If no such coordinates can be found, return the cube coordinates of the original coordinates.
	 * 
	 * @param	coordinates
	 *				The start coordinates.
	 * @param	range
	 *				How far the new coordinates may be from the given coordinates (in 3 dimensions).
	 * 
	 * @return	Valid unit coordinates which is at most 'range' cubes away from the given coordinates.
	 *				When no such coordinates are found, return the original coordinates.	
	 * 
	 * @throws	IllegalArgumentException
	 *				There are no valid coordinates in range.
	 */
	public int[] getRandomUnitCoordinatesInRange(double[] coordinates, int range) throws IllegalArgumentException {
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
	 * Checks whether this world has the given entity as one of its entities.
	 * 
	 * @param	entity
	 *				The entity to check.
	 *
	 * @return True if this world's list of entities contains the given entity.
	 *				| this.entities.contains(entity)
	 */
	@Basic
	@Raw
	public boolean hasAsEntity(@Raw Entity entity) {
		return entities.contains(entity);
	}

	/**
	 * Checks whether this world can have the given entity as one of its entities.
	 * 
	 * @param	entity
	 *				The entity to check.
	 *
	 * @return	True if and only if the given entity is effective
	 *				and that entity is a valid entity for a world.
	 */
	@Raw
	public boolean canHaveAsEntity(Entity entity) {
		return (entity != null) && (entity.canHaveAsWorld(this));
	}

	/**
	 * Checks whether this world has proper entities attached to it.
	 * 
	 * @return True if and only if this world can have each of the
	 *				entities attached to it as one of its entities,
	 *				and if each of these entities references this world as
	 *				the world to which they are attached.
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
	 * Returns the number of entities associated with this world.
	 *
	 * @return	The total number of entities collected in this world.
	 *				| entities.size();
	 */
	public int getNbEntities() {
		return entities.size();
	}

	/**
	 * Adds the given entity to the set of entities of this world.
	 * 
	 * @param	entity
	 *				The entity to be added.
	 *
	 * @post	This world has the given entity as one of its entities.
	 *				| new.hasAsEntity(entity)
	 *
	 * @throws	IllegalArgumentException
	 *				The given entity is not an active entity or
	 *				the world the given entity references is not this world.
	 *				| ((entity == null) || (entity.getWorld() != this))
	 */
	public void addEntity(@Raw Entity entity) throws IllegalArgumentException {
		if ((entity == null) || (entity.getWorld() != this))
			throw new IllegalArgumentException();
		
		entities.add(entity);
	}

	/**
	 * Removes the given entity from the set of entities of this world.
	 * 
	 * @param	entity
	 *				The entity to be removed.
	 *
	 * @post	This world no longer has the given entity as one of its entities.
	 *				| ! new.hasAsEntity(entity)
	 *
	 * @throws IllegalArgumentException
	 *				This world doesn't have the given entity as one of its entities
	 *				| ( ! this.hasAsEntity(entity))
	 */
	@Raw
	public void removeEntity(Entity entity) throws IllegalArgumentException {
		if ( ! this.hasAsEntity(entity) && ( ! this.isTerminated()))
			throw new IllegalArgumentException();
		
		entities.remove(entity);
		entity.setWorld(null);
	}

	/**
	 * A variable that stores a set collecting all the entities of this world.
	 * 
	 * @invar	The referenced set is effective.
	 *				| entities != null
	 * @invar	Each entity registered in the referenced list is effective and not yet terminated.
	 *				| for each entity in entities:
	 *				|   ( (entity != null) && (! entity.isTerminated()) )
	 */
	private final Set<Entity> entities = new HashSet<Entity>();
	
	/**
	 * Returns a set collecting all the entities of this world. 
	 * 
	 * @note	The resulting set does not contain a null reference.
	 * 
	 * @return	Each entity in the resulting set is attached to this world and vice versa.
	 */
	// NOTE: this is the formal way (same as in textbook) to return all objects of the set.
	public Set<Entity> getEntities() {
		return new HashSet<Entity>(this.entities);
	}
	
	/**
	 * Returns an item of this world that has coordinates equal to the given coordinates.
	 * 
	 * @param	coordinates
	 *				The position to find an item at.
	 *
	 * @return	An item which occupies the cube with the given coordinates.
	 *				Return null if no such item exists.
	 */
	public Item getItemAt(int[] coordinates){
		for (Entity entity: this.getEntities()){
			if (entity instanceof Item){
				int[] entityPosition = entity.getPosition().getCubeCoordinates();
				if (Position.equals(coordinates, entityPosition))
					return (Item) entity;
			}
		}
		return null;
	}
	
	/**
	 * Checks whether there are any items at the given position.
	 * 
	 * @param coordinates
	 *				The coordinates to check.
	 *
	 * @return	True if there is an item in this world which cube coordinatesare equal to the given coordinates.	
	 */
	public boolean hasItemAt(int[] coordinates){
		Item item = this.getItemAt(coordinates);
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
	 * @note	The resulting set does not contain a null reference.
	 * 
	 * @return	Each unit in the resulting set is attached to this world and vice versa.
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
	 * Returns the number of units associated with this world.
	 *
	 * @return	The total number of units in this world.
	 *				| this.getUnits().size()
	 */
	public int getNbUnits() {
		return this.getUnits().size();
	}
	
	/**
	 * Returns a unit of this world that has coordinates equal to the given coordinates.
	 * @param	coordinates
	 *				The coordinates to find a unit at.
	 * @return	A unit which occupies the cube with the given coordinates.
	 *				Return null if no such unit exists.
	 */
	public Unit getUnitAt(int[] coordinates){
		for (Unit unit: this.getUnits()){
			int[] unitPosition = unit.getPosition().getCubeCoordinates();
			if (Position.equals(coordinates, unitPosition))
				return unit;
		}
		return null;
	}
	
	/**
	 * Checks whether there are any units at the given coordinates.
	 * 
	 * @param	coordinates
	 *				The coordinates to check.
	 *
	 * @return	True if there is a unit in this world which cube coordinates are equal to the given coordinates.	
	 */
	public boolean hasUnitAt(int[] coordinates){
		Unit unit = this.getUnitAt(coordinates);
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
	 * @note	The resulting set does not contain a null reference.
	 * 
	 * @return	Each log in the resulting set is attached to this world and vice versa.
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
	 * Returns the number of logs associated with this world.
	 *
	 * @return	The total number of logs collected in this world.
	 *				| this.getLogs().size()
	 */
	public int getNbLogs() {
		return this.getLogs().size();
	}
	
	/**
	 * Returns a log of this world that has coordinates equal to the given coordinates.
	 * @param coordinates
	 *				The coordinates to find a log at.
	 * @return	A log which occupies the cube with the given coordinates.
	 *				Return null if no such log exists.
	 */
	public Log getLogAt(int[] coordinates){
		for (Log log: this.getLogs()){
			int[] logPosition = log.getPosition().getCubeCoordinates();
			if (Position.equals(coordinates, logPosition))
				return log;
		}
		return null;
	}
	
	/**
	 * Checks whether there are any logs at the given coordinates.
	 * 
	 * @param coordinates
	 *				The coordinates to check.
	 *
	 * @return	True if there is a log in this world which cube coordinates is equal to the given coordinates.	
	 */
	public boolean hasLogAt(int[] coordinates){
		Log log = this.getLogAt(coordinates);
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
	 * @note	The resulting set does not contain a null reference.
	 * 
	 * @return	Each boulder in the resulting set is attached to this world and vice versa.
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
	 * Returns the number of boulders associated with this world.
	 *
	 * @return  The total number of boulders collected in this world.
	 *				| this.getBoulders().size()
	 */
	public int getNbBoulders() {
		return this.getBoulders().size();
	}
	
	/**
	 * Returns a boulder of this world that has coordinates equal to the given coordinates.
	 * 
	 * @param coordinates
	 *				The coordinates to find a boulder at.
	 * @return	A boulder which occupies the cube with the given coordinates.
	 *				Return null if no such boulder exists.
	 */
	public Boulder getBoulderAt(int[] coordinates){
		for (Boulder boulder: this.getBoulders()){
			int[] boulderPosition = boulder.getPosition().getCubeCoordinates();
			if (Position.equals(coordinates, boulderPosition))
				return boulder;
		}
		return null;
	}
	
	/**
	 * Checks whether their are any boulders at the given coordinates.
	 * @param coordinates
	 *				The coordinates to check.
	 * @return	True if there is a boulder in this world which cube coordinates are equal to the given coordinates.	
	 */
	public boolean hasBoulderAt(int[] coordinates){
		Boulder boulder = this.getBoulderAt(coordinates);
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
	 * If the maximal amount of units for a world is reached, then null is returned.
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
			
			return newRandomUnit;
		}
		else{
			return null;
		}
	}
	
	/**
	 * A variable that stores the length of randomly generated names.
	 */
	private static final int DEFAULT_NAME_LENGTH = 5;
	
	/**
	 * Generates a random valid unit name of length DEFAULT_NAME_LENGTH.
	 * 
	 * @return	A random valid unit name of length DEFAULT_NAME_LENGTH.
	 *				| result == nameGenerator(DEFAULT_NAME_LENGTH)
	 */
	private static String nameGenerator() throws IllegalStateException{
		return nameGenerator(DEFAULT_NAME_LENGTH);
	}
	
	/**
	 * Returns a random legal unit name of the given size.
	 * 
	 * @param length
	 *				The length of the name that has to be generated.
	 *
	 * @return A random capital letter followed by a random string of allowed name characters of the given length minus 1.
	 *				| result == getRandomCapitalLetter() + getRandomStringOfAllowedNameCharacters(length-1)
	 */
	private static String nameGenerator(int length) throws IllegalStateException{
		String randomCapitalLetter = getRandomCapitalLetter();
		String randomRestOfTheName = getRandomStringOfAllowedNameCharacters(length-1);
		return randomCapitalLetter + randomRestOfTheName;
	}
	
	/**
	 * Returns a string with legal name characters of the given length.
	 * 
	 * @param	length
	 *				The length of the string that has to be generated.
	 *
	 * @return	A string of the given length, consisting of allowed name characters .
	 * 
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
	 * A variable that stores all the allowed name characters.
	 */
	private static String ALLOWED_NAME_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKMNOPQRSTUVWXYZ\"\' ";
	
	/**
	 * Returns a random capital letter from the roman alphabet.
	 * 
	 * @return A random character that is in ALL_CAPITAL_LETTERS..
	 */
	private static String getRandomCapitalLetter() {
		int choice = new Random().nextInt(ALL_CAPITAL_LETTERS.length());
		return Character.toString(ALL_CAPITAL_LETTERS.charAt(choice));
	}
	
	/**
	 * A variable that stores all capital letters of the roman alphabet.
	 */
	private static String ALL_CAPITAL_LETTERS = "ABCDEFGHIJKMNOPQRSTUVWXYZ";
	
	/**
	 * Generates random coordinates that are valid initial coordinates for a unit.
	 * 
	 * @return	Random cube coordinates that are passable terrain and lay directly above a cube that is not passable terrain.
	 */
	public int[] getRandomAvailableUnitCoordinates(){
		int[] randomAvailableCoordinates = this.getRandomCoordinates();
		while (!this.isValidInitialUnitCoordinates(randomAvailableCoordinates)){
			randomAvailableCoordinates = this.getRandomCoordinates();
		}
		return randomAvailableCoordinates;
	}

	/**
	 * Returns random coordinates within this worlds bounderies.
	 * 
	 * @return	Random coordinates within this worlds bounderies.
	 */
	int[] getRandomCoordinates() {
		int randomXCoordinate = CUBE_COORDINATE_MIN-1;
		while (!this.canHaveAsXCoordinate(randomXCoordinate))
			randomXCoordinate = new Random().nextInt( this.getMaximumXValue());
		
		int randomYCoordinate = CUBE_COORDINATE_MIN-1;
		while (!this.canHaveAsYCoordinate(randomYCoordinate))
			randomYCoordinate = new Random().nextInt( this.getMaximumYValue());
		
		int randomZCoordinate = CUBE_COORDINATE_MIN-1;
		while (!this.isValidZCoordinate(randomZCoordinate))
			randomZCoordinate = new Random().nextInt( this.getMaximumZValue());
		
		int[] randomCoordinates = {randomXCoordinate, randomYCoordinate, randomZCoordinate};
		return randomCoordinates ;
	}
	
	/**
	 * Checks whether the given coordinates are valid initial unit coordinates.
	 *  
	 * @param	coordinates
	 *				The coordinates to check.
	 *         
	 * @return	True if and only if the given coordinates are passable and the cube right underneath is not passable or the lowest possable coordinates.
	 *				| result ==  ( this.isPassable(coordinates) && 
	 *				|		( (coordinates[2] == CUBE_COORDINATE_MIN) || (!this.isPassable(coordinatesRightUnderneath)) ) )
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
	 *				The coordinates that have to be checked.
	 *
	 * @return	Returns true if and only if the cube in the given coordinates is passable. 
	 * 				A cube is passable when the terrain is AIR or WORKSHOP.
	 *				|result == cubes[coordinates[0]][coordinates[1]][coordinates[2]].isPassable();
	 */
	private boolean isPassable(int[] coordinates){
		return cubes[coordinates[0]][coordinates[1]][coordinates[2]].isPassable();
	}
	
	/**
	 * Checks whether the given x-coordinate is a valid x-coordinate.
	 *  
	 * @param	xCoordinate
	 *				The x-coordinate to check.
	 *
	 * @return	True if and only if the given value is higher than or equal to the minimal coordinate value
	 *				and less than the maximal value.
	 *				| result == ((xCoordinate >= this.CUBE_COORDINATE_MIN) && (xCoordinate < this.getMaximumXValue()))
	*/
	public boolean canHaveAsXCoordinate(int xCoordinate){
		return (xCoordinate < this.getMaximumXValue()) &&  xCoordinate >= World.CUBE_COORDINATE_MIN;
	}
	
	/**
	 * Checks whether the given y-coordinate is a valid ycoordinate.
	 *  
	 * @param	yCoordinate
	 *				The y-coordinate to check.
	 *
	 * @return	True if and only if the given value is higher than or equal to the minimal coordinate value
	 *				and less than the maximal value.
	 *				| result == ((yCoordinate >= this.CUBE_COORDINATE_MIN) && (yCoordinate < this.getMaximumYValue()))
	*/
	public boolean canHaveAsYCoordinate(int yCoordinate){
		return yCoordinate < this.getMaximumYValue() &&  yCoordinate >= World.CUBE_COORDINATE_MIN;
	}
	
	/**
	 * Checks whether the given z-coordinate is a valid z-coordinate.
	 *  
	 * @param	zCoordinate
	 *				The z-coordinate to check.
	 * @return	True if and only if the given value is higher than or equal to the minimal coordinate value
	 *				and less than the maximal value.
	 *				| result == ((zCoordinate >= this.CUBE_COORDINATE_MIN) && (zCoordinate < this.getMaximumZValue()))
	*/
	public boolean isValidZCoordinate(int zCoordinate){
		return zCoordinate < this.getMaximumZValue() &&  zCoordinate >= World.CUBE_COORDINATE_MIN;
	}
	
	
	// ===============================================================================================
	// Methods for advancing the time for all the entities in this world.
	// ===============================================================================================
	
	/**
	 * A variable that stores the cube coordinates of all the collapsing cubes.
	 */
	private List<int[]> collapsingCubes = new ArrayList<int[]>();
	
	/**
	 * Advances the time for the world and all game objects inside it.
	 * 
	 * @param	deltaT
	 *				The amount of time to advance the gametime of this world with.
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
			catch(Exception e){
				throw e;
			}
		}
		
		for(Log log: this.getLogs()){
			log.advanceTime(deltaT);
		}
		
		for(Boulder boulder: this.getBoulders()){
			boulder.advanceTime(deltaT);
		}
	}
}
