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
	
	//TODO canHaveAsWeight overriden naar min/maxwaarden?
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
	public Log(World world, double[] coordinates, int weight) throws IllegalArgumentException{
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
		super(world, Position.getCubeCenter(coordinates), new Random().nextInt(41)+10);
	}
	
//	/**
//	 * Initialize this new log with the given world and position.
//	 * 
//	 * @param	world
//	 * 				The world of this new log.
//	 * @param	position
//	 * 				The position of this new log.
//	 * @effect	The world of this log is set to the given world.
//	 * @effect	The position of this log is set to the given position.
//	 * @post	The weight of this log is set to a random value between 10 and 50, inclusively.
//	 * @throws	IllegalArgumentException
//	 * 			- The world is not a valid world for this log, or
//	 * 			- The position is not valid for this log in the given world.
//	 */
//	public Log(World world, int[] position){
//		
//		this.setWorld(world);
//		
//		this.setPosition(new ItemPosition(this.getWorld(), ItemPosition.getCubeCenter(position)));
//		
//		int randomWeight = new Random().nextInt(41)+10;
//		this.setWeight(randomWeight);
//	}
	
//	/**
//	 * Sets the weight of this log to the given weight.
//	 * @param weight
//	 *				The weight to set the weight of this log to.
//	 * @post The weight of this log is equal to the given weight.
//	 *				| new.getWeight() == weight
//	 */
//	public final void setWeight(int weight){
//		this.weight = weight;
//	}
//	
//	/**
//	 * Returns the weight of this log.
//	 */
//	public int getWeight(){
//		return this.weight;
//	}
//	
//	/**
//	 * A variable referencing the weight of this log.
//	 */
//	private int weight;
//	
//	/**
//	 * Return the position of this log.
//	 */
//	@Raw @Basic
//	public ItemPosition getPosition(){
//		return this.position;
//	}
//	
//	/**
//	 * Sets the Position of this log to the given position.
//	 *  
//	 * @param	position
//	 * 					The new position of this log.
//	 * 
//	 * @post The position of this log is equal to the given position.
//	 *				| new.getPosition() == position
//	 */
//	public void setPosition(ItemPosition position) {
//		this.position = position;
//	}
//	
//	/**
//	 * A variable that references the position of this log.
//	 */
//	private ItemPosition position;
//	
//	
//	//==========================================================================================
//	// Methods concerning the world. (bidirectional association)
//	//==========================================================================================
//
//	/**
//	 * Return the world this log is part of.
//	 */
//	@Basic @Raw
//	public World getWorld() {
//		return this.world;
//	}
//	
//	/**
//	 * Checks whether this log can have the given world as its world.
//	 * 
//	 * @param 	world
//	 * 			The world to check.
//	 * @return	True if and only if the given world is not effective
//	 * 			or if it can have this log as one of its logs.
//	 * 			| result == ( (world == null) || (world.canHaveAslog(this)) )
//	 */
//	@Raw
//	public boolean canHaveAsWorld(World world) {
//		return ( (world == null) || (world.canHaveAsLog(this)) );
//	}
//	
//	/**
//	 * Checks whether this log has a proper world to which it is attached.
//	 * 
//	 * @return	True if and only if this log can have the world to which it
//	 * 			is attached as its world, and if that world is either not
//	 * 			effective or has this log as one of its logs.
//	 * 			| (this.canHaveAsWorld(this.getWorld()) 
//	 *			|  && ( (this.getWorld() == null) || (this.getWorld().hasAslog(this))) )
//	 */
//	public boolean hasProperWorld() {
//		return (this.canHaveAsWorld(this.getWorld()) 
//				&& ( ( this.getWorld() == null) || (this.getWorld().hasAsLog(this)) ) );
//	}
//	
//	// TODO Mogen die exceptions en de documentatie erbij weg?
//	/**
//	 * Sets the world to which this log is attached to to the given world.
//	 * 
//	 * @param	world
//	 * 			The world to attach this log to.
//	 * @post	This log references the given world as the world to which it is attached.
//	 * 			| new.getWorld() == world
//	 * @throws	IllegalArgumentException
//	 * 			The world is effective but does not have this log as one of its logs.
//	 * @throws	IllegalStateException
//	 * 			The given world is not effective and this log references an effective world,
//	 * 			and that world references this log as one of its logs.
//	 */
//	public void setWorld(@Raw World world) throws IllegalStateException, IllegalArgumentException {
////		if ((world != null) && ( (this.getWorld() == null) || ( ! world.hasAsLog(this))))
////			throw new IllegalArgumentException("The given world doesn't reference this log correctly yet.");
////		if ((world == null) && ( ! (this.getWorld() == null || (this.hasProperWorld()))))
////			throw new IllegalStateException("This log is not properly linked to a world.");
//			
//		this.world = world;
//	}
//	
//	/**
//	 * A variable referencing the world to which this log is attached.
//	 */
//	private World world;
//	
	//================================================================================
	// Methods concerning being carried.
	//================================================================================
	
	/**
	 * Sets whether this log is being carried by a unit.
	 * @param value
	 *				The value to set whether this log is being carried to.
	 * @post The value of this.isBeingCarried is equal to the given value
	 *				| new.isBeingCarried() == value 
	 */
	void setIsBeingCarried(boolean value){
		this.isBeingCarried = value;
	}
	
	/**
	 * Returns whether this log is being carried by a unit.
	 * 
	 * @return | result == this.isBeingCarried
	 */
	boolean isBeingCarried(){
		return this.isBeingCarried;
	}
	
	/**
	 * A variable that holds whether this boulder is being carried by a unit.
	 */
	private boolean isBeingCarried;
	
