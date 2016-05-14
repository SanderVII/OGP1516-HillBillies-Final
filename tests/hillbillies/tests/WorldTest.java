package hillbillies.tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hillbillies.model.Boulder;
import hillbillies.model.Entity;
import hillbillies.model.Log;
import hillbillies.model.Terrain;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.util.Position;
import ogp.framework.util.Util;

/**
 * @author Sander Mergan, Thomas Vranken
 * @version	2.1
 */
public class WorldTest {
	
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
	public void caveInTest() {
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
	public void constructorTest() {
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
		
		assertTrue(world.isTerminated());
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
		assertTrue(world.getNeighbours(new int[]{1,1,1}).size() == 26);
		assertFalse(world.getNeighbours(new int[]{1,1,1}).contains(new int[]{1,1,1}));
		assertTrue(world.getNeighbours(new int[]{0,0,0}).size() == 7);
		assertFalse(world.getNeighbours(new int[]{0,0,0}).contains(new int[]{0,0,0}));
		assertTrue(world.getNeighbours(new int[]{1,1,0}).size() == 17);
		assertFalse(world.getNeighbours(new int[]{1,1,0}).contains(new int[]{1,1,0}));
		assertTrue(world.getNeighbours(new int[]{1,0,1}).size() == 17);
		assertFalse(world.getNeighbours(new int[]{1,0,1}).contains(new int[]{1,0,1}));
		assertTrue(world.getNeighbours(new int[]{0,1,1}).size() == 17);
		assertFalse(world.getNeighbours(new int[]{0,1,1}).contains(new int[]{0,1,1}));
		assertTrue(world.getNeighbours(new int[]{0,0,1}).size() == 11);
		assertFalse(world.getNeighbours(new int[]{0,0,1}).contains(new int[]{0,0,1}));
		assertTrue(world.getNeighbours(new int[]{1,0,0}).size() == 11);
		assertFalse(world.getNeighbours(new int[]{1,0,0}).contains(new int[]{1,0,0}));
		assertTrue(world.getNeighbours(new int[]{0,1,0}).size() == 11);
		assertFalse(world.getNeighbours(new int[]{1,1,1}).contains(new int[]{1,1,1}));
		
		Set<int[]> neighbours = world.getNeighbours(1, 1, 1);
		assertFalse(neighbours.contains(new int[]{1,1,1}));
	}
	
	@Test
	public void getTerrainTest() {
		int[][][] terrain = new int[3][3][3];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
		World world = new World(terrain, new DefaultTerrainChangeListener());
		assertTrue(world.getTerrain(1,1,1) == Terrain.WOOD);
		assertFalse(world.getTerrain(1,1,1) == Terrain.AIR);
		assertFalse(world.getTerrain(1,1,1) == Terrain.ROCK);
		assertFalse(world.getTerrain(1,1,1) == Terrain.WORKSHOP);
	}
	
	@Test
	public void getUnitsInCubeTest() {
		int[][][] terrain = new int[15][15][15];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
		
		World world = new World(terrain, new DefaultTerrainChangeListener());
		Unit unit = new Unit(world,  "Test a", new int[] { 5, 5, 0 }, 50, 50, 50, 50);
		unit.stopDefaultBehavior();
		world.addEntity(unit);
		Unit unit2 = new Unit(world,  "Test b", new int[] { 5, 5, 0 }, 49, 50, 50, 50);
		unit2.stopDefaultBehavior();
		world.addEntity(unit2);
		Unit unit3 = new Unit(world,  "Test c", new int[] { 5, 2, 0 }, 50, 50, 50, 50);
		unit3.stopDefaultBehavior();
		world.addEntity(unit3);
		
		assertTrue(world.getUnitsInCube(0,0,0).size() == 0);
		assertTrue(world.getUnitsInCube(5,5,0).size() == 2);
		assertTrue(world.getUnitsInCube(5,2,0).size() == 1);
	}
		
	@Test
	public void getDirectlyAdjecentCubesTest(){
		int[][][] terrain = new int[3][3][3];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
		World world = new World(terrain, new DefaultTerrainChangeListener());
		assertTrue(world.getDirectlyAdjacentCoordinates(1,1,1).size() == 6);
		assertFalse(world.getDirectlyAdjacentCoordinates(1,1,1).contains(new int[]{1,1,1}));
		assertTrue(world.getDirectlyAdjacentCoordinates(0,0,0).size() == 3);
		assertFalse(world.getDirectlyAdjacentCoordinates(0,0,0).contains(new int[]{0,0,0}));
		assertTrue(world.getDirectlyAdjacentCoordinates(1,1,0).size() == 5);
		assertFalse(world.getDirectlyAdjacentCoordinates(1,1,0).contains(new int[]{1,1,0}));
		assertTrue(world.getDirectlyAdjacentCoordinates(1,0,1).size() == 5);
		assertFalse(world.getDirectlyAdjacentCoordinates(1,0,1).contains(new int[]{1,0,1}));
		assertTrue(world.getDirectlyAdjacentCoordinates(0,1,1).size() == 5);
		assertFalse(world.getDirectlyAdjacentCoordinates(0,1,1).contains(new int[]{0,1,1}));
		assertTrue(world.getDirectlyAdjacentCoordinates(0,0,1).size() == 4);
		assertFalse(world.getDirectlyAdjacentCoordinates(0,0,1).contains(new int[]{0,0,1}));
		assertTrue(world.getDirectlyAdjacentCoordinates(1,0,0).size() == 4);
		assertFalse(world.getDirectlyAdjacentCoordinates(1,0,0).contains(new int[]{1,0,0}));
		assertTrue(world.getDirectlyAdjacentCoordinates(0,1,0).size() == 4);
		assertFalse(world.getDirectlyAdjacentCoordinates(1,1,1).contains(new int[]{1,1,1}));
		
		assertFalse(world.getDirectlyAdjacentCoordinates(1, 1, 1).contains(new int[]{1,1,1}));
	}
	
	@Test
	public void getValidCubeCoordinatesInRangeTest() {
		int[][][] terrain = new int[10][10][10];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
		World world = new World(terrain, new DefaultTerrainChangeListener());
		
		Set<int[]>temp = world.getValidCubeCoordinatesInRange(new int[]{1,1,1}, 3 );
		for (int[] cubeCoordinates: temp){
			assertTrue(world.canHaveAsCoordinates(cubeCoordinates));
		}
	}
	
	@Test
	public void getRandomValidCubeCoordinatesInRangeTest() {
		int[][][] terrain = new int[10][10][10];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
		World world = new World(terrain, new DefaultTerrainChangeListener());
		for (int i=0; i<50; i++){
			int[] cubeCoordinates = world.getRandomValidCubeCoordinatesInRange(new double[]{1.5,1.5,1.5}, 3 );
			
			assertTrue(world.canHaveAsCoordinates(cubeCoordinates));
			assertTrue(Position.getDistance(new double[]{1.5,1.5,1.5}, Position.getCubeCenter(cubeCoordinates)) <= Math.sqrt(3.0*3.0+3.0*3.0+3.0*3.0));
			
		}
	}
	
	@Test
	public void getRandomSolidCubeCoordinatesInRangeTest() {
		int[][][] terrain = new int[10][10][10];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
		World world = new World(terrain, new DefaultTerrainChangeListener());
		for (int i=0; i<50; i++){
			int[] cubeCoordinates = world.getRandomSolidCubeCoordinatesInRange(new double[]{1.5,1.5,1.5}, 3 );
			assertTrue(world.canHaveAsCoordinates(cubeCoordinates));
			assertTrue(Position.getDistance(new double[]{1.5,1.5,1.5}, Position.getCubeCenter(cubeCoordinates)) <= Math.sqrt(3.0*3.0+3.0*3.0+3.0*3.0));
			assertFalse(world.getCube(cubeCoordinates).isPassable());
			assertTrue(Position.equals(cubeCoordinates, new int[]{1,1,0}) || Position.equals(cubeCoordinates, new int[]{1,1,1}));
		}
		
		for (int i=0; i<10; i++){
			int[] cubeCoordinates = world.getRandomSolidCubeCoordinatesInRange(new double[]{9.5,9.5,9.5}, 3 );
			assertTrue(cubeCoordinates == null);
		}
		
	}
	
	@Test
	public void hasAsEntityTest() {
		int[][][] terrain = new int[20][40][10];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
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
		
		assertTrue(world.hasAsEntity(unit));
		assertTrue(world.hasAsEntity(log));
		assertTrue(world.hasAsEntity(boulder));
		assertFalse(world.hasAsEntity(null));
		assertFalse(world.hasAsEntity(log2));
		
	}
	
	@Test
	public void canHaveAsEntityTest() {
		int[][][] terrain = new int[20][40][10];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
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
		
		assertTrue(world.canHaveAsEntity(unit));
		assertTrue(world.canHaveAsEntity(log));
		assertTrue(world.canHaveAsEntity(boulder));
		assertFalse(world.canHaveAsEntity(null));
		assertTrue(world.canHaveAsEntity(log2));
		
		unit.terminate();
		
		assertFalse(world.canHaveAsEntity(unit));
	}
	
	@Test
	public void hasProperEntitiesTest() {
		int[][][] terrain = new int[20][40][10];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
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
		
		assertTrue(world.hasProperEntities());
		world.terminate();
		assertTrue(world.hasProperEntities());
		
	}
	
	@Test
	public void getNbEntitiesTest() {
		int[][][] terrain = new int[20][40][10];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
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
		
		assertTrue(world.getNbEntities() == 4);
		
		log2.terminate();
		assertTrue(world.getNbEntities() == 3);
		
		unit.terminate();
		assertTrue(world.getNbEntities() == 2);
		
		world.terminate();
		assertTrue(world.getNbEntities() == 0);
	}
	
	@Test
	public void addEntity_removeEntityTest() {
		int[][][] terrain = new int[20][40][10];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
		World world = new World(terrain, new DefaultTerrainChangeListener());
		assertTrue(world.getNbEntities() == 0);
		Unit unit = new Unit(world,  "Test", new int[] { 5, 5, 0 }, 50, 50, 50, 50);
		unit.stopDefaultBehavior();
		world.addEntity(unit);
		assertTrue(world.hasAsEntity(unit));
		Log log = new Log(world, new int[]{0,0,0});
		world.addEntity(log);
		assertTrue(world.hasAsEntity(log));
		Boulder boulder = new Boulder(world, new int[]{0,0,0});
		world.addEntity(boulder);
		assertTrue(world.hasAsEntity(boulder));
		Log log2 = new Log(world, new int[]{19,39,9});
		world.addEntity(log2);
		assertTrue(world.hasAsEntity(log2));
		assertTrue(world.getNbEntities() == 4);
		
		world.removeEntity(unit);
		assertTrue(world.getNbEntities() == 3);
		assertFalse(world.hasAsEntity(unit));
		world.removeEntity(boulder);
		assertTrue(world.getNbEntities() == 2);
		assertFalse(world.hasAsEntity(unit));
		world.removeEntity(log);
		assertTrue(world.getNbEntities() == 1);
		assertFalse(world.hasAsEntity(unit));
		world.removeEntity(log2);
		assertTrue(world.getNbEntities() == 0);
		assertFalse(world.hasAsEntity(unit));
	}
	
	@Test
	public void getItemAtTest() {
		int[][][] terrain = new int[20][40][10];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
		World world = new World(terrain, new DefaultTerrainChangeListener());
		Unit unit = new Unit(world,  "Test", new int[] { 0, 0, 0 }, 50, 50, 50, 50);
		unit.stopDefaultBehavior();
		world.addEntity(unit);
		Log log = new Log(world, new int[]{0,0,0});
		world.addEntity(log);
		assertTrue(world.getNbEntities() == 2);
		
		assertTrue(world.getItemAt(new int[]{0,0,0}) == log);
		assertTrue(world.hasItemAt(new int[]{0,0,0}));
		assertFalse(world.hasItemAt(new int[]{0,0,1}));
	}
	
	@Test
	public void UnitMethodsTest() {
		int[][][] terrain = new int[20][40][10];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
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
		Unit unit2 = new Unit(world,  "Test", new int[] { 5, 5, 1 }, 50, 50, 50, 50);
		unit2.stopDefaultBehavior();
		world.addEntity(unit2);
		Unit unit3 = new Unit(world,  "Test", new int[] { 6, 5, 0 }, 50, 50, 50, 50);
		unit3.stopDefaultBehavior();
		world.addEntity(unit3);
		
		assertTrue(world.getNbUnits() == 3);
		assertTrue(world.getUnitAt(new int[] { 5, 5, 0 }) == unit);
		assertTrue(world.getUnitAt(new int[] { 5, 5, 1 }) == unit2);
		assertTrue(world.getUnitAt(new int[] { 6, 5, 0 }) == unit3);
		assertFalse(world.hasUnitAt(new int[] { 0, 0, 0 }));
		assertTrue(world.hasUnitAt(new int[] { 5,5,1}));
	}
	
	@Test
	public void LogMethodsTest() {
		int[][][] terrain = new int[20][40][10];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
		World world = new World(terrain, new DefaultTerrainChangeListener());
		
		Unit unit = new Unit(world,  "Test", new int[] { 5, 5, 0 }, 50, 50, 50, 50);
		unit.stopDefaultBehavior();
		world.addEntity(unit);
		Log log = new Log(world, new int[]{5, 5, 0});
		world.addEntity(log);
		Boulder boulder = new Boulder(world, new int[]{5, 5, 0});
		world.addEntity(boulder);
		Log log2 = new Log(world, new int[]{5, 5, 1});
		world.addEntity(log2);
		Log log3 = new Log(world, new int[]{6, 5, 0});
		world.addEntity(log3);
		
		assertTrue(world.getNbLogs() == 3);
		assertTrue(world.getLogAt(new int[] { 5, 5, 0 }) == log);
		assertTrue(world.getLogAt(new int[] { 5, 5, 1 }) == log2);
		assertTrue(world.getLogAt(new int[] { 6, 5, 0 }) == log3);
		assertFalse(world.hasLogAt(new int[] { 0, 0, 0 }));
		assertTrue(world.hasLogAt(new int[] { 5,5,1}));
	}
	
	@Test
	public void BoulderMethodsTest() {
		int[][][] terrain = new int[20][40][10];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
		World world = new World(terrain, new DefaultTerrainChangeListener());
		
		Unit unit = new Unit(world,  "Test", new int[] { 5, 5, 0 }, 50, 50, 50, 50);
		unit.stopDefaultBehavior();
		world.addEntity(unit);
		Log log = new Log(world, new int[]{5, 5, 0});
		world.addEntity(log);
		Boulder boulder = new Boulder(world, new int[]{5, 5, 0});
		world.addEntity(boulder);
		Boulder boulder2 = new Boulder(world, new int[]{5, 5, 1});
		world.addEntity(boulder2);
		Boulder boulder3 = new Boulder(world, new int[]{6, 5, 0});
		world.addEntity(boulder3);
		
		assertTrue(world.getNbBoulders() == 3);
		assertTrue(world.getBoulderAt(new int[] { 5, 5, 0 }) == boulder);
		assertTrue(world.getBoulderAt(new int[] { 5, 5, 1 }) == boulder2);
		assertTrue(world.getBoulderAt(new int[] { 6, 5, 0 }) == boulder3);
		assertFalse(world.hasBoulderAt(new int[] { 0, 0, 0 }));
		assertTrue(world.hasBoulderAt(new int[] { 5,5,1}));
	}
	
	@Test
	public void spawnUnitTest() {
		int[][][] terrain = new int[20][40][10];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
		World world = new World(terrain, new DefaultTerrainChangeListener());
		
		for (int i=0; i<=World.MAX_UNITS_WORLD+1; i++){
			Unit unit =world.spawnUnit(false);
			if (unit!=null){
				assertTrue(Unit.isValidName(unit.getName()));
				assertTrue(world.isValidInitialUnitCoordinates(unit.getPosition().getCubeCoordinates()));
				assertTrue(unit.getWeight() >= Unit.getMinInitialBaseStat() && unit.getWeight() <= Unit.getMaxInitialBaseStat());
				assertTrue(unit.getStrength() >= Unit.getMinInitialBaseStat() && unit.getStrength() <= Unit.getMaxInitialBaseStat());
				assertTrue(unit.getAgility() >= Unit.getMinInitialBaseStat() && unit.getAgility() <= Unit.getMaxInitialBaseStat());
				assertTrue(unit.getToughness() >= Unit.getMinInitialBaseStat() && unit.getToughness() <= Unit.getMaxInitialBaseStat());
				assertTrue(unit.hasProperFaction());
				assertTrue(unit.hasProperWorld());
				assertTrue(unit.getDefaultBehaviorEnabled() == false);
			}
		}
	}
	
	@Test
	public void getRandomAvailableUnitCoordinatesTest() {
		int[][][] terrain = new int[20][40][10];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
		World world = new World(terrain, new DefaultTerrainChangeListener());
		
		for (int i=0; i<=50; i++){
			int[] randomCoordinates = world.getRandomAvailableUnitCoordinates();
			assertTrue(world.canHaveAsCoordinates(randomCoordinates));
			assertTrue(world.isValidInitialUnitCoordinates(randomCoordinates));
		}
	}
	
	@Test
	public void isValidInitialUnitCoordinatesTest() {
		int[][][] terrain = new int[20][40][10];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
		World world = new World(terrain, new DefaultTerrainChangeListener());

		assertTrue(world.isValidInitialUnitCoordinates(new int[]{0,0,0}));
		assertTrue(world.isValidInitialUnitCoordinates(new int[]{1,1,2}));
		assertFalse(world.isValidInitialUnitCoordinates(new int[]{1,1,0}));
		assertFalse(world.isValidInitialUnitCoordinates(new int[]{1,1,1}));
		assertFalse(world.isValidInitialUnitCoordinates(new int[]{5,5,5}));
	}
	
	@Test
	public void isValidxYZCoordinateTest() {
		int[][][] terrain = new int[20][40][10];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;
		World world = new World(terrain, new DefaultTerrainChangeListener());

		assertTrue(world.isValidXCoordinate(0));
		assertTrue(world.isValidYCoordinate(0));
		assertTrue(world.isValidZCoordinate(0));
		assertFalse(world.isValidXCoordinate(-1));
		assertFalse(world.isValidYCoordinate(-1));
		assertFalse(world.isValidZCoordinate(-1));
		assertTrue(world.isValidXCoordinate(19));
		assertTrue(world.isValidYCoordinate(39));
		assertTrue(world.isValidZCoordinate(9));
		assertFalse(world.isValidXCoordinate(20));
		assertFalse(world.isValidYCoordinate(40));
		assertFalse(world.isValidZCoordinate(10));
	}
	
	@Test
	public void advanceTimeTest() {
		int[][][] terrain = new int[20][40][10];
		terrain[1][1][0] = TYPE_ROCK;
		terrain[1][1][1] = TYPE_TREE;
		terrain[1][1][2] = TYPE_WORKSHOP;

		World world = new World(terrain, new DefaultTerrainChangeListener());
		Unit unit = new Unit(world,  "Test", new int[] { 0, 0, 0 }, 100, 100, 100, 100);
		unit.stopDefaultBehavior();
		world.addEntity(unit);
		Log log = new Log(world, new int[]{5, 5, 0});
		world.addEntity(log);
		Boulder boulder = new Boulder(world, new int[]{5, 5, 0});
		world.addEntity(boulder);
		Log log2 = new Log(world, new int[]{5, 5, 1});
		world.addEntity(log2);
		Log log3 = new Log(world, new int[]{6, 5, 0});
		world.addEntity(log3);
		
		assertTrue(world.isSolidConnectedToBorder(1, 1, 0));
		assertTrue(world.isSolidConnectedToBorder(1, 1, 1));
		unit.workAt(new int[]{1, 1, 0});
		advanceTimeFor(world, 100, 0.02);
		assertEquals(TYPE_AIR, world.getTerrain(1, 1, 0).ordinal());
		//	The second one collapses because it is no longer connected to a solid cube or the border of the world.
		assertEquals(TYPE_AIR, world.getTerrain(1, 1, 1).ordinal());
		
		for(Entity entity: world.getEntities()){
			System.out.println(entity.getGametime());
			assertTrue(Util.fuzzyEquals(entity.getGametime(), 100));
		}
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
