package hillbillies.model;

import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.util.ItemPosition;
import hillbillies.util.Position;
import ogp.framework.util.Util;

/**
 * A class of boulders, involving a world, weight and a position.
 * 
 * @invar  The target position of each boulder must be a valid target position for any
 *         boulder.
 *       | isValidTargetPosition(getTargetPosition())
 * @invar  The initial position of each boulder must be a valid initial position for any
 *         boulder.
 *       | isValidInitialPosition(getInitialPosition())
 * @author Sander Mergan, Thomas Vranken
 * @version 2.5
 */

public class Boulder extends Item{
	
	/**
	 * Initialize this new boulder with the given world, coordinates and weight.
	 * 
	 * @param	world
	 * 				The world of this new boulder.
	 * @param	coordinates
	 * 				The coordinates of this new boulder.
	 * @effect	The world of this boulder is set to the given world.
	 * @effect	The coordinates of this boulder is set to the given coordinates.
	 * @effect	The weight of this boulder is set to the given weight.
	 */
	public Boulder(World world, double[] coordinates, int weight) throws IllegalArgumentException{
		super(world, coordinates,weight);
	}
		
	/**
	 * Initializes this new log with given world and cube coordinates and
	 * a random weight bewteen 10 and 50 inclusively.
	 * 
	 * @param	world
	 * 				The world of this new log.
	 * @param	coordinates
	 * 				The coordinates of this new log.
	 * @effect	Creates a new log with given attributes and
	 * 				a random valid weight. 
	 * 				The log is placed in the center of the given cube.
	 */
	public Boulder(World world, double[] coordinates) {
		super(world, Position.getCubeCenter(coordinates), new Random().nextInt(41)+10);
	}
	
}
