package hillbillies.model;

import be.kuleuven.cs.som.annotate.*;
import hillbillies.positions.Position;
import ogp.framework.util.Util;

/**
 * A class of entities involving a world and a position.
 * 
 * @invar	This entity must have a proper world.
 *				| hasProperWorld(this)
 * @invar	The position of this entity is a valid position for this entity.
 *				| canHaveAsPosition(this.getPosition())
 * 
 * @author	Sander Mergan, Thomas Vranken
 * @version	2.3
 */
// world heeft een set of entity en legt losse voorwaarden op aan entities. Specifieke voorwaarden worden
// door de subentity zelf uitgewerkt met canHaveAs.
public abstract class Entity {
	
	/**
	 * Initializes this new entity with given world, coordinates and weight.
	 * 
	 * @param	world
	 *				The given world of this new entity.
	 * @param	coordinates
	 *				The coordinates of this new entity.
	 */
	protected Entity(World world, int[] coordinates, int weight) throws IllegalArgumentException, IllegalStateException{
		this.setWorld(world);
		this.setPosition(new Position(world, coordinates));
		this.setWeight(weight);
	}
	
	/**
	 * Initializes this new entity with given world and cube coordinates.
	 * The unit is placed in the center of the cube.
	 * 
	 * @param	world
	 *				The given world of this new entity.
	 * @param	cubeCoordinates
	 *				The cubeCoordinates of this new entity.
	 *
	 * @throws	IllegalArgumentException
	 *				The given position is invalid.
	 */
	protected Entity(World world, int[] cubeCoordinates) {
		this.setWorld(world);
		this.setPosition(new Position(world, Position.getCubeCenter(cubeCoordinates)));
	}
	
	// ==========================================================================================
	// Methods concerning the termination of this entity.
	// ==========================================================================================
	
	/**
	 * Terminates this entity.
	 *
	 * @post	This entity  is terminated.
	 *				| new.isTerminated()
	 * @effect	The world of this entity is set to null and this entity is removed from its world.
	 *				| if (this.getWorld != null)
	 *				|	then ( this.getWorld().removeEntity(this); this.setWorld(null) )
	 */
	public void terminate() {
		if (! this.isTerminated()) {
			if (this.getWorld() != null) {
				this.getWorld().removeEntity(this);
				this.setWorld(null);
			}
		}
		this.isTerminated = true;
	 }
	 
	/**
	 * Returns a boolean indicating whether or not this entity  is terminated.
 	 */
	@Basic @Raw
	public boolean isTerminated() {
		return this.isTerminated;
	}
	 
	 /**
	  * A variable that stores whether this entity is terminated.
	  */
	 protected boolean isTerminated = false;
	 
	
	// ==========================================================================================
	// Methods concerning the world. (bidirectional association)
	// ==========================================================================================
	
	/**
	 * A variable that stores the world to which this entity is attached.
	 */
	protected World world;

	/**
	 * Returns the world this entity is part of.
	 */
	@Basic @Raw
	public World getWorld() {
		return this.world;
	}
	
	/**
	 * Checks whether this entity can have the given world as its world.
	 * 
	 * @param	world
	 *				The world to check.
	 *
	 * @return	False if and only if this entity is terminated and its world is not null at the same time.
	 */
	/* NOTE: items can be bound to a null world. According to the Liskov substitution principle,
	entities then also have to support this feature. */
	public boolean canHaveAsWorld(World world) {
		if (this.isTerminated())
			return world == null;
		else
			return true;
	}
	
	/**
	 * Checks whether this entity has a proper world to which it is attached.
	 * 
	 * @return	True if and only if this entity can have the world to which it
	 *				is attached as its world, and if that world is either not
	 *				effective or has this entity as one of its entities.
	 */
	public boolean hasProperWorld() {
		return ( this.canHaveAsWorld(this.getWorld()) 
				&& ( ( this.getWorld() == null) || (this.getWorld().hasAsEntity(this)) ) );
	}
	
	/**
	 * Sets the world to which this entity is attached to to the given world.
	 * 
	 * @param	world
	 *				The world to attach this entity to.
	 *
	 * @post	This entity references the given world as the world to which it is attached.
	 *				| new.getWorld() == world
	 *
	 * @throws	IllegalArgumentException
	 *				This entity cannot have the given world as its world.
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
	 * Returns the coordinates of the cube this entity is in.
	 * 
	 * @return	The cube coordinates, given as an array of 3 integers.
	 * 					| result ==  Position.getCubeCoordinates(this.getPosition().getCoordinates())
	 */
	@Raw
	public int[] getCubeCoordinates(){
		return (Position.getCubeCoordinates(this.getPosition().getCoordinates()));
	}
	
