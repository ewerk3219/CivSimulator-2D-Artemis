package com.sim.util.mapmakertools;

import java.awt.Point;
import java.util.Stack;

import com.sim.subSystems.renderManager.RenderManager;
import com.sim.subSystems.world.Layer;
import com.sim.subSystems.world.Tile;

public class LayerSizeEditor {

	/* RenderManager this LayerSizeEditor is to work on */
	private final RenderManager rm;
	/** Location of the draggable pin in GRID COORDINATES */
	private Point upperLeft, lowerRight;
	/** Data needed to perform an undo, holds the old layer arrays. */
	private Stack<PreviousLayer> undoLayerStack;

	/** Maximum number of actions that have been saved and can be undone */
	public static final int MAX_UNDO = 15;

	/**
	 * To keep tract of old layers and where they lie within the current Area
	 * array
	 */
	private class PreviousLayer {
		public int indexOfLayer;
		public Tile[][] oldGrid;
	}

	public LayerSizeEditor(RenderManager rm) {
		upperLeft = new Point(0, 0);
		lowerRight = new Point(0, 0);
		this.rm = rm;
		undoLayerStack = new Stack<PreviousLayer>();
	}

	/**
	 * Constructs a LayerSizeEditor
	 * 
	 * @param currentLayer
	 *            Current layer in which to edit size upon.
	 */
	public LayerSizeEditor(RenderManager rm, Layer currentLayer) {
		this(rm);
		resetCornerPoints(currentLayer);
	}

	private void resetCornerPoints(Layer currentLayer) {
		int width = currentLayer.getLayerGrid().length;
		int height = currentLayer.getLayerGrid()[0].length;

		upperLeft.x = 0;
		upperLeft.y = 0;

		lowerRight.x = width;
		lowerRight.y = height;
	}

	/**
	 * Call when switching to work on a different layer, allows this
	 * LayerSizeEditor to update to the new grid.
	 * 
	 * @param newLayer
	 *            The layer that is to be worked on.
	 */
	public void switchLayer(Layer newLayer) {
		resetCornerPoints(newLayer);
	}

	/**
	 * 
	 * @param x
	 *            render coord x
	 * @param y
	 *            render coord y
	 */
	public void updateUpperLeft(int x, int y) {
		x = rm.renderXToGridX(x);
		y = rm.renderYToGridY(y);
		// Check overlapping
		if (x >= lowerRight.x) {
			x = lowerRight.x - 1;
		}
		if (y >= lowerRight.y) {
			y = lowerRight.y - 1;
		}
		this.upperLeft.x = x;
		this.upperLeft.y = y;
	}

	/**
	 * 
	 * @param x
	 *            render coord x
	 * @param y
	 *            render coord y
	 */
	public void updateUpperRight(int x, int y) {
		x = rm.renderXToGridX(x);
		y = rm.renderYToGridY(y);
		// Check overlapping
		if (x <= upperLeft.x) {
			x = upperLeft.x + 1;
		}
		if (y >= lowerRight.y) {
			y = lowerRight.y - 1;
		}
		this.lowerRight.x = x;
		this.upperLeft.y = y;
	}

	/**
	 * 
	 * @param x
	 *            render coord x
	 * @param y
	 *            render coord y
	 */
	public void updateLowerLeft(int x, int y) {
		x = rm.renderXToGridX(x);
		y = rm.renderYToGridY(y);
		// Check overlapping
		if (x >= lowerRight.x) {
			x = lowerRight.x - 1;
		}
		if (y <= upperLeft.y) {
			y = upperLeft.y + 1;
		}
		this.upperLeft.x = x;
		this.lowerRight.y = y;
	}

	/**
	 * 
	 * @param x
	 *            render coord x
	 * @param y
	 *            render coord y
	 */
	public void updateLowerRight(int x, int y) {
		x = rm.renderXToGridX(x);
		y = rm.renderYToGridY(y);
		// Check overlapping
		if (x <= upperLeft.x) {
			x = upperLeft.x + 1;
		}
		if (y <= upperLeft.y) {
			y = upperLeft.y + 1;
		}
		this.lowerRight.x = x;
		this.lowerRight.y = y;
	}

