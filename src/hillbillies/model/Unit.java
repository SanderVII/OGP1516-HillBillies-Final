package hillbillies.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.exception.UnitMaxedOutException;
import hillbillies.statements.Statement;
import hillbillies.util.Position;
import hillbillies.util.UnitPosition;
import ogp.framework.util.Util;

//TODO de fouten zoeken in defaultBehaviour. 
//TODO do not award xp for failed task (i.e. working air)
//TODO het path opnieuw berekenen na elke moveToAdjacent.
//TODO fix (unit)positions methods to use those of entity.

/** 
 * @version 2.17
 */

/**
 * A class of units involving a world, a faction, a name, a position, a number of primary attributes, health and stamina,
 * 	an orientation and a toggle (boolean value) for sprinting.
 * 
 * @invar	The current health of each unit must be a valid current health for any unit. 
 *        			| canHaveAsCurrentHealth(getCurrentHealth())
 * @invar	The current stamina of each unit must be a valid current stamina for any unit. 
 *        			| canHaveAsCurrentStamina(getCurrentStamina())
 * @invar	The weight of each unit must be in the range minBaseStat..MaxBaseStat and be at least the average of the strength and agility of this unit. 
 * 					| canHaveAsWeight(getWeight())
 * @invar	The maximum base stat must be a valid maximal base stat for any unit.
 *					| canHaveAsMaxBaseStat(getMaxBaseStat())
 * @invar	The minimal base stat must be a valid minimal base stat for any unit.
 *					| canHaveAsMinBaseStat(getMinBaseStat())
 * @invar	The name of each unit must be a valid name for any unit.
 *					| isValidName(getName())
 * @invar	The position of each unit must be a valid position for any unit.
 * 					|canHaveAsUnitPosition(getPosition())
 * @invar	The orientation of each unit must be a valid orientation for any unit.
 * 					| isValidOrientation(getAngle())
 * @invar	The initial position of each unit must be a valid initial position for any unit.
 *       			| isValidInitialPosition(getInitialPosition())
 * @invar	The target position of each unit must be a valid target position for any unit.
 *       			| isValidTargetPosition(getTargetPosition())
 * @invar	The is-sprinting value of each unit must be a valid is-sprinting value for any unit.
 *       			| isValidIsSprinting(getIsSprinting())
 * @invar	The experience of each unit must be a valid experience for any unit.
 *       			| isValidExperience(getExperience())
 * @invar	Each unit must have a proper world to which it is attached.
 * 					| hasProperWorld()
 * @invar	Each unit must have a proper faction to which it is attached.
 * 					| hasProperFaction()
 * @invar	Each unit must have a proper task.
 * 					| hasProperTask()
 * @invar	The is-falling property of each unit must be a valid is-falling property for any unit.
 *					| isValidIsFalling(getIsFalling())
 * 
 * @author Sander Mergan, Thomas Vranken
 */
public class Unit extends Entity{
	
	/**
	 * Initialize this new unit with the given world, faction, name, position, weight, strength, agility, toughness.
	 * 
	 * @param	world
	 * 					The world of this new unit.
	 * @param	faction
	 * 					The faction of this new unit.
	 * @param	name
	 * 					The name for this new unit.
	 * @param	position
	 * 					The initial position for this new unit.
	 * @param	weight 
	 * 					The weight for this new unit.
	 * @param	strength
	 * 					The strength for this new unit.
	 * @param	agility
	 * 					The agility for this new unit.
	 * @param	toughness
	 * 					The toughness for this new unit.
	 * 
	 * @effect	Sets the initial weight of this new unit to the given initial weight.
	 *					| this.setInitialWeight(weight)
	 *    
	 * @effect	Sets the strength of this new unit to the given initial strength.
	 *					| this.setInitialStrength(strength)
	 * 
	 * @effect	Sets the agility of this new unit to the given initial agility.
	 *					| this.setInitialAgility(agility)
	 *
	 * @effect	Sets the toughness of this new unit to the given initial toughness.
	 *					| this.setInitialToughness(toughness)
	 *
	 * @post	The world of this unit is set to the given world.
	 * 				| new.getWorld() == world
	 * 
	 * @post	The faction of this unit is set to the given faction.
	 * 				| new.getFaction() == faction
	 * 
	 * @post	The current health of the new unit is equal to the maximal health of that unit.
	 * 				| new.getCurrentHealth() == new.getMaxPoints()
	 * 
	 * @post	The current stamina of the new unit is equal to the maximal stamina of that unit.
	 * 				| new.getCurrentStamina() == new.getMaxPoints
	 * 
	 * @post	The name of this unit is equal to that given name.
	 * 				| new.getName(name)
	 * 
	 * @post	The position of this unit is equal to the given position.
	 * 				| (new.getPosition() == position)
	 * 
	 * @throws	IllegalArgumentException
	 * 				This unit cannot have the given world as its world,
	 * 				Or the world is at its maximum capacity.
	 * 				| (! canHaveAsWorld(world)) || (world.getNbUnits() >= World.MAX_UNITS_WORLD)
	 * 
	 * @throws	IllegalArgumentException
	 * 				This unit cannot have the given faction as its faction,
	 * 				Or the faction is at its maximum capacity.
	 * 				| (! canHaveAsFaction(faction)) || (faction.getNbUnits() >= World.MAX_UNITS_FACTION)
	 * 
	 * @throws	IllegalArgumentException
	 * 				The given name is not a valid name.
	 *  			| ! isValidName(name)
	 *  
	 * @throws	IllegalArgumentException
	 * 				The given position is not a valid position.
	 *  			| ! canHaveAsUnitPosition(position)
	 */
	public Unit(World world, Faction faction, String name, int[] position, 
			int weight, int strength, int agility, int toughness) throws IllegalArgumentException {
		
		super(world, position);

		this.setFaction(faction);
		
		// conditions that apply to name in order to be valid are checked in setName method itself.
		this.setName(name);
		
		// Upon creation, places this unit in the center of a cube.
		this.setPosition( new UnitPosition(this.getWorld(), Position.getCubeCenter(position)) );
		
		this.setInitialStrength(strength);
		
		this.setInitialAgility(agility);
		
		this.setInitialToughness(toughness);
		
		this.setInitialWeight(weight);
		
		this.setOrientation(Math.PI);
		
		// Precondition is checked in setCurrentHealth method itself.
		this.setCurrentHealth(this.getMaxPoints());
		// Precondition is checked in setCurrentStamina method itself.
		this.setCurrentStamina(this.getMaxPoints());
		
		faction.addUnit(this);
		world.addEntity(this);
	}
	
	/**
	 * Initialize this new unit with the given world, name, position, weight, strength, agility, toughness.
	 * The unit is added to a newly created faction of that world.
	 * @param	world
	 * 					The world of this new unit.
	 * @param	name
	 * 					The name for this new unit.
	 * @param	position
	 * 					The initial position for this new unit.
	 * @param	weight 
	 * 					The weight for this new unit.
	 * @param	strength
	 * 					The strength for this new unit.
	 * @param	agility
	 * 					The agility for this new unit.
	 * @param	toughness
	 * 					The toughness for this new unit.
	 * @effect	A new faction in the given world is created.
	 * 					|	new Faction(world)
	 * @effect	This unit is initialized with the given attributes and the newly created faction as its faction.
	 * 					| this(world, new Faction(world), name, position, weight, strength, agility, toughness)
	 */
	//NOTE: no need to repeat exceptions in documentation.
	public Unit(World world, String name, int[] position, 
			int weight, int strength, int agility, int toughness) throws IllegalArgumentException {
		this(world, new Faction(world), name, position, weight, strength, agility, toughness);
	}
	
	/**
	 * Initialize this new unit with the given world and name. All of its stats are chosen randomly
	 * between valid initial values. The unit is added to a newly created faction of that world.
	 * Its position is randomly chosen and is valid for any unit in the given world.
	 * @param	world
	 * 					The world of this new unit.
	 * @param	name
	 * 					The name for this new unit.
	 * @effect	This unit is initialized with random attributes, position and a new faction.
	 * 					| this(world, name, position, weight, strength, agility, toughness)
	 */
	//NOTE: no need to repeat exceptions in documentation.
	public Unit(World world, String name) throws IllegalArgumentException {
		this(world, name, world.getRandomAvailableUnitCoordinates(),
				new Random().nextInt(getMaxInitialBaseStat()-getMinInitialBaseStat())+getMinInitialBaseStat(),
				new Random().nextInt(getMaxInitialBaseStat()-getMinInitialBaseStat())+getMinInitialBaseStat(),
				new Random().nextInt(getMaxInitialBaseStat()-getMinInitialBaseStat())+getMinInitialBaseStat(),
				new Random().nextInt(getMaxInitialBaseStat()-getMinInitialBaseStat())+getMinInitialBaseStat());
	}
	
	/**
	 * Returns a textual representation of this unit.
	 */
	@Override
	public String toString() {
		return "Unit\n"+"name: " + getName() +"\n"
				+ "position: " + this.getPosition().toString() +"\n"
				+ "faction: " + getFaction() +"\n"
				+ "world: " + getWorld() + "\n"
				+"=====";
	}
	
	
	// ===================================================================================================
	// Methods concerning the termination of this unit.
	// ===================================================================================================
	
	/**
	 * Terminate this unit.
	 *
	 * @post   This unit  is terminated.
	 *       | new.isTerminated()
	 * @post   No world is connected with this unit.
	 *       | ! new.hasWorld()
	 * @post   The world this unit is part of no longer references this unit.
	 * 		 | if (hasWorld())
	 * 		 |	then (! getWorld().getAllUnits().contains(new))
	 * @post   The faction this unit is part of no longer references this unit.
	 * 		 | if (hasFaction())
	 * 		 |	then (! getFaction().getAllUnits().contains(new))
	 * @post   The log this unit is carrying is dropped on the ground.
	 * 		 | if (isCarryingLog())
	 * 		 |	then ( dropLog() )
	 * @post   The boulder this unit is carrying is dropped on the ground.
	 * 		 | if (isCarryingBoulder())
	 * 		 |	then ( dropBoulder() )
	 * @post   This unit does nothing.
	 * 		 | new.getCurrentActivity() == Activity.NOTHING
	 */
	 public void terminate() {
		 if (! this.isTerminated()) {
			 this.getWorld().removeEntity(this);
			 
			 if (this.hasProperFaction())
				 this.getFaction().removeUnit(this);
	
			this.setCurrentActivity(Activity.NOTHING);
			this.stopSprinting();
			this.stopDefaultBehavior();
			this.resetCoordinates();
			
			if (this.hasItem())
				this.dropItem(this.getCubeCoordinates());
	
			this.isTerminated = true;
		 }
	 }
	 
	 /**
	  * Return a boolean indicating whether or not this unit
	  * is terminated.
	  */
	 @Basic @Raw
	 public boolean isTerminated() {
		 return this.isTerminated;
	 }
	 
	 /**
	  * Variable registering whether this person is terminated.
	  */
	 private boolean isTerminated = false;
	 
	
	//=============================================================================
	// Methods for name and primary attributes.
	//=============================================================================
	
	/**
	 * Checks whether the given minimal base stat is a valid minimal base stat for any unit.
	 * @param	minBaseStat
	 * 					The minimal base stat to check.
	 * @return	True if and only if the given minimal base stat is equal to or greater than one.
	 * 					|result == (minBaseStat >= 1)
	 */
	@Raw
	public static boolean canHaveAsMinBaseStat(int minBaseStat){
		return minBaseStat >= 1;
	}
		
	/**
	 * Returns the minimal base stat.
	 */
	@Basic @Raw @Immutable
	public static final int getMinBaseStat(){
		return minBaseStat;
	}
	
	/**
	 * A variable that stores the minimal base stat.
	 */
	private static final int minBaseStat = 1;
	
	/**
	 * Checks whether the given maximal base stat is a valid maximal base stat for any unit.
	 * @param	maxBaseStat
	 * 					The maximal base stat to check.
	 * @return	True if and only if the given maximal base stat is equal to or greater than this units minimal base stat.
	 * 					|result == (maxBaseStat >= this.getMinBaseStat())
	 */
	@Raw
	public static boolean canHaveAsMaxBaseStat(int maxBaseStat){
		return maxBaseStat >= Unit.getMinBaseStat();
	}
	
	/**
	 * Returns the maximal base stat.
	 */
	@Basic @Raw @Immutable
	public static final int getMaxBaseStat(){
		return maxBaseStat;
	}
	
	/**
	 * A variable that stores the maximal base stat.
	 */
	private static final int maxBaseStat = 200;

	/**
	 * Checks whether the given minimal initial base stat is a valid minimal initial base stat for any unit.
	 * @param	minInitialBaseStat
	 * 					The minimal initial base stat to check.
	 * @return	True if and only if the given minimal initial base stat is equal to or greater than 1.
	 * 					|result == (minInitialBaseStat >= 1)
	 */
	@Raw
	public static boolean canHaveAsMinInitialBaseStat(int minInitialBaseStat){
		return minInitialBaseStat >= 1;
	}
		
	/**
	 * Returns the minimal initial base stat.
	 */
	@Basic @Raw @Immutable
	public static final int getMinInitialBaseStat(){
		return Unit.minInitialBaseStat;
	}
	
	/**
	 * A variable that stores the minimal initial base stat.
	 */
	private static final int minInitialBaseStat = 25;
	
	/**
	 * Checks whether the given maximal initial base stat is a valid maximal initial base stat for any unit.
	 * @param	maxInitialBaseStat
	 * 					The maximal initial base stat to check.
	 * @return	True if and only if the given maximal initial base stat is equal to or greater than this units minimal initial base stat.
	 * 					|result == maxInitialBaseStat >= this.getMinInitialBaseStat()
	 */
	@Raw
	public static boolean canHaveAsMaxInitialBaseStat(int maxInitialBaseStat){
		return maxInitialBaseStat >= getMinInitialBaseStat();
	}
	
	/**
	 * Returns the maximum initial base stat.
	 */
	@Basic @Raw @Immutable
	public static final int getMaxInitialBaseStat(){
		return maxInitialBaseStat;
	}
	
	/**
	 * A variable that stores the maximum initial base stat.
	 */
	private static final int maxInitialBaseStat = 100;
	
	/**
	 * Return the name of this unit.
	 */
	@Basic @Raw
	public String getName() {
		return this.name;
	}

	/**
 		* Set the name of this unit to the given name.
 		* 
 		* @param  name
 		*         			The new name for this unit.
 	 	* @post The name of this new unit is equal to the given name.
	 	*       		| new.getName() == name
	 	* @throws		IllegalArgumentException
	 	*         			The given name is not a valid name for any unit.
	 	*       			| ! isValidName(getName())
	 */
	@Raw
	public void setName(String name) 
		throws IllegalArgumentException {
	if (! isValidName(name))
		throw new IllegalArgumentException();
	this.name = name;
	}