	/**
 	 * Returns the position of the center of the cube this entity is in.
 	 * 
 	 * @return	The coordinates of the center of the cube this entity occupies.
 	 * 				| result == getCubeCenter(getPosition().getCoordinates())
 	 */
 	public double[] getCubeCenter() {
 		return (Position.getCubeCenter(this.getPosition().getCoordinates()));
 	}
	
	/**
	 * Returns the position of this entity.
	 */
	@Basic @Raw
	public Position getPosition() {
		return this.position;
	}
	
	/**
	 * Checks whether the given position is a valid position for this entity.
	 *  
	 * @param	position
	 *				The position to check.
	 *         
	 * @return True if the world of this entity and the given position are the same.  
	*/
	public boolean canHaveAsPosition(Position position) {
		if (position == null)
			return false;
		return ( (this.getWorld() == position.getWorld()) && this.canHaveAsCoordinates(position.getCoordinates()));
	}
	
	/**
	 * Checks whether the given cube coordinates are valid for this entity.
	 *  
	 * @param	coordinates
	 *				The cube coordinates to check.
	 *
	 * @return	True if the cube coordinates are within the world boundaries
	 *				of this entity, and the corresponding cube is passable. 
	*/
	public boolean canHaveAsCoordinates(int[] coordinates) {
		return this.getWorld().canHaveAsCoordinates(coordinates) && 
				this.getWorld().getCube(coordinates).isPassable();
	}
	
	/**
	 * Checks whether the given coordinates are valid for this entity.
	 *  
	 * @param	coordinates
	 *				The coordinates to check.
	 *
	 * @return	True if the corresponding cube coordinates are valid.
	*/
	public boolean canHaveAsCoordinates(double[] coordinates) {
		return canHaveAsCoordinates(Position.getCubeCoordinates(coordinates));
	}
	
	/**
	 * Sets the position of this entity to the given position.
	 * 
	 * @param	position
	 *				The new position for this entity.
	 *
	 * @post	The position of this new entity is equal to the given position.
	 *				| new.getPosition() == position
	 *
	 * @throws	IllegalArgumentException
	 *				The given position is not a valid position for any entity.
	 *				| ! canHaveAsPosition(getPosition())
	 */
	@Raw
	protected void setPosition(Position position) 
			throws IllegalArgumentException {
		if (! canHaveAsPosition(position))
			throw new IllegalArgumentException();
		this.position = position;
	}
	
	/**
	 * A variable that stores the position of this entity.
	 */
	protected Position position;
	
	
	// ==========================================================================================
	// Methods concerning the falling behavior.
	// ==========================================================================================
	
  	/**
   	 * A variable that stores if the entity is falling.
   	 */
	protected boolean isFalling = false;

	/**
	 * A symbolic constant denoting the velocity of an entity when falling.
	 */
	public static double[] FALL_VELOCITY = {0,0,-3.0};
	
	/**
	 * Returns the is-falling property of this entity.
	 */
	@Basic @Raw
	public boolean getIsFalling() {
		return this.isFalling;
	}
	
	/**
	 * Set the is-falling property of this entity to the given is-falling property.
	 * 
	 * @param	isFalling
	 *				The new is-falling property for this entity.
	 *
	 * @post	The is-falling property of this entity is equal to the given is-falling property.
	 *				| new.getIsFalling() == isFalling
	 */
	@Raw
	// NOTE: keep this method protected. Use the methods initiateFalling en endFalling
	// to start/stop falling when necessary.
	protected void setIsFalling(boolean isFalling) {
		this.isFalling = isFalling;
	}
	
	/**
	 * Checks whether this entity is falling.
	 * 
	 * @return	True if the cube below this entity's cube is not solid,
	 *				or if the is-falling property is true.
	 *				False if the z-coordinate of this entity is 0.
	 *				| if (isFalling)
	 *				|	then result == true
	 *				| else if (Util.fuzzyEquals(getPosition()[2], 0))
	 *				|	then result == false
	 *				| else
	 *				| 	result == (this.getWorld().getCubeBelow(this.getPosition()).isPassable())
	 */
	protected boolean isFalling() throws IllegalArgumentException {
		if (this.getIsFalling())
			return true;
		else if (this.getPosition().getCubeCoordinates()[2] == 0)
			return false;
		else
			return (this.getWorld().getCubeBelow(this.getPosition().getCubeCoordinates()).isPassable());
	}
	
