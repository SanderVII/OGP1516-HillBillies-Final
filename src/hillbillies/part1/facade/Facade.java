package hillbillies.part1.facade;

import hillbillies.model.Unit;
import hillbillies.model.World;
import ogp.framework.util.ModelException;


/**
 * @version	2.1
 */
public class Facade implements hillbillies.part1.facade.IFacade {
	
	public Facade(){
		
	}
	
	/* Unit creation */
	/**
	 * Create a new unit with the given attributes.
	 * 
	 * @param 	name
	 *            		The name of the unit.
	 * @param 	initialCoordinates
	 *            		The initial coordinates of the unit, as an array with 3 elements
	 *            		{x, y, z}.
	 * @param 	weight
	 *            		The initial weight of the unit
	 * @param 	agility
	 *            		The initial agility of the unit
	 * @param 	strength
	 *           	 	The initial strength of the unit
	 * @param 	toughness
	 *            		The initial toughness of the unit
	 * @param 	enableDefaultBehavior
	 *            		Whether the default behavior of the unit is enabled
	 * 
	 * @return The generated unit
	 * 
	 * @throws ModelException
	 *             		A precondition was violated or an exception was thrown.
	 */
	@Override @Deprecated
	public Unit createUnit(String name, int[] initialCoordinates, int weight, int agility, int strength, int toughness,
			boolean enableDefaultBehavior) throws ModelException{
		try{
			World world = new World(new int[15][15][15], null);
			Unit unit = new Unit(world, name, initialCoordinates, weight, strength, agility, toughness);
			if (enableDefaultBehavior)
				unit.startDefaultBehavior();
			else
				unit.stopDefaultBehavior();
			return unit;
		}
		catch(IllegalArgumentException e){
			throw new ModelException();
		}
	}

