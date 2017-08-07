package com.sim.simStates;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import com.sim.Simulator;

public class MenuState extends BasicGameState {

	private int buttonX;
	private int buttonY;
	private int buttonWidth;
	private int buttonHeight;

	private Shape buttonShape;

	private int buttonY2;

	private Shape buttonShape2;

	private Image background;

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		buttonWidth = 300;
		buttonHeight = 50;
		buttonX = container.getWidth() / 2 - buttonWidth / 2;
		buttonY = container.getHeight() / 2 - buttonHeight / 2;
		buttonY2 = buttonY + 100;
		buttonShape = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);
		buttonShape2 = new Rectangle(buttonX, buttonY2, buttonWidth, buttonHeight);
		background = new Image("res/backgroundImages/fantasyCastle.jpg");

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		g.drawImage(background, 0, 0);
		g.pushTransform();
		g.setColor(new Color(128, 0, 128));
		g.fillRect(0, background.getHeight(), container.getWidth(),
				container.getHeight() - background.getHeight());
		g.popTransform();
		renderButton(g, 1);
		renderButton(g, 2);
		// Sword Image draw
		g.drawImage(Simulator.simManager.map.resourceLoader.sword, buttonX + 10,
				buttonY + 8, new Color(255, 255, 255));

	}

	private void renderButton(Graphics g, int buttonNum) {
		int buttonYNum = 0;
		Shape buttonShapeToUse = null;
		String buttonMessage = "";

		switch (buttonNum) {
		case (1):
			buttonYNum = this.buttonY;
			buttonShapeToUse = this.buttonShape;
			buttonMessage = "Start Simulation";
			break;
		case (2):
			buttonYNum = this.buttonY2;
			buttonShapeToUse = this.buttonShape2;
			buttonMessage = "Create a new Map";
			break;
		}

		g.setColor(new Color(125, 150, 0));

		// Button
		g.fill(buttonShapeToUse);
		g.setColor(new Color(255, 0, 0));
		g.draw(buttonShapeToUse);
		g.setColor(new Color(0, 0, 0));
		g.drawString(buttonMessage, buttonX + buttonWidth / 4,
				buttonYNum + buttonHeight / 3);

		// Reset Color
		g.setColor(new Color(255, 255, 255));
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		checkButton(container, game);
	}

	private void checkButton(GameContainer container, StateBasedGame game) {
		/* Check if menu main buttons are pressed */
		if (container.getInput().isMousePressed(0)) {
			if (buttonShape2.contains(container.getInput().getMouseX(),
					container.getInput().getMouseY())) {
				game.enterState(4);
			}
			if (buttonShape.contains(container.getInput().getMouseX(),
					container.getInput().getMouseY())) {
				game.enterState(0);
			}
		}

		/* Can just hit keys to switch to desired state as well */
		if (container.getInput().isKeyPressed(Keyboard.KEY_0)) {
			game.enterState(0);
		}
		if (container.getInput().isKeyPressed(Keyboard.KEY_1)) {
			game.enterState(1);
		}
		if (container.getInput().isKeyPressed(Keyboard.KEY_2)) {
			game.enterState(2);
		}
		if (container.getInput().isKeyPressed(Keyboard.KEY_3)) {
			game.enterState(3);
		}
		if (container.getInput().isKeyPressed(Keyboard.KEY_4)) {
			game.enterState(4);
		}
	}

	@Override
	public int getID() {
		return 1;
	}

}
