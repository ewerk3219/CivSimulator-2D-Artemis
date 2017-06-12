package com.sim.subSystems.world;

import java.awt.Point;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import com.artemis.Entity;
import com.sim.Direction;
import com.sim.Simulator;
import com.sim.simStates.SimulationState;
import com.sim.subSystems.entity.components.Visible;
import com.sim.subSystems.entity.util.PointTranslator;

public class Layer {

	public static final Direction[] ALL_DIRECTIONS = { Direction.NORTH,
			Direction.NORTHEAST, Direction.EAST, Direction.SOUTHEAST, Direction.SOUTH,
			Direction.SOUTHWEST, Direction.WEST, Direction.NORTHWEST };

	private Tile[][] grid;

	public Layer(int length, int width) {
		this.grid = new Tile[length][width];
	}

	/*
	 * Initializes this layer to the first sprite in the SpriteSheet and makes
	 * everything non-solid.
	 */
	public void testInitTiles() {
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {
				// grid[x][y] = new Tile(x * standardUnit, y * standardUnit,
				// false,
				// Color.gray);
				grid[x][y] = new Tile(x, y, false, Color.magenta);
			}
		}
	}

	/*
	 * Renders this Layer with displayX and displayY being the origin. (i.e. the
	 * top left corner)
	 */
	public void renderLayer(Graphics g) {
		for (int gridX = 0; gridX < grid.length; gridX++) {
			for (int gridY = 0; gridY < grid[0].length; gridY++) {
				Tile tile = grid[gridX][gridY];
				int x = gridX * SimulationState.standardUnit
						+ Simulator.simManager.simState.getRenderX();
				int y = gridY * SimulationState.standardUnit
						+ Simulator.simManager.simState.getRenderY();
				if (g.getWorldClip().contains(x, y)) {
					tile.resetTileShapeCoordinates(x, y);
					g.setColor(tile.getTerrainColor());
					g.fill(tile.getShape());
					Image entityAppearance = tile.getEntityAppearance();
					if (entityAppearance != null) {
						g.drawImage(entityAppearance, x, y);
					}
				}
			}
		}
		g.setColor(Color.white);
	}

	/*
	 * Does the same thing at the other move entity only it allows use of the
	 * Direction enumeration class.
	 * 
	 * Moves Entity 'e' to the tile location (x,y) only if the tile at (x,y) is
	 * not already occupied by an entity and the location is not a solid tile.
	 * If the designated tile at (x,y) already contains an entity then no
	 * operation is executed and false is returned indicating nothing was
	 * changed. If there is no entity at the designated tile at (x,y) then the
	 * entity will be moved to the new tile and erased from its original tile it
	 * came from and will return true indicated a successful operation.
	 */
	public boolean moveEntityTo(Entity e, Direction directionToGo) {
		Visible visible = e.getComponent(Visible.class);
		Point p = PointTranslator.getNewPoint(visible.getX(), visible.getY(),
				directionToGo);
		return moveEntityTo(e, p.x, p.y);
	}

	/*
	 * Moves Entity 'e' to the tile location (x,y) only if the tile at (x,y) is
	 * not already occupied by an entity and the location is not a solid tile.
	 * If the designated tile at (x,y) already contains an entity then no
	 * operation is executed and false is returned indicating nothing was
	 * changed. If there is no entity at the designated tile at (x,y) then the
	 * entity will be moved to the new tile and erased from its original tile it
	 * came from and will return true indicated a successful operation.
	 */
	public boolean moveEntityTo(Entity e, int x, int y) {
		Tile designatedTile = this.getTile(x, y);
		if (designatedTile.isSolid() || designatedTile.containAnEntity()) {
			return false;
		}
		Visible visible = e.getComponent(Visible.class);
		int entityXLocation;
		int entityYLocation;
		if (visible == null) {
			// If the Entity does not have a visible component
			Point entityLocation = findEntityLocation(e);
			entityXLocation = entityLocation.x;
			entityYLocation = entityLocation.y;
		} else {
			// If the Entity has a visible component
			entityXLocation = visible.getX();
			entityYLocation = visible.getY();
			visible.setX(x);
			visible.setY(y);
		}
		getTile(entityXLocation, entityYLocation).removeEntity();
		getTile(x, y).setEntity(e);
		return true;
	}

	/*
	 * Finds the Point (x, y) tile that the entity resides. Returns null if not
	 * found.
	 */
	private Point findEntityLocation(Entity e) {
		for (int x = 0; x < this.grid.length; x++) {
			for (int y = 0; y < this.grid[0].length; y++) {
				if (this.grid[x][y].hasEntity(e)) {
					return new Point(x, y);
				}
			}
		}
		return null;
	}

	public boolean isEntityInTile(Point p, Direction directionToLook) {
		return isEntityInTile(p.x, p.y, directionToLook);
	}

	public boolean isEntityInTile(int x, int y) {
		return this.getTile(x, y).containAnEntity();
	}

	public boolean isEntityInTile(int x, int y, Direction directionToLook) {
		Point tileP = PointTranslator.getNewPoint(x, y, directionToLook);
		return this.getTile(tileP.x, tileP.y).containAnEntity();
	}

	public int getTotalEntities() {
		int sum = 0;
		for (int x = 0; x < this.grid.length; x++) {
			for (int y = 0; y < this.grid[0].length; y++) {
				Tile tile = this.grid[x][y];
				if (tile.containAnEntity()) {
					sum += 1;
				}
			}
		}
		return sum;
	}

	public Tile getTile(int x, int y, Direction d) {
		Point p = PointTranslator.getNewPoint(x, y, d);
		return this.getTile(p.x, p.y);
	}

	/*
	 * Returns a tile from the grid at the locations x and y. If the location is
	 * out of the array boundaries, then null will be returned.
	 */
	public Tile getTile(int x, int y) {
		if (x >= this.grid.length || x < 0 || y >= this.grid[0].length || y < 0) {
			return null;
		} else
			return grid[x][y];
	}

	public Tile[][] getLayerGrid() {
		return this.grid;
	}

	/*
	 * Returns a random tile adjacent to the given one at the grid (x,y)
	 * position
	 */
	public Tile getRandomAdjacentTile(Layer grassField, int x, int y) {
		for (Direction direction : ALL_DIRECTIONS) {
			Tile tile = grassField.getTile(x, y, direction);
			if (tile != null)
				return tile;
		}
		return null;
	}

	public double getMaxHeight() {
		double max = -1000;
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {
				Tile tile = this.getTile(x, y);
				if (tile.getHeight() > max) {
					max = tile.getHeight();
				}
			}
		}
		return max;
	}

	public double getMinHeight() {
		double min = 1000;
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {
				Tile tile = this.getTile(x, y);
				if (tile.getHeight() < min) {
					min = tile.getHeight();
				}
			}
		}
		return min;
	}
}
