package hillbillies.tests;

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import hillbillies.expressions.unitType.AnyExpression;
import hillbillies.expressions.unitType.UnitExpression;
import hillbillies.model.Activity;
import hillbillies.model.Faction;
import hillbillies.model.Log;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part2.internal.map.CubeType;
import hillbillies.part2.internal.map.GameMap;
import hillbillies.part2.internal.map.GameMapReader;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.positions.Position;
import hillbillies.positions.UnitPosition;
import hillbillies.statements.expressionType.actions.FollowStatement;
import ogp.framework.util.Util;
// TODO tests for follow methods


/**
 * A class of tests for the public methods in the class Unit.
 * 
 * @author Sander Mergan, Thomas Vranken
 * @version 2.6
 */
public class UnitTest {
	
	@Before
	public void setUp() throws Exception {
//		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
//		Unit unitRandom = new Unit(world, "RandomUnit");
//		Unit unitMax = new Unit(world, "UnitMax",new int[]{world.getMaximumXValue()-1, 
//																							world.getMaximumYValue()-1, 
//																							world.getMaximumZValue()-1},
//																							100,100,100,100);
//		Unit unitMin = new Unit(world, "UnitMin", new int[]{0, 0, 1},25,25,25, 25);
//		Unit unitLowWeight = new Unit(world, "UnitLowWeight", new int[] {world.getMaximumXValue()-world.getMaximumXValue()/2-3, 
//																														world.getMaximumYValue()-world.getMaximumYValue()+5, 
//																														world.getMaximumZValue()-5}, 
//																														25,60,50,40);
	}
	
	@Test
	public void constructorTest() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMin = new Unit(world, "UnitMin", new int[]{0, 0, 1},25,25,25, 25);
		
		assertEquals("UnitMin", unitMin.getName());
		assertEquals(0.5,unitMin.getPosition().getXCoordinate(),0.0005); // omdat bij van het type double zijn moet je een absolute fout meegeven.
		assertEquals(0.5,unitMin.getPosition().getYCoordinate(),0.0005);
		assertEquals(1.5,unitMin.getPosition().getZCoordinate(),0.0005);
		assertEquals(25, unitMin.getMinWeight());
		assertEquals(25, unitMin.getWeight());
		assertEquals(25, unitMin.getStrength());
		assertEquals(25, unitMin.getAgility());
		assertEquals(25, unitMin.getToughness());
		assertTrue(unitMin.getFaction() != null);
		assertTrue(unitMin.getWorld() == world);
		assertTrue(unitMin.getPosition() instanceof UnitPosition);
		
		Unit unitLowWeight = new Unit(world, "UnitLowWeight", new int[] {world.getMaximumXValue()-world.getMaximumXValue()/2-3, 
																														world.getMaximumYValue()-world.getMaximumYValue()+5, 
																														world.getMaximumZValue()-5}, 
																														25,60,50,40);
		
		assertEquals("UnitLowWeight", unitLowWeight.getName());
		assertEquals(unitLowWeight.getWorld().getMaximumXValue()-unitLowWeight.getWorld().getMaximumXValue()/2-3 +0.5, unitLowWeight.getPosition().getXCoordinate(),0.0005);
		assertEquals(unitLowWeight.getWorld().getMaximumYValue()-unitLowWeight.getWorld().getMaximumYValue()+5 +0.5, unitLowWeight.getPosition().getYCoordinate(),0.0005);
		assertEquals(unitLowWeight.getWorld().getMaximumZValue()-5 + 0.5,unitLowWeight.getPosition().getZCoordinate(),0.0005);
		assertEquals(55, unitLowWeight.getMinWeight());
		assertEquals(55, unitLowWeight.getWeight());
		assertEquals(60, unitLowWeight.getStrength());
		assertEquals(50, unitLowWeight.getAgility());
		assertEquals(40, unitLowWeight.getToughness());
		assertTrue(unitLowWeight.getFaction() != null);
		assertTrue(unitLowWeight.getWorld() == world);
		assertTrue(unitLowWeight.getPosition() instanceof UnitPosition);
		
		
		Unit unitMax = new Unit(world, "UnitMax",new int[]{world.getMaximumXValue()-1, 
				world.getMaximumYValue()-1, 
				world.getMaximumZValue()-1},
				100,100,100,100);
		
		assertEquals("UnitMax", unitMax.getName());
		assertEquals(world.getMaximumXValue()-0.5, unitMax.getPosition().getXCoordinate(),0.0005);
		assertEquals(world.getMaximumYValue()-0.5, unitMax.getPosition().getYCoordinate(),0.0005);
		assertEquals(world.getMaximumZValue()-0.5, unitMax.getPosition().getZCoordinate(),0.0005);
		assertEquals(100, unitMax.getMinWeight());
		assertEquals(100, unitMax.getWeight());
		assertEquals(100, unitMax.getStrength());
		assertEquals(100, unitMax.getAgility());
		assertEquals(100, unitMax.getToughness());
		assertTrue(unitMax.getFaction() != null);
		assertTrue(unitMax.getWorld() == world);
		assertTrue(unitMax.getPosition() instanceof UnitPosition);
		
		Faction faction = new Faction(world);
		Unit unit = new Unit(world, faction, "Unit",new int[]{world.getMaximumXValue()-1, 
				world.getMaximumYValue()-1, 
				world.getMaximumZValue()-1},
				100,100,100,100);
		
		assertEquals("Unit", unit.getName());
		assertEquals(world.getMaximumXValue()-0.5, unit.getPosition().getXCoordinate(),0.0005);
		assertEquals(world.getMaximumYValue()-0.5, unit.getPosition().getYCoordinate(),0.0005);
		assertEquals(world.getMaximumZValue()-0.5, unit.getPosition().getZCoordinate(),0.0005);
		assertEquals(100, unit.getMinWeight());
		assertEquals(100, unit.getWeight());
		assertEquals(100, unit.getStrength());
		assertEquals(100, unit.getAgility());
		assertEquals(100, unit.getToughness());
		assertTrue(unit.getFaction() == faction);
		assertTrue(unit.getWorld() == world);
		assertTrue(unit.getPosition() instanceof UnitPosition);
		
