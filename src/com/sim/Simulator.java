package com.sim;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Simulator {

	public static final String SIM_NAME = "CivSim2D";

	public static SimManager simManager;

	public static void main(String[] args) {
		simManager = new SimManager(SIM_NAME);
		try {
			AppGameContainer app = new AppGameContainer(simManager);
			app.setDisplayMode(1920, 1080, false);
			app.setAlwaysRender(true); // Doesn't need focus to keep rendering
			app.setVSync(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
