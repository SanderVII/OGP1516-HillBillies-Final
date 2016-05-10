package hillbillies.tests;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import hillbillies.model.*;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.part3.facade.Facade;
import hillbillies.util.Position;
import ogp.framework.util.ModelException;

/**
 * 
 * @author Thomas Vranken
 * @version	2.0
 */
@SuppressWarnings("all")
public class SchedulerTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	private Facade facade;
	private World world;
	private Unit unit;
	private Unit unit2;
	private Faction faction;
	private Faction faction2;
//	private Scheduler scheduler;

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
		this.facade = new Facade();
		int[][][] types = new int[3][3][3];
		types[1][1][0] = TYPE_ROCK;
		types[1][1][1] = TYPE_ROCK;
		types[1][1][2] = TYPE_TREE;
		types[2][2][2] = TYPE_WORKSHOP;

		this.world = facade.createWorld(types, new DefaultTerrainChangeListener());
		this.unit = new Unit(world, "Test", new int[] { 0, 0, 0 }, 50, 50, 50, 50);
		this.unit2 = new Unit(world, "TestTwo", new int[] { 0, 0, 0 }, 50, 50, 50, 50);
		this.faction = unit.getFaction();
		this.faction2 = unit2.getFaction();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testSchedulerLifetime() throws Exception {
		Scheduler scheduler = faction.getScheduler();
		assertTrue(scheduler.getFaction() == faction);
		assertTrue(scheduler.getNbTasks() == 0);
		assertTrue(this.facade.getScheduler(faction) == scheduler);
		assertTrue(scheduler.hasProperFaction());
		assertTrue(faction.hasProperScheduler());
		
		scheduler.terminate();
		assertTrue(scheduler.isTerminated());
		assertTrue(scheduler.getFaction() == null);
		assertFalse(faction.isTerminated());
		assertTrue(faction.getScheduler() == null);
	}
	
	@Test
	public void Scheduler_NullFaction() throws Exception {
		exception.expect(IllegalArgumentException.class);
		Scheduler testScheduler = new Scheduler(null);
	}
	
	@Test
	public void Scheduler_FactionWithScheduler() throws Exception {	
		exception.expect(IllegalArgumentException.class);
		Scheduler scheduler4 = new Scheduler(faction);
		assertFalse(faction.getScheduler() == scheduler4);
		assertTrue(null == scheduler4);
	}
	
	//TODO move to faction test?
	@Test
	public void SetScheduler_CurrentScheduler() {
		Scheduler scheduler2 = faction2.getScheduler();

		this.faction2.setScheduler(scheduler2);
		assertTrue(scheduler2.hasProperFaction());
		assertTrue(faction2.hasProperScheduler());
	}
	
	@Test
	public void addTasks() {
		Scheduler scheduler = faction.getScheduler();
		List<Task> tasks = new ArrayList<>();
		tasks.add(new Task("test0", 100, null));
		scheduler.addAllTasks(tasks);

	}
	
	@Test
	public void getTasksWithPredicate() {
		Scheduler scheduler = faction.getScheduler();
		List<Task> tasks = new ArrayList<>();
		tasks.add(new Task("test1", 100, null));
		tasks.add(new Task("test2", -5, null));
		for (Task task: tasks)
			task.addScheduler(scheduler);
		scheduler.addAllTasks(tasks);
		
		// Test for negative priority
		List<Task> result = scheduler.getTasksWithPredicate((Task x) -> (x.getPriority() < 0));
		assertTrue(result.contains(tasks.get(1)));
		assertFalse(result.contains(tasks.get(0)));
		
		// Test for names
		List<Task> result2 = scheduler.getTasksWithPredicate((Task x) -> (x.getName().contains("test")));
		assertTrue(result2.contains(tasks.get(1)));
		assertTrue(result2.contains(tasks.get(0)));
		List<Task> result3 = scheduler.getTasksWithPredicate((Task x) -> (x.getName().contains("test1")));
		assertFalse(result3.contains(tasks.get(1)));
		assertTrue(result3.contains(tasks.get(0)));
	}
	
	

}
