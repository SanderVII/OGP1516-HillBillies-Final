package hillbillies.tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hillbillies.model.World;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.positions.Position;
import ogp.framework.util.Util;

/**
 * @author Sander Mergan, Thomas Vranken
 * @version	2.1
 */
public class PositionTest {
	
	private final int[][]neighboursNormal = new int[][] {{9,9,9},{9,9,10},{9,9,11},
		{9,10,9},{9,10,10},{9,10,11},{9,11,9},{9,11,10},{9,11,11},
		{10,9,9},{10,9,10},{10,9,11},{10,10,9},{10,10,10},{10,10,11},
		{10,11,9},{10,11,10},{10,11,11},
		{11,9,9},{11,9,10},{11,9,11},{11,10,9},{11,10,10},{11,10,11},
		{11,11,9},{11,11,10},{11,11,11}};
	private final double deltaT = 0.1;
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
	public void constructorTest(){
		int[][][] terrain = new int[3][3][3];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
		World world = new World(terrain, new DefaultTerrainChangeListener());
		
		Position position = new Position(world, new int[]{1, 1, 1});
		assertTrue(position.getWorld() == world);
		assertTrue(Position.equals(position.getCoordinates(), new double[]{1.5, 1.5, 1.5} ));
		assertTrue(Position.equals(position.getCubeCoordinates(), new int[]{1, 1, 1} ));
		
		Position position2 = new Position(world, new int[]{0, 0, 0});
		assertTrue(position2.getWorld() == world);
		assertTrue(Position.equals(position2.getCoordinates(), new double[]{0.5, 0.5, 0.5} ));
		assertTrue(Position.equals(position2.getCubeCoordinates(), new int[]{0, 0, 0} ));
		
		Position position3 = new Position(world, new int[]{2, 2, 2});
		assertTrue(position3.getWorld() == world);
		assertTrue(Position.equals(position3.getCoordinates(), new double[]{2.5, 2.5, 2.5} ));
		assertTrue(Position.equals(position3.getCubeCoordinates(), new int[]{2, 2, 2} ));
		
		try{new Position(null, new int[]{0 ,0, 0}); assertTrue(false);} catch(IllegalArgumentException e){ assertTrue(true); }
		try{ new Position(world, new int[]{0, 0, 0, 0}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ new Position(world, new int[]{0, 0}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ new Position(world, new int[]{3, 3, 3}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ new Position(world, new int[]{-1, -1, -1}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
	}
	
	@Test
	public void toStringTest(){
		int[][][] terrain = new int[3][3][3];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
		World world = new World(terrain, new DefaultTerrainChangeListener());
		Position position = new Position(world, new int[]{1, 1, 1});
		
		assertTrue(position.toString().equals( "Position: { 1.5, 1.5, 1.5 }"));
	}
	
	@Test
	public void canHaveAsCoordinatesTest(){
		int[][][] terrain = new int[3][3][3];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
		World world = new World(terrain, new DefaultTerrainChangeListener());
		Position position = new Position(world, new int[]{1, 1, 1});
		
		assertFalse(position.canHaveAsCoordinates(new double[]{3, 2, 2}));
		assertFalse(position.canHaveAsCoordinates(new double[]{2, 3, 2}));
		assertFalse(position.canHaveAsCoordinates(new double[]{2, 2, 3}));
		assertFalse(position.canHaveAsCoordinates(new double[]{-1, 0, 0}));
		assertFalse(position.canHaveAsCoordinates(new double[]{0, -1, 0}));
		assertFalse(position.canHaveAsCoordinates(new double[]{0, 0, -1}));
		assertTrue(position.canHaveAsCoordinates(new double[]{0, 0, 0}));
		assertTrue(position.canHaveAsCoordinates(new double[]{2, 2, 2}));
		assertTrue(position.canHaveAsCoordinates(new double[]{0, 1, 2}));
	}
	
	@Test
	public void setCoordinatesTest(){
		int[][][] terrain = new int[3][3][3];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
		World world = new World(terrain, new DefaultTerrainChangeListener());
		Position position = new Position(world, new int[]{1, 1, 1});
		
		try{ position.setCoordinates(new int[]{0, 1, 2}); assertTrue(Position.equals(position.getCubeCoordinates(), new int[]{0, 1, 2}));} catch(IllegalArgumentException e){ assertTrue(false); }
		try{ position.setCoordinates(new int[]{0, 0, 0}); assertTrue(Position.equals(position.getCubeCoordinates(), new int[]{0, 0, 0}));} catch(IllegalArgumentException e){ assertTrue(false); }
		try{ position.setCoordinates(new int[]{0, 0, 0, 0}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ position.setCoordinates(new int[]{0, 0}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ position.setCoordinates(new int[]{3, 3, 3}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ position.setCoordinates(new int[]{-1, -1, -1}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		
		try{ position.setCoordinates(new double[]{0.5, 1.5, 2.5}); assertTrue(Position.equals(position.getCoordinates(), new double[]{0.5, 1.5, 2.5}));} catch(IllegalArgumentException e){ assertTrue(false); };
		try{ position.setCoordinates(new double[]{0.5, 0.5, 0.5}); assertTrue(Position.equals(position.getCoordinates(), new double[]{0.5, 0.5, 0.5}));} catch(IllegalArgumentException e){ assertTrue(false); };
		try{ position.setCoordinates(new double[]{0.5, 0.5, 0.5, 0.5}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ position.setCoordinates(new double[]{0.5, 0.5}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ position.setCoordinates(new double[]{3.5, 3.5, 3.5}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ position.setCoordinates(new double[]{-1.5, -1.5, -1.5}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
	}
	
	@Test
	public void getCubeCoordinatesTest() {
		assertTrue(Position.equals(Position.getCubeCoordinates(new double[] {10.96,10.24,10.1}), new int[] {10,10,10}));
		try{ Position.getCubeCoordinates(new double[]{0, 0, 0, 0}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.getCubeCoordinates(new double[]{0, 0}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
	}
	
	@Test
	public void getCubeCenterTest() {
		assertTrue(Position.fuzzyEquals(
				Position.getCubeCenter(new int[] {10,10,10}), new double[]{10.5,10.5,10.5}));
		assertTrue(Position.fuzzyEquals(
				Position.getCubeCenter(new int[]{5,-4,-5}), new double[]{5.5,-3.5,-4.5}));
		
		try{ Position.getCubeCenter(new double[]{0, 0, 0, 0}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.getCubeCenter(new double[]{0, 0}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
	}
	
	@Test
	public void getSurfaceCenterTest() {
		assertTrue(Position.fuzzyEquals(
				Position.getSurfaceCenter(new double[] {10.96,10.24,10.1}),new double[]{10.5,10.5,10.1}));
		assertTrue(Position.fuzzyEquals(
				Position.getSurfaceCenter(new double[]{5.2,-3.6,-4.9}),new double[]{5.5,-3.5,-4.9}));
		try{ Position.getCubeCenter(new double[]{0, 0, 0, 0}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.getCubeCenter(new double[]{0, 0}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
	}
	
	@Test
	public void calculateNextCoordinatesTest() {
		assertTrue(Position.fuzzyEquals(
				Position.calculateNextCoordinates(new double[] {1.0,1.0,1.0}, new double[]{10,-10,20}, deltaT),new double[]{2.0,0.0,3.0}));
		assertTrue(Position.fuzzyEquals(
				Position.calculateNextCoordinates(new double[] {-1.0,-1.0,-1.0}, new double[]{10,-10,20}, deltaT),new double[]{0.0,-2.0,1.0}));
		
		try{ Position.calculateNextCoordinates(new double[] {1.0,1.0,1.0,0.0}, new double[]{10,-10,20}, deltaT); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.calculateNextCoordinates(new double[] {1.0,1.0,1.0}, new double[]{10,-10,20,0}, deltaT); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.calculateNextCoordinates(new double[] {1.0,1.0,1.0,0.0}, new double[]{10,-10,20,0}, deltaT); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.calculateNextCoordinates(new double[] {1.0,1.0}, new double[]{10,-10,20}, deltaT); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.calculateNextCoordinates(new double[] {1.0,1.0,1.0}, new double[]{10,-10}, deltaT); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.calculateNextCoordinates(new double[] {1.0,1.0}, new double[]{10,-10}, deltaT); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.calculateNextCoordinates(new double[] {1.0,1.0,1.0,0.0}, new double[]{10,-10}, deltaT); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.calculateNextCoordinates(new double[] {1.0,1.0}, new double[]{10,-10,20,0}, deltaT); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		
	}
	
	@Test
	public void getDistanceTest() {
		assertTrue(Util.fuzzyEquals(Position.getDistance(new double[] {1.0,1.0,1.0}, new double[] {-1.0,-1.0,-1.0}),3.464102));
		try{ Position.getDistance(new double[] {1.0,1.0,1.0,0.0}, new double[]{10,-10,20}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.getDistance(new double[] {1.0,1.0,1.0}, new double[]{10,-10,20,0}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.getDistance(new double[] {1.0,1.0,1.0,0.0}, new double[]{10,-10,20,0}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.getDistance(new double[] {1.0,1.0}, new double[]{10,-10,20}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.getDistance(new double[] {1.0,1.0,1.0}, new double[]{10,-10}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.getDistance(new double[] {1.0,1.0}, new double[]{10,-10}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.getDistance(new double[] {1.0,1.0,1.0,0.0}, new double[]{10,-10}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.getDistance(new double[] {1.0,1.0}, new double[]{10,-10,20,0}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }	
	}
	
	@Test
	public void equalsTest() {
		assertTrue(Position.equals(new double[] {1.0,1.0,1.0}, new double[] {1.0,1.0,1.0}));
		try{ Position.equals(new double[] {1.0,1.0,1.0,0.0}, new double[]{10,-10,20}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.equals(new double[] {1.0,1.0,1.0}, new double[]{10,-10,20,0}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.equals(new double[] {1.0,1.0,1.0,0.0}, new double[]{10,-10,20,0}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.equals(new double[] {1.0,1.0}, new double[]{10,-10,20}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.equals(new double[] {1.0,1.0,1.0}, new double[]{10,-10}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.equals(new double[] {1.0,1.0}, new double[]{10,-10}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.equals(new double[] {1.0,1.0,1.0,0.0}, new double[]{10,-10}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.equals(new double[] {1.0,1.0}, new double[]{10,-10,20,0}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		
		assertTrue(Position.equals(new int[] {1,1,1}, new int[] {1,1,1}));
		try{ Position.equals(new int[] {1,1,1,0}, new int[]{10,-10,20}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.equals(new int[] {1,1,1}, new int[]{10,-10,20,0}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.equals(new int[] {1,1,1,0}, new int[]{10,-10,20,0}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.equals(new int[] {1,1}, new int[]{10,-10,20}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.equals(new int[] {1,1,1}, new int[]{10,-10}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.equals(new int[] {1,1}, new int[]{10,-10}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.equals(new int[] {1,1,1,0}, new int[]{10,-10}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.equals(new int[] {1,1}, new int[]{10,-10,20,0}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		
		int[][][] terrain = new int[3][3][3];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
		World world = new World(terrain, new DefaultTerrainChangeListener());
		Position position1 = new Position(world, new int[]{1, 1, 1});
		Position position2 = new Position(world, new int[]{1, 1, 1});
		Position position3 = new Position(world, new int[]{1, 2, 1});
		
		assertTrue(Position.equals(position1, position2));
		assertFalse(Position.equals(position1,  position3));
		assertFalse(Position.equals(position2,  position3));
	}
	
	@Test
	public void fuzzyEqualsTest() {
		assertTrue(Position.fuzzyEquals(new double[] {1.0,1.0,1.0}, new double[] {1.0000006,0.9999994,1.0}));
		try{ Position.fuzzyEquals(new double[] {1.0,1.0,1.0,0.0}, new double[]{10,-10,20}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.fuzzyEquals(new double[] {1.0,1.0,1.0}, new double[]{10,-10,20,0}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.fuzzyEquals(new double[] {1.0,1.0,1.0,0.0}, new double[]{10,-10,20,0}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.fuzzyEquals(new double[] {1.0,1.0}, new double[]{10,-10,20}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.fuzzyEquals(new double[] {1.0,1.0,1.0}, new double[]{10,-10}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.fuzzyEquals(new double[] {1.0,1.0}, new double[]{10,-10}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.fuzzyEquals(new double[] {1.0,1.0,1.0,0.0}, new double[]{10,-10}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.fuzzyEquals(new double[] {1.0,1.0}, new double[]{10,-10,20,0}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		
	}
	
	@Test
	public void isAdJacentToTest() {
		assertFalse(Position.isAdjacentTo(new int[] {10,10,10}, new int[]{5,-4,-5}));
		assertTrue(Position.isAdjacentTo(new int[] {10,10,10}, new int[] {10,10,10}));
		assertTrue(Position.isAdjacentTo(new int[] {10,10,10}, new int[]{9,9,9}));
		
		try{ Position.isAdjacentTo(new int[] {1,1,1,0}, new int[]{10,-10,20}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.isAdjacentTo(new int[] {1,1,1}, new int[]{10,-10,20,0}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.isAdjacentTo(new int[] {1,1,1,0}, new int[]{10,-10,20,0}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.isAdjacentTo(new int[] {1,1}, new int[]{10,-10,20}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.isAdjacentTo(new int[] {1,1,1}, new int[]{10,-10}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.isAdjacentTo(new int[] {1,1}, new int[]{10,-10}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.isAdjacentTo(new int[] {1,1,1,0}, new int[]{10,-10}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.isAdjacentTo(new int[] {1,1}, new int[]{10,-10,20,0}); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
	}
	
	@Test
	public void getVelocityTest() {
		assertTrue(Position.fuzzyEquals(
				Position.getVelocity(new double[] {1.0,1.0,1.0}, new double[] {-1.0,-1.0,-1.0}, 2.0), new double[]{-1.1547,-1.1547,-1.1547}));
		assertTrue(Position.fuzzyEquals(
				Position.getVelocity(new double[]{5.2,-3.6,-4.9}, new double[]{5.2,-3.6,-4.9}, 2.0),new double[]{0,0,0}));
		
		try{ Position.getVelocity(new double[] {1.0,1.0,1.0,0.0}, new double[]{10,-10,20}, 2.0); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.getVelocity(new double[] {1.0,1.0,1.0}, new double[]{10,-10,20,0}, 2.0); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.getVelocity(new double[] {1.0,1.0,1.0,0.0}, new double[]{10,-10,20,0}, 2.0); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.getVelocity(new double[] {1.0,1.0}, new double[]{10,-10,20}, 2.0); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.getVelocity(new double[] {1.0,1.0,1.0}, new double[]{10,-10}, 2.0); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.getVelocity(new double[] {1.0,1.0}, new double[]{10,-10}, 2.0); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.getVelocity(new double[] {1.0,1.0,1.0,0.0}, new double[]{10,-10}, 2.0); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ Position.getVelocity(new double[] {1.0,1.0}, new double[]{10,-10,20,0}, 2.0); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		
	}
	
	@Test
	public void getCubeCoordinatesInRange1Test() {
		int count = 0;
		Set<int[]> result = Position.getCubeCoordinatesInRange(new int[] {10,10,10}, 1);
		for (int[]position: neighboursNormal)
			for (int[] resultpos: result)
				if (Position.equals(position, resultpos))
					count++;
		assertTrue(count==27);
	}
	
	@Test
	public void getCubeCoordinatesInRange2Test() {
		assertTrue(Position.getCubeCoordinatesInRange(new int[] {10,10,10}, 4).size() == 729);
		assertTrue(Position.getCubeCoordinatesInRange(new int[] {10,10,10}, 2).size() == 125);
		assertTrue(Position.getCubeCoordinatesInRange(new int[] {-10,-10,-10}, 4).size() == 729);
		assertTrue(Position.getCubeCoordinatesInRange(new int[] {-10,-10,-10}, 2).size() == 125);
		assertTrue(Position.getCubeCoordinatesInRange(new int[] {-10,10,10}, 2).size() == 125);
		assertTrue(Position.getCubeCoordinatesInRange(new int[] {10,-10,10}, 2).size() == 125);
		assertTrue(Position.getCubeCoordinatesInRange(new int[] {10,10,-10}, 2).size() == 125);
		assertTrue(Position.getCubeCoordinatesInRange(new int[] {-10,-10,10}, 2).size() == 125);
		assertTrue(Position.getCubeCoordinatesInRange(new int[] {-10,10,-10}, 2).size() == 125);
		assertTrue(Position.getCubeCoordinatesInRange(new int[] {10,-10,-10}, 2).size() == 125);
	}
	
	@Test 
	public void getXYZCoordinate_canHaveAsXYZCoordinateTest(){
		int[][][] terrain = new int[3][3][3];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
		World world = new World(terrain, new DefaultTerrainChangeListener());
		Position position = new Position(world, new int[]{0, 1, 2});
		
		assertTrue(position.getXCoordinate() == 0.5);
		assertTrue(position.getYCoordinate() == 1.5);
		assertTrue(position.getZCoordinate() == 2.5);
		

		assertFalse(position.canHaveAsXCoordinate(3.5));
		assertFalse(position.canHaveAsXCoordinate(3.0));
		assertFalse(position.canHaveAsXCoordinate(-0.01));
		assertTrue(position.canHaveAsXCoordinate(1.5));
		assertFalse(position.canHaveAsYCoordinate(3.5));
		assertFalse(position.canHaveAsYCoordinate(3.0));
		assertFalse(position.canHaveAsYCoordinate(-0.01));
		assertTrue(position.canHaveAsYCoordinate(1.5));
		assertFalse(position.canHaveAsZCoordinate(3.5));
		assertFalse(position.canHaveAsZCoordinate(3.0));
		assertFalse(position.canHaveAsZCoordinate(-0.01));
		assertTrue(position.canHaveAsZCoordinate(1.5));
		
	}
	
	@Test 
	public void setXYZCoordinateTest(){
		int[][][] terrain = new int[3][3][3];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
		World world = new World(terrain, new DefaultTerrainChangeListener());
		Position position = new Position(world, new int[]{0, 1, 2});
		
		try{ position.setXCoordinate(2.5); assertTrue(position.getXCoordinate() == 2.5); } catch(IllegalArgumentException e){ assertTrue(false); }
		try{ position.setXCoordinate(-0.2); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ position.setXCoordinate(3.0); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ position.setYCoordinate(0.5); assertTrue(position.getYCoordinate() == 0.5); } catch(IllegalArgumentException e){ assertTrue(false); }
		try{ position.setYCoordinate(-0.2); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ position.setYCoordinate(3.0); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ position.setZCoordinate(1.5); assertTrue(position.getZCoordinate() == 1.5); } catch(IllegalArgumentException e){ assertTrue(false); }
		try{ position.setZCoordinate(-0.2); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		try{ position.setZCoordinate(3.0); assertTrue(false); } catch(IllegalArgumentException e){ assertTrue(true); }
		
	}
	
	
	
	
}
