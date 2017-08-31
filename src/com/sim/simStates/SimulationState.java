package com.sim.simStates;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.sim.Simulator;
import com.sim.subSystems.renderManager.RenderManager;
import com.sim.subSystems.world.WorldManager;

public class SimulationState extends BasicGameState {

	public static final long FPS_TIME_TO_SLEEP = 28;
	private long startTime = Sys.getTime();
	private long currentTime = Sys.getTime();
	private long currentTic;

	private WorldManager worldManager;
	private RenderManager rm;

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		System.out.println("Initialization...");
		System.out.println();
		// Artemis setup
		initWorldManager();
		initSimMap();
		rm = new RenderManager(container, container.getGraphics(), game,
				worldManager);
		// initEntityTest();
		// -------
		System.out.println("Initialization complete");
	}

	private void initEntityTest() {
		for (int i = 0; i < 100; i++) {
			// parameters are test numbers! They don't actually do anything
			this.worldManager.addEntity(1, 1);
		}
	}

	private void initWorldManager() {
		this.worldManager = new WorldManager("testMap");
	}

	private void initSimMap() {
		Simulator.simManager.initSimMap();
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		checkKeys(worldManager, container, game);
		worldManager.process(delta);
		updateSimTimingAndSleep(delta);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		this.rm.render();
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

	private void checkKeys(WorldManager worldManager, GameContainer gc,
			StateBasedGame sbg) {
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
		if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			int mouseX = gc.getInput().getMouseX();
			int mouseY = gc.getInput().getMouseY();
			int gridX = rm.renderXToGridX(mouseX);
			int gridY = rm.renderYToGridY(mouseY);
			System.out.println(
					"Mouse Point in grid Coords   : (" + gridX + ", " + gridY + ")");
			System.out.println(
					"Mouse Point in render Coords : (" + mouseX + ", " + mouseY + ")");

		}
		if (gc.getInput().isKeyPressed(Keyboard.KEY_M)) {
			System.out
					.println("Test Mind AI set to: " + worldManager.toggleMindOnOff());
		}
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

	public RenderManager getRenderManager() {
		return this.rm;
	}

	public long getStartTime() {
		return this.startTime;
	}

	public long getCurrentTime() {
		return this.currentTime;
	}
}