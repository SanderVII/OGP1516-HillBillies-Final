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
import hillbillies.expressions.positionType.PositionOfExpression;
import hillbillies.expressions.positionType.SelectedPositionExpression;
import hillbillies.expressions.unitType.AnyExpression;
import hillbillies.expressions.unitType.EnemyExpression;
import hillbillies.expressions.unitType.FriendExpression;
import hillbillies.expressions.unitType.UnitExpression;
import hillbillies.model.Activity;
import hillbillies.model.Faction;
import hillbillies.model.Scheduler;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.part3.facade.Facade;
import hillbillies.part3.programs.TaskParser;
import hillbillies.positions.Position;
import hillbillies.statements.expressionType.actions.AttackStatement;
import hillbillies.statements.expressionType.actions.FollowStatement;
import hillbillies.statements.expressionType.actions.MoveToStatement;
import hillbillies.statements.expressionType.actions.WorkAtStatement;
import ogp.framework.util.ModelException;

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
		advanceTimeFor(world, 1, 0.2);
		
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
	
	@Test
	public void attackEnemy() throws Exception {
		Unit enemy = new Unit(world, "Enemy", new int[] { 0, 0, 0 }, 50, 50, 50, 50);
		facade.addUnit(enemy, world);
		
		List<Task> tasks = TaskParser.parseTasksFromString(
				"name: \"attack enemy\"" + "\n" + 
				"priority: 1" + "\n" + 
				"activities: attack enemy;", 
				facade.createTaskFactory(),Collections.singletonList(new int[] { 1, 1, 1 }));
		
		// tasks are created
		assertNotNull(tasks);
		// there's exactly one task
		assertEquals(1, tasks.size());
		Task taskdummy = tasks.get(0);
		facade.schedule(scheduler, taskdummy);
		unit.startDefaultBehavior();
		advanceTimeFor(world, 0.1, 0.1);
		
		assertTrue(unit.hasTask());
		Task task = unit.getTask();
		
		assertTrue(task.getUnit() == unit);
		assertTrue(task.getStatement().getTask() == task);
		@SuppressWarnings("unchecked")
		AttackStatement<UnitExpression> statement = (AttackStatement<UnitExpression>) task.getStatement();
		assertTrue(statement.getTask() == task);
		EnemyExpression expression = (EnemyExpression) statement.getExpression();
		assertTrue(expression.getSuperText() == statement);
		assertTrue(expression.getSuperTask() == task);
		assertTrue(expression.evaluate() == enemy);
		assertTrue(unit.getCurrentActivity() == Activity.ATTACK);
	}
	
	@Test
	public void followAny() throws Exception {
		Unit any = new Unit(world, "Any", new int[] { 2, 2, 0 }, 50, 50, 50, 50);
		facade.addUnit(any, world);
		
		List<Task> tasks = TaskParser.parseTasksFromString(
				"name: \"follow any\"" + "\n" + 
				"priority: 10" + "\n" + 
				"activities: follow any;", 
				facade.createTaskFactory(),Collections.singletonList(new int[] { 1, 1, 1 }));
		
		// tasks are created
		assertNotNull(tasks);
		// there's exactly one task
		assertEquals(1, tasks.size());
		Task taskdummy = tasks.get(0);
		facade.schedule(scheduler, taskdummy);
		unit.startDefaultBehavior();
		advanceTimeFor(world, 0.1, 0.1);
		
		assertTrue(unit.hasTask());
		Task task = unit.getTask();
		
		assertTrue(task.getUnit() == unit);
		assertTrue(task.getStatement().getTask() == task);
		@SuppressWarnings("unchecked")
		FollowStatement<UnitExpression> statement = (FollowStatement<UnitExpression>) task.getStatement();
		assertTrue(statement.getTask() == task);
		AnyExpression expression = (AnyExpression) statement.getExpression();
		assertTrue(expression.getSuperText() == statement);
		assertTrue(expression.getSuperTask() == task);
		assertTrue(expression.evaluate() == any);
		assertTrue(unit.getCurrentActivity() == Activity.MOVE);
		assertTrue(unit.getUnitToFollow() == any);
	}
	
	@Test
	public void MoveToPositionOfAlly() throws Exception {
		Unit friend = new Unit(world, unit.getFaction(), "Ally", new int[] { 2, 2, 0 }, 50, 50, 50, 50);
		facade.addUnit(friend, world);
		
		List<Task> tasks = TaskParser.parseTasksFromString(
				"name: \"moveTo friend\"" + "\n" + 
				"priority: 10" + "\n" + 
				"activities:" + "\n" + 
				"ally := friend;" + "\n" + 
				"moveTo (position_of (ally));", 
				facade.createTaskFactory(),Collections.singletonList(new int[] { 1, 1, 1 }));
		
		// tasks are created
		assertNotNull(tasks);
		// there's exactly one task
		assertEquals(1, tasks.size());
		Task taskdummy = tasks.get(0);
		facade.schedule(scheduler, taskdummy);
		unit.startDefaultBehavior();
		advanceTimeFor(world, 0.1, 0.1);
		
		assertTrue(unit.hasTask());
		Task task = unit.getTask();
		
		assertTrue(task.getUnit() == unit);
		assertTrue(task.getStatement().getTask() == task);
		@SuppressWarnings("unchecked")
		MoveToStatement<PositionExpression> statement = (MoveToStatement<PositionExpression>) task.getStatement();
		assertTrue(statement.getTask() == task);
		PositionOfExpression<UnitExpression> expression = (PositionOfExpression<UnitExpression>) statement.getExpression();
		assertTrue(expression.getSuperText() == statement);
		assertTrue(expression.getSuperTask() == task);
		FriendExpression friendExpression = (FriendExpression) expression.getExpression();
		assertTrue(friendExpression.getSuperText() == expression);
		assertTrue(friendExpression.getSuperTask() == task);
		
		assertTrue(friendExpression.evaluate() == friend);
		assertTrue(unit.getCurrentActivity() == Activity.MOVE);
		assertTrue(Position.fuzzyEquals(unit.getTargetCoordinates(), friend.getCubeCenter()));
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