	/**
	 * Returns whether a name is a valid name.
	 * 
	 * @param	name
	 *            		The name to be checked.
	 * @return	Returns true if and only if the given name is not null, has at least 2 legal characters and the first
	 * 					character is an uppercase letter.
	 * 					Legal characters are: uppercase and lowercase letters, single and double quotes and spaces.
	 * 					|result == (!Character.isUpperCase(name.charAt(0))) && (name.length() >= 2) && (name != null) && (name.matches("[a-zA-Z\\s\'\"]+"))
	 */
	@Raw
	public static boolean isValidName(String name) {
		return ( (name != null) &&
					   (name.matches("[A-Z]([a-zA-Z\\s\'\"])+"))) ; // The length is at least 2 because the first character has to be an uppercase letter and that has to be followed by at least one of the legal characters.
		// name.matches("[a-zA-Z\\s\'\"]+") 	checks whether the characters of the string name are letters, spaces or quotes.
		// 	a-z 	means the characters in name can be lowercase letters.
		// 	A-Z 	means the characters in name can be Uppercase letters.
		// 	\\s 	means the characters in name can be spaces.
		//		\'  	means the characters in name can be single quotes.
		// 	\" 	means the characters in name can be double quotes.
		//		+ 		in the end means that the characters in the brackets in front of it can appear more than once and at least one of has to them appear.
	}

	/**
	 * A variable that stores the name of this unit.
	 */
	private String name;

	/**
	 * Returns the weight of this unit.
	 */
	@Basic @Raw
	public int getWeight() {
		int weight = 0;
		if (this.hasItem())
			weight += this.getItem().getWeight();
		
		weight += this.weight;
		return weight;
	}

	/**
	 * Returns the minimal weight of this unit based on the given strength and
	 * agility.
	 * 
	 * @param	strength
	 *           		The strength of this unit.
	 * @param	agility
	 *            		The agility of this unit.
	 * @return integer displaying the minimal value for the weight of this unit.
	 *         			| result == (strength + agility)/2
	 */
	@Raw
	public static int getMinWeight(int strength, int agility) {
		return (strength + agility) / 2;
	}

	/**
	 * Returns the minimal weight of this unit based on this units strength and agility.
	 *
	 * @return integer displaying the minimal value for the weight of this unit.
	 *             		| result == (this.getStrength() + this.getAgility())/2
	 */
	public int getMinWeight() {
		return (int)( (this.getStrength() + this.getAgility()) / 2.0 );
	}
	
	/**
	 * Return whether the given weight is valid.
	 * 
	 * @param	weight
	 * 					The weight to be checked.
	 * @return	True if and only if the given weight is in the range this.getMinBaseStat()..this.getMaxBaseStat() and 
	 * 					weight exceeds (or is equal to) the average of this unit's strength and agility. 
	 * 					|result == (weight >= this.getMinBaseStat()) && (weight <= this.getMaxBaseStat() && 
	 *       			|	(weight >= this.getMinWeight())
	 */
	public boolean canHaveAsWeight(int weight){
		// If this unit is carrying an item, then the maximum value for weight is not accounted for.
		if (this.hasItem())
			return ( (weight >= getMinBaseStat()) && (weight >= this.getMinWeight()) );
		
		return ( (weight >= getMinBaseStat()) && (weight <= getMaxBaseStat()) && (weight >= this.getMinWeight()) );
	}
	
	/**
	 * Sets the weight of this unit to the given weight.
	 * 
	 * @param	weight
	 *          		The new weight of this unit.
	 * @post	If the given weight is a valid weight, the weight of this unit is equal to the given weight. 
	 *       		| if (this.canHaveAsWeight()) 
	 *       		| 	then new.getWeight() == weight
	 * @post If the given weight is lower than the average of the strength and agility of this unit, the weight of this unit is equal to that average.
	 *       		| if (weight < (this.getMinWeight()) 
	 *       		| 	then new.getWeight() == (this.getMinWeight())
	 * @post	If the given weight exceeds the maximum base stat, the weight of this unit is equal to that maximum base stat. 
	 * 				| if (weight > this.getMaxBaseStat()) 
	 * 				| 	then new.getWeight == this.getMaxBaseStat()
	 */
	@Override
	public void setWeight(int weight) {
		if (weight > Unit.getMaxBaseStat()) {
			this.weight = getMaxBaseStat();
		}else if (weight >= this.getMinWeight()) {
			this.weight = weight;
		} else if (weight < this.getMinWeight()) {
			this.weight = getMinWeight(); 
		}
	}

	/**
	 * Sets the initial weight of this unit to the given weight.
	 * 
	 * @param	weight
	 * 					The weight for this new unit,
	 * 
	 * @post	If the given weight is in the range getMinInitialBaseStat()..getMaxInitialBaseStat(), the weight of this unit is equal to the given weight. 
	 *       		| if ( (weight >= getMinInitialBaseStat()) && (weight <=getMaxInitialBaseStat()) ) 
	 *       		| 	then new.getWeight() == weight
	 * @post	If the given weight is lower than getMinInitialBaseStat(), the weight of this unit is equal to getMinInitialBaseStat(). 
	 *       		| if (weight < getMinInitialBaseStat()) 
	 *       		|	then new.getWeight() == getMinInitialBaseStat()
	 * @post	If the given weight exceeds getMaxInitialBaseStat(), the weight of this unit is equal to getMaxInitialBaseStat(). 
	 *       		| if (weight > getMaxInitialBaseStat()) 
	 *       		| 	then new.getWeight() == getMaxInitialBaseStat()
	 */
	public void setInitialWeight(int weight) {
		if (weight > getMaxInitialBaseStat()) {
			this.setWeight(getMaxInitialBaseStat());
		} else if (weight < getMinInitialBaseStat()) {
			this.setWeight(getMinInitialBaseStat());
		} else {
			this.setWeight(weight);
		}
	}
	
	/**
	 * A variable that registers the weight of this unit.
	 */
	private int weight;

	/**
	 * Returns the strength of this unit.
	 */
	@Basic @Raw
	public int getStrength() {
		return this.strength;
	}
	
	/**
	 * Checks whether the given strength is valid.
	 * 
	 * @param	strength
	 * 					The strength of this unit.
	 * @return	True if and only if the giveen strength is in the range this.getMinBaseStat()..this.getMaxBaseStat().
	 * 					|result == (strength >= this.getMinBaseStat()) && (strength <= this.getMaxBaseStat())
	*/
	public boolean canHaveAsStrength(int strength){
		return ((strength >= Unit.getMinBaseStat()) && (strength <= Unit.getMaxBaseStat()));
	}
	
	/**
	 * Sets the strength of this unit to the given strength.
	 * 
	 * @param	strength
	 *          		The new strength of this unit.
	 * @post	If the given strength is in the range this.getMinBaseStat()..this.getMaxBaseStat(), the strength of this unit is set to the given strength. 
	 *       		| if (canHaveAsStrength(strength)) 
	 *       		| 	then new.getStrength() == strength
	 * @post If the given strength is lower than this.getMinBaseStat(), the strength of this unit is set to this.getMinBaseStat(). 
	 *       		| if (strength < this.getMinBaseStat()) 
	 *       		|	then new.getStrength() == this.getMinBaseStat()
	 * @post If the given strength exceeds this.getMaxBaseStat(), the strength of this unit is set to this.getMaxBaseStat(). 
	 *       		| if (strength > this.getMaxBaseStat()) 
	 *       		| 	then new.getStrength() == this.getMaxBaseStat()
	 * @post If changing the strength of this unit makes its current weigth invalid (by raising the minimal weight), 
	 * 				the weight of this unit changes to the new minimal weight. 
	 *       		| if (this.getWeight < this.getMinWeight()) 
	 *       		| 	then new.getWeight() == this.getMinWeight()
	 */
	public void setStrength(int strength) {
		if (this.canHaveAsStrength(strength)) {
			this.strength = strength;
			if (this.getWeight() < getMinWeight(strength, this.getAgility())) {
				this.setWeight(getMinWeight(strength, this.getAgility()));
			}
		} else if (strength < Unit.getMinBaseStat()) {
			this.strength = Unit.getMinBaseStat();
		} else if (strength > Unit.getMaxBaseStat()) {
			this.strength = Unit.getMaxBaseStat();
			if (this.getWeight() < getMinWeight(strength, this.getAgility())) {
				this.setWeight(getMinWeight(strength, this.getAgility()));
			}
		}
	}

	/**
	 * Sets the initial strength of this unit to the given strength.
	 * 
	 * @param	strength
	 * 					The strength for this new unit,
	 * 
	 * @post	If the given strength is in the range getMinInitialBaseStat()..getMaxInitialBaseStat(), the strength of this unit is equal to the given strength. 
	 *       		| if ( (strength >= getMinInitialBaseStat()) && (strength <=getMaxInitialBaseStat()) ) 
	 *       		| 	then new.getStrength() == strength
	 * @post	If the given strength is lower than getMinInitialBaseStat(), the strength of this unit is equal to getMinInitialBaseStat(). 
	 *       		| if (strength < getMinInitialBaseStat()) 
	 *       		|	then new.getStrength() == getMinInitialBaseStat()
	 * @post	If the given strength exceeds getMaxInitialBaseStat(), the strength of this unit is equal to getMaxInitialBaseStat(). 
	 *       		| if (strength > getMaxInitialBaseStat()) 
	 *       		| 	then new.getStrength() == getMaxInitialBaseStat()
	 */
	public void setInitialStrength(int strength) {
		if (strength > getMaxInitialBaseStat()) {
			this.setStrength(getMaxInitialBaseStat());
		} else if (strength < getMinInitialBaseStat()) {
			this.setStrength(getMinInitialBaseStat());
		} else {
			this.setStrength(strength);
		}
	}
	
	/**
	 * A variable that registers the strength of this unit.
	 */
	private int strength;

	/**
	 * Returns the agility of this unit.
	 */
	@Basic @Raw
	public int getAgility() {
		return this.agility;
	}
	
	/**
	 * Checks whether the given agility is valid.
	 * 
	 * @param	agility
	 * 					The agility of this unit.
	 * @return	True if and only if agility is in the range this.getMinBaseStat()..this.getMaxBaseStat().
	 * 					|result == (agility >= this.getMinBaseStat()) && (agility <= this.getMaxBaseStat())
	*/
	public boolean canHaveAsAgility(int agility){
		return ((agility >= Unit.getMinBaseStat()) && (agility <= Unit.getMaxBaseStat()));
	}
	
	/**
	 * Sets the agility of this unit to the given agility.
	 * 
	 * @param	agility
	 *          		The new agility of this unit.
	 * @post	If the given agility is in the range this.getMinBaseStat()..this.getMaxBaseStat(), 
	 * 				the agility of this unit is set to the given agility. 
	 *       		|if (this.canHaveAsAgility())
	 *       		|	then new.getAgility() == agility
	 * @post	If the given agility is lower than this.getMinBaseStat(), the agility of this unit is set to this.getMinBaseStat(). 
	 *       		|if (agility < this.getMinBaseStat()) 
	 *       		|	then new.getAgility() == this.getMinBaseStat()
	 * @post	If the given agility exceeds this.getMaxBaseStat(), the agility of this unit is set to this.getMaxBaseStat(). 
	 *       		|if (agility > this.getMaxBaseStat()) 
	 *       		|	then new.getAgility() == this.getMaxBaseStat()
	 * @post	if changing the agility of this unit makes its weigth (by raising the minimal weight), 
	 * 				the weight of this unit changes to the new minimal weight. 
	 *       		|if (this.getWeight() < (this.getStrength() + agility)/2)) 
	 *       		|	then new.getWeight() == this.getWeight()
	 */
	public void setAgility(int agility) {
		if (this.canHaveAsAgility(agility)) {
			this.agility = agility;
			if (this.getWeight() < getMinWeight(this.getStrength(), agility)) {
				setWeight(getMinWeight(this.getStrength(), agility));
			}
		} else if (agility < Unit.getMinBaseStat()) {
			this.agility = Unit.getMinBaseStat();
		} else if (agility > Unit.getMaxBaseStat()) {
			this.agility = Unit.getMaxBaseStat();
			if (this.getWeight() < getMinWeight(this.getStrength(), agility)) {
				setWeight(getMinWeight(this.getStrength(), agility));
			}
		}
	}
	
	/**
	 * Sets the initial agility of this unit to the given agility.
	 * 
	 * @param	agility
	 * 					The agility for this new unit,
	 * 
	 * @post	If the given agility is in the range getMinInitialBaseStat()..getMaxInitialBaseStat(), the agility of this unit is equal to the given agility. 
	 *       		| if ( (agility >= getMinInitialBaseStat()) && (agility <=getMaxInitialBaseStat()) ) 
	 *       		| 	then new.getAgility() == agility
	 * @post	If the given agility is lower than getMinInitialBaseStat(), the agility of this unit is equal to getMinInitialBaseStat(). 
	 *       		| if (agility < getMinInitialBaseStat()) 
	 *       		|	then new.getAgility() == getMinInitialBaseStat()
	 * @post	If the given agility exceeds getMaxInitialBaseStat(), the agility of this unit is equal to getMaxInitialBaseStat(). 
	 *       		| if (agility > getMaxInitialBaseStat()) 
	 *       		| 	then new.getAgility() == getMaxInitialBaseStat()
	 */
	public void setInitialAgility(int agility) {
		if (agility > getMaxInitialBaseStat()) {
			this.setAgility(getMaxInitialBaseStat());
		} else if (agility < getMinInitialBaseStat()) {
			this.setAgility(getMinInitialBaseStat());
		} else {
			this.setAgility(agility);
		}
	}

	/**
	 * A variable that registers the agility of this unit.
	 */
	private int agility;
	
	/**
	 * Returns the toughness of this unit.
	 */
	@Basic @Raw
	public int getToughness() {
		return this.toughness;
	}
	
	/**
	 * Checks whether the given toughness is valid.
	 * 
	 * @param	toughness
	 * 					The toughness of this unit.
	 * @return	True if and only if toughness is in the range this.getMinBaseStat()..this.getMaxBaseStat().
	 * 					|result == (toughness >= this.getMinBaseStat()) && ((toughness <= this.getMaxBaseStat())
	*/
	public boolean canHaveAsToughness(int toughness){
		return ((toughness >= Unit.getMinBaseStat()) && (toughness <= Unit.getMaxBaseStat()));
	}
	
