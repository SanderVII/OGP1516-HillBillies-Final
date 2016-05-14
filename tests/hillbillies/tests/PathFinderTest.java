package hillbillies.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

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
		List<int[]> path1 = PathFinder.getPath(new int[]{},new int[]{}, world, false);
		
	}

	/**
	 * Helper method to load the terrain from a world-file.
	 * @param size
	 *				The size of the world that should be loaded. Only existing worldFiles should be asked for.
	 * @return The terrain of the world that was asked for.
	 */
	private int[][][] terrain(String size) {
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
	
}
