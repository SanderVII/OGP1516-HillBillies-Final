package hillbillies.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hillbillies.model.PathFinder;
import hillbillies.model.World;
import hillbillies.part2.internal.map.CubeType;
import hillbillies.part2.internal.map.GameMap;
import hillbillies.part2.internal.map.GameMapReader;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.util.Position;

/**
 * @author Sander Mergan, Thomas Vranken
 * @version	2.0
 */
public class PathFinderTest {
	
	@SuppressWarnings("unused")
	private static final int TYPE_AIR = 0;
	@SuppressWarnings("unused")
	private static final int TYPE_ROCK = 1;
	@SuppressWarnings("unused")
	private static final int TYPE_TREE = 2;
	@SuppressWarnings("unused")
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
	public void getPathTest() {
		int[][][] terrain = terrain("20x40x10");
		World world = new World(terrain, new DefaultTerrainChangeListener());
		// No diagonal moves allowed.
		List<int[]> path1 = PathFinder.getPath(new int[]{1, 27, 5},new int[]{1, 22, 5}, world, false);
		assertTrue(path1.size() == 8);
		for (int[] coordinates: path1){
			assertTrue(hasSolidNeighbours(coordinates, world));
			assertTrue(world.getCube(coordinates).isPassable());
		}
		
		List<int[]> path2 = PathFinder.getPath(new int[]{11, 17, 5},new int[]{13, 17, 5}, world, false);
		assertTrue(path2.size() == 5);
		for (int[] coordinates: path2){
			assertTrue(hasSolidNeighbours(coordinates, world));
			assertTrue(world.getCube(coordinates).isPassable());
			assertFalse(Position.equals(coordinates, new int[]{12, 17, 5}));
		}
		
		
		World world2 = new World(terrain("15x15x15uTurn"), new DefaultTerrainChangeListener());
		List<int[]> path3 = PathFinder.getPath(new int[]{0, 0, 0},new int[]{2, 0, 0}, world2, false);
		assertTrue(path3.size() == 11);
		for (int[] coordinates: path3){
			assertTrue(hasSolidNeighbours(coordinates, world));
			assertTrue(world2.getCube(coordinates).isPassable());
		}
	}

	/**
	 * Helper method to load the terrain from a world-file.
	 * @param name
	 *				The name of the world that should be loaded. Only existing wrld files should be asked for.
	 * @return The terrain of the world that was asked for.
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
	
	/**
	 * Helper method for checking if the cube position with the given coordinates is adjacent
	 * to at least one cube which is solid.
	 * 
	 * @param	coordinates
	 * 			The coordinates to check neighbours for.
	 * @param	world
	 * 			The world in which to check.
	 * @return	False if none of the neighbouring cubes is solid.
	 * 			| for (int[] position: neighbours)
	 *			|	if ( ! world.getCube(position).isPassable())
	 * 			|		then result == true
	 * 			| result == false
	 */
	private static boolean hasSolidNeighbours(int[] coordinates, World world) {
		Set<int[]> neighbours = world.getNeighbours(coordinates);
		for (int[] neighbour: neighbours)
			if ( ! world.getCube(neighbour).isPassable())
				return true;
		return false;
	}
}
