package hillbillies.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.exceptions.MaxIterationException;
import hillbillies.exceptions.UnitMaxedOutException;
import hillbillies.positions.Position;
import hillbillies.positions.UnitPosition;
import hillbillies.statements.Statement;
import hillbillies.statements.Status;
import util.RandomSetElement;
	
/** 
 * @version 2.20
 */

/**
 * A class of units involving a world, a faction, a name, a position, a number of primary attributes, health and stamina,
 * 	an orientation.
 * 
 * @invar	The current health of each unit must be a valid current health for any unit. 
 *				| canHaveAsCurrentHealth(getCurrentHealth())
 * @invar	The current stamina of each unit must be a valid current stamina for any unit. 
 *				| canHaveAsCurrentStamina(getCurrentStamina())
 * @invar	The weight of each unit must be a valid weight for any unit.
 *				| canHaveAsWeight(getWeight())
 * @invar	The maximum base stat must be a valid maximal base stat for any unit.
 *				| canHaveAsMaxBaseStat(getMaxBaseStat())
 * @invar	The minimal base stat must be a valid minimal base stat for any unit.
 *				| canHaveAsMinBaseStat(getMinBaseStat())
 * @invar	The name of each unit must be a valid name for any unit.
 *				| isValidName(getName())
 * @invar	The coordinates of each unit must be valid coordiniates for any unit.
 *				|getPosition().canHaveAsCoordinates(getPosition().getCoordinates())
 * @invar	The orientation of each unit must be a valid orientation for any unit.
 *				| isValidOrientation(getAngle())
 * @invar	The initial coordiniates of each unit must be valid initial coordiniates for any unit.
 *				| canHaveAsInitialCoordinates(getInitialCoordinates())
 * @invar	The target coordiniates of each unit must be valid target coordiniates for any unit.
 *				| canHaveAsTargetCoordinates(getTargetCoordinates())
 * @invar	The is-sprinting value of each unit must be a valid is-sprinting value for any unit.
 *				| isValidIsSprinting(getIsSprinting())
 * @invar	The experience of each unit must be a valid experience for any unit.
 *				| isValidExperience(getExperience())
 * @invar	Each unit must have a proper world to which it is attached.
 *				| hasProperWorld()
 * @invar	Each unit must have a proper faction to which it is attached.
 *				| hasProperFaction()
 * @invar	Each unit must have a proper task.
 *				| hasProperTask()
 * @invar	The is-falling property of each unit must be a valid is-falling property for any unit.
 *				| isValidIsFalling(getIsFalling())
 * 
 * @author Sander Mergan, Thomas Vranken
 */
public class Unit extends Entity{
	
	/**
	 * Initialize this new unit with the given world, faction, name, position, weight, strength, agility, toughness.
	 * 
	 * @param	world
	 *				The world of this new unit.
	 * @param	faction
	 *				The faction of this new unit.
	 * @param	name
	 *				The name of this new unit.
	 * @param	coordinates
	 *				The cube coordiniates of this new unit.
	 * @param	weight 
	 *				The weight of this new unit.
	 * @param	strength
	 *				The strength of this new unit.
	 * @param	agility
	 *				The agility of this new unit.
	 * @param	toughness
	 *				The toughness of this new unit.
	 * 
	 * @effect	Sets the initial weight of this new unit to the given initial weight.
	 *				| this.setInitialWeight(weight)
	 * @effect	Sets the strength of this new unit to the given initial strength.
	 *				| this.setInitialStrength(strength)
	 * @effect	Sets the agility of this new unit to the given initial agility.
	 *				| this.setInitialAgility(agility)
	 * @effect	Sets the toughness of this new unit to the given initial toughness.
	 *				| this.setInitialToughness(toughness)
	 * @effect	Sets the world of this unit to the given world.
	 *				| this.setWorld(world)
	 * @effect	Sets the faction of this unit to the given faction.
	 *				| this.setFaction(faction)
	 * @effect	Sets the current health of the new unitl to the maximal health of this unit.
	 *				| this.setCurrentHealth(getMaxPoints())
	 * @effect	Sets the current stamina of the new unit to the maximal stamina of this unit.
	 *				| this.setCurrentStamina(getMaxPoints)
	 * @effect	Sets the name of this unit to the given name.
	 *				| this.setName(name)
	 * @effect	Sets the coordinates of this unit to the given coordinates.
	 *				| this.setCoordinates(Position.getCubeCenter(coordinates))
	 * @effect	Sets the orientation of this unit to Pi/2.0
	 *				| this.setOrientation(Math.PI/2.0)
	 */
	public Unit(World world, Faction faction, String name, int[] coordinates, 
			int weight, int strength, int agility, int toughness) throws IllegalArgumentException {
		
		super(world, coordinates);

		this.setFaction(faction);
		
		// conditions that apply to name in order to be valid are checked in setName method itself.
		this.setName(name);
		
		// Upon creation, places this unit in the center of a cube.
		this.setPosition( new UnitPosition(this.getWorld(), Position.getCubeCenter(coordinates)) );
		
		this.setInitialStrength(strength);
		
		this.setInitialAgility(agility);
		
		this.setInitialToughness(toughness);
		
		this.setInitialWeight(weight);
		
		// Default orientation is PI/2
		this.setOrientation(Math.PI/2.0);
		
		// Precondition is checked in setCurrentHealth method itself.
		this.setCurrentHealth(this.getMaxPoints());
		// Precondition is checked in setCurrentStamina method itself.
		this.setCurrentStamina(this.getMaxPoints());
		
		faction.addUnit(this);
		world.addEntity(this);
	}
	
	/**
	 * Initialize this new unit with the given world, name, coordinates, weight, strength, agility, toughness.
	 * The unit is added to a newly created faction of that world.
	 * 
	 * @param	world
	 *				The world of this new unit.
	 * @param	name
	 *				The name of this new unit.
	 * @param	coordinates
	 *				The coordinates of this new unit.
	 * @param	weight 
	 *				The weight of this new unit.
	 * @param	strength
	 *				The strength of this new unit.
	 * @param	agility
	 *				The agility of this new unit.
	 * @param	toughness
	 *				The toughness of this new unit.
	 * 
	 * @effect	A new faction in the given world is created.
	 *				|	new Faction(world)
	 * @effect	This unit is initialized with the given attributes and the newly created faction as its faction.
	 *				| this(world, new Faction(world), name, position, weight, strength, agility, toughness)
	 */
	public Unit(World world, String name, int[] coordinates, 
			int weight, int strength, int agility, int toughness) throws IllegalArgumentException {
		this(world, new Faction(world), name, coordinates, weight, strength, agility, toughness);
	}
	
	/**
	 * Initialize this new unit with the given world and name. All of its stats are chosen randomly
	 * between valid initial values. The unit is added to a newly created faction of that world.
	 * Its position is randomly chosen and is valid for any unit in the given world.
	 * 
	 * @param	world
	 *				The world of this new unit.
	 * @param	name
	 *				The name of this new unit.
	 * 
	 * @effect	This unit is initialized with random attributes, coordinates and a new faction.
	 *				| this(world, name, randomCoordinates, randomWeight, randomStrength, randomAgility, randomToughness)
	 */
	public Unit(World world, String name) throws IllegalArgumentException {
		this(world, name, world.getRandomAvailableUnitCoordinates(),
				new Random().nextInt(getMaxInitialBaseStat()-getMinInitialBaseStat()+1)+getMinInitialBaseStat(),
				new Random().nextInt(getMaxInitialBaseStat()-getMinInitialBaseStat()+1)+getMinInitialBaseStat(),
				new Random().nextInt(getMaxInitialBaseStat()-getMinInitialBaseStat()+1)+getMinInitialBaseStat(),
				new Random().nextInt(getMaxInitialBaseStat()-getMinInitialBaseStat()+1)+getMinInitialBaseStat());
	}
	
	/**
	 * Returns a textual representation of this unit.
	 */
	@Override
	public String toString() {
		return "Unit\n"+"name: " + getName() +"\n"
				+ "position: " + this.getPosition().toString() +"\n"
				+ "faction: " + this.getFaction() +"\n"
				+ "world: " + this.getWorld() + "\n"
				+"=====";
	}
	
	
	// ===================================================================================================
	// Methods concerning the termination of this unit.
	// ===================================================================================================
	
	/**
	 * Terminate this unit.
	 *
	 * @post	This unit  is terminated.
	 *				| new.isTerminated()
	 * @post	The world this unit was part of no longer references this unit.
	 *				| if (hasWorld())
	 *				|	then ( ! getWorld().getAllUnits().contains(new))
	 * @post	No world is connected with this unit.
	 *				| ( ! new.hasWorld())
	 * @post	The faction this unit was part of no longer references this unit.
	 *				| if (hasFaction())
	 *				|	then (! getFaction().getAllUnits().contains(new))
	 * @effect	The item this unit was carrying is dropped.
	 *				| if (hasItem())
	 *				|	then ( dropItem() )
	 * @effect	Sets this unit's current activity to NOTHING.
	 *				| new.getCurrentActivity() == Activity.NOTHING
	 * @effect	This unit stops sprinting.
	 *				| this.stopSprinting()
	 * @effect	Resets the targetCoordinates and initialCoordinates of this unit.
	 *				| this.stopSprinting()
	 */
	@Override
	 public void terminate() {
		 if (! this.isTerminated()) {
			 
			 this.setCurrentActivity(Activity.NOTHING);
			this.stopSprinting();
			this.stopDefaultBehavior();
			this.resetCoordinates();
			
			if (this.hasItem())
				this.dropItem(this.getCubeCoordinates());
			
			this.isTerminated = true;
			Faction faction = this.getFaction();
			World world = this.getWorld();
			 faction.removeUnit(this);
			 world.removeEntity(this);
		 }
	 }
	
	
	//=============================================================================
	// Methods for name and primary attributes.
	//=============================================================================
	
	/**
	 * Checks whether the given minimal base stat is a valid minimal base stat for any unit.
	 * 
	 * @param	minBaseStat
	 *				The minimal base stat to check.
	 * 
	 * @return	True if and only if the given minimal base stat is equal to or greater than one.
	 *				|result == (minBaseStat >= 1)
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
		return MIN_BASE_STAT;
	}
	
	/**
	 * A variable that stores the minimal base stat.
	 */
	private static final int MIN_BASE_STAT = 1;
	
	/**
	 * Checks whether the given maximal base stat is a valid maximal base stat for any unit.
	 * 
	 * @param	maxBaseStat
	 *				The maximal base stat to check.
	 * 
	 * @return	True if and only if the given maximal base stat is equal to or greater than this units minimal base stat.
	 *				|result == (maxBaseStat >= this.getMinBaseStat())
	 */
	@Raw
	public static boolean canHaveAsMaxBaseStat(int maxBaseStat){
		return maxBaseStat >= getMinBaseStat();
	}
	
	/**
	 * Returns the maximal base stat.
	 */
	@Basic @Raw @Immutable
	public static final int getMaxBaseStat(){
		return MAX_BASE_STAT;
	}
	
	/**
	 * A variable that stores the maximal base stat.
	 */
	private static final int MAX_BASE_STAT = 200;

	/**
	 * Checks whether the given minimal initial base stat is a valid minimal initial base stat for any unit.
	 * 
	 * @param	minInitialBaseStat
	 *				The minimal initial base stat to check.
	 * 
	 * @return	True if and only if the given minimal initial base stat is equal to or greater than 1.
	 *				|result == (minInitialBaseStat >= 1)
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
		return MIN_INITIAL_BASE_STAT;
	}
	
	/**
	 * A variable that stores the minimal initial base stat.
	 */
	private static final int MIN_INITIAL_BASE_STAT = 25;
	
	/**
	 * Checks whether the given maximal initial base stat is a valid maximal initial base stat for any unit.
	 * 
	 * @param	maxInitialBaseStat
	 *				The maximal initial base stat to check.
	 * 
	 * @return	True if and only if the given maximal initial base stat is equal to or greater than this units minimal initial base stat.
	 *				|result == maxInitialBaseStat >= this.getMinInitialBaseStat()
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
		return MAX_INITIAL_BASE_STAT;
	}
	
	/**
	 * A variable that stores the maximum initial base stat.
	 */
	private static final int MAX_INITIAL_BASE_STAT = 100;
	
	/**
	 * Return the name of this unit.
	 */
	@Basic @Raw
	public String getName() {
		return this.name;
	}

	/**
	* Sets the name of this unit to the given name.
	* 
	* @param	name
	*				The new name for this unit.
	*
 	* @post	The name of this new unit is equal to the given name.
 	*				| new.getName() == name
 	* @throws		IllegalArgumentException
 	*				The given name is not a valid name for any unit.
 	*				| ! isValidName(getName())
	 */
	@Raw
	public void setName(String name) throws IllegalArgumentException {
	if (! isValidName(name))
		throw new IllegalArgumentException();
	
	this.name = name;
	}

