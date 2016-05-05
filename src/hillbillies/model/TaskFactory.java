package hillbillies.model;

import java.util.List;

import hillbillies.part3.programs.ITaskFactory;
import hillbillies.part3.programs.SourceLocation;

public class TaskFactory implements ITaskFactory<Object, Object, Object>{

	/* TASKS */

	/**
	 * Create a list of tasks from the given arguments.
	 * 
	 * @param name
	 *            The name of the task
	 * @param priority
	 *            The initial priority of the task
	 * @param activity
	 *            The activity of the task. Most likely this is a sequence
	 *            statement.
	 * @param selectedCubes
	 *            A list of cube coordinates (each represented as an array {x,
	 *            y, z}) that were selected by the player in the GUI.
	 * @return A list of new task instances. One task instance should be created
	 *         for each selectedCube coordinate. If selectedCubes is empty and
	 *         the 'selected' expression does not occur in the activity, a list
	 *         with exactly one Task instance should be returned.
	 */
	@Override
	public List createTasks(String name, int priority, Object activity, List selectedCubes) {
		// TODO Auto-generated method stub
		return null;
	}

	/* STATEMENTS */

	/**
	 * Create a statement that represents the assignment of a variable.
	 * 
	 * @param variableName
	 *            The name of the assigned variable
	 * @param value
	 *            An expression that evaluates to the assigned value
	 */
	@Override
	public Object createAssignment(String variableName, Object value, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Create a statement that represents a while loop.
	 * 
	 * @param condition
	 *            The condition of the while loop
	 * @param body
	 *            The body of the loop (most likely this is a sequence
	 *            statement).
	 */
	@Override
	public Object createWhile(Object condition, Object body, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Create an if-then-else statement.
	 * 
	 * @param condition
	 *            The condition of the if statement
	 * @param ifBody
	 *            * The body of the if-part, which must be executed when the
	 *            condition evaluates to true
	 * @param elseBody
	 *            The body of the else-part, which must be executed when the
	 *            condition evaluates to false. Can be null if no else clause is
	 *            specified.
	 */
	@Override
	public Object createIf(Object condition, Object ifBody, Object elseBody, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Create a break statement that immediately terminates the enclosing loop.
	 * 
	 * @param sourceLocation
	 * 
	 * @note Students working alone may return null.
	 */
	@Override
	public Object createBreak(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Create a print statement that prints the value obtained by evaluating the
	 * given expression.
	 * 
	 * @param value
	 *            The expression to evaluate and print
	 */
	@Override
	public Object createPrint(Object value, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Create a sequence of statements.
	 * 
	 * @param statements
	 *            The statements that must be executed in the given order.
	 */
	@Override
	public Object createSequence(List statements, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Create a moveTo statement
	 * 
	 * @param position
	 *            The position to which to move
	 */
	@Override
	public Object createMoveTo(Object position, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Create a work statement
	 * 
	 * @param position
	 *            The position on which to work
	 */
	@Override
	public Object createWork(Object position, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Create a follow statement
	 * 
	 * @param unit
	 *            The unit to follow
	 */
	@Override
	public Object createFollow(Object unit, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Create an attack statement
	 * 
	 * @param unit
	 *            The unit to attack
	 */
	@Override
	public Object createAttack(Object unit, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Create an expression that evaluates to the current value of the given
	 * variable.
	 * 
	 * @param variableName
	 *            The name of the variable to read.
	 */
	@Override
	public Object createReadVariable(String variableName, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Create an expression that evaluates to true when the given position
	 * evaluates to a solid position; false otherwise.
	 * 
	 * @param position
	 *            The position expression
	 */
	@Override
	public Object createIsSolid(Object position, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Create an expression that evaluates to true when the given position
	 * evaluates to a passable position; false otherwise.
	 * 
	 * @param position
	 *            The position expression
	 */
	@Override
	public Object createIsPassable(Object position, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Create an expression that evaluates to true when the given unit evaluates
	 * to a unit of the same faction; false otherwise.
	 * 
	 * @param unit
	 *            The unit expression
	 */
	@Override
	public Object createIsFriend(Object unit, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Create an expression that evaluates to true when the given unit evaluates
	 * to a unit of another faction; false otherwise.
	 * 
	 * @param unit
	 *            The unit expression
	 */
	@Override
	public Object createIsEnemy(Object unit, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Create an expression that evaluates to true when the given unit evaluates
	 * to a unit that is alive; false otherwise.
	 * 
	 * @param unit
	 *            The unit expression
	 */
	@Override
	public Object createIsAlive(Object unit, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Create an expression that evaluates to true when the given unit evaluates
	 * to a unit that carries an item; false otherwise.
	 * 
	 * @param unit
	 *            The unit expression
	 */
	@Override
	public Object createCarriesItem(Object unit, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Create an expression that evaluates to true when the given expression
	 * evaluates to false, and vice versa.
	 * 
	 * @param expression
	 */
	@Override
	public Object createNot(Object expression, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Create an expression that evaluates to true when both the left and right
	 * expression evaluate to true; false otherwise.
	 * 
	 * @note short-circuit: the right expression does not need to be evaluated
	 *       when the left expression evaluates to false.
	 */
	@Override
	public Object createAnd(Object left, Object right, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Create an expression that evaluates to false only when the left and right
	 * expression evaluate to false; true otherwise.
	 * 
	 * @note short-circuit: the right expression does not need to be evaluated
	 *       when the left expression evaluates to true.
	 */
	@Override
	public Object createOr(Object left, Object right, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Create an expression that evaluates to the current position of the unit
	 * that is executing the task.
	 */
	@Override
	public Object createHerePosition(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Create an expression that evaluates to the position of a log.
	 * 
	 * @note for groups of two students, this needs to be the log closest to the
	 *       unit that is executing the task.
	 */
	@Override
	public Object createLogPosition(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Create an expression that evaluates to the position of a boulder.
	 * 
	 * @note for groups of two students, this needs to be the boulder closest to
	 *       the unit that is executing the task.
	 */
	@Override
	public Object createBoulderPosition(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Create an expression that evaluates to the position of a workshop.
	 * 
	 * @note for groups of two students, this needs to be the workshop closest
	 *       to the unit that is executing the task.
	 */
	@Override
	public Object createWorkshopPosition(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Create an expression that evaluates to the position selected by the user
	 * in the GUI.
	 * 
	 * @note Students working alone may return null.
	 */
	@Override
	public Object createSelectedPosition(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Create an expression that evaluates to a position next to the given
	 * position.
	 * 
	 * @param position
	 * 
	 */
	@Override
	public Object createNextToPosition(Object position, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Create an expression that evaluates to a static position with a given
	 * coordinate.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	@Override
	public Object createLiteralPosition(int x, int y, int z, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Create an expression that evaluates to the unit that is currently
	 * executing the task.
	 */
	@Override
	public Object createThis(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Create an expression that evaluates to a unit that is part of the same
	 * faction as the unit currently executing the task.
	 */
	@Override
	public Object createFriend(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Create an expression that evaluates to a unit that is not part of the
	 * same faction as the unit currently executing the task.
	 */
	@Override
	public Object createEnemy(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Create an expression that evaluates to any unit (other than this).
	 */
	@Override
	public Object createAny(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Create an expression that evaluates to true.
	 */
	@Override
	public Object createTrue(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Create an expression that evaluates to false.
	 */
	@Override
	public Object createFalse(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

}
