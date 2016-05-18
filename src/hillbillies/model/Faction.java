package hillbillies.model;

import java.util.HashSet;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;


/**
 * A class of factions involving ...
 * 
 * @invar   Each faction must have proper units.
 *        | hasProperUnits()
 * @invar	Each faction must have a proper world.
 * 		  | hasProperWorld()
 * @invar	Each faction must have a proper scheduler.
 * 		  | hasProperScheduler()
 * 
 * @author Sander Mergan, Thomas Vranken
 * @version 2.7
 */
public class Faction {

	/**
	 * Creates a new faction with the given unit as a member, 
	 * connected to the given world.
	 * 
	 * @param 	unit
	 * 				The given unit.
	 * @param	world
	 * 				The world to connect this faction to.
	 * @post	This new faction has the given unit as one of its units.
	 * @post	The given unit has this faction as its faction.
	 * @post	This new faction has the given world as its world.
	 * @post	The given world has this faction as its faction.
	 * @effect	A new scheduler is created for this faction.
	 * 			The scheduler references this faction and vice-versa.
	 * @throws	IllegalArgumentException
	 * 			The given unit is invalid, or the unit has a proper world 
	 * 			which is not the given world.
	 */
	@Raw
	//TODO controleer association
	public Faction(Unit unit, World world) throws IllegalArgumentException{	
		if ((unit.hasProperWorld()) && (unit.getWorld() != world))
			throw new IllegalArgumentException("the unit's world is not the given world.");
		if (! this.canHaveAsUnit(unit))
			throw new IllegalArgumentException("Rejected unit" + unit.toString());
		unit.setFaction(this);
		this.setWorld(world);
		this.addUnit(unit);
		world.addFaction(this);
		
		Scheduler scheduler = new Scheduler(this);
		this.setScheduler(scheduler);
		
		if (! world.hasAsEntity(unit))
			world.addEntity(unit);
	}
	
	/**
	 * Creates a new empty faction with the given world as its world.
	 * 
	 * @param 	world
	 * 				The given world.
	 * @post	This new faction has the given world as its world.
	 * @post	This faction has no units.
	 * @effect	A new scheduler is created for this faction.
	 * 			The scheduler references this faction and vice-versa.
	 * @throws	IllegalArgumentException
	 * 			The given world is invalid.
	 */
	@Raw
	public Faction(World world) throws IllegalArgumentException {	
		this.setWorld(world);
		world.addFaction(this);
		
		Scheduler scheduler = new Scheduler(this);
		this.setScheduler(scheduler);
	}
	
	/**
	 * Creates a new faction with the given unit as a member.
	 * 
	 * @param 	unit
	 * 				The given unit.
	 * @post	This new faction has the given unit as one of its units.
	 * @post	The given unit has this faction as its faction.
	 * @post	This new faction has the unit's world as its world.
	 * @post	The unit's world has this faction as its faction.
	 * @throws	IllegalArgumentException
	 * 			The given unit is invalid.
	 */
	@Raw
	public Faction(Unit unit) throws IllegalArgumentException{	
		unit.setFaction(this);
		this.setWorld(unit.getWorld());
		this.addUnit(unit);
		this.getWorld().addFaction(this);
	}
	
	/**
	 * Returns a textual representation of this faction.
	 */
	@Override
	public String toString() {
		String string = "";
		string += world.toString()+ ", ";
		for(Unit unit:this.units)
			string += unit.getName() + ", ";
		string += " >>> ";
		return string;
	}
	
	// =================================================================================================
	// Destructor for factions.
	// =================================================================================================
	
	/**
	 * Terminate this faction.
	 *
	 * @post   This faction  is terminated.
	 *       | new.isTerminated()
	 * @post   All units in this faction, if any, are terminated.
	 *       
	 */
	//TODO terminate all connections.
	 public void terminate() {
		 if (! this.isTerminated()) {
			 if (this.getNbUnits() > 0)
				 for (Unit unit: units)
					 unit.terminate();
			 World formerWorld = this.getWorld();
			 this.setWorld(null);
			 formerWorld.removeFaction(this);
		 }
		 this.isTerminated = true;
	 }
	 
	 /**
	  * Return a boolean indicating whether or not this faction
	  * is terminated.
	  */
	 @Basic @Raw
	 public boolean isTerminated() {
		 return this.isTerminated;
	 }
	 
	 /**
	  * Variable registering whether this faction is terminated.
	  */
	 private boolean isTerminated = false;
	 