	/**
	 * Sets the toughness of this unit to the given toughness.
	 * 
	 * @param	toughness
	 *          		The new toughness of this unit.
	 * @post If the given toughness is in the range this.getMinBaseStat()..this.getMaxBaseStat(), 
	 * 				the toughness of this unit is set to the given toughness. 
	 *       		|if (this.canHaveAsToughness(toughness)) 
	 *       		|	then new.getToughness() == toughness
	 * @post	If the given toughness is lower than this.getMinBaseStat(), 
	 * 				the toughness of this unit is set to this.getMinBaseStat(). 
	 *       		|if (toughness < this.getMinBaseStat()) 
	 *       		|	then new.getToughness() == this.getMinBaseStat()
	 * @post	If the given toughness exceeds this.getMaxBaseStat(), 
	 * 				the toughness of this unit is set to this.getMaxBaseStat(). 
	 *       		|if (toughness > this.getMaxBaseStat()) 
	 *       		|	then new.getToughness() == this.getMaxBaseStat()
	 */
	public void setToughness(int toughness) {
		if (this.canHaveAsToughness(toughness)) {
			this.toughness = toughness;
		} else if (toughness < Unit.getMinBaseStat()) {
			this.toughness = Unit.getMinBaseStat();
		} else if (toughness > Unit.getMaxBaseStat()) {
			this.toughness = Unit.getMaxBaseStat();
		}
	}
	
	/**
	 * Sets the initial toughness of this unit to the given toughness.
	 * 
	 * @param	toughness
	 * 					The toughness for this new unit,
	 * 
	 * @post	If the given toughness is in the range getMinInitialBaseStat()..getMaxInitialBaseStat(), the toughness of this unit is equal to the given toughness. 
	 *       		| if ( (toughness >= getMinInitialBaseStat()) && (toughness <=getMaxInitialBaseStat()) ) 
	 *       		| 	then new.getToughness() == toughness
	 * @post	If the given toughness is lower than getMinInitialBaseStat(), the toughness of this unit is equal to getMinInitialBaseStat(). 
	 *       		| if (toughness < getMinInitialBaseStat()) 
	 *       		|	then new.getToughness() == getMinInitialBaseStat()
	 * @post	If the given toughness exceeds getMaxInitialBaseStat(), the toughness of this unit is equal to getMaxInitialBaseStat(). 
	 *       		| if (toughness > getMaxInitialBaseStat()) 
	 *       		| 	then new.getToughness() == getMaxInitialBaseStat()
	 */
	public void setInitialToughness(int toughness) {
		if (toughness > getMaxInitialBaseStat()) {
			this.setToughness(getMaxInitialBaseStat());
		} else if (toughness < getMinInitialBaseStat()) {
			this.setToughness(getMinInitialBaseStat());
		} else {
			this.setToughness(toughness);
		}
	}
	
	/**
	 * A variable that registers the toughness of this unit.
	 */
	private int toughness;

	// ==============================================================================
	// Methods concerning the faction. (bidirectional association)
	// Terminated units must still satisfy their invariants.
	// Their faction is set to null.
	// ==============================================================================

	/**
	 * Return the faction this unit is part of.
	 */
	@Basic @Raw
	public Faction getFaction() {
		return this.faction;
	}
	
	/**
	 * Checks whether this unit can have the given faction as its faction.
	 * 
	 * @param 	faction
	 *			The faction to check.
	 * @return	If this unit is terminated, true if the given faction
	 * 			is not effective.
	 * 			| if (isTerminated())
	 * 			|	then result == (faction == null)
	 * 			Else, true if the given faction is effective
	 * 			and not yet terminated.
	 * 			| else
	 * 			|	then result == (faction != null) && (!faction.isTerminated())
	 */
	@Raw
	public boolean canHaveAsFaction(@Raw Faction faction) {
		if (this.isTerminated)
			return faction == null;
		else
			return (faction != null) && (! faction.isTerminated());
	}
	
	/**
	 * Checks whether this unit has a proper faction to which it is attached.
	 * 
	 * @return	True if and only if this unit can have the faction to which it
	 * 			is attached as its faction, and if that faction is either not
	 * 			effective or has this unit as one of its units.
	 * 			| (this.canHaveAsFaction(this.getFaction()) 
	 *			|  && ( (this.getFaction() == null) || (this.getFaction().hasAsUnit(this))) )
	 */
	public boolean hasProperFaction() {
		return ( this.canHaveAsFaction(this.getFaction()) 
				&& ( ( this.getFaction() == null) || (this.getFaction().hasAsUnit(this)) ) );
	}
	
	/**
	 * Sets the faction to which this unit is attached to to the given faction.
	 * 
	 * @param	faction
	 * 			The faction to attach this unit to.
	 * @post	This unit references the given faction as the faction to which it is attached.
	 * 			| new.getFaction() == faction
	 * @throws	IllegalArgumentException
	 * 			This unit cannot have the given faction as its faction,
	 * 			or the faction is at its maximum capacity.
	 * 			| (! canHaveAsFaction(faction)) || (faction.getNbUnits() >= World.MAX_UNITS_FACTION)
	 */
	public void setFaction(@Raw Faction faction) throws IllegalArgumentException {
		if ( (! this.canHaveAsFaction(faction)) || (faction.getNbUnits() >= World.MAX_UNITS_FACTION) )
			throw new IllegalArgumentException(faction.toString());
		this.faction = faction;
	}
	
	/**
	 * A variable referencing the faction to which this unit is attached.
	 */
	private Faction faction;
	
	// ======================================================================================
	// Methods for health and stamina.
	// ======================================================================================
	
	/**
	 * Return the current health of this unit.
	 */
	@Basic	@Raw
	public double getCurrentHealth() {
		return this.currentHealth;
	}
	
	/**
	 * Check whether the given current health is a valid current health for any unit.
	 * 
	 * @param	currentHealth
	 * 					The current health to check.
	 * @return	True only if the current stamina is greater than or equal to 0,
	 *         			and lesser than or equal to its max value. 
	 *         			| result == ((currentStamina >= 0) && (currentStamina <= getMaxPoints()))
	 */
	public boolean canHaveAsCurrentHealth(double currentHealth) {
		return (currentHealth >= 0) && (currentHealth <= this.getMaxPoints());
	}
	
	/**
	 * Set the current health of this unit to the given current health.
	 * 
	 * @param	currentHealth
	 *          		The new current health for this unit.
	 * @pre 	The given current health must be a valid current health for any unit. 
	 *      		| canHaveAsCurrentHealth(currentHealth)
	 * @post	The current health of this unit is equal to the given currentHealth. 
	 *       		| new.getCurrentHealth() == currentHealth
	 */
	private void setCurrentHealth(double currentHealth) {
		assert canHaveAsCurrentHealth(currentHealth);
		this.currentHealth = currentHealth;
	}
	
	/**
	 * Variable that registers the current health of this unit.
	 */
	private double currentHealth;
	
	/**
	 * Return the current stamina of this unit.
	 */
	@Basic	@Raw
	public double getCurrentStamina() {
		return this.currentStamina;
	}
	
	/**
	 * Check whether the given current stamina is a valid current stamina for any unit.
	 * 
	 * @param	currenStamina
	 * 					The current stamina to check.
	 * @return	True only if the current stamina is greater than or equal to 0,
	 *         			and lesser than or equal to its maximal value. 
	 *         			| result == ((currentStamina >= 0) && (currentStamina <= getMaxPoints()))
	 */
	public boolean canHaveAsCurrentStamina(double currentStamina) {
		return ((currentStamina >= 0) && (currentStamina <= this.getMaxPoints()));
	}
	
	/**
	 * Set the current stamina of this unit to the given current stamina.
	 * 
	 * @param	newCurrentStamina
	 *            		The new current stamina for this unit.
	 * @pre 	The given current stamina must be a valid current stamina for any unit.
	 *      	 	| canHaveAsCurrentStamina(currentStamina)
	 * @post	The current stamina of this unit is equal to the given current stamina. 
	 *       		| new.getCurrentStamina() == currentStamina
	 */
	private void setCurrentStamina(double newCurrentStamina) {
		assert canHaveAsCurrentStamina(newCurrentStamina);
		this.currentStamina = newCurrentStamina;
	}
	
	/**
	 * Variable registering the current stamina of this unit.
	 */
	private double currentStamina;
	
	/**
	 * Returns the maximum points of health and stamina of this unit, based on this units weight and toughness.
	 * 
	 * @return The maximum points for health and stamina are both determined as
	 *         			 200 * weight/100 * toughness/100, rounded up to the next integer. 
	 *         			| result == (int) Math.ceil(getWeight() * getToughness() / 50.0)
	 */
	public int getMaxPoints() {
		if (this.hasItem())
			return this.getMaxPoints(this.getWeight()-this.getItem().getWeight(),this.getToughness());
		else
			return getMaxPoints(this.getWeight() , this.getToughness());
	}

	/**
	 * Returns the maximum points of health and stamina of this unit, based on the given weight and toughness.
	 * 
	 * @param	weight
	 *         			The given weight is used for calculating the max value of this unit health and stamina.
	 * @param	toughness
	 *          		The given thoughness is used for calculating the max value.
	 * @return	The maximum points for health and stamina are both determined as
	 *         			200 * weight/100 * toughness/100, rounded up to the next integer. 
	 *         			| result == (int) Math.ceil(getWeight() * getToughness() / 50.0)
	 */
	public int getMaxPoints(int weight, int toughness) {
		return (int) Math.ceil(weight * toughness / 50.0);
	}
	
	/* Methods for positions and coordinates. */
	
	/**
	 * Returns the length of one cube.
	 */
	@Basic @Raw
	public int getCubeLength(){
		return Cube.CUBE_LENGHT;
	}

	/**
	 * Return the exact position of this unit.
	 */
	@Basic @Raw
	public UnitPosition getPosition(){
		return (UnitPosition) this.position;
	}
 	
 	/**
 	 * Returns the position of the 2D-center of the cube this unit is standing in.
 	 * The 2D-center is defined as the center in the XY-plane. The z-coordinate
 	 * is the same as this unit's z-coordinate.
 	 * 
 	 * @return	The coordinates of the center of the cube this unit occupies.
 	 * 				| result == getSurfaceCenter(getPosition())
 	 */
 	public double[] getSurfaceCenter() {
 		return (Position.getSurfaceCenter(this.getPosition().getCoordinates()));
 	}
 
	/**
	 * Sets the position of this unit to the given position.
	 *  
	 * @param	position
	 * 					The new position of this unit.
	 * @throws 	IllegalArgumentException
	 * 					The given position is not a valid position for any unit..
	 * 					|! canHaveAsPosition(getPosition())
	 */
	public void setCoordinates(double[] position) throws IllegalArgumentException{
		this.position.setCoordinates(position);
	}
	
	
	//===============================================================================
	// Methods concerning the world. (bidirectional association)
	// Terminated units must still satisfy their invariants.
	// Their world is set to null.
	//===============================================================================

	/**
	 * Return the world this unit is part of.
	 */
	@Basic @Raw
	public World getWorld() {
		return this.world;
	}
	
	/**
	 * Checks whether this unit can have the given world as its world.
	 * 
	 * @param 	world
	 *			The world to check.
	 * @return	If this unit is terminated, true if the given world
	 * 			is not effective.
	 * 			| if (isTerminated())
	 * 			|	then result == (world == null)
	 *			If the world has this unit as one of its units, return true.
	 *			| else if (world.hasAsEntity(this))
	 *			|	then result == true
	 * 			Else, true if the given world is effective,
	 * 			not yet terminated and has room for units.
	 * 			| else
	 * 			|	then result == (world != null) && (!world.isTerminated())
	 */
	@Raw
	@Override
	public boolean canHaveAsWorld(@Raw World world) {
		if (this.isTerminated)
			return world == null;
		else if (world.hasAsEntity(this))
			return true;
		else
			return (world != null) && (! world.isTerminated()) && (world.getNbUnits() < World.MAX_UNITS_WORLD);
	}
	
	/**
	 * Checks whether this unit has a proper world to which it is attached.
	 * 
	 * @return	True if and only if this unit can have the world to which it
	 * 			is attached as its world, and if that world is either not
	 * 			effective or has this unit as one of its units.
	 * 			| (this.canHaveAsWorld(this.getWorld()) 
	 *			|  && ( (this.getWorld() == null) || (this.getWorld().hasAsUnit(this))) )
	 */
	public boolean hasProperWorld() {
		return ( this.canHaveAsWorld(this.getWorld()) 
				&& ( ( this.getWorld() == null) || (this.getWorld().hasAsEntity(this)) ) );
	}
	
	/**
	 * Sets the world to which this unit is attached to to the given world.
	 * 
	 * @param	world
	 * 			The world to attach this unit to.
	 * @post	This unit references the given world as the world to which it is attached.
	 * 			| new.getWorld() == world
	 * @throws	IllegalArgumentException
	 * 			This unit cannot have the given world as its world,
	 * 			Or the world is at its maximum capacity.
	 * 			| (! canHaveAsWorld(world)) || (world.getNbUnits() >= World.MAX_UNITS_WORLD)
	 */
	public void setWorld(@Raw World world) throws IllegalArgumentException {
		if ( (! this.canHaveAsWorld(world)) || (world.getNbUnits() >= World.MAX_UNITS_WORLD) ){
			
			throw new IllegalArgumentException();
	}
//		System.out.println("number of units in the world: " + world.getNbUnits()  );
		this.world = world;
	}
	
	/**
	 * A variable referencing the world to which this unit is attached.
	 */
	private World world;
	
	
	// ===================================================================================
	// Methods for initial coordinates and target coordinate variables.
	// ===================================================================================
	
	/**
 	 * Return the target coordinates of this unit.
 	 */
 	@Basic @Raw
 	public double[] getTargetCoordinates() {
 		return this.targetCoordinates;
 	}
 	
 	/**
 	 * Check whether the given target coordinates are valid target coordinates for this unit.
 	 *  
 	 * @param	targetCoordinates
 	 *         			The target coordinates to check.
 	 * @return	True if the target coordinates are null, or are valid coordinates for this unit in its world.
 	 *       			| result == (targetCoordinates == null || canHaveAsCoordinates(targetCoordinates))
 	*/
 	public boolean canHaveAsTargetCoordinates(double[] targetCoordinates) {
 		return( (targetCoordinates == null) || this.getPosition().canHaveAsUnitCoordinates(targetCoordinates) );
 	}
 	
 	/**
 	 * Set the target coordinates of this unit to the given target coordinates.
 	 * 
 	 * @param	targetCoordinates
 	 *         			The new target coordinates for this unit.
 	 * @post	The target coordinates of this new unit are equal to the given target coordinates.
 	 *       		| new.getTargetCoordinates() == targetCoordinates
 	 * @throws 	IllegalArgumentException
 	 *         			The given target coordinates are not valid target coordinates for any unit.
 	 *       			| ! canHaveAsTargetCoordinates(getTargetCoordinates())
 	 */
 	@Raw
 	public void setTargetCoordinates(double[] targetCoordinates) throws IllegalArgumentException {
 		if (! canHaveAsTargetCoordinates(targetCoordinates))
 			throw new IllegalArgumentException("The given target coordinates are invalid.");
 		this.targetCoordinates = targetCoordinates;
 	}
 
