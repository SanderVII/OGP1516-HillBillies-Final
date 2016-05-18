package hillbillies.tests;

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import hillbillies.model.Activity;
import hillbillies.model.Faction;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part2.internal.map.CubeType;
import hillbillies.part2.internal.map.GameMap;
import hillbillies.part2.internal.map.GameMapReader;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.positions.Position;
import hillbillies.positions.UnitPosition;
import ogp.framework.util.Util;

/**
 * @author Sander Mergan, Thomas Vranken
 * @version 2.4
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
		assertTrue(unit2.canHaveAsCoordinates(unit2.getPosition().getCoordinates()));
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
	public void canHaveAsMinBaseStat(){
		assertTrue(Unit.canHaveAsMinBaseStat(1));
		assertTrue(Unit.canHaveAsMinBaseStat(100));
		assertFalse(Unit.canHaveAsMinBaseStat(0));
	}
	
	@Test 
	public void canHaveAsMaxBaseStat(){
		assertTrue(Unit.canHaveAsMinBaseStat(1));
		assertTrue(Unit.canHaveAsMinBaseStat(100));
		assertFalse(Unit.canHaveAsMinBaseStat(0));
	}
	
	@Test
	public void getMinWeightTest() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitLowWeight = new Unit(world, "UnitLowWeight", new int[] {world.getMaximumXValue()-world.getMaximumXValue()/2-3, 
																														world.getMaximumYValue()-world.getMaximumYValue()+5, 
																														world.getMaximumZValue()-5}, 
																														25,60,50,40);
		
		assertEquals(55,unitLowWeight.getMinWeight());
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
	public void canHaveAsAgility_TrueCase() {
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
	public void canHaveAsCurrentHealth() {
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
	public void canHaveAsName() {
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
	public void getCubePosition() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMin = new Unit(world, "UnitMin", new int[]{0, 0, 1},25,25,25, 25);
		
		assertTrue(unitMin.getCubeCoordinates()[0] == 0);
		assertTrue(unitMin.getCubeCoordinates()[1] == 0);
		assertTrue(unitMin.getCubeCoordinates()[2] == 1);
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
	public void canHAveAsInitialCoordinatesTest() throws Exception {
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
					unitRandom.getWorld().getRandomUnitCoordinatesInRange(unitRandom.getPosition().getCoordinates(), 2));
			double speed = unitRandom.getWalkSpeed(target);
			
			assertTrue(Position.fuzzyEquals(unitRandom.getVelocity(target), 
					Position.getVelocity(unitRandom.getPosition().getCoordinates(), target, speed)));
		}
		
		// No distance case
		double[] target = unitRandom.getPosition().getCoordinates() ;
		double speed = unitRandom.getWalkSpeed(target);
		assertTrue(Position.fuzzyEquals(unitRandom.getVelocity(target), 
				Position.getVelocity(unitRandom.getPosition().getCoordinates(), target, speed)));
	}
	
	@Test
	public void isAdjacentToTest() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMin = new Unit(world, "UnitMin", new int[]{0, 0, 1},25,25,25, 25);
		
		assertTrue(unitMin.isAdjacentTo(new int[]{0,0,1}));
	}
	
	@Test
	public void moveToAdjacentTest() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMin = new Unit(world, "UnitMin", new int[]{0, 0, 1},25,25,25, 25);
		
		unitMin.moveToAdjacent(0, 0, 1);
		assertTrue(unitMin.getCurrentActivity()== Activity.MOVE);
		advanceTimeFor(world, 10, 0.2);
		assertTrue(unitMin.getCurrentActivity() == Activity.NOTHING);
	}
	
	@Test
	public void advanceTimeWorkAtTest_CurrentPosition(){
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMin = new Unit(world, "UnitMin", new int[]{0, 0, 1},25,25,25, 25);
		
		unitMin.workAt(unitMin.getCubeCoordinates());
		double deltaT = 0.1;
		assertTrue(unitMin.isWorking());
		assertTrue(unitMin.getProgress() == 0);
		
		advanceTimeFor(unitMin, unitMin.getWorkDuration(), deltaT);
		
		assertFalse(unitMin.isWorking());
		
	}
	
	@Test
	public void advanceTimeWorkAtTest_Origin(){
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMin = new Unit(world, "UnitMin", new int[]{0, 0, 1},25,25,25, 25);
		
		unitMin.workAt(new int[] {0,0,0});
		double deltaT = 0.2;
		assertTrue(unitMin.isWorking());
		assertTrue(unitMin.getProgress() == 0);
		while  (unitMin.getProgress() <  unitMin.getWorkDuration() - deltaT){
			unitMin.advanceTime(deltaT);
			assertTrue(unitMin.isWorking());
		}
		unitMin.advanceTime(deltaT);
		assertFalse(unitMin.isWorking());
		
	}
	
	@Test
	public void AdvanceTimeTest_DeltaT() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMin = new Unit(world, "UnitMin", new int[]{0, 0, 1},25,25,25, 25);
		
		assertTrue(unitMin.getGametime() == 0);
		
		unitMin.advanceTime(0.2);
		assertTrue(Util.fuzzyEquals(unitMin.getGametime(), 0.2));
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
