package hillbillies.model;

import be.kuleuven.cs.som.annotate.*;
import hillbillies.util.Position;
import ogp.framework.util.Util;

/**
 * A class of entities involving a world and a position.
 * 
 * @invar	This entity must have a proper world.
 * @invar	The position of this entity is a valid position for this entity.
 * @author 	Sander Mergan, Thomas Vranken
 * @version	2.3
 */
// world heeft een set of entity en legt losse voorwaarden op aan entities. Specifieke voorwaarden worden
// door de subentity zelf uitgewerkt met canHaveAs.
public abstract class Entity {
	
	/**
	 * Initializes this new entity with given world, coordinates and weight.
	 * 
	 * @param 	world
	 * 				The given world of this new entity.
	 * @param 	coordinates
	 * 				The coordinates of this new entity.
	 * @throws	IllegalArgumentException
	 * 				The given position is invalid.
	 */
	protected Entity(World world, double[] coordinates, int weight) throws IllegalArgumentException, IllegalStateException{
		this.setWorld(world);
		this.setPosition(new Position(world, coordinates));
		this.setWeight(weight);
	}
	
	/**
	 * Initializes this new entity with given world and cube coordinates.
	 * The unit is placed in the center of the cube.
	 * 
	 * @param 	world
	 * 				The given world of this new entity.
	 * @param 	cubeCoordinates
	 * 				The cubeCoordinates of this new entity.
	 * @throws	IllegalArgumentException
	 * 				The given position is invalid.
	 */
	protected Entity(World world, int[] cubeCoordinates) {
		this.setWorld(world);
		this.setPosition(new Position(world, Position.getCubeCenter(cubeCoordinates)));
	}
	
	// ==========================================================================================
	// Methods concerning the termination of this entity.
	// ==========================================================================================
	
	/**
	 * Terminate this entity.
	 *
	 * @post	This entity  is terminated.
	 *				| new.isTerminated()
	 * @effect 	The world of this entity is set to null.
	 */
	//TODO more posts?
	public void terminate() {
		if (! this.isTerminated()) {
			if (this.getWorld() != null) {
				World world = this.getWorld();
				this.setWorld(null);
				world.removeEntity(this);
			}
		}
		this.isTerminated = true;
	 }
	 
	/**
	 * Return a boolean indicating whether or not this entity
	 * is terminated.
 	 */
	@Basic @Raw
	public boolean isTerminated() {
		return this.isTerminated;
	}
	 
	 /**
	  * Variable registering whether this entity is terminated.
	  */
	 protected boolean isTerminated = false;
	 
	
	// ==========================================================================================
	// Methods concerning the world. (bidirectional association)
	// ==========================================================================================
	
	/**
	 * A variable referencing the world to which this entity is attached.
	 */
	protected World world;

	/**
	 * Return the world this entity is part of.
	 */
	@Basic @Raw
	public World getWorld() {
		return this.world;
	}
	
	/**
	 * Checks whether this entity can have the given world as its world.
	 * 
	 * @param 	world
	 *			The world to check.
	 * @return	If this entity is terminated, true if the given world
	 * 			is not effective.
	 * 			Else, true if the given world is effective
	 * 			and not yet terminated.
	 */
	//TODO fix doc
	/* NOTE: items can be bound to a null world. According to the Liskov substitution principle,
	entities then also have to support this feature. */
	public boolean canHaveAsWorld(World world) {
		if (this.isTerminated())
			return world == null;
		else
			return true;
//		else
//			return (world != null) && (! world.isTerminated());
	}
	
	/**
	 * Checks whether this entity has a proper world to which it is attached.
	 * 
	 * @return	True if and only if this entity can have the world to which it
	 * 			is attached as its world, and if that world is either not
	 * 			effective or has this entity as one of its entities.
	 */
	public boolean hasProperWorld() {
		return ( this.canHaveAsWorld(this.getWorld()) 
				&& ( ( this.getWorld() == null) || (this.getWorld().hasAsEntity(this)) ) );
	}
	
	/**
	 * Sets the world to which this entity is attached to to the given world.
	 * 
	 * @param	world
	 * 			The world to attach this entity to.
	 * @post	This entity references the given world as the world to which it is attached.
	 * @throws	IllegalArgumentException
	 * 			This entity cannot have the given world as its world.
	 */
	protected void setWorld(@Raw World world) throws IllegalArgumentException {
		if (! this.canHaveAsWorld(world))
			throw new IllegalArgumentException();
		this.world = world;
	}
	
	
	// ==========================================================================================
	// Methods concerning the position.
	// ==========================================================================================
	
