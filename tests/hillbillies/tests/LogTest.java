package hillbillies.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hillbillies.model.Item;
import hillbillies.model.Log;
import hillbillies.model.World;
import hillbillies.part2.listener.DefaultTerrainChangeListener;

/**
 * @author Sander Mergan, Thomas Vranken
 * @version	2.0
 */
public class LogTest {
	
	
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
	public void ConstructorTest() {
		int[][][] terrain = new int[20][40][10];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
		World world = new World(terrain, new DefaultTerrainChangeListener());
		try{
			new Log(world, new double[]{0.5,0.5,0.5}, Item.MAXIMAL_WEIGHT+1);
			assertFalse(true);}
		catch(IllegalArgumentException e){	assertTrue(true);}
		
		try{
			new Log(world, new double[]{0.5,0.5,0.5}, Item.MINIMAL_WEIGHT-1);
			assertFalse(true);}
		catch(IllegalArgumentException e){	assertTrue(true);}
		
		Log log = new Log(world, new double[]{0.5,0.5,0.5}, (Item.MAXIMAL_WEIGHT-Item.MAXIMAL_WEIGHT)/2+Item.MINIMAL_WEIGHT);
		assertTrue(log.canHaveAsWeight(log.getWeight()));
	}

}