		Unit unit2 = new Unit(world, "UnitTwo");
		assertEquals("UnitTwo", unit2.getName());
		assertTrue(unit2.canHaveAsCoordinates(unit2.getCoordinates()));
		assertTrue(unit2.canHaveAsStrength(unit2.getStrength()));
		assertTrue(unit2.canHaveAsAgility(unit2.getAgility()));
		assertTrue(unit2.canHaveAsToughness(unit2.getToughness()));
		assertTrue(unit2.canHaveAsWeight(unit2.getWeight()));
		assertTrue(unit2.getFaction() != null);
		assertTrue(unit2.getWorld() == world);
		assertTrue(unit2.getPosition() instanceof UnitPosition);
		
	}

	@Test
	public void toStringTest() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Faction faction = new Faction(world);
		Unit unit = new Unit(world, faction, "Unit",new int[]{world.getMaximumXValue()-1, 
				world.getMaximumYValue()-1, 
				world.getMaximumZValue()-1},
				100,100,100,100);
		
		assertTrue(unit.toString().equals("Unit"+"\n"+"name: "+ "Unit"+"\n"+
				"position: "+unit.getPosition().toString()+"\n"+"faction: "+faction+"\n"+
				"world: "+world+"\n"+"====="));
		
	}
	
	@Test
	public void terminateTest() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Faction faction = new Faction(world);
		Unit unit = new Unit(world, faction, "Unit",new int[]{world.getMaximumXValue()-1, 
				world.getMaximumYValue()-1, 
				world.getMaximumZValue()-1},
				100,100,100,100);
		
		assertFalse(unit.isTerminated());
		assertTrue(unit.getFaction() == faction);
		assertTrue(unit.getWorld() == world);
		
		unit.terminate();
		
		assertTrue(unit.isTerminated());
		assertTrue(unit.getFaction() == null);
		assertTrue(unit.getWorld() == null);
		assertTrue(unit.getCurrentActivity() == Activity.NOTHING);
		assertFalse(unit.getIsSprinting());
		assertFalse(unit.getDefaultBehaviorEnabled());
		assertEquals(unit.getTargetCoordinates(), null);
		assertEquals(unit.getInitialCoordinates(), null);
	}
	
	@Test 
	public void canHaveAsMinBaseStatTest(){
		assertTrue(Unit.canHaveAsMinBaseStat(1));
		assertTrue(Unit.canHaveAsMinBaseStat(100));
		assertFalse(Unit.canHaveAsMinBaseStat(0));
	}
	
	@Test 
	public void canHaveAsMaxBaseStatTest(){
		assertTrue(Unit.canHaveAsMinBaseStat(Unit.getMinBaseStat()));
		assertTrue(Unit.canHaveAsMinBaseStat(Unit.getMinBaseStat()+100));
		assertFalse(Unit.canHaveAsMinBaseStat(Unit.getMinBaseStat()-1));
	}
	
	@Test 
	public void canHaveAsMinInitialBaseStatTest(){
		assertTrue(Unit.canHaveAsMinInitialBaseStat(1));
		assertTrue(Unit.canHaveAsMinInitialBaseStat(100));
		assertFalse(Unit.canHaveAsMinInitialBaseStat(0));
	}
	
	@Test 
	public void canHaveAsMaxInitialBaseStatTest(){
		assertTrue(Unit.canHaveAsMaxInitialBaseStat(Unit.getMinInitialBaseStat()));
		assertTrue(Unit.canHaveAsMaxInitialBaseStat(Unit.getMinInitialBaseStat()+100));
		assertFalse(Unit.canHaveAsMaxInitialBaseStat(0));
		assertFalse(Unit.canHaveAsMaxInitialBaseStat(Unit.getMinInitialBaseStat()-1));
	}
	
	@Test
	public void setNameTest() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "Unit",new int[]{world.getMaximumXValue()-1, 
				world.getMaximumYValue()-1, 
				world.getMaximumZValue()-1},
				100,100,100,100);
		
		try{unit.setName("UnitMin"); assertTrue(true);} catch(IllegalArgumentException e){ assertTrue(false);}
		try{unit.setName("Unit Min"); assertTrue(true);} catch(IllegalArgumentException e){ assertTrue(false);}
		try{unit.setName("Unit'\" Min"); assertTrue(true);} catch(IllegalArgumentException e){ assertTrue(false);}
		try{unit.setName("U "); assertTrue(true);} catch(IllegalArgumentException e){ assertTrue(false);}
		try{unit.setName("UNIT MIN"); assertTrue(true);} catch(IllegalArgumentException e){ assertTrue(false);}
		try{unit.setName("\'\'\'\"\"\""); assertTrue(false);} catch(IllegalArgumentException e){ assertTrue(true);}
		try{unit.setName("uNIT MIN"); assertTrue(false);} catch(IllegalArgumentException e){ assertTrue(true);}
		try{unit.setName("M"); assertTrue(false);} catch(IllegalArgumentException e){ assertTrue(true);}
		try{unit.setName("M35"); assertTrue(false);} catch(IllegalArgumentException e){ assertTrue(true);}
	}
	
	@Test
	public void isValidNameTest() {
		//	Valid No spaces.
		assertTrue(Unit.isValidName("UnitMin"));
		// Valid With spaces.
		assertTrue(Unit.isValidName("Unit Min"));
		// Valid with spaces and quotes.
		assertTrue(Unit.isValidName("Unit'\" Min"));
		// Valid two characters.
		assertTrue(Unit.isValidName("U "));
		// Valid spaces and all capital letters.
		assertTrue(Unit.isValidName("UNIT MIN"));
		// Invalid only quotes.
		assertFalse(Unit.isValidName("\'\'\'\"\"\""));
		// Invalid first letter not capital.
		assertFalse(Unit.isValidName("uNIT MIN"));
		// Invalid only one character.
		assertFalse(Unit.isValidName("M"));
		// Invalid digit in name.
		assertFalse(Unit.isValidName("M35"));
	}
	
	@Test
	public void getWeightTest(){
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "Unit", new int[]{0, 0, 1}, 100,100,100,100);
		Log log = new Log(world, new int[]{0, 0, 1});
		
		assertTrue(unit.getWeight() == 100);
		
		unit.workAt(new int[]{0, 0, 1});
		advanceTimeFor(world, 50, 0.1);
		
		assertTrue(unit.getWeight() == (100+log.getWeight()));
	}
	
	@Test
	public void getMinWeightTest() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitLowWeight = new Unit(world, "UnitLowWeight", new int[] {world.getMaximumXValue()-world.getMaximumXValue()/2-3, 
																														world.getMaximumYValue()-world.getMaximumYValue()+5, 
																														world.getMaximumZValue()-5}, 
																														25,60,50,40);
		
		assertEquals(55, unitLowWeight.getMinWeight());
		assertEquals(55, Unit.getMinWeight(60, 50));
	}
	
	@Test
	public void canHaveAsWeightTest() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMin = new Unit(world, "UnitMin", new int[]{0, 0, 1},25,25,25, 25);
		Unit unitMax = new Unit(world, "UnitMax",new int[]{world.getMaximumXValue()-1, 
				world.getMaximumYValue()-1, 
				world.getMaximumZValue()-1},
				100,100,100,100);
		Unit unitLowWeight = new Unit(world, "UnitLowWeight", new int[] {world.getMaximumXValue()-world.getMaximumXValue()/2-3, 
				world.getMaximumYValue()-world.getMaximumYValue()+5, 
				world.getMaximumZValue()-5}, 
				25,60,50,40);
		
		assertTrue(unitMin.canHaveAsWeight(30));
		assertFalse(unitMin.canHaveAsWeight(-1));
		assertFalse(unitMax.canHaveAsWeight(201));
		assertFalse(unitLowWeight.canHaveAsWeight(25));
	}
	
	@Test
	public void setWeightTest(){
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "Unit", new int[]{0, 0, 1}, 100,95,95,100);
		Log log = new Log(world, new int[]{0, 0, 1});
		assertTrue(unit.getWeight() == 100);
		
		unit.setWeight(Unit.getMaxBaseStat()+1);
		assertTrue(unit.getWeight() == Unit.getMaxBaseStat());
		unit.setWeight(Unit.getMinBaseStat()-1);
		assertTrue(unit.getWeight() == unit.getMinWeight());
		unit.setWeight(Unit.getMaxBaseStat());
		assertTrue(unit.getWeight() == Unit.getMaxBaseStat());
		unit.setWeight(Unit.getMinBaseStat());
		assertTrue(unit.getWeight() == unit.getMinWeight());
		
		unit.setWeight(100);
		unit.workAt(new int[]{0, 0, 1});
		advanceTimeFor(world, 50, 0.1);
		
		assertTrue(unit.getWeight() == 100+log.getWeight());
		unit.setWeight(Unit.getMaxBaseStat()+1);
		assertTrue(unit.getWeight() == Unit.getMaxBaseStat()+log.getWeight());
		unit.setWeight(Unit.getMinBaseStat()-1);
		assertTrue(unit.getWeight() == unit.getMinWeight()+log.getWeight());
		unit.setWeight(Unit.getMaxBaseStat());
		assertTrue(unit.getWeight() == Unit.getMaxBaseStat()+log.getWeight());
		unit.setWeight(Unit.getMinBaseStat()-1);
		assertTrue(unit.getWeight() == unit.getMinWeight()+log.getWeight());
		
		unit.setWeight(100);
		unit.workAt(new int[]{0, 0, 1});
		advanceTimeFor(world, 50, 0.1);
		
		assertTrue(unit.getWeight() == 100);
		unit.setWeight(Unit.getMaxBaseStat()+1);
		assertTrue(unit.getWeight() == Unit.getMaxBaseStat());
		unit.setWeight(Unit.getMinBaseStat()-1);
		assertTrue(unit.getWeight() == unit.getMinWeight());
		unit.setWeight(Unit.getMaxBaseStat());
		assertTrue(unit.getWeight() == Unit.getMaxBaseStat());
		unit.setWeight(Unit.getMinBaseStat()-1);
		assertTrue(unit.getWeight() == unit.getMinWeight());
	}
	
	@Test
	public void setInitialWeightTest(){
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "Unit", new int[]{0, 0, 1}, 100,95,95,100);
		Log log = new Log(world, new int[]{0, 0, 1});
		assertTrue(unit.getWeight() == 100);
		
		unit.setInitialWeight(Unit.getMaxInitialBaseStat()+1);
		assertTrue(unit.getWeight() == Unit.getMaxInitialBaseStat());
		unit.setInitialWeight(Unit.getMinInitialBaseStat()-1);
		assertTrue(unit.getWeight() == unit.getMinWeight());
		unit.setInitialWeight(Unit.getMaxInitialBaseStat());
		assertTrue(unit.getWeight() == Unit.getMaxInitialBaseStat());
		unit.setInitialWeight(Unit.getMinInitialBaseStat());
		assertTrue(unit.getWeight() == unit.getMinWeight());
		
		unit.setInitialWeight(100);
		unit.workAt(new int[]{0, 0, 1});
		advanceTimeFor(world, 50, 0.1);
		
		assertTrue(unit.getWeight() == 100+log.getWeight());
		unit.setInitialWeight(Unit.getMaxInitialBaseStat()+1);
		assertTrue(unit.getWeight() == Unit.getMaxInitialBaseStat()+log.getWeight());
		unit.setInitialWeight(Unit.getMinInitialBaseStat()-1);
		assertTrue(unit.getWeight() == unit.getMinWeight()+log.getWeight());
		unit.setInitialWeight(Unit.getMaxInitialBaseStat());
		assertTrue(unit.getWeight() == Unit.getMaxInitialBaseStat()+log.getWeight());
		unit.setInitialWeight(Unit.getMinInitialBaseStat()-1);
		assertTrue(unit.getWeight() == unit.getMinWeight()+log.getWeight());
		
		unit.setInitialWeight(100);
		unit.workAt(new int[]{0, 0, 1});
		advanceTimeFor(world, 50, 0.1);
		
		assertTrue(unit.getWeight() == 100);
		unit.setInitialWeight(Unit.getMaxInitialBaseStat()+1);
		assertTrue(unit.getWeight() == Unit.getMaxInitialBaseStat());
		unit.setInitialWeight(Unit.getMinInitialBaseStat()-1);
		assertTrue(unit.getWeight() == unit.getMinWeight());
		unit.setInitialWeight(Unit.getMaxInitialBaseStat());
		assertTrue(unit.getWeight() == Unit.getMaxInitialBaseStat());
		unit.setInitialWeight(Unit.getMinInitialBaseStat()-1);
		assertTrue(unit.getWeight() == unit.getMinWeight());
	}
	
	@Test
	public void canHaveAsStrengthTest() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitLowWeight = new Unit(world, "UnitLowWeight", new int[] {world.getMaximumXValue()-world.getMaximumXValue()/2-3, 
																														world.getMaximumYValue()-world.getMaximumYValue()+5, 
																														world.getMaximumZValue()-5}, 
																														25,60,50,40);
		Unit unitMin = new Unit(world, "UnitMin", new int[]{0, 0, 1},25,25,25, 25);
		Unit unitMax = new Unit(world, "UnitMax",new int[]{world.getMaximumXValue()-1, 
				world.getMaximumYValue()-1, 
				world.getMaximumZValue()-1},
				100,100,100,100);
		
		assertTrue(unitLowWeight.canHaveAsStrength(150));
		assertFalse(unitMin.canHaveAsWeight(-1));
		assertFalse(unitMax.canHaveAsWeight(201));
	}
	
	@Test
	public void setStrengthTest(){
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "Unit", new int[]{0, 0, 1}, 100,95,95,100);
		assertTrue(unit.getStrength() == 95);
		
		unit.setStrength(Unit.getMaxBaseStat()+1);
		assertTrue(unit.getStrength() == Unit.getMaxBaseStat());
		assertTrue(unit.getWeight() == (Unit.getMaxBaseStat()+95)/2);
		unit.setStrength(Unit.getMinBaseStat()-1);
		assertTrue(unit.getStrength() == Unit.getMinBaseStat());
		assertTrue(unit.getWeight() == (Unit.getMaxBaseStat()+95)/2);
		unit.setStrength(Unit.getMaxBaseStat());
		assertTrue(unit.getStrength() == Unit.getMaxBaseStat());
		assertTrue(unit.getWeight() == (Unit.getMaxBaseStat()+95)/2);
		unit.setStrength(Unit.getMinBaseStat());
		assertTrue(unit.getStrength() == Unit.getMinBaseStat());
		assertTrue(unit.getWeight() == (Unit.getMaxBaseStat()+95)/2);
		
	}
	
	@Test
	public void setInitialStrengthTest(){
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "Unit", new int[]{0, 0, 1}, 90,95,95,100);
		assertTrue(unit.getStrength() == 95);
		
		unit.setInitialStrength(Unit.getMaxInitialBaseStat()+1);
		assertTrue(unit.getStrength() == Unit.getMaxInitialBaseStat());
		assertTrue(unit.getWeight() == (Unit.getMaxInitialBaseStat()+95)/2);
		unit.setInitialStrength(Unit.getMinInitialBaseStat()-1);
		assertTrue(unit.getStrength() == Unit.getMinInitialBaseStat());
		assertTrue(unit.getWeight() == (Unit.getMaxInitialBaseStat()+95)/2);
		unit.setInitialStrength(Unit.getMaxInitialBaseStat());
		assertTrue(unit.getStrength() == Unit.getMaxInitialBaseStat());
		assertTrue(unit.getWeight() == (Unit.getMaxInitialBaseStat()+95)/2);
		unit.setInitialStrength(Unit.getMinInitialBaseStat());
		assertTrue(unit.getStrength() == Unit.getMinInitialBaseStat());
		assertTrue(unit.getWeight() == (Unit.getMaxInitialBaseStat()+95)/2);
		
	}
	
	@Test
	public void canHaveAsAgilityTest() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitLowWeight = new Unit(world, "UnitLowWeight", new int[] {world.getMaximumXValue()-world.getMaximumXValue()/2-3, 
																														world.getMaximumYValue()-world.getMaximumYValue()+5, 
																														world.getMaximumZValue()-5}, 
																														25,60,50,40);
		Unit unitMin = new Unit(world, "UnitMin", new int[]{0, 0, 1},25,25,25, 25);
		Unit unitMax = new Unit(world, "UnitMax",new int[]{world.getMaximumXValue()-1, 
				world.getMaximumYValue()-1, 
				world.getMaximumZValue()-1},
				100,100,100,100);
		
		assertTrue(unitLowWeight.canHaveAsAgility(150));
		assertFalse(unitMin.canHaveAsAgility(-1));
		assertFalse(unitMax.canHaveAsAgility(201));
	}
	
	@Test
	public void setAgilityTest(){
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "Unit", new int[]{0, 0, 1}, 100,95,95,100);
		assertTrue(unit.getAgility() == 95);
		
		unit.setAgility(Unit.getMaxBaseStat()+1);
		assertTrue(unit.getAgility() == Unit.getMaxBaseStat());
		assertTrue(unit.getWeight() == (Unit.getMaxBaseStat()+95)/2);
		unit.setAgility(Unit.getMinBaseStat()-1);
		assertTrue(unit.getAgility() == Unit.getMinBaseStat());
		assertTrue(unit.getWeight() == (Unit.getMaxBaseStat()+95)/2);
		unit.setAgility(Unit.getMaxBaseStat());
		assertTrue(unit.getAgility() == Unit.getMaxBaseStat());
		assertTrue(unit.getWeight() == (Unit.getMaxBaseStat()+95)/2);
		unit.setAgility(Unit.getMinBaseStat());
		assertTrue(unit.getAgility() == Unit.getMinBaseStat());
		assertTrue(unit.getWeight() == (Unit.getMaxBaseStat()+95)/2);
		
	}
	
	@Test
	public void setInitialAgilityTest(){
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "Unit", new int[]{0, 0, 1}, 90,95,95,100);
		assertTrue(unit.getAgility() == 95);
		
		unit.setInitialAgility(Unit.getMaxInitialBaseStat()+1);
		assertTrue(unit.getAgility() == Unit.getMaxInitialBaseStat());
		assertTrue(unit.getWeight() == (Unit.getMaxInitialBaseStat()+95)/2);
		unit.setInitialAgility(Unit.getMinInitialBaseStat()-1);
		assertTrue(unit.getAgility() == Unit.getMinInitialBaseStat());
		assertTrue(unit.getWeight() == (Unit.getMaxInitialBaseStat()+95)/2);
		unit.setInitialAgility(Unit.getMaxInitialBaseStat());
		assertTrue(unit.getAgility() == Unit.getMaxInitialBaseStat());
		assertTrue(unit.getWeight() == (Unit.getMaxInitialBaseStat()+95)/2);
		unit.setInitialAgility(Unit.getMinInitialBaseStat());
		assertTrue(unit.getAgility() == Unit.getMinInitialBaseStat());
		assertTrue(unit.getWeight() == (Unit.getMaxInitialBaseStat()+95)/2);
		
	}
	
	@Test
	public void canHaveAsToughnessTest() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitLowWeight = new Unit(world, "UnitLowWeight", new int[] {world.getMaximumXValue()-world.getMaximumXValue()/2-3, 
																														world.getMaximumYValue()-world.getMaximumYValue()+5, 
																														world.getMaximumZValue()-5}, 
																														25,60,50,40);
		Unit unitMin = new Unit(world, "UnitMin", new int[]{0, 0, 1},25,25,25, 25);
		Unit unitMax = new Unit(world, "UnitMax",new int[]{world.getMaximumXValue()-1, 
				world.getMaximumYValue()-1, 
				world.getMaximumZValue()-1},
				100,100,100,100);
		
		assertTrue(unitLowWeight.canHaveAsToughness(150));
		assertFalse(unitMin.canHaveAsToughness(-1));
		assertFalse(unitMax.canHaveAsToughness(201));
	}
	
	@Test
	public void setToughnessTest(){
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "Unit", new int[]{0, 0, 1}, 100,95,95, 95);
		assertTrue(unit.getToughness() == 95);
		
		unit.setToughness(Unit.getMaxBaseStat()+1);
		assertTrue(unit.getToughness() == Unit.getMaxBaseStat());
		unit.setToughness(Unit.getMinBaseStat()-1);
		assertTrue(unit.getToughness() == Unit.getMinBaseStat());
		unit.setToughness(Unit.getMaxBaseStat());
		assertTrue(unit.getToughness() == Unit.getMaxBaseStat());
		unit.setToughness(Unit.getMinBaseStat());
		assertTrue(unit.getToughness() == Unit.getMinBaseStat());
		
	}
	
	@Test
	public void setInitialToughnessTest(){
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "Unit", new int[]{0, 0, 1}, 90,95,95,95);
		assertTrue(unit.getToughness() == 95);
		
		unit.setInitialToughness(Unit.getMaxInitialBaseStat()+1);
		assertTrue(unit.getToughness() == Unit.getMaxInitialBaseStat());
		unit.setInitialToughness(Unit.getMinInitialBaseStat()-1);
		assertTrue(unit.getToughness() == Unit.getMinInitialBaseStat());
		unit.setInitialToughness(Unit.getMaxInitialBaseStat());
		assertTrue(unit.getToughness() == Unit.getMaxInitialBaseStat());
		unit.setInitialToughness(Unit.getMinInitialBaseStat());
		assertTrue(unit.getToughness() == Unit.getMinInitialBaseStat());
		
	}
	
	@Test
	public void canHaveAsFactionTest(){
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "UnitMax",new int[]{0, 0, 1}, 100,100,100,100);
		Faction faction = new Faction(world);
		Faction faction2 = new Faction(world);
		
		assertTrue(unit.canHaveAsFaction(faction));
		assertFalse(unit.canHaveAsFaction(null));
		
		faction.terminate();
		
		assertFalse(unit.canHaveAsFaction(faction));
		assertFalse(unit.canHaveAsFaction(null));
		
		unit.terminate();
		
		assertFalse(unit.canHaveAsFaction(faction2));
		assertFalse(unit.canHaveAsFaction(faction));
		assertTrue(unit.canHaveAsFaction(null));
	}
	
	@Test
	public void setFactionTest(){
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "UnitMax",new int[]{0, 0, 1}, 100,100,100,100);
		Unit unit2 = new Unit(world, "UnitMax",new int[]{0, 0, 1}, 100,100,100,100);
		Faction faction = new Faction(world);
		Faction faction2 = new Faction(world);
		
		assertTrue(unit.canHaveAsFaction(faction));
		assertTrue(unit.canHaveAsFaction(faction2));
		assertFalse(unit.canHaveAsFaction(null));
		try{unit.setFaction(faction2); assertTrue(true);}catch(IllegalArgumentException e){assertTrue(false);}
		try{unit.setFaction(null); assertTrue(false);}catch(IllegalArgumentException e){assertTrue(true);}
		
		faction.terminate();
		
		assertFalse(unit.canHaveAsFaction(faction));
		assertFalse(unit.canHaveAsFaction(null));
		try{unit.setFaction(null); assertTrue(false);}catch(IllegalArgumentException e){assertTrue(true);}
		try{unit.setFaction(faction); assertTrue(false);}catch(IllegalArgumentException e){assertTrue(true);}
		
		unit.terminate();
		
		assertFalse(unit.canHaveAsFaction(faction2));
		assertFalse(unit.canHaveAsFaction(faction));
		assertTrue(unit.canHaveAsFaction(null));
		try{unit.setFaction(faction); assertTrue(false);}catch(IllegalArgumentException e){assertTrue(true);}
		try{unit.setFaction(faction2); assertTrue(false);}catch(IllegalArgumentException e){assertTrue(true);}
		try{unit.setFaction(null); assertTrue(true);}catch(IllegalArgumentException e){assertTrue(false);}
		
		for (int x=0; x< Faction.MAX_UNITS_FACTION; x++){
			new Unit(world, faction2, "Filler", new int[]{0, 0, 1}, 25, 25, 25, 25);
		}
		
		assertTrue(faction2.getNbUnits() == Faction.MAX_UNITS_FACTION);
		try{unit2.setFaction(faction2); assertTrue(false);}catch(IllegalArgumentException e){assertTrue(true);}
		
		
	}
	
	@Test
	public void canHaveAsCurrentHealthTest() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMax = new Unit(world, "UnitMax",new int[]{world.getMaximumXValue()-1, 
																							world.getMaximumYValue()-1, 
																							world.getMaximumZValue()-1},
																							100,100,100,100);
		
		assertTrue(unitMax.canHaveAsCurrentHealth(50));
		assertFalse(unitMax.canHaveAsCurrentHealth(-50));
		assertFalse(unitMax.canHaveAsCurrentHealth(250));
	}
	
	@Test
	public void canHaveAsCurrentStaminaTest() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMax = new Unit(world, "UnitMax",new int[]{world.getMaximumXValue()-1, 
																							world.getMaximumYValue()-1, 
																							world.getMaximumZValue()-1},
																							100,100,100,100);
		
		assertTrue(unitMax.canHaveAsCurrentStamina(50));
		assertFalse(unitMax.canHaveAsCurrentStamina(-50));
		assertFalse(unitMax.canHaveAsCurrentStamina(250));
	}

	@Test
	public void getMaxPointsTest(){
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "UnitMax",new int[]{0, 0, 1}, 100,100,100,100);
		
		assertEquals(unit.getMaxPoints(), 200);
		assertEquals(Unit.getMaxPoints(100, 100), 200);
		assertEquals(Unit.getMaxPoints(59, 149), 176);
	}
	
	@Test 
	public void canHaveAsWorldTest(){
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		World world2 = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world2, "UnitMax",new int[]{0, 0, 1}, 100,100,100,100);
		
		assertTrue(unit.canHaveAsWorld(world));
		assertFalse(unit.canHaveAsFaction(null));
		
		world.terminate();
		
		assertFalse(unit.canHaveAsWorld(world));
		assertFalse(unit.canHaveAsWorld(null));
		
		unit.terminate();
		
		assertFalse(unit.canHaveAsWorld(world2));
		assertFalse(unit.canHaveAsWorld(world));
		assertTrue(unit.canHaveAsWorld(null));
		
	}
	
	@Test
	public void hasProperWorldTest(){
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "UnitMax",new int[]{0, 0, 1}, 100,100,100,100);
		
		assertTrue(unit.hasProperWorld());
	}
	
	@Test
	public void setWorldTest(){
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		World world2 = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world2, "UnitMax",new int[]{0, 0, 1}, 100,100,100,100);
		Unit unit2 = new Unit(world2, "UnitMax",new int[]{0, 0, 1}, 100,100,100,100);
		
		assertTrue(unit.canHaveAsWorld(world));
		assertTrue(unit.canHaveAsWorld(world2));
		assertFalse(unit.canHaveAsWorld(null));
		try{unit.setWorld(world2); assertTrue(true);}catch(IllegalArgumentException e){assertTrue(false);}
		try{unit.setWorld(null); assertTrue(false);}catch(IllegalArgumentException e){assertTrue(true);}
		
		world.terminate();
		
		assertFalse(unit.canHaveAsWorld(world));
		assertFalse(unit.canHaveAsWorld(null));
		try{unit.setWorld(null); assertTrue(false);}catch(IllegalArgumentException e){assertTrue(true);}
		try{unit.setWorld(world); assertTrue(false);}catch(IllegalArgumentException e){assertTrue(true);}
		
		unit.terminate();
		
		assertFalse(unit.canHaveAsWorld(world2));
		assertFalse(unit.canHaveAsWorld(world));
		assertTrue(unit.canHaveAsWorld(null));
		try{unit.setWorld(world); assertTrue(false);}catch(IllegalArgumentException e){assertTrue(true);}
		try{unit.setWorld(world2); assertTrue(false);}catch(IllegalArgumentException e){assertTrue(true);}
		try{unit.setWorld(null); assertTrue(true);}catch(IllegalArgumentException e){assertTrue(false);}
		
		for (int x=0; x< World.MAX_UNITS_WORLD; x++){
			world2.spawnUnit(false);
		}
		
		assertTrue(world2.getNbUnits() == World.MAX_UNITS_WORLD);
		try{unit2.setWorld(world2); assertTrue(false);}catch(IllegalArgumentException e){assertTrue(true);}
	}
	
	@Test
	public void canHaveAsTargetCoordinatesTest() throws Exception {
		// True case
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMin = new Unit(world, "UnitMin", new int[]{0, 0, 1},25,25,25, 25);
		
		double[] target = {unitMin.getPosition().getXCoordinate(),unitMin.getPosition().getYCoordinate(),unitMin.getPosition().getZCoordinate()};
		assertTrue(unitMin.canHaveAsTargetCoordinates(target));
		assertTrue(unitMin.canHaveAsTargetCoordinates(null));
		
		// False case
		double[] target1 = {500,unitMin.getPosition().getYCoordinate(),unitMin.getPosition().getZCoordinate()};
		double[] target2 = {unitMin.getPosition().getXCoordinate(),-1,unitMin.getPosition().getZCoordinate()};
		assertFalse(unitMin.canHaveAsTargetCoordinates(target1));
		assertFalse(unitMin.canHaveAsTargetCoordinates(target2));
	}
	
	@Test
	public void canHaveAsInitialCoordinatesTest() throws Exception {
		// True case
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMin = new Unit(world, "UnitMin", new int[]{0, 0, 1},25,25,25, 25);
		
		double[] target = {unitMin.getPosition().getXCoordinate(),unitMin.getPosition().getYCoordinate(),unitMin.getPosition().getZCoordinate()};
		assertTrue(unitMin.canHaveAsInitialCoordinates(target));
		
		// False case
		double[] target1 = {500,unitMin.getPosition().getYCoordinate(),unitMin.getPosition().getZCoordinate()};
		double[] target2 = {unitMin.getPosition().getXCoordinate(),-1,unitMin.getPosition().getZCoordinate()};
		assertFalse(unitMin.canHaveAsInitialCoordinates(target1));
		assertFalse(unitMin.canHaveAsInitialCoordinates(target2));
	}
	
	@Test
	public void getWalkSpeedTest() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMin = new Unit(world, "UnitMin", new int[]{0, 0, 1},25,25,25, 25);
		
		double[] target = {unitMin.getPosition().getXCoordinate(),unitMin.getPosition().getYCoordinate(),unitMin.getPosition().getZCoordinate()+1};
		assertTrue(Util.fuzzyEquals(unitMin.getWalkSpeed(target),0.75));
		
	}
	
	@Test
	public void getVelocityTest() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitRandom = new Unit(world, "RandomUnit");
		
		// Random case
		// Execute multiple times because of randomness.
		for (int count = 0; count < 10; count++) {
			double[] target = Position.getCubeCenter(
					unitRandom.getWorld().getRandomUnitCoordinatesInRange(unitRandom.getCoordinates(), 2));
			double speed = unitRandom.getWalkSpeed(target);
			
			assertTrue(Position.fuzzyEquals(unitRandom.getVelocity(target), 
					Position.getVelocity(unitRandom.getCoordinates(), target, speed)));
		}
		
		// No distance case
		double[] target = unitRandom.getCoordinates() ;
		double speed = unitRandom.getWalkSpeed(target);
		assertTrue(Position.fuzzyEquals(unitRandom.getVelocity(target), 
				Position.getVelocity(unitRandom.getCoordinates(), target, speed)));
	}
	
	@Test
	public void isAdjacentToTest() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMin = new Unit(world, "UnitMin", new int[]{0, 0, 1},25,25,25, 25);
		
		assertTrue(unitMin.isAdjacentTo(new int[]{0,0,1}));
	}
	
	@Test
	public void isValidIsSprintingTest(){
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "UnitMin", new int[]{0, 0, 1}, 25, 25, 25, 25);
		
		assertTrue(unit.isValidIsSprinting(false));
		assertTrue(unit.isValidIsSprinting(true));
		unit.terminate();
		assertFalse(unit.isValidIsSprinting(true));
	}
	
	@Test
	public void setIsSprintingTest(){
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "UnitMin", new int[]{0, 0, 1}, 25, 25, 25, 25);
		
		try{unit.setIsSprinting(false); assertTrue( ! unit.getIsSprinting());}catch(IllegalStateException e){assertTrue(false);}
		try{unit.setIsSprinting(true); assertTrue(unit.getIsSprinting());}catch(IllegalStateException e){assertTrue(false);}
		unit.terminate();
		try{unit.setIsSprinting(true); assertTrue(false);}catch(IllegalStateException e){assertTrue(true);}
	}
	
	@Test
	public void startSprinting_stopSprintingTest() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "UnitMin", new int[]{0, 0, 1},25,25,25, 25);
		Unit unit2 = new Unit(world, "UnitMin", new int[]{19, 39, 9},25,25,25, 25);
		
		unit.startSprinting();
		assertTrue(unit.getIsSprinting());
		unit.stopSprinting();
		assertFalse(unit.getIsSprinting());
		unit.terminate();
		unit.startSprinting();
		assertFalse(unit.getIsSprinting());
		
		unit2.startSprinting();
		assertFalse(unit2.getIsSprinting());
	}
	
	@Test
	public void canMoveToTest() {
		World world = new World(terrain("20x40x10IsolatedAirPosition"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "Unit", new int[]{0, 0, 1},25,25,25, 25);
		Unit unit2 = new Unit(world, "UnitTwo", new int[]{15, 3, 2},25,25,25, 25);
		
		assertTrue(unit.canMoveTo(new int[]{0, 0, 1}));
		assertFalse(unit.canMoveTo(new int[]{0, 0, 0}));
		assertFalse(unit.canMoveTo(new int[]{19, 39, 9}));
		assertFalse(unit.canMoveTo(new int[]{15, 3, 2}));
		assertFalse(unit2.canMoveTo(new int[]{0, 0, 1}));
		assertFalse(unit2.canMoveTo(new int[]{0, 0, 0}));
		assertFalse(unit2.canMoveTo(new int[]{19, 39, 9}));
		assertFalse(unit2.canMoveTo(new int[]{15, 3, 2}));
	}
	
	@Test
	public void moveToAdjacentTest() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "UnitMin", new int[]{0, 0, 1},25,25,25, 25);
		Unit unit2 = new Unit(world, "UnitMin", new int[]{0, 0, 1},25,25,25, 25);
		
		unit.moveToAdjacent(0, 0, 1);
		unit.moveToAdjacent(0, 0, 1);
		assertTrue(unit.getCurrentActivity()== Activity.MOVE);
		assertFalse(unit.getDefaultBehaviorEnabled());
		assertTrue(UnitPosition.equals(unit.getTargetCoordinates(), new double[]{0.5, 0.5, 2.5}));
		assertTrue(UnitPosition.equals(unit.getInitialCoordinates(), new double[]{0.5, 0.5, 1.5}));
		advanceTimeFor(world, 10, 0.2);
		assertTrue(unit.getCurrentActivity() == Activity.NOTHING);
		assertEquals(unit.getTargetCoordinates(), null);
		assertEquals(unit.getInitialCoordinates(), null);
		
		unit.moveToAdjacent(0, 0, 0);
		assertTrue(unit.getCurrentActivity() == Activity.NOTHING);
		unit2.moveToAdjacent(0, 0, -1);
		assertTrue(unit2.getCurrentActivity() == Activity.NOTHING);
	}
	
	@Test
	public void canDoMoveToAdjacentTest() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "Unit", new int[]{0, 0, 1},25,25,25, 25);
		Unit unit2 = new Unit(world, "UnitTwo", new int[]{19, 39, 9},25,25,25, 25);
		
		assertTrue(unit.canDoMoveToAdjacent());
		assertFalse(unit2.canDoMoveToAdjacent());
	}
	
	// TODO tests for not interrupting moveToAdjacent. (not implemented in unit yet.)
	@Test
	public void moveToTest() {
		World world = new World(terrain("20x40x10IsolatedAirPosition"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "Unit", new int[]{0, 0, 1},25,25,25, 25);
		Unit unit2 = new Unit(world, "UnitTwo", new int[]{15, 3, 2},25,25,25, 25);
		
		unit.moveTo(new int[]{4, 5, 2});
		assertTrue(unit.moveToPath.size() == 5);
		assertTrue(unit.isMoving());
		assertTrue(unit.getProgress() == 0);
		assertTrue(UnitPosition.equals(unit.moveToPath.get(unit.moveToPath.size()-1), new int[]{4, 5, 2}));
		assertTrue(UnitPosition.equals(unit.getInitialCoordinates(), unit.getCoordinates()));
		
		advanceTimeFor(world, 20, 0.2);
		assertTrue(unit.moveToPath.size() == 0);
		assertFalse(unit.isMoving());
		
		unit.moveTo(new int[]{-1, 0, 0});
		assertFalse(unit.isMoving());
		unit.moveTo(new int[]{0, 0, 0});
		assertFalse(unit.isMoving());
		
		unit2.moveTo(new int[]{0, 0, 1});
		assertFalse(unit2.isMoving());
		unit2.moveTo(new int[]{-1, 0, 0});
		assertFalse(unit2.isMoving());
		unit2.moveTo(new int[]{0, 0, 0});
		assertFalse(unit2.isMoving());
		unit2.moveTo(new int[]{19, 39, 9});
		assertFalse(unit2.isMoving());
	}
	
	@Test
	public void cancelMoveToTest() {
		World world = new World(terrain("20x40x10IsolatedAirPosition"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "Unit", new int[]{0, 0, 1},25,25,25, 25);
		
		unit.moveTo(new int[]{4, 5, 2});
		assertTrue(unit.moveToPath.size() == 5);
		assertTrue(unit.isMoving());
		assertTrue(unit.getProgress() == 0);
		assertTrue(UnitPosition.equals(unit.moveToPath.get(unit.moveToPath.size()-1), new int[]{4, 5, 2}));
		assertTrue(UnitPosition.equals(unit.getInitialCoordinates(), unit.getCoordinates()));
		
		advanceTimeFor(world, 0.25, 0.0078125);
		
		unit.cancelMoveTo();
		assertTrue(unit.moveToPath.size() == 0);
		assertFalse(unit.isMoving());
		assertTrue(unit.getTargetCoordinates() == null);
		assertTrue(unit.getInitialCoordinates() == null);
		assertTrue(UnitPosition.equals(unit.getCoordinates(), new double[]{0.5, 0.5, 1.5}));
	}
	
	@Test
	public void hasSolidNeighboursTest() {
		World world = new World(terrain("20x40x10IsolatedAirPosition"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "Unit", new int[]{0, 0, 1},25,25,25, 25);
		
		assertTrue(unit.hasSolidNeighbours(new int[]{0, 0, 1}));
		assertFalse(unit.hasSolidNeighbours(new int[]{19, 39, 9}));
		assertTrue(unit.hasSolidNeighbours(new int[]{0, 0, 0}));
		try{unit.hasSolidNeighbours(new int[]{-1, 0, 0}); assertTrue(false);}catch(IllegalArgumentException e){assertTrue(true);}
	}
	
	@Test
	public void hasPassableNeighboursTest() {
		World world = new World(terrain("20x40x10IsolatedAirPosition"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "Unit", new int[]{0, 0, 1},25,25,25, 25);
		
		assertTrue(unit.hasPassableNeighbours(new int[]{0, 0, 1}));
		assertTrue(unit.hasPassableNeighbours(new int[]{19, 39, 9}));
		assertFalse(unit.hasPassableNeighbours(new int[]{5, 5, 0}));
		try{unit.hasPassableNeighbours(new int[]{-1, 0, 0}); assertTrue(false);}catch(IllegalArgumentException e){assertTrue(true);}
	}
	
	@Test
	public void isValidOrientationTest(){
		assertFalse(Unit.isValidOrientation(-4));
		assertTrue(Unit.isValidOrientation(Math.PI));
		assertTrue(Unit.isValidOrientation(-Math.PI));
		assertTrue(Unit.isValidOrientation(0));
		assertFalse(Unit.isValidOrientation(4));
	}
	
	@Test
	public void setOrientationTest(){
		World world = new World(terrain("20x40x10IsolatedAirPosition"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "Unit", new int[]{0, 0, 1},25,25,25, 25);
		
		unit.setOrientation(-1,-1);
		assertTrue(unit.getOrientation() == Math.atan2(-1, -1));
		unit.setOrientation(-1, 1);
		assertEquals(unit.getOrientation(), Math.atan2(1, -1), 0.00005);
		unit.setOrientation(1, 1);
		assertEquals(unit.getOrientation(), Math.atan2(1, 1), 0.00005);
		unit.setOrientation(1,  -1);
		assertEquals(unit.getOrientation(), Math.atan2(-1, 1), 0.00005);
		unit.setOrientation(0, 0);
		assertEquals(unit.getOrientation(), Math.atan2(0, 0), 0.00005);
	}
	
	@Test
	public void hasTaskTest(){
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "Unit", new int[]{0, 0, 1},25,25,25, 25);
		
		assertFalse(unit.hasTask());
		
		Task task = new Task("digTunnel", -100, new FollowStatement<UnitExpression>(new AnyExpression(new SourceLocation(0, 0)), new SourceLocation(0, 0)));
		task.setUnit(unit);
		unit.setTask(task);
		
		assertTrue(unit.hasTask());
	}
	
	@Test
	public void hasProperTaskTest(){
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "Unit", new int[]{0, 0, 1},25,25,25, 25);
		
		assertTrue(unit.hasProperTask());
		
		Task task = new Task("digTunnel", -100, new FollowStatement<UnitExpression>(new AnyExpression(new SourceLocation(0, 0)), new SourceLocation(0, 0)));
		task.setUnit(unit);
		unit.setTask(task);
		
		assertTrue(unit.hasProperTask());
		
		task.terminate();
		
		assertTrue(unit.hasProperTask());
		assertTrue(task.getUnit() == null);
		assertTrue(task.hasProperUnit());
	}
	
	@Test
	public void setTaskTest(){
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "Unit", new int[]{0, 0, 1},25,25,25, 25);
		
		assertFalse(unit.hasTask());
		
		Task task = new Task("digTunnel", -100, new FollowStatement<UnitExpression>(new AnyExpression(new SourceLocation(0, 0)), new SourceLocation(0, 0)));
		try{unit.setTask(task); assertTrue(false);} catch(IllegalArgumentException e){assertTrue(true);}
		task.setUnit(unit);
		unit.setTask(task);
		
		assertTrue(unit.hasTask());
		
		try{unit.setTask(null); assertTrue(false);} catch(IllegalArgumentException e){assertTrue(true);}
		
		task.terminate();
		
		assertFalse(unit.hasTask());
	}
	
	@Test
	public void SetCurrentActivityTest(){
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "Unit", new int[]{0, 0, 1},25,25,25, 25);
		
		try{unit.setCurrentActivity(null); assertTrue(false);} catch(NullPointerException e){assertTrue(true);}
		unit.setCurrentActivity(Activity.MOVE);
		assertTrue(unit.getCurrentActivity() == Activity.MOVE);
		
		unit.terminate();
		
		try{unit.setCurrentActivity(Activity.MOVE); assertTrue(false);} catch(IllegalStateException e){assertTrue(true);}
		try{unit.setCurrentActivity(null); assertTrue(false);} catch(NullPointerException e){assertTrue(true);}
	}
	
	@Test
	public void isValidProgressTest(){
		assertFalse(Unit.isValidProgress(-1));
		assertTrue(Unit.isValidProgress(0));
		assertTrue(Unit.isValidProgress(1));
		assertFalse(Unit.isValidProgress(Double.NaN));
	}
	
	@Test
	public void getWorkDurationTest(){
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "Unit", new int[]{0, 0, 1},25,25,25, 25);
		
		assertEquals(500.0/25, unit.getWorkDuration(), 0.00005);
	}
	
	@Test
	public void isWorkingTest(){
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "Unit", new int[]{0, 0, 1},25,25,25, 25);
		
		assertFalse(unit.isWorking());
		
		unit.workAt(new int[]{0, 0, 0});
		
		assertTrue(unit.isWorking());
		
		advanceTimeFor(world, unit.getWorkDuration()+0.5, 0.015625);
		
		assertFalse(unit.isWorking());
		
		unit.workAt(new int[]{0, 0, 0});
		
		assertTrue(unit.isWorking());
		
		unit.terminate();
		
		assertFalse(unit.isWorking());
	}
	
	@Test
	public void WorkAtTest(){
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "Unit", new int[]{0, 0, 1},25,25,25, 25);
		new Log(world, new int[]{0, 0, 1});
		
		unit.startDefaultBehavior();
		unit.workAt(unit.getCubeCoordinates());
		unit.workAt(unit.getCubeCoordinates());
		
		assertTrue(unit.isWorking());
		assertTrue(unit.getProgress() == 0);
		assertFalse(unit.getDefaultBehaviorEnabled());
		assertTrue(unit.getOrientation() == 0);
		assertTrue(unit.getPreviousActivity() == Activity.WORK);
		
		advanceTimeFor(unit, unit.getWorkDuration(), 0.015625);
		
		assertFalse(unit.isWorking());
		assertTrue(unit.hasItem());
		
		unit.workAt(new int[]{0, 0, 0});
		
		assertFalse(unit.isWorking());
		
		unit.moveToAdjacent(0, 0, 1);
		
		
		assertTrue(unit.isMoving());
		unit.workAt(new int[]{0, 0, 2});
		assertTrue(unit.isMoving());
		
		
		World world2 = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMin = new Unit(world2, "UnitMin", new int[]{0, 0, 1},25,25,25, 25);
		
		unitMin.workAt(new int[] {0,0,0});
		assertTrue(unitMin.isWorking());
		assertTrue(unitMin.getProgress() == 0);
		advanceTimeFor(world2, unitMin.getWorkDuration(), 0.015625);
		assertFalse(unitMin.isWorking());
	}
	
	@Test
	public void RestTest(){
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "Unit", new int[]{0, 0, 1},25,25,25, 25);
		
		unit.startDefaultBehavior();
		unit.rest();
		unit.rest();
		
		assertTrue(unit.isResting());
		assertTrue(unit.getProgress() == 0);
		assertFalse(unit.getDefaultBehaviorEnabled());
		assertTrue(unit.getPreviousActivity() == Activity.NOTHING);
		
		advanceTimeFor(unit, 10, 0.015625);
		
		assertFalse(unit.isResting());
		
		unit.moveToAdjacent(0, 0, 1);
		
		assertTrue(unit.isMoving());
		
		unit.rest();
		
		assertTrue(unit.isMoving());

		unit.terminate();
		assertTrue(unit.getCurrentActivity() == Activity.NOTHING);
	}
	
	@Test
	public void isRestingTest(){
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "Unit", new int[]{0, 0, 1},25,25,25, 25);
		
		assertFalse(unit.isResting());
		
		unit.rest();
		
		assertTrue(unit.isResting());
	}
	
	@Test
	public void isAttackingTest(){
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "Unit", new int[]{0, 0, 2},25,25,25, 25);
		Unit unit2 = new Unit(world, "UnitTwo", new int[]{0, 1, 2},25,25,25, 25);
		
		assertFalse(unit.isAttacking());
		
		unit.attack(unit2);
		
		assertTrue(unit.isAttacking());
	}
	
	@Test
	public void AttackTest(){
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "Unit", new int[]{0, 0, 2},25,25,25, 25);
		Unit unit2 = new Unit(world, "UnitTwo", new int[]{0, 1, 2},25,25,25, 25);
		
		assertTrue(unit.getCurrentActivity() == Activity.NOTHING);
		assertTrue(unit.getUnitUnderAttack() ==null);
		
		unit.attack(unit2);
		
		assertTrue(unit.isAttacking());
		assertTrue(unit.getUnitUnderAttack() == unit2);
		assertTrue(unit.getProgress() == 0);
	}
	
	@Test
	public void FightTest(){
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "Unit", new int[]{0, 0, 2},25,25,25, 25);
		Unit unit2 = new Unit(world, "UnitTwo", new int[]{0, 1, 2},25,25,25, 25);
		Unit unit3 = new Unit(world, "UnitThree", new int[]{10, 35, 6},25,25,25, 25);
		
		assertTrue(unit.getCurrentActivity() == Activity.NOTHING);
		assertTrue(unit.getUnitUnderAttack() ==null);
		
		unit.fight(null);
		assertTrue(unit.getCurrentActivity() == Activity.NOTHING);
		unit.fight(unit3);
		assertTrue(unit.getCurrentActivity() == Activity.NOTHING);

		unit.fight(unit2);
		
		assertFalse(unit.getDefaultBehaviorEnabled());
		assertTrue(unit.isAttacking());
		assertTrue(unit.getUnitUnderAttack() == unit2);
		assertTrue(unit.getProgress() == 0);
		
		Faction faction = new Faction(world);
		Unit unit4 = new Unit(world, faction,  "UnitThree", new int[]{10, 35, 6},25,25,25, 25);
		Unit unit5 = new Unit(world, faction, "UnitThree", new int[]{10, 35, 6},25,25,25, 25);
		unit4.fight(unit5);
		assertTrue(unit4.getCurrentActivity() == Activity.NOTHING);
	}
	
	@Test
	public void hasItem_hasProperItemTest(){
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "Unit", new int[]{0, 0, 2}, 25, 25, 25, 25);
		new Log(world, new int[]{0, 0, 1});
		
		assertFalse(unit.hasItem());
		assertTrue(unit.hasProperItem());
		
		unit.workAt(new int[]{0, 0, 1});
		
		advanceTimeFor(world, unit.getWorkDuration()+0.5, 0.015625);
		assertTrue(unit.hasItem());
		assertTrue(unit.hasProperItem());
		
		unit.terminate();
		
		assertFalse(unit.hasItem());
		assertTrue(unit.hasProperItem());
	}
	
	@Test
	public void startDefaultBehavior_stopDefaultBehaviorTest(){
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "Unit", new int[]{0, 0, 2}, 25, 25, 25, 25);
		
		assertFalse(unit.getDefaultBehaviorEnabled());
		assertTrue(unit.getCurrentActivity() == Activity.NOTHING);
		assertFalse(unit.hasTask());
		
		unit.startDefaultBehavior();
		
		assertTrue(unit.getDefaultBehaviorEnabled());
		
		unit.stopDefaultBehavior();
		
		assertFalse(unit.getDefaultBehaviorEnabled());
		assertTrue(unit.getCurrentActivity() == Activity.NOTHING);
		assertFalse(unit.hasTask());
	}
	
	@Test
	public void AdvanceTimeFightTest() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "Unit", new int[]{0, 0, 2},100, 100, 100, 100);
		Unit unit2 = new Unit(world, "Unit two", new int[]{0, 1, 2},25,25,25, 25);
		Unit unit3 = new Unit(world, "Unit three", new int[]{0, 1, 2},25,25,25, 25);
		
		int nbSuccesfulAttacks = 0;
		int previousHealth = unit2.getMaxPoints();
		for(int x=0; x<10; x++ ){
			unit.fight(unit2);
			advanceTimeFor(world, 1.125, 0.0078125);
			if (unit2.getCurrentHealth() < previousHealth){
				nbSuccesfulAttacks++;
			}
		}
		assertTrue(nbSuccesfulAttacks > 0);
		assertTrue(unit2.isTerminated());
			
		nbSuccesfulAttacks = 0;
		previousHealth = (int)unit.getCurrentHealth();
		for(int x=0; x<10; x++ ){
			unit3.fight(unit);
			advanceTimeFor(world, 1.125, 0.0078125);
			
			if (unit.getCurrentHealth() < previousHealth){
				System.out.println("previous health: "+previousHealth);
				System.out.println(unit.getCurrentHealth());
				nbSuccesfulAttacks++;
			}
		}
		assertTrue(nbSuccesfulAttacks == 0);
		assertFalse(unit.isTerminated());
		
	}
	
	@Test
	public void AdvanceTimeFallTest(){
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "Unit", new int[]{19, 39, 9},100, 100, 100, 100);
		
		advanceTimeFor(world, 10, 0.0078125);
		
		assertFalse(unit.getCurrentHealth() == unit.getMaxPoints());
		assertFalse(UnitPosition.equals(unit.getCubeCoordinates(), new int[]{19, 39, 9}));
		assertTrue(unit.getExperience() == 0);
	}
	
	@Test
	public void AdvanceTimeDefaultTest(){
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "Unit", new int[]{0, 0, 2},100, 100, 100, 100);
		unit.startDefaultBehavior();
		assertTrue(unit.getDefaultBehaviorEnabled());
		advanceTimeFor(world, 10, 0.0078125);
		assertTrue(unit.getDefaultBehaviorEnabled());
		
		assertFalse(unit.getCurrentActivity() == Activity.NOTHING );
	}
	
	/**
	 * Helper method to advance time for the given world by some time.
	 * 
	 * @param time
	 *            The time, in seconds, to advance.
	 * @param step
	 *            The step size, in seconds, by which to advance.
	 */
	private static void advanceTimeFor(Unit unit, double time, double step) {
		int n = (int) (time / step);
		for (int i = 0; i < n; i++)
			unit.advanceTime(step);
		unit.advanceTime(time - n * step);
	}
	
	/**
	 * Helper method to advance time for the given world by some time.
	 * 
	 * @param time
	 *            The time, in seconds, to advance.
	 * @param step
	 *            The step size, in seconds, by which to advance.
	 */
	private static void advanceTimeFor(World world, double time, double step) {
		int n = (int) (time / step);
		for (int i = 0; i < n; i++)
			world.advanceTime(step);
		world.advanceTime(time - n * step);
	}
	
	/**
	 * Helper method to load the terrain for a new world. Only existing terrains should be asked for.
	 * @param name
	 *				The name of the terrain.
	 */
	private static int[][][] terrain(String name) {
		GameMap map;
		final String LEVEL_FILE_EXTENSION = ".wrld";
		final String LEVELS_PATH = "resources/";
		try {
			map = new GameMapReader().readFromResource(LEVELS_PATH + name + LEVEL_FILE_EXTENSION);
			
			int[][][] types = new int[map.getNbTilesX()][map.getNbTilesY()][map.getNbTilesZ()];

			for (int x = 0; x < types.length; x++) {
				for (int y = 0; y < types[x].length; y++) {
					for (int z = 0; z < types[x][y].length; z++) {
						CubeType type = map.getTypeAt(x, y, z);
						types[x][y][z] = type.getByteValue();
					}
				}
			}
			return types;
		} 
		catch (IOException e) {e.printStackTrace();	}
		
		return null;
	}
	
}