	/**
	 * Return the position of this entity.
	 */
	@Basic @Raw
	public Position getPosition() {
		return this.position;
	}
	
	/**
	 * Check whether the given position is a valid position for
	 * this entity.
	 *  
	 * @param  position
	 *         The position to check.
	 * @return The world of this entity and the given position must be the same.  
	*/
	public boolean canHaveAsPosition(Position position) {
		if (position == null)
			return false;
		return (this.getWorld() == position.getWorld());
	}
	
	/**
	 * Check whether the given cube coordinates are valid for this entity.
	 *  
	 * @param  	coordinates
	 *         	The cube coordinates to check.
	 * @return 	True if the cube coordinates are within the world boundaries
	 * 			of this entity, and the corresponding cube is passable. 
	*/
	public boolean canHaveAsCoordinates(int[] coordinates) {
		return this.getWorld().canHaveAsCoordinates(coordinates) && 
				this.getWorld().getCube(coordinates).isPassable();
	}
	
	/**
	 * Check whether the given coordinates are valid for this entity.
	 *  
	 * @param  	coordinates
	 *         	The coordinates to check.
	 * @return 	True if the corresponding cube coordinates are valid.
	*/
	public boolean canHaveAsCoordinates(double[] coordinates) {
		return canHaveAsCoordinates(Position.getCubeCoordinates(coordinates));
	}
	
	/**
	 * Set the position of this entity to the given position.
	 * 
	 * @param  position
	 *         The new position for this entity.
	 * @post   The position of this new entity is equal to
	 *         the given position.
	 *       | new.getPosition() == position
	 * @throws IllegalArgumentException
	 *         The given position is not a valid position for any
	 *         entity.
	 *       | ! canHaveAsPosition(getPosition())
	 */
	@Raw
	public void setPosition(Position position) 
			throws IllegalArgumentException {
		if (! canHaveAsPosition(position))
			throw new IllegalArgumentException();
		this.position = position;
	}
	
	/**
	 * Variable registering the position of this entity.
	 */
	protected Position position;
	
	
	// ==========================================================================================
	// Methods concerning the falling behavior.
	// ==========================================================================================
	
  	/**
   	 * Value registering if the entity is falling.
   	 */
	private boolean isFalling = false;

	/**
	 * Symbolic constant denoting the velocity of an entity when falling.
	 */
	public static double[] FALL_VELOCITY = {0,0,-3.0};
	
	/**
	 * Return the is-falling property of this entity.
	 */
	@Basic @Raw
	public boolean getIsFalling() {
		return this.isFalling;
	}
	
	/**
	 * Set the is-falling property of this entity to the given is-falling property.
	 * 
	 * @param  isFalling
	 *         The new is-falling property for this entity.
	 * @post   The is-falling property of this entity is equal to
	 *         the given is-falling property.
	 *       | new.getIsFalling() == isFalling
	 */
	@Raw
	// NOTE: keep this method protected. Use the methods initiateFalling en endFalling
	// to start/stop falling when necessary.
	protected void setIsFalling(boolean isFalling) {
		this.isFalling = isFalling;
	}
	
	/**
	 * Checks if this entity is falling.
	 * 
	 * @return	True if the cube below this entity's cube is not solid,
	 * 			or if the is-falling property is true.
	 * 			False if the z-coordinate of this entity is 0.
	 * 			| if (isFalling)
	 * 			|	then result == true
	 * 			| else if (Util.fuzzyEquals(getPosition()[2], 0))
	 *			|	then result == false
	 * 			| else
	 * 			| 	result == (this.getWorld().getCubeBelow(this.getPosition()).isPassable())
	 */
	public boolean isFalling() throws IllegalArgumentException {
		if (this.getIsFalling())
			return true;
		else if (Util.fuzzyEquals(this.getPosition().getZCoordinate(), 0))
			return false;
		else
			return (this.getWorld().getCubeBelow(this.getPosition().getCoordinates()).isPassable());
	}
	
	/**
	 * Initiates the falling behavior of this entity if possible.
	 * If the entity does not fullfill the requirements to initiate it, nothing happens.
	 * If this entity is already falling, nothing happens.
	 * 
	 * @post	If this entity fullfills the requirements to fall,
	 * 				the is-falling property of this entity is set to true.
	 * @throws	IllegalStateException
	 * 				This entity is terminated.
	 */
	public void initiateFalling() {
		if (this.isTerminated())
			throw new IllegalStateException();
		if (this.isFalling() && (! this.getIsFalling()))
			this.setIsFalling(true);
	}
	
