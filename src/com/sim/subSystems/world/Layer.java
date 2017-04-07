package com.sim.subSystems.world;

import java.awt.Point;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import com.artemis.Entity;
import com.sim.Simulator;
import com.sim.simStates.SimulationState;
import com.sim.subSystems.entity.components.Visible;

public class Layer {

	private Tile[][] grid;

	public Layer(int length, int width) {
		this.grid = new Tile[length][width];
	}

	/*
	 * Initializes this layer to the first sprite in the SpriteSheet and makes
	 * everything non-solid.
	 */
	public void testInitTiles() {
		int standardUnit = SimulationState.STANDARD_UNIT;
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {
				grid[x][y] = new Tile(x * standardUnit, y * standardUnit, false,
						Color.gray);
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
				tile.resetTileShapeCoordinates();
				g.setColor(tile.getTerrainColor());
				g.fill(tile.getShape());
				Image entityAppearance = tile.getEntityAppearance();
				if (entityAppearance != null) {
					// may need to fix scaling
					g.drawImage(entityAppearance,
							gridX * SimulationState.STANDARD_UNIT
									+ Simulator.simManager.simState.getRenderX(),
							gridY * SimulationState.STANDARD_UNIT
									+ Simulator.simManager.simState.getRenderY());
				}
			}
		}
		g.setColor(Color.white);
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

	/*
	 * Returns a tile from the grid at the locations x and y
	 */
	public Tile getTile(int x, int y) {
		return grid[x][y];
	}

	public Tile[][] getLayerGrid() {
		return this.grid;
	}
}
