package hillbillies.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hillbillies.util.Position;

/**
 * 
 * @author Thomas Vranken
 * @version	2.0
 */
public class BoulderTest {
	
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

}
