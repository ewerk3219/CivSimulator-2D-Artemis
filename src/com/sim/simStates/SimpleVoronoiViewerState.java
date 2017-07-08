package com.sim.simStates;

import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.sim.Simulator;

import megamu.mesh.Voronoi;

public class SimpleVoronoiViewerState extends BasicGameState {

	private Voronoi vd;

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		float[][] siteArray = getRandomSiteArray();
		vd = new Voronoi(siteArray);
	}

	private Random random = new Random();
	private static final int SITE_NUM = 100;

	private float[][] getRandomSiteArray() {
		float[][] siteArray = new float[SITE_NUM][2];
		for (int i = 0; i < SITE_NUM; i++) {
			siteArray[i][0] = random.nextFloat() * Simulator.simManager.getContainer().getWidth();
			siteArray[i][1] = random.nextFloat() * Simulator.simManager.getContainer().getHeight();
		}
		return siteArray;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		renderGraphLines(g);
	}

	private void renderGraphLines(Graphics g) {
		g.pushTransform();
		g.setColor(Color.red);
		g.scale(100, 100);
		g.popTransform();
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		if (container.getInput().isKeyPressed(Keyboard.KEY_0)) {
			game.enterState(0);
		}
		if (container.getInput().isKeyPressed(Keyboard.KEY_1)) {
			game.enterState(1);
		}
		if (container.getInput().isKeyPressed(Keyboard.KEY_2)) {
			game.enterState(2);
		}
		if (container.getInput().isKeyPressed(Keyboard.KEY_9)) {
			container.getGraphics().scale(0.5f, 0.5f);
		}

	}

	@Override
	public int getID() {
		return 3;
	}

}
