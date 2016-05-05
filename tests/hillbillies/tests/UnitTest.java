package hillbillies.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import hillbillies.model.Activity;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part2.internal.map.CubeType;
import hillbillies.part2.internal.map.GameMap;
import hillbillies.part2.internal.map.GameMapReader;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.part3.facade.IFacade;
import hillbillies.util.Position;
import ogp.framework.util.ModelException;
import ogp.framework.util.Util;

/**
 * @version 2.1
 */
public class UnitTest {

	private Unit unitMax, unitMin, unitLowWeight;
	private World world20x40x10;
	private int[] origin = new int[] {0,0,0};
	private TerrainChangeListener modelListener = new DefaultTerrainChangeListener();
	private int[][][] Terrain = Terrain("20x40x10");
	private Unit unitRandom;
	
	private final int TIMES_TEST = 20;
	
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
	
	private int[][][] Terrain(String size) {
		GameMap map;
		final String LEVEL_FILE_EXTENSION = ".wrld";
		final String LEVELS_PATH = "resources/";
		try {
			map = new GameMapReader().readFromResource(LEVELS_PATH + size + LEVEL_FILE_EXTENSION);
			
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
	
	/**
	 * Set up a mutable test fixture.
	 * 
	 * @post The variable unitMax references a new unit with all primary 
	 * 				attributes at their maximum on creation and full health and stamina.
	 * @post The variable unitMin references a new unit with all primary 
	 * 				attributes at their minimum on creation and full health and stamina.
	 * @post The variable unitLowWeight references a new unit with its primary attributes
	 * 				chosen in such a way that the given weight is below the minimal weight.
	 * @note	25*25/50 = 12.5 < 13
	 * @note	100*100/50 = 200
	 */
	@Before
	public void setUp() throws Exception {
		world20x40x10 = new World(Terrain, modelListener);
		unitRandom = new Unit(world20x40x10, "RandomUnit");
		unitMax = new Unit(world20x40x10, "UnitMax",new int[]{world20x40x10.getMaximumXValue()-1, 
																							world20x40x10.getMaximumYValue()-1, 
																							world20x40x10.getMaximumZValue()-1},
																							100,100,100,100);
		unitMin = new Unit(world20x40x10, "UnitMin", new int[]{0, 0, 1},25,25,25, 25);
		unitLowWeight = new Unit(world20x40x10, "UnitLowWeight", new int[] {world20x40x10.getMaximumXValue()-world20x40x10.getMaximumXValue()/2-3, 
																														world20x40x10.getMaximumYValue()-world20x40x10.getMaximumYValue()+5, 
																														world20x40x10.getMaximumZValue()-5}, 
																														25,60,50,40);
	}
	
	@Test
	public final void advanceTimeWorkAt_CurrentPosition(){
		unitMin.workAt(unitMin.getCubePosition());
		System.out.println(Arrays.toString(unitMin.getCubePosition()));
		double deltaT = 0.1;
		assertTrue(unitMin.isWorking());
		assertTrue(unitMin.getProgress() == 0);
		
		advanceTimeFor(unitMin, unitMin.getWorkDuration(), deltaT);
		
		assertFalse(unitMin.isWorking());
		assertTrue(unitMin.getProgress() == 0);
		
	}
	
	@Test
	public final void advanceTimeWorkAt_Origin(){
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
		assertTrue(unitMin.getProgress() == 0);
		
	}
	
	@Test
	public final void AdvanceTime_DeltaT() {
		assertTrue(unitMin.getGametime() == 0);
		
		try {unitMin.advanceTime(-1); assertTrue(false);} catch(IllegalArgumentException e){assertTrue(true);}
		try {unitMin.advanceTime(0.2+1.0/90.0); assertTrue(false);} catch(IllegalArgumentException e){assertTrue(true);}
		
		unitMin.advanceTime(0.2);
		assertTrue(Util.fuzzyEquals(unitMin.getGametime(), 0.2));
	}
	
	@Test
	public final void extendedConstructor_NormalCase() {
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
		assertEquals(55,unitLowWeight.getMinWeight());
	}
	
	@Test
	public final void canHaveAsWeight_TrueCase() {
		assertTrue(unitMin.canHaveAsWeight(30));
	}
	
	@Test
	public final void canHaveAsWeight_BelowMinBaseStat() {
		assertFalse(unitMin.canHaveAsWeight(-1));
	}
	
	@Test
	public final void canHaveAsWeight_AboveMaxBaseStat() {
		assertFalse(unitMax.canHaveAsWeight(201));
	}
	
	@Test
	public final void canHaveAsWeight_LowWeight() {
		assertFalse(unitLowWeight.canHaveAsWeight(25));
	}
	
	@Test
	public final void canHaveAsStrength_TrueCase() {
		assertTrue(unitLowWeight.canHaveAsStrength(150));
	}
	
	@Test
	public final void canHaveAsStrength_BelowMinBaseStat() {
		assertFalse(unitMin.canHaveAsWeight(-1));
	}
	
	@Test
	public final void canHaveAsStrength_AboveMaxBaseStat() {
		assertFalse(unitMax.canHaveAsWeight(201));
	}
	
	@Test
	public final void canHaveAsAgility_TrueCase() {
		assertTrue(unitLowWeight.canHaveAsAgility(150));
	}
	
	@Test
	public final void canHaveAsAgility_BelowMinBaseStat() {
		assertFalse(unitMin.canHaveAsAgility(-1));
	}
	
	@Test
	public final void canHaveAsAgility_AboveMaxBaseStat() {
		assertFalse(unitMax.canHaveAsAgility(201));
	}
	
	@Test
	public final void canHaveAsToughness_TrueCase() {
		assertTrue(unitLowWeight.canHaveAsToughness(150));
	}
	
	@Test
	public final void canHaveAsToughness_BelowMinBaseStat() {
		assertFalse(unitMin.canHaveAsToughness(-1));
	}
	
	@Test
	public final void canHaveAsToughness_AboveMaxBaseStat() {
		assertFalse(unitMax.canHaveAsToughness(201));
	}
	
	@Test
	public final void canHaveAsCurrentHealth_TrueCase() {
		assertTrue(unitMax.canHaveAsCurrentHealth(50));
	}
	
	@Test
	public final void canHaveAsCurrentHealth_Negative() {
		assertFalse(unitMax.canHaveAsCurrentHealth(-50));
	}

	@Test
	public final void canHaveAsCurrentHealth_AboveMaxHealth() {
		assertFalse(unitMax.canHaveAsCurrentHealth(250));
	}
	
	@Test
	public final void canHaveAsCurrentStamina_TrueCase() {
		assertTrue(unitMax.canHaveAsCurrentStamina(50));
	}
	
	@Test
	public final void canHaveAsCurrentStamina_Negative() {
		assertFalse(unitMax.canHaveAsCurrentStamina(-50));
	}

	@Test
	public final void canHaveAsCurrenStamina_AboveMaxStamina() {
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
	public final void isValidXCoordinate() {
		// True Case.
		assertTrue(unitMax.getPosition().isValidXCoordinate(unitMax.getWorld().getMaximumXValue()/2));
		
		// False Case negative coöridinate.
		assertFalse(unitMax.getPosition().isValidXCoordinate(-25.0));
		
		// False Case coöridinate to big.
		assertFalse(unitMax.getPosition().isValidXCoordinate(unitMax.getWorld().getMaximumXValue()));
	}
	
	@Test
	public final void isValidYCoordinate() {
		// True case.
		assertTrue(unitMax.getPosition().isValidYCoordinate(unitMax.getWorld().getMaximumYValue()/2));
		
		// False Case negative coöridinate.
		assertFalse(unitMax.getPosition().isValidYCoordinate(-25.0));
				
		// False Case coöridinate to big.
		assertFalse(unitMax.getPosition().isValidYCoordinate(unitMax.getWorld().getMaximumYValue()));
	}
	
	@Test
	public final void isValidZCoordinate() {
		// True case.
		assertTrue(unitMax.getPosition().isValidZCoordinate(unitMax.getWorld().getMaximumZValue()/2));
		
		// False Case negative coöridinate.
		assertFalse(unitMax.getPosition().isValidZCoordinate(-25.0));
				
		// False Case coöridinate to big.
		assertFalse(unitMax.getPosition().isValidZCoordinate(unitMax.getWorld().getMaximumZValue()));
	}

	@Test
	public final void isValidPosition() {
		// True Case.
		assertTrue(unitMin.getPosition().canHaveAsUnitCoordinates(unitMin.getPosition().getXCoordinate(),unitMin.getPosition().getYCoordinate(),unitMin.getPosition().getZCoordinate()));
		// False case x-coördinate to big.
		assertFalse(unitMin.getPosition().canHaveAsUnitCoordinates(unitMin.getWorld().getMaximumXValue(),unitMin.getPosition().getYCoordinate(),unitMin.getPosition().getZCoordinate()));
		// False case y-coördinate to big.
		assertFalse(unitMin.getPosition().canHaveAsUnitCoordinates(unitMin.getPosition().getXCoordinate(),unitMin.getWorld().getMaximumYValue(),unitMin.getPosition().getZCoordinate()));
		// False case z-coördinate to big.
		assertFalse(unitMin.getPosition().canHaveAsUnitCoordinates(unitMin.getPosition().getXCoordinate(),unitMin.getPosition().getYCoordinate(), unitMin.getWorld().getMaximumZValue()));
		// False case negative x-coördinate.
		assertFalse(unitMin.getPosition().canHaveAsUnitCoordinates(-1,unitMin.getPosition().getYCoordinate(),unitMin.getPosition().getZCoordinate()));
		// False case negative y-coördinate.
		assertFalse(unitMin.getPosition().canHaveAsUnitCoordinates(unitMin.getPosition().getXCoordinate(),-1,unitMin.getPosition().getZCoordinate()));
		// False case negative z-coördinate.
		assertFalse(unitMin.getPosition().canHaveAsUnitCoordinates(unitMin.getPosition().getXCoordinate(),unitMin.getPosition().getYCoordinate(),-1));
	}
	
	@Test
	public final void getCoordinates() {
		assertTrue(Util.fuzzyEquals(unitMin.getPosition().getXCoordinate(), 0.5));
		assertTrue(Util.fuzzyEquals(unitMin.getPosition().getYCoordinate(), 0.5));
		assertTrue(Util.fuzzyEquals(unitMin.getPosition().getZCoordinate(), 1.5));
	}
	
	@Test
	public final void getCubePosition() {
		assertTrue(unitMin.getCubePosition()[0] == 0);
		assertTrue(unitMin.getCubePosition()[1] == 0);
		assertTrue(unitMin.getCubePosition()[2] == 1);
	}
	
	@Test
	public final void getCubeCente() {
		assertTrue(Position.getCubeCenter(unitMin.getCubePosition())[0] == 0.5);
		assertTrue(Position.getCubeCenter(unitMin.getCubePosition())[1] == 0.5);
		assertTrue(Position.getCubeCenter(unitMin.getCubePosition())[2] == 1.5);
	}
	
	@Test
	public final void setPosition_RandomPosition() {
		for (int count = 0; count < TIMES_TEST; count ++) {
			double[] position = Position.getCubeCenter(unitRandom.getWorld().getRandomAvailableUnitCoordinates());
			assertTrue(unitRandom.getPosition().canHaveAsUnitCoordinates(position));
		}
	}
	
	@Test
	public final void isValidTargetPosition_TrueCase() throws Exception {
		double[] target = {unitMin.getPosition().getXCoordinate(),unitMin.getPosition().getYCoordinate(),unitMin.getPosition().getZCoordinate()};
		assertTrue(unitMin.isValidTargetCoordinates(target));
		assertTrue(unitMin.isValidTargetCoordinates(null));
	}
	
	@Test
	public final void isValidTargetPosition_FalseCase() throws Exception {
		double[] target1 = {500,unitMin.getPosition().getYCoordinate(),unitMin.getPosition().getZCoordinate()};
		double[] target2 = {unitMin.getPosition().getXCoordinate(),-1,unitMin.getPosition().getZCoordinate()};
		assertFalse(unitMin.isValidTargetCoordinates(target1));
		assertFalse(unitMin.isValidTargetCoordinates(target2));
	}
	
	@Test
	public final void isValidInitialPosition_TrueCase() throws Exception {
		double[] target = {unitMin.getPosition().getXCoordinate(),unitMin.getPosition().getYCoordinate(),unitMin.getPosition().getZCoordinate()};
		assertTrue(unitMin.isValidInitialCoordinates(target));
	}
	
	@Test
	public final void isValidInitialPosition_FalseCase() throws Exception {
		double[] target1 = {500,unitMin.getPosition().getYCoordinate(),unitMin.getPosition().getZCoordinate()};
		double[] target2 = {unitMin.getPosition().getXCoordinate(),-1,unitMin.getPosition().getZCoordinate()};
		assertFalse(unitMin.isValidInitialCoordinates(target1));
		assertFalse(unitMin.isValidInitialCoordinates(target2));
	}
	
	@Test
	public final void getDistance() {
		double[] target = {unitMin.getPosition().getXCoordinate()+1,unitMin.getPosition().getYCoordinate()+1,unitMin.getPosition().getZCoordinate()+1};
		assertTrue(Util.fuzzyEquals(unitMin.getDistance(target),Math.sqrt(3) ));
	}
	
	@Test
	public final void getWalkSpeed() {
		double[] target = {unitMin.getPosition().getXCoordinate(),unitMin.getPosition().getYCoordinate(),unitMin.getPosition().getZCoordinate()+1};
		assertTrue(Util.fuzzyEquals(unitMin.getWalkSpeed(target),0.75));
		
	}
	
	@Test
	public final void getVelocityRandom() {
		// Execute multiple times because of randomness.
		for (int count = 0; count < 10; count++) {
			double[] target = Position.getCubeCenter(
					unitRandom.getWorld().getRandomUnitCoordinatesInRange(unitRandom.getPosition().getCoordinates(), 2));
			double speed = unitRandom.getWalkSpeed(target);
			
			assertTrue(Position.fuzzyEquals(unitRandom.getVelocity(target), 
					Position.getVelocity(unitRandom.getPosition().getCoordinates(), target, speed)));
		}
	}
	
	@Test
	public final void getVelocityNoDistance() {
		double[] target = unitRandom.getPosition().getCoordinates() ;
		double speed = unitRandom.getWalkSpeed(target);
		assertTrue(Position.fuzzyEquals(unitRandom.getVelocity(target), 
				Position.getVelocity(unitRandom.getPosition().getCoordinates(), target, speed)));
	}
	
	@Test
	public void isAdjacentTo() {
		assertTrue(unitMin.isAdjacentTo(new int[]{0,0,1}));
	}
	
	@Test
	public void moveToAdjacent() {
		unitMin.moveToAdjacent(1, 0, 0);
		assertTrue(unitMin.getCurrentActivity()== Activity.MOVE);
		assertTrue(unitMin.getInitialCoordinates()[0]== unitMin.getPosition().getXCoordinate());
		assertTrue(unitMin.getTargetCoordinates()[0]== unitMin.getPosition().getXCoordinate()+1);
	}
	
}
