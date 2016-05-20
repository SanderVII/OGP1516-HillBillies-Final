package hillbillies.positions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.Cube;
import hillbillies.model.World;
import ogp.framework.util.Util;

/**
 * A class of positions and helper methods used for calculating positions/coordinates.
 * All methods use vectors(arrays) of length 3.
 * 
 * @invar	The coordinates of this position must be valid coordinates for this position.
 *				| canHaveAsCoordinates(getCoordinates())
 * 
 * @author 	Thomas Vranken, Sander Mergan
 * @version	3.0
 */
public class Position {

	/**
	 * Initializes this position with the given world and coordinates.
	 * 
	 * @param world
	 *				The world this position belongs to.
	 * @param doubleCoordinates
	 *				The coordinates for this new position.
	 *
	 *@post The world of this position is equal to the given world. 
	 *				| this.getWorld() == world
	 * @effect Sets the coordinates of this position to the given coordinates. 
	 *				| setCoordinates(doubleCoordinates)
	 *
	 * @throws IllegalArgumentException
	 *				The given world is not a valid world for any position.
	 *				| ( ! isValidWorld(world))
	 */
	public Position(World world, double[] doubleCoordinates) throws IllegalArgumentException{
		if ( ! isValidWorld(world) )
			throw new IllegalArgumentException("You are trying to set an illegal world.");
		this.world = world;
		
		this.setCoordinates(doubleCoordinates);
	}
	
	/**
	 * Initializes this position with the given world and coordinates.
	 * 
	 * @param	world
	 *				The world this position belongs to.
	 * @param doubleCoordinates
	 *				The coordinates for this new position.
	 * 
	 * @effect Initializes this position with the given world and the cube center of the given cube coordinates
	 *				as its coordinates.
	 *				| this(world, getCubeCenter(cubeCoordinates))
	 */
	public Position(World world, int[] cubeCoordinates) {
		this(world, getCubeCenter(cubeCoordinates));
	}
	
	/**
	 * Returns a string representation of this position.
	 */
	@Override
	public String toString(){
		return "Position: { " + this.getXCoordinate() + ", " + this.getYCoordinate() + ", " + this.getZCoordinate() + " }";
	}
	
	/**
	 * Checks whether this position can have the given coordinates as its coordinates.
	 * 
	 * @param	coordinates
	 *				The coordinates to check.
	 * 
	 * @return	True if the given coordinates are within the range of this position's world and the given coordinates are three-dimensional.
	 *				False if coordinates is null.
	 */
	public boolean canHaveAsCoordinates(double[] coordinates) {
		return ( (coordinates != null) &&
				(isValidArray(coordinates)) &&
				(this.canHaveAsXCoordinate(coordinates[0])) && 
				(this.canHaveAsYCoordinate(coordinates[1])) && 
				(this.canHaveAsZCoordinate(coordinates[2])) );
	}
	
	/**
	 * Checks whether the given cube coordinates are valid coordinates.
	 * 
	 * @param	cubeCoordinates
	 *				The coordinates of the cube to check.
	 *
	 * @return	| this.canHaveAsCoordinates(getCubeCenter(cubeCoordinates))
	 */
	@Raw
	public boolean canHaveAsCoordinates(int[] cubeCoordinates) {
		return canHaveAsCoordinates(getCubeCenter(cubeCoordinates));
	}
	
	/**
	 * Sets the coordinates of this position.
	 * 
	 * @param	doubleCoordinates
	 *				The coordinates to set this position's coordinates to.
	 *
	 * @post	The coordinates of this position are equal to the given coordinates.
	 *				| new.coordinates = doubleCoordinates
	 *
	 * @throws	IllegalArgumentException
	 *				The given coordinates are not valid coordinates for this position.
	 * @throws	IllegalArgumentException
	 *				The given coordinates are not three-dimensional.
	 *				| ( ! isValidArray(doubleCoordinates))
	 */
	public void setCoordinates(double[] doubleCoordinates) throws IllegalArgumentException{
		if ( ! isValidArray(doubleCoordinates)) {
			throw new IllegalArgumentException("The position of a unit should be three-dimensional.");
		}
		
		if (! canHaveAsCoordinates(doubleCoordinates)){
			throw new IllegalArgumentException(Arrays.toString(doubleCoordinates));
		}
		this.coordinates = doubleCoordinates;
	}
	
