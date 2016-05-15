package hillbillies.statements;

import hillbillies.part3.programs.SourceLocation;
import hillbillies.statements.expressionType.WhileStatement;

public class BreakStatement extends Statement {

	public BreakStatement(SourceLocation sourceLocation) {
		super(sourceLocation);
		if (! (this.getSuperText() instanceof WhileStatement))
			throw new IllegalStateException("incorrect break placement");
	}

	@Override
	public void execute() {
		this.getSuperText().setStatus(Status.DONE);
	}
	
	/**
	 * Return true if this break statement has a while-statement as a
	 * super Text Object. 
	 * @note	This can span multiple 'layers' of text objects. 
	 * 			(i.e. break inside if/else inside while == true)
	 */
	@Override
	public boolean isWellFormed() {
		Statement superText = this.getSuperText();
		while ( !superText.hasTask()) {
			if (superText instanceof WhileStatement)
				return true;
			else
				superText = superText.getSuperText();
		}
		return false;
	}

}
