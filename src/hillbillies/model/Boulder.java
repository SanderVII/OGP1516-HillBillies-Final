package hillbillies.model;

import java.util.Random;

/**
 * A class of boulders, involving a world, weight and position.
 *
 * @author Sander Mergan, Thomas Vranken
 * @version 3.0
 */

public class Boulder extends Item{
	
	/**
	 * Initializes this new boulder with the given world, coordinates and weight.
	 * 
	 * @param	world
	 *				The world of this new boulder.
	 * @param	coordinates
	 *				The coordinates of this new boulder.
	 *
	 * @effect	Initializes this boulder with the given world, coordinates and weight and adds this new boulder to the world.
	 *				| super(world, coordinates, weight)
	 *				| world.addEntity(this)
	 */
	public Boulder(World world, int[] coordinates, int weight) throws IllegalArgumentException{
		super(world, coordinates,weight);
		world.addEntity(this);
	}
		
	/**
	 * Initializes this new boulder with given world and cube coordinates and
	 * a random weight bewteen 10 and 50 inclusively.
	 * 
	 * @param	world
	 *				The world of this new boulder.
	 * @param	coordinates
	 *				The coordinates of this new boulder.
	 *
	 * @effect	Creates a new boulder with the given world and coordinates and a random valid weight. 
	 *				| this(world, coordinates, new Random().nextInt(41)+10)
	 *				| world.addEntity(this)
	 */
	public Boulder(World world, int[] coordinates) {
		this(world, coordinates, new Random().nextInt(41)+10);
		world.addEntity(this);
	}
}
