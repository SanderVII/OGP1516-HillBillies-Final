package hillbillies.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hillbillies.model.Boulder;
import hillbillies.model.Log;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part2.listener.DefaultTerrainChangeListener;

/**
 * 
 * @author Thomas Vranken
 * @version	2.0
 */
public class WorldTest {
	
//	private int[][][] terrain = new int[20][40][10];
	private static final int TYPE_AIR = 0;
	private static final int TYPE_ROCK = 1;
	private static final int TYPE_TREE = 2;
	private static final int TYPE_WORKSHOP = 3;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void caveIn() {
		int[][][] terrain = new int[3][3][3];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;

		World world = new World(terrain, new DefaultTerrainChangeListener());
		Unit unit = new Unit(world,  "Test", new int[] { 0, 0, 0 }, 50, 50, 50, 50);
		unit.stopDefaultBehavior();
		world.addEntity(unit);
		assertTrue(world.isSolidConnectedToBorder(1, 1, 0));
		assertTrue(world.isSolidConnectedToBorder(1, 1, 1));
		unit.workAt(new int[]{1, 1, 0});
		advanceTimeFor(world, 100, 0.02);
		assertEquals(TYPE_AIR, world.getTerrain(1, 1, 0).ordinal());
		//	The second one collapses because it is no longer connected to a solid cube or the border of the world.
		assertEquals(TYPE_AIR, world.getTerrain(1, 1, 1).ordinal());
	}
	
	@Test
	public void constructor() {
		int[][][] terrain = new int[20][40][10];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
		terrain[5][5][5] = TYPE_TREE; // This should collaps.

		World world = new World(terrain, new DefaultTerrainChangeListener());
		assertTrue(world.getMaximumXValue() == 20);
		assertTrue(world.getMaximumYValue() == 40);
		assertTrue(world.getMaximumZValue() == 10);
		assertTrue(world.isSolidConnectedToBorder(1, 1, 0));
		assertTrue(world.isSolidConnectedToBorder(1, 1, 1));
		assertFalse(world.isSolidConnectedToBorder(5, 5, 5));
		assertFalse(world.isSolidConnectedToBorder(world.getMaximumXValue()-1, world.getMaximumYValue()-1, world.getMaximumZValue()-1));
		assertEquals(TYPE_ROCK, world.getTerrain(1, 1, 0).ordinal());
		//	The second one collapses because it is no longer connected to a solid cube or the border of the world.
		assertEquals(TYPE_TREE, world.getTerrain(1, 1, 1).ordinal());
		assertEquals(TYPE_AIR, world.getTerrain(5,5,5).ordinal());
		assertTrue(world.getTerrainChangeListener() != null);
		for (int x=0; x<(world.getMaximumXValue());x++){
			for (int y=0; y<(world.getMaximumYValue());y++){
				for (int z=0; z<(world.getMaximumZValue());z++){
					System.out.println(z);
					assertTrue(world.getCube(x,y,z) != null);
				}
			}
		}
	}
	
	@Test
	public void toStringTest() {
		int[][][] terrain = new int[20][40][10];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
		terrain[5][5][5] = TYPE_TREE; // This should collaps.

		World world = new World(terrain, new DefaultTerrainChangeListener());
		assertTrue(world.toString() == "<<< World >>> ");
		assertTrue(world.toString() instanceof String);
	}
	
	@Test
	public void terminateTest() {
		int[][][] terrain = new int[20][40][10];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
		terrain[5][5][5] = TYPE_TREE; // This should collaps.

		World world = new World(terrain, new DefaultTerrainChangeListener());
		Unit unit = new Unit(world,  "Test", new int[] { 5, 5, 0 }, 50, 50, 50, 50);
		unit.stopDefaultBehavior();
		world.addEntity(unit);
		Log log = new Log(world, new int[]{0,0,0});
		world.addEntity(log);
		Boulder boulder = new Boulder(world, new int[]{0,0,0});
		world.addEntity(boulder);
		Log log2 = new Log(world, new int[]{19,39,9});
		world.addEntity(log2);
		
		assertFalse(world.isTerminated());
		assertTrue(world.hasLogAt(new int[]{0,0,0}));
		assertTrue(world.hasLogAt(new int[]{19,39,9}));
		assertTrue(world.hasBoulderAt(new int[]{0,0,0}));
		assertTrue(world.hasUnitAt(new int[]{5,5,0}));
		assertTrue(world.hasProperEntities());
		
		world.terminate();
		
		assertFalse(world.hasLogAt(new int[]{0,0,0}));
		assertFalse(world.hasLogAt(new int[]{19,39,9}));
		assertFalse(world.hasBoulderAt(new int[]{0,0,0}));
		assertFalse(world.hasUnitAt(new int[]{5,5,0}));
		assertTrue(world.isTerminated());
		assertTrue(world.hasProperEntities());
	}

