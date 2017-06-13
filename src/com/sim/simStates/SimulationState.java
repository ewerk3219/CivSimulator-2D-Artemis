package com.sim.simStates;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.sim.Simulator;
import com.sim.subSystems.world.Area;
import com.sim.subSystems.world.WorldManager;

public class SimulationState extends BasicGameState {

	public static final int DEFAULT_STANDARD_UNIT = 32;
	public static int standardUnit = 8;

	public static final long FPS_TIME_TO_SLEEP = 28;
	public static final float INFORMATION_DISPLAY_SCALE = 1f;
	private long startTime = Sys.getTime();
	private long currentTime = Sys.getTime();
	private long currentTic;

	private int renderX;
	private int renderY;

	private int renderDeltaX; // used for updating entity positions
	private int renderDeltaY;
	private int cameraMoveSpeed = standardUnit / 8;

	private float scale;
	private WorldManager worldManager;

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		System.out.println("Initialization...");
		System.out.println();
		scale = 1f;
		renderX = 0;
		renderY = 0;
		renderDeltaX = 0;
		renderDeltaY = 0;
		resetWorldClip(container);
		// Artemis setup
		initWorldManager();
		initSimMap();
		// initEntityTest();
		// -------
		System.out.println("Initialization complete");
	}

	private void resetWorldClip(GameContainer container) {
		container.getGraphics()
				.setWorldClip(new Rectangle(-SimulationState.standardUnit,
						-SimulationState.standardUnit,
						container.getWidth() + SimulationState.standardUnit,
						container.getHeight() + SimulationState.standardUnit));
	}

	private void initEntityTest() {
		for (int i = 0; i < 100; i++) {
			// parameters are test numbers! They don't actually do anything
			this.worldManager.addEntity(1, 1);
		}
	}

	private void initWorldManager() {
		this.worldManager = new WorldManager("testMap");
		this.worldManager.generateNewLayer();
		this.worldManager.setCurrentLayer(1);
	}

	private void initSimMap() {
		Simulator.simManager.initSimMap();
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		updateDeltasAndCheckKeys(container, game);
		this.worldManager.process(delta);
		updateSimTimingAndSleep(delta);
		debugInfo();
	}

	private void debugInfo() {
		// System.out.println("RenderX: " + this.renderX);
		// System.out.println("RenderY: " + this.renderY);
		// System.out.println("Total Entities: " + this.worldManager.getArea()
		// .getLayerContainer().get(0).getTotalEntities());
		// System.out.println("screen width: " + container.getScreenWidth() + ",
		// width: " + container.getWidth());
	}

	private void updateDeltasAndCheckKeys(GameContainer container,
			StateBasedGame game) {
		int oldX = this.renderX;
		int oldY = this.renderY;
		checkKeys(container, game);
		this.renderDeltaX = this.renderX - oldX;
		this.renderDeltaY = this.renderY - oldY;
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

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		g.pushTransform();
		g.setBackground(new Color(0, 0, 0)); // clear background
		renderGrid(container, g, 100);
		g.scale(scale, scale);
		this.worldManager.getArea().render(g);
		// renderEntityShape(g);
		g.popTransform();
		diplaySystemInformation(g, 10, 30);
	}

	private void renderGrid(GameContainer container, Graphics g, int size) {
		g.pushTransform();
		g.scale(1.0f, 1.0f);
		int resolution = container.getScreenWidth();
		for (int i = 0; i < resolution; i += size) {
			g.drawLine(i, 0, i, resolution);
			g.drawLine(0, i, resolution, i);
		}
		g.popTransform();
	}

	private void updateSimTimingAndSleep(int delta) {
		currentTime = Sys.getTime();
		if (delta < FPS_TIME_TO_SLEEP) {
			long timeToSleep = FPS_TIME_TO_SLEEP - delta;
			try {
				Thread.sleep(timeToSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.currentTic++;
	}

	private void checkKeys(GameContainer container, StateBasedGame game) {
		if (container.getInput().isKeyPressed(Keyboard.KEY_ESCAPE)) {
			game.enterState(1);
		}
		if (container.getInput().isKeyDown(Keyboard.KEY_EQUALS)) {
			standardUnit++;
			if (standardUnit > 32) {
				standardUnit = 32;
			}
			resetWorldClip(container);
		}
		if (container.getInput().isKeyDown(Keyboard.KEY_MINUS)) {
			standardUnit--;
			if (standardUnit < 1) {
				standardUnit = 1;
			}
			resetWorldClip(container);
		}
		if (container.getInput().isKeyDown(Keyboard.KEY_UP)) {
			this.renderY += cameraMoveSpeed;
		}
		if (container.getInput().isKeyDown(Keyboard.KEY_DOWN)) {
			this.renderY -= cameraMoveSpeed;
		}
		if (container.getInput().isKeyDown(Keyboard.KEY_LEFT)) {
			this.renderX += cameraMoveSpeed;
		}
		if (container.getInput().isKeyDown(Keyboard.KEY_RIGHT)) {
			this.renderX -= cameraMoveSpeed;
		}
		if (container.getInput().isKeyPressed(Keyboard.KEY_M)) {
			System.out.println(
					"Test Mind AI set to: " + this.worldManager.toggleMindOnOff());
		}
		if (container.getInput().isKeyPressed(Keyboard.KEY_0)) {
			standardUnit = 32;
		}
		if (container.getInput().isKeyPressed(Keyboard.KEY_9)) {
			this.cameraMoveSpeed++;
		}
		if (container.getInput().isKeyPressed(Keyboard.KEY_8)) {
			this.cameraMoveSpeed--;
		}
	}

	public static final Rectangle DEBUG_RECTANGLE = new Rectangle(5, 5, 240, 110);

	private void diplaySystemInformation(Graphics g, int x, int y) {
		g.setColor(Color.black);
		g.fill(DEBUG_RECTANGLE);
		g.setColor(Color.gray);
		g.draw(DEBUG_RECTANGLE);
		g.setColor(Color.white);
		g.scale(INFORMATION_DISPLAY_SCALE, INFORMATION_DISPLAY_SCALE);
		g.drawString("LWJGL Version: " + Sys.getVersion(), x, y);
		long elapsedTime = (currentTime - startTime) / 1000;
		g.drawString("Elapsed Time: " + elapsedTime + " seconds", x, y + 20);
		g.drawString("Current Tic: " + currentTic, x, y + 40);
		g.drawString("Current Standard Unit: " + standardUnit, x, y + 60);
		g.scale(scale, scale);
	}

	public synchronized long getCurrentTic() {
		return this.currentTic;
	}

	@Override
	public int getID() {
		return 0;
	}

	public WorldManager getWorldManager() {
		return this.worldManager;
	}
}