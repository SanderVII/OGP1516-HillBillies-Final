package hillbillies.tests;

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import hillbillies.model.Activity;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part2.internal.map.CubeType;
import hillbillies.part2.internal.map.GameMap;
import hillbillies.part2.internal.map.GameMapReader;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.positions.Position;
import ogp.framework.util.Util;

/**
 * @author Sander Mergan, Thomas Vranken
 * @version 2.2
 */
public class UnitTest {

	private int[] origin = new int[] {0,0,0};
	private final int TIMES_TEST = 20;
	
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
	public final void advanceTimeWorkAt_CurrentPosition(){
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMin = new Unit(world, "UnitMin", new int[]{0, 0, 1},25,25,25, 25);
		world.addEntity(unitMin);
		
		unitMin.workAt(unitMin.getCubeCoordinates());
		double deltaT = 0.1;
		assertTrue(unitMin.isWorking());
		assertTrue(unitMin.getProgress() == 0);
		
		advanceTimeFor(unitMin, unitMin.getWorkDuration(), deltaT);
		
		assertFalse(unitMin.isWorking());
		
	}
	
	@Test
	public final void advanceTimeWorkAt_Origin(){
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMin = new Unit(world, "UnitMin", new int[]{0, 0, 1},25,25,25, 25);
		world.addEntity(unitMin);
		
		unitMin.workAt(origin);
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
	public final void AdvanceTime_DeltaT() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMin = new Unit(world, "UnitMin", new int[]{0, 0, 1},25,25,25, 25);
		world.addEntity(unitMin);
		
		assertTrue(unitMin.getGametime() == 0);
		
		unitMin.advanceTime(0.2);
		assertTrue(Util.fuzzyEquals(unitMin.getGametime(), 0.2));
	}
	
	@Test
	public final void extendedConstructor_NormalCase() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMin = new Unit(world, "UnitMin", new int[]{0, 0, 1},25,25,25, 25);
		world.addEntity(unitMin);
		
