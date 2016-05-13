package hillbillies.tests;

import static org.junit.Assert.*;

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
public class EntityTest {
	
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
	public void terminateTest() {
		int[][][] terrain = new int[20][40][10];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
		World world = new World(terrain, new DefaultTerrainChangeListener());
		Log log = new Log(world, new int[]{0,0,0}, Item.MINIMAL_WEIGHT);
		world.addEntity(log);
		
		assertTrue(world.hasAsEntity(log));
		
		log.terminate();
		
		assertTrue(log.isTerminated());
		assertTrue(log.getWorld() == null);
		assertFalse(world.hasAsEntity(log));
	}
	
	@Test
	public void canHaveAsPosition_canHaveAsCoordinatesTest() {
		int[][][] terrain = new int[20][40][10];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
		World world = new World(terrain, new DefaultTerrainChangeListener());
		World world2 = new World(terrain, new DefaultTerrainChangeListener());
		Log log = new Log(world, new int[]{0,0,0}, Item.MINIMAL_WEIGHT);
		Position position = new Position(world, new int[]{0,0,0});
		Position position2 = new Position(world2, new int[]{0,0,0});
		Position position3 = new Position(world, new int[]{1,1,0});
		
		assertTrue(log.canHaveAsPosition(position));
		assertFalse(log.canHaveAsPosition(position2));
		assertTrue(log.canHaveAsPosition(position3));
		assertTrue(log.canHaveAsCoordinates(position.getCoordinates()));
		assertFalse(log.canHaveAsCoordinates(position3.getCoordinates()));
	}
	
	@Test
	public void canHaveAsWorld_hasProperWorldTest() {
		int[][][] terrain = new int[20][40][10];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
		World world = new World(terrain, new DefaultTerrainChangeListener());
		Log log = new Log(world, new int[]{0,0,0}, Item.MINIMAL_WEIGHT);
		
		
		assertTrue(log.canHaveAsWorld(world));
		assertTrue(log.canHaveAsWorld(null));
		
		world.addEntity(log);
		
		assertTrue(log.hasProperWorld());
		
		log.terminate();
		
		assertTrue(log.isTerminated());
		assertTrue(log.canHaveAsWorld(null));
		assertFalse(log.canHaveAsWorld(world));
		assertTrue(log.hasProperWorld());
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