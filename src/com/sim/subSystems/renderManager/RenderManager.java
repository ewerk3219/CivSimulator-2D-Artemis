package com.sim.subSystems.renderManager;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import com.sim.simStates.SimulationState;
import com.sim.subSystems.world.Area;
import com.sim.subSystems.world.WorldManager;

public class RenderManager {

	public static final int DEFAULT_STANDARD_UNIT = 32;
	public int standardUnit = 8;

	private final GameContainer gc;
	private final Graphics g;
	private final StateBasedGame sbg;
	private final SimulationState simState;

	private int renderX;
	private int renderY;

	private int renderDeltaX; // used for updating entity positions
	private int renderDeltaY;
	private int cameraMoveSpeed;

	private float scale;

	public RenderManager(GameContainer gc, Graphics g, StateBasedGame sbg,
			SimulationState simState) {
		this.gc = gc;
		this.g = g;
		this.sbg = sbg;
		this.simState = simState;
		scale = 1f;
		renderX = 0;
		renderY = 0;
		renderDeltaX = 0;
		renderDeltaY = 0;
		cameraMoveSpeed = standardUnit / 8;
	}

	public void resetWorldClip(GameContainer container) {
		container.getGraphics()
				.setWorldClip(new Rectangle(-standardUnit, -standardUnit,
						container.getWidth() + standardUnit,
						container.getHeight() + standardUnit));
	}

	public void updateDeltasAndCheckKeys(WorldManager worldManager) {
		int oldX = this.renderX;
		int oldY = this.renderY;
		checkKeys(worldManager);
		this.renderDeltaX = this.renderX - oldX;
		this.renderDeltaY = this.renderY - oldY;
	}

	public void render(Area specificArea) {
		g.pushTransform();
		g.setBackground(new Color(0, 0, 0)); // clear background
		renderGrid(100);
		g.scale(scale, scale);
		renderBlock(specificArea);
		// renderEntityShape(g);
		g.popTransform();
		diplaySystemInformation(g, 10, 30);
	}

	/**
	 * Alternate method of rendering an area. Optimized version of the first
	 * rendering method.
	 */
	private void renderBlock(Area specificArea) {
		int startX = Area.renderXToGridX(0);
		if (startX < 0) { // bounding within array
			startX = 0;
		}
		int startY = Area.renderYToGridY(0);
		if (startY < 0) { // bounding within array
			startY = 0;
		}
		int endX = Area.renderXToGridX(gc.getWidth()) + 1;
		if (endX >= specificArea.getGridWidth()) {
			// bounding within array
			endX = specificArea.getGridWidth();
		}
		int endY = Area.renderYToGridY(gc.getHeight()) + 1;
		if (endY >= specificArea.getGridHeight()) {
			// bounding within array
			endY = specificArea.getGridHeight();
		}
		specificArea.renderBlock(g, standardUnit, startX, startY, endX, endY);

	}

	private void renderGrid(int size) {
		g.pushTransform();
		g.scale(1.0f, 1.0f);
		int resolution = gc.getScreenWidth();
		for (int i = 0; i < resolution; i += size) {
			g.drawLine(i, 0, i, resolution);
			g.drawLine(0, i, resolution, i);
		}
		g.popTransform();
	}

	public static final Rectangle DEBUG_RECTANGLE = new Rectangle(5, 5, 240,
			110);
	public static final float INFORMATION_DISPLAY_SCALE = 1f;

	private void diplaySystemInformation(Graphics g, int x, int y) {
		g.setColor(Color.black);
		g.fill(DEBUG_RECTANGLE);
		g.setColor(Color.gray);
		g.draw(DEBUG_RECTANGLE);
		g.setColor(Color.white);
		g.scale(INFORMATION_DISPLAY_SCALE, INFORMATION_DISPLAY_SCALE);
		g.drawString("LWJGL Version: " + Sys.getVersion(), x, y);
		long elapsedTime = (simState.getCurrentTime() - simState.getStartTime())
				/ 1000;
		g.drawString("Elapsed Time: " + elapsedTime + " seconds", x, y + 20);
		g.drawString("Current Tic: " + simState.getCurrentTic(), x, y + 40);
		g.drawString("Current Standard Unit: " + standardUnit, x, y + 60);
		g.scale(scale, scale);
	}

	private void checkKeys(WorldManager worldManager) {
		if (gc.getInput().isKeyPressed(Keyboard.KEY_1)) {
			sbg.enterState(1);
		}
		if (gc.getInput().isKeyPressed(Keyboard.KEY_2)) {
			sbg.enterState(2);
		}
		if (gc.getInput().isKeyPressed(Keyboard.KEY_3)) {
			sbg.enterState(3);
		}
		if (gc.getInput().isKeyDown(Keyboard.KEY_EQUALS)) {
			standardUnit++;
			if (standardUnit > 32) {
				standardUnit = 32;
			}
			resetWorldClip(gc);
		}
		if (gc.getInput().isKeyDown(Keyboard.KEY_MINUS)) {
			standardUnit--;
			if (standardUnit < 1) {
				standardUnit = 1;
			}
			resetWorldClip(gc);
		}
		if (gc.getInput().isKeyDown(Keyboard.KEY_UP)) {
			this.renderY += cameraMoveSpeed;
		}
		if (gc.getInput().isKeyDown(Keyboard.KEY_DOWN)) {
			this.renderY -= cameraMoveSpeed;
		}
		if (gc.getInput().isKeyDown(Keyboard.KEY_LEFT)) {
			this.renderX += cameraMoveSpeed;
		}
		if (gc.getInput().isKeyDown(Keyboard.KEY_RIGHT)) {
			this.renderX -= cameraMoveSpeed;
		}
		if (gc.getInput().isKeyPressed(Keyboard.KEY_M)) {
			System.out.println(
					"Test Mind AI set to: " + worldManager.toggleMindOnOff());
		}
		if (gc.getInput().isKeyPressed(Keyboard.KEY_BACK)) {
			standardUnit = 32;
		}
		if (gc.getInput().isKeyPressed(Keyboard.KEY_9)) {
			this.cameraMoveSpeed++;
		}
		if (gc.getInput().isKeyPressed(Keyboard.KEY_8)) {
			this.cameraMoveSpeed--;
		}
		if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			int mouseX = gc.getInput().getMouseX();
			int mouseY = gc.getInput().getMouseY();
			int gridX = Area.renderXToGridX(mouseX);
			int gridY = Area.renderYToGridY(mouseY);
			System.out.println("Mouse Point in grid Coords   : (" + gridX + ", "
					+ gridY + ")");
			System.out.println("Mouse Point in render Coords : (" + mouseX
					+ ", " + mouseY + ")");

		}
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
}