	@Test
	public void factionsTest() {
		int[][][] terrain = new int[20][40][10];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
		terrain[5][5][5] = TYPE_TREE; // This should collaps.

		World world = new World(terrain, new DefaultTerrainChangeListener());
		int amountOfUnits = World.MAX_FACTIONS*2+1;
		for (int x=0; x<amountOfUnits; x++){
			world.spawnUnit(false);
		}
		
		
		for (Unit unit: world.getUnits()){
			assertTrue(world.hasAsFaction(unit.getFaction()));
		}

		assertTrue(world.hasProperFactions());
		assertTrue(world.getNbActiveFactions() == 5); 
		
		assertFalse(world.hasRoomForFaction());
	}

	@Test
	public void coordinateBounderiesTest() {
		int[][][] terrain = new int[3][3][3];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
		World world = new World(terrain, new DefaultTerrainChangeListener());
		
		assertTrue(World.isValidMaximumXValue(World.CUBE_COORDINATE_MIN+1));
		assertTrue(World.isValidMaximumYValue(World.CUBE_COORDINATE_MIN+1));
		assertTrue(World.isValidMaximumZValue(World.CUBE_COORDINATE_MIN+1));
		assertFalse(World.isValidMaximumXValue(World.CUBE_COORDINATE_MIN));
		assertFalse(World.isValidMaximumYValue(World.CUBE_COORDINATE_MIN));
		assertFalse(World.isValidMaximumZValue(World.CUBE_COORDINATE_MIN));
		assertFalse(World.isValidMaximumXValue(World.CUBE_COORDINATE_MIN-1));
		assertFalse(World.isValidMaximumYValue(World.CUBE_COORDINATE_MIN-1));
		assertFalse(World.isValidMaximumZValue(World.CUBE_COORDINATE_MIN-1));
		
		assertTrue(world.canHaveAsCoordinates(new double[]{World.CUBE_COORDINATE_MIN,World.CUBE_COORDINATE_MIN,World.CUBE_COORDINATE_MIN}));
		assertFalse(world.canHaveAsCoordinates(new double[]{World.CUBE_COORDINATE_MIN-1,World.CUBE_COORDINATE_MIN,World.CUBE_COORDINATE_MIN}));
		assertFalse(world.canHaveAsCoordinates(new double[]{World.CUBE_COORDINATE_MIN,World.CUBE_COORDINATE_MIN-1,World.CUBE_COORDINATE_MIN}));
		assertFalse(world.canHaveAsCoordinates(new double[]{World.CUBE_COORDINATE_MIN,World.CUBE_COORDINATE_MIN,World.CUBE_COORDINATE_MIN-1}));
		assertTrue(world.canHaveAsCoordinates(new double[]{World.CUBE_COORDINATE_MIN+1,World.CUBE_COORDINATE_MIN,World.CUBE_COORDINATE_MIN}));
		assertTrue(world.canHaveAsCoordinates(new double[]{World.CUBE_COORDINATE_MIN,World.CUBE_COORDINATE_MIN+1,World.CUBE_COORDINATE_MIN}));
		assertTrue(world.canHaveAsCoordinates(new double[]{World.CUBE_COORDINATE_MIN,World.CUBE_COORDINATE_MIN,World.CUBE_COORDINATE_MIN+1}));

		assertFalse(world.canHaveAsCoordinates(new double[]{1,1,1,1}));
		
	}
	
	@Test
	public void NeighboursTest() {
		int[][][] terrain = new int[3][3][3];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
		World world = new World(terrain, new DefaultTerrainChangeListener());
		assertTrue(world.getNeighbours(new int[]{1,1,1}).size() == 27);
		assertFalse(world.getNeighbours(new int[]{1,1,1}).contains(new int[]{1,1,1}));
		assertTrue(world.getNeighbours(new int[]{0,0,0}).size() == 8);
		assertFalse(world.getNeighbours(new int[]{0,0,0}).contains(new int[]{0,0,0}));
		assertTrue(world.getNeighbours(new int[]{1,1,0}).size() == 18);
		assertFalse(world.getNeighbours(new int[]{1,1,0}).contains(new int[]{1,1,0}));
		assertTrue(world.getNeighbours(new int[]{1,0,1}).size() == 18);
		assertFalse(world.getNeighbours(new int[]{1,0,1}).contains(new int[]{1,0,1}));
		assertTrue(world.getNeighbours(new int[]{0,1,1}).size() == 18);
		assertFalse(world.getNeighbours(new int[]{0,1,1}).contains(new int[]{0,1,1}));
		assertTrue(world.getNeighbours(new int[]{0,0,1}).size() == 12);
		assertFalse(world.getNeighbours(new int[]{0,0,1}).contains(new int[]{0,0,1}));
		assertTrue(world.getNeighbours(new int[]{1,0,0}).size() == 12);
		assertFalse(world.getNeighbours(new int[]{1,0,0}).contains(new int[]{1,0,0}));
		assertTrue(world.getNeighbours(new int[]{0,1,0}).size() == 12);
		assertFalse(world.getNeighbours(new int[]{1,1,1}).contains(new int[]{1,1,1}));
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

}
