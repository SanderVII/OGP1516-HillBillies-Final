package hillbillies.statements;

import java.util.List;

/**
 * An interface implementing methods for getting the sub-statements of a statement.
 *
 */
public interface ISubStatement {
	
	public List<Statement> getSubStatements();
	
	public default boolean hasSubStatements() {
		return this.getSubStatements().size() != 0;
	}
	
}
