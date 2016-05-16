package hillbillies.positions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.World;

/**
 * A class of unit positions.
 * @author Sander Mergan Thomas Vrancken
 * 
 * @version 2.3
 */
public class UnitPosition extends Position{
	
	/**
	 * Initializes this unitPosition with the given coordinates and world.
	 * @param world
	 *				The world for this new unit position.
	 * @param doubleCoordinates
	 *				The coordinates for this new unit  position.
	 * @effect super(world, doubleCoordinates)
	 */
	public UnitPosition(World world, double[] doubleCoordinates){
		super(world, doubleCoordinates);
	}
	
	/**
	 * Returns the velocity vector, based on the given coordinates and speed.
	 * 
	 * @param 	coordinates1
	 * 				The first coordinates.
	 * @param 	coordinates2
	 * 				The second coordinates.
	 * @param	speed
	 * 				The given speed.
	 * @return	If the two coordinates are almost the same, return an array of zeros.
	 *				| if ( Unit.fuzzyEquals(this.getTargetCoordinates(), this.getCoordinates()) ) 
	 *				|	then return {0,0,0}
	 * 			Else, the velocity based on the mathematical formula using the given speed.
	 * 				| result == {(coordinates2[0]-coordinates1[0])/(getDistance(coordinates1, coordinates2)) * speed,
	 *				| 	(coordinates2[1]-coordinates1[1])/(getDistance(coordinates1, coordinates2)) * speed,
	 *				| 	(coordinates2[2]-coordinates1[2])/(getDistance(coordinates1, coordinates2)) * speed}
	 * @throws	IllegalArgumentException
 	 * 			The given coordinates 1 is invalid.
 	 * 				| ! (isValidArray(coordinates1))
 	 * @throws	IllegalArgumentException
 	 * 			The given coordinates 2 is invalid.
 	 * 				| ! (isValidArray(coordinates2))
	 */
	public static double[] getVelocity(double[] coordinates1, double[] coordinates2, double speed) {
		if (! isValidArray(coordinates1))
			throw new IllegalArgumentException(Arrays.toString(coordinates1));
 		if (! isValidArray(coordinates2))
 			throw new IllegalArgumentException(Arrays.toString(coordinates2));
		
		// If target and current are the same, in the velocity formula you get a division by zero.
		if (fuzzyEquals(coordinates1, coordinates2))
				return new double[] {0,0,0};
		else {
			double distance = getDistance(coordinates1, coordinates2);
			double[] velocity = {(coordinates2[0]-coordinates1[0])/(distance) * speed,
					(coordinates2[1]-coordinates1[1])/(distance) * speed,
					(coordinates2[2]-coordinates1[2])/(distance) * speed};
			return velocity;
		}
	}
	
	/**
	 * Sets the coordinates of this position.
	 * @param doubleCoordinates
	 *				The coordinates to set this positions coordinates to.
	 *
	 * @post The position is equal to the given coordinates.
	 *				| new.coordinates = coordinates
	 *
	 * @throws IllegalArgumentException
	 *				The given coordinates are not valid coordinates for any unit position.
	 */
	@Override
	public void setCoordinates(double[] doubleCoordinates) throws IllegalArgumentException{		
		if ( ! canHaveAsUnitCoordinates(doubleCoordinates))
			throw new IllegalArgumentException("The given position is not a valid position for any unit.");
		this.coordinates = doubleCoordinates;
	}
	
	/**
	 * Checks whether the given coordinates are valid coordinates for any unit.
	 * @param	coordinates
	 * 					The coordinates to check.
	 * @return	True if and only if all three coordinates are within the game world
	 * 					and the cube with these coordinates is not solid.
	 * 					|result == (isValidXCoordinate(coordinates[0]) && 
	 * 										   isValidYCoordinate(coordinates[1]) &&
	 * 									       isValidZCoordinate(coordinates[2]) &&
	 * 										   getWorld().getCube(coordinates[0], coordinates[1], coordinates[2]).isPassable())
	 */
	@Raw
	public boolean canHaveAsUnitCoordinates(double[] coordinates) {
		return canHaveAsUnitCoordinates(coordinates[0],coordinates[1],coordinates[2]);
	}
	
	/**
	 * Checks whether the given cube coordinates are valid coordinates for any unit.
	 * @param	cubeCoordinates
	 * 					The coordinates of the cube to check.
	 * @return	True if and only if all three coordinates are within the game world
	 * 					and the cube with these coordinates is not solid.
	 * 					|result == (isValidXCoordinate(cubePosition[0]) && 
	 * 									 isValidYCoordinate(cubePosition[1]) &&
	 * 									 isValidZCoordinate(cubePosition[2]) &&
	 * 									 getWorld().getCube(cubePosition[0], cubePosition[1], cubePosition[2]).isPassable()))
	 */
	@Raw
	public boolean canHaveAsUnitCoordinates(int[] cubeCoordinates) {		
		return canHaveAsUnitCoordinates(cubeCoordinates[0],cubeCoordinates[1],cubeCoordinates[2]);
	}
	
	/**
	 * Checks if the given coordinates are valid coordinates for this unit.
	 * @param	xCoordinate
	 * 					The x-coordinate of the coordinates to check.
	 * @param	yCoordinate
	 * 					The y-coordinate of the coordinates to check.
	 * @param	zCoordinate
	 * 					The z-coordinate of the coordinates to check.
	 * @return	True if and only if all coordinates are valid 
	 * 					and the cube the unit is standing in is not solid.
	 * 					|result == (isValidPosition(double xCoordinate, double yCoordinate, double zCoordinate)
	 * 					|			&& getWorld().getCube(xCoordinate, yCoordinate, zCoordinate).isPassable() )
	 */
	// IMPORTANT: valid position for units != valid positions for world.
	// Units must stay in passable cubes!
	@Raw
	public boolean canHaveAsUnitCoordinates(double xCoordinate, double yCoordinate, double zCoordinate) {
		int[] cubePosition = Position.getCubeCoordinates(new double[] {xCoordinate, yCoordinate, zCoordinate});
		return (this.canHaveAsXCoordinate(xCoordinate) && this.canHaveAsYCoordinate(yCoordinate) && this.canHaveAsZCoordinate(zCoordinate) 
				&& this.getWorld().getCube(cubePosition[0], cubePosition[1], cubePosition[2]).isPassable());
	}
	
	/**
	 * Returns the corresponding list of Positions of the given list of UnitPositions.
	 * @param	queue
	 * 				The given list to convert.
	 * @throws	IllegalArgumentException
 	 *				The given UnitPosition is invalid.
 	 * 				| ! (isValidUnitPosition(unitPosition))
	 */
	public static List<Position> convertListOfUnitPositionToPosition(List<UnitPosition> queue) {
		List<Position> dummy = new ArrayList<Position>();
		for (UnitPosition unitPosition: queue){
			dummy.add((Position)unitPosition);
		}
		return dummy;
	}
	
}