  	/**
  	 * A variable that stores the target coordinates of this unit.
   	 */		   
   	private double[] targetCoordinates;

 	/**
 	 * Return the initial coordinates of this unit.
 	 */
 	@Basic @Raw
 	public double[] getInitialCoordinates() {
 		return this.initialCoordinates;
 	}
 	
 	/**
 	 * Check whether the given initial coordinates are valid initial coordinates for this unit.
 	 *  
 	 * @param	initialCoordinates
 	 *         			The initial coordinates to check.
 	 * @return	True if the initial coordinates are null, or are valid coordinates for this unit in its world.
 	 *       			| result == (initialCoordinates == null || canHaveAsCoordinates(initialCoordinates))
 	*/
 	public  boolean canHaveAsInitialCoordinates(double[] initialCoordinates) {
 		return ( (initialCoordinates == null) || (this.getPosition().canHaveAsUnitCoordinates(initialCoordinates)) );
 	}
 	
 	/**
 	 * Set the initial coordinates of this unit to the given initial coordinates.
 	 * 
 	 * @param	initialCoordinates
 	 *         			The new initial coordinates for this unit.
 	 * @post	The initial coordinates of this new unit are equal to the given initial coordinates.
 	 *       		| new.getInitialCoordinates() == initialCoordinates
 	 * @throws 	IllegalArgumentException
 	 *         			The given initial coordinates are not valid initial coordinates for any unit.
 	 *       			| ! canHaveAsInitialCoordinates(getInitialCoordinates())
 	 */
 	@Raw
 	public void setInitialCoordinates(double[] initialCoordinates) throws IllegalArgumentException {
 		if (! canHaveAsInitialCoordinates(initialCoordinates))
 			throw new IllegalArgumentException("The given initial coordinates are invalid.");
 		this.initialCoordinates = initialCoordinates;
 	}
 
   	/**
   	 * A variable that stores the initial coordinates of this unit (before it moved).
   	 */
   	private double[] initialCoordinates;
   	
   	/**
   	 * Resets the target coordinates and initial coordinates of this unit.
   	 * 
   	 * @post	The initial coordinates and target coordinates are both equal to null;
   	 * 			| new.getInitialCoordinates() == null && new.getTargetCoordinates() == null
   	 */
   	private void resetCoordinates() {
   		this.setTargetCoordinates(null);
   		this.setInitialCoordinates(null);
   	}
   	
   	
	//============================================================================================
	// Methods concerning distance and speed.
	//============================================================================================
	
   	/**
	 * Returns the distance between this unit's current position and the center of the target cube.
	 * @param	targetCoordinates
	 * 					The coordinates of the target.
	 * @return	The distance based on the mathematical formula of distance in 3 dimensions.
	 * 					| result == PositionUtil.getDistance(this.getPosition().getCoordinates(), targetCoordinates);
	 */
	public double getDistance(double[] targetCoordinates) {
		return Position.getDistance(this.getPosition().getCoordinates(), targetCoordinates);
	}
	
 	/**
	 * Returns the base speed of this unit.
	 * @return	The base speed is given as 1.5*(agility+strength)/(2.0*weight).
	 * 					| result == 1.5 * (getStrength() + getAgility()) / (2.0 * getWeight());
	 */
	public double getBaseSpeed() {
		return 1.5 * (this.getStrength() + this.getAgility()) / (2.0 * this.getWeight());
	}
	
	/**
	 * Returns the walking speed of the unit if it walks between the
	 * given coordinates.
	 * @param	start
	 * 					The starting coordinates.
	 * @param	targetCoordinates
	 * 					The coordinates to move to.
	 * @return	The walking speed, depending on the target coordinates.
	 * 					If the difference between start z-coordinate and the target z-coordinate is negative,
	 * 					the walking speed equals to 0.5 * baseSpeed.
	 * 					If the difference between start z-coordinate and the target z-coordinate is positive,
	 * 					the walking speed equals to 1.2 * baseSpeed.
	 * 					Else the walking speed is equal to the base speed.
	 * 					| if (Math.signum(start[2]-targetCoordinates[2]) < 0)
	 * 					|	then result == 0.5 * getBaseSpeed()
	 * 					| else if (Math.signum(start[2]-targetCoordinates[2]) > 0)
	 * 					|	then result == 1.2 * getBaseSpeed()
	 * 					| else
	 * 					|	result ==  getBaseSpeed()
	 */		
	public double getWalkSpeed(double[] start, double[] targetCoordinates){
		double sign = Math.signum(start[2]-targetCoordinates[2]);
		if (sign < 0 && sign != Double.NaN)
			return 0.5 * this.getBaseSpeed();
		else if (sign > 0 && sign != Double.NaN)
			return 1.2 * this.getBaseSpeed();
		else
			return this.getBaseSpeed();
	}
	
 	/**
	 * Returns the walking speed of this unit.
	 * @param	targetCoordinates
	 * 					The coordinates to move to.
	 * @return	The walking speed from this unit's current coordinates to the target coordinates.
	 * 			| result == (getWalkSpeed(this.getPosition().getCoordinates(), targetCoordinates))
	 */		
	public double getWalkSpeed(double[] targetCoordinates){
		return (this.getWalkSpeed(this.getPosition().getCoordinates(), targetCoordinates));
	}
	
	/**
	 * Returns the sprinting speed of the unit.
	 * @param	targetCoordinates
	 * 					The coordinates to move to.
	 * @return	The sprinting speed of the unit.
	 * 					| result == getWalkSpeed()
	 */
	public double getSprintSpeed(double[] targetCoordinates) {
		return 2*this.getWalkSpeed(targetCoordinates);
	}
 	
	/**
	 * Returns the velocity vector of this unit 
	 * based on its current coordinates and some given coordinates.
	 * 
	 * @param	targetCoordinates
	 * 				The given destination.
	 * @return	The velocity vector as calculated in Position.getVelocity()
	 * 				| result == Position.getVelocity(this.getPosition().getCoordinates(), targetCoordinates, speed)
	 */
	public double[] getVelocity(double[] targetCoordinates) {
		double speed = 0.0;
		if ((this.getIsSprinting()) && this.getCurrentStamina() > 0)
			speed = this.getSprintSpeed(targetCoordinates);
		else
			speed = this.getWalkSpeed(targetCoordinates);
		return (UnitPosition.getVelocity(this.getPosition().getCoordinates(), targetCoordinates, speed));
	}
	
	/**
	 * Returns the velocity vector of this unit 
	 * based on its current coordinates and target coordinates.
	 * 
	 * @return	The velocity vector between target coordinates and current coordinates for this unit.
	 * 				| result == getVelocity(this.getTargetCoordinates())
	 * @throws	NullPointerException
	 * 				The unit's target coordinates are null.
	 */
	public double[] getVelocity() throws NullPointerException {
		if (this.getTargetCoordinates() == null)
			throw new NullPointerException();
		return this.getVelocity(this.getTargetCoordinates());
	}
	
	/**
	 * Checks if the target cube with given coordinates is next to the cube the unit is standing in.
	 * 
	 * @param	targetCube
	 * 					The cube to check.
	 * @return	True if and only if the coordinates are the same or next to each other.
	 * 					| result == Position.isAdjacentTo(this.getCubePosition().getCubeCoordinates, targetCube)
	 * @throws	IllegalArgumentException
	 * 					The target cube is invalid for this unit in it's world.
	 */
	@Raw
	public boolean isAdjacentTo(int[]targetCube) throws IllegalArgumentException {
		if (!this.getWorld().canHaveAsCoordinates(targetCube[0],targetCube[1],targetCube[2]))
			throw new IllegalArgumentException(Arrays.toString(targetCube));
	
		return (UnitPosition.isAdjacentTo(this.getPosition().getCubeCoordinates(), targetCube));
	}
	
	
	//======================================================================================
	// Methods for moving and sprinting.
	//======================================================================================
	
	/**
	 * Checks whether this unit is moving.
	 * 
	 * @return	True if and only if this unit's current activity is set to MOVE.
	 * 					|result == (this.getCurrentActivity() == Activity.MOVE)
	 */
	@Basic @Raw
	public boolean isMoving(){
		return (this.getCurrentActivity() == Activity.MOVE);
	}

	/**
	 * Return the is-sprinting value of this unit.
	 */
	@Basic @Raw
	public boolean getIsSprinting() {
		return this.isSprinting;
	}
	
	/**
	 * Check whether the given value is a valid is-sprinting value for
	 * this unit.
	 *  
	 * @param	is-sprinting
	 *         			The is-sprinting value to check.
	 * @return	The unit can only sprint if it has stamina left
	 * 			and is not terminated.
	 * 			| if (isSprinting == true)
	 *       	| 	then result == (getCurrentStamina > 0) && (! isTerminated())
	 *       	A unit can always stop sprinting.
	 *       	| else (return true)
	 *       
	 */ 		
	public boolean isValidIsSprinting(boolean isSprinting) {
		if (isSprinting)
			return (this.getCurrentStamina()>0) && (! this.isTerminated());
		else
			return true;
	}
	
	/**
	 * Set the is-sprinting property of this unit to the given value.
	 * 
	 * @param	isSprinting
	 *         			The new is-sprinting value for this unit.
	 * @post	The is-sprinting value of this new unit is equal to the given is-sprinting value.
	 *       		| new.getIsSprinting() == isSprinting
	 * @throws 	IllegalStateException
	 *         			The given is-sprinting value is not a valid is-sprinting for any unit.
	 *       			| ! isValidIsSprinting(getIsSprinting())
	 */
	@Raw
	public void setIsSprinting(boolean isSprinting) throws IllegalStateException {
		if (! isValidIsSprinting(isSprinting))
			throw new IllegalStateException("This unit cannot sprint.");
		this.isSprinting = isSprinting;
	}
	
	/**
	 * A variable that registers whether this unit is sprinting or not.
	 */
	private boolean isSprinting;

	/**
	 * Makes this unit start sprinting, if possible.
	 * @effect	The unit's sprint value is set to true.
	 * 			| setIsSprinting(true)
	 */
	@Raw
	public void startSprinting(){
		try{
			if ( ! this.isFalling())
				this.setIsSprinting(true);
		} catch (IllegalStateException e){}
	}
	
	/**
	 * Makes this unit stop sprinting. 
	 */
	@Raw
	public void stopSprinting(){
		this.setIsSprinting(false);
	}
	
	/**
	 * Initiates movement to the given coordinates.
	 * @param	dx
	 * 				The move to make in the x-direction.
	 * @param	dy
	 * 				The move to make in the y-direction.
	 * @param	dz
	 * 				The move to make in the z-direction.
	 * @effect Initiate movement to the given coordinates and disable default behaviour.
	 *				| moveToAdjacent(dx, dy, dz, false)
	 */
	public void moveToAdjacent(int dx, int dy, int dz) throws IllegalArgumentException{
		this.moveToAdjacent(dx, dy, dz, false);
 	}	
	
	/**
	 * Initiates movement to the given coordinates.
	 * @param	dx
	 * 				The move to make in the x-direction.
	 * @param	dy
	 * 				The move to make in the y-direction.
	 * @param	dz
	 * 				The move to make in the z-direction.
	 * @param thisIsDefaultBehaviour
	 *				Whether the movement is initiated by default behaviour or the player.
	 * @post	The current activity of the unit is set to MOVE.
	 *				| new.getCurrentActivity() == MOVE
	 * @post	The target coordinates of the unit are set to the cube decided by the given direction.
	 * 				| new.getTargetCoordinates() =={this.getPosition().getCoordinates()[0] + dx,
	 * 				|							this.getPosition().getCoordinates()[1] + dy,this.getPosition().getCoordinates()[2] + dz}
	 * @post	The initial coordinates of the unit are set to the current coordinates.
	 * 				| new.getInitialCoordinates() == this.getPosition().getCoordinates()
	 * @note Throws no exception because Exception is caught.
	 */
	private void moveToAdjacent(int dx, int dy, int dz, boolean thisIsDefaultBehaviour) throws IllegalArgumentException{
		// TODO Moving to an adjacent cube may only be interrupted if the unit is attacked, or falling. Done?
		try {
			if ( ! thisIsDefaultBehaviour)
				this.setDefaultBehaviorEnabled(false);
			
			if ((this.getCurrentActivity() == Activity.MOVE))
				throw new IllegalStateException("Unit is already moving.");
			
			Cube destinationCube = this.getWorld().getCube(
					this.getPosition().getCubeCoordinates()[0]+dx, this.getPosition().getCubeCoordinates()[1]+dy, this.getPosition().getCubeCoordinates()[2]+dz);
			
			if ((dx==0) && (dy==0) && (dz==0)) {
				throw new IllegalArgumentException("No need to move unit.");
			}
			if (! destinationCube.isPassable()){
				throw new IllegalArgumentException(destinationCube.toString());
			}

			double[] dummyCoordinates = {this.getPosition().getCoordinates()[0] + dx,this.getPosition().getCoordinates()[1] + dy,this.getPosition().getCoordinates()[2] + dz};

	 		this.setCurrentActivity(Activity.MOVE);
	 		this.setTargetCoordinates(dummyCoordinates);
	 		this.setInitialCoordinates(this.getPosition().getCoordinates());
		} 
		catch (Exception e) {
		}
	}
	
	// TODO finish?
	/**
	 * Checks whether this unit can start moving to an adjacent cube.
	 * A unit can only start moving if it is not falling.
	 * @return True if this unit is not falling.
	 *				| ( ! this.isFalling())
	 */
	public boolean canDoMoveToAdjacent() {
		return ( ! this.isFalling());
	}
	
	/**
	 * Initializes movement for this unit to the given cube coordinates and disables default behaviour.
	 * @param	destinationCoordinates
	 * 					The destination cube.
	 * @effect Initializes movement for this unit to the given cube coordinates and disables default behaviour.
	 *				| this.moveTo(destinationCoordinates, false)
	 * @throws	IllegalArgumentException
	 * 			The given destination is invalid.
	 */
	public void moveTo(int[] destinationCoordinates) throws IllegalArgumentException{
		this.moveTo(destinationCoordinates,false);
	}
	
