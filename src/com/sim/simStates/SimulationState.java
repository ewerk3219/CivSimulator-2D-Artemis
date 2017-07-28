package com.sim.simStates;

import org.lwjgl.Sys;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
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
	private RenderManager renderManager;

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		System.out.println("Initialization...");
		System.out.println();
		renderManager = new RenderManager(container, container.getGraphics(),
				game, this);
		renderManager.resetWorldClip(container);
		// Artemis setup
		initWorldManager();
		initSimMap();
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
		this.worldManager.generateNewLayer();
		this.worldManager.setCurrentLayer(1);
	}

	private void initSimMap() {
		Simulator.simManager.initSimMap();
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		renderManager.updateDeltasAndCheckKeys(worldManager);
		worldManager.process(delta);
		updateSimTimingAndSleep(delta);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		this.renderManager.render(worldManager.getArea());
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
		return this.renderManager;
	}

	public long getStartTime() {
		return this.startTime;
	}

	public long getCurrentTime() {
		return this.currentTime;
	}
}