	// =================================================================================================
	// Methods concerning the units in this faction. (bidirectional association)
	// A faction can be empty and can contain a certain amount of units.
	// =================================================================================================

	/**
	 * Check whether this faction has the given unit as one of its
	 * units.
	 * 
	 * @param  unit
	 *         The unit to check.
	 */
	@Basic
	@Raw
	public boolean hasAsUnit(@Raw Unit unit) {
		return units.contains(unit);
	}

	/**
	 * Check whether this faction can have the given unit
	 * as one of its units.
	 * 
	 * @param  unit
	 *         The unit to check.
	 * @return True if and only if the given unit is effective
	 *         and that unit is a valid unit for a faction.
	 *       | result ==
	 *       |   (unit != null) &&
	 *       |   Unit.canHaveAsFaction(this)
	 */
	@Raw
	public boolean canHaveAsUnit(Unit unit) {
		return (unit != null) && (unit.canHaveAsFaction(this));
	}

	/**
	 * Check whether this faction has proper units attached to it.
	 * 
	 * @return True if and only if this faction can have each of the
	 *         units attached to it as one of its units,
	 *         and if each of these units references this faction as
	 *         the faction to which they are attached.
	 *       | for each unit in Unit:
	 *       |   if (hasAsUnit(unit))
	 *       |     then canHaveAsUnit(unit) &&
	 *       |          (unit.getFaction() == this)
	 */
	public boolean hasProperUnits() {
		for (Unit unit : units) {
			if (!canHaveAsUnit(unit))
				return false;
			if (unit.getFaction() != this)
				return false;
		}
		return true;
	}

	/**
	 * Return the number of units associated with this faction.
	 *
	 * @return  The total number of units collected in this faction.
	 */
	public int getNbUnits() {
		return units.size();
	}

	/**
	 * Add the given unit to the set of units of this faction.
	 * 
	 * @param  unit
	 *         The unit to be added.
	 * @pre    The given unit is effective and already references
	 *         this faction.
	 *       | (unit != null) && (unit.getFaction() == this)
	 * @post   This faction has the given unit as one of its units.
	 *       | new.hasAsUnit(unit)
	 * @throws IllegalArgumentException
	 *				The given unit is not an active unit or the given unit is not correctly connected to this faction.
	 */
	// NOTE: A check to see if the faction is not full, is executed in unit.setFaction().
	public void addUnit(@Raw Unit unit) throws IllegalArgumentException{
		if (unit == null || unit.getFaction() != this)
			throw new IllegalArgumentException();
		units.add(unit);
	}

	/**
	 * Remove the given unit from the set of units of this faction.
	 * 
	 * @param  unit
	 *         The unit to be removed.
	 * @pre    This faction has the given unit as one of
	 *         its units, and the given unit does not
	 *         reference any faction.
	 *       | this.hasAsUnit(unit) &&
	 *       | (unit.getFaction() == null)
	 * @post   This faction no longer has the given unit as
	 *         one of its units.
	 *       | ! new.hasAsUnit(unit)
	 */
	@Raw
	public void removeUnit(Unit unit) {
		assert this.hasAsUnit(unit) && (unit.getFaction() == null);
		units.remove(unit);
		unit.setFaction(null);
	}

	/**
	 * Variable referencing a set collecting all the units
	 * of this faction.
	 * 
	 * @invar  The referenced set is effective.
	 *       | units != null
	 * @invar  Each unit registered in the referenced list is
	 *         effective and not yet terminated.
	 *       | for each unit in units:
	 *       |   ( (unit != null) &&
	 *       |     (! unit.isTerminated()) )
	 */
	private final Set<Unit> units = new HashSet<Unit>();
	
	/**
	 * Returns a set collecting all the units of this faction. 
	 * 
	 * @return	The resulting set does not contain a null reference.
	 * @return	Each unit in the resulting set is attached to this faction,
	 * 			and vice versa.
	 */
	// NOTE: this is the formal way (same as in textbook) to return all objects of the set.
	public Set<Unit> getUnits() {
		return new HashSet<Unit>(this.units);
	}
	
	// =================================================================================================
	// Methods concerning the world of this faction. (bidirectional association)
	// Since units must always belong to a world, each faction must also
	// belong to a world.
	// =================================================================================================
	
	/**
	 * Return the world this faction is part of.
	 */
	@Basic @Raw
	public World getWorld() {
		return this.world;
	}
	