	/**
	 * Initializes movement for this unit to the given cube position by filling moveToPath.
	 * @param	destinationCoordinates
	 * 					The destination cube.
	 * @param	thisIsDefaultBehaviour
	 *				Whether attacking is called by default behaviour or not.
	 *
	 * @throws	IllegalArgumentException
	 * 				The given destination is invalid.
	 *				| (! this.getPosition().canHaveAsUnitCoordinates(destinationCoordinates))
	 * @note Silent reject for:
	 *				no adjacent solid cube (makes a unit go to mid air, which will make it fall), 
	 *				target is unreachable because is is surrounded by solid cubes,
	 *				this unit cannot move away from it's current position because it is locked in by solid cubes.
	 *				This prevents the program from trying all possible cubes in the world (which takes very long, even for small worlds),
	 *				while we know there is no path.
	 */
	/* TODO there seems to be some bug: when the path is a U-shape (on a single z-level)
	 * the unit walks towards the cube right before the final one, and then suddenly flashes to
	 * its destination.
	 */
	private void moveTo(int[] destinationCoordinates, boolean thisIsDefaultBehaviour) throws IllegalArgumentException{
		if (thisIsDefaultBehaviour)
			System.out.println("moveTo by defaultBehaviour");
		if ( ! this.getPosition().canHaveAsUnitCoordinates(destinationCoordinates)) {// Checking if the target cube is passable happens in canHaveAsUnitCoordinates.	
			throw new IllegalArgumentException();
		}
		
		if ((this.hasSolidNeighbours(destinationCoordinates))
				&& (this.hasPassableNeighbours(destinationCoordinates))
				&& (this.hasPassableNeighbours(this.getPosition().getCubeCoordinates()))){
		
			if ( ! thisIsDefaultBehaviour)
				this.setDefaultBehaviorEnabled(false);
			
			// Readability
			int[] currentCoordinates =  this.getPosition().getCubeCoordinates();	
			
	 		if (moveToPath.size() != 0){
	 			// Clear the moveToPath to interrupt an earlier moveTo. 
	 			// A moveToAdjacent should never be interrupted so keep the one next move in line (and only that one). 
	 			int[] dummy = moveToPath.get(0);
	 			moveToPath.clear();
	 			moveToPath.add(dummy);
			}
		 		// Pass the current activity trough to previousActivity and Set the current activity op MOVE.
		 		this.setPreviousActivity(this.getCurrentActivity());
		 		this.setCurrentActivity(Activity.MOVE);
		 		this.setInitialCoordinates(this.getPosition().getCoordinates());
		 		
				moveToPath = searchPath(currentCoordinates, destinationCoordinates);
		}
	}
	
	private List<int[]> searchPath(int[] startCoordinates, int[] destinationCoordinates) {
		return PathFinder.getPath(startCoordinates, destinationCoordinates, this.getWorld(), false); 
		/* Pick true if you want to allow diagonal movement in moveTo.*/
	}

	/**
	 * A list registering the path this unit has to follow.
	 * It is filled when executing moveTo() and it is used during advanceTime
	 * to decide the next coordinates to move to.
	 */
	private List<int[]> moveToPath = new ArrayList<>();
	
	/**
   	 * Cancels the move-to command of this non-terminated unit.
   	 * 
   	 * @post	The unit's target coordinates, initial coordinates and moveToPath are reset.
   	 * 			The unit stands in the center of its current cube and does nothing.
   	 * 
   	 */
   	public void cancelMoveTo(){
   		try {
	   		this.resetCoordinates();
	   		this.setCurrentActivity(Activity.NOTHING);
	   		this.setCoordinates(this.getCubeCenter());
	   		this.moveToPath.clear();
   		} catch(IllegalStateException e) {}
   	}
	
	
	// ================================================================================================
	// Methods for falling.
	// ================================================================================================
	
	/**
	 * Checks if this unit is falling.
	 * 
	 * @return	True if none of the neighbouring cubes is solid
	 * 			and this unit stands in the center of a cube.
	 * 			True if the is-falling property is true.
	 * 			False if if the z-coordinate of this unit's cube is 0.
	 * 			| if (isFalling)
	 * 			|	then result == true
	 * 			| else if (getCubePosition()[2] == 0)
	 *			|	then result == false
	 * 			| else
	 * 			| 	result == !hasSolidNeighbours(this.getXCoordinate(), this.getYCoordinate(), this.getZCoordinate())
	 * @throws	IllegalArgumentException
	 * 			The unit does not occupy a valid cube.
	 */
	public boolean isFalling() throws IllegalArgumentException {
		// NOTE: do not change the is-falling property in this method itself! 
		// It will break the code (not to mention it is bad practice to return and change values in one method).
		if (this.getIsFalling())
			return true;
		else if (this.getCubeCoordinates()[2] == 0)
			return false;
		else
			return (!hasSolidNeighbours(this.getPosition().getXCoordinate(), this.getPosition().getYCoordinate(), this.getPosition().getZCoordinate())
					 && (Position.fuzzyEquals(this.getPosition().getCoordinates(), this.getCubeCenter())));
	}
	
	/**
	 * Checks if the cube position with the given coordinates is adjacent
	 * to at least one cube which is solid.
	 * 
	 * @param	x
	 * 			The x-coordinate of the cube.
	 * @param	y
	 * 			The y-coordinate of the cube.
	 * @param	z
	 * 			The z-coordinate of the cube.
	 * @return	False if none of the neighbouring cubes is solid.
	 * 			| for (int[] position: neighbours)
	 *			|	if ( ! getWorld().getCube(position).isPassable())
	 * 			|		then result == true
	 * 			| result == false
	 */
	public boolean hasSolidNeighbours(int[] position) {
		Set<int[]> neighbours = this.getWorld().getNeighbours(position);
		for (int[] neighbour: neighbours)
			if ( ! this.getWorld().getCube(neighbour).isPassable())
				return true;
		return false;
	}
	
	/**
	 * Checks if the cube position with the given coordinates is adjacent
	 * to at least one cube which is passable.
	 * 
	 * @param	x
	 * 			The x-coordinate of the cube.
	 * @param	y
	 * 			The y-coordinate of the cube.
	 * @param	z
	 * 			The z-coordinate of the cube.
	 * @return	False if none of the neighbouring cubes is passable.
	 * 			| for (int[] position: neighbours)
	 *			|	if ( getWorld().getCube(position).isPassable())
	 * 			|		then result == true
	 * 			| result == false
	 */
	public boolean hasPassableNeighbours(int[] position) {
		Set<int[]> neighbours = this.getWorld().getNeighbours(position);
		for (int[] neighbour: neighbours)
			if ( this.getWorld().getCube(neighbour).isPassable())
				return true;
		return false;
	}
	
	/**
	 * Checks if the cube which holds the given position 
	 * is not adjacent to a cube which has a solid terrain type.
	 * 
	 * @param	x
	 * 			The x-coordinate.
	 * @param	y
	 * 			The y-coordinate.
	 * @param	z
	 * 			The z-coordinate.
	 * @return	False if none of the neighbouring cubes is solid.
	 * 			| for (Cube cube: getWorld().getNeighbours(x,y,z) )
	 * 			|	if ( cube.isPassable() )
	 * 			|		then result == true
	 * 			| result == false
	 * @throws	IllegalArgumentException
	 * 			The given position is invalid.
	 */
	public boolean hasSolidNeighbours(double x, double y, double z) throws IllegalArgumentException {
		int[] cubePosition = UnitPosition.getCubeCoordinates(new double[]{x,y,z});
		return this.hasSolidNeighbours(cubePosition);
	}	
	
	//===========================================================================================
	// Methods for orientation.
	//===========================================================================================
	
	/**
	 * Return the orientation of this unit.
	 */
	@Basic @Raw
 	public double getOrientation() {
		return this.orientation;
	}
	
	/**
	 * Check whether the given orientation is a valid orientation for  any unit.
	 *  
	 * @param	orientation
	 *         			The orientation to check.
	 * @return	True if and only if the angle is in between -PI and PI, inclusively.
	 *       			| result == (orientation <=PI) && (orientation >= -PI)
	 * @note	The orientation is often the angle in polar coordinates 
	 * 					of a point with cartesic coordinates (x,y). It is mostly calculated
	 * 					using atan(double y, double x). This method returns, under 
	 * 					normal circumstances, a value between -PI and PI. 
	 * 					Exceptional values such as NaN will be changed to the default value (PI/2).
	*/
	@Raw
	public static boolean isValidOrientation(double orientation) {
		return (orientation <=Math.PI) && (orientation >= -Math.PI);
	}
	
	/**
	 * Set the orientation of this unit to the given orientation.
	 * 
	 * @param	orientation
	 *         			The orientation angle for this unit.
	 * @post	If the given orientation is a valid orientation for any unit,
	 *         		the orientation of this new unit is equal to the given orientation
	 *       		| if (isValidOrientation(orientation))
	 *       		|   then new.getOrientation() == orientation
	 */
	@Raw
	private void setOrientation(double orientation) {
		if (isValidOrientation(orientation))
			this.orientation = orientation;
	}
	
 	/**
 	 * Set the orientation of this unit to the given orientation.
 	 * 
 	 * @param	velocityX
 	 * 					The velocity in the x-direction.
 	 * @param	velocityY
 	 * 					The velocity in the y-direction.
 	 * @effect	The orientation is set to the value of arctangent of velocityY and velocityX.
 	 * 					| setOrientation(Math.atan2(velocityY,velocityX))
 	 */
 	public void setOrientation(double velocityX, double velocityY ) {
 		double orientation = Math.atan2(velocityY, velocityX);
 		setOrientation(orientation);
 	}
	
	/**
	 * A variable that registers the orientation of this unit.
	 */
	private double orientation;
	
	// ==================================================================================
	// Methods concerning the task of this unit.
	// ==================================================================================
	
	/**
	 * Return the task of this unit.
	 */
	@Basic @Raw
	public Task getTask() {
		return this.task;
	}
	
	/**
	 * Check whether this unit has a task.
	 * @return	True if the task of this unit is effective.
	 * 			| result == (getTask() != null)
	 */
	public boolean hasTask() {
		return this.getTask() != null;
	}
	
	/**
	 * Checks whether this unit has a proper task attached to it.
	 * 
	 * @return	True if and only if the task of this unit does not reference
	 * 			an effective task, or that task references this unit
	 * 			as its unit.
	 * 			| result == ( (this.getTask() == null) || 
	 * 			|				(this.getTask().getUnit() == this) )
	 */
	public boolean hasProperTask() {
		return ( (this.getTask() == null) || (this.getTask().getUnit() == this) );
	}
	
	/**
	 * Sets the task attached to this unit to to the given task.
	 * 
	 * @param	task
	 * 			The task to attach to this unit.
	 * @post	This unit references the given task as its task.
	 * 			| new.getTask() == task
	 * @throws	IllegalArgumentException
	 * 			The given task is effective but does not references
	 * 			this unit as its unit.
	 * 			| (task != null) && (task.getUnit() != this)
	 * 			Or, the task is not effective and this unit references
	 * 			a task which still references this unit as its unit.
	 * 			| (task == null) && (this.hasTask() && (this.getTask().getUnit() == this))
	 */
	public void setTask(@Raw Task task) throws IllegalArgumentException {
		if ( (task != null) && (task.getUnit() != this) )
			throw new IllegalArgumentException("Task does not properly associate this unit.");
		if ( (task == null) && (this.hasTask() && (this.getTask().getUnit() == this)))
			throw new IllegalArgumentException("Link with current task not properly broken.");
		this.task = task;
	}
	
	/**
	 * A variable referencing the task attached to this unit.
	 */
	private Task task;
	
	/**
	 * Finish the task of this unit, if necessary.
	 * 
	 * @effect	The task of this unit is finished.
	 * 			| if this.hasTask()
	 *			|	then this.getTask().setFinished(true)
	 * @post	The task of his unit references no scheduler and
	 * 			its old schedulers no longer reference this task.
	 * 			| new.getTask().getScheduler().size() == 0 &&
	 * 			| for each scheduler in this.getTask().getSchedulers()
	 * 			|	! scheduler.hasAsTask(new.getTask())
	 */
	//TODO fix doc, check code
	private void finishTask() {
		if (this.hasTask()) 
			this.getTask().terminate();
	}
	
	// ==================================================================================
	// Methods for the different activities.
	// ==================================================================================
	
	/**
	 * Returns the current activity.
	 */
	@Basic @Raw
	public Activity getCurrentActivity(){
		return this.currentActivity;
	}
	
	/**
	 * Sets the current activity of this non-terminated unit.
	 * 
	 * @param	activity
	 * 				The new activity of this unit.
	 * 
	 * @post	The given activity is equal to the current activity.
	 * 				| new.getCurrentActivity() == activity
	 * @throws	IllegalStateException
	 * 				The unit is terminated.
	 * 				| isTerminated()
	 */
	@Raw
	public void setCurrentActivity(Activity activity) {
		if (this.isTerminated())
			throw new IllegalStateException("unit terminated.");
		this.currentActivity = activity;
	}
	
	/**
	 * A variable that stores the current activity of this unit. The default value is NOTHING.
	 */
	private Activity currentActivity = Activity.NOTHING;
	
	/**
	 * Checks whether the time until the current activity of this unit is finished, is a valid time.
	 * 
	 * @param	progress
	 * 					The progress of the current activity of this unit.
	 * 
	 * @return	True if and only the given progres, is greater than or equal to zero.
	 * 					| result == timeToEndOfActivity >= 0
	 */
	@Raw
	public static boolean canHaveAsProgress(double progress){
		return progress >= 0 ;
	}
	
	/**
	 * Returns the progress of the current activity.
	 */
	@Basic @Raw
	public double getProgress(){
		return this.progress;
	}
	
	/**
	 * Sets the time to complete a given activity.
	 * 
	 * @param	progress
	 * 					The new progress of the current activity of this unit..
	 * 
	 * @post	The new progress of this unit is equal the the given progress..
	 * 				| new.getTimeToEndOfActivity() == timeToEndOfActivity()
	 */
	private void setProgress(double progress) throws IllegalArgumentException{
		if (progress < 0){
			throw new IllegalArgumentException("The time to the end of the activity cannot be negative");
		}
		this.progress = progress;
	}
	
	/**
	 * A variable that stores the progress of this units current activity.
	 */
	private double progress;
	
	/**
	 * Returns how long it takes to perform work one time.
	 * 
	 * @return	The time it takes to perform work one time is given by 500.0/strength.
	 * 					|result == 500.0/this.getStrength()
	 */
	public double getWorkDuration(){
		return 500.0/this.getStrength();
	}
	
	/**
	 * Checks whether this unit is working.
	 * 
	 * @return True if and only if the current activity is WORK.
	 * 					|result == this.getCurrentActivity() == Activity.WORK
	 */
	@Raw
	public boolean isWorking(){
		return this.getCurrentActivity() == Activity.WORK;
	}
	
	/**
	 * Makes this unit work at the given position.
	 * 
	 * @param	workTarget
	 *				The position to perform work at.
	 * @effect Makes this unit work at the given target coordinates.
	 *				| this.workAt(workTarget, false)
	 */
	public void workAt(int[] workTarget) throws NullPointerException, IllegalArgumentException{
		this.workAt(workTarget, false);
	}
	
