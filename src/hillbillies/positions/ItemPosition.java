package hillbillies.positions;

import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.World;

/**
 * A class of item positions.
 * @author Sander Mergan Thomas Vrancken
 * 
 * @version 2.1
 */
public class ItemPosition extends Position {

	/**
	 * Initializes this itemPosition with the given coordinates and world.
	 * @param world
	 *				The world for this new item position.
	 * @param doubleCoordinates
	 *				The coordinates for this new item position.
	 * @effect super(world, doubleCoordinates)
	 */
	public ItemPosition(World world, double[] doubleCoordinates) {
		super(world, doubleCoordinates);
	}
	
	/**
	 * Checks if the given coordinates are valid coordinates.
	 * @param	xCoordinate
	 * 					The x-coordinate of the given coordinates.
	 * @param	yCoordinate
	 * 					The y-coordinate of the given coordinates.
	 * @param	zCoordinate
	 * 					The z-coordinate of the given coordinates.
	 * @return	True if and only if all coordinates are valid.
	 * 					|result == (isValidPosition(double xCoordinate, double yCoordinate, double zCoordinate))
	 */
	@Raw
	public boolean canHaveAsItemCoordinates(double xCoordinate, double yCoordinate, double zCoordinate) {
		return this.isValidXCoordinate(xCoordinate) && this.isValidYCoordinate(yCoordinate) && this.isValidZCoordinate(zCoordinate);
	}
	
	/**
	 * Checks whether the given coordinates are valid coordinates.
	 * @param	coordinates
	 * 					The coordinates to check.
	 * @return	True if and only if all three coordinates lay within the game world.
	 * 					|result == (isValidXCoordinate(position[0]) && 
	 * 										isValidYCoordinate(position[1]) &&
	 * 									    isValidZCoordinate(position[2]))
	 */
	@Raw
	public boolean canHaveAsItemCoordinates(double[] coordinates) {
		return (this.isValidXCoordinate(coordinates[0]) && this.isValidYCoordinate(coordinates[1])  && this.isValidZCoordinate(coordinates[2]));
	}
	
	/**
	 * Checks whether the given cube coordinates are valid coordinates.
	 * @param	cubeCoordinates
	 * 					The coordinates of the cube to check.
	 * @return	True if and only if all three coordinates are within the game world.
	 * 					|result == (isValidXCoordinate(cubePosition[0]) && 
	 * 									 isValidYCoordinate(cubePosition[1]) &&
	 * 									 isValidZCoordinate(cubePosition[2]))
	 */
	@Raw
	public boolean canHaveAsItemCoordinates(int[] cubeCoordinates) {
		return (this.isValidXCoordinate(cubeCoordinates[0]) && this.isValidYCoordinate(cubeCoordinates[1])  && this.isValidZCoordinate(cubeCoordinates[2]));
	}
	
}
