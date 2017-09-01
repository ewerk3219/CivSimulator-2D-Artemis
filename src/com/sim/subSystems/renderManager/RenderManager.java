package com.sim.subSystems.renderManager;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import com.sim.Simulator;
import com.sim.simStates.SimulationState;
import com.sim.subSystems.gui.mapMaker.MapMakerGUI;
import com.sim.subSystems.world.Area;
import com.sim.subSystems.world.WorldManager;

public class RenderManager {

	public static final int DEFAULT_STANDARD_UNIT = 32;
	public int standardUnit = 8;

	private final GameContainer gc;
	private final Graphics g;
	private final StateBasedGame sbg;
	private final WorldManager wm;
	
	private int renderX;
	private int renderY;

	private int renderDeltaX; // used for updating entity positions
	private int renderDeltaY;
	private int cameraMoveSpeed;

	private float scale;

	public RenderManager(GameContainer gc, Graphics g, StateBasedGame sbg,
			WorldManager wm) {
		this.gc = gc;
		this.g = g;
		this.sbg = sbg;
		this.wm = wm;
		scale = 1f;
		renderX = 0;
		renderY = 0;
		renderDeltaX = 0;
		renderDeltaY = 0;
		cameraMoveSpeed = standardUnit / 8;
	}

	public void updateDeltas(WorldManager worldManager) {
		int oldX = this.renderX;
		int oldY = this.renderY;
		this.renderDeltaX = this.renderX - oldX;
		this.renderDeltaY = this.renderY - oldY;
	}

	public void render() {
		g.pushTransform();
		g.setBackground(new Color(0, 0, 0)); // clear background
		g.setColor(Color.gray);
		// renderGrid(100);
		g.scale(scale, scale);
		renderBlock();
		// renderEntityShape(g);
		renderGrid(standardUnit, true);
		g.popTransform();
	}

	/**
	 * Alternate method of rendering an area. Optimized version of the first
	 * rendering method.
	 */
	private void renderBlock() {
		int startX = renderXToGridX(0);
		if (startX < 0) { // bounding within array
			startX = 0;
		}
		int startY = renderYToGridY(0);
		if (startY < 0) { // bounding within array
			startY = 0;
		}
		int endX = renderXToGridX(gc.getWidth()) + 1;
		if (endX > wm.getGridWidth()) {
			// bounding within array
			endX = wm.getGridWidth();
		}
		int endY = renderYToGridY(gc.getHeight()) + 1;
		if (endY > wm.getGridHeight()) {
			// bounding within array
			endY = wm.getGridHeight();
		}
		wm.renderBlock(g, this, standardUnit, startX, startY, endX, endY);

	}

	/**
	 * Renders a basic grid line on screen.
	 * 
	 * @param sizeIncrement
	 *            How spaced out the grid is.
	 * @param followRenderXY
	 *            True if grid moves with map, false if stationary.
	 */
	private void renderGrid(int sizeIncrement, boolean followRenderXY) {
		int initX = 0;
		int initY = 0;
		int borderX = gc.getWidth();
		int borderY = gc.getHeight();
		if (followRenderXY) {
			initX = renderX;
			initY = renderY;
			borderX += initX;
			borderY += initY;
		}
		g.setColor(Color.darkGray);
		for (int x = initX; x < gc.getWidth() + initX; x += sizeIncrement) {
			g.drawLine(x, 0, x, borderY);
			for (int y = initY; y < gc.getHeight() + initY; y += sizeIncrement) {
				g.drawLine(0, y, borderX, y);
			}
		}
	}

	public void zoomIn() {
		standardUnit++;
		if (standardUnit > 32) {
			standardUnit = 32;
		}
	}

	public void zoomOut() {
		standardUnit--;
		if (standardUnit < 1) {
			standardUnit = 1;
		}
	}

	public void resetZoom() {
		this.standardUnit = 32;
	}

	public void goLeft() {
		this.renderX += this.cameraMoveSpeed;
	}

	public void goRight() {
		this.renderX -= this.cameraMoveSpeed;
	}

	public void goUp() {
		this.renderY += this.cameraMoveSpeed;
	}

	public void goDown() {
		this.renderY -= this.cameraMoveSpeed;
	}

	public void increaseCameraSpeed() {
		this.cameraMoveSpeed++;
	}

	public void decreaseCameraSpeed() {
		this.cameraMoveSpeed--;
	}

	public int getRenderX() {
		return this.renderX;
	}

	public int getRenderY() {
		return this.renderY;
	}

	public int getDeltaX() {
		return this.renderDeltaX;
	}

	public int getDeltaY() {
		return this.renderDeltaY;
	}

	// --------------- Statics --------------- \\

	/**
	 * Translates grid-X coordinate to renderCoordinate-X for tile grid only.
	 * 
	 * @param gridX
	 *            x-axis coordinate value.
	 */
	public int gridXToRenderCoordX(int gridX) {
		return gridX * standardUnit + this.renderX;
	}

	/**
	 * Translates grid-Y coordinate to renderCoordinate-Y for tile grid only.
	 * 
	 * @param gridY
	 *            y-axis coordinate value.
	 */
	public int gridYToRenderCoordY(int gridY) {
		return gridY * standardUnit + this.renderY;
	}

	/**
	 * Translates render-X coordinate to grid-X coordinate for tile grid only.
	 * 
	 * @param renderX
	 *            x-axis coordinate value for rendering.
	 */
	public int renderXToGridX(int renderX) {
		return (renderX - this.renderX) / standardUnit;
	}

	/**
	 * Translates render-Y coordinate to grid-Y coordinate for tile grid only.
	 * 
	 * @param renderY
	 *            y-axis coordinate value for rendering.
	 */
	public int renderYToGridY(int renderY) {
		return (renderY - this.renderY) / standardUnit;
	}
}