	/**
	 * Sets the coordinates of this position.
	 * 
	 * @param cubeCoordinates
	 *				The cube coordinates to set this position's coordinates to.
	 *
	 * @effect	Sets the coordinates if this poition to the given coordinates
	 *				| new.coordinates = cubeCoordinates
	 */
	public void setCoordinates(int[] cubeCoordinates) throws IllegalArgumentException {
		this.setCoordinates(getCubeCenter(cubeCoordinates));
	}
	
	/**
	 * Returns the coordinates of this position.
	 */
	public double[] getCoordinates(){
		return this.coordinates;
	}
	
	/**
	 * A variable that stores the coordinates of this position.
	 */
	protected double[] coordinates = new double[3];
	
	/**
	 * Checks whether the given array is a valid array which can be used 
	 * by the other methods of this class.
	 * 
	 * @param	array
	 *				The array to check.
	 *
	 * @return	True if the size of this array is exactly 3.
	 *				| result == (array.length == 3)
	 */
	protected static boolean isValidArray(double[] array) {
		return (array == null) ||(array.length == 3);
	}
	
	/**
	 * Checks whether the given array is a valid array which can be used 
	 * by the other methods of this class.
	 * 
	 * @param	array
	 *				The array to check.
	 *
	 * @return	True if the size of this array is exactly 3.
	 *				| result == (array.length == 3)
	 */
	protected static boolean isValidArray(int[] array) {
		return (array == null) ||(array.length == 3);
	}
	
	/**
 	 * Returns the coordinates of the cube that hold the given exact coordinates.
	 * Cube coordinates correspond to the exact coordinates, rounded down to the nearest integer.
 	 * 
 	 * @param coordinates
 	 *				The exact coordinates to get the cube coordinates for.
 	 * 
 	 * @return	The cube coordinates which hold the given coordinates.
 	 *				| for each coordinate in coordinates:
 	 *				|	cubeCoordinates[coordinate] == (int) Math.floor(cubeCoordinates[coordinate])
 	 *
 	 * @throws	IllegalArgumentException
 	 *				The given coordinates are not three-dimensional.
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
	 * Returns the coordinates of the cube corresponding to the exact coordinates of this position.
	 * 
	 * @return	The cube coordinates, given as an array of 3 integers.
	 *				| result ==  getCubeCoordinates(this.getCoordinates())
	 */
	@Raw
	public int[] getCubeCoordinates() throws IllegalArgumentException {
		return getCubeCoordinates( this.getCoordinates());
	}
	
	/**
 	 * Returns the coordinates of the center of the given cube.
 	 * 
 	 * @param	cubeCoordinates
 	 *				The given cube coordinates.
 	 *
 	 * @return	The coordinates of the center of the cube.
 	 *				| for each coordinate in cubeCenter:
 	 *				|	cubeCenter[coordinate] == Math.floor(cubeCoordinates[coordinate]) + Cube.CUBE_LENGHT / 2.0;
 	 *
 	 * @throws	IllegalArgumentException
 	 *				The given coordinates are not three-dimensional.
 	 *				| ! (isValidArray(cubeCoordinates))
 	 */
 	public static double[] getCubeCenter(int[] cubeCoordinates) throws IllegalArgumentException {
 		if (! isValidArray(cubeCoordinates))
			throw new IllegalArgumentException(Arrays.toString(cubeCoordinates));
 		double[] cubeCenter = new double[3];
 		for (int count = 0; count < 3; count++) {
 			cubeCenter[count] = Math.floor(cubeCoordinates[count]) + Cube.CUBE_LENGHT / 2.0;
 		}
 		return cubeCenter;
 	}