	/**
	 * Get the precise coordinate of the given unit.
	 * 
	 * @param unit
	 *            The unit for which to return the position.
	 * @return The coordinates of the center of the unit, as an array with 3
	 *         doubles {x, y, z}.
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	@Override
	public double[] getPosition(Unit unit) throws ModelException {
		return unit.getPosition().getCoordinates();
	}

	/**
	 * Get the coordinates of the cube occupied by the given unit.
	 * 
	 * @param unit
	 *            The unit for which to return the cube coordinates.
	 * @return The coordinates of the cube in which the center of the unit lies,
	 *         as an array with 3 integers {x, y, z}.
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	@Override
	public int[] getCubeCoordinate(Unit unit) throws ModelException {
		return unit.getPosition().getCubeCoordinates();
	}

	/**
	 * Get the current name of the given unit.
	 * 
	 * @param unit
	 *            The unit for which to return the name.
	 * @return The current name of the unit.
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	@Override
	public String getName(Unit unit) throws ModelException {
		return unit.getName();
	}

	/**
	 * Set the name of the given unit to the given value.
	 * 
	 * @param unit
	 *            The unit whose name to change.
	 * @param newName
	 *            The new name for the unit.
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	@Override
	public void setName(Unit unit, String newName) throws ModelException {
		try{
			unit.setName(newName);
		}
		catch(IllegalArgumentException e){
			throw new ModelException();
		}
	}

	/**
	 * Return the weight attribute of the given unit.
	 * 
	 * @param unit
	 *            The unit for which to return the attribute's value
	 * @return The current weight of the unit
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	@Override
	public int getWeight(Unit unit) throws ModelException {
		return unit.getWeight();
	}

	/**
	 * Sets the weight attribute's value of the given unit to the given value.
	 * 
	 * @param unit
	 *            The unit for which to change the attribute's value
	 * @param newValue
	 *            The new weight
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	@Override
	public void setWeight(Unit unit, int newValue) throws ModelException {
			unit.setWeight(newValue);
	}

	/**
	 * Return the strength attribute of the given unit.
	 * 
	 * @param unit
	 *            The unit for which to return the attribute's value
	 * @return The current strength of the unit
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	@Override
	public int getStrength(Unit unit) throws ModelException {
		return unit.getStrength();
	}

	/**
	 * Sets the strength attribute's value of the given unit to the given value.
	 * 
	 * @param unit
	 *            The unit for which to change the attribute's value
	 * @param newValue
	 *            The new strength
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	@Override
	public void setStrength(Unit unit, int newValue) throws ModelException {
			unit.setStrength(newValue);	
	}

	/**
	 * Return the agility attribute of the given unit.
	 * 
	 * @param unit
	 *            The unit for which to return the attribute's value
	 * @return The current agility of the unit
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	@Override
	public int getAgility(Unit unit) throws ModelException {
		return unit.getAgility();
	}

	/**
	 * Sets the agility attribute's value of the given unit to the given value.
	 * 
	 * @param unit
	 *            The unit for which to change the attribute's value
	 * @param newValue
	 *            The new agility
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	@Override
	public void setAgility(Unit unit, int newValue) throws ModelException {
			unit.setAgility(newValue);	
	}

	/**
	 * Return the toughness attribute of the given unit.
	 * 
	 * @param unit
	 *            The unit for which to return the attribute's value
	 * @return The current toughness of the unit
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	@Override
	public int getToughness(Unit unit) throws ModelException {
		return unit.getToughness();
	}

	/**
	 * Sets the toughness attribute's value of the given unit to the given
	 * value.
	 * 
	 * @param unit
	 *            The unit for which to change the attribute's value
	 * @param newValue
	 *            The new toughness
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	@Override
	public void setToughness(Unit unit, int newValue) throws ModelException {
		unit.setToughness(newValue);		
	}
	
	/**
	 * Return the maximum number of hitpoints for the given unit.
	 * 
	 * @param unit
	 *            The unit for which to return the maximum number of hitpoints
	 * @return The maximum number of hitpoints for the given unit.
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	@Override
	public int getMaxHitPoints(Unit unit) throws ModelException {
		return unit.getMaxPoints();
	}

	/**
	 * Return the current number of hitpoints for the given unit.
	 * 
	 * @param unit
	 *            The unit for which to return the current number of hitpoints
	 * @return The current number of hitpoints for the given unit.
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	@Override
	public int getCurrentHitPoints(Unit unit) throws ModelException {
		return (int)unit.getCurrentHealth();
	}

	/**
	 * Return the maximum number of stamina points for the given unit.
	 * 
	 * @param unit
	 *            The unit for which to return the maximum number of stamina
	 *            points
	 * @return The maximum number of stamina points for the given unit.
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	@Override
	public int getMaxStaminaPoints(Unit unit) throws ModelException {
		return unit.getMaxPoints();
	}

	/**
	 * Return the current number of stamina points for the given unit.
	 * 
	 * @param unit
	 *            The unit for which to return the current number of stamina
	 *            points
	 * @return The current number of stamina points for the given unit.
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	@Override
	public int getCurrentStaminaPoints(Unit unit) throws ModelException {
		return (int)unit.getCurrentStamina();
	}

	/**
	 * Advance the state of the given unit by the given time period.
	 * 
	 * @param unit
	 *            The unit for which to advance the time
	 * @param dt
	 *            The time period, in seconds, by which to advance the unit's
	 *            state.
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	@Override @Deprecated
	public void advanceTime(Unit unit, double dt) throws ModelException {
		try {
			unit.advanceTime(dt);
		}
		catch(IllegalArgumentException e){
			throw new ModelException();
		}
		catch(NullPointerException e){
			throw new ModelException();
		}
		catch (IllegalStateException e){
			throw new ModelException();
		}
	}

	/**
	 * Move the given unit to an adjacent cube.
	 * 
	 * @param unit
	 *            The unit to move
	 * @param dx
	 *            The amount of cubes to move in the x-direction; should be -1,
	 *            0 or 1.
	 * @param dy
	 *            The amount of cubes to move in the y-direction; should be -1,
	 *            0 or 1.
	 * @param dz
	 *            The amount of cubes to move in the z-direction; should be -1,
	 *            0 or 1.
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	@Override
	public void moveToAdjacent(Unit unit, int dx, int dy, int dz) throws ModelException {
		try{
			unit.moveToAdjacent(dx, dy, dz);
		}
		catch (Exception e){
//			unit.cancelMoveTo();
//			throw e;
		}
	}

	/**
	 * Return the current speed of the given unit.
	 * 
	 * @param unit
	 *            The unit for which to retrieve the speed.
	 * @return The speed of the given unit.
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	@Override
	public double getCurrentSpeed(Unit unit) throws ModelException {
		if (unit.getTargetCoordinates() != null) {
			double[] velocity = unit.getVelocity(unit.getTargetCoordinates());
			return (Math.sqrt(Math.pow(velocity[0], 2) + 
					Math.pow(velocity[1], 2) + Math.pow(velocity[2], 2)));
		}
		else
			return 0.0;
	}
	
	/**
	 * Return whether the given unit is currently moving.
	 * 
	 * @param unit
	 *            The unit for which to retrieve the state.
	 * @return true if the unit is currently moving; false otherwise
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	@Override
	public boolean isMoving(Unit unit) throws ModelException {
		return unit.isMoving();
	}
	
	/**
	 * Enable sprinting mode for the given unit.
	 * 
	 * @param unit
	 *            The unit which should start sprinting.
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	@Override
	public void startSprinting(Unit unit) throws ModelException {
		try{
			unit.startSprinting();
		}
		catch(IllegalArgumentException e){
			throw new ModelException();
		}
	}

	/**
	 * Disable sprinting mode for the given unit.
	 * 
	 * @param unit
	 *            The unit which should stop sprinting.
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	@Override
	public void stopSprinting(Unit unit) throws ModelException {
		unit.stopSprinting();
	}

	/**
	 * Return whether the given unit is currently sprinting.
	 * 
	 * @param unit
	 *            The unit for which to retrieve the state.
	 * @return true if the unit is currently sprinting; false otherwise
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	@Override
	public boolean isSprinting(Unit unit) throws ModelException {
		return unit.getIsSprinting();
	}

	/**
	 * Return the current orientation of the unit.
	 * 
	 * @param unit
	 *            The unit for which to retrieve the orientation
	 * @return The orientation of the unit, in radians.
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	@Override
	public double getOrientation(Unit unit) throws ModelException {
		return unit.getOrientation();
	}

	/**
	 * Start moving the given unit to the given cube.
	 * 
	 * @param unit
	 *            The unit that should start moving
	 * @param cube
	 *            The coordinate of the cube to move to, as an array of integers
	 *            {x, y, z}.
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	@Override
	public void moveTo(Unit unit, int[] cube) throws ModelException {
		try{
			unit.moveTo(cube);
		}
		catch(Exception e){
			unit.cancelMoveTo();
		}
	}

	/**
	 * Make the given unit start working at its current coordinates..
	 * 
	 * @param unit
	 *            The unit that should start working
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	public void work(Unit unit) throws ModelException {
		unit.workAt(unit.getWorld().getRandomSolidCubeCoordinatesInRange(unit.getPosition().getCoordinates(), 1));
	}

	/**
	 * Return whether the given unit is currently working.
	 * 
	 * @param unit
	 *            The unit for which to retrieve the state
	 * @return true if the unit is currently working; false otherwise
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	@Override
	public boolean isWorking(Unit unit) throws ModelException {
		return unit.isWorking();
	}

	/**
	 * Make the given unit fight with another unit.
	 * 
	 * @param attacker
	 *            The unit that initiates the fight by attacking another unit
	 * @param defender
	 *            The unit that gets attacked and should defend itself
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	@Override
	public void fight(Unit attacker, Unit defender) throws ModelException{
		try {
			attacker.fight(defender);
		}
		catch(NullPointerException e){
			throw new ModelException();
		}
		catch(IllegalArgumentException e){
			throw new ModelException();
		}
	}

	/**
	 * Return whether the given unit is currently attacking another unit.
	 * 
	 * @param unit
	 *            The unit for which to retrieve the state
	 * @return true if the unit is currently attacking another unit; false
	 *         otherwise
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	@Override
	public boolean isAttacking(Unit unit) throws ModelException {
		return unit.isAttaking();
	}

	/**
	 * Make the given unit rest.
	 * 
	 * @param unit
	 *            The unit that should start resting
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	@Override
	public void rest(Unit unit) throws ModelException {
		unit.rest();
	}

	/**
	 * Return whether the given unit is currently resting.
	 * 
	 * @param unit
	 *            The unit for which to retrieve the atate
	 * @return true if the unit is currently resting; false otherwise
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	@Override
	public boolean isResting(Unit unit) throws ModelException {
		return unit.isResting();
	}

	/**
	 * Enable or disable the default behavior of the given unit.
	 * 
	 * @param unit
	 *            The unit for which to enable or disable the default behavior
	 * @param value
	 *            true if the default behavior should be enabled; false
	 *            otherwise
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	@Override
	public void setDefaultBehaviorEnabled(Unit unit, boolean value) throws ModelException {
//		try{
			if (value){
				unit.startDefaultBehavior();
			}
			else{
				unit.stopDefaultBehavior();
			}
//		}
//		catch(IllegalArgumentException e){
//			throw new ModelException();
//		}
//		catch(NullPointerException e){
//			throw new ModelException();
//		}
	}

	/**
	 * Returns whether the default behavior of the given unit is enabled.
	 * 
	 * @param unit
	 *            The unit for which to retrieve the default behavior state.
	 * @return true if the default behavior is enabled; false otherwise
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	@Override
	public boolean isDefaultBehaviorEnabled(Unit unit) throws ModelException {
		return unit.getDefaultBehaviorEnabled();
	}

}
