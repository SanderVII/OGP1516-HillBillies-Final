package hillbillies.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.Cube;
import hillbillies.model.World;
import ogp.framework.util.Util;

/**
 * A class of positions and helper methods for used for calculating positions.
 * All methods use vectors(arrays) of length 3.
 * @invar  The cost of each position must be a valid cost for any position.
 *				 | isValidCost(getCost())
 * @invar  The coordinates of this position must be valid coordinates for this position.
 *				 | canHaveAsCoordinates(getCoordinates())
 * 
 * @author 	Thomas Vranken, Sander Mergan
 * @version	2.6
 */
public class Position {
	
	/**
	 * Initializes this position with the given world, coordinates and zero cost.
	 * @param world
	 *				The world this position belongs to.
	 * @param doubleCoordinates
	 *				The coordinates for this new position.
	 * @effect Initializes this position with the given world and coordinates and zero cost.
	 */
	public Position(World world, double[] doubleCoordinates) throws IllegalArgumentException{
		this(world, doubleCoordinates,0);
	}
	
	/**
	 * Initializes this position with the given world, coordinates and cost.
	 * @param world
	 *				The world this position belongs to.
	 * @param doubleCoordinates
	 *				The coordinates for this new position.
	 * @param cost
	 *				An integer representing the cost of traveling trough this position.
	 *
	 *@effect Sets the world of this position to the given world. 
	 *				| setWorld(world)
	 *				TODO misschien enkel de grootte van de wereld overnemen zodat isValidCoordinates static kan.
	 * @effect Sets the coordinates of this position to the given coordinates. 
	 *				| setCoordinates(doubleCoordinates)
	 * @effect Sets the cost of traveling through this position to the given cost. 
	 *				| setCost(cost)
	 */
	public Position(World world, double[] doubleCoordinates, double cost) throws IllegalArgumentException{
		this.setWorld(world);
		this.setCoordinates(doubleCoordinates);
		this.setCost(cost);
	}
	
	/**
	 * Initializes this position with the given world, coordinates and zero cost.
	 * @param world
	 *				The world this position belongs to.
	 * @param doubleCoordinates
	 *				The coordinates for this new position.
	 * @effect Initializes this position with the given world, the cube center of the given cube coordinates
	 *				as its coordinates and zero cost. 
	 */
	public Position(World world, int[] cubeCoordinates) {
		this(world, getCubeCenter(cubeCoordinates));
	}
	
	/**
	 * Returns a string representation of this position.
	 * @return | result == "Position: { " + this.getXCoordinate() + ", " + this.getYCoordinate() + ", " + this.getZCoordinate() + " }"
	 */
	@Override
	public String toString(){
		return "Position: { " + this.getXCoordinate() + ", " + this.getYCoordinate() + ", " + this.getZCoordinate() 
		+ ", " + this.getCost() + " }";
	}
	
	/**
	 * Return the cost of this position.
	 */
	@Basic @Raw
	public double getCost() {
		return this.cost;
	}
	
	/**
	 * Check whether the given cost is a valid cost for
	 * any position.
	 *  
	 * @param  cost
	 *				The cost to check.
	 * @return The cost must be greater than or equal to 0.
	 *				| result == (cost >= 0)
	*/
	public static boolean isValidCost(double cost) {
		return (cost >= 0);
	}
	
	/**
	 * Set the cost of this position to the given cost.
	 * 
	 * @param  cost
	 *				The new cost for this position.
	 * @post	The cost of this new position is equal to the given cost.
	 *				| new.getCost() == cost
	 * @throws IllegalArgumentException
	 *				The given cost is not a valid cost for any position.
	 *				| ! isValidCost(getCost())
	 */
	@Raw
	public void setCost(double cost) throws IllegalArgumentException {
		if (! isValidCost(cost))
			throw new IllegalArgumentException();
		this.cost = cost;
	}
	
	/**
	 * Variable registering the cost of this position.
	 */
	private double cost;
	
