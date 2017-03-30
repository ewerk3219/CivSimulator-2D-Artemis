package com.sim;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.sim.simStates.MenuState;
import com.sim.simStates.SimulationState;

public class SimManager extends StateBasedGame {

	public SimulationState simState;
	public SimMap map;

	public SimManager(String name) {
		super(name);
		simState = new SimulationState();
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		this.addState(new MenuState());
		this.addState(simState);
	}

	/*
	 * Has to be done in the init method of the menu state (RuntimeException
	 * otherwise since no openGL context will be found in the current thread)
	 */
	public void initSimMap() {
		map = new SimMap();
	}

}
