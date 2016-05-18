package hillbillies.tests;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hillbillies.expressions.positionType.PositionExpression;
import hillbillies.expressions.positionType.SelectedPositionExpression;
import hillbillies.model.Faction;
import hillbillies.model.Scheduler;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.part3.facade.Facade;
import hillbillies.part3.programs.TaskParser;
import hillbillies.statements.expressionType.actions.WorkAtStatement;

/**
 * 
 * @author Sander Mergan, Thomas Vranken
 * @version	2.0
 */
public class TaskFactoryTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	private Facade facade;
	private World world;
	private Unit unit;
	private Scheduler scheduler;

	@SuppressWarnings("unused")
	private static final int TYPE_AIR = 0;
	private static final int TYPE_ROCK = 1;
	private static final int TYPE_TREE = 2;
	private static final int TYPE_WORKSHOP = 3;

	@Before
	public void setup() throws Exception {
		this.facade = new Facade();
		int[][][] types = new int[3][3][3];
		types[1][1][0] = TYPE_ROCK;
		types[1][1][1] = TYPE_ROCK;
		types[1][1][2] = TYPE_TREE;
		types[2][2][2] = TYPE_WORKSHOP;

		world = facade.createWorld(types, new DefaultTerrainChangeListener());
		unit = new Unit(world, "Test", new int[] { 0, 0, 0 }, 50, 50, 50, 50);
		facade.addUnit(unit, world);
		Faction faction = facade.getFaction(unit);

		scheduler = facade.getScheduler(faction);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void workSelected() throws Exception {
		List<Task> tasks = TaskParser.parseTasksFromString(
				"name: \"work task\"\npriority: 1\nactivities: work selected;", facade.createTaskFactory(),
				Collections.singletonList(new int[] { 1, 1, 1 }));

		// tasks are created
		assertNotNull(tasks);
		// there's exactly one task
		assertEquals(1, tasks.size());
		Task taskdummy = tasks.get(0);
		// test name
		assertEquals("work task", facade.getName(taskdummy));
		// test priority
		assertEquals(1, facade.getPriority(taskdummy));
		
		facade.schedule(scheduler, taskdummy);
		unit.startDefaultBehavior();
		
		assertTrue(unit.hasTask());
		Task task = unit.getTask();
		
		assertTrue(task.getUnit() == unit);
		assertTrue(task.getStatement().getTask() == task);
		@SuppressWarnings("unchecked")
		WorkAtStatement<PositionExpression> statement = (WorkAtStatement<PositionExpression>) task.getStatement();
		assertTrue(statement.getTask() == task);
		SelectedPositionExpression expression = (SelectedPositionExpression) statement.getExpression();
		assertTrue(expression.getSuperText() == statement);
		assertTrue(expression.getSuperTask() == task);
	}

}
