package com.sim.simStates;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import com.sim.Simulator;

public class MenuState extends BasicGameState {

	private int buttonX;
	private int buttonY;
	private int buttonWidth;
	private int buttonHeight;
	private Shape buttonShape;

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		buttonWidth = 300;
		buttonHeight = 50;
		buttonX = 1920 / 2 - buttonWidth / 2;
		buttonY = 1080 / 2 - buttonHeight / 2;
		buttonShape = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.setBackground(new Color(0, 120, 0));
		renderButton(g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		checkButton(container, game);
	}

	private void renderButton(Graphics g) {
		g.setColor(new Color(125, 150, 0));

		// Button
		g.fill(buttonShape);
		g.setColor(new Color(255, 0, 0));
		g.draw(buttonShape);
		g.setColor(new Color(0, 0, 0));
		g.drawString("Start Simulation", buttonX + buttonWidth / 4, buttonY + buttonHeight / 3);

		// Sword Image draw
		g.drawImage(Simulator.simManager.map.resourceLoader.sword, buttonX - 30, buttonY, new Color(255, 255, 255));

		// Reset Color
		g.setColor(new Color(255, 255, 255));
	}

	private void checkButton(GameContainer container, StateBasedGame game) {
		if (container.getInput().isMousePressed(0)
				&& buttonShape.contains(container.getInput().getMouseX(), container.getInput().getMouseY())) {
			game.enterState(0);
		}
		if (container.getInput().isKeyPressed(Keyboard.KEY_0)) {
			game.enterState(0);
		}
		if (container.getInput().isKeyPressed(Keyboard.KEY_2)) {
			game.enterState(2);
		}
	}

	@Override
	public int getID() {
		return 1;
	}

}