	/**
	 * Returns whether a name is a valid name.
	 * 
	 * @param	name
	 *				The name to be checked.
	 *            
	 * @return	Returns true if and only if the given name is not null, has at least 2 legal characters and the first
	 *				character is an uppercase letter.
	 *				Legal characters are: uppercase and lowercase letters, single and double quotes and spaces.
	 *				| result == ( (name != null) && (name.matches("[A-Z]([a-zA-Z\\s\'\"])+"))) 
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
	@Override
	public int getWeight() {
		int weight = 0;
		if (this.hasItem())
			weight += this.getItem().getWeight();
		
		weight += this.weight;
		return weight;
	}

	/**
	 * Returns the minimal weight of this unit based on the given strength and agility.
	 * 
	 * @param	strength
	 *				The strength of this unit.
	 * @param	agility
	 *				The agility of this unit.
	 *
	 * @return The minimal value for the weight of this unit.
	*				| result == (strength + agility)/2
	 */
	@Raw
	public static int getMinWeight(int strength, int agility) {
		return (strength + agility) / 2;
	}

	/**
	 * Returns the minimal weight of this unit based on this units strength and agility.
	 *
	 * @return The minimal value for the weight of this unit.
	 *				| result == getMinWeight(this.getStrength(), this.getAgility())
	 */
	public int getMinWeight() {
		return getMinWeight(this.getStrength(), this.getAgility());
	}
	