	/**
	 * Initiates the falling behavior of this entity if possible.
	 * If the entity does not fullfill the requirements to initiate it, nothing happens.
	 * If this entity is already falling, nothing happens.
	 * 
	 * @post	If this entity fullfills the requirements to fall,
	 *				the is-falling property of this entity is set to true.
	 * 
	 * @throws	IllegalStateException
	 *				This entity is terminated.
	 */
	public void initiateFalling() {
		if (this.isTerminated())
			throw new IllegalStateException();
		if (this.isFalling() && (! this.getIsFalling()))
			this.setIsFalling(true);
	}
	
	/**
	 * Checks whether this entity fullfills the requirements to stop falling.
	 * These requirements are:
	 *		- The entity must occupy a passable cube above a solid cube.
	 *		- The z-coordinate of this entity must be less than or equal to the z-coordinate
	 *  		of the center of the passable cube.
	 *  
	 * @note	Entities fall toward the center of a cube. As such, a requirement
	 *				to end is that the entity has reached or surpassed the center of its cube.
	 *
	 * @return	True if all of the requirements are fullfilled.
	 */
	public boolean canEndFalling() {
		return ( (this.getPosition().getCubeCoordinates()[2] == 0) || (! this.getWorld().getCubeBelow(this.getPosition().getCubeCoordinates()).isPassable()))
					&& (Util.fuzzyLessThanOrEqualTo(
							this.getPosition().getZCoordinate(), 
							this.getPosition().getCubeCenter()[2]));
	}
	
	/**
	 * Ends the falling behavior of this entity if possible.
	 * If the entity does not fullfill the requirements to end it, nothing happens.
	 * 
	 * @effect	If this entity fullfills the requirements to stop falling,
	 *				The is-falling property of this entity is set to false
	 *				and the coordinates are set to the center of the cube. .
	 */
	public void endFalling() {
		if (this.canEndFalling()){
			
			this.setIsFalling(false);
			this.getPosition().setCoordinates((Position.getCubeCenter(this.getPosition().getCoordinates())));
		}
	}
	
	
	// ==========================================================================================
	// Methods concerning the weight.
	// ==========================================================================================
	
	/**
	 * Returns the weight of this entity.
	 */
	public int getWeight(){
		return this.weight;
	}
	
	/**
	 * Sets the weight of this item to the given weight.
	 * 
	 * @param	weight
	 *				The new weight of this item.
	 * 
	 * @post	The weight of this entity is equal to the given weight.
	 *				| new.getWeight() == weight
	 *
	 * @throws	IllegalStateException
	 *				This item is terminated.
	 * @throws	IllegalArgumentException
	 *				This item cannot have the given weight as its weight.
	 */
	protected void setWeight(int weight) {
		if (this.isTerminated())
			throw new IllegalStateException();
		if (! canHaveAsWeight(weight))
			throw new IllegalArgumentException();
		this.weight = weight;
		
	}

	/**
	 * Checks whether this entity can have the given weight as its weight.
	 *  
	 * @param	weight
	 *				The weight to check.
	 * @return	True if the given weight is greater than or equal to zero.
	 *				| result == (weight >= 0)
	*/
	@Raw
	public boolean canHaveAsWeight(int weight) {
		return weight >= 0;
	}
	
	/**
	 * A variable that stores the weight of this entity.
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
	 * Sets the game time of this entity to the given time.
	 * 
	 * @param	gametime
	 *				The new gametime.
	 *
	 * @post	The gametime of this entity is set to the given amount. 
	 *				| new.getGametime() == gametime
	 *
	 * @throws	IllegalArgumentException
	 *				The given gametime is not a valid gametime for any entity.
	 *				| !isValidGametime(gametime)
	 */
	protected void setGametime(double gametime) throws IllegalArgumentException{
		if ( ! this.isValidGametime(gametime)){
			throw new IllegalArgumentException("This game time is invalid, because it is to low." );
		}
		this.gametime = gametime;
	}
	
	/**
	 * Advances the game time of this entity with the given dt.
	 * 
	 * @param	dt
	 *				The time to add.
	 *
	 * @post	The gametime of this entity is increased with the given amount. 
	 *				| new.getGameTime() == getGametime() + dt
	 *
	 * @throws	IllegalArgumentException
	 *				The new gametime is not a valid gametime for any entity.
	 *				| !isValidGametime(gametime+dt)
	 */
	protected void advanceGametime(double dt) throws IllegalArgumentException{
		if ( ! this.isValidGametime(this.getGametime() + dt)){
			throw new IllegalArgumentException();
		}
		this.setGametime(this.getGametime() + dt);
	}
	
	/**
	 * Checks whether the given time is a valid gameTime. 
	 * 
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