	/*TODO use in setPosition, docs and invariants?
	 * not using this assumes a position can be created with coordinates outside of its world.
	 */
	/**
	 * Check if this position can have the given coordinates as its coordinates.
	 * @param 	coordinates
	 * 				The coordinates to check.
	 * @return	True if the coordinates are within the range of this position's world.
	 */
	public boolean canHaveAsCoordinates(double[] coordinates) {
		return ( (coordinates != null) &&
				(this.getWorld().getMaximumXValue() > coordinates[0]) && 
				(this.getWorld().getMaximumYValue() > coordinates[1]) && 
				(this.getWorld().getMaximumZValue() > coordinates[2]) && 
				(coordinates[0] >= World.CUBE_COORDINATE_MIN) && 
				(coordinates[1] >= World.CUBE_COORDINATE_MIN) && 
				(coordinates[2] >= World.CUBE_COORDINATE_MIN) );
	}
	
	/**
	 * Sets the coordinates of this position.
	 * 
	 * @param doubleCoordinates
	 *				The coordinates to set this positions coordinates to.
	 * @post The coordinates of this position are equal to the given coordinates.
	 *				| new.coordinates = doubleCoordinates
	 * @throws IllegalArgumentException
	 *				The given coordinates are not valid coordinates for this position.
	 */
	public void setCoordinates(double[] doubleCoordinates) throws IllegalArgumentException{
		if ( ! isValidArray(doubleCoordinates)) {
			throw new IllegalArgumentException("The position of a unit should be three-dimensional.");
		}
		
		if (! canHaveAsCoordinates(doubleCoordinates)){
			System.out.println(Arrays.toString(doubleCoordinates));
			throw new IllegalArgumentException();
		}
		this.coordinates = doubleCoordinates;
	}
	
	/**
	 * Sets the coordinates of this position.
	 * @param cubeCoordinates
	 *				The cube coordinates to set this positions coordinates to.
	 *
	 * @post The coordinates are equal to the center of the cube at the given cubeCoordinates.
	 *				| new.coordinates = cubeCoordinates
	 */
	public void setCoordinates(int[] cubeCoordinates) {
		double[] doublePosition = new double[3];
		doublePosition = getCubeCenter(cubeCoordinates);
		this.setCoordinates(doublePosition);
	}
	
	/**
	 * Returns the coordinates of this position.
	 * @return | result == this.position
	 */
	public double[] getCoordinates(){
		return this.coordinates;
	}
	
	/**
	 * A variable referencing the coordinates.
	 */
	protected double[] coordinates = new double[3];
	
	/**
	 * Checks if the given array is a valid array which can be used 
	 * by the other methods of this class.
	 * 
	 * @param 	array
	 * 				The array to check.
	 * @return 	True if the size of this array is exactly 3.
	 * 				| result == (array.length == 3)
	 */
	protected static boolean isValidArray(double[] array) {
		return (array == null) ||(array.length == 3);
	}
	
	/**
	 * Checks if the given array is a valid array which can be used 
	 * by the other methods of this class.
	 * 
	 * @param 	array
	 * 				The array to check.
	 * @return 	True if the corresponding double[] array is valid.
	 * 				| result == isValidArray(array)
	 */
	protected static boolean isValidArray(int[] array) {
		double[] dummy = new double[array.length];
		for (int count = 0; count < array.length; count++)
			dummy[count] = array[count];
		return isValidArray(dummy);
	}
	
	/**
 	 * Return the coordinates of the cube which holds the given location.
 	 * 
 	 * @return	The cube coordinates which hold the given coordinates.
 	 * 				| for each coordinate in position:
 	 *				|	cubeCoordinates[coordinate] == (int) Math.floor(cubeCoordinates[coordinate])
 	 * @throws	IllegalArgumentException
 	 *				The given coordinates are invalid.
 	 *				| ! (isValidArray(coordinates))
 	 */
	@Raw
 	public static int[] getCubeCoordinates(double[] coordinates) throws IllegalArgumentException {
		if (! isValidArray(coordinates))
			throw new IllegalArgumentException(Arrays.toString(coordinates));
 		int[] cubeCoordinates = new int[3];
 		for (int count = 0; count < 3; count++)
 			cubeCoordinates[count] = (int) Math.floor(coordinates[count]);
 		return cubeCoordinates;
 	}
	
