package com.sim.subSystems.world;

import java.awt.Point;
import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import com.artemis.Entity;
import com.sim.Direction;
import com.sim.Simulator;
import com.sim.subSystems.renderManager.RenderManager;

public class Area {

	private ArrayList<Layer> layerContainer;
	private int currentLayer; // Defines what layer will be rendered

	/**
	 * Constructs an empty area. CANNOT BE USED UNTIL LAYERS ARE ADDED
	 */
	public Area() {
		// TODO make this constructor immediately ask for parameters so it can
		// Initialize with at least one layer
		this.layerContainer = new ArrayList<Layer>();
		currentLayer = 0;
	}

	/**
	 * Initializes the area with the given layer
	 * 
	 * @param layer
	 *            The initial layer.
	 */
	public Area(Layer layer) {
		this();
		layerContainer.add(layer);
	}

	/**
	 * Renders the current layer of this Area
	 * 
	 * 
	 * @param g
	 *            Graphics object to draw on.
	 * @param standardUnit
	 *            A standardized length or magnitude for drawing.
	 * @param startX
	 *            Left most x coordinate in view.
	 * @param startY
	 *            topmost y coordinate in view.
	 * @param endX
	 *            right most x coordinate in view.
	 * @param endY
	 *            bottom most y coordinate in view.
	 */
	public void renderBlock(Graphics g, RenderManager rm, int standardUnit, int startX,
			int startY, int endX, int endY) {
		this.layerContainer.get(currentLayer).renderBlock(g, rm, standardUnit, startX,
				startY, endX, endY);
	}

	/**
	 * Pre: layer must be one less than the total number of layers AND be
	 * greater than or equal to 0 (throws an IllegalArgumentException
	 * otherwise).
	 * 
	 * Post: Sets this Area's current layer to the parameter layer.
	 */
	public void setLayer(int layer) throws IllegalArgumentException {
		if (layer > getTotalLayers() - 1 || layer < 0) {
			throw new IllegalArgumentException("Bad layer index: " + layer);
		}
		this.currentLayer = layer;
	}

	/**
	 * Adds a layer with a specified width and height. Requires a SpriteSheet
	 * which will be used as the terrain textures.
	 */
	public void addLayer(int width, int height) {
		this.layerContainer.add(new Layer(width, height));
	}

	/**
	 * Adds the layer from the parameters to the existing Area.
	 */
	public void addLayer(Layer layer) {
		this.layerContainer.add(layer);
	}

	/**
	 * Returns a pointer to the ArrayList<Layer> which holds each individual
	 * Layer.
	 */
	public ArrayList<Layer> getLayerContainer() {
		return this.layerContainer;
	}

	/**
	 * Returns the total number of layers contained within this Area.
	 */
	public int getTotalLayers() {
		return this.layerContainer.size();
	}

	/**
	 * Returns whether or not Area has no layers.
	 */
	public boolean isEmpty() {
		return getTotalLayers() == 0;
	}

	/**
	 * Initializes each layer contained within this Area to its default state.
	 */
	public void testInitAllLayers() {
		for (Layer layer : layerContainer) {
			layer.defaultInitNullTiles();
		}
	}

	public Layer getCurrentLayer() {
		return this.getLayerContainer().get(currentLayer);
	}

	/**
	 * Returns a list of tile shapes who's tile was solid. Used for rendering in
	 * debug.
	 */
	public ArrayList<Tile> getSolidShapes() {
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		Layer layer = this.layerContainer.get(currentLayer);
		for (int x = 0; x < layer.getLayerGrid().length; x++) {
			for (int y = 0; y < layer.getLayerGrid()[0].length; y++) {
				Tile tile = layer.getLayerGrid()[x][y];
				if (tile.isSolid()) {
					tiles.add(tile);
				}
			}
		}
		return tiles;
	}

	public void setCurrentLayer(int layer) {
		this.currentLayer = layer;
	}

	public int getCurrentLayerIndex() {
		return this.currentLayer;
	}

	public Tile getTile(int x, int y) {
		return this.getCurrentLayer().getTile(x, y);
	}

	public Tile getTile(int x, int y, Direction d) {
		return this.getCurrentLayer().getTile(x, y, d);
	}

	public int getGridWidth() {
		return this.getCurrentLayer().getLayerGrid().length;
	}

	public int getGridHeight() {
		return this.getCurrentLayer().getLayerGrid()[0].length;
	}

	public boolean isEntityInTile(Point p, Direction directionToLook) {
		return this.getCurrentLayer().isEntityInTile(p, directionToLook);
	}

	public boolean isEntityInTile(int x, int y) {
		return this.getCurrentLayer().isEntityInTile(x, y);
	}

	public boolean isEntityInTile(int x, int y, Direction directionToLook) {
		return this.getCurrentLayer().isEntityInTile(x, y, directionToLook);
	}

	public void moveEntityTo(Entity e, Direction directionToGo) {
		this.getCurrentLayer().moveEntityTo(e, directionToGo);
	}

	public void moveEntityTo(Entity e, int x, int y) {
		this.getCurrentLayer().moveEntityTo(e, x, y);
	}

	public void prepForDeletion() {
		for (Layer layer : layerContainer) {
			layer.deleteAllEntities();
		}
	}
}