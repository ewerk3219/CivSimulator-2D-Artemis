package com.sim.simStates;

import java.awt.Point;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.sim.subSystems.gui.mapMaker.MakerStates;
import com.sim.subSystems.gui.mapMaker.MapMakerGUI;
import com.sim.subSystems.renderManager.RenderManager;
import com.sim.subSystems.world.WorldManager;
import com.sim.util.mapmakertools.LayerSizeEditor;

public class MapMakerState extends BasicGameState {

	/* World Manager used within this state */
	private WorldManager wm;
	/* Render Manager */
	private RenderManager rm;
	/* Map loaded flag */
	private boolean isMapLoaded = false;
	/* Button render and input */
	private MapMakerGUI gui;
	/* Map maker state */
	private MakerStates state;

	/* For changing the size of the layerGrid when desired */
	private LayerSizeEditor layerSizeEditor;

	/* Locations of the four pins on the four corners of the grid */
	private Circle upperLeft, upperRight, lowerLeft, lowerRight;
	private Color pinColor = Color.orange;
	private boolean upperLeftDragged, upperRightDragged, lowerLeftDragged,
			lowerRightDragged;

	public static final int PIN_RADIUS = 12;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		state = MakerStates.MenuState;
		gui = new MapMakerGUI(gc.getGraphics(), gc, this);
		wm = new WorldManager();
		rm = new RenderManager(gc, gc.getGraphics(), sbg, wm);
		rm.standardUnit = 16;