	/**
 	 * Returns the coordinates of the center of the given cube.
 	 * @param	cubeCoordinates
 	 * 				The given cube coordinates.
 	 * @return	The coordinates of the center of the cube.
 	 * 				| for each coordinate in cubeCenter:
 	 *				|	cubeCenter[coordinate] == Math.floor(cubeCoordinates[coordinate]) + Cube.CUBE_LENGHT / 2.0;
 	 * @throws	IllegalArgumentException
 	 *				The given position is invalid.
 	 * 				| ! (isValidArray(cubeCoordinates))
 	 */
 	public static double[] getCubeCenter(int[] cubeCoordinates) {
 		if (! isValidArray(cubeCoordinates))
			throw new IllegalArgumentException(Arrays.toString(cubeCoordinates));
 		double[] cubeCenter = new double[3];
 		for (int count = 0; count < 3; count++) {
 			cubeCenter[count] = Math.floor(cubeCoordinates[count]) + Cube.CUBE_LENGHT / 2.0;
 		}
 		return cubeCenter;
 	}

 	/**
 	 * Returns the coordinates of the center of the cube which holds the given position.
 	 * @param	coordinates
 	 * 				The given coordinates.
 	 * @return	The coordinates of the center of the cube of the given coordinates.
 	 * 				| getCubeCenter(getCubeCoordinates(coordinates))
 	 * @throws	IllegalArgumentException
 	 *				The given position is invalid.
 	 * 				| ! (isValidArray(coordinates))
 	 */
 	public static double[] getCubeCenter(double[] coordinates) {
 		if (! isValidArray(coordinates))
			throw new IllegalArgumentException(Arrays.toString(coordinates));
 		return (getCubeCenter(getCubeCoordinates(coordinates)));
 	}
 	
 	/**
 	 * Returns the coordinates of the center of the cube corresponding to the current coordinates of this position.
 	 * 
 	 * @return	The coordinates of the center of the cube corresponding to the current coordinates of this position.
 	 *				| result == getCubeCenter(getPosition())
 	 */
 	public double[] getCubeCenter() {
 		return getCubeCenter(this.getCoordinates());
 	}
 	
 	/**
 	 * Returns the coordinates of the center of the cube which holds the given coordinates in the XY-plane.
 	 * The z-coordinate is the same as that of the given position.
 	 * 
 	 * @param	coordinates
 	 *				The given coordinates
 	 * @return	Coordinates at the same z-level as the given coordinates,
 	 * 				and the x and y-coordinate equal to the center of the occupied cube.
 	 * 				| result == {this.getCubeCenter(coordinates)[0],this.getCubeCenter(coordinates)[1],coordinates[2]});	
 	 * @throws	IllegalArgumentException
 	 *				The given coordinates are invalid.
 	 * 				| ! (isValidArray(position))		
 	 */
 	public static double[] getSurfaceCenter(double[] coordinates) {
 		if (! isValidArray(coordinates))
			throw new IllegalArgumentException(Arrays.toString(coordinates));
 		double[] cubeCenter = getCubeCenter(coordinates);
 		return (new double[]{cubeCenter[0],cubeCenter[1],coordinates[2]});
 	}
 	
 	/**
 	 * Returns the coordinates of the 2D-center of the cube corresponding to the position.
 	 * The 2D-center is defined as the center in the XY-plane. The z-coordinate
 	 * is the same as this unit's z-coordinate.
 	 * 
 	 * @return	The coordinates of the center of the cube corresponding to the coordinates.
 	 * 				| result == getSurfaceCenter(getPosition())
 	 */
 	public double[] getSurfaceCenter() {
 		return getSurfaceCenter(this.getCoordinates());
 	}
 	
