package hillbillies.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part2.facade.IFacade;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.util.Position;
import ogp.framework.util.ModelException;

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
	public void CaveIn() {
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
	public void Constructor() {
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
		assertEquals(TYPE_ROCK, world.getTerrain(1, 1, 0).ordinal());
		//	The second one collapses because it is no longer connected to a solid cube or the border of the world.
		assertEquals(TYPE_TREE, world.getTerrain(1, 1, 1).ordinal());
		assertEquals(TYPE_AIR, world.getTerrain(5,5,5).ordinal());
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
