package hillbillies.model;

import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.util.Position;
import ogp.framework.util.Util;

/**
 * A class of logs, involving a world, weight and coordinates.
 * 
 * @author Sander Mergan, Thomas Vranken     
 * @version 2.5
 */

public class Log extends Item {
	
	// Exception gooien want dat wordt er ook gedaan in Entity als de gegeven weight negatief is.
	/**
	 * Initialize this new log with the given world, coordinates and weight.
	 * 
	 * @param	world
	 * 				The world of this new log.
	 * @param	coordinates
	 * 				The coordinates of this new log.
	 * @effect	The world of this log is set to the given world.
	 * @effect	The coordinates of this log is set to the given coordinates.
	 * @post	The weight of this log is set to the given weight.
	 * @throws	IllegalArgumentException
	 * 			- The world is not a valid world for this log, or
	 * 			- The position is not valid for this log in the given world.
	 */
	public Log(World world, int[] coordinates, int weight) throws IllegalArgumentException{
		super(world, coordinates,weight);
	}
	
	/**
	 * Initializes this new log with given world and cube coordinates and
	 * a random weight between 10 and 50 inclusively.
	 * 
	 * @param	world
	 * 				The world of this new log.
	 * @param	coordinates
	 * 				The coordinates of this new log.
	 * @effect	Creates a new log with given attributes and
	 * 				a random valid weight. 
	 * 				The log is placed in the center of the given cube.
	 */
	public Log(World world, int[] coordinates) {
		super(world, coordinates, new Random().nextInt(41)+10);
	}
}