 	/**
 	 * Returns the next position for a given velocity and time interval.
 	 * 
 	 * @param	coordinates
 	 * 				The original coordinates.
 	 * @param	velocity
 	 * 				The given velocity.
 	 * @param	deltaT
 	 * 				The given time interval.
 	 * @return	The next coordinates, calculated as the velocity times deltaT, added to the original coordinates.
 	 * 				| for element in nextPosition
 	 * 				|	nextCoordinates[element] = coordinates[element] + velocity[element] * deltaT;
 	 * @throws	IllegalArgumentException
 	 *				The given coordinates are invalid.
 	 * 				| ! (isValidArray(coordinates))
 	 * @throws	IllegalArgumentException
 	 *				The given velocity is invalid.
 	 * 				| ! (isValidArray(coordinates))		
 	 */
 	public static double[] calculateNextCoordinates(double[] coordinates, double[] velocity, double deltaT) {
 		if (! isValidArray(coordinates))
			throw new IllegalArgumentException(Arrays.toString(coordinates));
 		if (! isValidArray(velocity))
 			throw new IllegalArgumentException(Arrays.toString(velocity));
 		double[] newCoordinates = new double[3];
		for (int count = 0; count < 3; count++){
			newCoordinates[count] = coordinates[count] + velocity[count] * deltaT;
		}
		return newCoordinates;
 	}
 	
 	/**
 	 * Returns the distance between the two given coordinates.
 	 * @param	coordinates1
 	 * 				The first coordinates.
 	 * @param	coordinates2
 	 * 				The second coordinates.
 	 * @return The distance based on the mathematical formula of distance in 3 dimensions.
 	 * 				| result == Math.sqrt(Math.pow(coordinates1[0] - coordinates2[0], 2)
 	 *				| +Math.pow(coordinates1[1] - coordinates2[1], 2)
 	 *				| +Math.pow(coordinates1[2] - coordinates2[2], 2));
 	 * @throws	IllegalArgumentException
 	 *				The given coordinates 1 is invalid.
 	 * 				| ! (isValidArray(coordinates1))
 	 * @throws	IllegalArgumentException
 	 *				The given coordinates 2 is invalid.
 	 * 				| ! (isValidArray(coordinates2))
 	 */
 	public static double getDistance(double[] coordinates1, double[] coordinates2) throws IllegalArgumentException{
		if (! isValidArray(coordinates1))
			throw new IllegalArgumentException(Arrays.toString(coordinates1));
 		if (! isValidArray(coordinates2))
 			throw new IllegalArgumentException(Arrays.toString(coordinates2));
 		return Math.sqrt(Math.pow(coordinates1[0] - coordinates2[0], 2)
 				+Math.pow(coordinates1[1] - coordinates2[1], 2)
 				+Math.pow(coordinates1[2] - coordinates2[2], 2));
 	}
 	
 	/**
	 * Checks if two cube coordinates are the same by comparing each of their coordinates.
	 * 
	 * @param	coordinates1
	 *				The coordinates of the first cube.
	 * @param	coordinates2
	 *				The coordinates of the second cube.
	 * @return	True if all coordinates of coordinates1 and coordinates2 are the same.
	 * @throws	IllegalArgumentException
 	 *				The given coordinates 1 is invalid.
 	 * 				| ! (isValidArray(coordinates1))
 	 * @throws	IllegalArgumentException
 	 *				The given coordinates 2 is invalid.
 	 * 				| ! (isValidArray(coordinates2))
	 */
	public static boolean equals (int[] coordinates1, int[] coordinates2) throws IllegalArgumentException {
		if (! isValidArray(coordinates1))
			throw new IllegalArgumentException(Arrays.toString(coordinates1));
 		if (! isValidArray(coordinates2))
 			throw new IllegalArgumentException(Arrays.toString(coordinates2));
		return ( (coordinates1[0] == coordinates2[0]) && (coordinates1[1] == coordinates2[1])
				&& (coordinates1[2] == coordinates2[2]) );
	}

