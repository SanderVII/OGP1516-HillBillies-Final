package hillbillies.expressions;

import hillbillies.model.*;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.statements.ISuperExpressionStatement;
import hillbillies.statements.ISuperStatement;
import hillbillies.statements.Statement;
import hillbillies.statements.expressionType.ExpressionStatement;
import hillbillies.tasks.ISuperTextObject;
import hillbillies.tasks.TextObject;

public abstract class Expression extends TextObject {

	public Expression(SourceLocation sourceLocation) {
		super(sourceLocation);
	}
	
	public abstract Object evaluate();
	
	/*
	 * NOTE: Expressions are either connected to a statement or another expression
	 * when a task is created.
	 */
	
	private TextObject superText;
	
	public TextObject getSuperText() {
		return this.superText;
	}
	
	public boolean hasSuperText() {
		return this.superText != null;
	}
	
	public void setSuperText(TextObject superText) {
		this.superText = superText;
	}
	
	
	
//	private Expression superExpression;
//	
//	public Expression getSuperExpression() {
//		return this.superExpression;
//	}
//	
//	@Override
//	public void setSuperExpression(Expression expression) {
//		this.superExpression = expression;
//	}
//	
//	private Statement superStatement;
//	
//	@Override
//	public Statement getSuperStatement() {
//		return this.superStatement;
//	}
//	
//	//TODO make sure the super statement is of type ExpressionStatement
//	/**
//	 * NOTE: expressions are created without a statement.
//	 * setExpression assumes this and creates a bidirectional association.
//	 */
//	@Override
//	public void setSuperStatement(Statement statement) {
//		assert (this.superStatement.getExpression() == this);
//		this.superStatement = statement;
//	}
	
//	public ExpressionStatement getStatement() {
//		return this.statement;
//	}
//	
//	public boolean hasStatement() {
//		return this.statement != null;
//	}
//	
//	/**
//	 * NOTE: expressions are created without a statement.
//	 * setExpression assumes this and creates a bidirectional association.
//	 */
//	public void setStatement(ExpressionStatement statement) throws IllegalArgumentException {
//		assert (this.statement.getExpression() == this);
//		this.statement = statement;
//	}
//	
//	private ExpressionStatement statement;
	
	public Task getSuperTask() {
		return this.getSuperText().getSuperTask();
	}
	
	
	
	/**
	 * Helper method for quickly getting the executing unit.
	 */
	public Unit getUnit() {
		Unit result = this.getSuperTask().getUnit();
		return result;
	}
}