 	/**
 	 * Returns the coordinates of the center of the cube which holds the given coordinates.
 	 * 
 	 * @param	coordinates
 	 *				The given coordinates.
 	 *
 	 * @return	The coordinates of the center of the cube of the given coordinates.
 	 *				| getCubeCenter(getCubeCoordinates(coordinates))
 	 *
 	 * @throws	IllegalArgumentException
 	 *				The given coordinates are not three-dimensional.
 	 *				| ! (isValidArray(coordinates))
 	 */
 	public static double[] getCubeCenter(double[] coordinates) throws IllegalArgumentException {
 		if (! isValidArray(coordinates))
			throw new IllegalArgumentException(Arrays.toString(coordinates));
 		return (getCubeCenter(getCubeCoordinates(coordinates)));
 	}
 	
 	/**
 	 * Returns the coordinates of the center of the cube corresponding to the current coordinates of this position.
 	 * 
 	 * @return	The coordinates of the center of the cube corresponding to the current coordinates of this position.
 	 *				| result == getCubeCenter(getcoordinates())
 	 */
 	public double[] getCubeCenter() throws IllegalArgumentException {
 		return getCubeCenter(this.getCoordinates());
 	}
 	
 	/**
 	 * Returns the coordinates of the center of the cube which holds the given coordinates in the XY-plane.
 	 * The z-coordinate is the same as that of the given coordinates.
 	 * 
 	 * @param	coordinates
 	 *				The given coordinates
 	 *
 	 * @return	Coordinates at the same z-level as the given coordinates,
 	 *				and the x and y-coordinate equal to the center of the occupied cube.
 	 *				| result == {this.getCubeCenter(coordinates)[0],this.getCubeCenter(coordinates)[1],coordinates[2]});	
 	 *
 	 * @throws	IllegalArgumentException
 	 *				The given coordinates are not three-dimensional.
 	 *				| ! (isValidArray(coordinates))		
 	 */
 	public static double[] getSurfaceCenter(double[] coordinates) throws IllegalArgumentException {
 		if (! isValidArray(coordinates))
			throw new IllegalArgumentException(Arrays.toString(coordinates));
 		double[] cubeCenter = getCubeCenter(coordinates);
 		return (new double[]{cubeCenter[0],cubeCenter[1],coordinates[2]});
 	}
 	
 	/**
 	 * Returns the coordinates of the center of the cube which holds the exact coordinates of this position in the XY-plane.
 	 * The z-coordinate is the same as that of the given coordinates.
 	 * 
 	 * @return	The coordinates of the center of the cube corresponding to the coordinates.
 	 *					| result == getSurfaceCenter(getCoordinates())
 	 */
 	public double[] getSurfaceCenter() throws IllegalArgumentException {
 		return getSurfaceCenter(this.getCoordinates());
 	}
 	