	/**
	 * Checks if two coordinates are nearly the same.
	 * @param	coordinates1
	 * 				The first coordinates.
	 * @param	coordinates2
	 * 				The second coordinates.
	 * @return	True if all coordinates are nearly the same.
	 * 				| result == (Util.fuzzyEquals(coordinates1[0]-coordinates2[0], 0)) &&
	 *		 		|	(Util.fuzzyEquals(coordinates1[1]-coordinates2[1], 0)) &&
	 *			 	|	(Util.fuzzyEquals(coordinates1[2]-coordinates2[2], 0)) 
	 * @throws	IllegalArgumentException
 	 * 				The given coordinates 1 is invalid.
 	 * 				| ! (isValidArray(coordinates1))
 	 * @throws	IllegalArgumentException
 	 * 				The given coordinates 2 is invalid.
 	 * 				| ! (isValidArray(coordinates2))
	 */
	public static boolean fuzzyEquals(double[] coordinates1, double[] coordinates2) throws IllegalArgumentException{
		if (! isValidArray(coordinates1))
			throw new IllegalArgumentException(Arrays.toString(coordinates1));
 		if (! isValidArray(coordinates2))
 			throw new IllegalArgumentException(Arrays.toString(coordinates2));
		 return ( (Util.fuzzyEquals(coordinates1[0]-coordinates2[0], 0)) &&
				 	(Util.fuzzyEquals(coordinates1[1]-coordinates2[1], 0)) &&
				 	(Util.fuzzyEquals(coordinates1[2]-coordinates2[2], 0)) );
	}
	
	/**
	 * Checks if two cube coordinates are next to each other, or the same.
	 * 
	 * @param	coordinates1
	 * 				The coordinates of the first cube.
	 * @param	coordinates2
	 * 				The coordinates of the second cube.
	 * @return	True if and only if the difference between coordinates 
	 * 				is less than or equal to 1 for each coordinate.
	 * 				| result == (Math.abs(coordinates1[0] - coordinates2[0]) <= 1) &&
	 *				| (Math.abs(coordinates1[1] - coordinates2[1]) <= 1) &&
	 *				| (Math.abs(coordinates1[2] - coordinates2[2]) <= 1)
	 * @throws	IllegalArgumentException
 	 * 				The given coordinates 1 is invalid.
 	 * 				| ! (isValidArray(coordinates1))
 	 * @throws	IllegalArgumentException
 	 * 				The given coordinates 2 is invalid.
 	 * 				| ! (isValidArray(coordinates2))
	 */
	public static boolean isAdjacentTo(int[] coordinates1, int[] coordinates2) throws IllegalArgumentException {
		if (! isValidArray(coordinates1))
			throw new IllegalArgumentException(Arrays.toString(coordinates1));
 		if (! isValidArray(coordinates2))
 			throw new IllegalArgumentException(Arrays.toString(coordinates2));
		return (Math.abs(coordinates1[0] - coordinates2[0]) <= 1) &&
				(Math.abs(coordinates1[1] - coordinates2[1]) <= 1) &&
				(Math.abs(coordinates1[2] - coordinates2[2]) <= 1);
	}
	
	/**
	 * Returns the velocity vector, based on the given coordinates and speed.
	 * 
	 * @param	coordinates1
	 * 				The first coordinates.
	 * @param	coordinates2
	 * 				The second coordinates.
	 * @param	speed
	 * 				The given speed.
	 * @return	If the two coordinates are almost the same, return an array of zeros.
	 *				| if ( Unit.fuzzyEquals(this.getTargetCoordinates(), this.getCoordinates()) ) 
	 *				|	then return {0,0,0}
	 * 				Else, the velocity based on the mathematical formula using the given speed.
	 * 				| result == {(coordinates2[0]-coordinates1[0])/(getDistance(coordinates1, coordinates2)) * speed,
	 *				| 	(coordinates2[1]-coordinates1[1])/(getDistance(coordinates1, coordinates2)) * speed,
	 *				| 	(coordinates2[2]-coordinates1[2])/(getDistance(coordinates1, coordinates2)) * speed}
	 * @throws	IllegalArgumentException
 	 * 				The given coordinates 1 is invalid.
 	 * 				| ! (isValidArray(coordinates1))
 	 * @throws	IllegalArgumentException
 	 * 				The given coordinates 2 is invalid.
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
	 * Returns a set of all integer coordinates which are at most r cubes away from
	 * the given coordinates. The given coordinates are also included.
	 * @param	coordinates
	 * 				The given coordinates.
	 * @param	r
	 * 				The given range.
	 * @return	A set of coordinatess which integer coordinates are at most r away from the coordinates
	 * 				of the given coordinates, inclusively.
	 *				| for all newCoordinates in result:
	 * 				|		newCoordinates[0] = coordinates[0] +/- (0..r) &&
	 * 				|		newCoordinates[1] = coordinates[1] +/- (0..r) &&
	 * 				|		newCoordinates[2] = coordinates[2] +/- (0..r)
	 * @throws	IllegalArgumentException
	 * 				The given coordinates is invalid.
	 * 				| ! (isValidArray(coordinates))
	 * @throws	IllegalArgumentException	
	 * 				The given range is negative.
	 * 				| r < 0
	 */
	public static Set<int[]> getCubeCoordinatesInRange(int[] coordinates, int r) throws IllegalArgumentException {
		if (! isValidArray(coordinates))
			throw new IllegalArgumentException(Arrays.toString(coordinates));
		if (r < 0)
			throw new IllegalArgumentException(Integer.toString(r));
		Set<int[]> result = new HashSet<>();
		for (int x=-r; x <= r; x++)
			for (int y=-r; y<=r; y++)
				for (int z=-r; z <= r; z++) {
					result.add(new int[]{x+coordinates[0],y+coordinates[1],z+coordinates[2]});
				}
		return result;
	}

