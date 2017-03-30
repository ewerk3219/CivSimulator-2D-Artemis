package com.sim.subSystems.world;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import com.sim.simStates.SimulationState;

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
			}
		}
		g.setColor(Color.white);
	}

	/*
	 * Returns a tile frome the grid at the locations x and y
	 */
	public Tile getTile(int x, int y) {
		return grid[x][y];
	}

	public Tile[][] getLayerGrid() {
		return this.grid;
	}
}
