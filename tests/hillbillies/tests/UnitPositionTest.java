package hillbillies.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hillbillies.model.World;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.positions.Position;
import hillbillies.positions.UnitPosition;

/**
 * @author Sander Mergan, Thomas Vranken
 * @version	2.1
 */
public class UnitPositionTest {
	
	@SuppressWarnings("unused")
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
	public void canHaveAsCoordinatesTest(){
		int[][][] terrain = new int[3][3][3];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
		World world = new World(terrain, new DefaultTerrainChangeListener());
		Position position = new UnitPosition(world, new int[]{0, 0, 0});
		
		assertFalse(position.canHaveAsCoordinates(new double[]{3, 2, 2}));
		assertFalse(position.canHaveAsCoordinates(new double[]{2, 3, 2}));
		assertFalse(position.canHaveAsCoordinates(new double[]{2, 2, 3}));
		assertFalse(position.canHaveAsCoordinates(new double[]{-1, 0, 0}));
		assertFalse(position.canHaveAsCoordinates(new double[]{0, -1, 0}));
		assertFalse(position.canHaveAsCoordinates(new double[]{0, 0, -1}));
		assertTrue(position.canHaveAsCoordinates(new double[]{0, 0, 0}));
		assertTrue(position.canHaveAsCoordinates(new double[]{2, 2, 2}));
		assertTrue(position.canHaveAsCoordinates(new double[]{0, 1, 2}));
		assertFalse(position.canHaveAsCoordinates(new double[]{1, 1, 0}));
		assertFalse(position.canHaveAsCoordinates(new double[]{1, 1, 1}));
		assertTrue(position.canHaveAsCoordinates(new double[]{1, 1, 2}));
	}
}