	/**
	 * Checks if a list of positions contains a certain location.
	 * @param queue
	 *				The list of positions to check.
	 * @param coordinates2
	 *				The position to search for.
	 * @return True if any of the list's positions holds the same position as the given coordinates.
	 */
	public static boolean containsCoordinates(List<Position> queue, int[] coordinates2) {
		for (int count = 0; count < queue.size(); count++) {
			int[] coordinates1 = queue.get(count).getCubeCoordinates();
			if (Position.equals(coordinates1, coordinates2))
				return true;
		}
		return false;
	}
	
	/**
	 * Return the coordinates of the cube corresponding to the exact coordinates.
	 * A cube coordinate corresponds to the exact coordinates, rounded down to the nearest integer.
	 * 
	 * @return	The cube coordinates, given as an array of 3 integers.
	 * 					| result ==  getCubeCoordinates(this.getCoordinates())
	 */
	@Raw
	public int[] getCubeCoordinates(){
		return getCubeCoordinates( this.getCoordinates());
	}
	
	/**
	 * Return the x-coordinate of this unit.
	 */
	@Basic @Raw
	public double getXCoordinate() {
		return this.getCoordinates()[0];
	}
	
	/**
	 * Check whether the given x-coordinate is a valid x-coordinate for any unit.
	 *  
	 * @param	x-coordinate
	 *         			The x-coordinate to check.
	 * @return	True if and only if the given value is higher than or equal to the minimal coordinate value
	 * 					and less than the maximal value.
	 *       			| result == ((xCoordinate >= World.CUBE_COORDINATE_MIN) && (xCoordinate < this.getWorld().getMaximumXValue()))
	*/
	@Raw
	public boolean isValidXCoordinate(double xCoordinate) {
		return ((xCoordinate >= World.CUBE_COORDINATE_MIN) && (xCoordinate < this.getWorld().getMaximumXValue()));
	}
	
	/**
	 * Set the x-coordinate of this unit to the given x-coordinate.
	 * 
	 * @param	xCoordinate
	 *         			The new x-coordinate for this unit.
	 * @post	The x-coordinate of this new unit is equal to the given x-coordinate.
	 *       		| new.getXCoordinate() == xCoordinate
	 * @throws	IllegalArgumentException
	 *         			The given x-coordinate is not a valid x-coordinate for any unit.
	 *       			| ! isValidXCoordinate(getXCoordinate())
	 */
	@Raw
	public void setXCoordinate(double xCoordinate)  throws IllegalArgumentException {
		if (! isValidXCoordinate(xCoordinate))
			throw new IllegalArgumentException("X Coordinate is outside the allowed range!");
		this.coordinates[0] = xCoordinate;
	}
	
	/**
	 * Return the y-coordinate of this unit.
	 */
	@Basic @Raw
	public double getYCoordinate() {
		return this.getCoordinates()[1];
	}
	
