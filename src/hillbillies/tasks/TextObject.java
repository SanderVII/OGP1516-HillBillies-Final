package hillbillies.tasks;

import hillbillies.model.Task;
import hillbillies.part3.programs.SourceLocation;

public abstract class TextObject {

	public TextObject(SourceLocation sourceLocation) {
		this.sourceLocation = sourceLocation;
	}
	
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
	
	private final SourceLocation sourceLocation;
	
	public abstract Task getSuperTask();
	
	/**
	 * Check if the object is correctly placed.
	 * Since we use generic classes to check at runtime, this method usually returns true.
	 * However, some text objects (such as break and readVariable)
	 * have specific conditions and must override this method.
	 * @return
	 */
	//TODO maybe better to make abstract?
	public boolean isWellFormed() {
		return true;
	}

}
