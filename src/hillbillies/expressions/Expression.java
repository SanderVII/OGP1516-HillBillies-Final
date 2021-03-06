package hillbillies.expressions;

import hillbillies.model.*;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.tasks.TextObject;

public abstract class Expression extends TextObject implements IExpression {

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
	
	public Task getSuperTask() {
		return this.getSuperText().getSuperTask();
	}
	
	@Override
	public Expression clone() throws CloneNotSupportedException {
		Expression cloned = (Expression) super.clone();
//		if (this.hasSuperText())
//			cloned.setSuperText(this.getSuperText().clone());
		return cloned;
	}
}