	/**
	 * Makes this unit work at the given position.
	 * 
	 * @param	workTarget
	 *				The position to perform work at.
	 * @param	thisIsDefaultBehaviour
	 *				Whether working is called by default behaviour or not.
	 * @post	The new current activity of this unit is equal to WORK.
	 * 				|new.getCurrentActivity() == Activity.WORK
	 * @post	The new workTarget of this unit is equal to the given workTarget.
	 * 				|new.getWorkTarget() == workTarget
	 */
	public void workAt(int[] workTarget, boolean thisIsDefaultBehaviour) throws NullPointerException, IllegalArgumentException{
		if (thisIsDefaultBehaviour)
			System.out.println("workAt by defaultBehaviour");
		try {
			
			if ( ! thisIsDefaultBehaviour)
				this.setDefaultBehaviorEnabled(false);
			
			if(this.hasItem())
				if (! this.getItem().canHaveAsCoordinates(workTarget))
					throw new IllegalArgumentException("cannot drop item here.");
			
			
			// MoveToAdjacent may not be interupted except by being attacked.
			if ((this.getCurrentActivity() != Activity.MOVE) && (moveToPath.size() == 0)) {
				this.setProgress(0.0);
				
				// If the workTarget is not in range, then an exception is thrown.
				if (this.isAdjacentTo(workTarget)) {
					this.setWorkTarget(workTarget);
					this.setPreviousActivity(this.getCurrentActivity());
					this.setCurrentActivity(Activity.WORK);
				}
			}
		} 
		catch(IllegalStateException e){
		}
		catch(IllegalArgumentException e) {
		}
	}
	
	/**
	 * Makes this non-terminated unit rest.
	 * 
	 * @effect Makes this unit rest.
	 *				| this.rest(false);
	 * @throws	IllegalStateException
	 * 				The unit cannot currently rest.
	 */
	public void rest() throws IllegalStateException{
		this.rest(false);
	}
	
	/**
	 * Makes this non-terminated unit rest.
	 * 
	 * @param	thisIsDefaultBehaviour
	 *				Whether resting is called by default behaviour or not.
	 * @post	The new current activity of this unit is equal to REST.
	 * 				|new.getCurrentActivity() == Activity.REST
	 * @throws	IllegalStateException
	 * 				The unit cannot currently rest.
	 */
	private void rest(boolean thisIsDefaultBehaviour) throws IllegalStateException{
		// moveToAdjacent() may not be interrupted by resting. 
		// moveTo() can however be interrupted.
		if (thisIsDefaultBehaviour)
			System.out.println("rest by defaultBehaviour");
		try {
			
			if((moveToPath.size() == 0) && (this.getCurrentActivity() == Activity.MOVE) )
				throw new IllegalStateException();
			
			if ( ! thisIsDefaultBehaviour)
				this.setDefaultBehaviorEnabled(false);
			
			if (this.getCurrentActivity() != Activity.REST){
				this.setPreviousActivity(this.getCurrentActivity());
				this.setCurrentActivity(Activity.REST);
			}
		} catch (IllegalStateException e) {}
	}
	
	/**
	 * checks whether this unit is currently resting.
	 * 
	 * @return	True if and only if the current activity of this unit is REST.
	 * 					|result == this.getCurrentActivity() == Activity.REST;
	 */
	@Raw
	public boolean isResting(){
		return (this.getCurrentActivity() == Activity.REST);
	}
	
	/**
	 * checks whether this unit is currently attacking another unit.
	 * 
	 * @return	True if and only if the current activity of this unit is ATTACK.
	 * 					|result == (this.getCurrentActivity() == Activity.ATTACK);
	 */
	@Raw
	public boolean isAttaking(){
		return (this.getCurrentActivity() == Activity.ATTACK);
	}
	
	/**
	 * Returns the time this unit last finished resting.
	 * 
	 * @return	The time this unit last finished resting.
	 * 					| result == this.timeOfLastRest
	 */
	@Raw @Basic
	private double getTimeOfLastRest(){
		return this.timeOfLastRest;
	}
	
	/**
	 * Sets the time since this unit last rested to the current game time.
	 * 
	 * @post	The new time of this units last rest is the current game time.
	 * 				|new.getTimeOfLastRest() == this.GetGametime()
	 */
	@Raw
	private void setTimeOfLastRest(){
		this.timeOfLastRest = this.getGametime();
	}
	
	/**
	 * A variable that stores the game time of the last time this unit finished resting.
	 */
	private double timeOfLastRest = 0;
	
	/**
	 * Sets the current activity from the previous call of advanceTime.
	 * @param	activity
	 * 				The activity to set this.previousActivity to.
	 * @post	the previous activity of this unit is equal to the given activity.
	 * 				|new.previousActivity() == activity 
	 */
	@Raw
	private void setPreviousActivity(Activity activity){
		this.previousActivity = activity;
	}
	
	/**
	 * Returns the previous activity of the unit.
	 * 
	 * @return	The activity of this unit before it was changed.
	 * 				|this.previousActivity
	 */
	@Basic @Raw 
 	public Activity getPreviousActivity(){
		return this.previousActivity;
	}
	
	/**
	 * A variable that stores the activity of this unit before it was interrupted/changed.
	 */
	private Activity previousActivity;
	
	/**
	 * Returns whether this unit has rested long enough to have gained at least one healthpoint.
	 */
	@Basic @Raw
	private boolean getInitialRestTimePassed(){
		return this.initialRestTimePassed;
	}
	
	/**
	 * Sets whether this unit has rested long enough to have gained at least one healthpoint.
	 * @param	value
	 * 					The new value of this parameter
	 * @post	The value of the initialRestTimePassed is equal to the given value.
	 * 				| new.getInitialRestTimePassed() == value
	 */
	@Raw
	private void setInitialRestTimePassed(boolean value){
		this.initialRestTimePassed = value;
	}
	
	/**
	 * A variable that stores whether this unit has rested long enaough to have gained at least one healthpoint.
	 */
	private boolean initialRestTimePassed;
	
	/**
	 * Sets the unit that is under attack of this unit.
	 * 
	 * @param defender
	 *				The new unit under attack of this unit.
	 * @post	The new unit that is under attack of this unit is equal to the given defender.
	 *				| new.getUnitUnderAttack() == defender
	 */
	@Raw
	private void setUnitUnderAttack(Unit defender){
		this.unitUnderAttack = defender;
	}
	
	/**
	 * Returns the unit that is under attack of this unit.
	 */
	@Basic @Raw
	private Unit getUnitUnderAttack(){
		return this.unitUnderAttack;
	}
	
	/**
	 * A variable that stores the unit that is under attack of this unit.
	 */
	private Unit unitUnderAttack; 
	
	/**
	 * Makes this unit attack another unit.
	 * 
	 * @param	defender
	 * 				The unit that will be attacked this unit.
	 * 
	 * @post	The new current activity of this unit is attacking.
	 * 				| new.getCurrentActivity() == Activity.ATTACK
	 * 
	 * @post	The new unit that is under attack is equal to the given defender.
	 * 				| new.getUnitThatIsUnderAttack() == defender
	 */
	public void attack(Unit defender) throws IllegalStateException{
		try{
			this.setCurrentActivity(Activity.ATTACK);
			this.setUnitUnderAttack(defender);
		} catch	(Exception e){
			throw e;
		}
	}
	
	/**
	 * Checks whether this unit is currently fighting
	 * 
	 * @return	True if and only if this unit is currently attacking or defending.
	 * 					| result == this.isAttaking()
	 */
	@Raw
	private boolean isFighting(){
		return this.isAttaking();
	}
	
	/**
	 * Checks whether this unit managed to block the given attacker.
	 * 
	 * @param	attacker
	 *				The unit that is trying to attack this unit.
	 * 
	 * @return	The chance to block is greater than or equal to a random number in the range 0..1. 
	 * 					| result == ( 0.25*( (this.getStrength() + this.getAgility()) / 
	 * 					| 		 (attacker.getStrength() + attacker.getAgility()) ) 
	 * 					|					<= Math.random() )
	 */
	private boolean managedToBlock(Unit attacker){
		double Pb = 0.25*( (this.getStrength() + this.getAgility()) / (attacker.getStrength() + attacker.getAgility()) );
		double temp = Math.random();
		return (temp <= Pb);
	}
	
	/**
	 * Checks whether this unit managed to dodge the given attacker.
	 * 
	 * @return	The chance to dodge is greater than or equal to a random number in the range 0..1. 
	 * 					| result == ( 0.20*(this.getAgility()/attacker.getAgility());
	 * 					|					<= Math.random() )
	 */
	private boolean managedToDodge(Unit attacker){
		double Pd = 0.20*(this.getAgility()/attacker.getAgility());
		double temp = Math.random();
		return (temp <= Pd);
	}
	
	/**
	 * Makes this unit defend against an attack of another unit.
	 * 
	 * @param	attacker
	 * 					The unit that attacks this Unit.
	 * 
	 * @post	If this unit managed to dodge, the variable defenderDodged of the attacker is set to true and this unit jumped to a random adjacent position.
	 * 				| if (this.managedToDodge())
	 * 				|	then  (new attacker).defenderDodged() == true
	 * 				|		 && (new this).getPosition() != this.getPosition() 
	 * 
	 * @post	If this unit manages to block, the variable defenderBlocked of the attacker is set to true.
	 *  			| if (this.managedToBlock())
	 * 				|	then  (new attacker).defenderBlocked() == true
	 * 
	 * @throws	NullPointerException
	 * 					The given attacker is not an active unit
	 * 					| attacker == null
	 */
	public void defend(Unit attacker) throws NullPointerException, IllegalArgumentException{
		if (attacker == null){
			throw new NullPointerException("The given attacker is not an active unit.");
		}
		
		// If this unit manages to dodge...
		if (this.managedToDodge(attacker)){
			//	...the variable defenderDodged of the attacker is set to true...
			attacker.setDefenderBlocked(true);
			// ... and the unit jumps to an adjacent position.
			int[] newPosition = this.getPosition().getCubeCoordinates();
			while (newPosition == this.getPosition().getCubeCoordinates())
				newPosition = this.randomAdjacentPosition(this.getPosition().getCubeCoordinates());
			this.setCoordinates(Position.getCubeCenter(newPosition));
			// Stop all other movements
			this.resetCoordinates();
			this.addExperience(20);
		}
		// If this unit manages to block...
		else if (this.managedToBlock(attacker)){
			//	...the variable defenderBlocked of the attacker is set to true.
			attacker.setDefenderBlocked(true);
			this.addExperience(20);
		}
		else{ attacker.setDefenderBlocked(false); }
		
	}
	
	/**
	 * Sets whether the unit this unit is attacking managed to block or dodge to the given value.
	 * 
	 * @param value
	 *				The new value to set defenderBlocked to.
	 * @post	The new value of defenderBlocked is equal to the given value.
	 *				| new.getDefenderBlocked() == value
	 */
	@Raw
	void setDefenderBlocked(boolean value){
		this.defenderBlocked = value;
	}
	
	/**
	 * Returns whether the unit this unit is attacking managed to block or dodge.
	 */
	@Basic @Raw
	public boolean getDefenderBlocked(){
		return this.defenderBlocked;
	}
	
	/**
	 * A variable that holds whether the unit this unit is attacking managed to block or dodge. 
	 */
	private boolean defenderBlocked;
	
	/**
	 * Makes this unit fight with another unit.
	 * 
	 * @param	defender
	 * 					The unit to attack.
	 * @param	thisIsDefaultBehaviour
	 *				Whether fighting is called by default behaviour or not.
	 * 
	 * @post	The unit under atack is set to the given defender.
	 * 				| this.getUnitUnderAttack() == defender
	 * 
	 * @post	If the given other unit is in range, the current activity of this unit will be set to ATTACK.
	 * 				| if (! this.isAdjacentTo(other.getCubePosition()))
	 * 				|	then (new this).getUnitBeingFought() == other
	 *
	 * @throws	NullPointerException
	 *					The given other unit is not an active unit.
	 *					| other == null
	 * @throws	IllegalArgumentException
	 *					The given other unit is not in range or belongs to the same faction.
	 *					| (defender.getFaction() == this.getFaction()) || ( ! this.isAdjacentTo(defender.getCubePosition()))
	 * 
	 */
	public void fight(Unit defender) throws IllegalArgumentException, NullPointerException{
		this.fight(defender, false);
	}
	
	/**
	 * Makes this unit fight with another unit.
	 * 
	 * @param	defender
	 * 					The unit to attack.
	 * @param	thisIsDefaultBehaviour
	 *				Whether attacking is called by default behaviour or not.
	 * 
	 * @post	The unit under atack is set to the given defender.
	 * 				| this.getUnitUnderAttack() == defender
	 * 
	 * @post	If the given other unit is in range, the current activity of this unit will be set to ATTACK.
	 * 				| if (! this.isAdjacentTo(other.getCubePosition()))
	 * 				|	then (new this).getUnitBeingFought() == other
	 *
	 * @throws	NullPointerException
	 *					The given other unit is not an active unit.
	 *					| other == null
	 * @throws	IllegalArgumentException
	 *					The given other unit is not in range or belongs to the same faction.
	 *					| (defender.getFaction() == this.getFaction()) || ( ! this.isAdjacentTo(defender.getCubePosition()))
	 * 
	 */
	private void fight(Unit defender, boolean thisIsDefaultBehaviour) throws IllegalArgumentException, NullPointerException{
		if (thisIsDefaultBehaviour)
			System.out.println("fight by defaultBehaviour");
		try {
			if ( ! thisIsDefaultBehaviour)
				this.setDefaultBehaviorEnabled(false);
			
			if(defender == null)
				throw new NullPointerException("The given defender is not an effective unit");
			// If the units are not in eachothers range, then an exception is thrown.
			if ( ! this.isAdjacentTo(defender.getPosition().getCubeCoordinates()))
						throw new IllegalArgumentException("The given defender is not in range");
			//	If the units belong to the same faction they cannot fight eachother. 
			if (defender.getFaction() == this.getFaction())
				throw new IllegalArgumentException("The given defender belongs to the same faction as this unit. \n");

			this.attack(defender);
		} catch (Exception e) {}
	}
	
	/**
	 * Generates a random position that is adjacent to the given cube.
	 * @param	position
	 * 				The given cube position.
	 * @return	A random cube position which is next to the given position.
	 * 				| if this.isAdjacentTo(newRandomPosition)
	 * 				| 	then result == newRandomPosition
	 */
	private int[] randomAdjacentPosition( int[] position) {
		int newRandomXCoordinate = -1;
		while ( ! this.getPosition().isValidXCoordinate(newRandomXCoordinate))
			 newRandomXCoordinate = position[0] + new Random().nextInt(3)-1;
		int newRandomYCoordinate = -1;
		while ( ! this.getPosition().isValidYCoordinate(newRandomYCoordinate))
			 newRandomYCoordinate = position[1] + new Random().nextInt(3)-1;
		int[] newRandomPosition = { newRandomXCoordinate,
									newRandomYCoordinate, 
									this.getPosition().getCubeCoordinates()[2]};
		return newRandomPosition;
	}
	
