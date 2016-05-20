package hillbillies.model;

import java.util.Random;

/**
 * A class of logs, involving a world, weight and position.
 * 
 * @author Sander Mergan, Thomas Vranken     
 * @version 3.0
 */

public class Log extends Item {
	
	/**
	 * Initializes this new log with the given world, coordinates and weight.
	 * 
	 * @param	world
	 *				The world of this new log.
	 * @param	coordinates
	 *				The coordinates of this new log.
	 *
	 * @effect	Initializes this log with the given world, coordinates and weight and adds this new log to the world.
	 *				| super(world, coordinates, weight)
	 *				| world.addEntity(this)
	 */
	public Log(World world, int[] coordinates, int weight) throws IllegalArgumentException{
		super(world, coordinates,weight);
		world.addEntity(this);
	}
	
	/**
	 * Initializes this new log with given world and cube coordinates and
	 * a random weight bewteen 10 and 50 inclusively.
	 * 
	 * @param	world
	 *				The world of this new log.
	 * @param	coordinates
	 *				The coordinates of this new log.
	 *
	 * @effect	Creates a new log with the given world and coordinates and a random valid weight. 
	 *				| this(world, coordinates, new Random().nextInt(41)+10)
	 *				| world.addEntity(this)
	 */
	public Log(World world, int[] coordinates) {
		this(world, coordinates, new Random().nextInt(41)+10);
		world.addEntity(this);
	}
}
