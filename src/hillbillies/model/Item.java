package hillbillies.model;

import java.util.Random;

import be.kuleuven.cs.som.annotate.*;
import hillbillies.positions.Position;
import ogp.framework.util.Util;

/**
 * A class of items, a specific type of entities, which can be picked up by units.
 * An item has a specific weight, next to the properties of an entity.
 * 
 * @invar	Each item can have its weight as weight.
 *				| canHaveAsWeight(this.getWeight())
 * @author	Sander Mergan, Thomas Vranken
 * @version	3.0
 *
 */
public abstract class Item extends Entity {
	
	/**
	 * Initializes this new item with given world, coordinates and weight.
	 * 
	 * @param	world
	 *				The given world of this new item.
	 * @param	coordinates
	 *				The coordinates of this new item.
	 * @param	weight
	 *				The weight of this new item.
	 *
	 * @throws	IllegalArgumentException
	 *				The given weight is invalid.
	 */
	protected Item(World world, int[] coordinates, int weight) {
		super(world, coordinates, weight);
	}
	
	/**
	 * Initializes this new item with given world, coordinates and a random valid weight.
	 * 
	 * @param	world
	 *				The given world of this new item.
	 * @param	coordinates
	 *				The coordinates of this new item.
	 *
	 * @effect	The weight of this item is set to a random valid weight
	 *				| (new.getWeight() == weight) && (canHaveAsWeight(weight))
	 *
	 * @throws	IllegalArgumentException
	 *				The given weight is invalid.
	 */
	protected Item(World world, int[] coordinates) {
		this(world, coordinates, new Random().nextInt(41)+10);
	}
	
	/**
	 * Transfers this item to the world of the unit which carries it. 
	 * 
	 * @post	This item no longer references the unit, and that unit no longer 
	 *				references this item.
	 * @post	This item is connected to the world of the unit it was connected to.
	 *				That world references this item as one of its entities.
	 * @post	This item is placed at the location the unit was standing.
	 * 
	 * @throws	IllegalStateException
	 *				This item is terminated, does not reference an effective unit, or that
	 *				unit does not reference an effective world.
	 */
	public void moveToWorld() throws IllegalStateException {
		if (this.isTerminated())
			throw new IllegalStateException();
		if (this.getUnit() == null)
			throw new IllegalStateException("not carried by a unit.");
		if (this.getUnit().getWorld() == null)
			throw new IllegalStateException();
		this.getPosition().setCoordinates(this.getUnit().getPosition().getCoordinates());
		World world = this.getUnit().getWorld();
		Unit unit = this.getUnit();
		this.setUnit(null);
		unit.setItem(null);
		this.setWorld(world);
		world.addEntity(this);
	}
	
	/**
	 * Transfers this item from its world to the given unit.
	 * 
	 * @param	unit
	 *				The unit which will carry the item.
	 * 
	 * @post	This item references the given unit as its unit, and that unit references this item as its item.
	 * @post	This item references no world, and the world no longer references this item.
	 * 
	 * @throws	IllegalStateException
	 *				This item is terminated.
	 * @throws	IllegalArgumentException
	 *				The world of this item and the world of the unit are different, or the unit carries an item or is terminated.
	 */
	public void moveToUnit(Unit unit) throws IllegalStateException, IllegalArgumentException {
		if (this.isTerminated())
			throw new IllegalStateException();
		if (this.getWorld() != unit.getWorld())
			throw new IllegalArgumentException("unit belongs to different world.");
		if (unit.hasItem() || unit.isTerminated())
			throw new IllegalArgumentException("unit cannot carry this item.");
		
		this.setUnit(unit);
		unit.setItem(this);
		World world = this.getWorld();
		this.setWorld(null);
		world.removeEntity(this);
		
	}
	
	// ==========================================================================================
	// Methods concerning the weight of this item.
	// ==========================================================================================
	
	/**
	 * Checks whether this item can have the given weight as its weight.
	 *  
	 * @param	weight
	 *				The weight to check.
	 *
	 * @return	True if the given weight is greater than or equal to zero.
	 *				| result == ((weight >= 10) && (weight <= 50))
	*/
	@Override @Raw
	public boolean canHaveAsWeight(int weight) {
		return ((weight >= MINIMAL_WEIGHT) && (weight <= MAXIMAL_WEIGHT));
	}
	