	/**
	 * Returns the workTarget of this unit.
	 */
	@Basic @Raw
	private int[] getWorkTarget() {
		return this.workTarget;
	}
	
	/**
	 * Sets the target position for this unit to work at.
	 * 
	 * @param workTarget
	 *				The new target position for this unit to work at.
	 *
	 * @post	The new target position for this unit to work at,  is equal to the given workTarget.
	 *				| new.getWorkTarget() == workTarget
	 */
	@Raw
	private void setWorkTarget(int[] workTarget) {
		this.workTarget = workTarget;
	}

	/** 
	 * A variable storing the position this unit should work at.
	 */
	private int[] workTarget;
	
	
	// ===============================================================================
	// Methods concerning the item carried by this unit.
	// ===============================================================================
	
	/**
	 * Returns the item this unit is carrying.
	 */
	@Basic @Raw
	public Item getItem() {
		return this.item;
	}
	
	/**
	 * Returns whether this unit is carrying an item.
	 * @return The item this unit is carrying is not null.
	 *				| this.getItem() != null
	 */
	public boolean hasItem() {
		return this.getItem() != null;
	}
	
	/**
	 * Returns whether this unit is carrying a proper item.
	 * @return The item is null or the unit this item is carried by is this unit.
	 *				| ( (this.getItem() == null) || (this.getItem().getUnit() == this) )
	 */
	public boolean hasProperItem() {
		return (this.getItem() == null) || (this.getItem().getUnit() == this);
	}
	
	/**
	 * Sets the item this unit is carrying.
	 * 
	 * @param item
	 *				The item to be carried.
	 * @post	The item this unit is carrying is equal to the given item.
	 *				| this.getItem() == item
	 * @throws IllegalArgumentException
	 *				The given item doesn't reference this unit correctly.
	 *				| (item != null) && (item.getUnit != this)  
	 * @throws IllegalArgumentException
	 *				The given item is null and the item currently being carried is not correctly dropped yet. 
	 *				| (item == null) && (this.hasItem()) &&(this.getItem().getUnit() == this)
	 */
	void setItem(Item item) {
		if ( (item != null) && (item.getUnit() != this))
			throw new IllegalArgumentException();
		if ( (item == null) && (this.hasItem()) &&(this.getItem().getUnit() == this) )
				throw new IllegalArgumentException();
		this.item = item;
	}
	
	/**
	 * Variable registering the item carried by this unit.
	 */
	private Item item;
	
	/**
	 * Make this unit drop the item it is carrying at the given cube coordinates.
 	 * 
 	 * @param	coordinates
 	 *				The cube coordinates to drop the item at.
 	 * @effect 	This unit no longer references the item as its item.
 	 * 				| this.setItem(null)
 	 * @effect	The item no longer references this unit as its unit,
 	 * 				and now references the world of this unit.
 	 * 				| this.getItem().getUnit() == null
 	 * 				| && this.getItem().getWorld() == this.getWorld()
 	 * @effect 	The coordinates of the item's position this unit is carrying, 
 	 * 				are set to the center of the cube with given coordinates.
 	 *				| this.getItem().getPosition().setCoordinates(coordinates)
 	 * @throws	IllegalArgumentException
 	 *				The given coordinates are invalid for this item.
  	 */
	private void dropItem(int[] coordinates) throws IllegalArgumentException {
		if (! this.getItem().canHaveAsCoordinates(coordinates))
			throw new IllegalArgumentException();
		Item item = this.getItem();
		item.moveToWorld();
		item.getPosition().setCoordinates(Position.getCubeCenter(coordinates));
	}
		
	/**
  	 * Make this unit pick up the given item.
  	 * 
  	 * @param 	item
  	 *				The item to pick up.
 	 * @effect 	This unit references the given item as its item.
 	 * 				| this.setItem(item)
 	 * 			The given item references this unit as its unit,
 	 * 				and no longer references a world.
 	 * 				| item.setUnit(this) &&
 	 * 				| item.setWorld(null)
  	 */
	public void pickUpItem(Item item) throws IllegalStateException, IllegalArgumentException {
		item.moveToUnit(this);
	}
	
	// ===============================================================================
	// Methods concerning default behavior.
	// ===============================================================================
	
	/**
	 * Return whether default behavior is enabled or not.
	 */
	@Basic
	public boolean getDefaultBehaviorEnabled(){
		return this.defaultBehaviorEnabled;
	}
	
	/**
	 * Sets defaultBehaviorEnabled to the given value.
	 * @post	The value of this unit's default behavior is set
	 * 			to the given value
	 * @throws	IllegalStateException
	 * 			This unit is terminated.
	 * 			isTerminated()
	 */
	private void setDefaultBehaviorEnabled(boolean value){
		if (this.isTerminated())
			throw new IllegalStateException("unit is terminated.");
		this.defaultBehaviorEnabled = value;
	}
	
	/**
	 * A variable that records whether this units default behavior is enabled.
	 */
	private boolean defaultBehaviorEnabled;
	
	/**
	 * A symbolic constant used to limit the range of moving for units which have enabled default behavior.
	 * This value is chosen based on observations, and prevents units from moving to far away locations, 
	 * as these take a huge amount of time and may even cause the game to crash. If the moveTo algorithm is
	 * better optimized, this value can be raised or even ignored.
	 */
	private final static int MAX_RANGE_DEFAULTMOVE = 3;
	
	/**
	 * Starts the default behavior of this unit.
	 * @effect	The unit is doing one of the possible activities.
	 * 					| new.getCurrentActivity == (MOVE || WORK || REST || ATTACK)
	 */
	public void startDefaultBehavior() throws IllegalArgumentException, NullPointerException {
		this.setDefaultBehaviorEnabled(true);
		// TODO default behaviour volgens part 3 implementeren.
		Scheduler scheduler = this.getFaction().getScheduler();
		List<Task> availableTasks = scheduler.getUnassignedTasks();
		if (availableTasks.size() != 0) {
			availableTasks.get(0).assignTo(this);
			this.getTask().getStatement().execute();
		}
		else {
//		try {
			int choice =  new Random().nextInt(4);
			if (choice == 0){
				// The unit chose to work. 
				this.workAt(this.getWorld().getRandomValidCubeCoordinatesInRange(this.getPosition().getCoordinates(), 1), true);
			}
			else if (choice == 1){
				// The unit chose to rest.
				this.rest(true);
			}
			else if (choice == 2){
				// The unit chose to go to a random position. Once the unit is moving, it can decide to start sprinting until it runs out of stamina.
				int[] randomPosition = this.getWorld().getRandomUnitCoordinatesInRange(this.getPosition().getCoordinates(), MAX_RANGE_DEFAULTMOVE);
				this.moveTo(randomPosition, true);
			}
			else if (choice == 3){
				//	The unit chose to attack a random unit in its reach.
				// If there are no attackable units in its reach, then a new behaviour is chosen.
				Unit combattant = this.getUnitThatCanBeAtacked();
				if (combattant == null)
					this.startDefaultBehavior();
				else 
					this.fight(combattant, true); 
			}
//		} catch (Exception e){
//			// Do nothing
//			System.out.println("I do nothing now because I failed at default behaviour.");
//		}
		}
	}
	
	/**
	 * Returns a unit that can be attacked by this unit.
	 * 
	 * @return A unit that is on a position adjacent to this position and belongs to a different faction than this unit.
	 */
	private Unit getUnitThatCanBeAtacked() {
		// Returns a unit that can be attacked.
		// If no such unit exists in the world, then null is returned.
		for (Unit unit: this.getWorld().getUnits()){
			if ( (this.isAdjacentTo(unit.getPosition().getCubeCoordinates())) && (this.getFaction() != unit.getFaction()))
				return unit;
		}
		
		return null;
	}

	/**
	 * Stops the default behavior of this unit.
	 * 
	 * @post	The current activity of this unit equals NOTHING.
	 * 				| this.getCurrentActivity() == Activity.NOTHING
	 * @effect	This unit stops executing its task.
	 * 				| getTask().stopExecuting()
	 * 			
	 */
	public void stopDefaultBehavior() {
		try {
			this.setDefaultBehaviorEnabled(false);
			this.setCurrentActivity(Activity.NOTHING);
			if (this.hasTask())
				this.getTask().stopExecuting();
		} catch (IllegalStateException e) {}
	}
	
	
	//============================================================================
	// Methods concerning experience.
	//============================================================================
	
	/**
	 * Symbolic constant denoting the amount of experience a unit needs to level up.
	 * If the unit reaches this value, one of its stats increases.
	 */
	private final int EXP_LEVELUP = 10;
	
	/**
	 * Return the experience of this unit.
	 */
	@Basic @Raw
	public int getExperience() {
		return this.experience;
	}
	
	/**
	 * Check whether the given experience is a valid experience for
	 * this unit.
	 *  
	 * @param  	experience
	 *         	The experience to check.
	 * @return 	The experience must be greater than or equal to 0 
	 * 			and the combined experience must be less than or equal to the max value for an integer.
	 *       	| result == (experience >= 0) && (experience+ this.getExperience() <= Integer.MAX_VALUE)
	*/
	public boolean isValidExperience(int experience) {
		return ( (experience >= 0) && (experience + this.getExperience() <= Integer.MAX_VALUE) );
	}
	
	/**
	 * Set the experience of this unit to the given experience.
	 * 
	 * @param  experience
	 *         The new experience for this unit.
	 * @post   The experience of this new unit is equal to
	 *         the given experience.
	 *       | new.getExperience() == experience
	 * @throws IllegalArgumentException
	 *         The given experience is not a valid experience for any
	 *         unit.
	 *       | ! isValidExperience(getExperience())
	 */
	@Raw
	public void setExperience(int experience) throws IllegalArgumentException {
		if (! isValidExperience(experience))
			throw new IllegalArgumentException();
		this.experience = experience;
	}
	
	/**
	 * Variable registering the experience of this unit.
	 */
	private int experience;
	
	/**
	 * Returns a list of numbers which represents stats which are not yet at
	 * their maximum value. (agility=0, strength=1, toughness=2)
	 * @return	A list of integers, with their corresponding stat not yet maxed.
	 * 			| for (integer in result)
	 * 			|	getStatOfInteger() < Unit.getMaxBaseStat()
	 */
	public List<Integer> statsNotMaxed() {
		List<Integer> result = new ArrayList<>();
		if (this.canHaveAsAgility(this.getAgility() + 1))
			result.add(0);
		if (this.canHaveAsStrength(this.getStrength() + 1))
			result.add(1);
		if (this.canHaveAsToughness(this.getToughness() + 1))
			result.add(2);
		return result;
	}
	
	/**
	 * Raises one stat of this unit by one. The stat is chosen at random
	 * and is not yet maxed.
	 * 
	 * @post	One of the unit's stat is raised by one. This stat was not yet maxed.
	 * 			All other stats remain the same.
	 * 			| (new.getStatX() == this.getStatX() + 1) && (new.getStatY() == this.getStatY() && (new.getStatZ() == this.getStatZ())
	 * @throws 	UnitMaxedOutException
	 * 			All of the unit's stats are already maxed.
	 * 			| statsNotMaxed().size() == 0
	 * @note	Also corrects the unit's weight if necessary through setters for the stats.
	 */
	private void levelUp() throws UnitMaxedOutException {
		List<Integer> possibleStats = this.statsNotMaxed();
		if (possibleStats.size() != 0) {
			int choice = possibleStats.get(new Random().nextInt(possibleStats.size()));
			if (choice == 0)
				this.setAgility(this.getAgility()+1);
			else if (choice == 1)
				this.setStrength(this.getStrength()+1);
			else if (choice == 2)
				this.setToughness(this.getToughness()+1);
		} 
		else
			throw new UnitMaxedOutException("unit is maxed.");
	}
	
	// Main method.
	/**
	 * Adds the given amount of experience to the total experience of this unit.
	 * Also increases one or more random stats of the unit if it levels up because of the added experience.
	 * 
	 * @param 	extraExperience
	 * 			The amount of experience added.
	 * @post	The unit's total experience is raised with the given amount.
	 * 			| new.getExperience() == this.getExperience() + extraExperience
	 * 			If the unit levels up, one of the unit's stat is raised by one. 
	 * 			This stat was not yet maxed. All other stats remain the same.
	 * 			| (new.getStatX() == this.getStatX() + 1) && (new.getStatY() == this.getStatY() && (new.getStatZ() == this.getStatZ())
	 */
	public void addExperience(int extraExperience) {
		try {
			// NOTE: hasEnoughExperience also implicitely checks the conditions of setExperience.
			// If setExperience would fail, hasEnoughExperience will fail too and nothing changes.
			if (this.hasEnoughExperience(extraExperience)) {
				int amountOfLevels = this.amountOfLevels(extraExperience);
				for (int count = 0; count < amountOfLevels; count++) 
					this.levelUp();
			}
			this.setExperience(this.getExperience()+extraExperience);
		} catch (Exception e) {
			// Do nothing.
		}
	}

	/**
	 * Check if this unit has enough experience to level up if it would receive
	 * the given amount of extra experience.
	 * @param 	extraExperience
	 * 			The extra experience added.
	 * @return	True if the new total experience has exceeded the cap needed for leveling up.
	 * 			| result = ((extraExperience + this.getExperience()) / EXP_LEVELUP > this.getExperience() / EXP_LEVELUP)
	 * @throws	IllegalArgumentException
	 * 			The given extra experience is invalid.
	 */
	private boolean hasEnoughExperience(int extraExperience) throws IllegalArgumentException {
		if ( ! this.isValidExperience(extraExperience))
			throw new IllegalArgumentException();
		
		int newTotalExperience = extraExperience + this.getExperience();
		return ( (newTotalExperience / EXP_LEVELUP) > (this.getExperience() / EXP_LEVELUP) );
	}
	
	/**
	 * Returns the amount of times this unit can level up if it receives the given
	 * amount of experience.
	 * @param 	extraExperience
	 * 			The given experience.
	 * @return 	The amount of times this unit can level up.
	 * 			| result == (((extraExperience + this.getExperience()) / EXP_LEVELUP) - (this.getExperience() / EXP_LEVELUP))
	 * @throws 	IllegalArgumentException
	 * 			The given experience is invalid.
	 * 			| !isValidExperience(extraExperience)
	 */
	private int amountOfLevels(int extraExperience) throws IllegalArgumentException{
		if ( ! this.isValidExperience(extraExperience))
			throw new IllegalArgumentException();
		
		int newTotalExperience = extraExperience + this.getExperience();
		return ( (newTotalExperience / EXP_LEVELUP) - (this.getExperience() / EXP_LEVELUP) );
	}
	
	
	// ==============================================================================
	// Methods for registering whether this unit was interrupted during its actions.
	// ==============================================================================
	
	/**
	 * Returns the is-interrupted property of this unit.
	 */
	@Basic @Raw
	public boolean getIsInterrupted() {
		return this.isInterrupted;
	}
	