	/**
	 * Return whether the given weight is valid.
	 * 
	 * @param	weight
	 *				The weight to be checked.
	 *
	 * @return	True if and only if the given weight is in the range this.getMinBaseStat()..this.getMaxBaseStat() and 
	 *				weight exceeds (or is equal to) the minimal weight for this unit. 
	 *				|result == (weight >= this.getMinBaseStat()) && (weight <= this.getMaxBaseStat() && 
	 *				|	(weight >= this.getMinWeight())
	 */
	@Override
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
	 *				The new weight of this unit.
	 *
	 * @post	If the given weight is a valid weight, the weight of this unit is equal to the given weight.
	 * 				| if (this.canHaveAsWeight()) 
	 *				| 	then new.getWeight() == weight
	 * @post	If the given weight is lower than the minimal weight for this unit, 
	 *				then the weight of this unit is equal to the minimal weight for this unit .
	 *				| if (weight < (this.getMinWeight()) 
	 *				| 	then new.getWeight() == (this.getMinWeight())
	 * @post	If the given weight exceeds the maximum base stat, the weight of this unit is equal to that maximum base stat. 
	 *				| if (weight > this.getMaxBaseStat()) 
	 *				| 	then new.getWeight == this.getMaxBaseStat()
	 */
	@Override
	public void setWeight(int weight) {
		if (weight > getMaxBaseStat())
			this.weight = getMaxBaseStat();
		else if (weight >= this.getMinWeight())
			this.weight = weight;
		else if (weight < this.getMinWeight())
			this.weight = getMinWeight(); 
	}

	/**
	 * Sets the initial weight of this unit to the given weight.
	 * 
	 * @param	weight
	 *				The weight for this new unit.
	 * 
	 * @post	If the given weight is in the range getMinInitialBaseStat()..getMaxInitialBaseStat(), the weight of this unit is equal to the given weight. 
	 *				| if ( (weight >= getMinInitialBaseStat()) && (weight <=getMaxInitialBaseStat()) ) 
	 *				| 	then new.getWeight() == weight
	 * @post	If the given weight is lower than getMinInitialBaseStat(), the weight of this unit is equal to getMinInitialBaseStat(). 
	 *				| if (weight < getMinInitialBaseStat()) 
	 *				|	then new.getWeight() == getMinInitialBaseStat()
	 * @post	If the given weight exceeds getMaxInitialBaseStat(), the weight of this unit is equal to getMaxInitialBaseStat(). 
	 *				| if (weight > getMaxInitialBaseStat()) 
	 *				| 	then new.getWeight() == getMaxInitialBaseStat()
	 */
	public void setInitialWeight(int weight) {
		if (weight > getMaxInitialBaseStat())
			this.setWeight(getMaxInitialBaseStat());
		else if (weight < getMinInitialBaseStat())
			this.setWeight(getMinInitialBaseStat());
		else
			this.setWeight(weight);
	}
	
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
	 *				The strength of this unit.
	 * 
	 * @return	True if and only if the given strength is in the range this.getMinBaseStat()..this.getMaxBaseStat().
	 *				|result == (strength >= this.getMinBaseStat()) && (strength <= this.getMaxBaseStat())
	*/
	public boolean canHaveAsStrength(int strength){
		return ((strength >= getMinBaseStat()) && (strength <= getMaxBaseStat()));
	}
	
	/**
	 * Sets the strength of this unit to the given strength.
	 * 
	 * @param	strength
	 *				The new strength of this unit.
	 *          
	 * @post	If the given strength is in the range this.getMinBaseStat()..this.getMaxBaseStat(), the strength of this unit is equal to the given strength. 
	 *				| if (canHaveAsStrength(strength)) 
	 *				| 	then new.getStrength() == strength
	 * @post If the given strength is lower than this.getMinBaseStat(), the strength of this unit is equal to this.getMinBaseStat(). 
	 *				| if (strength < this.getMinBaseStat()) 
	 *				|	then new.getStrength() == this.getMinBaseStat()
	 * @post If the given strength exceeds this.getMaxBaseStat(), the strength of this unit is equal to this.getMaxBaseStat(). 
	 *				| if (strength > this.getMaxBaseStat()) 
	 *				| 	then new.getStrength() == this.getMaxBaseStat()
	 * @post If changing the strength of this unit makes its current weigth invalid (by raising the minimal weight), 
	 *				the weight of this unit changes to the new minimal weight. 
	 *				| if (this.getWeight < this.getMinWeight()) 
	 *				| 	then new.getWeight() == this.getMinWeight()
	 */
	public void setStrength(int strength) {
		if (this.canHaveAsStrength(strength)) 
			this.strength = strength;
		else if (strength < getMinBaseStat()) 
			this.strength = getMinBaseStat();
		else if (strength > getMaxBaseStat()) 
			this.strength = getMaxBaseStat();
		
		if (this.getWeight() < this.getMinWeight()) 
			this.setWeight(this.getMinWeight());
	}

	/**
	 * Sets the initial strength of this unit to the given strength.
	 * 
	 * @param	strength
	 *				The strength for this new unit.
	 * 
	 * @post	If the given strength is in the range getMinInitialBaseStat()..getMaxInitialBaseStat(), the strength of this unit is equal to the given strength. 
	 *				| if ( (strength >= getMinInitialBaseStat()) && (strength <=getMaxInitialBaseStat()) ) 
	 *				| 	then new.getStrength() == strength
	 * @post	If the given strength is lower than getMinInitialBaseStat(), the strength of this unit is equal to getMinInitialBaseStat(). 
	 *				| if (strength < getMinInitialBaseStat()) 
	 *				|	then new.getStrength() == getMinInitialBaseStat()
	 * @post	If the given strength exceeds getMaxInitialBaseStat(), the strength of this unit is equal to getMaxInitialBaseStat(). 
	 *				| if (strength > getMaxInitialBaseStat()) 
	 *				| 	then new.getStrength() == getMaxInitialBaseStat()
	 */
	public void setInitialStrength(int strength) {
		if (strength > getMaxInitialBaseStat())
			this.setStrength(getMaxInitialBaseStat());
		else if (strength < getMinInitialBaseStat())
			this.setStrength(getMinInitialBaseStat());
		else
			this.setStrength(strength);
		
		if (this.getWeight() < this.getMinWeight())
			setInitialWeight(this.getMinWeight());
	}
	
	/**
	 * A variable that holds the strength of this unit.
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
	 *				The agility of this unit.
	 * 
	 * @return	True if and only if agility is in the range this.getMinBaseStat()..this.getMaxBaseStat().
	 *				|result == (agility >= this.getMinBaseStat()) && (agility <= this.getMaxBaseStat())
	*/
	public boolean canHaveAsAgility(int agility){
		return ((agility >=  getMinBaseStat()) && (agility <=  getMaxBaseStat()));
	}
	
	/**
	 * Sets the agility of this unit to the given agility.
	 * 
	 * @param	agility
	 *				The new agility of this unit.
	 *          
	 * @post	If the given agility is in the range this.getMinBaseStat()..this.getMaxBaseStat(), 
	 *				the agility of this unit is equal to the given agility. 
	 *				|if (this.canHaveAsAgility())
	 *				|	then new.getAgility() == agility
	 * @post	If the given agility is lower than this.getMinBaseStat(), the agility of this unit is equal to this.getMinBaseStat(). 
	 *				|if (agility < this.getMinBaseStat()) 
	 *				|	then new.getAgility() == this.getMinBaseStat()
	 * @post	If the given agility exceeds this.getMaxBaseStat(), the agility of this unit is equal to this.getMaxBaseStat(). 
	 *				|if (agility > this.getMaxBaseStat()) 
	 *				|	then new.getAgility() == this.getMaxBaseStat()
	 * @post	if changing the agility of this unit makes its weigth (by raising the minimal weight), 
	 *				the weight of this unit changes to the new minimal weight. 
	 *				|if (this.getWeight() < (this.getStrength() + agility)/2)) 
	 *				|	then new.getWeight() == this.getWeight()
	 */
	public void setAgility(int agility) {
		if (this.canHaveAsAgility(agility))
			this.agility = agility;
		else if (agility <  getMinBaseStat()) 
			this.agility =  getMinBaseStat();
		else if (agility >  getMaxBaseStat()) 
			this.agility =  getMaxBaseStat();
		
		if (this.getWeight() < getMinWeight()) 
			setWeight(getMinWeight());
	}
	
	/**
	 * Sets the initial agility of this unit to the given agility.
	 * 
	 * @param	agility
	 *				The agility for this new unit.
	 * 
	 * @post	If the given agility is in the range getMinInitialBaseStat()..getMaxInitialBaseStat(), the agility of this unit is equal to the given agility. 
	 *				| if ( (agility >= getMinInitialBaseStat()) && (agility <=getMaxInitialBaseStat()) ) 
	 *				| 	then new.getAgility() == agility
	 * @post	If the given agility is lower than getMinInitialBaseStat(), the agility of this unit is equal to getMinInitialBaseStat(). 
	 *				| if (agility < getMinInitialBaseStat()) 
	 *				|	then new.getAgility() == getMinInitialBaseStat()
	 * @post	If the given agility exceeds getMaxInitialBaseStat(), the agility of this unit is equal to getMaxInitialBaseStat(). 
	 *				| if (agility > getMaxInitialBaseStat()) 
	 *				| 	then new.getAgility() == getMaxInitialBaseStat()
	 */
	public void setInitialAgility(int agility) {
		if (agility > getMaxInitialBaseStat())
			this.setAgility(getMaxInitialBaseStat());
		else if (agility < getMinInitialBaseStat())
			this.setAgility(getMinInitialBaseStat());
		else
			this.setAgility(agility);
	
		if (this.getWeight() < this.getMinWeight())
			setInitialWeight(this.getMinWeight());
	}

	/**
	 * A variable that stores the agility of this unit.
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
	 *				The toughness of this unit.
	 * 
	 * @return	True if and only if toughness is in the range this.getMinBaseStat()..this.getMaxBaseStat().
	 *				|result == (toughness >= this.getMinBaseStat()) && ((toughness <= this.getMaxBaseStat())
	*/
	public boolean canHaveAsToughness(int toughness){
		return ((toughness >=  getMinBaseStat()) && (toughness <=  getMaxBaseStat()));
	}
	
	/**
	 * Sets the toughness of this unit to the given toughness.
	 * 
	 * @param	toughness
	 *				The new toughness of this unit.
	 *          
	 * @post	If the given toughness is in the range this.getMinBaseStat()..this.getMaxBaseStat(), 
	 *				the toughness of this unit is equal to the given toughness. 
	 *				|if (this.canHaveAsToughness(toughness)) 
	 *				|	then new.getToughness() == toughness
	 * @post	If the given toughness is lower than this.getMinBaseStat(), 
	 *				the toughness of this unit is equal to this.getMinBaseStat(). 
	 *				|if (toughness < this.getMinBaseStat()) 
	 *				|	then new.getToughness() == this.getMinBaseStat()
	 * @post	If the given toughness exceeds this.getMaxBaseStat(), 
	 *				the toughness of this unit is equal to this.getMaxBaseStat(). 
	 *				|if (toughness > this.getMaxBaseStat()) 
	 *				|	then new.getToughness() == this.getMaxBaseStat()
	 */
	public void setToughness(int toughness) {
		if (this.canHaveAsToughness(toughness))
			this.toughness = toughness;
		else if (toughness < getMinBaseStat())
			this.toughness = getMinBaseStat();
		else if (toughness > getMaxBaseStat())
			this.toughness = getMaxBaseStat();
	}
	
	/**
	 * Sets the initial toughness of this unit to the given toughness.
	 * 
	 * @param	toughness
	 *				The toughness for this new unit.
	 * 
	 * @post	If the given toughness is in the range getMinInitialBaseStat()..getMaxInitialBaseStat(), the toughness of this unit is equal to the given toughness. 
	 *				| if ( (toughness >= getMinInitialBaseStat()) && (toughness <=getMaxInitialBaseStat()) ) 
	 *				| 	then new.getToughness() == toughness
	 * @post	If the given toughness is lower than getMinInitialBaseStat(), the toughness of this unit is equal to getMinInitialBaseStat(). 
	 *				| if (toughness < getMinInitialBaseStat()) 
	 *				|	then new.getToughness() == getMinInitialBaseStat()
	 * @post	If the given toughness exceeds getMaxInitialBaseStat(), the toughness of this unit is equal to getMaxInitialBaseStat(). 
	 *				| if (toughness > getMaxInitialBaseStat()) 
	 *				| 	then new.getToughness() == getMaxInitialBaseStat()
	 */
	public void setInitialToughness(int toughness) {
		if (toughness > getMaxInitialBaseStat()) 
			this.setToughness(getMaxInitialBaseStat());
		else if (toughness < getMinInitialBaseStat()) 
			this.setToughness(getMinInitialBaseStat());
		else 
			this.setToughness(toughness);
		
	}
	
	/**
	 * A variable that stores the toughness of this unit.
	 */
	private int toughness;

	// ==============================================================================
	// Methods concerning the faction. (bidirectional association)
	// Terminated units must still satisfy their invariants.
	// Their faction is set to null.
	// ==============================================================================

	/**
	 * Returns the faction this unit is part of.
	 */
	@Basic @Raw
	public Faction getFaction() {
		return this.faction;
	}
	
	/**
	 * Checks whether this unit can have the given faction as its faction.
	 * 
	 * @param	faction
	 *				The faction to check.
	 *
	 * @return	If this unit is terminated, true if the given faction is not effective.
	 *				| if (isTerminated())
	 *				|	then result == (faction == null)
	 *				Else, true if the given faction is effective
	 *				and not yet terminated.
	 *				| else
	 *				|	then result == (faction != null) && ( ! faction.isTerminated())
	 */
	@Raw
	public boolean canHaveAsFaction(@Raw Faction faction) {
		if (this.isTerminated){
			return faction == null;
		}
		else
			return (faction != null) && ( ! faction.isTerminated());
	}
	
	/**
	 * Checks whether this unit has a proper faction to which it is attached.
	 * 
	 * @return	True if and only if this unit can have the faction to which it
	 *				is attached as its faction, and if that faction is either not
	 *				effective or has this unit as one of its units.
	 *				| (this.canHaveAsFaction(this.getFaction()) 
	 *				|  && ( (this.getFaction() == null) || (this.getFaction().hasAsUnit(this))) )
	 */
	@SuppressWarnings("unused")
	private boolean hasProperFaction() {
		return ( this.canHaveAsFaction(this.getFaction()) 
				&& ( ( this.getFaction() == null) || (this.getFaction().hasAsUnit(this)) ) );
	}
	
	/**
	 * Sets the faction to which this unit is attached to to the given faction.
	 * 
	 * @param	faction
	 *				The faction to attach this unit to.
	 * 
	 * @post	This unit references the given faction as the faction to which it is attached.
	 *				| new.getFaction() == faction
	 *
	 * @throws	IllegalArgumentException
	 *				This unit cannot have the given faction as its faction,
	 *				or the faction is at its maximum capacity.
	 *				| ( ! canHaveAsFaction(faction)) || (faction.getNbUnits() >= World.MAX_UNITS_FACTION)
	 */
	public void setFaction(@Raw Faction faction) throws IllegalArgumentException {
		if ( ! this.canHaveAsFaction(faction))
			throw new IllegalArgumentException();
		if ( ( ! this.isTerminated() && (faction.getNbUnits() >= Faction.MAX_UNITS_FACTION)) )
			throw new IllegalArgumentException();
		this.faction = faction;
	}
	
	/**
	 * A variable that stores the faction to which this unit is attached.
	 */
	private Faction faction;
	
	// ======================================================================================
	// Methods for health and stamina.
	// ======================================================================================
	
	/**
	 * Returns the current health of this unit.
	 */
	@Basic	@Raw
	public double getCurrentHealth() {
		return this.currentHealth;
	}
	
	/**
	 * Checks whether the given current health is a valid current health for any unit.
	 * 
	 * @param	currentHealth
	 *				The current health to check.
	 * 
	 * @return	True only if the current stamina is greater than or equal to 0,
	 *				and lesser than or equal to its maximal value. 
	 *				| result == ((currentStamina >= 0) && (currentStamina <= getMaxPoints()))
	 */
	public boolean canHaveAsCurrentHealth(double currentHealth) {
		return (currentHealth >= 0) && (currentHealth <= this.getMaxPoints());
	}
	
	/**
	 * Sets the current health of this unit to the given current health.
	 * 
	 * @param	currentHealth
	 *				The new current health for this unit.
	 *          
	 * @pre	The given current health must be a valid current health for any unit. 
	 *				| canHaveAsCurrentHealth(currentHealth)
	 *
	 * @post	The current health of this unit is equal to the given currentHealth. 
	 *				| new.getCurrentHealth() == currentHealth
	 */
	private void setCurrentHealth(double currentHealth) {
		assert canHaveAsCurrentHealth(currentHealth);
		this.currentHealth = currentHealth;
	}
	
	/**
	 * A variable that stores the current health of this unit.
	 */
	private double currentHealth;
	
	/**
	 * Returns the current stamina of this unit.
	 */
	@Basic	@Raw
	public double getCurrentStamina() {
		return this.currentStamina;
	}
	
	/**
	 * Checks whether the given current stamina is a valid current stamina for any unit.
	 * 
	 * @param	currenStamina
	 *				The current stamina to check.
	 * 
	 * @return	True only if the current stamina is greater than or equal to 0,
	 *				and lesser than or equal to its maximal value. 
	 *				| result == ((currentStamina >= 0) && (currentStamina <= getMaxPoints()))
	 */
	public boolean canHaveAsCurrentStamina(double currentStamina) {
		return ((currentStamina >= 0) && (currentStamina <= this.getMaxPoints()));
	}
	
	/**
	 * Sets the current stamina of this unit to the given current stamina.
	 * 
	 * @param	newCurrentStamina
	 *				The new current stamina for this unit.
	 *            
	 * @pre	The given current stamina must be a valid current stamina for any unit.
	 *				| canHaveAsCurrentStamina(currentStamina)
	 *
	 * @post	The current stamina of this unit is equal to the given current stamina. 
	 *				| new.getCurrentStamina() == currentStamina
	 */
	private void setCurrentStamina(double newCurrentStamina) {
		assert canHaveAsCurrentStamina(newCurrentStamina);
		this.currentStamina = newCurrentStamina;
	}
	
	/**
	 * A variable that stores the current stamina of this unit.
	 */
	private double currentStamina;
	
	/**
	 * Returns the maximum points of health and stamina of this unit, based on this units weight and toughness.
	 * 
	 * @return The maximum points for health and stamina are both determined as
	 *				 200 * weight/100 * toughness/100, rounded up to the next integer. 
	 *				| result == getMaxPoints(this.weight, getToughness())
	 */
	public int getMaxPoints() {
		return getMaxPoints(this.weight,this.getToughness());
	}

	/**
	 * Returns the maximum points of health and stamina of this unit, based on the given weight and toughness.
	 * 
	 * @param	weight
	 *				The given weight is used for calculating the max value of this unit health and stamina.
	 * @param	toughness
	 *				The given thoughness is used for calculating the max value.
	 *          
	 * @return	The maximum points for health and stamina are both determined as
	 *				200 * weight/100 * toughness/100, rounded up to the next integer. 
	 *				| result == (int) Math.ceil(getWeight() * getToughness() / 50.0)
	 */
	public static int getMaxPoints(int weight, int toughness) {
		return (int) Math.ceil(weight * toughness / 50.0);
	}
	
	
	// ======================================================================================
	// Methods for positions and coordinates.
	// ======================================================================================

	/**
	 * Returns the exact position of this unit.
	 */
	@Override
	@Basic @Raw
	public UnitPosition getPosition(){
		return (UnitPosition) this.position;
	}
 
	/**
	 * Sets the coordinates of this unit to the given coordinates.
	 * If the new coordinates are invalid, stop the current activity.
	 * If the unit is executing a task, it is considered failed and will be
	 * unassigned from the unit.
	 *  
	 * @param	coordinates
	 *				The new coordinates of this unit.
	 * @effect	The coordinates of this unit are set to the given coordinates.
	 * 			| this.position.setCoordinates(coordinates)
	 * @post	If the coordinates are invalid, the activity is set to nothing,
	 * 			the intial and target coordinates are reset,
	 * 			and the task is returned, if possible.
	 * 			| if ( position.canHaveAsCoordinates(coordinates))
	 * 			| 	then (this.resetCoordinates()
	 *			| 			this.setCurrentActivity(Activity.NOTHING)
	 *			| 			if (this.hasTask())
	 *			| 				then this.returnFailedTask()
	 */
	private void setCoordinates(double[] coordinates) {
		try {
			this.position.setCoordinates(coordinates);
		} catch (IllegalArgumentException e) {
			this.resetCoordinates();
			this.setCurrentActivity(Activity.NOTHING);
			if (this.hasTask())
				this.returnFailedTask();
		}
	}
	
	/**
	 * Returns the coordinates of this unit.
	 * 
	 * @return this.position.getCoordinates()
	 */
	@Raw
	public double[] getCoordinates(){
		return this.position.getCoordinates();
	}
	
	// ==============================================================================
	// Methods concerning the world. (bidirectional association)
	// Terminated units must still satisfy their invariants.
	// Their world is set to null.
	// ==============================================================================
	
	/**
	 * Checks whether this unit can have the given world as its world.
	 * 
	 * @param	world
	 *				The world to check.
	 *
	 * @return	If this unit is terminated, true if the given world
	 *				is not effective.
	 *				| if (isTerminated())
	 *				|	then result == (world == null)
	 *				If the world has this unit as one of its units, return true.
	 *				| else if (world.hasAsEntity(this))
	 *				|	then result == true
	 *				Else, true if the given world is effective,
	 *				not yet terminated and has room for units.
	 *				| else
	 *				|	then result == (world != null) && (!world.isTerminated())
	 */
	@Raw
	@Override
	public boolean canHaveAsWorld(@Raw World world) {
		if (this.isTerminated)
			return world == null;
		else if (world == null)
			return false;
		else if (world.hasAsEntity(this))
			return true;
		else
			return (world != null) && (! world.isTerminated()) && (world.getNbUnits() < World.MAX_UNITS_WORLD);
	}
	
	/**
	 * Checks whether this unit has a proper world to which it is attached.
	 * 
	 * @return	True if and only if this unit can have the world to which it
	 *				is attached as its world, and if that world is either not
	 *				effective or has this unit as one of its units.
	 *				| (this.canHaveAsWorld(this.getWorld()) 
	 *				|  && ( (this.getWorld() == null) || (this.getWorld().hasAsUnit(this))) )
	 */
	public boolean hasProperWorld() {
		return ( this.canHaveAsWorld(this.getWorld()) 
				&& ( ( this.getWorld() == null) || (this.getWorld().hasAsEntity(this)) ) );
	}
	
	/**
	 * Sets the world to which this unit is attached to to the given world.
	 * 
	 * @param	world
	 *				The world to attach this unit to.
	 * 
	 * @post	This unit references the given world as the world to which it is attached.
	 *				| new.getWorld() == world
	 *
	 * @throws	IllegalArgumentException
	 *				This unit cannot have the given world as its world,
	 *				Or the world is at its maximum capacity.
	 *				| (! canHaveAsWorld(world)) || (world.getNbUnits() >= World.MAX_UNITS_WORLD)
	 */
	public void setWorld(@Raw World world) throws IllegalArgumentException {
		if ( ! this.canHaveAsWorld(world))
			throw new IllegalArgumentException();
		if ( ( ! this.isTerminated()) && (world.getNbUnits() >= World.MAX_UNITS_WORLD))
			throw new IllegalArgumentException();
		this.world = world;
	}
	
	
	// ===================================================================================
	// Methods for initial coordinates and target coordinate variables.
	// ===================================================================================
	
	/**
 	 * Returns the target coordinates of this unit.
 	 */
 	@Basic @Raw
 	public double[] getTargetCoordinates() {
 		return this.targetCoordinates;
 	}
 	
 	/**
 	 * Checks whether the given target coordinates are valid target coordinates for this unit.
 	 *  
 	 * @param	targetCoordinates
 	 *				The target coordinates to check.
 	 *         
 	 * @return	True if the target coordinates are null, or are valid coordinates for this unit in its world.
 	 *       			| result == (targetCoordinates == null || canHaveAsCoordinates(targetCoordinates))
 	*/
 	public boolean canHaveAsTargetCoordinates(double[] targetCoordinates) {
 		return( (targetCoordinates == null) || this.getPosition().canHaveAsCoordinates(targetCoordinates) );
 	}
 	
 	/**
 	 * Sets the target coordinates of this unit to the given target coordinates.
 	 * 
 	 * @param	targetCoordinates
 	 *				The new target coordinates for this unit.
 	 *         
 	 * @post	The target coordinates of this new unit are equal to the given target coordinates.
 	 *       		| new.getTargetCoordinates() == targetCoordinates
 	 *       
 	 * @throws 	IllegalArgumentException
 	 *				The given target coordinates are not valid target coordinates for any unit.
 	 *				| ! canHaveAsTargetCoordinates(getTargetCoordinates())
 	 */
 	@Raw
 	private void setTargetCoordinates(double[] targetCoordinates) throws IllegalArgumentException {
 		if (! canHaveAsTargetCoordinates(targetCoordinates))
 			throw new IllegalArgumentException("The given target coordinates are invalid.");
 		this.targetCoordinates = targetCoordinates;
 	}
 
  	/**
  	 * A variable that stores the target coordinates of this unit.
   	 */		   
   	private double[] targetCoordinates;

 	/**
 	 * Returns the initial coordinates of this unit.
 	 */
 	@Basic @Raw
 	public double[] getInitialCoordinates() {
 		return this.initialCoordinates;
 	}
 	
 	/**
 	 * Checks whether the given initial coordinates are valid initial coordinates for this unit.
 	 *  
 	 * @param	initialCoordinates
 	 *				The initial coordinates to check.
 	 *         
 	 * @return	True if the initial coordinates are null, or are valid coordinates for this unit in its world.
 	 *				| result == (initialCoordinates == null || canHaveAsCoordinates(initialCoordinates))
 	*/
 	public  boolean canHaveAsInitialCoordinates(double[] initialCoordinates) {
 		return ( (initialCoordinates == null) || (this.getPosition().canHaveAsCoordinates(initialCoordinates)) );
 	}
 	
 	/**
 	 * Sets the initial coordinates of this unit to the given initial coordinates.
 	 * 
 	 * @param	initialCoordinates
 	 *				The new initial coordinates for this unit.
 	 *
 	 * @post	The initial coordinates of this new unit are equal to the given initial coordinates.
 	 *				| new.getInitialCoordinates() == initialCoordinates
 	 *
 	 * @throws 	IllegalArgumentException
 	 *				The given initial coordinates are not valid initial coordinates for any unit.
 	 *				| ! canHaveAsInitialCoordinates(getInitialCoordinates())
 	 */
 	@Raw
 	private void setInitialCoordinates(double[] initialCoordinates) throws IllegalArgumentException {
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
   	 *				| new.getInitialCoordinates() == null && new.getTargetCoordinates() == null
   	 */
   	private void resetCoordinates() {
   		this.setTargetCoordinates(null);
   		this.setInitialCoordinates(null);
   	}
   	
   	
	//============================================================================================
	// Methods concerning distance and speed.
	//============================================================================================
	
 	/**
	 * Returns the base speed of this unit.
	 * @return	The base speed is given as 1.5*(agility+strength)/(2.0*weight).
	 *				| result == 1.5 * (getStrength() + getAgility()) / (2.0 * getWeight());
	 */
	private double getBaseSpeed() {
		return 1.5 * (this.getStrength() + this.getAgility()) / (2.0 * this.getWeight());
	}
	
	/**
	 * Returns the walking speed of the unit if it walks between the given coordinates.
	 * 
	 * @param	start
	 *				The starting coordinates.
	 * @param	targetCoordinates
	 *				The coordinates to move to.
	 * 
	 * @return	The walking speed, based on the starting coordinates and the target coordinates.
	 *				If the difference between start z-coordinate and the target z-coordinate is negative,
	 *				the walking speed equals to 0.5 * baseSpeed.
	 *				If the difference between start z-coordinate and the target z-coordinate is positive,
	 *				the walking speed equals to 1.2 * baseSpeed.
	 *				Else the walking speed is equal to the base speed.
	 *				| if (Math.signum(start[2]-targetCoordinates[2]) < 0)
	 *				|	then result == 0.5 * getBaseSpeed()
	 *				| else if (Math.signum(start[2]-targetCoordinates[2]) > 0)
	 *				|	then result == 1.2 * getBaseSpeed()
	 *				| else
	 *				|	result ==  getBaseSpeed()
	 */		
	private double getWalkSpeed(double[] start, double[] targetCoordinates) {
		if ((start == null) || (targetCoordinates == null))
			return 0.0;
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
	 * 
	 * @param	targetCoordinates
	 *				The coordinates to move to.
	 *
	 * @return	The walking speed from this unit's current coordinates to the target coordinates.
	 *				| result == (getWalkSpeed(this.getCoordinates(), targetCoordinates))
	 */		
	public double getWalkSpeed(double[] targetCoordinates){
		return (this.getWalkSpeed(this.getCoordinates(), targetCoordinates));
	}
	
	/**
	 * Returns the sprinting speed of the unit.
	 * 
	 * @param	targetCoordinates
	 *				The coordinates to move to.
	 * 
	 * @return	The sprinting speed of the unit.
	 *				| result == 2*getWalkSpeed(targetCoordinates)
	 */
	private double getSprintSpeed(double[] targetCoordinates) {
		return 2*this.getWalkSpeed(targetCoordinates);
	}
 	
	/**
	 * Returns the velocity vector of this unit based on its current coordinates and the given coordinates.
	 * 
	 * @param	targetCoordinates
	 *				The given destination coordinates.
	 * 
	 * @return	The velocity vector as calculated in Position.getVelocity()
	 *				| result == Position.getVelocity(this.getCoordinates(), targetCoordinates, speed)
	 */
	public double[] getVelocity(double[] targetCoordinates) {
		double speed = 0.0;
		if ((this.getIsSprinting()) && this.getCurrentStamina() > 0)
			speed = this.getSprintSpeed(targetCoordinates);
		else
			speed = this.getWalkSpeed(targetCoordinates);
		return (UnitPosition.getVelocity(this.getCoordinates(), targetCoordinates, speed));
	}
	
	/**
	 * Checks whether the target cube with given coordinates is next to the cube the unit is standing in.
	 * 
	 * @param	targetCube
	 *				The cube to check.
	 *
	 * @return	True if and only if the coordinates are the same or next to each other.
	 *				| result == Position.isAdjacentTo(this.getCubePosition().getCubeCoordinates, targetCube)
	 *
	 * @throws	IllegalArgumentException
	 *				The target cube is invalid for this unit's world.
	 */
	@Raw
	public boolean isAdjacentTo(int[]targetCube) throws IllegalArgumentException {
		if (!this.getWorld().canHaveAsCoordinates(targetCube))
			throw new IllegalArgumentException(Arrays.toString(targetCube));
	
		return (Position.isAdjacentTo(this.getPosition().getCubeCoordinates(), targetCube));
	}
	
	
	//======================================================================================
	// Methods for moving and sprinting.
	//======================================================================================
	
	/**
	 * Checks whether this unit is moving.
	 * 
	 * @return	True if and only if this unit's current activity is set to MOVE.
	 *				|result == (this.getCurrentActivity() == Activity.MOVE)
	 */
	@Basic @Raw
	public boolean isMoving(){
		return (this.getCurrentActivity() == Activity.MOVE);
	}

	/**
	 * Returns the is-sprinting value of this unit.
	 */
	@Basic @Raw
	public boolean getIsSprinting() {
		return this.isSprinting;
	}
	
	/**
	 * Checks whether the given value is a valid is-sprinting value for this unit.
	 *  
	 * @param	is-sprinting
	 *				The is-sprinting value to check.
	 *         
	 * @return	The unit can only sprint if it has stamina left
	 *				and is not terminated.
	 *				| if (isSprinting == true)
	 *				| 	then result == (getCurrentStamina > 0) && (! isTerminated())
	 *				A unit can always stop sprinting.
	 *				| else (return true)
	 */ 		
	public boolean isValidIsSprinting(boolean isSprinting) {
		if (isSprinting)
			return (this.getCurrentStamina()>0) && (! this.isTerminated());
		else
			return true;
	}
	
	/**
	 * Sets the is-sprinting property of this unit to the given value.
	 * 
	 * @param	isSprinting
	 *				The new is-sprinting value for this unit.
	 *
	 * @post	The is-sprinting value of this new unit is equal to the given is-sprinting value.
	 *				| new.getIsSprinting() == isSprinting
	 *
	 * @throws 	IllegalStateException
	 *				The given is-sprinting value is not a valid is-sprinting for any unit.
	 *				| ! isValidIsSprinting(getIsSprinting())
	 */
	@Raw
	public void setIsSprinting(boolean isSprinting) throws IllegalStateException {
		if (! isValidIsSprinting(isSprinting))
			throw new IllegalStateException("This unit cannot sprint.");
		
		this.isSprinting = isSprinting;
	}
	
	/**
	 * A variable that stores whether this unit is sprinting or not.
	 */
	private boolean isSprinting;

	/**
	 * Makes this unit start sprinting, if possible.
	 * 
	 * @effect	The unit's is-sprinting value is set to true, if it is not falling.
	 *				| if ( ! this.isFalling())
	 *				|	then setIsSprinting(true)
	 */
	@Raw
	public void startSprinting(){
		try{
			if ((this.isTerminated()) || ( ! this.isFalling()))
				this.setIsSprinting(true);
		} catch (IllegalStateException e){}
	}
	
	/**
	 * Makes this unit stop sprinting.
	 * 
	 * @effect	The unit's is-sprinting value is set to false.
	 *				| setIsSprinting(true)
	 */
	@Raw
	public void stopSprinting(){
		this.setIsSprinting(false);
	}
	
	/**
	 * Checks if this unit can move to the given destination.
	 * It does not check if there is a full path, bur rather does
	 * some simple checks.
	 * 
	 * @param	destination
	 *				The destination for the unit to move to.
	 * 
	 * @return	True if and only if:
	 *				- The destination cube is passable and
	 *				- The destination cube has at least one solid neighbour and
	 *				- The destination cube has at least one passable neighbour and
	 *				- The current position has at least one passable neighbour.
	 *				| result == (destinationCube.isPassable()) && (this.hasSolidNeighbours(destination)) &&
	 *				| (this.hasPassableNeighbours(destination)) && 
	 *				| (this.hasPassableNeighbours(this.getCubeCoordinates()))
	 */
	public boolean canMoveTo(int[] destination) {
		Cube destinationCube = this.getWorld().getCube(destination);
		
		return ( (destinationCube.isPassable()) && (this.hasSolidNeighbours(destination)) &&
				(this.hasPassableNeighbours(destination)) && 
				(this.hasPassableNeighbours(this.getCubeCoordinates())) );
	}
	
	/**
	 * Initiates movement to the given coordinates.
	 * 
	 * @param	dx
	 *				The move to make in the x-direction.
	 * @param	dy
	 *				The move to make in the y-direction.
	 * @param	dz
	 *				The move to make in the z-direction.
	 * 
	 * @effect Initiate movement to the given coordinates and disable default behavior.
	 *				| moveToAdjacent(dx, dy, dz, false)
	 */
	public void moveToAdjacent(int dx, int dy, int dz) throws IllegalArgumentException{
		this.moveToAdjacent(dx, dy, dz, false);
 	}	
	
	/**
	 * Initiates movement to the given coordinates.
	 * 
	 * @param	dx
	 *				The move to make in the x-direction.
	 * @param	dy
	 *				The move to make in the y-direction.
	 * @param	dz
	 *				The move to make in the z-direction.
	 * @param thisIsDefaultBehavior
	 *				Whether the movement is initiated by default behavior or the player.
	 *
	 * @post	The current activity of the unit is set to MOVE.
	 *				| new.getCurrentActivity() == MOVE
	 * @post	The target coordinates of the unit are set to the cube decided by the given direction.
	 *				| new.getTargetCoordinates() =={this.getPosition().getCubeCoordinates()[0] + dx,
	 *				|							this.getPosition().getCubeCoordinates()[1] + dy,this.getPosition().getCubeCoordinates()[2] + dz}
	 * @post	The initial coordinates of the unit are set to the current coordinates.
	 *				| new.getInitialCoordinates() == this.getCoordinates()
	 *
	 * @note Throws no exception because Exception is caught.
	 */
	private void moveToAdjacent(int dx, int dy, int dz, boolean thisIsDefaultBehavior) {
		// TODO Moving to an adjacent cube may only be interrupted if the unit is attacked, or falling.
		try {
			this.setProgress(0);
			
			Cube destinationCube = this.getWorld().getCube(
					this.getPosition().getCubeCoordinates()[0]+dx, this.getPosition().getCubeCoordinates()[1]+dy, this.getPosition().getCubeCoordinates()[2]+dz);
			
			if (( ! thisIsDefaultBehavior) && (this.getDefaultBehaviorEnabled())){
				this.stopDefaultBehavior();
			}
			
			if ((this.getCurrentActivity() == Activity.MOVE))
				throw new IllegalStateException("Unit is already moving.");
			
			
			if ((dx==0) && (dy==0) && (dz==0)) {
				throw new IllegalArgumentException("No need to move unit.");
			}
			if (! destinationCube.isPassable()){
				throw new IllegalArgumentException(destinationCube.toString());
			}

			if ( (1<dx) || (dx<-1) || (1<dy) || (dy<-1) || (1<dz) || (dz<-1) )
				throw new IllegalArgumentException();
			
			double[] dummyCoordinates = {this.getPosition().getXCoordinate() + dx, this.getPosition().getYCoordinate() + dy, this.getPosition().getZCoordinate() + dz};
	 		this.setCurrentActivity(Activity.MOVE);
	 		this.setTargetCoordinates(dummyCoordinates);
	 		this.setInitialCoordinates(this.getCoordinates());
		} 
		catch (Exception e) {
		}
	}
	
	/**
	 * Checks whether this unit can start moving to an adjacent cube.
	 * A unit can only start moving if it is not falling.
	 * 
	 * @return True if this unit is not falling.
	 *				| ( ! this.isFalling())
	 */
	public boolean canDoMoveToAdjacent() {
		return ( ! this.isFalling());
	}
	
	/**
	 * Initializes movement for this unit to the given cube coordinates and disables default behavior.
	 * 
	 * @param	destinationCoordinates
	 *				The destination cube.
	 * 
	 * @effect Initializes movement for this unit to the given cube coordinates and disables default behavior.
	 *				| this.moveTo(destinationCoordinates, false)
	 */
	public void moveTo(int[] destinationCoordinates) throws IllegalArgumentException{
		this.moveTo(destinationCoordinates,false);
	}
	
	/**
	 * Initializes movement for this unit to the given cube position by filling moveToPath.
	 * 
	 * @param	destinationCoordinates
	 *				The destination cube.
	 * @param	thisIsDefaultBehavior
	 *				Whether attacking is called by default behavior or not.
	 *
	 * @post	The progress of this unit is equal to zero.
	 *				|new.getProgress() == 0
	 *
	 * @note Silent reject for:
	 *				no adjacent solid cube (makes a unit go to mid air, which will make it fall), 
	 *				target is unreachable because is is surrounded by solid cubes,
	 *				this unit cannot move away from it's current position because it is locked in by solid cubes.
	 *				This prevents the program from trying all possible cubes in the world (which takes very long, even for small worlds),
	 *				while we know there is no path.
	 */
	public void moveTo(int[] destinationCoordinates, boolean thisIsDefaultBehavior) 
			throws IllegalArgumentException {
		try {
			if ( ! this.getPosition().canHaveAsCoordinates(destinationCoordinates)) {// Checking if the target cube is passable happens in canHaveAsUnitCoordinates.	
				throw new IllegalArgumentException();
			}
			if ( ! this.canMoveTo(destinationCoordinates))
				throw new IllegalArgumentException();
			if ( Position.equals(this.getCubeCoordinates(), destinationCoordinates))
				throw new IllegalStateException("already at destination");
			
			else {
				if ( ! thisIsDefaultBehavior)
					this.stopDefaultBehavior();
				
				this.setProgress(0);
				
				// Readability
				int[] currentCoordinates =  this.getPosition().getCubeCoordinates();	
				
		 		// Pass the current activity trough to previousActivity and Set the current activity op MOVE.
				moveToPath = searchPath(currentCoordinates, destinationCoordinates);
				moveToPath.remove(0); 
				this.setPreviousActivity(this.getCurrentActivity());
				this.setCurrentActivity(Activity.MOVE);
				this.setInitialCoordinates(this.getCoordinates());
		 		
			}
		} catch (IllegalArgumentException e) {
			if (this.hasTask())
 				this.returnFailedTask();
		} catch (IllegalStateException e) {
			if (this.hasTask())
 				this.finishTask();
		}
	}
	
	/**
	 * Searches a path from the start coordinates to the destination coordinates.
	 * 
	 * @param startCoordinates
	 *				The start coordinates for the path.
	 * @param destinationCoordinates
	 *				The destination coordinates for the path.
	 *
	 * @return	A list a cube coordinates which represent the path from the start coordinates to the destination coordinates.
	 */
	private List<int[]> searchPath(int[] startCoordinates, int[] destinationCoordinates)
			throws MaxIterationException {
		return PathFinder.getPath(startCoordinates, destinationCoordinates, this.getWorld());
	}

	/**
	 * A list that stores the path this unit has to follow.
	 * It is filled when executing moveTo() and it is used during advanceTime to decide the next coordinates to move to.
	 */
	public List<int[]> moveToPath = new ArrayList<>();
	
	/**
   	 * Cancels the move-to command of this non-terminated unit.
   	 * 
   	 * @effect	The unit's target coordinates, initial coordinates and moveToPath are reset.
   	 *				The unit stands in the center of its current cube and does nothing.
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
	 * Checks whether this unit is falling.
	 * 
	 * @return	True if none of the neighbouring cubes is solid
	 *				and this unit stands in the center of a cube.
	 *				True if the is-falling property is true.
	 *				False if if the z-coordinate of this unit's cube is 0.
	 *				| if (isFalling)
	 *				|	then result == true
	 *				| else if (getCubePosition()[2] == 0)
	 *				|	then result == false
	 *				| else
	 *				| 	result == !hasSolidNeighbours(this.getXCoordinate(), this.getYCoordinate(), this.getZCoordinate())
	 *
	 * @throws	IllegalArgumentException
	 * 			The unit does not occupy a valid cube.
	 */
   	@Override
	protected boolean isFalling() throws IllegalArgumentException {
		// NOTE: do not change the is-falling property in this method itself! 
		// It will break the code (not to mention it is bad practice to return and change values in one method).
		if (this.getIsFalling())
			return true;
		else if (this.getCubeCoordinates()[2] == 0)
			return false;
		else
			return ( ! hasSolidNeighbours(this.getPosition().getCubeCoordinates())
					 && (Position.fuzzyEquals(this.getCoordinates(), this.getCubeCenter())));
	}
	
	/**
	 * Checks whether the cube coordinates with the given coordinates are adjacent
	 * to at least one cube which is solid.
	 * 
	 * @param	coordinates
	 *				The coordinates to check.
	 *
	 * @return	False if none of the neighbouring cubes is solid.
	 *				| for (int[] position: neighbours)
	 *				|	if ( ! getWorld().getCube(coordinates).isPassable())
	 *				|		then result == true
	 *				| result == false
	 */
	public boolean hasSolidNeighbours(int[] coordinates) throws IllegalArgumentException {
		Set<int[]> neighbours = this.getWorld().getNeighbours(coordinates);
		for (int[] neighbour: neighbours)
			if ( ! this.getWorld().getCube(neighbour).isPassable())
				return true;
		return false;
	}
	
	/**
	 * Checks whether the cube coordinates with the given coordinates are adjacent
	 * to at least one cube which is passable.
	 * 
	 * @param	coordinates
	 *				The coordinates to check.
	 *
	 * @return	False if none of the neighbouring cubes is passable.
	 *				| for (int[] position: neighbours)
	 *				|	if ( getWorld().getCube(coordinates).isPassable())
	 *				|		then result == true
	 *				| result == false
	 */
	public boolean hasPassableNeighbours(int[] coordinates) throws IllegalArgumentException {
		Set<int[]> neighbours = this.getWorld().getNeighbours(coordinates);
		for (int[] neighbour: neighbours)
			if ( this.getWorld().getCube(neighbour).isPassable())
				return true;
		return false;
	}
	
	
	//===========================================================================================
	// Methods for orientation.
	//===========================================================================================
	
	/**
	 * Returns the orientation of this unit.
	 */
	@Basic @Raw
 	public double getOrientation() {
		return this.orientation;
	}
	
	/**
	 * Checks whether the given orientation is a valid orientation for  any unit.
	 *  
	 * @param	orientation
	 *				The orientation to check.
	 *         
	 * @return	True if and only if the angle is in between -PI and PI, inclusively.
	 *				| result == (orientation <=PI) && (orientation >= -PI)
	 *
	 * @note	The orientation is often the angle in polar coordinates 
	 *				of a point with cartesic coordinates (x,y). It is mostly calculated
	 *				using atan(double y, double x). This method returns, under 
	 *				normal circumstances, a value between -PI and PI. 
	 *				Exceptional values such as NaN will be changed to the default value (PI/2).
	*/
	@Raw
	public static boolean isValidOrientation(double orientation) {
		return (orientation <=Math.PI) && (orientation >= -Math.PI);
	}
	
	/**
	 * Sets the orientation of this unit to the given orientation.
	 * 
	 * @param	orientation
	 *				The new orientation angle for this unit.
	 *
	 * @post	If the given orientation is a valid orientation for any unit,
	 *				the orientation of this new unit is equal to the given orientation.
	 *				| if (isValidOrientation(orientation))
	 *				|   then new.getOrientation() == orientation
	 */
	@Raw
	private void setOrientation(double orientation) {
		if (isValidOrientation(orientation))
			this.orientation = orientation;
	}
	
 	/**
 	 * Sets the orientation of this unit to the orientation given by the velocity of this unit in the x- and y-direction.
 	 * 
 	 * @param	velocityX
 	 *				The velocity in the x-direction.
 	 * @param	velocityY
 	 *				The velocity in the y-direction.
 	 *
 	 * @effect	The orientation is set to the value of arctangent of velocityY and velocityX.
 	 *				| setOrientation(Math.atan2(velocityY,velocityX))
 	 */
 	public void setOrientation(double velocityX, double velocityY ) {
 		double orientation = Math.atan2(velocityY, velocityX);
 		setOrientation(orientation);
 	}
	
	/**
	 * A variable that stores the orientation of this unit.
	 */
	private double orientation;
	
	// ==================================================================================
	// Methods concerning the task of this unit.
	// ==================================================================================
	
	/**
	 * Returns the task of this unit.
	 */
	@Basic @Raw
	public Task getTask() {
		return this.task;
	}
	
	/**
	 * Checks whether this unit has a task.
	 * 
	 * @return	True if the task of this unit is effective.
	 *				| result == (getTask() != null)
	 */
	public boolean hasTask() {
		return (this.getTask() != null);
	}
	
	/**
	 * Checks whether this unit has a proper task attached to it.
	 * 
	 * @return	True if and only if the task of this unit does not reference
	 *				an effective task, or that task references this unit as its unit.
	 *				| result == ( (this.getTask() == null) || 
	 *				|				(this.getTask().getUnit() == this) )
	 */
	public boolean hasProperTask() {
		return ( (this.getTask() == null) || (this.getTask().getUnit() == this) );
	}
	
	/**
	 * Sets the task attached to this unit to to the given task.
	 * 
	 * @param	task
	 *				The task to attach to this unit.
	 *
	 * @post	This unit references the given task as its task.
	 *				| new.getTask() == task
	 *
	 * @throws	IllegalArgumentException
	 *				The given task is effective but does not references
	 *				this unit as its unit.
	 *				| (task != null) && (task.getUnit() != this)
	 *				Or, the task is not effective and this unit references
	 *				a task which still references this unit as its unit.
	 *				| (task == null) && (this.hasTask() && (this.getTask().getUnit() == this))
	 */
	public void setTask(@Raw Task task) throws IllegalArgumentException {
		if ( (task != null) && (task.getUnit() != this) )
			throw new IllegalArgumentException("Task does not properly associate this unit.");
		if ( (task == null) && (this.hasTask() && (this.getTask().getUnit() == this)))
			throw new IllegalArgumentException("Link with current task not properly broken.");
		this.task = task;
	}
	
	/**
	 * A variable that holds the task attached to this unit.
	 */
	private Task task;
	
	/**
	 * Finish the task of this unit, if it has one.
	 * 
	 * @effect If this unit has a task, then the task gets terminated.
	 *				| if (this.hasTask())
	 *				|	then this.getTask().terminate()
	 */
	private void finishTask() {
		if (this.hasTask()) 
			this.getTask().terminate();
	}
	
	/**
	 * Returns a failed task back to the scheduler with a lower priority.
	 * 
	 * @effect	If this unit has a task with a failed statement,
	 *				then the task is detached from this unit and
	 *				the priority is lowered by one. The statement status is reset.
	 *				| task.setPriority(task.getPriority()-1)
	 *				| task.setUnit(null)
	 *				| this.setTask(null)
	 *				| task.getStatement().resetStatus()
	 * @throws	IllegalStateException
	 * 				This unit does not have a task.
	 * 				| ! this.hasTask()
	 */
	private void returnFailedTask() throws IllegalStateException {
		if (! this.hasTask())
			throw new IllegalStateException();
		this.getTask().returnFailedTask();
		this.moveToPath.clear();
	}
	
	
	// ==================================================================================
	// Methods for the different activities.
	// ==================================================================================
	
	/**
	 * Returns the current activity of this unit.
	 */
	@Basic @Raw
	public Activity getCurrentActivity(){
		return this.currentActivity;
	}
	
	/**
	 * Sets the current activity of this non-terminated unit to the given activity.
	 * 
	 * @param	activity
	 *				The new activity of this unit.
	 * 
	 * @post	The given activity is equal to the current activity.
	 *				| new.getCurrentActivity() == activity
	 *
	 * @throws	IllegalStateException
	 *				The unit is terminated.
	 *				| isTerminated()
	 *@throws	NullPointerException
	 *				The given activity is null.
	 *				| activity == null
	 */
	@Raw
	public void setCurrentActivity(Activity activity)  throws IllegalStateException{
		if (activity == null)
			throw new NullPointerException();
		if (this.isTerminated())
			throw new IllegalStateException("unit terminated.");
		
		this.currentActivity = activity;
	}
	
	/**
	 * A variable that stores the current activity of this unit. The default value is NOTHING.
	 */
	private Activity currentActivity = Activity.NOTHING;
	
	/**
	 * Checks whether the given progress is a valid progress for any unit.
	 * 
	 * @param	progress
	 *				The progress to check.
	 * 
	 * @return	True if and only the given progres, is greater than or equal to zero.
	 *				| result == progress >= 0
	 */
	@Raw
	public static boolean isValidProgress(double progress){
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
	 * Sets the progress of the current activity to the given progress.
	 * 
	 * @param	progress
	 *				The new progress of the current activity of this unit..
	 * 
	 * @post	The new progress of this unit is equal the the given progress.
	 *				| new.getProgress() == progress
	 */
	private void setProgress(double progress) throws IllegalArgumentException{
		if ( ! isValidProgress(progress))
			throw new IllegalArgumentException("The given progress is invalid.");
		
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
	 *				| result == 500.0/this.getStrength()
	 */
	public double getWorkDuration(){
		return 500.0/this.getStrength();
	}
	
	/**
	 * Checks whether this unit is working.
	 * 
	 * @return	True if and only if the current activity is WORK.
	 **				| result == this.getCurrentActivity() == Activity.WORK
	 */
	@Raw
	public boolean isWorking(){
		return this.getCurrentActivity() == Activity.WORK;
	}
	
	/**
	 * Makes this unit start working at the given position and disables default behavior.
	 * 
	 * @param	workTarget
	 *				The position to perform work at.
	 *
	 * @effect	Makes this unit start working at the given target coordinates and disables default behavior.
	 *				| this.workAt(workTarget, false)
	 */
	public void workAt(int[] workTarget) {
		this.workAt(workTarget, false);
	}
	
	/**
	 * Makes this unit work at the given position.
	 * 
	 * @param	workTarget
	 *				The position to perform work at.
	 * @param	thisIsDefaultBehavior
	 *				Whether working is called by default behavior or not.
	 *
	 * @post	The new current activity of this unit is equal to WORK.
	 *				| new.getCurrentActivity() == Activity.WORK
	 * @post	The new workTarget of this unit is equal to the given workTarget.
	 *				| new.getWorkTarget() == workTarget
	 * @post	If workAt is not called by defaultBehavior, then default behavior is disabled.
	 *				| if ( ! thisIsDefaultBehavior)
	 *				|	then this.getDefaultBehaviorEnabled() == false 
	 * @post	The progress of this unit is equal to zero.
	 *				|new.getProgress() == 0
	 */
	public void workAt(int[] workTarget, boolean thisIsDefaultBehavior){
		try {
			if (( ! thisIsDefaultBehavior) && (this.getDefaultBehaviorEnabled()))
				this.stopDefaultBehavior();
			
			if(this.hasItem() && ( ! this.getItem().canHaveAsCoordinates(workTarget)))
				throw new IllegalArgumentException("cannot drop item here.");
			
			
			// MoveToAdjacent may not be interupted except by being attacked.
			if ((this.getCurrentActivity() != Activity.MOVE) && (moveToPath.size() == 0)) {
				this.setProgress(0.0);
				
				// If the workTarget is not in range, then there is a silent reject.
				if (this.isAdjacentTo(workTarget)) {
					double[] cubeCoordinates = Position.getCubeCenter(workTarget);
					this.setOrientation(cubeCoordinates[0] - this.getPosition().getXCoordinate(), cubeCoordinates[1] - this.getPosition().getYCoordinate());
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
	 * Returns the workTarget of this unit.
	 */
	@Basic @Raw
	private int[] getWorkTarget() {
		return this.workTarget;
	}
	
	/**
	 * Sets the target coordinates for this unit to work at.
	 * 
	 * @param workTarget
	 *				The new target coordinates for this unit to work at.
	 *
	 * @post	The new target coordinates for this unit to work at,  are equal to the given workTarget.
	 *				| new.getWorkTarget() == workTarget
	 */
	@Raw
	private void setWorkTarget(int[] workTarget) {
		this.workTarget = workTarget;
	}

	/** 
	 * A variable that stores the coordinates this unit should work at.
	 */
	private int[] workTarget;
	
	/**
	 * Makes this unit start resting and turns of default behavior.
	 * 
	 * @effect Makes this unit start resting and disables fedault behavior.
	 *				| this.rest(false);
	 */
	public void rest(){
		this.rest(false);
	}
	
	/**
	 * Makes this unit start resting.
	 * 
	 * @param	thisIsDefaultBehavior
	 *				Whether resting is called by default behavior or not.
	 *
	 * @post	The new current activity of this unit is equal to REST.
	 *				|new.getCurrentActivity() == Activity.REST
	 * @post	If rest is not called by default behavior, then it is disabled.
	 *				| if ( ! thisIsDefaultBehavior)
	 *				|	then setDefaultBehaviorEnabled(true)
	 * @post	The progress of this unit is equal to zero.
	 *				|new.getProgress() == 0
	 */
	private void rest(boolean thisIsDefaultBehavior){
		// moveToAdjacent() may not be interrupted by resting. 
		// moveTo() can however be interrupted.
		try {
			
			if((moveToPath.size() == 0) && (this.getCurrentActivity() == Activity.MOVE) )
				throw new IllegalStateException();
			
			if (( ! thisIsDefaultBehavior) && (this.getDefaultBehaviorEnabled()))
				this.stopDefaultBehavior();
			
			this.setProgress(0);
			
			if (this.getCurrentActivity() != Activity.REST){
				this.setPreviousActivity(this.getCurrentActivity());
				this.setCurrentActivity(Activity.REST);
			}
		} catch (IllegalStateException e) {}
	}
	
	/**
	 * Checks whether this unit is currently resting.
	 * 
	 * @return	True if and only if the current activity of this unit is REST.
	 *				| result == this.getCurrentActivity() == Activity.REST;
	 */
	@Raw
	public boolean isResting(){
		return (this.getCurrentActivity() == Activity.REST);
	}
	
	/**
	 * Returns the time this unit last finished resting.
	 * 
	 * @return	The time this unit last finished resting.
	 *				| result == this.timeOfLastRest
	 */
	@Raw @Basic
	private double getTimeOfLastRest(){
		return this.timeOfLastRest;
	}
	
	/**
	 * Sets the time since this unit last rested to the current game time.
	 * 
	 * @post	The new time of this units last rest is the current game time.
	 *				| new.getTimeOfLastRest() == this.GetGametime()
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
	 * Sets the previous activity of this unit to the given activity.
	 * 
	 * @param	activity
	 *				The activity to set the previous activity to.
	 *
	 * @post	The previous activity of this unit is equal to the given activity.
	 *				| new.previousActivity() == activity 
	 */
	@Raw
	private void setPreviousActivity(Activity activity){
		this.previousActivity = activity;
	}
	
	/**
	 * Returns the previous activity of this unit.
	 */
	@Basic @Raw 
 	public Activity getPreviousActivity(){
		return this.previousActivity;
	}
	
	/**
	 * A variable that stores the previous activity of this unit. 
	 * The initial value is NOTHING since that is the default for current activity.
	 */
	private Activity previousActivity = Activity.NOTHING;
	
	/**
	 * Returns whether this unit has rested long enough to have gained at least one healthpoint.
	 */
	@Basic @Raw
	private boolean getInitialRestTimePassed(){
		return this.initialRestTimePassed;
	}
	
	/**
	 * Sets whether this unit has rested long enough to have gained at least one healthpoint.
	 * 
	 * @param	value
	 *				The new value of this parameter
	 *
	 * @post	The value of initialRestTimePassed is equal to the given value.
	 *				| new.getInitialRestTimePassed() == value
	 */
	@Raw
	private void setInitialRestTimePassed(boolean value){
		this.initialRestTimePassed = value;
	}
	
	/**
	 * A variable that stores whether this unit has rested long enough to have gained at least one healthpoint.
	 */
	private boolean initialRestTimePassed;
	
	/**
	 * Checks whether this unit is currently attacking another unit.
	 * 
	 * @return	True if and only if the current activity of this unit is ATTACK.
	 *				| result == (this.getCurrentActivity() == Activity.ATTACK);
	 */
	@Raw
	public boolean isAttacking(){
		return (this.getCurrentActivity() == Activity.ATTACK);
	}
	
	/**
	 * Sets the unit that is under attack from this unit.
	 * 
	 * @param defender
	 *				The new unit under attack from this unit.
	 *
	 * @post	The new unit that is under attack from this unit is equal to the given defender.
	 *				| new.getUnitUnderAttack() == defender
	 */
	@Raw
	private void setUnitUnderAttack(Unit defender){
		this.unitUnderAttack = defender;
	}
	
	/**
	 * Returns the unit that is under attack from this unit.
	 */
	@Basic @Raw
	public Unit getUnitUnderAttack(){
		return this.unitUnderAttack;
	}
	
	/**
	 * A variable that stores the unit that is under attack from this unit.
	 */
	private Unit unitUnderAttack; 
	
	/**
	 * Makes this unit start attacking another unit.
	 * 
	 * @param	defender
	 *				The unit that will be attacked this unit.
	 * 
	 * @post	The new current activity of this unit is attacking.
	 *				| new.getCurrentActivity() == Activity.ATTACK
	 * @post	The unit that is under attack from this unit is equal to the given defender.
	 *				| new.getUnitThatIsUnderAttack() == defender
	 * @post	The progress of this unit is equal to zero.
	 *				| new.getProgress() == 0
	 */
	public void attack(Unit defender) throws IllegalStateException{
			this.setProgress(0);
			this.setCurrentActivity(Activity.ATTACK);
			this.setUnitUnderAttack(defender);
	}
	
	/**
	 * Makes this unit fight with a given defender and disables default behavior.
	 * 
	 * @param	defender
	 *				The unit to attack.
	 * 
	 * @effect Makes this unit start fighting with the given defender and disables default behavior.
	 *				| this.fight(defender, false)
	 */
	public void fight(Unit defender){
		this.fight(defender, false);
	}
	
	/**
	 * Makes this unit fight with another unit.
	 * 
	 * @param	defender
	 *				The unit to attack.
	 * @param	thisIsDefaultBehavior
	 *				Whether attacking is called by default behavior or not.
	 * 
	 * @post	If fight is not called by default behavior, then default behavior is stopped.
	 *				| if ( ! thisIsDefaultBehavior)
	 *				|	this.setDefaultBehaviorEnabled(false)
	 * @post	The unit under attack from this unit is set to the given defender.
	 *				| this.getUnitUnderAttack() == defender
	 * @post	If the given defender is in range, the current activity of this unit will be set to ATTACK.
	 *				| if ( ! this.isAdjacentTo(other.getPosition().getCubeCoordinates()))
	 *				|	then (new this).getUnitBeingFought() == other
	 */
	private void fight(Unit defender, boolean thisIsDefaultBehavior){
		if (thisIsDefaultBehavior)
			System.out.println("fight by defaultBehavior");
		try {
			if (( ! thisIsDefaultBehavior) && (this.getDefaultBehaviorEnabled()))
				this.stopDefaultBehavior();
			
			if(defender == null)
				throw new NullPointerException("The given defender is not an effective unit");
			// If the units are not in eachothers range, then an exception is thrown.
			if ( ! this.isAdjacentTo(defender.getPosition().getCubeCoordinates()))
						throw new IllegalArgumentException("The given defender is not in range");
			//	If the units belong to the same faction they cannot fight eachother. 
			if (defender.getFaction() == this.getFaction())
				throw new IllegalArgumentException("The given defender belongs to the same faction as this unit.");

			this.attack(defender);
		} catch (Exception e) {}
	}
	
	/**
	 * Checks whether this unit managed to block the given attacker.
	 * 
	 * @param	attacker
	 *				The unit that is trying to attack this unit.
	 * 
	 * @return	The chance to block is greater than or equal to a random number in the range 0..1. 
	 *				The chance to block is given by the following formula:
	 *				| result == ( 0.25*( (this.getStrength() + this.getAgility()) / 
	 *				| 		 (attacker.getStrength() + attacker.getAgility()) ) 
	 *				|					<= Math.random() )
	 *
	 * @throws	NullPointerException
	 *				The given attacker is not an active unit
	 *				| attacker == null
	 */
	private boolean managedToBlock(Unit attacker) throws NullPointerException{
		if (attacker == null)
			throw new NullPointerException("The given attacker is not an active unit.");
		
		double blockChance = 0.25*( (this.getStrength() + this.getAgility()) / (attacker.getStrength() + attacker.getAgility()) );
		double temp = Math.random();
		return (temp <= blockChance);
	}
	
	/**
	 * Checks whether this unit managed to dodge the given attacker.
	 * 
	 * @return	The chance to dodge is greater than or equal to a random number in the range 0..1. 
	 *				The chance to dodge is given by the following formula:
	 *				| result == ( 0.20*(this.getAgility()/attacker.getAgility());
	 *				|					<= Math.random() )
	 * @throws	NullPointerException
	 *				The given attacker is not an active unit
	 *				| attacker == null
	 */
	private boolean managedToDodge(Unit attacker) throws NullPointerException{
		if (attacker == null)
			throw new NullPointerException("The given attacker is not an active unit.");
		
		double dodgeChance = 0.20*(this.getAgility()/attacker.getAgility());
		double temp = Math.random();
		return (temp <= dodgeChance);
	}
	
	/**
	 * Makes this unit defend against an attack of another unit.
	 * 
	 * @param	attacker
	 *				The unit that attacks this unit.
	 * 
	 * @post	If this unit managed to dodge, the variable defenderBlocked of the attacker is set to true and this unit jumped to a random adjacent position.
	 *				| if (this.managedToDodge())
	 *				|	then  (new attacker).defenderBlocked == true
	 *				|		 && (new this).getPosition().getCoordinates() != this.getCoordinates()
	 * @post	If this unit manages to block, the variable defenderBlocked of the attacker is set to true.
	 *	 			| if (this.managedToBlock())
	 *				|	then  (new attacker).defenderBlocked() == true
	 * 
	 * @throws	NullPointerException
	 *				The given attacker is not an active unit
	 *				| attacker == null
	 */
	private void defend(Unit attacker) throws NullPointerException, IllegalArgumentException{
		if (attacker == null){
			throw new NullPointerException("The given attacker is not an active unit.");
		}
		
		// If this unit manages to dodge...
		if (this.managedToDodge(attacker)){
			//	...the variable defenderDodged of the attacker is set to true...
			attacker.setDefenderBlocked(true);
			// ... and the unit jumps to an adjacent position.
			double[] newCoordinates = this.getCoordinates();
			while (Position.equals(newCoordinates, this.getCoordinates()))
				newCoordinates = this.getRandomDodgeMove(this.getCoordinates());
			this.setCoordinates(newCoordinates);
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
	 * Sets whether the unit that is under attack from this unit managed to block or dodge to the given value.
	 * 
	 * @param value
	 *				The new value to set defenderBlocked to.
	 *
	 * @post	The new value of defenderBlocked is equal to the given value.
	 *				| new.getDefenderBlocked() == value
	 */
	@Raw
	void setDefenderBlocked(boolean value){
		this.defenderBlocked = value;
	}
	
	/**
	 * Returns whether the unit that is under under attack from this unit managed to block or dodge.
	 */
	@Basic @Raw
	public boolean getDefenderBlocked(){
		return this.defenderBlocked;
	}
	
	/**
	 * A variable that holds whether the unit under attack from this unit managed to block or dodge.
	 */
	private boolean defenderBlocked;
	
	/**
	 * Generates random coordinates that are at a distance of 0..1 in the x- and y-direction respectively from the given coordinates.
	 * 
	 * @param	coordinates
	 *				The given coordinates.
	 * 
	 * @return	Random coordinates which are at a distance of 0..1 in the x- and y-direction respectively from the given coordinates.
	 *				| result == new double[]{coordinates[0] + new Random().nextDouble()*2-1, coordinates[1] + new Random().nextDouble()*2-1, this.getPosition().getCubeCoordinates()[2]}
	 */
	private double[] getRandomDodgeMove( double[] coordinates) {
		double[] newRandomCoordinates = new double[]{-1, -1, -1};
		while(this.canHaveAsCoordinates(newRandomCoordinates)){
			double newRandomXCoordinate = -1;
			while ( ! this.getPosition().canHaveAsXCoordinate(newRandomXCoordinate))
				 newRandomXCoordinate = coordinates[0] + new Random().nextDouble()*2-1;
			double newRandomYCoordinate = -1;
			while ( ! this.getPosition().canHaveAsYCoordinate(newRandomYCoordinate))
				 newRandomYCoordinate = coordinates[1] + new Random().nextDouble()*2-1;
			newRandomCoordinates = new double[]{ newRandomXCoordinate, newRandomYCoordinate, this.getPosition().getCubeCoordinates()[2]};
	}
		return newRandomCoordinates;
	}
	
	
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
	 * 
	 * @return The item this unit is carrying is not null.
	 *				| this.getItem() != null
	 */
	public boolean hasItem() {
		return this.getItem() != null;
	}
	
	/**
	 * Returns whether this unit is carrying a proper item.
	 * 
	 * @return The item is null or the unit this item is carried by is this unit.
	 *				| result == ( (this.getItem() == null) || (this.getItem().getUnit() == this) )
	 */
	public boolean hasProperItem() {
		return ( ! this.hasItem()) || (this.getItem().getUnit() == this);
	}
	
	/**
	 * Sets the item this unit is carrying.
	 * 
	 * @param item
	 *				The item to be carried.
	 *
	 * @post	The item this unit is carrying is equal to the given item.
	 *				| this.getItem() == item
	 *
	 * @throws IllegalArgumentException
	 *				The given item doesn't reference this unit correctly.
	 *				| (item != null) && (item.getUnit() != this)  
	 * @throws IllegalArgumentException
	 *				The given item is null and the item currently being carried is not correctly dropped yet. 
	 *				| (item == null) && (this.hasItem()) &&(this.getItem().getUnit() == this)
	 */
	void setItem(Item item) throws IllegalArgumentException {
		if ( (item != null) && (item.getUnit() != this))
			throw new IllegalArgumentException();
		if ( (item == null) && (this.hasItem()) &&(this.getItem().getUnit() == this) )
				throw new IllegalArgumentException();
		
		this.item = item;
	}
	
	/**
	 * A variable that stores the item carried by this unit.
	 */
	private Item item;
	
	/**
	 * Makes this unit drop the item it is carrying at the given cube coordinates.
 	 * 
 	 * @param	coordinates
 	 *				The cube coordinates to drop the item at.
 	 *
 	 * @effect 	This unit no longer references the item as its item.
 	 *				| this.setItem(null)
 	 * @effect	The item no longer references this unit as its unit,
 	 *				and now references the world of this unit as its world.
 	 *				| this.getItem().getUnit() == null
 	 *				| && this.getItem().getWorld() == this.getWorld()
 	 * @effect 	The coordinates of the item's position this unit is carrying, 
 	 *				are set to the center of the cube with given coordinates.
 	 *				| this.getItem().getPosition().setCoordinates(Position.getCubeCenter(coordinates))
 	 *
 	 * @throws	IllegalArgumentException
 	 *				The given coordinates are invalid for this item.
 	 *				|  (! this.getItem().canHaveAsCoordinates(coordinates))
  	 */
	private void dropItem(int[] coordinates) throws IllegalArgumentException {
		if (! this.getItem().canHaveAsCoordinates(coordinates))
			throw new IllegalArgumentException();
		
		Item item = this.getItem();
		item.moveToWorld();
		item.getPosition().setCoordinates(Position.getCubeCenter(coordinates));
	}
		
	/**
  	 * Makes this unit pick up the given item.
  	 * 
  	 * @param	item
  	 *				The item to pick up.
  	 *
 	 * @effect	This unit references the given item as its item.
 	 *				The given item references this unit as its unit,
 	 *				and no longer references a world.
 	 *				| item.moveToUnit(this)
  	 */
	private void pickUpItem(Item item) throws IllegalStateException, IllegalArgumentException {
		item.moveToUnit(this);
	}
	
	
	// ===============================================================================
	// Methods concerning default behavior.
	// ===============================================================================
	
	/**
	 * Returns whether default behavior is enabled or not.
	 */
	@Basic @Raw
	public boolean getDefaultBehaviorEnabled(){
		return this.defaultBehaviorEnabled;
	}
	
	/**
	 * Sets whether this unit's default behavior is enabled to the given value.
	 * 
	 * @post	The value of whether this unit's default behavior is enabled, is equal to the given value.
	 *				|new.getDefaultBehaviorEnabled() == true
	 *
	 * @throws	IllegalStateException
	 *				This unit is terminated.
	 *				| isTerminated()
	 */
	private void setDefaultBehaviorEnabled(boolean value) throws IllegalStateException{
		if (this.isTerminated())
			throw new IllegalStateException("unit is terminated.");
		
		this.defaultBehaviorEnabled = value;
	}
	
	/**
	 * A variable that stores whether this unit's default behavior is enabled.
	 */
	private boolean defaultBehaviorEnabled;
	
	/**
	 * Starts the default behavior of this unit.
	 * 
	 * @effect	Sets whether this unit's default behavior is enabled to true.
	 *				| this.setDefaultBehaviorEnabled(true)
	 */
	public void startDefaultBehavior() throws IllegalStateException {
		this.setDefaultBehaviorEnabled(true);
	}
	
	/**
	 * Stops the default behavior of this unit.
	 * 
	 * @effect	This unit stops executing its task.
	 * 				| getTask().stopExecuting()
	 * @effect	Sets whether this unit's default behavior is enabled to false.
	 *				| this.setDefaultBehaviorEnabled(false)
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
	// Methods for following another unit.
	//============================================================================
	
	/**
	 * A variable that holds the unit this unit has to follow.
	 */
	private Unit unitToFollow;
	
	/**
	 * Returns the unit this unit has to follow.
	 */
	public Unit getUnitToFollow() {
		return this.unitToFollow;
	}
	
	/**
	 * Checks whether this unit has to follow another unit.
	 * 
	 * @return	True if the unit to follow is effective.
	 * 				| result == getUnitToFollow() != null
	 */
	public boolean hasUnitToFollow() {
		return this.getUnitToFollow() != null;
	}
	
	/**
	 * Sets the unit this unit has to follow to the given unit.
	 * 
	 * @param 	unitToFollow
	 *				The new unit to follow.
	 * @post	This unit is following the given unit.
	 * 				| new.getUnitToFollow() == unitToFollow
	 */
	private void setUnitToFollow(Unit unitToFollow) {
		this.unitToFollow = unitToFollow;
	}
	
	/**
	 * Checks if this unit is next to the given unit.
	 * 
	 * @param 	other
	 * 				The other unit.
	 * @return	True if the cube coordinates of this unit are next to
	 * 				the cube coordinates of the other unit.
	 * 				| result == Position.isAdjacentTo(
	 * 				|	this.getCubeCoordinates(), other.getCubeCoordinates())
	 */
	public boolean isNextTo(Unit other) {
		return Position.isAdjacentTo(this.getCubeCoordinates(), other.getCubeCoordinates());
	}
	
	/**
	 * Starts the follow behavior of this unit, causing it to follow the given unit.
	 * 
	 * @param 	other
	 * 				The unit to follow.
	 * @param	Whether default behavior should be turned off.
	 * @post	This unit is following the other unit.
	 * 				| new.getUnitToFollow() == other
	 * @post	This unit is executing default behavior.
	 * 				| this.startDefaultBehavior()
	 * @effect	This unit is moving towards the cube of the other unit.
	 * 				| this.moveTo(other.getCubeCoordinates())	
	 *				If the other unit is terminated or this unit is next to it,
	 *				and this unit has a task, that task is finished.
	 *				| if (other.isTerminated()) || (this.isNextTo(other))
	 *				| 	then if (this.hasTask())
	 *				|		then this.finishTask()
	 */
	public void follow(Unit other, boolean defaultbehavior) {
		try {
			if (other.isTerminated())
				throw new IllegalArgumentException("other unit is dead.");
			if (this.isNextTo(other))
				throw new IllegalStateException("already next to other unit.");
			
			if ( ! defaultbehavior)
				this.stopDefaultBehavior();
			this.setUnitToFollow(other);
			this.moveTo(other.getCubeCoordinates(),defaultbehavior);
		} catch (Exception e) {
			if (this.hasTask())
				this.finishTask();
				
		}
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
	 * Returns the experience of this unit.
	 */
	@Basic @Raw
	public int getExperience() {
		return this.experience;
	}
	
	/**
	 * Checks whether this unit can have the given experience as its experience
	 *  
	 * @param	experience
	 *				The experience to check.
	 *         
	 * @return	The experience must be greater than or equal to 0 
	 *				and the combined experience must be less than or equal to the maximal value for an integer.
	 *				| result == (experience >= 0) && (experience+ this.getExperience() <= Integer.MAX_VALUE)
	*/
	private boolean canHaveAsExperience(int experience) {
		return (experience >= 0);
	}
	
	/**
	 * Sets the experience of this unit to the given experience.
	 * 
	 * @param	experience
	 *				The new experience for this unit.
	 *
	 * @post	The experience of this new unit is equal to
	 *				the given experience.
	 *				| new.getExperience() == experience
	 *
	 * @throws	IllegalArgumentException
	 *				The given experience is not a valid experience for any
	 *				unit.
	 *				| ! isValidExperience(getExperience())
	 */
	@Raw
	private void setExperience(int experience) throws IllegalArgumentException {
		if (! canHaveAsExperience(experience))
			throw new IllegalArgumentException();
		this.experience = experience;
	}
	
	/**
	 * A variable that stores the experience of this unit.
	 */
	private int experience;
	
	/**
	 * Returns a list of numbers which represent stats which are not yet at
	 * their maximum value. (agility=0, strength=1, toughness=2)
	 * @return	A list of integers, with their corresponding stat not yet maxed.
	 *				| for (integer in result)
	 *				|	getStatOfInteger() < getMaxBaseStat()
	 */
	private List<Integer> statsNotMaxed() {
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
	 * Raises one stat of this unit by one. The stat is chosen at random and is not yet maxed.
	 * 
	 * @post	One of the unit's stats is raised by one. This stat was not yet maxed.
	 * 			All other stats remain the same.
	 * 			| (new.getStatX() == this.getStatX() + 1) && (new.getStatY() == this.getStatY() && (new.getStatZ() == this.getStatZ())
	 * 
	 * @throws 	UnitMaxedOutException
	 * 			All of the unit's stats are already maxed.
	 * 			| statsNotMaxed().size() == 0
	 * 
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
	 * @param	extraExperience
	 *				The amount of experience added.
	 *
	 * @post	The unit's total experience is raised with the given amount.
	 *				| new.getExperience() == this.getExperience() + extraExperience
	 * @post	If the unit levels up, one of the unit's stat is raised by one. 
	 *				This stat was not yet maxed. All other stats remain the same.
	 *				| (new.getStatX() == this.getStatX() + 1) && (new.getStatY() == this.getStatY() && (new.getStatZ() == this.getStatZ())
	 */
	private void addExperience(int extraExperience) {
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
	 * 
	 * @param	extraExperience
	 *				The extra experience added.
	 *
	 * @return	True if the new total experience has exceeded the amount needed for leveling up.
	 *				| result = ((extraExperience + this.getExperience()) / EXP_LEVELUP > this.getExperience() / EXP_LEVELUP)
	 * @throws	IllegalArgumentException
	 *				The given extra experience is invalid.
	 */
	private boolean hasEnoughExperience(int extraExperience) throws IllegalArgumentException {
		if ( ! this.canHaveAsExperience(extraExperience))
			throw new IllegalArgumentException();
		
		int newTotalExperience = extraExperience + this.getExperience();
		return ( (newTotalExperience / EXP_LEVELUP) > (this.getExperience() / EXP_LEVELUP) );
	}
	
	/**
	 * Returns the amount of times this unit can level up if it receives the given amount of experience.
	 * @param	extraExperience
	 *				The given experience.
	 * 
	 * @return	The amount of times this unit can level up.
	 *				| result == (((extraExperience + this.getExperience()) / EXP_LEVELUP) - (this.getExperience() / EXP_LEVELUP))
	 * @throws 	IllegalArgumentException
	 *				The given experience is invalid.
	 *				| !isValidExperience(extraExperience)
	 */
	private int amountOfLevels(int extraExperience) throws IllegalArgumentException{
		if ( ! this.canHaveAsExperience(extraExperience))
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
	
	/** Sets the is-interrupted property of this unit to the given value.
	 * 
	 * @param value
	 *				The given value.
	 *
	 * @post	The value of is-interrupted is equal to the given value.
	 *				| new.getIsInterrupted() == value
	 */
	@Raw
	private void setIsInterrupted(boolean value) {
		this.isInterrupted = value;
	}
	
	/**
	 * A variable that stores the is-interrupted property of this unit.
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
	
	/**
	 * Advances the game time of this unit with a given deltaT.
	 * 
	 * @param	deltaT
	 * 					The time to increase the game time with.
	 * 
	 * @post	The gameTime of this  unit is equal to its old game time increased with deltaT.
	 *       		| new.getGameTime() == this.getGameTime() + deltaT
	 * @throws	IllegalArgumentException
	 *         			The given deltaT is negative or bigger than 0.2.
	 *       			| (deltaT<0) || (deltaT>0.2)
	 */
	@Override
	public void advanceTime(double deltaT) throws IllegalArgumentException, IllegalStateException, NullPointerException{
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
				this.setInitialCoordinates(this.getCoordinates());
			}
			this.setIsFalling(true);
			this.setCurrentActivity(Activity.MOVE);
			this.setIsSprinting(false);
		}
		
		// Normal case
		else {
			
			// If the initial rest time has not been passed and the this unit is not being attacked, 
			// the current activity always results in REST.
			if ( (this.getPreviousActivity() == Activity.REST) && (this.getCurrentActivity() != Activity.REST) && ( ! this.getInitialRestTimePassed()) && ( ! this.isAttacking()) )
				this.rest(this.getDefaultBehaviorEnabled());
			
			// The unit has to rest every 3 minutes.
			if ( (this.getGametime() - this.getTimeOfLastRest()) >= 180.0 ) 
				this.rest(this.getDefaultBehaviorEnabled());
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
				if (this.getDefaultBehaviorEnabled())
					advanceTimeDefault(deltaT);
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
		if ( (this.getCurrentHealth() < this.getMaxPoints()) && ( ! this.isAttacking()) ) {
			if(this.getCurrentHealth() + extraHealth < this.getMaxPoints())
				this.setCurrentHealth(this.getCurrentHealth() + extraHealth);
			else
				this.setCurrentHealth(this.getMaxPoints());
		}
		// Recover stamina if health is full. Don't recover stamina while fighting.
		else if ( this.getCurrentStamina() < this.getMaxPoints() && ( ! this.isAttacking()) ) {
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
//			if ( this.getDefaultBehaviorEnabled())
//				this.startDefaultBehavior();
			if (this.getPreviousActivity() != null)
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
				if ((defender.getCurrentHealth() - this.getStrength()/10.0) <= 0)
					defender.setCurrentHealth(0);
				else
					defender.setCurrentHealth(defender.getCurrentHealth() - this.getStrength()/10.0);
				 this.addExperience(20);
			}
			
			// Do what has to be done when the defender dies.
			if (defender.getCurrentHealth() <= 0)
				defender.terminate();
			
			// Reset the unit that this unit is attacking.
			this.setUnitUnderAttack(null);
			
			// the current activity is set to nothing.
			this.setCurrentActivity(Activity.NOTHING);
						
			// If default behavior is enabled, keep it running.
			if (this.hasTask()) {
				this.getTask().finishExplicitStatement();
			}
		}
	}
	
	public void advanceTimeWorkAt(double deltaT, int[] workTarget) {
		// Count down to the end of the work command.
		this.setProgress(this.getProgress() + deltaT);
		
		// Dropping boulders and logs has to be instantaneous so it has to happen before the progress check.
		// If this unit is carrying a log or a boulder:
		if (this.hasItem()) {
			this.dropItem(workTarget);
			this.setCurrentActivity(Activity.NOTHING);
			
			this.addExperience(10);
			
			// If default behavior is enabled, keep it running.
			if (this.getDefaultBehaviorEnabled())
				this.getTask().finishExplicitStatement();
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
			
			// the current activity is set to nothing.
			this.setCurrentActivity(Activity.NOTHING);
			
			// If default behavior is enabled, keep it running.
			if (this.hasTask()) {
				this.getTask().finishExplicitStatement();
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
				
				// chance: 1% per deltaT
				int chance = new Random().nextInt(100);
				if (chance < 1)
					this.setIsSprinting(true);
			}
			
			// From moveTo() use the filled-in moveToPath to get the next cube to reach, if necessary.
			if (moveToPath.size() != 0) {
				this.setTargetCoordinates(Position.getCubeCenter(moveToPath.get(0)));
			}
			
			// Calculate the new position.
			for (int count = 0; count < 3; count++){
				newPosition[count] = this.getCoordinates()[count] + 
						this.getVelocity(this.getTargetCoordinates())[count] * deltaT;
			}
			
			// Set the orientation towards the target position.
			if (! Position.fuzzyEquals(this.getTargetCoordinates(), newPosition)) {
				this.setOrientation(this.getVelocity(this.getTargetCoordinates())[0], this.getVelocity(this.getTargetCoordinates())[1]);
			}
			
			// If the target position is reached or surpassed, set to target position.
			if (Position.getDistance(this.getTargetCoordinates(), this.getInitialCoordinates()) 
					<= Position.getDistance(newPosition, this.getInitialCoordinates())) {
				this.setCoordinates(this.getTargetCoordinates());
				
				// update/stop follow behavior.
				if (this.hasUnitToFollow()) {
					if (this.isNextTo(this.getUnitToFollow()))
						this.moveToPath.clear();
					else if (moveToPath.size() >= 1)
						this.moveTo(this.moveToPath.get(this.moveToPath.size()-1), this.getDefaultBehaviorEnabled());
					// recalculate path upon arrival.
					else
						this.follow(this.getUnitToFollow(), this.getDefaultBehaviorEnabled());
				}
				// Remove the cube from the path since you've just reached it.
				else if (moveToPath.size() !=0) {
					moveToPath.remove(0);
					// Recalculate the path since it may have changed by the time this unit moved one cube.
					if (moveToPath.size() >= 1){
						this.moveTo(this.moveToPath.get(this.moveToPath.size()-1), this.getDefaultBehaviorEnabled());
					}
				}
				
				// Add experience (1 per cube traversed)
				this.addExperience(1);
				
				
				// Last position to reach, no need to move anymore.
				if (moveToPath.size() == 0) {
					this.resetCoordinates();
					this.setCurrentActivity(Activity.NOTHING);
					if (this.hasTask())
						this.getTask().finishExplicitStatement();
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
					this.setCurrentStamina(0);
					this.setIsSprinting(false);
				}
			}
		}
	}
	
	private void advanceTimeFall(double deltaT) {
		double[] newCoordinates;

		double[] targetCoordinates = Position.getCubeCenter(this.getWorld().getCubeBelow(Position.getCubeCoordinates(initialCoordinates)).getPosition().getCoordinates());
		this.setTargetCoordinates(targetCoordinates);

		this.setCoordinates(this.getPosition().getSurfaceCenter());
		
		// No other movement allowed.
		this.moveToPath.clear();
		
		// Calculate the new position.
		newCoordinates = Position.calculateNextCoordinates(this.getCoordinates(), Entity.FALL_VELOCITY, deltaT);
		
		// No updates needed for orientation (only for x and y).
		
		// The target position is reached or surpassed.
		if (Position.getDistance(this.getTargetCoordinates(), this.getInitialCoordinates()) 
				<= Position.getDistance(newCoordinates, this.getInitialCoordinates())) {

			// Update initial position
			this.setInitialCoordinates(targetCoordinates);
			this.setCoordinates(targetCoordinates);
			
			// Lose health
			if (this.getCurrentHealth() > 10)
				this.setCurrentHealth(this.getCurrentHealth()-10);
			else{
				this.setCurrentHealth(0);
				this.terminate();
			}
			
			// The unit has stopped falling
			if (this.hasSolidNeighbours(UnitPosition.getCubeCoordinates(targetCoordinates))) {
				
				this.setCoordinates(this.getTargetCoordinates());
				this.setCurrentActivity(Activity.NOTHING);
				this.resetCoordinates();
				this.setIsFalling(false);
			}
		} else {
			this.setCoordinates(newCoordinates); 				
		}	
	}
	
	private void advanceTimeDefault(double deltaT) {
		Scheduler scheduler = this.getFaction().getScheduler();
		List<Task> availableTasks = scheduler.getUnassignedTasks();
		if ((availableTasks.size() != 0) && (!this.hasTask())) {
			availableTasks.get(0).assignTo(this);
		}
		if (this.hasTask()) {
			Statement statement  = this.getTask().getStatement();
			double i = 0.0;
			while (i < deltaT && !(this.getTask().hasExplicitStatement())) {
				Status status = statement.getStatus();
				switch (status) {
					case NOTSTARTED:
						statement.execute();
						break;
					case DONE:
						this.finishTask();
						break;
					case FAILED:
						this.returnFailedTask();
					default:
						break;
				}
				i += STATEMENT_EXECUTION_TIME;
			}
		}
				
		else {
			List<Activity> options = DefaultManager.getAvailableRandomActivities(this);
			int choice = new Random().nextInt(options.size());
			Activity randomActivity = options.get(choice);
			if (randomActivity == Activity.WORK) {
				RandomSetElement<int[]> randomElement = new RandomSetElement<>();
				Set<int[]> cubes = DefaultManager.getAvailableWorkCubes(this);
				int[] cube = randomElement.getRandomElement(cubes);
				this.workAt(cube,true);
			}
			
			if (randomActivity == Activity.MOVE) {
				RandomSetElement<int[]> randomElement = new RandomSetElement<>();
				Set<int[]> cubes = DefaultManager.getAvailableMoveCubes(this, DefaultManager.MAX_RANGE_DEFAULTMOVE);
				int[] cube = randomElement.getRandomElement(cubes);
				this.moveTo(cube,true);
			}
			
			if (randomActivity == Activity.ATTACK) {
				RandomSetElement<Unit> randomElement = new RandomSetElement<>();
				Set<Unit> units = DefaultManager.getAvailableEnemies(this);
				Unit unit= randomElement.getRandomElement(units);
				this.attack(unit);
			}
			
			if (randomActivity == Activity.REST) {
				this.rest(true);
			}
		}	
	}
}

