package hillbillies.tasks;

import java.util.List;

public interface ISubTextObject {
	
	public List<TextObject> getSubText();
	
	public default boolean hasSubText() {
		return this.getSubText().size() != 0;
	}
}