	/**
	 * Alters the given layer to mirror the new size of the current pin
	 * locations. Also remembers the old grid in case a undo operation is
	 * required, but only to a maximum of undo operations and will remove the
	 * oldest change if the maximum is passed.
	 * 
	 * @param currentLayer
	 *            The layer to be altered
	 * @param currentLayerIndex
	 *            The array index from the Area class for the given layer
	 * 
	 * @throws RuntimeException
	 *             If pins are on top of each other, then exception is thrown
	 */
	public void setLayerToSize(Layer currentLayer, int currentLayerIndex) {
		if (upperLeft.equals(lowerRight)) {
			throw new RuntimeException("The pins cannot equal each other");
		}
		PreviousLayer pl = new PreviousLayer();
		pl.indexOfLayer = currentLayerIndex;
		pl.oldGrid = currentLayer.getLayerGrid().clone();
		this.undoLayerStack.push(pl);
		if (this.undoLayerStack.size() > MAX_UNDO) {
			deleteOldestUndo();
		}

		int oldWidth = currentLayer.getLayerGrid().length;
		int oldHeight = currentLayer.getLayerGrid()[0].length;

		// May want to check if pins overlap each other here.

		int newWidth = Math.abs(upperLeft.x - lowerRight.x);
		int newHeight = Math.abs(upperLeft.y - lowerRight.y);

		Tile[][] newGrid = new Tile[newWidth][newHeight];

		// Transfer tiles still being used to the new grid
		int startX;
		int startY;
		if (upperLeft.x < 0) {
			startX = 0;
		} else {
			startX = upperLeft.x;
		}
		if (upperLeft.y < 0) {
			startY = 0;
		} else {
			startY = upperLeft.y;
		}

		int endX;
		int endY;
		if (lowerRight.x > oldWidth) {
			endX = oldWidth;
		} else {
			endX = lowerRight.x;
		}
		if (lowerRight.y > oldHeight) {
			endY = oldHeight;
		} else {
			endY = lowerRight.y;
		}

		for (int x = startX; startX < endX; startX++) {
			for (int y = startY; startY < endY; startY++) {
				newGrid[x - upperLeft.x][y - upperLeft.y] = pl.oldGrid[x][y];
			}
		}

		// Set layer to new grid and defaults new tiles
		currentLayer.setGrid(newGrid);
		currentLayer.defaultInitNullTiles();

		if (upperLeft.x != 0 || upperLeft.y != 0) {
			resetCornerPoints(currentLayer);
		}
	}

	private void deleteOldestUndo() {
		this.undoLayerStack.remove(0);
	}

	/**
	 * If a layer is deleted from within an Area, then this should be called to
	 * update this editor
	 * 
	 * @param indexOfRemovedLayer
	 *            The index of the removed layer within the Area class
	 *            ArrayList.
	 */
	public void removeUndoOfDeletedLayer(int indexOfRemovedLayer) {
		Stack<PreviousLayer> auxilaryStack = new Stack<PreviousLayer>();
		while (!this.undoLayerStack.isEmpty()) {
			PreviousLayer current = undoLayerStack.pop();
			if (current.indexOfLayer != indexOfRemovedLayer) {
				if (current.indexOfLayer > indexOfRemovedLayer) {
					current.indexOfLayer--;
				}
				auxilaryStack.push(current);
			}
		}

		while (!auxilaryStack.isEmpty()) {
			undoLayerStack.push(auxilaryStack.pop());
		}
	}

	/**
	 * Restores the given layer to the most recent version. This does not alter
	 * tile data, but does restore tiles lost do to reducing the size of the
	 * map.
	 * 
	 * @param indexOfLayer
	 *            Index of the layer from the Area class
	 * @param currentLayer
	 *            Layer that is to be restored to a previous version
	 * 
	 * @throws IllegalArgumentException
	 *             If indexOfLayer is negative (less than zero)
	 * @throws IllegalArgumentException
	 *             If currentLayer is null
	 */
	public void undo(int indexOfLayer, Layer currentLayer) {
		if (indexOfLayer < 0) {
			throw new IllegalArgumentException("Illegal layer index: " + indexOfLayer);
		}
		if (currentLayer == null) {
			throw new IllegalArgumentException(
					"currentLayer cannot be null in undo operation");
		}
		// find the most recent previous version of the given Layer
		if (!this.undoLayerStack.isEmpty()) {
			Stack<PreviousLayer> auxilaryStack = new Stack<PreviousLayer>();
			PreviousLayer pl;
			do {
				pl = this.undoLayerStack.pop();
				auxilaryStack.push(pl);
			} while (pl.indexOfLayer != indexOfLayer && !undoLayerStack.isEmpty());
			// either found it or it didn't
			if (pl.indexOfLayer == indexOfLayer) {
				// It did manage to find a previous undo for this layer
				currentLayer.setGrid(pl.oldGrid);
				auxilaryStack.pop();
			}
			// put everything back
			while (!auxilaryStack.isEmpty()) {
				undoLayerStack.push(auxilaryStack.pop());
			}
		}
	}

	/**
	 * Returns the four coordinates of the pins in grid coordinates.
	 * 
	 * @return An array of four points representing the four vertices of the
	 *         area in grid coordinates of the current size of the new array.
	 */
	public Point[] gridSizePins() {
		Point[] rectCoords = new Point[4];
		rectCoords[0] = new Point(upperLeft.x, upperLeft.y);
		rectCoords[1] = new Point(lowerRight.x, upperLeft.y);
		rectCoords[2] = new Point(upperLeft.x, lowerRight.y);
		rectCoords[3] = new Point(lowerRight.x, lowerRight.y);
		return rectCoords;
	}
}