	/**
	 * Checks whether this faction can have the given world as its world.
	 * 
	 * @param 	world
	 *			The world to check.
	 * @return	If this faction is terminated, true if the given world
	 * 			is not effective.
	 * 			| if (isTerminated())
	 * 			|	then result == (world == null)
	 * 			Else, true if the given world is effective
	 * 			and not yet terminated.
	 * 			| else
	 * 			|	then result == (world != null) && (!world.isTerminated())
	 */
	@Raw
	public boolean canHaveAsWorld(@Raw World world) {
		if (this.isTerminated)
			return world == null;
		else
			return (world != null) && (! world.isTerminated());
	}
	
	/**
	 * Checks whether this faction has a proper world to which it is attached.
	 * 
	 * @return	True if and only if this faction can have the world to which it
	 * 			is attached as its world, and if that world is either not
	 * 			effective or has this faction as one of its factions.
	 * 			| (this.canHaveAsWorld(this.getWorld()) 
	 *			|  && ( (this.getWorld() == null) || (this.getWorld().hasAsFaction(this))) )
	 */
	public boolean hasProperWorld() {
		return ( this.canHaveAsWorld(this.getWorld()) 
				&& ( ( this.getWorld() == null) || (this.getWorld().hasAsFaction(this)) ) );
	}
	
	/**
	 * Sets the world to which this faction is attached to to the given world.
	 * 
	 * @param	world
	 * 			The world to attach this faction to.
	 * @post	This faction references the given world as the world to which it is attached.
	 * 			| new.getWorld() == world
	 * @throws	IllegalArgumentException
	 * 			This faction cannot have the given world as its world,
	 * 			Or the world is at its maximum capacity.
	 * 			| (! canHaveAsWorld(world)) || (world.getNbFactions() >= World.MAX_FACTIONS)
	 */
	public void setWorld(@Raw World world) throws IllegalArgumentException {
		if ( (! this.canHaveAsWorld(world)) || (world.getNbActiveFactions() >= World.MAX_FACTIONS) )
			throw new IllegalArgumentException();
		this.world = world;
	}
	
	/**
	 * A variable referencing the world to which this faction is attached.
	 */
	private World world;
	
	// =================================================================================================
	// Methods concerning the scheduler of this faction.
	// =================================================================================================
	
	/**
	 * Return the scheduler of this faction.
	 */
	@Basic @Raw
	public Scheduler getScheduler() {
		return this.scheduler;
	}
	
	/**
	 * Check whether this faction has a scheduler.
	 * @return	True if the scheduler of this faction is effective.
	 * 			| result == (getScheduler() != null)
	 */
	public boolean hasScheduler() {
		return this.getScheduler() != null;
	}
	
	/**
	 * Checks whether this faction has a proper scheduler attached to it.
	 * 
	 * @return	True if and only if the scheduler of this faction does not reference
	 * 			an effective scheduler, or that scheduler references this faction
	 * 			as its faction.
	 * 			| result == ( (this.getScheduler() == null) || 
	 * 			|				(this.getScheduler().getFaction() == this) )
	 */
	public boolean hasProperScheduler() {
		return ( (this.getScheduler() == null) || (this.getScheduler().getFaction() == this) );
	}
	
	/**
	 * Sets the scheduler attached to this faction to to the given scheduler.
	 * 
	 * @param	scheduler
	 * 			The scheduler to attach to this faction.
	 * @post	This faction references the given scheduler as its scheduler.
	 * 			| new.getScheduler() == scheduler
	 * @throws	IllegalArgumentException
	 * 			The given scheduler is effective but does not references
	 * 			this faction as its faction.
	 * 			| (scheduler != null) && (scheduler.getFaction() != this)
	 * 			Or, the scheduler is not effective and this faction references
	 * 			a scheduler which still references this faction as its faction.
	 * 			| (scheduler == null) && (this.hasScheduler() && (this.getScheduler().getFaction() == this))
	 */
	public void setScheduler(@Raw Scheduler scheduler) throws IllegalArgumentException {
		if ( (scheduler != null) && (scheduler.getFaction() != this) )
			throw new IllegalArgumentException("Scheduler does not properly associate this faction.");
		if ( (scheduler == null) && (this.hasScheduler() && (this.getScheduler().getFaction() == this)))
			throw new IllegalArgumentException("Link with current scheduler not properly broken.");
		this.scheduler = scheduler;
	}
	
	/**
	 * A variable referencing the scheduler attached to this faction.
	 */
	private Scheduler scheduler;
	
}
