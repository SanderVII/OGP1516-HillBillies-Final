package hillbillies.tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hillbillies.util.Position;
import ogp.framework.util.Util;

/**
 * @author Sander Mergan, Thomas Vranken
 * @version	2.0
 */
public class PositionTest {
	
	final double[] positionEasy1 = new double[] {1.0,1.0,1.0};
	final double[] positionEasy2 = new double[] {-1.0,-1.0,-1.0};
	final double[] positionNormal = new double[] {10.96,10.24,10.1};
	final int[] cubeNormal = new int[] {10,10,10};
	final int[][]neighboursNormal = new int[][] {{9,9,9},{9,9,10},{9,9,11},
		{9,10,9},{9,10,10},{9,10,11},{9,11,9},{9,11,10},{9,11,11},
		{10,9,9},{10,9,10},{10,9,11},{10,10,9},{10,10,10},{10,10,11},
		{10,11,9},{10,11,10},{10,11,11},
		{11,9,9},{11,9,10},{11,9,11},{11,10,9},{11,10,10},{11,10,11},
		{11,11,9},{11,11,10},{11,11,11}};
	final double[] positionNegative = new double[]{5.2,-3.6,-4.9};
	final int[] cubeNegative = new int[]{5,-4,-5};
	final double speed = 2.0;
	final double[] velocity = new double[]{10,-10,20};
	final double deltaT = 0.1;

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
	public void getCubePosition() {
		assertTrue(Position.equals(Position.getCubeCoordinates(positionNormal),cubeNormal)); 
		assertTrue(Position.equals(Position.getCubeCoordinates(positionNegative),cubeNegative)); 
	}
	
	@Test
	public void getCubeCenter() {
		assertTrue(Position.fuzzyEquals(
				Position.getCubeCenter(cubeNormal), new double[]{10.5,10.5,10.5}));
		assertTrue(Position.fuzzyEquals(
				Position.getCubeCenter(cubeNegative), new double[]{5.5,-3.5,-4.5}));
	}
	
	@Test
	public void getSurfaceCenter() {
		assertTrue(Position.fuzzyEquals(
				Position.getSurfaceCenter(positionNormal),new double[]{10.5,10.5,10.1}));
		assertTrue(Position.fuzzyEquals(
				Position.getSurfaceCenter(positionNegative),new double[]{5.5,-3.5,-4.9}));
	}
	
	@Test
	public void calculateNextPosition() {
		assertTrue(Position.fuzzyEquals(
				Position.calculateNextCoordinates(positionEasy1, velocity, deltaT),new double[]{2.0,0.0,3.0}));
		assertTrue(Position.fuzzyEquals(
				Position.calculateNextCoordinates(positionEasy2, velocity, deltaT),new double[]{0.0,-2.0,1.0}));
	}
	
	@Test
	public void getDistance() {
		assertTrue(Util.fuzzyEquals(Position.getDistance(positionEasy1, positionEasy2),3.464102));
	}
	
	@Test
	public void isAdJacentTo() {
		assertFalse(Position.isAdjacentTo(cubeNormal, cubeNegative));
		assertTrue(Position.isAdjacentTo(cubeNormal, cubeNormal));
		assertTrue(Position.isAdjacentTo(cubeNormal, new int[]{9,9,9}));
	}
	
	@Test
	public void getVelocity() {
		assertTrue(Position.fuzzyEquals(
				Position.getVelocity(positionEasy1, positionEasy2, speed),new double[]{-1.1547,-1.1547,-1.1547}));
		assertTrue(Position.fuzzyEquals(
				Position.getVelocity(positionNegative, positionNegative, speed),new double[]{0,0,0}));
	}
	
	@Test
	public void getCubePositionsInRange1() {
		int count = 0;
		Set<int[]> result = Position.getCubeCoordinatesInRange(cubeNormal, 1);
		for (int[]position: neighboursNormal)
			for (int[] resultpos: result)
				if (Position.equals(position, resultpos))
					count++;
		assertTrue(count==27);
	}

}