	/** Sets the is-interrupted value of this unit to the given value.
	 * 
	 * @param value
	 * 			The given value.
	 * @post	The value of is-interrupted is equal to the given value.
	 * 			| new.getIsInterrupted() == value
	 */
	@Raw
	private void setIsInterrupted(boolean value) {
		this.isInterrupted = value;
	}
	
	/**
	 * Value registering the is-interrupted property of this unit.
	 */
	private boolean isInterrupted = false;
	
	
	//============================================================================
	// Methods concerning game time.
	//============================================================================
	
	/**
	 * Registers the time needed to execute a statement.
	 * If less time is used, exactly one statement is executed.
	 */
	public static final double STATEMENT_EXECUTION_TIME = 0.001;
	
	// No formal documentation required.
	/**
	 * Advances the game time of this unit with a given deltaT.
	 * @param	deltaT
	 * 					The time to increase the game time with.
	 * @post	The gameTime of this  unit is equal to its old game time increased with deltaT.
	 *       		| new.getGameTime() == this.getGameTime() + deltaT
	 * @throws	IllegalArgumentException
	 *         			The given deltaT is negative or bigger than 0.2.
	 *       			| (deltaT<0) || (deltaT>0.2)
	 */
	@Override
	public void advanceTime(double deltaT) throws IllegalArgumentException, IllegalStateException, NullPointerException{
		//TODO move voorwaarden naar World.advanceTime()
		if (this.isTerminated()){
			throw new IllegalStateException("This unit is terminated.");
		}
		
		// Advance the gametime.
		this.setGametime(this.getGametime() + deltaT);
		
		// special case: the unit is falling.
		// Only changing settings, the action starts at advanceTimeFall()
		if ( (this.isFalling()) || (this.getIsFalling()) ) {
			// if the is-falling property is set to false, it means falling was just initiated.
			// in this case, the unit starts from the center of the noSolidNeighbours-cube.
			if (! this.getIsFalling()) {
				this.setCoordinates(this.getPosition().getSurfaceCenter());
				this.setInitialCoordinates(this.getPosition().getCoordinates());
			}
			this.setIsFalling(true);
			this.setCurrentActivity(Activity.MOVE);
			this.setIsSprinting(false);
		}
		
		// Normal case
		else {
			
			// If the initial rest time has not been passed and the this unit is not being attacked, 
			// the current activity always results in REST.
			if ( (this.getPreviousActivity() == Activity.REST) && (this.getCurrentActivity() != Activity.REST) && ( ! this.getInitialRestTimePassed()) && ( ! this.isFighting()) )
				this.rest(this.getDefaultBehaviorEnabled());
			
			// REST has no fixed end to reset its progress, so it happens when this unit stops resting 
			// and the initial rest time is surpassed.
			if ( (this.getPreviousActivity() == Activity.REST) && (this.getCurrentActivity() != Activity.REST) && ( this.getInitialRestTimePassed()) ){
				this.setProgress(0.0);
				this.setInitialRestTimePassed(false);
			}
			
			// The unit has to rest every 3 minutes.
			if ( (this.getGametime() - this.getTimeOfLastRest()) >= 180.0 ) 
				this.rest(this.getDefaultBehaviorEnabled());
		}
		
		if (this.hasTask()) {
			this.advanceTimeTask(deltaT);
		}

		// Actual implementation of activities.
		switch(this.getCurrentActivity()) {
			case MOVE:
				advanceTimeMove(deltaT);
				break;
			
			case WORK:
				advanceTimeWorkAt(deltaT, this.getWorkTarget());
				break;

			case ATTACK:
				advanceTimeAttack(deltaT, this.getUnitUnderAttack());
				break;
			
			case REST:
			advanceTimeRest(deltaT);
				break;
				
			case NOTHING:
				break;
		default:
			break;
		}
		
	}

	private void advanceTimeRest(double deltaT) {
		// Run progress
		this.setProgress(this.getProgress() + deltaT);
		
		double extraHealth = (deltaT/0.2)*(this.getToughness()/200.0);
		double extraStamina = (deltaT/0.2)*(this.getToughness()/100.0);
		
		// Recover health first. Don't recover health while fighting.
		if ( (this.getCurrentHealth() < this.getMaxPoints()) && ( ! this.isFighting()) ) {
			if(this.getCurrentHealth() + extraHealth < this.getMaxPoints())
				this.setCurrentHealth(this.getCurrentHealth() + extraHealth);
			else
				this.setCurrentHealth(this.getMaxPoints());
		}
		// Recover stamina if health is full. Don't recover stamina while fighting.
		else if ( this.getCurrentStamina() < this.getMaxPoints() && ( ! this.isFighting()) ) {
			if (this.getCurrentStamina() + extraStamina < this.getMaxPoints())	
				this.setCurrentStamina(this.getCurrentStamina() + extraStamina);
			else
				this.setCurrentStamina(this.getMaxPoints());
		}
		
		// A unit needs to get at least one point of health before changing activity.
		if ( this.getProgress() >= 40.0/this.getToughness())  
			this.setInitialRestTimePassed(true);
		
		// reset the time of the last rest.
		this.setTimeOfLastRest();
		
		// If the initial rest time has passed and the unit is fully recovered, change the current activity to the previous one.
		if ( (this.getCurrentHealth() == this.getMaxPoints()) && (this.getCurrentStamina() == this.getMaxPoints())
				&& this.getInitialRestTimePassed() ) {
			// If default behavior is enabled, then a new activity is chosen.
			if ( this.getDefaultBehaviorEnabled())
				this.startDefaultBehavior();
			else if (this.getPreviousActivity() != null)
				this.setCurrentActivity(this.getPreviousActivity());
			else
				this.setCurrentActivity(Activity.NOTHING);
		}
	}

	private void advanceTimeAttack(double deltaT, Unit defender) {
		// Countdown towards the actual attack.
		this.setProgress(this.getProgress() + deltaT);
		// Let the attacker face the unit it is attacking
		this.setOrientation(Math.atan2((defender.getPosition().getYCoordinate() - this.getPosition().getYCoordinate()), (defender.getPosition().getXCoordinate() - this.getPosition().getXCoordinate())));
		
		// If the countdown ends and the defending unit is still in range, this unit attacks the defender.
		if ( (this.getProgress() >= 1.0) && (this.isAdjacentTo(defender.getPosition().getCubeCoordinates())) ) {
			// Make the defending unit face the attacker.
			defender.setOrientation(Math.atan2((this.getPosition().getYCoordinate() - defender.getPosition().getYCoordinate()), this.getPosition().getXCoordinate() - defender.getPosition().getXCoordinate()));
			// The defender tries to dodge or block.
			defender.defend(this);
			// If the defender fails in both...
			if ( ! this.getDefenderBlocked())	{
				//  it loses an amount of health based on the attackers stats.
				defender.setCurrentHealth(defender.getCurrentHealth() - this.getStrength()/10.0);
				 this.addExperience(20);
			}
			
			// Reset Progress.
			this.setProgress(0.0);
			
			// Do what has to be done when the defender dies.
			if (defender.getCurrentHealth() <= 0)
				defender.terminate();
			
			// Reset the unit that this unit is attacking.
			this.setUnitUnderAttack(null);
			
			// The attackers activity is set back to the default when its attack is finished.
			this.setCurrentActivity(Activity.NOTHING);
			
			// Continue default behavior if it was enabled.
			if (this.getDefaultBehaviorEnabled())
				this.startDefaultBehavior();
		}
	}
	
	public void advanceTimeWorkAt(double deltaT, int[] workTarget) {
		this.setProgress(this.getProgress() + deltaT);
		
		double[] cube = Position.getCubeCenter(workTarget);
		this.setOrientation(cube[0] - this.getPosition().getXCoordinate(),
				cube[1] - this.getPosition().getYCoordinate());
		
		// Dropping boulders and logs has to be instantaneous so it has to happen before the progress check.
		// If this unit is carrying a log or a boulder:
		if (this.hasItem()) {
			this.dropItem(workTarget);
			// Reset the progress.
			this.setProgress(0.0);
			
			this.setCurrentActivity(Activity.NOTHING);
			
			this.addExperience(10);
			
			// If default behavior is enabled, keep it running.
			if (this.getDefaultBehaviorEnabled())
				this.startDefaultBehavior();
		}
		
		// If the work time is over...
		else if (this.getProgress() >= this.getWorkDuration()) {
			//	Make the code more readable.
			Cube workTargetCube = this.getWorld().getCube(workTarget[0], workTarget[1], workTarget[2]);

			// Depending on what type of cube this unit is on and what it is carrying, it performs a different task.
			
			// If the workTarget is a workshop and their are also a log and a boulder available...
			 if ( (workTargetCube.getTerrainType() == Terrain.WORKSHOP) && (this.getWorld().hasLogAt(workTarget)) && (this.getWorld().hasBoulderAt(workTarget))  ) {
				//	...then this unit consumes the log and the boulder...
				this.getWorld().removeEntity(this.getWorld().getBoulderAt(workTarget));
				this.getWorld().removeEntity(this.getWorld().getLogAt(workTarget));
				//	...to raise its toughness and weight.
				this.setWeight(this.getWeight() + 5);
				this.setToughness(this.getToughness() + 5);
				this.addExperience(10);
			}
			
			// If there is a boulder at the workTarget...
			 else if (this.getWorld().hasItemAt(workTarget)) {
				this.pickUpItem(this.getWorld().getItemAt(workTarget));
				this.addExperience(10);
			 }
			
			// If there is wood at the workTarget...
			else if (workTargetCube.getTerrainType() == Terrain.WOOD){
				//	...then make the cube collapse.
				this.getWorld().collapsCube(workTarget);
				this.addExperience(10);
			}
			
			//	If there is rock at the workTarget...
			else if (workTargetCube.getTerrainType() == Terrain.ROCK){
				//	...then make the cube collaps.
				this.getWorld().collapsCube(workTarget);
				this.addExperience(10);
			}
			
			// Reset progress.
			this.setProgress(0.0);
			
			// the current activity is set to nothing.
			this.setCurrentActivity(Activity.NOTHING);
			
			
			// If default behavior is enabled, keep it running.
			if (this.getDefaultBehaviorEnabled()) {
				this.finishTask();
				this.startDefaultBehavior();
			}
			
		}
	}

	private void advanceTimeMove(double deltaT) {
		if (this.isFalling() || (this.isFalling == true))
			advanceTimeFall(deltaT);
		else{
			double[] newPosition = new double[3];
			double staminaDrain = 10 * deltaT;
			
			// Add a random chance to start sprinting if this unit has activity default behavior enabled..
			if ( this.getDefaultBehaviorEnabled() &&
					(this.getCurrentStamina()>=staminaDrain+1)){
				
				// chance: 0.1% per deltaT
				int chance = new Random().nextInt(1000);
				if (chance < 1)
					this.setIsSprinting(true);
			}
			
			// From moveTo() use the filled-in moveToPath to get the next cube to reach, if necessary.
			if (moveToPath.size() != 0) {
				this.setTargetCoordinates(Position.getCubeCenter(moveToPath.get(0)));
			}
			
			// Calculate the new position.
			for (int count = 0; count < 3; count++){
				newPosition[count] = this.getPosition().getCoordinates()[count] + 
						this.getVelocity(this.getTargetCoordinates())[count] * deltaT;
			}
			
			// Set the orientation towards the target position.
			if (! UnitPosition.fuzzyEquals(this.getTargetCoordinates(), newPosition)) {
				this.setOrientation(this.getVelocity(this.getTargetCoordinates())[0], this.getVelocity(this.getTargetCoordinates())[1]);
			}
				
			// If the target position is reached or surpassed, set to target position.
			if (UnitPosition.getDistance(this.getTargetCoordinates(), this.getInitialCoordinates()) 
					<= UnitPosition.getDistance(newPosition, this.getInitialCoordinates())) {
				
				this.setCoordinates(this.getTargetCoordinates());
				
				// Remove the cube from the path since you've just reached it.
				if (moveToPath.size() !=0) {
					moveToPath.remove(0);
				}
				
				// Add experience (1 per cube traversed)
				this.addExperience(1);
				
				// Last position to reach, no need to move anymore.
				if (moveToPath.size() ==0) {
					this.setCurrentActivity(Activity.NOTHING);
					this.resetCoordinates();
						
					// Continue default behavior if it is enabled.
					if (this.getDefaultBehaviorEnabled())
						this.startDefaultBehavior();
				}
			} else {
				// Set unit position to the calculated position.
				this.setCoordinates(newPosition); 				
			}
			
			// Drain stamina if necessary.
			if (this.getIsSprinting()== true){
				if (this.getCurrentStamina()>staminaDrain+1)
					this.setCurrentStamina(this.getCurrentStamina()-staminaDrain);
				else {
					this.setCurrentStamina(minBaseStat);
					this.setIsSprinting(false);
				}
			}
		}
	}
	
	private void advanceTimeFall(double deltaT) {
		double[] newPosition = new double[3];

		double[] targetPosition = UnitPosition.getCubeCenter(this.getWorld().getCubeBelow(Position.getCubeCoordinates(initialCoordinates)).getPosition().getCoordinates());
		this.setTargetCoordinates(targetPosition);

		this.setCoordinates(this.getPosition().getSurfaceCenter());
		
		// No other movement allowed.
		this.moveToPath.clear();
		
		// Calculate the new position.
		newPosition = UnitPosition.calculateNextCoordinates(this.getPosition().getCoordinates(), Entity.FALL_VELOCITY, deltaT);
		
		// No updates needed for orientation (only for x and y).
		
		// The target position is reached or surpassed.
		if (UnitPosition.getDistance(this.getTargetCoordinates(), this.getInitialCoordinates()) 
				<= UnitPosition.getDistance(newPosition, this.getInitialCoordinates())) {

			// Update initial position
			this.setInitialCoordinates(targetPosition);
			this.setCoordinates(targetPosition);
			
			// Lose health
			if (this.getCurrentHealth() > 10)
				this.setCurrentHealth(this.getCurrentHealth()-10);
			else{
				this.setCurrentHealth(0);
				this.terminate();
			}
			
			// The unit has stopped falling
			if (this.hasSolidNeighbours(targetPosition[0], targetPosition[1], targetPosition[2])) {
				
				this.setCoordinates(this.getTargetCoordinates());
				this.setCurrentActivity(Activity.NOTHING);
				this.resetCoordinates();
				this.setIsFalling(false);
			}
		} else {
			this.setCoordinates(newPosition); 				
		}	
	}
	
	private void advanceTimeTask(double deltaT) {
//		Statement statement = this.getTask().getStatement();
//		double time = Unit.STATEMENT_EXECUTION_TIME;
//		while (Util.fuzzyLessThanOrEqualTo(time, deltaT)) {
//			statement.execute();
//		}
	}
}
