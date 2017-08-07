package com.sim.simStates;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.sim.subSystems.world.WorldManager;

public class MapMakerState extends BasicGameState {

	/* World Manager used within this state */
	private WorldManager wm;
	/* Input for text */
	private TextField inputTextField;
	/* Font used for rendering */
	private TrueTypeFont renderFont;
	/* Input Bar */
	private Shape inputBarOutline;
	/* Tool browser bar */
	private Shape toolBrowserBar;

	/* Custom Color */
	public static final Color PURPLE = new Color(128, 0, 128);

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		/* Font and input box init. */
		int inputFontSize = 25;
		renderFont = new TrueTypeFont(new Font("Arial", Font.PLAIN, inputFontSize),
				true);
		int browserYShift = 100;
		int inputXOffset = 20;
		int inputYOffset = gc.getHeight() - browserYShift / 2 - 15;
		int inputWidth = 300;
		int inputHeight = 50;
		TextField input = new TextField(gc, renderFont, inputXOffset, inputYOffset,
				inputWidth, inputHeight);
		inputTextField = input;
		gc.getGraphics().setFont(renderFont);

		inputBarOutline = new Rectangle(0, gc.getHeight() - browserYShift,
				gc.getWidth(), browserYShift);
		toolBrowserBar = new Rectangle(0, 0, gc.getWidth(), browserYShift);

		/* other stuff */
		// blah
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		renderBrowsers(gc, g);
	}

	public void renderBrowsers(GameContainer gc, Graphics g) {
		g.setColor(PURPLE);
		g.fill(toolBrowserBar);
		g.fill(inputBarOutline);

		g.setColor(Color.white);
		inputTextField.render(gc, g);

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {

	}

	@Override
	public int getID() {
		return 4;
	}

}
