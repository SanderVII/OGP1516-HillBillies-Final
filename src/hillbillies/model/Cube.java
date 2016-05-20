package hillbillies.model;

import java.util.Arrays;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.positions.Position;

/**
 * A class of cubes involving a world, a position and a terrain type.
 * 
 * @invar	The terrain type of each cube must be a valid terrain type for any cube.
 *				| isValidTerrainType(this.getTerrainType())
 * @invar	The coordinates of the position of each cube must be valid coordinates for any cube.
 *				| isValidCubeCoordinates(this.getPosition().getCubeCoordinates)
 *
 * @author	Sander Mergan, Thomas Vranken
 * @version	3.0
 */	
public class Cube {
	
	/**
	 * Initializes this new cube with the given terrain.
	 * 
	 * @param	terrainIndex
	 *				The terrainIndex representing the terrain for this new cube.
	 * @param world
	 *				The world for this new cube.
	 * 
	 * @post	The terrain of this new cube is equal to the terrain represented by the given terrainIndex.
	 *				| this.getTerrainType() == Terrain.values()[terrainIndex]
	 * @post	The world of this new cube is equal to the given world.
	 *				| this.getWorld() == world
	 */
	public Cube(int terrainIndex, World world, int[] position) throws IllegalArgumentException{
		this.setTerrainType(terrainIndex);
		this.setWorld(world);
		this.position = (new Position(this.getWorld(), Position.getCubeCenter(position)));
	}
	
	/**
	 * Returns a string representation of this cube. 
	 * The representation includes that we are dealing with a cube, its position and its terrainType.
	 */
	@Override
	public String toString() {
		return "Cube\n"
				+ "position: " + Arrays.toString(getPosition().getCoordinates()) +"\n"
				+ "terrain: " + getTerrainType() +"\n"
				+"=====";
	}
	
	/**
	 * Sets the world this cube belongs to to the given world.
	 * 
	 * @param world
	 *				The new world this cube will belong to.
	 *
	 * @post	The world of this cube is equal to the given world.
	 *				| new.getWorld() == world
	 */
	private void setWorld(World world) {
		this.world = world;
	}

	/**
	 * Returns the world this cube is part of.
	 */
	public World getWorld(){
		return this.world;
	}
	
	/**
	 * A variable that stores the world this cube is part of.
	 */
	private World world;

	/**
	 * A symbolic constant denoting the length of a cube.
	 */
	public static final int CUBE_LENGHT = 1;
	
	/**
	 * Returns the terrain type of this cube.
	 */
	@Basic @Raw
	public Terrain getTerrainType() {
		return this.terrainType;
	}
	
	/**
	 * Checks whether the given terrain type index is a valid terrain type index for any cube.
	 *  
	 * @param	terrainIndex
	 *				The index, corresponding to a terrain type, to check.
	 *
	 * @return	The terrain type index must be a valid index for the enum Terrain.
	 *				| result == ( (terrainIndex >= 0) && (terrainIndex < Terrain.values().length) )
	*/
	public static boolean isValidTerrainTypeIndex(int terrainIndex) {
		return ( (terrainIndex >= 0) && (terrainIndex < Terrain.values().length) );
	}
	
	/**
	 * Sets the terrain type of this cube to the terrain with the given terrain index. 
	 * 
	 * @param	terrainIndex
	 *				The index (position in enum) of the new terrain type for this cube.
	 *         
	 * @post	The terrain type of this new cube is equal to the terrain type with the given index.
	 *				| new.getTerrainType() == Terrain.values()[terrainIndex]
	 *       
	 * @throws	IllegalArgumentException
	 *				The given value does not correspond to a terrain type for any cube.
	 *				| ! isValidTerrainType(getTerrainType())
	 */
	public void setTerrainType(int terrainIndex) throws IllegalArgumentException {
		if (!isValidTerrainTypeIndex(terrainIndex))
			throw new IllegalArgumentException();
		
		this.setTerrainType(Terrain.values()[terrainIndex]);
	}
	
	/**
	 * Sets the terrain type of this cube to the given terrain type.
	 * 
	 * @param	terrainType
	 *				The new terrain type for this cube.
	 * @post	The terrain type of this new cube is equal to the given terrain type.
	 *				| new.getTerrainType() == terrainType
	 */
	@Raw
	public void setTerrainType(Terrain terrainType) {
		this.terrainType = terrainType;
	}
	
	/**
	 * A variable that stores the terrain type of this cube.
	 * The default value is air.
	 */
	private Terrain terrainType = Terrain.AIR;
	
	/**
	 * Returns whether this cube is passable or not.
	 * 
	 * @return	True if the terrain is AIR or WORKSHOP.
	 */
	@Raw
	public boolean isPassable() {
		if ((this.getTerrainType() == Terrain.AIR) || (this.getTerrainType() == Terrain.WORKSHOP))
			return true;
		else
			return false;
	}
		
	/**
	 * Returns the position of this cube.
	 */
	@Basic @Raw
	public Position getPosition() {
		return this.position;
	}
	
	/**
	 * Checks whether the given coordinates are valid coordinates for any cube.
	 *  
	 * @param	coordinates
	 *				The position to check.
	 *
	 * @return	True if all coordinates are greater than the minimal coordinate value.
	*/
	public boolean isValidCubeCoordinates(int[] coordinates) {
		if (coordinates.length == 3){
				if (	(World.CUBE_COORDINATE_MIN <= coordinates[0]) && 
						(World.CUBE_COORDINATE_MIN <= coordinates[1]) && 
						(World.CUBE_COORDINATE_MIN <= coordinates[2])  )
				return true;
		}
		return false;
	}
	
	/**
	 * A variable that stores the position of this cube.
	 */
	private final Position position;
}