		// just to initialize them, they cannot be used before a map has been
		// loaded.
		layerSizeEditor = new LayerSizeEditor(rm);
		upperLeft = new Circle(0, 0, PIN_RADIUS);
		upperRight = new Circle(0, 0, PIN_RADIUS);
		lowerLeft = new Circle(0, 0, PIN_RADIUS);
		lowerRight = new Circle(0, 0, PIN_RADIUS);
		upperLeftDragged = false;
		upperRightDragged = false;
		lowerLeftDragged = false;
		lowerRightDragged = false;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		renderBrowsers(gc, g);
		gui.renderButtons();
	}

	public void renderBrowsers(GameContainer gc, Graphics g) {

		/*
		 * Map rendering, only runs if user has chosen a map and it has been
		 * loaded in
		 */
		if (isMapLoaded) {
			rm.render();
			g.setColor(pinColor);
			g.fill(upperLeft);
			g.fill(upperRight);
			g.fill(lowerLeft);
			g.fill(lowerRight);
			gui.renderAesthetics();
			gui.renderButtons();
			gui.renderInput();
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		checkKeys(gc);
		updateSizeEditorPins();
	}

	private void checkKeys(GameContainer gc) {
		if (gc.getInput().isKeyDown(Keyboard.KEY_EQUALS)) {
			rm.zoomIn();
		}
		if (gc.getInput().isKeyDown(Keyboard.KEY_MINUS)) {
			rm.zoomOut();
		}
		if (gc.getInput().isKeyDown(Keyboard.KEY_UP)) {
			rm.goUp();
		}
		if (gc.getInput().isKeyDown(Keyboard.KEY_DOWN)) {
			rm.goDown();
		}
		if (gc.getInput().isKeyDown(Keyboard.KEY_LEFT)) {
			rm.goLeft();
		}
		if (gc.getInput().isKeyDown(Keyboard.KEY_RIGHT)) {
			rm.goRight();
		}
		if (gc.getInput().isKeyPressed(Keyboard.KEY_BACK)) {
			rm.resetZoom();
		}
		if (gc.getInput().isKeyPressed(Keyboard.KEY_9)) {
			rm.increaseCameraSpeed();
		}
		if (gc.getInput().isKeyPressed(Keyboard.KEY_8)) {
			rm.decreaseCameraSpeed();
		}

		layerSizeEditorKeyCheck(gc);
	}

	private void layerSizeEditorKeyCheck(GameContainer gc) {

		// Check for undo function
		if (gc.getInput().isKeyDown(Keyboard.KEY_LCONTROL)
				&& gc.getInput().isKeyPressed(Keyboard.KEY_Z)) {
			layerSizeEditor.undo(wm.getArea().getCurrentLayerIndex(),
					wm.getArea().getCurrentLayer());
		}

		// Layer Editor Pin Dragging
		if (upperLeftDragged || upperRightDragged || lowerLeftDragged
				|| lowerRightDragged) {
			int mouseX = gc.getInput().getMouseX();
			int mouseY = gc.getInput().getMouseY();
			// Record new pin location
			if (upperLeftDragged) {
				layerSizeEditor.updateUpperLeft(mouseX, mouseY);
			} else if (upperRightDragged) {
				layerSizeEditor.updateUpperRight(mouseX, mouseY);
			} else if (lowerLeftDragged) {
				layerSizeEditor.updateLowerLeft(mouseX, mouseY);
			} else {
				layerSizeEditor.updateLowerRight(mouseX, mouseY);
			}
			// Refresh the pin locations here in the MapMakerState
			updateSizeEditorPins();

			if (!gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				// Make sure they're all off now
				upperLeftDragged = false;
				upperRightDragged = false;
				lowerLeftDragged = false;
				lowerRightDragged = false;
			}
		} else if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			int mouseX = gc.getInput().getMouseX();
			int mouseY = gc.getInput().getMouseY();
			int gridX = rm.renderXToGridX(mouseX);
			int gridY = rm.renderYToGridY(mouseY);
			System.out.println(
					"Mouse Point in grid Coords   : (" + gridX + ", " + gridY + ")");
			System.out.println(
					"Mouse Point in render Coords : (" + mouseX + ", " + mouseY + ")");

			// If a pin has not been dragged then record which pin
			if (upperLeft.contains(gc.getInput().getMouseX(),
					gc.getInput().getMouseY())) {
				upperLeftDragged = true;
			} else if (upperRight.contains(gc.getInput().getMouseX(),
					gc.getInput().getMouseY())) {
				upperRightDragged = true;
			} else if (lowerLeft.contains(gc.getInput().getMouseX(),
					gc.getInput().getMouseY())) {
				lowerLeftDragged = true;
			} else if (lowerRight.contains(gc.getInput().getMouseX(),
					gc.getInput().getMouseY())) {
				lowerRightDragged = true;
			}
		}
	}

	public void createNewMap(int width, int height) {
		System.out.println("Initializing new world\n\t\tStandby...");
		wm.createNewWorld(width, height);
		isMapLoaded = true;
		updateSizeEditorPins();
		// Set up layerSizeEditor
		layerSizeEditor.switchLayer(wm.getArea().getCurrentLayer());

		System.out.println("Finished");
	}

	private void updateSizeEditorPins() {
		Point[] corners = layerSizeEditor.gridSizePins();
		int renderXPin = rm.gridXToRenderCoordX(corners[0].x);
		int renderYPin = rm.gridYToRenderCoordY(corners[0].y);
		// System.out.println("(" + renderXPin + ", " + renderYPin + ")");

		upperLeft.setCenterX(renderXPin);
		upperLeft.setCenterY(renderYPin);
		renderXPin = rm.gridXToRenderCoordX(corners[1].x);
		renderYPin = rm.gridYToRenderCoordY(corners[1].y);
		// System.out.println("(" + renderXPin + ", " + renderYPin + ")");

		upperRight.setCenterX(renderXPin);
		upperRight.setCenterY(renderYPin);
		renderXPin = rm.gridXToRenderCoordX(corners[2].x);
		renderYPin = rm.gridYToRenderCoordY(corners[2].y);
		// System.out.println("(" + renderXPin + ", " + renderYPin + ")");

		lowerLeft.setCenterX(renderXPin);
		lowerLeft.setCenterY(renderYPin);
		renderXPin = rm.gridXToRenderCoordX(corners[3].x);
		renderYPin = rm.gridYToRenderCoordY(corners[3].y);
		System.out.println("(" + corners[3].x + ", " + corners[3].y + ")");
		// System.out.println("(" + renderXPin + ", " + renderYPin + ")");

		lowerRight.setCenterX(renderXPin);
		lowerRight.setCenterY(renderYPin);
	}

	public MakerStates getState() {
		return this.state;
	}

	public void setState(MakerStates state) {
		gui.switchState(state);
		this.state = state;
	}

	public void submitLayerSizeForNewMapSize() {
		layerSizeEditor.setLayerToSize(wm.getArea().getCurrentLayer(),
				wm.getArea().getCurrentLayerIndex());
	}

	@Override
	public int getID() {
		return 4;
	}
}