 	/**
 	 * Returns the next coordinates for a given velocity and time interval.
 	 * 
 	 * @param	coordinates
 	 *				The original coordinates.
 	 * @param	velocity
 	 *				The velocity to move with.
 	 * @param	deltaT
 	 *				The time interval to advance time for..
 	 *
 	 * @return	The next coordinates, calculated as the velocity times deltaT, added to the original coordinates.
 	 *				| for element in nextCoordinates
 	 *				|	nextCoordinates[element] = coordinates[element] + velocity[element] * deltaT;
 	 *
 	 * @throws	IllegalArgumentException
 	 *				The given coordinates are not three-dimensional.
 	 *				| ! (isValidArray(coordinates))
 	 * @throws	IllegalArgumentException
 	 *				The given velocity is not three-dimensional.
 	 *				| ! (isValidArray(velocity))		
 	 */
 	public static double[] calculateNextCoordinates(double[] coordinates, double[] velocity, double deltaT) throws IllegalArgumentException {
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
 	 * 
 	 * @param	coordinates1
 	 *				The first coordinates.
 	 * @param	coordinates2
 	 *				The second coordinates.
 	 *
 	 * @return	The distance based on the mathematical formula of distance in 3 dimensions.
 	 *				| result == Math.sqrt(Math.pow(coordinates1[0] - coordinates2[0], 2)
 	 *				| +Math.pow(coordinates1[1] - coordinates2[1], 2)
 	 *				| +Math.pow(coordinates1[2] - coordinates2[2], 2));
 	 *
 	 * @throws	IllegalArgumentException
 	 *				The given coordinates 1 are not three-dimensional.
 	 *				| ! (isValidArray(coordinates1))
 	 * @throws	IllegalArgumentException
 	 *				The given coordinates 2 are not three-dimensional.
 	 *				| ! (isValidArray(coordinates2))
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
	 * Checks whether two cube coordinates are next to each other, or the same.
	 * 
	 * @param	coordinates1
	 *				The coordinates of the first cube.
	 * @param	coordinates2
	 *				The coordinates of the second cube.
	 *
	 * @return	True if and only if the difference between coordinates 
	 *				is less than or equal to 1 for each coordinate.
	 *				| result == (Math.abs(coordinates1[0] - coordinates2[0]) <= 1) &&
	 *				| (Math.abs(coordinates1[1] - coordinates2[1]) <= 1) &&
	 *				| (Math.abs(coordinates1[2] - coordinates2[2]) <= 1)
	 *
	 * @throws	IllegalArgumentException
 	 *					The given coordinates 1 are not three-dimensional.
 	 *					| ! (isValidArray(coordinates1))
 	 * @throws	IllegalArgumentException
 	 *					The given coordinates 2 are not three-dimensional.
 	 *					| ! (isValidArray(coordinates2))
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
	 *				The first coordinates.
	 * @param	coordinates2
	 *				The second coordinates.
	 * @param	speed
	 *				The magnitude of the velocity.
	 *
	 * @return	If the two coordinates are almost the same, return an array of zeros.
	 *				| if ( Unit.fuzzyEquals(this.getTargetCoordinates(), this.getCoordinates()) ) 
	 *				|	then return {0,0,0}
	 *				Else, the velocity based on the mathematical formula using the given speed.
	 *				| result == {(coordinates2[0]-coordinates1[0])/(getDistance(coordinates1, coordinates2)) * speed,
	 *				| 	(coordinates2[1]-coordinates1[1])/(getDistance(coordinates1, coordinates2)) * speed,
	 *				| 	(coordinates2[2]-coordinates1[2])/(getDistance(coordinates1, coordinates2)) * speed}
	 *
	 * @throws	IllegalArgumentException
 	 *				The given coordinates 1 are not three-dimensional.
 	 *				| ! (isValidArray(coordinates1))
 	 * @throws	IllegalArgumentException
 	 *				The given coordinates 2 are not three-dimensional.
 	 *				| ! (isValidArray(coordinates2))
	 */
	public static double[] getVelocity(double[] coordinates1, double[] coordinates2, double speed) throws IllegalArgumentException {
		if (! isValidArray(coordinates1))
			throw new IllegalArgumentException(Arrays.toString(coordinates1));
 		if (! isValidArray(coordinates2))
 			throw new IllegalArgumentException(Arrays.toString(coordinates2));
		
		// If target and current are the same, in the velocity formula you get a division by zero.
		if (equals(coordinates1, coordinates2))
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
	 * Returns a set of all cube coordinates which are at most r cubes away from
	 * the given coordinates. The given coordinates are also included.
	 * 
	 * @param	coordinates
	 *				The given coordinates.
	 * @param	r
	 *				The given range.
	 *
	 * @return	A set of coordinatess that are at most r away from the given coordinates (inclusif).
	 *				| for all newCoordinates in result:
	 *				|		newCoordinates[0] = coordinates[0] +/- (0..r) &&
	 *				|		newCoordinates[1] = coordinates[1] +/- (0..r) &&
	 *				|		newCoordinates[2] = coordinates[2] +/- (0..r)
	 *
	 * @throws	IllegalArgumentException
	 *					The given coordinates are not three-dimensional.
	 *					| ! (isValidArray(coordinates))
	 * @throws	IllegalArgumentException	
	 *					The given range is negative.
	 *					| r < 0
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
	 * Returns the x-coordinate of this position.
	 */
	@Basic @Raw
	public double getXCoordinate() {
		return this.getCoordinates()[0];
	}
	
	/**
	 * Checks whether the given x-coordinate is a valid x-coordinate for this position.
	 *  
	 * @param	x-coordinate
	 *				The x-coordinate to check.
	 *
	 * @return	True if and only if the given value is higher than or equal to the minimal coordinate value
	 *				and less than the maximal value.
	 *				| result == ((xCoordinate >= World.CUBE_COORDINATE_MIN) && (xCoordinate < this.getWorld().getMaximumXValue()))
	*/
	@Raw
	public boolean canHaveAsXCoordinate(double xCoordinate) {
		return ((xCoordinate >= World.CUBE_COORDINATE_MIN) && (xCoordinate < this.getWorld().getMaximumXValue()));
	}
	
	/**
	 * Sets the x-coordinate of this position to the given x-coordinate.
	 * 
	 * @param	xCoordinate
	 *				The new x-coordinate for this unit.
	 *
	 * @post	The x-coordinate of this position is equal to the given x-coordinate.
	 *				| new.getXCoordinate() == xCoordinate
	 *
	 * @throws	IllegalArgumentException
	 *				The given x-coordinate is not a valid x-coordinate for any unit.
	 *				| ! isValidXCoordinate(getXCoordinate())
	 */
	@Raw
	public void setXCoordinate(double xCoordinate)  throws IllegalArgumentException {
		if (! canHaveAsXCoordinate(xCoordinate))
			throw new IllegalArgumentException("X Coordinate is outside the allowed range!");
		
		this.coordinates[0] = xCoordinate;
	}
	
	/**
	 * Returns the y-coordinate of this position.
	 */
	@Basic @Raw
	public double getYCoordinate() {
		return this.getCoordinates()[1];
	}
	
	/**
	 * Checks whether the given y-coordinate is a valid y-coordinate for this position.
	 *  
	 * @param	y-coordinate
	 *				The y-coordinate to check.
	 *
	 * @return True if and only if the given value is higher than or equal to the minimal coordinate value
	 *				and less than the maximal value.
	 *				| result == ((yCoordinate >= World.CUBE_COORDINATE_MIN) && (yCoordinate < this.getWorld().getMaximumYValue())) 
	*/
	@Raw
	public boolean canHaveAsYCoordinate(double yCoordinate) {
		return ((yCoordinate >= World.CUBE_COORDINATE_MIN) && (yCoordinate < this.getWorld().getMaximumYValue()));
	}
	
	/**
	 * Sets the y-coordinate of this position to the given y-coordinate.
	 * 
	 * @param	yCoordinate
	 *				The new y-coordinate for this position.
	 *
	 * @post	The y-coordinate of this position is equal to the given y-coordinate.
	 *				| new.getYCoordinate() == yCoordinate
	 *
	 * @throws	IllegalArgumentException
	 *				The given y-coordinate is not a valid y-coordinate for any unit.
	 *				| ! isValidYCoordinate(getYCoordinate())
	 */
	@Raw
	public void setYCoordinate(double yCoordinate) throws IllegalArgumentException {
		if (! canHaveAsYCoordinate(yCoordinate))
			throw new IllegalArgumentException("Y Coordinate is outside the allowed range!");
		this.coordinates[1] = yCoordinate;
	}
	
	/**
	 * Returns the z-coordinate of this position.
	 */
	@Basic @Raw
	public double getZCoordinate() {
		return this.getCoordinates()[2];
	}
	
	/**
	 * Checks whether the given z-coordinate is a valid z-coordinate for this position.
	 *  
	 * @param	z-coordinate
	 *         			The z-coordinate to check.
	 *         
	 * @return	True if and only if the given value is higher than or equal to the minimal coordinate value
	 *				and less than the maximal value.
	 *				| result == ((zCoordinate >= World.CUBE_COORDINATE_MIN) && (zCoordinate < this.getWorld().getMaximumZValue())) 
	 */     
	@Raw
	public boolean canHaveAsZCoordinate(double zCoordinate) {
		return ((zCoordinate >= World.CUBE_COORDINATE_MIN) && (zCoordinate <this.getWorld().getMaximumZValue()));
	}
	
	/**
	 * Sets the z-coordinate of this position to the given z-coordinate.
	 * 
	 * @param	zCoordinate
	 *				The new z-coordinate for this position.
	 *
	 * @post	The z-coordinate of this position is equal to the given z-coordinate.
	 *				| new.getZCoordinate() == zCoordinate
	 *
	 * @throws IllegalArgumentException
	 *				The given z-coordinate is not a valid z-coordinate for this position.
	 *				| ! isValidZCoordinate(getZCoordinate())
	 */
	@Raw
	public void setZCoordinate(double zCoordinate) throws IllegalArgumentException {
		if (! canHaveAsZCoordinate(zCoordinate))
			throw new IllegalArgumentException("Z Coordinate is outside of the allowed range!");
		this.getCoordinates()[2]= zCoordinate;
	}
	
	/**
	 * Returns the world of this position.
	 */
	public World getWorld(){
		return this.world;
	}
	
	/**
	 * Returns whether the given world is a valid world for any position.
	 * 
	 * @param	world
	 *				The world to check.
	 *
	 * @return	The given world is an active world.
	 *				| result == (world != null)
	 */
	private static boolean isValidWorld(World world) {
		return world != null;
	}
	
	/**
	 * A variable that stores the world of this position.
	 */
	private final World world;
	
	/**
	 * Checks whether two given cube coordinates are the same by comparing each of their coordinates.
	 * 
	 * @param	coordinates1
	 *				The coordinates of the first cube.
	 * @param	coordinates2
	 *				The coordinates of the second cube.
	 *
	 * @return	True if all coordinates of coordinates1 and coordinates2 are the same.
	 * 
	 * @throws	IllegalArgumentException
 	 *				The given coordinates 1 are not three-dimensional.
 	 *				| ! (isValidArray(coordinates1))
 	 * @throws	IllegalArgumentException
 	 *				The given coordinates 2 are not three-dimensional.
 	 *				| ! (isValidArray(coordinates2))
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
	 * Checks whether two given coordinates are the same by comparing each of their coordinates.
	 * 
	 * @param coordinates1
	 *				One of the coordinates to compare.
	 * @param coordinates2
	 *				The other coordinates to compare.
	 *
	 * @return	True if all coordinates are nearly the same.
	 *				| result == (Util.fuzzyEquals(coordinates1[0], coordinates2[0])) &&
	 *				|	(Util.fuzzyEquals(coordinates1[1], coordinates2[1])) &&
	 *				|	(Util.fuzzyEquals(coordinates1[2], coordinates2[2])) 
	 *
	 * @throws IllegalArgumentException
	 *				The given coordinates1 are not three-dimensional.
	 *				| isValidArray(coordinates1)
	 * @throws IllegalArgumentException
	 *				The given coordinates2 are not three-dimensional.
	 *				| isValidArray(coordinates2)
	 */
	public static boolean equals(double[] coordinates1, double[] coordinates2) throws IllegalArgumentException {
		if (! isValidArray(coordinates1))
			throw new IllegalArgumentException(Arrays.toString(coordinates1));
 		if (! isValidArray(coordinates2))
 			throw new IllegalArgumentException(Arrays.toString(coordinates2));
		return ( Util.fuzzyEquals(coordinates1[0], coordinates2[0]) && Util.fuzzyEquals(coordinates1[1], coordinates2[1])
				&& Util.fuzzyEquals(coordinates1[2], coordinates2[2]) );
	}
	
	/**
	 * Determines whether 2 given coordinates are the same by comparing each of their coordinates.
	 * 
	 * @param position1
	 *				One of the positions to compare.
	 * @param position2
	 *				The other position to compare.
	 *
	 * @return True if the coordinates of the the two given positions are equal and the world of the two positions is equal.
	 *				| result == (equals(position1.getCoordinates(),position2.getCoordinates()) && (position1.getWorld() == position2.getWorld()))
	 */
	public static boolean equals(Position position1, Position position2) throws IllegalArgumentException {
		return (equals(position1.getCoordinates(),position2.getCoordinates())
				&& (position1.getWorld() == position2.getWorld()));
	}
	
}