//	// ================================================================================
//	// Methods for falling.
//	// ================================================================================
//	
//	/**
//	 * Returns the target position of this log.
//	 */
//	@Basic @Raw
//	public double[] getTargetCoordinates() {
//		return this.targetCoordinates;
//	}
//	
//	/**
//	 * Check whether the given target position is a valid target position for
//	 * this log.
//	 *  
//	 * @param  target position
//	 *         The target position to check.
//	 * @return True if the target position is null, or is a valid position for this log in its world.
//	 *       | result == (targetPosition == null || isValidPosition(targetPosition))
//	*/
//	public boolean isValidTargetCoordinates(double[] targetPosition) {
//		return (targetPosition == null || this.getPosition().canHaveAsItemCoordinates(targetPosition));
//	}
//	
//	/**
//	 * Set the target position of this log to the given target position.
//	 * 
//	 * @param  targetPosition
//	 *         The new target position for this log.
//	 * @post   The target position of this new log is equal to
//	 *         the given target position.
//	 *       | new.getTargetPosition() == targetPosition
//	 * @throws IllegalArgumentException
//	 *         The given target position is not a valid target position for any
//	 *         log.
//	 *       | ! isValidTargetPosition(getTargetPosition())
//	 */
//	@Raw
//	public void setTargetCoordinates(double[] targetPosition) throws IllegalArgumentException {
//		if (! isValidTargetCoordinates(targetPosition))
//			throw new IllegalArgumentException();
//		this.targetCoordinates = targetPosition;
//	}
//	
//	/**
//	 * Variable registering the target position of this log.
//	 */
//	private double[] targetCoordinates;
//	
//	/**
//	 * Returns the initial position of this log.
//	 */
//	@Basic @Raw
//	public double[] getInitialCoordinates() {
//		return this.initialCoordinates;
//	}
//	
//	/**
//	 * Check whether the given initial position is a valid initial position for
//	 * this log.
//	 *  
//	 * @param  initial position
//	 *         The initial position to check.
//	 * @return True if the initial position is null, or is a valid position for this log in its world.
//	 *       | result == (initialPosition == null || isValidPosition(initialPosition))
//	*/
//	public boolean isValidInitialCoordinates(double[] initialPosition) {
//		return ( (initialPosition == null) || (this.getPosition().canHaveAsItemCoordinates(initialPosition)) );
//	}
//	
//	/**
//	 * Set the initial position of this log to the given initial position.
//	 * 
//	 * @param  initialPosition
//	 *         The new initial position for this log.
//	 * @post   The initial position of this new log is equal to
//	 *         the given initial position.
//	 *       | new.getInitialPosition() == initialPosition
//	 * @throws IllegalArgumentException
//	 *         The given initial position is not a valid initial position for any
//	 *         log.
//	 *       | ! isValidInitialPosition(getInitialPosition())
//	 */
//	@Raw
//	public void setInitialCoordinates(double[] initialPosition) throws IllegalArgumentException {
//		if (! isValidInitialCoordinates(initialPosition))
//			throw new IllegalArgumentException();
//		this.initialCoordinates = initialPosition;
//	}
//	
//	/**
//	 * Variable registering the initial position of this log.
//	 */
//	private double[] initialCoordinates;
//	
//   	/**
//   	 * Resets the target position and initial position of this log.
//   	 * 
//   	 * @post	The initial position and target position are both equal to null;
//   	 * 			| new.getInitialPosition() == null && new.getTargetPosition() == null
//   	 */
//   	private void resetCoordinates() {
//   		this.setTargetCoordinates(null);
//   		this.setInitialCoordinates(null);
//   	}
//	
//	/**
//	 * Return the is-falling property of this log.
//	 */
//	@Basic @Raw
//	public boolean getIsFalling() {
//		return this.isFalling;
//	}
//	
//	/**
//	 * Set the is-falling property of this log to the given is-falling property.
//	 * 
//	 * @param  isFalling
//	 *         The new is-falling property for this log.
//	 * @post   The is-falling property of this new log is equal to
//	 *         the given is-falling property.
//	 *       	| new.getIsFalling() == isFalling
//	 */
//	@Raw
//	public void setIsFalling(boolean isFalling) {
//		this.isFalling = isFalling;
//	}
//	
//  	/**
//   	 * Value used to check if the unit is falling. Works alongside
//   	 * the isFalling-method. Needed because once a unit enters a lower cube,
//   	 * it may already think it is no longer falling according to the method.
//   	 */
//	private boolean isFalling = false;
//	
//	/**
//	 * Checks if this log is falling.
//	 * 
//	 * @return	True if the cube below is not solid,
//	 * 			or if the is-falling property is true.
//	 * 			False if the z-coordinate of this log is 0.
//	 * 			| if (isFalling)
//	 * 			|	then result == true
//	 * 			| else if (Util.fuzzyEquals(getZCoordinate(), 0))
//	 *			|	then result == false
//	 * 			| else
//	 * 			| 	result == (this.getWorld().getCubeBelow(this.getPosition()).isPassable())
//	 * @throws	IllegalArgumentException
//	 * 			The log does not occupy a valid cube.
//	 */
//	public boolean isFalling() throws IllegalArgumentException {
//		// NOTE: do not change the is-falling property in this method itself! 
//		// It will break the code (not to mention it is bad practice to return and change values in one method).
//		if (this.getIsFalling())
//			return true;
//		else if (Util.fuzzyEquals(this.getPosition().getZCoordinate(), 0))
//			return false;
//		else
//			return (this.getWorld().getCubeBelow( this.getPosition().getCoordinates() ).isPassable());
//	}
//	
//	/**
//	 * Symbolic constant denoting the velocity of a log when falling.
//	 */
//	private static final double[] FALL_VELOCITY = {0,0,-3.0};
//	
//
//	//================================================================================
//	// AdvanceTime()
//	//================================================================================
//	
//	/**
//	 * Returns the gametime of this log.
//	 */
//	@Basic @Raw
//	public double getGametime(){
//		return this.gametime;
//	}
//	
//	/**
//	 * Set the game time of this log to the given gametime.
//	 * 
//	 * @param  gametime
//	 *         			The new gametime for this log.
//	 * @post   The new gametime of this log is equal to the given gametime.
//	 *       			| new.getgameTime() == gametime
//	 * @throws IllegalArgumentException
//	 *         			The given gametime is not a valid gametime for any log.
//	 *       			| !isValidGametime(gametime)
//	 */
//	private void setGametime(double gametime) throws IllegalArgumentException{
//		if ( ! this.isValidGametime(gametime)){
//			throw new IllegalArgumentException("This game time is invalid, because it was to low." );
//		}
//		this.gametime = gametime;
//	}
//	
//	/**
//	 * Checks whether the given gameTime is a valid gameTime. 
//	 * @return The given gameTime is greater than or equal to the current gameTime.
//	 *				| result == gameTime >= this.getGameTime
//	 */
//	@Raw
//	public boolean isValidGametime(double gameTime){
//		return (gameTime >= this.getGametime()) ;
//	}
//	
//	/**
//	 * A variable that stores the time past in the game since the creation of this unit.
//	 */
//	private double gametime=0;
//	
//	// No formal documentation required.
//	/**
//	 * Advances the game time for this log.
//	 * 
//	 * @param deltaT
//	 * 			The time to increase the game time with.
//	 */
//	public void advanceTime(double deltaT) {
//		if  (deltaT < 0)
//			throw new IllegalArgumentException("This deltaT is invalid, because it is negative.");
//		if  (deltaT > 0.2)
//			throw new IllegalArgumentException("This deltaT is invalid, because it is to big.");
//		// Advance the gametime.
//		this.setGametime(this.getGametime() + deltaT);
//		
//		// special case: the log is falling.
//		if ( (this.isFalling()) || (this.getIsFalling()) ) {
//			// if the is-falling property is set to false, it means falling was just initiated.
//			// in this case, the log starts from the center of the noSolidNeighbours-cube.
//			if (! this.getIsFalling()) {
//				this.getPosition().setCoordinates(ItemPosition.getSurfaceCenter(this.getPosition().getCoordinates()));
//				this.setInitialCoordinates(this.getPosition().getCoordinates());
//			}
//			this.setIsFalling(true);
//		
//			// For clarity purposes.
//			double[] initialPosition = this.getInitialCoordinates();
//			double[] newPosition = new double[3];
//			Cube targetCube = this.getWorld().getCubeBelow(initialPosition);
//
//			double[] targetPosition = ItemPosition.getCubeCenter(targetCube.getPosition().getCoordinates());
//			this.setTargetCoordinates(targetPosition);
//			
//			// Calculate the new position.
//			for (int count = 0; count < 3; count++){
//				newPosition[count] = this.getPosition().getCoordinates()[count] + Log.FALL_VELOCITY[count] * deltaT;
//			}
//
//			// The target position is reached or surpassed.
//			if (ItemPosition.getDistance(this.getTargetCoordinates(), this.getInitialCoordinates()) 
//					<= ItemPosition.getDistance(newPosition, this.getInitialCoordinates())) {
//
//				// Update initial position
//				this.setInitialCoordinates(targetPosition);
//				this.getPosition().setCoordinates(targetPosition);
//				
//				// The log has stopped falling
//				if (! this.getWorld().getCubeBelow(targetPosition).isPassable()) {
//					
//					this.getPosition().setCoordinates(this.getTargetCoordinates());
//					this.resetCoordinates();
//					this.setIsFalling(false);
//				}
//			} else {
//				this.getPosition().setCoordinates(newPosition); 				
//			}	
//		}
//	}
}