	/**
	 * Check if this entity fullfills the requirements to stop falling.
	 * These requirements are:
	 * 	- The entity must occupy a passable cube above a solid cube.
	 *  - The z-coordinate of this entity must be less than or equal to the z-coordinate
	 *  	of the center of the passable cube.
	 *  
	 * @note	Entities fall toward the center of a cube. As such, a requirement
	 * 				to end is that the entity has reached or surpassed the center of its cube.
	 * @return	True if all of the requirements are fullfilled.
	 */
	public boolean canEndFalling() {
		return (! this.getWorld().getCubeBelow(this.getPosition().getCoordinates()).isPassable())
					&& (Util.fuzzyLessThanOrEqualTo(
							this.getPosition().getZCoordinate(), 
							this.getPosition().getCubeCenter()[2]));
	}
	
	/**
	 * End the falling behavior of this entity if possible.
	 * If the entity does not fullfill the requirements to end it, nothing happens.
	 * 
	 * @post	If this entity fullfills the requirements to stop falling,
	 * 				The is-falling property of this entity is set to false.
	 */
	public void endFalling() {
		if (this.canEndFalling())
			this.setIsFalling(false);
	}
	
	
	// ==========================================================================================
	// Methods concerning the weight.
	// ==========================================================================================
	
	/**
	 * Return the weight of this entity.
	 */
	public int getWeight(){
		return this.weight;
	}
	
	/**
	 * Set the weight of this item to the given weight.
	 * 
	 * @param	weight
	 * 				The new weight of this item.
	 * @post	The weight of this entity is equal to the given weight.
	 *				| new.getWeight() == weight
	 * @throws	IllegalStateException
	 * 				This item is terminated.
	 * @throws 	IllegalArgumentException
	 * 				This item cannot have the given weight as its weight.
	 */
	protected void setWeight(int weight) {
		// TODO als deze functie wordt opgeroepen vanuit Log, 
		//		wordt er dan de canHaveAsWeight van Log gebruikt?
		if (this.isTerminated())
			throw new IllegalStateException();
		if (! this.canHaveAsWeight(weight))
			throw new IllegalArgumentException();
		this.weight = weight;
		
	}

	/**
	 * Check whether this entity can have the given weight as its weight.
	 *  
	 * @param  weight
	 *         The weight to check.
	 * @return True if the given weight is greater than or equal to zero.
	 *       | result == (weight >= 0)
	*/
	@Raw
	public boolean canHaveAsWeight(int weight) {
		return weight >= 0;
	}
	
	/**
	 * Variable registering the weight of this entity.
	 */
	protected int weight;
	
	// ==========================================================================================
	// Methods concerning the gametime.
	// ==========================================================================================
	
	/**
	 * Returns the gametime of this entity.
	 */
	@Basic @Raw
	public double getGametime(){
		return this.gametime;
	}
	
	/**
	 * Set the game time of this entity to the given time.
	 * 
	 * @param  	gametime
	 *         		The new gametime.
	 * @post   	The gametime of this entity is set to the given amount. 
	 *       		| new.getGametime() == gametime
	 * @throws 	IllegalArgumentException
	 *         		The given gametime is not a valid gametime for any entity.
	 *       		| !isValidGametime(gametime)
	 */
	protected void setGametime(double gametime) throws IllegalArgumentException{
		if ( ! this.isValidGametime(gametime)){
			throw new IllegalArgumentException("This game time is invalid, because it is to low." );
		}
		this.gametime = gametime;
	}
	
	/**
	 * Advance the game time of this entity with the given time.
	 * 
	 * @param  	dt
	 *         		The time to add.
	 * @post   	The gametime of this entity is increased with the given amount. 
	 *       		| new.getGameTime() == getGametime() + dt
	 * @throws 	IllegalArgumentException
	 *         		The new gametime is not a valid gametime for any entity.
	 *       		| !isValidGametime(gametime+dt)
	 */
	public void advanceGametime(double dt) throws IllegalArgumentException{
		if ( ! this.isValidGametime(this.getGametime() + dt)){
			throw new IllegalArgumentException();
		}
		this.setGametime(this.getGametime() + dt);
	}
	
	/**
	 * Checks whether the given time is a valid gameTime. 
	 * @return The given gameTime is greater than or equal to the current gameTime.
	 *				| result == gameTime >= this.getGameTime
	 */
	@Raw
	public boolean isValidGametime(double gameTime){
		return (gameTime >= this.getGametime()) ;
	}
	
	/**
	 * A variable that stores the time past in the game since the creation of this unit.
	 */
	private double gametime=0;
	
	
	/**
	 * Advances the time for this entity.
	 */
	public abstract void advanceTime(double deltaT);
}
