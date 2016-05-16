package hillbillies.positions;

import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.World;

/**
 * A class of unit positions.
 * @author Sander Mergan Thomas Vrancken
 * 
 * @version 2.3
 */
public class UnitPosition extends Position{
	
	/**
	 * Initializes this UnitPosition with the given coordinates and world.
	 * @param world
	 *				The world for this new UnitPosition.
	 * @param doubleCoordinates
	 *				The coordinates for this new UnitPosition.
	 * @effect super(world, doubleCoordinates)
	 */
	public UnitPosition(World world, double[] doubleCoordinates){
		super(world, doubleCoordinates);
	}

	/**
	 * Initializes this UnitPosition with the given coordinates and world.
	 * @param world
	 *				The world for this new unit position.
	 * @param doubleCoordinates
	 *				The coordinates for this new unit  position.
	 * @effect super(world, getCubeCenter(cubeCoordinates))
	 */
	public UnitPosition(World world, int[] cubeCoordinates) {
		super(world, cubeCoordinates);
	}
	
	/**
	 * Checks whether the given coordinates are valid coordinates for any unit.
	 * @param	coordinates
	 * 					The coordinates to check.
	 * @return	True if and only if all three coordinates are within the game world
	 * 					and the cube with these coordinates is not solid.
	 * 					|result == ( (super.canHaveAsCoordinates(coordinates))  &&
	 * 										   (getWorld().getCube(coordinates[0], coordinates[1], coordinates[2]).isPassable()) )
	 */
	// IMPORTANT: valid position for units != valid positions for world.
	// Units must stay in passable cubes!
	@Override
	@Raw
	public boolean canHaveAsCoordinates(double[] coordinates) {
		return ( (super.canHaveAsCoordinates(coordinates) )
				&& (this.getWorld().getCube(Position.getCubeCoordinates(coordinates)).isPassable()) );
	}
	
}