	/**
	 * A variable that stores the minimal weight for an item.
	 */
	public static final int MINIMAL_WEIGHT = 10; 
	
	/**
	 * A variable that stores the maximal weight for an item.
	 */
	public static final int MAXIMAL_WEIGHT = 50;
	
	
	// ==========================================================================================
	// Methods concerning the position of this item.
	// ==========================================================================================
	
	/**
	 * Checks whether the given coordinates are valid coordinates for this item.
	 * If this item has a world, then the checkers of its Entity superclass can be used, 
	 * but when it does'nt have a world and does have a unit, then the checkers of the Entity superclass of its unit is used.
	 * 
	 * @param coordinates
	 *				The coordinates to check.
	 */
	@Override
	public boolean canHaveAsCoordinates(int[] coordinates) {
		if ((this.getWorld() == null) && (this.getUnit() != null))
			return (this.getUnit().canHaveAsCoordinates(coordinates));
		else
			return super.canHaveAsCoordinates(coordinates);
	}
	
	
	// ==========================================================================================
	// Methods concerning the unit carrying this item.
	// ==========================================================================================
	
	/**
	 * A variable that stores the unit of this item.
	 */
	private Unit unit;

	/**
	 * Returns the unit of this item.
	 */
	@Basic @Raw
	public Unit getUnit() {
		return this.unit;
	}
	
	/**
	 * Checks whether this item can have the given unit as its unit.
	 * 
	 * @param	unit
	 *				The unit to check.
	 *
	 * @return	If this item is terminated, true if the given unit is not effective.
	 *				If this item's world is effective, true if the world of this
	 *				item and the given unit's world are the same or the given unit is not effective.
	 *				Else, return true.
	 */
	public boolean canHaveAsUnit(Unit unit) {
		if (this.isTerminated())
			return unit == null;
		else if (this.getWorld() != null)
			return ( (unit==null) || (this.getWorld() == unit.getWorld()) );
		else
			return true;
	}
	
	/**
	 * Checks whether this item has a proper unit.
	 * 
	 * @return	True if and only if this item can have the unit to which it
	 *				is attached as its unit and if that unit is either not
	 *				effective or has this item as its item.
	 */
	public boolean hasProperUnit() {
		return ( this.canHaveAsUnit(this.getUnit()) 
				&& ( ( this.getUnit() == null) || (this.getUnit().getItem() == this) ) );
	}
	
	/**
	 * Sets the unit to of this item to the given unit.
	 * 
	 * @param	unit
	 *				The unit to attach this item to.
	 *
	 * @post	This item references the given unit as the unit to which it is attached.
	 * 
	 * @throws	IllegalArgumentException
	 *				his item cannot have the given unit as its unit.
	 */
	protected void setUnit(@Raw Unit unit) throws IllegalArgumentException {
		if (! this.canHaveAsUnit(unit))
			throw new IllegalArgumentException();
		this.unit = unit;
	}

	
	// ==========================================================================================
	// Methods concerning the gametime.
	// ==========================================================================================
	
	/**
	 * Advances the time for this item.
	 */
	public void advanceTime(double deltaT) throws IllegalArgumentException, IllegalStateException {
		super.advanceGametime(deltaT);
		this.initiateFalling();
		
		if (this.isFalling()) {
			double[] newCoordinates = Position.calculateNextCoordinates(this.getPosition().getCoordinates(), Entity.FALL_VELOCITY, deltaT);
			int[] temp = Position.getCubeCoordinates(newCoordinates);
			temp[2]++;
			if ( (Util.fuzzyEquals(newCoordinates[2],  (double)temp[2],  1e-5))  
					&& ((newCoordinates[2]<0)
							|| ( ! this.getWorld().getCube(Position.getCubeCoordinates(newCoordinates)).isPassable()))  ){
				this.getPosition().setCoordinates(temp);
			}
			else{
				this.getPosition().setCoordinates(newCoordinates);
			}
		}
		this.endFalling();
	}
	
}
