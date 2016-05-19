package hillbillies.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.expressions.Expression;
import hillbillies.expressions.booleanType.AndExpression;
import hillbillies.expressions.booleanType.BooleanExpression;
import hillbillies.expressions.booleanType.CarriesItemExpression;
import hillbillies.expressions.booleanType.FalseExpression;
import hillbillies.expressions.booleanType.IsAliveExpression;
import hillbillies.expressions.booleanType.IsEnemyExpression;
import hillbillies.expressions.booleanType.IsFriendExpression;
import hillbillies.expressions.booleanType.IsPassableExpression;
import hillbillies.expressions.booleanType.IsSolidExpression;
import hillbillies.expressions.booleanType.NotExpression;
import hillbillies.expressions.booleanType.OrExpression;
import hillbillies.expressions.booleanType.TrueExpression;
import hillbillies.expressions.booleanType.VariableBooleanExpression;
import hillbillies.expressions.positionType.BoulderPositionExpression;
import hillbillies.expressions.positionType.HereExpression;
import hillbillies.expressions.positionType.LiteralPositionExpression;
import hillbillies.expressions.positionType.LogPositionExpression;
import hillbillies.expressions.positionType.NextToExpression;
import hillbillies.expressions.positionType.PositionExpression;
import hillbillies.expressions.positionType.PositionOfExpression;
import hillbillies.expressions.positionType.SelectedPositionExpression;
import hillbillies.expressions.positionType.VariablePositionExpression;
import hillbillies.expressions.positionType.WorkshopPositionExpression;
import hillbillies.expressions.unitType.AnyExpression;
import hillbillies.expressions.unitType.EnemyExpression;
import hillbillies.expressions.unitType.FriendExpression;
import hillbillies.expressions.unitType.ThisExpression;
import hillbillies.expressions.unitType.UnitExpression;
import hillbillies.expressions.unitType.VariableUnitExpression;
import hillbillies.model.Task;
import hillbillies.part3.programs.ITaskFactory;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.TaskParser;
import hillbillies.statements.BreakStatement;
import hillbillies.statements.SequenceStatement;
import hillbillies.statements.Statement;
import hillbillies.statements.expressionType.AssignmentStatement;
import hillbillies.statements.expressionType.IfElseStatement;
import hillbillies.statements.expressionType.PrintStatement;
import hillbillies.statements.expressionType.WhileStatement;
import hillbillies.statements.expressionType.actions.AttackStatement;
import hillbillies.statements.expressionType.actions.FollowStatement;
import hillbillies.statements.expressionType.actions.MoveToStatement;
import hillbillies.statements.expressionType.actions.WorkAtStatement;

public class TaskFactory implements ITaskFactory<Expression, Statement, Task> {
	
	public TaskFactory() {
		
	}
	
	//TODO problem: different tasks reference the same statement and influence each other.
	@Override
	public List<Task> createTasks(String name, int priority, Statement activity, List<int[]> selectedCubes) {
		List<Task> result = new ArrayList<>();
		if (selectedCubes.size() == 0) {
			//TODO no selected expression may exist in the statement(s).
			result.add(new Task(name, priority, activity));
		}
		else
			for (int[]cube: selectedCubes) {
				
				result.add(new Task(name, priority, activity, cube));
			}
		return result;		
	}

	@Override
	public Statement createAssignment(String variableName, Expression value, SourceLocation sourceLocation) {
		this.addVariable(variableName, value);
		return new AssignmentStatement<Expression>(variableName, value, sourceLocation);
	}

	@Override
	public Statement createWhile(Expression condition, Statement body, SourceLocation sourceLocation) {
		// TODO finish!!
		return new WhileStatement<BooleanExpression>(
				(BooleanExpression) condition,  body, sourceLocation);
	}

	@Override
	public Statement createIf(Expression condition, Statement ifBody, Statement elseBody,
			SourceLocation sourceLocation) {
		return new IfElseStatement<BooleanExpression>(
				(BooleanExpression) condition, ifBody, elseBody, sourceLocation);
	}

	@Override
	public Statement createBreak(SourceLocation sourceLocation) {
		//TODO finish
		return new BreakStatement(sourceLocation);
	}