	/**
	 * Check whether the given y-coordinate is a valid y-coordinate for
	 * any unit.
	 *  
	 * @param	y-coordinate
	 *         			The y-coordinate to check.
	 * @return True if and only if the given value is higher than or equal to the minimal coordinate value
	 * 					and less than the maximal value.
	 *       			| result == ((yCoordinate >= World.CUBE_COORDINATE_MIN) && (yCoordinate < this.getWorld().getMaximumYValue())) 
	*/
	@Raw
	public boolean isValidYCoordinate(double yCoordinate) {
		return ((yCoordinate >= World.CUBE_COORDINATE_MIN) && (yCoordinate < this.getWorld().getMaximumYValue()));
	}
	
	/**
	 * Set the y-coordinate of this unit to the given y-coordinate.
	 * 
	 * @param	 yCoordinate
	 *         			The new y-coordinate for this unit.
	 * @post	The y-coordinate of this new unit is equal to the given y-coordinate.
	 *       		| new.getYCoordinate() == yCoordinate
	 * @throws	IllegalArgumentException
	 *         			The given y-coordinate is not a valid y-coordinate for any unit.
	 *       			| ! isValidYCoordinate(getYCoordinate())
	 */
	@Raw
	public void setYCoordinate(double yCoordinate) throws IllegalArgumentException {
		if (! isValidYCoordinate(yCoordinate))
			throw new IllegalArgumentException("Y Coordinate is outside the allowed range!");
		this.coordinates[1] = yCoordinate;
	}
	
	/**
	 * Return the z-coordinate of this unit.
	 */
	@Basic @Raw
	public double getZCoordinate() {
		return this.getCoordinates()[2];
	}
	
	/**
	 * Check whether the given z-coordinate is a valid z-coordinate for any unit.
	 *  
	 * @param	z-coordinate
	 *         			The z-coordinate to check.
	 * @return	True if and only if the given value is higher than or equal to the minimal coordinate value
	 * 					and less than the maximal value.
	 *       			| result == ((zCoordinate >= World.CUBE_COORDINATE_MIN) && (zCoordinate < this.getWorld().getMaximumZValue())) 
	 */     
	@Raw
	public boolean isValidZCoordinate(double zCoordinate) {
		return ((zCoordinate >= World.CUBE_COORDINATE_MIN) && (zCoordinate <this.getWorld().getMaximumZValue()));
	}
	
	/**
	 * Set the z-coordinate of this unit to the given z-coordinate.
	 * 
	 * @param	zCoordinate
	 *         			The new z-coordinate for this unit.
	 * @post	The z-coordinate of this new unit is equal to the given z-coordinate.
	 *       		| new.getZCoordinate() == zCoordinate
	 * @throws IllegalArgumentException
	 *         			The given z-coordinate is not a valid z-coordinate for any unit.
	 *       			| ! isValidZCoordinate(getZCoordinate())
	 */
	@Raw
	public void setZCoordinate(double zCoordinate) throws IllegalArgumentException {
		if (! isValidZCoordinate(zCoordinate))
			throw new IllegalArgumentException("Z Coordinate is outside of the allowed range!");
		this.getCoordinates()[2]= zCoordinate;
	}
	
	/**
	 * Returns the world this is a position of.
	 */
	public World getWorld(){
		return this.world;
	}
	
	/**
	 * Sets the world this position belongs to to the given world.
	 * @param	world
	 *				The world to attach this position to.
	 * @post	The world of this position is equal to the given world.
	 *				| new.getWorld() == world
	 * @throws IllegalArgumentException
	 *				The given world is not a valid world for any position.
	 */
	protected void setWorld(World world) throws IllegalArgumentException{
		if ( ! isValidWorld(world) )
			throw new IllegalArgumentException("You are trying to set an illegal world.");
		
		this.world = world;
	}
	
	// TODO voorwaarden verzinnen.
	/**
	 * Returns whether this world is a valid world.
	 * @param world
	 *				The world to check.
	 * @return The given world is an active world.
	 */
	private boolean isValidWorld(World world) {
		return world != null;
	}
	
	/**
	 * A variable referencing the world this position belongs to.
	 */
	private World world;
	
