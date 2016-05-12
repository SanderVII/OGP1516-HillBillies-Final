package hillbillies.expressions;

import java.util.List;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part3.programs.SourceLocation;

public class LiteralPositionExpression extends CubePositionExpression {
	
	public LiteralPositionExpression(int x, int y, int z, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.setX(x);
		this.setY(y);
		this.setZ(z);
	}

	@Override
	public int[] evaluate() {
		return new int[] {this.getX(), this.getY(), this.getZ()};
	}
	
	private int x;
	private int y;
	private int z;
	
	public int getX() {
		return this.x;
	}
	
	private void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return this.y;
	}
	
	private void setY(int y) {
		this.y = y;
	}
	
	public int getZ() {
		return this.z;
	}
	
	private void setZ(int z) {
		this.z = z;
	}

}