	@Override
	public Statement createPrint(Expression value, SourceLocation sourceLocation) {
		return new PrintStatement<Expression>(value, sourceLocation);
	}

	@Override
	public Statement createSequence(List<Statement> statements, SourceLocation sourceLocation) {
		// TODO finish!!
		return new SequenceStatement(statements, sourceLocation);
	}

	@Override
	public Statement createMoveTo(Expression position, SourceLocation sourceLocation) {
		return new MoveToStatement<PositionExpression>((PositionExpression) position, sourceLocation);
	}

	@Override
	public Statement createWork(Expression position, SourceLocation sourceLocation) {
		return new WorkAtStatement<PositionExpression>((PositionExpression) position, sourceLocation);
	}

	@Override
	public Statement createFollow(Expression unit, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return new FollowStatement<UnitExpression>((UnitExpression) unit, sourceLocation);
	}

	@Override
	public Statement createAttack(Expression unit, SourceLocation sourceLocation) {
		return new AttackStatement<UnitExpression>((UnitExpression) unit, sourceLocation);
	}

	@Override
	public Expression createReadVariable(String variableName, SourceLocation sourceLocation) {
		Expression value = this.getValue(variableName);
		if (value instanceof BooleanExpression)
			return new VariableBooleanExpression(variableName, sourceLocation);
		else if (value instanceof UnitExpression)
			return new VariableUnitExpression(variableName, sourceLocation);
		else
			return new VariablePositionExpression(variableName, sourceLocation);
	}

	@Override
	public Expression createIsSolid(Expression position, SourceLocation sourceLocation) {
		return new IsSolidExpression<PositionExpression>(
				(PositionExpression)position, sourceLocation);
	}

	@Override
	public Expression createIsPassable(Expression position, SourceLocation sourceLocation) {
		return new IsPassableExpression<PositionExpression>(
				(PositionExpression)position, sourceLocation);
	}

	@Override
	public Expression createIsFriend(Expression unit, SourceLocation sourceLocation) {
		return new IsFriendExpression<UnitExpression>((UnitExpression)unit, sourceLocation);
	}

	@Override
	public Expression createIsEnemy(Expression unit, SourceLocation sourceLocation) {
		return new IsEnemyExpression<UnitExpression>((UnitExpression)unit, sourceLocation);
	}

	@Override
	public Expression createIsAlive(Expression unit, SourceLocation sourceLocation) {
		return new IsAliveExpression<UnitExpression>((UnitExpression)unit, sourceLocation);
	}

	@Override
	public Expression createCarriesItem(Expression unit, SourceLocation sourceLocation) {
		return new CarriesItemExpression<UnitExpression>((UnitExpression)unit, sourceLocation);
	}

	@Override
	public Expression createNot(Expression expression, SourceLocation sourceLocation) {
		return new NotExpression<BooleanExpression>((BooleanExpression) expression, sourceLocation);
	}

	@Override
	public Expression createAnd(Expression left, Expression right, SourceLocation sourceLocation) {
		return new AndExpression<BooleanExpression>(
				(BooleanExpression)left, (BooleanExpression)right, sourceLocation);
	}

	@Override
	public Expression createOr(Expression left, Expression right, SourceLocation sourceLocation) {
		return new OrExpression<BooleanExpression>(
				(BooleanExpression)left, (BooleanExpression)right, sourceLocation);
	}

	@Override
	public Expression createHerePosition(SourceLocation sourceLocation) {
		return new HereExpression(sourceLocation);
	}

	@Override
	public Expression createLogPosition(SourceLocation sourceLocation) {
		return new LogPositionExpression(sourceLocation);
	}

	@Override
	public Expression createBoulderPosition(SourceLocation sourceLocation) {
		return new BoulderPositionExpression(sourceLocation);
	}

	@Override
	public Expression createWorkshopPosition(SourceLocation sourceLocation) {
		return new WorkshopPositionExpression(sourceLocation);
	}

	@Override
	public Expression createSelectedPosition(SourceLocation sourceLocation) {
		return new SelectedPositionExpression(sourceLocation);
	}