	/**
	 * Determines whether 2 given coordinates are the same. Coordinates are the same is the x-,y- and z-coordinate are the same.
	 * @param coordinates1
	 *				One of the coordinates to compare.
	 * @param coordinates2
	 *				The other coordinates to compare.
	 */
	public static boolean equals(double[] coordinates1, double[] coordinates2) {
		if (! isValidArray(coordinates1))
			throw new IllegalArgumentException(Arrays.toString(coordinates1));
 		if (! isValidArray(coordinates2))
 			throw new IllegalArgumentException(Arrays.toString(coordinates2));
		return ( Util.fuzzyEquals(coordinates1[0], coordinates2[0]) && Util.fuzzyEquals(coordinates1[1], coordinates2[1])
				&& Util.fuzzyEquals(coordinates1[2], coordinates2[2]) );
	}
	
	/**
	 * Determines whether 2 given positions are the same. Positions are the same is the x-,y- and z-coordinate are the same.
	 * @param position1
	 *				One of the positions to compare.
	 * @param position2
	 *				The other position to compare.
	 */
	public static boolean equals(Position position1, Position position2){
		return equals(position1.getCoordinates(),position2.getCoordinates());
	}
	
	/**
	 * Determines whether 2 given positions are the same (extended version). Positions are the same is the x-,y- and z-coordinate are the same 
	 * and their cost have to be equal as well.
	 * @param position1
	 *				One of the positions to compare.
	 * @param position2
	 *				The other position to compare.
	 */
	public static boolean extendedEquals(Position position1, Position position2) {
		return equals(position1, position2) && (position1.getCost() == position2.getCost());
	}
	
	/**
	 * Return the angle between two vectors which have the same starting point, and their own ending point.
	 * @param start
	 * 			The start position.
	 * @param destination1
	 * 			The end position of the first vector.
	 * @param destination2
	 * 			The end position of the second vector. 
	 * @return	The angle between the two vectors in radians.
	 */
	public static double getAngleBetween(double[] start, double[] destination1, double[] destination2) {
		double[] baseVector = new double[2];
		for (int count = 0; count < 2; count++)
			baseVector[count] = destination1[count]-start[count];
		double[] compareVector = new double[2];
		for (int count = 0; count < 2; count++)
			compareVector[count] = destination2[count]-start[count];
		
		double angle1 = Math.atan2(destination1[1]-start[1], destination1[0]-start[0]);
		double angle2 = Math.atan2(destination2[1]-start[1], destination2[0]-start[0]);
		if (angle1 < 0)
			angle1 = angle1 + Math.PI * 2;
		if (angle2 < 0)
			angle2 = angle2 + Math.PI * 2;
		
		double result = (Math.abs(angle2-angle1));
		if (result > Math.PI)
			result = 2*Math.PI-result;
		return result;
	}
	
	/**
	 * Return the angle between two vectors which have the same starting point, and their own ending point.
	 * @param start
	 * 			The start position.
	 * @param destination1
	 * 			The end position of the first vector.
	 * @param destination2
	 * 			The end position of the second vector. 
	 * @return	The angle between the two vectors in radians.
	 */
	public static double getAngleBetween(int[] start, int[] destination1, int[] destination2) {
		double[] startDouble = new double[]{start[0],start[1]};
		double[] destination1Double = new double[]{destination1[0],destination1[1]};
		double[] destination2Double = new double[]{destination2[0],destination2[1]};
		return Position.getAngleBetween(startDouble, destination1Double, destination2Double);
	}
	
	/**
	 * Return the angle between two vectors which have the same starting point, and their own ending point.
	 * @param start
	 * 			The start position.
	 * @param destination1
	 * 			The end position of the first vector.
	 * @param destination2
	 * 			The end position of the second vector. 
	 * @return	The angle between the two vectors in radians.
	 */
	public static double getAngleBetween(Position start, Position destination1, Position destination2) {
		double[] startDouble = start.getCoordinates();
		double[] destination1Double = destination1.getCoordinates();
		double[] destination2Double = destination2.getCoordinates();
		return Position.getAngleBetween(startDouble, destination1Double, destination2Double);
	}
	
}
