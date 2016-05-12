package hillbillies.tests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hillbillies.model.Item;
import hillbillies.model.Log;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.util.Position;

/**
 * @author Sander Mergan, Thomas Vranken
 * @version	2.1
 */
public class ItemTest {
	
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
	public void moveToWorld_moveToUnitTest() {
		int[][][] terrain = new int[20][40][10];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
		World world = new World(terrain, new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "TestUnit",new int[]{0,0,0}, 100, 100, 100, 100);
		world.addEntity(unit);
		Log log = new Log(world, new double[]{0.5,0.5,0.5}, Item.MINIMAL_WEIGHT);
		world.addEntity(log);
		assertTrue(world.hasAsEntity(unit));
		assertTrue(world.hasAsEntity(log));
		
		// pick up log.
		unit.workAt(new int[]{0,0,0});
		advanceTimeFor(world, 100, 0.15);
		assertTrue(unit.getItem() == log);
		assertTrue(log.getUnit() == unit);
		assertTrue(log.getWorld() == null);
		assertFalse(world.hasAsEntity(log));
		
		// drop log.
		unit.workAt(new int[]{0,0,0});
		advanceTimeFor(world, 1, 0.1);
		assertTrue(unit.getItem() == null);
		assertTrue(log.getUnit() == null);
		assertTrue(log.getWorld() == world);
		assertTrue(world.hasAsEntity(log));
		
		// pick up log.
		unit.workAt(new int[]{0,0,0});
		advanceTimeFor(world, 100, 0.1);
		assertTrue(unit.getItem() == log);
		assertTrue(log.getUnit() == unit);
		assertTrue(log.getWorld() == null);
		assertFalse(world.hasAsEntity(log));
		unit.terminate();
		advanceTimeFor(world, 1, 0.1);
		assertTrue(unit.getItem() == null);
		assertTrue(log.getUnit() == null);
		assertTrue(log.getWorld() == world);
		assertTrue(world.hasAsEntity(log));
		
	}

	@Test
	public void canHaveAsWeightTest() {
		int[][][] terrain = new int[20][40][10];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
		World world = new World(terrain, new DefaultTerrainChangeListener());
		Log log = new Log(world, new double[]{0.5,0.5,0.5}, Item.MINIMAL_WEIGHT);
		world.addEntity(log);
		
		assertFalse(log.canHaveAsWeight(Item.MINIMAL_WEIGHT-1));
		assertTrue(log.canHaveAsWeight(Item.MINIMAL_WEIGHT));
		assertFalse(log.canHaveAsWeight(Item.MAXIMAL_WEIGHT+1));
		assertTrue(log.canHaveAsWeight(Item.MAXIMAL_WEIGHT));
	}
	
	@Test
	public void canHaveAsCoordinatesTest() {
		int[][][] terrain = new int[20][40][10];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
		World world = new World(terrain, new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "TestUnit",new int[]{0,0,0}, 100, 100, 100, 100);
		world.addEntity(unit);
		Log log = new Log(world, new double[]{0.5,0.5,0.5}, Item.MINIMAL_WEIGHT);
		world.addEntity(log);
		assertTrue(world.hasAsEntity(unit));
		assertTrue(world.hasAsEntity(log));
		
		// pick up log.
		unit.workAt(new int[]{0,0,0});
		advanceTimeFor(world, 100, 0.15);
		assertTrue(unit.getItem() == log);
		assertTrue(log.getUnit() == unit);
		assertTrue(log.getWorld() == null);
		assertFalse(world.hasAsEntity(log));
		
		assertTrue(log.canHaveAsCoordinates(new int[]{0, 0, 0}));
		assertFalse(log.canHaveAsCoordinates(new int[]{1, 1, 0}));
		assertFalse(log.canHaveAsCoordinates(new int[]{1, 1, 1}));
		assertTrue(log.canHaveAsCoordinates(new int[]{1, 1, 2}));
	}
	
	@Test
	public void canHaveAsUnit_hasProperUnitTest() {
		int[][][] terrain = new int[20][40][10];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
		World world = new World(terrain, new DefaultTerrainChangeListener());
		World world2 = new World(terrain, new DefaultTerrainChangeListener());
		Unit unit = new Unit(world, "TestUnit",new int[]{0,0,0}, 100, 100, 100, 100);
		world.addEntity(unit);
		Unit unit2 = new Unit(world2, "TestUnit",new int[]{0,0,0}, 100, 100, 100, 100);
		world2.addEntity(unit2);
		
		Log log = new Log(world, new double[]{0.5,0.5,0.5}, Item.MINIMAL_WEIGHT);
		world.addEntity(log);
		assertTrue(world.hasAsEntity(unit));
		assertTrue(world.hasAsEntity(log));
		assertTrue(log.canHaveAsUnit(null));
		assertFalse(log.canHaveAsUnit(unit2));
		assertTrue(log.hasProperUnit());
		log.terminate();
		assertTrue(log.canHaveAsUnit(null));
		assertFalse(log.canHaveAsUnit(unit));
		assertTrue(log.hasProperUnit());
		
		Log log2 = new Log(world, new double[]{1.5,0.5,0.5}, Item.MINIMAL_WEIGHT);
		world.addEntity(log2);
		unit.workAt(new int[]{1,0,0});
		advanceTimeFor(world, 100, 0.15);
		assertTrue(unit.getItem() == log2);
		assertTrue(log2.getUnit() == unit);
		assertTrue(log2.getWorld() == null);
		assertFalse(world.hasAsEntity(log2));
		assertTrue(log2.canHaveAsUnit(unit));
		assertTrue(log2.canHaveAsUnit(null));
		assertTrue(log2.hasProperUnit());
	}
	
	@Test
	public void advanceTimeTest(){
		int[][][] terrain = new int[20][40][10];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
		World world = new World(terrain, new DefaultTerrainChangeListener());
		
		Log log = new Log(world, new double[]{0.5,0.5,3.5}, Item.MINIMAL_WEIGHT);
		world.addEntity(log);
		System.out.println(Arrays.toString(log.getPosition().getCubeCoordinates()));
		assertTrue(Position.equals(log.getPosition().getCubeCoordinates(), new int[]{0,0,3}));
		advanceTimeFor(world, 20, 0.1);
		System.out.println(Arrays.toString(log.getPosition().getCubeCoordinates()));
		assertTrue(Position.equals(log.getPosition().getCubeCoordinates(), new int[]{0,0,0}));
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