	@Override
	public Expression createNextToPosition(Expression position, SourceLocation sourceLocation) {
		return new NextToExpression<PositionExpression>((PositionExpression) position, sourceLocation);
	}

	@Override
	public Expression createPositionOf(Expression unit, SourceLocation sourceLocation) {
		return new PositionOfExpression<UnitExpression>((UnitExpression) unit, sourceLocation);
	}

	@Override
	public Expression createLiteralPosition(int x, int y, int z, SourceLocation sourceLocation) {
		return new LiteralPositionExpression(x, y, z, sourceLocation);
	}

	@Override
	public Expression createThis(SourceLocation sourceLocation) {
		return new ThisExpression(sourceLocation);
	}

	@Override
	public Expression createFriend(SourceLocation sourceLocation) {
		return new FriendExpression(sourceLocation);
	}

	@Override
	public Expression createEnemy(SourceLocation sourceLocation) {
		return new EnemyExpression(sourceLocation);
	}

	@Override
	public Expression createAny(SourceLocation sourceLocation) {
		return new AnyExpression(sourceLocation);
	}

	@Override
	public Expression createTrue(SourceLocation sourceLocation) {
		return new TrueExpression(sourceLocation);
	}

	@Override
	public Expression createFalse(SourceLocation sourceLocation) {
		return new FalseExpression(sourceLocation);
	}
	
	/**
	 * Check whether this taskfactory has the given variable as one of its
	 * variables.
	 * 
	 * @param  variable
	 *         The variable to check.
	 */
	@Basic
	@Raw
	public boolean hasAsVariable(String variable) {
		return variables.containsKey(variable);
	}

	/**
	 * Check whether this taskfactory can have the given variable
	 * as one of its variables.
	 * 
	 * @param  variable
	 *         The variable to check.
	 * @return True if and only if the given variable is effective.
	 */
	@Raw
	public boolean canHaveAsVariable(String variable, Expression value) {
		return value != null;
	}

	/**
	 * Check whether this taskfactory has proper variables attached to it.
	 * 
	 * @return True if and only if this taskfactory can have each of the
	 *         variables attached to it as one of its variables.
	 */
	public boolean hasProperVariables() {
		for (String variable : variables.keySet()) {
			if (!canHaveAsVariable(variable,variables.get(variable)))
				return false;
		}
		return true;
	}

	/**
	 * Return the number of variables associated with this taskfactory.
	 *
	 * @return  The total number of variables collected in this taskfactory.
	 */
	public int getNbVariables() {
		return variables.size();
	}

	/**
	 * Add the given variable with expression to the map of variables of this taskfactory.
	 * 
	 * @param  	variable
	 *         	The variable to be added.
	 * @param	The value to store for the variable.
	 * @pre    	The given variable is effective.
	 * @post   	This task has the given variable as one of its variables.
	 */
	public void addVariable(String variable, Expression value) {
		assert (variable != null);
		variables.put(variable, value);
	}

	/**
	 * Remove the given variable from the set of variables of this taskfactory.
	 * 
	 * @param  variable
	 *         The variable to be removed.
	 * @pre    This task taskfactory the given variable as one of
	 *         its variables.
	 * @post   This taskfactory no longer has the given variable as
	 *         one of its variables.
	 */
	@Raw
	public void removeVariable(String variable) {
		assert this.hasAsVariable(variable);
		variables.remove(variable);
	}

	/**
	 * Variable referencing a map collecting all the variables
	 * of this taskfactory and their values.
	 * 
	 * @invar  The referenced map is effective.
	 *       | variables != null
	 * @invar  Each variable registered in the referenced map is
	 *         effective.
	 */
	private final Map<String, Expression> variables = new HashMap<>();
	
	/**
	 * Returns a map collecting all the variables of this taskfactory. 
	 * 
	 * @return	A map in which each variable value is effective.
	 */
	public Map<String, Expression> getVariables() {
		return new HashMap<>(variables);
	}
	
	/**
	 * Return the value of the given variable.
	 * 
	 * @param 	variable
	 * 			The variable which holds the value.
	 * @return	The corresponding value of the variable.
	 */
	public Expression getValue(String variable) {
		return variables.get(variable);
	}



}