		assertEquals("UnitMin", unitMin.getName());
		assertEquals(0.5,unitMin.getPosition().getXCoordinate(),0.0005); // omdat bij van het type double zijn moet je een absolute fout meegeven.
		assertEquals(0.5,unitMin.getPosition().getYCoordinate(),0.0005);
		assertEquals(1.5,unitMin.getPosition().getZCoordinate(),0.0005);
		assertEquals(25, unitMin.getMinWeight());
		assertEquals(25, unitMin.getWeight());
		assertEquals(25, unitMin.getStrength());
		assertEquals(25, unitMin.getAgility());
		assertEquals(25, unitMin.getToughness());
	}
	
	@Test
	public final void extendedConstructor_LowWeight() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitLowWeight = new Unit(world, "UnitLowWeight", new int[] {world.getMaximumXValue()-world.getMaximumXValue()/2-3, 
																														world.getMaximumYValue()-world.getMaximumYValue()+5, 
																														world.getMaximumZValue()-5}, 
																														25,60,50,40);
		world.addEntity(unitLowWeight);
		
		assertEquals("UnitLowWeight", unitLowWeight.getName());
		assertEquals(unitLowWeight.getWorld().getMaximumXValue()-unitLowWeight.getWorld().getMaximumXValue()/2-3 +0.5, unitLowWeight.getPosition().getXCoordinate(),0.0005); // omdat bij van het type double zijn moet je een absolute fout meegeven.
		assertEquals(unitLowWeight.getWorld().getMaximumYValue()-unitLowWeight.getWorld().getMaximumYValue()+5 +0.5, unitLowWeight.getPosition().getYCoordinate(),0.0005);
		assertEquals(unitLowWeight.getWorld().getMaximumZValue()-5 + 0.5,unitLowWeight.getPosition().getZCoordinate(),0.0005);
		assertEquals(55, unitLowWeight.getMinWeight());
		assertEquals(55, unitLowWeight.getWeight());
		assertEquals(60, unitLowWeight.getStrength());
		assertEquals(50, unitLowWeight.getAgility());
		assertEquals(40, unitLowWeight.getToughness());
	}

	@Test
	public final void getMinWeight_SingleCase() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitLowWeight = new Unit(world, "UnitLowWeight", new int[] {world.getMaximumXValue()-world.getMaximumXValue()/2-3, 
																														world.getMaximumYValue()-world.getMaximumYValue()+5, 
																														world.getMaximumZValue()-5}, 
																														25,60,50,40);
		world.addEntity(unitLowWeight);
		
		assertEquals(55,unitLowWeight.getMinWeight());
	}
	
	@Test
	public final void canHaveAsWeight_TrueCase() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMin = new Unit(world, "UnitMin", new int[]{0, 0, 1},25,25,25, 25);
		world.addEntity(unitMin);
		
		assertTrue(unitMin.canHaveAsWeight(30));
	}
	
	@Test
	public final void canHaveAsWeight_BelowMinBaseStat() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMin = new Unit(world, "UnitMin", new int[]{0, 0, 1},25,25,25, 25);
		world.addEntity(unitMin);
		
		assertFalse(unitMin.canHaveAsWeight(-1));
	}
	
	@Test
	public final void canHaveAsWeight_AboveMaxBaseStat() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMax = new Unit(world, "UnitMax",new int[]{world.getMaximumXValue()-1, 
																							world.getMaximumYValue()-1, 
																							world.getMaximumZValue()-1},
																							100,100,100,100);
		world.addEntity(unitMax);
		
		assertFalse(unitMax.canHaveAsWeight(201));
	}
	
	@Test
	public final void canHaveAsWeight_LowWeight() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitLowWeight = new Unit(world, "UnitLowWeight", new int[] {world.getMaximumXValue()-world.getMaximumXValue()/2-3, 
																														world.getMaximumYValue()-world.getMaximumYValue()+5, 
																														world.getMaximumZValue()-5}, 
																														25,60,50,40);
		world.addEntity(unitLowWeight);
		
		assertFalse(unitLowWeight.canHaveAsWeight(25));
	}
	
	@Test
	public final void canHaveAsStrength_TrueCase() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitLowWeight = new Unit(world, "UnitLowWeight", new int[] {world.getMaximumXValue()-world.getMaximumXValue()/2-3, 
																														world.getMaximumYValue()-world.getMaximumYValue()+5, 
																														world.getMaximumZValue()-5}, 
																														25,60,50,40);
		world.addEntity(unitLowWeight);
		
		assertTrue(unitLowWeight.canHaveAsStrength(150));
	}
	
	@Test
	public final void canHaveAsStrength_BelowMinBaseStat() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMin = new Unit(world, "UnitMin", new int[]{0, 0, 1},25,25,25, 25);
		world.addEntity(unitMin);
		
		assertFalse(unitMin.canHaveAsWeight(-1));
	}
	
	@Test
	public final void canHaveAsStrength_AboveMaxBaseStat() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMax = new Unit(world, "UnitMax",new int[]{world.getMaximumXValue()-1, 
																							world.getMaximumYValue()-1, 
																							world.getMaximumZValue()-1},
																							100,100,100,100);
		world.addEntity(unitMax);
		
		assertFalse(unitMax.canHaveAsWeight(201));
	}
	
	@Test
	public final void canHaveAsAgility_TrueCase() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitLowWeight = new Unit(world, "UnitLowWeight", new int[] {world.getMaximumXValue()-world.getMaximumXValue()/2-3, 
																														world.getMaximumYValue()-world.getMaximumYValue()+5, 
																														world.getMaximumZValue()-5}, 
																														25,60,50,40);
		world.addEntity(unitLowWeight);
		
		assertTrue(unitLowWeight.canHaveAsAgility(150));
	}
	
	@Test
	public final void canHaveAsAgility_BelowMinBaseStat() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMin = new Unit(world, "UnitMin", new int[]{0, 0, 1},25,25,25, 25);
		world.addEntity(unitMin);
		
		assertFalse(unitMin.canHaveAsAgility(-1));
	}
	
	@Test
	public final void canHaveAsAgility_AboveMaxBaseStat() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMax = new Unit(world, "UnitMax",new int[]{world.getMaximumXValue()-1, 
																							world.getMaximumYValue()-1, 
																							world.getMaximumZValue()-1},
																							100,100,100,100);
		world.addEntity(unitMax);
		
		assertFalse(unitMax.canHaveAsAgility(201));
	}
	
	@Test
	public final void canHaveAsToughness_TrueCase() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitLowWeight = new Unit(world, "UnitLowWeight", new int[] {world.getMaximumXValue()-world.getMaximumXValue()/2-3, 
																														world.getMaximumYValue()-world.getMaximumYValue()+5, 
																														world.getMaximumZValue()-5}, 
																														25,60,50,40);
		world.addEntity(unitLowWeight);
		assertTrue(unitLowWeight.canHaveAsToughness(150));
	}
	
	@Test
	public final void canHaveAsToughness_BelowMinBaseStat() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMin = new Unit(world, "UnitMin", new int[]{0, 0, 1},25,25,25, 25);
		world.addEntity(unitMin);
		
		assertFalse(unitMin.canHaveAsToughness(-1));
	}
	
	@Test
	public final void canHaveAsToughness_AboveMaxBaseStat() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMax = new Unit(world, "UnitMax",new int[]{world.getMaximumXValue()-1, 
																							world.getMaximumYValue()-1, 
																							world.getMaximumZValue()-1},
																							100,100,100,100);
		world.addEntity(unitMax);
		
		assertFalse(unitMax.canHaveAsToughness(201));
	}
	
	@Test
	public final void canHaveAsCurrentHealth_TrueCase() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMax = new Unit(world, "UnitMax",new int[]{world.getMaximumXValue()-1, 
																							world.getMaximumYValue()-1, 
																							world.getMaximumZValue()-1},
																							100,100,100,100);
		world.addEntity(unitMax);
		
		assertTrue(unitMax.canHaveAsCurrentHealth(50));
	}
	
	@Test
	public final void canHaveAsCurrentHealth_Negative() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMax = new Unit(world, "UnitMax",new int[]{world.getMaximumXValue()-1, 
																							world.getMaximumYValue()-1, 
																							world.getMaximumZValue()-1},
																							100,100,100,100);
		world.addEntity(unitMax);
		
		assertFalse(unitMax.canHaveAsCurrentHealth(-50));
	}

	@Test
	public final void canHaveAsCurrentHealth_AboveMaxHealth() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMax = new Unit(world, "UnitMax",new int[]{world.getMaximumXValue()-1, 
																							world.getMaximumYValue()-1, 
																							world.getMaximumZValue()-1},
																							100,100,100,100);
		world.addEntity(unitMax);
		
		assertFalse(unitMax.canHaveAsCurrentHealth(250));
	}
	
	@Test
	public final void canHaveAsCurrentStamina_TrueCase() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMax = new Unit(world, "UnitMax",new int[]{world.getMaximumXValue()-1, 
																							world.getMaximumYValue()-1, 
																							world.getMaximumZValue()-1},
																							100,100,100,100);
		world.addEntity(unitMax);
		
		assertTrue(unitMax.canHaveAsCurrentStamina(50));
	}
	
	@Test
	public final void canHaveAsCurrentStamina_Negative() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMax = new Unit(world, "UnitMax",new int[]{world.getMaximumXValue()-1, 
																							world.getMaximumYValue()-1, 
																							world.getMaximumZValue()-1},
																							100,100,100,100);
		world.addEntity(unitMax);
		
		assertFalse(unitMax.canHaveAsCurrentStamina(-50));
	}

	@Test
	public final void canHaveAsCurrenStamina_AboveMaxStamina() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMax = new Unit(world, "UnitMax",new int[]{world.getMaximumXValue()-1, 
																							world.getMaximumYValue()-1, 
																							world.getMaximumZValue()-1},
																							100,100,100,100);
		world.addEntity(unitMax);
		
		assertFalse(unitMax.canHaveAsCurrentStamina(250));
	}

	@Test
	public final void canHaveAsName() {
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
	public final void getCoordinates() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMin = new Unit(world, "UnitMin", new int[]{0, 0, 1},25,25,25, 25);
		world.addEntity(unitMin);
		
		assertTrue(Util.fuzzyEquals(unitMin.getPosition().getXCoordinate(), 0.5));
		assertTrue(Util.fuzzyEquals(unitMin.getPosition().getYCoordinate(), 0.5));
		assertTrue(Util.fuzzyEquals(unitMin.getPosition().getZCoordinate(), 1.5));
	}
	
	@Test
	public final void getCubePosition() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMin = new Unit(world, "UnitMin", new int[]{0, 0, 1},25,25,25, 25);
		world.addEntity(unitMin);
		
		assertTrue(unitMin.getCubeCoordinates()[0] == 0);
		assertTrue(unitMin.getCubeCoordinates()[1] == 0);
		assertTrue(unitMin.getCubeCoordinates()[2] == 1);
	}
	
	@Test
	public final void getCubeCente() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMin = new Unit(world, "UnitMin", new int[]{0, 0, 1},25,25,25, 25);
		world.addEntity(unitMin);
		
		assertTrue(Position.getCubeCenter(unitMin.getCubeCoordinates())[0] == 0.5);
		assertTrue(Position.getCubeCenter(unitMin.getCubeCoordinates())[1] == 0.5);
		assertTrue(Position.getCubeCenter(unitMin.getCubeCoordinates())[2] == 1.5);
	}
	
	@Test
	public final void setPosition_RandomPosition() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitRandom = new Unit(world, "RandomUnit");
		world.addEntity(unitRandom);
		
		for (int count = 0; count < TIMES_TEST; count ++) {
			double[] position = Position.getCubeCenter(unitRandom.getWorld().getRandomAvailableUnitCoordinates());
			assertTrue(unitRandom.getPosition().canHaveAsCoordinates(position));
		}
	}
	
	@Test
	public final void canHaveAsTargetCoordinatesTest() throws Exception {
		// True case
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMin = new Unit(world, "UnitMin", new int[]{0, 0, 1},25,25,25, 25);
		world.addEntity(unitMin);
		
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
	public final void canHAveAsInitialCoordinatesTest() throws Exception {
		// True case
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMin = new Unit(world, "UnitMin", new int[]{0, 0, 1},25,25,25, 25);
		world.addEntity(unitMin);
		
		double[] target = {unitMin.getPosition().getXCoordinate(),unitMin.getPosition().getYCoordinate(),unitMin.getPosition().getZCoordinate()};
		assertTrue(unitMin.canHaveAsInitialCoordinates(target));
		
		// False case
		double[] target1 = {500,unitMin.getPosition().getYCoordinate(),unitMin.getPosition().getZCoordinate()};
		double[] target2 = {unitMin.getPosition().getXCoordinate(),-1,unitMin.getPosition().getZCoordinate()};
		assertFalse(unitMin.canHaveAsInitialCoordinates(target1));
		assertFalse(unitMin.canHaveAsInitialCoordinates(target2));
	}
	
	@Test
	public final void getDistanceTest() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMin = new Unit(world, "UnitMin", new int[]{0, 0, 1},25,25,25, 25);
		world.addEntity(unitMin);
		
		double[] target = {unitMin.getPosition().getXCoordinate()+1,unitMin.getPosition().getYCoordinate()+1,unitMin.getPosition().getZCoordinate()+1};
		assertTrue(Util.fuzzyEquals(unitMin.getDistance(target),Math.sqrt(3) ));
	}
	
	@Test
	public final void getWalkSpeedTest() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitMin = new Unit(world, "UnitMin", new int[]{0, 0, 1},25,25,25, 25);
		world.addEntity(unitMin);
		
		double[] target = {unitMin.getPosition().getXCoordinate(),unitMin.getPosition().getYCoordinate(),unitMin.getPosition().getZCoordinate()+1};
		assertTrue(Util.fuzzyEquals(unitMin.getWalkSpeed(target),0.75));
		
	}
	
	@Test
	public final void getVelocityTest() {
		World world = new World(terrain("20x40x10"), new DefaultTerrainChangeListener());
		Unit unitRandom = new Unit(world, "RandomUnit");
		world.addEntity(unitRandom);
		
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
		world.addEntity(unitMin);
		
		unitMin.moveToAdjacent(0, 0, 1);
		assertTrue(unitMin.getCurrentActivity()== Activity.MOVE);
		advanceTimeFor(world, 10, 0.2);
		assertTrue(unitMin.getCurrentActivity() == Activity.NOTHING);
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
	private int[][][] terrain(String name) {
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
