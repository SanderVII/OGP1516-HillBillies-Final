package hillbillies.model;

import java.util.Arrays;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.positions.ItemPosition;
import hillbillies.positions.Position;

/**
 * @version	2.0
 */

/**
 * A class of cubes involving...
 * @invar	The terrain type of each cube must be a valid terrain type for any cube.
 *      	| isValidTerrainType(getTerrainType())
 * @invar  	The position of each cube must be a valid position for any cube.
 *       	| isValidPosition(getPosition())
 * @author	Sander Mergan, Thomas Vranken
 */	
public class Cube {
	
	/**
	 * Initialize this new cube with the given terrain.
	 * 
	 * @param	terrainIndex
	 * 					The terrainIndex representing the terrain for this new cube.
	 * @param world
	 *					The world for this new cube.
	 * 
	 * @post	The terrain of this new cube is equal to the terrain represented by the given terrainIndex.
	 *				| this.getTerrainType() == Terrain.values()[terrainIndex]
	 * @post	The world of this new cube is equal to the given world.
	 *				| this.getWorld() == world
	 */
	public Cube(int terrainIndex, World world, int[] position) throws IllegalArgumentException{
		this.setTerrainType(terrainIndex);
		this.setWorld(world);
		this.setPosition(new ItemPosition(this.getWorld(), ItemPosition.getCubeCenter(position)));
	}
	
	/**
	 * Returns a string representation of this cube. 
	 * The reprsentation includes that we are dealing with a cube, its poition 
	 * and its terrainType.
	 * 
	 * @return A string representation of the cube.
	 */
	@Override
	public String toString() {
		return "Cube\n"
				+ "position: " + Arrays.toString(getPosition().getCoordinates()) +"\n"
				+ "terrain: " + getTerrainType() +"\n"
				+"=====";
	}
	
	/**
	 * Sets the world this cube belongs to.
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
	 * A variable that references the world this cube is part of.
	 */
	private World world;

	/**
	 * Symbolic constant denoting the length of a cube.
	 */
	public static final int CUBE_LENGHT = 1;
	
	/**
	 * Return the terrain type of this cube.
	 */
	@Basic @Raw
	public Terrain getTerrainType() {
		return this.terrainType;
	}
	
	/**
	 * Check whether the given terrain type index is a valid terrain type index for any cube.
	 *  
	 * @param	terrainIndex
	 *				The index, corresponding to a terrain type, to check.
	 * @return	The terrain type index must be a valid index for the enum Terrain.
	 *				| result == ( (terrainIndex >= 0) && (terrainIndex < Terrain.values().length) )
	*/
	public static boolean isValidTerrainTypeIndex(int terrainIndex) {
		return ( (terrainIndex >= 0) && (terrainIndex < Terrain.values().length) );
	}
	
	/**
	 * Set the terrain type of this cube to the terrain with the given terrain index. 
	 * 
	 * @param  	terrainIndex
	 *         	The index (position in enum) of the new terrain type for this cube.
	 * @post   	The terrain type of this new cube is equal to
	 *         	the terrain type with the given index.
	 *       	| new.getTerrainType() == Terrain.values()[terrainIndex]
	 * @throws 	IllegalArgumentException
	 *         	The given value does not correspond to a terrain type for any
	 *         	cube.
	 *       	| ! isValidTerrainType(getTerrainType())
	 */
	public void setTerrainType(int terrainIndex) throws IllegalArgumentException {
		if (!isValidTerrainTypeIndex(terrainIndex))
			throw new IllegalArgumentException();
		
		this.setTerrainType(Terrain.values()[terrainIndex]);
	}
	
	/**
	 * Set the terrain type of this cube to the given terrain type.
	 * 
	 * @param  terrainType
	 *         The new terrain type for this cube.
	 * @post   The terrain type of this new cube is equal to
	 *         the given terrain type.
	 *       | new.getTerrainType() == terrainType
	 */
	@Raw
	public void setTerrainType(Terrain terrainType) {
		this.terrainType = terrainType;
	}
	
	/**
	 * Variable registering the terrain type of this cube.
	 * The default value is air.
	 */
	private Terrain terrainType = Terrain.AIR;
	
	/**
	 * Returns whether this cube is passable or not.
	 * @return	True if the terrain is air or a workshop.
	 */
	@Raw
	public boolean isPassable() {
		if ((this.getTerrainType() == Terrain.AIR) || (this.getTerrainType() == Terrain.WORKSHOP))
			return true;
		else
			return false;
	}
		
	/**
	 * Return the position of this cube.
	 */
	@Basic @Raw
	public Position getPosition() {
		return this.position;
	}
	
	/**
	 * Check whether the given position is a valid position for
	 * any cube.
	 *  
	 * @param	position
	 *				The position to check.
	 * @return	True if all coordinates are greater than the minimal coordinate value.
	 * 
	 * @note	Cubes do not know the world they are part of. World also has a checker for valid positions
	 *				(within the boundaries of the world).
	*/
	public boolean isValidCubeCoordinates(int[] position) {
		if (position.length == 3){
				if (	(World.CUBE_COORDINATE_MIN <= position[0]) && 
						(World.CUBE_COORDINATE_MIN <= position[1]) && 
						(World.CUBE_COORDINATE_MIN <= position[2])  )
				return true;
		}
		return false;
	}

	// TODO controle bij setPosition nodig? Die vraag geldt ook voor andere klassen.
	/**
	 * Set the position of this cube to the given position.
	 * 
	 * @param	position
	 *				The new position for this cube.
	 * @post	The position of this new cube is equal to the given position.
	 *				| new.getPosition() == position
	 * @throws IllegalArgumentException
	 *				The given position is not a valid position for any cube.
	 *				| ! isValidPosition(getPosition())
	 */
	@Raw
	public void setPosition(Position position) throws IllegalArgumentException {
		this.position = position;
	}
	
	/**
	 * Variable registering the position of this cube.
	 */
	private Position position;
	
}
