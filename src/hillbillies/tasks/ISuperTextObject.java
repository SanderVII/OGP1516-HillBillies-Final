package hillbillies.tasks;

public interface ISuperTextObject<O extends TextObject> {
	
	public abstract O getSuperText();
	
	public default boolean hasSuperText() {
		return this.getSuperText() != null;
	}
	
	abstract void setSuperText(O superText);
}
