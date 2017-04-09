package com.sim.subSystems.world;

import java.util.ArrayList;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

public class Area {

	private ArrayList<Layer> layerContainer;
	private int currentLayer; // Defines what layer will be rendered

	/*
	 * Constructs an empty area. CANNOT BE USED UNTIL LAYERS ARE ADDED
	 */
	public Area() {
		// TODO make this constructor immediately ask for parameters so it can
		// Initialize with atleast one layer
		this.layerContainer = new ArrayList<Layer>();
		currentLayer = 0;
	}

	/*
	 * Renders the layer grid based on the current layer this Area is focused
	 * on.
	 */
	public void render(Graphics g) {
		this.layerContainer.get(currentLayer).renderLayer(g);
	}

	/*
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

	/*
	 * Adds a layer with a specified width and height. Requires a SpriteSheet
	 * which will be used as the terrain textures.
	 */
	public void addLayer(int width, int height) {
		this.layerContainer.add(new Layer(width, height));
	}

	/*
	 * Adds the layer from the parameters to the existing Area.
	 */
	public void addLayer(Layer layer) {
		this.layerContainer.add(layer);
	}

	/*
	 * Returns a pointer to the ArrayList<Layer> which holds each individual
	 * Layer.
	 */
	public ArrayList<Layer> getLayerContainer() {
		return this.layerContainer;
	}

	/*
	 * Returns the total number of layers contained within this Area.
	 */
	public int getTotalLayers() {
		return this.layerContainer.size();
	}

	/*
	 * Returns whether or not Area has no layers.
	 */
	public boolean isEmpty() {
		return getTotalLayers() == 0;
	}

	/*
	 * Initializes each layer contained within this Area to its default state.
	 */
	public void testInitAllLayers() {
		for (Layer layer : layerContainer) {
			layer.testInitTiles();
		}
	}

	public Layer getCurrentLayer() {
		return this.getLayerContainer().get(currentLayer);
	}

	/*
	 * Returns a list of tile shapes who's tile was solid. Used for rendering in
	 * debug.
	 */
	public ArrayList<Shape> getSolidShapes() {
		ArrayList<Shape> tiles = new ArrayList<Shape>();
		Layer layer = this.layerContainer.get(currentLayer);
		for (int x = 0; x < layer.getLayerGrid().length; x++) {
			for (int y = 0; y < layer.getLayerGrid()[0].length; y++) {
				Tile tile = layer.getLayerGrid()[x][y];
				if (tile.isSolid()) {
					tiles.add(tile.getShape());
				}
			}
		}
		return tiles;
	}
}
