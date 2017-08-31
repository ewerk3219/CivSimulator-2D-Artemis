package com.sim.subSystems.gui.mapMaker;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.gui.TextField;

import com.sim.Simulator;
import com.sim.simStates.MapMakerState;
import com.sim.subSystems.gui.GUI;
import com.sim.util.mapmakertools.LayerSizeEditor;

public class MapMakerGUI implements GUI, ComponentListener {

	private final Graphics g;
	private final GameContainer gc;
	private final MapMakerState mms;

	/* Custom Color */
	public static final Color PURPLE = new Color(128, 0, 128);

	/* For mouse over coloring */
	private final Color mouseOverColor = new Color(128, 128, 128);

	// --- Input:

	/* Input for text */
	private TextField inputTextField;
	/* Font used for rendering */
	private TrueTypeFont renderFont;

	// --- Art:

	/* Input Bar */
	private Shape inputBarOutline;
	/* Tool browser bar */
	private Shape toolBrowserBar;

	// --- Buttons:

	// MenuState buttons:
	private MouseOverArea load;
	private MouseOverArea save;
	private MouseOverArea newMap;

	// MakerState buttons:
	private MouseOverArea pause;
	private MouseOverArea minus;
	private MouseOverArea plus;
	private MouseOverArea submitSize;

	// NewMapMakerState buttons:
	private MouseOverArea back;
	private MouseOverArea submitNewMap;

	private int browserYShift, inputXOffset, inputYOffset;

	public MapMakerGUI(Graphics g, GameContainer gc, MapMakerState mms) {
		this.g = g;
		this.gc = gc;
		this.mms = mms;

		/* Font and input box init. */
		int inputFontSize = 25;
		renderFont = new TrueTypeFont(new Font("Arial", Font.PLAIN, inputFontSize),
				true);
		browserYShift = 100;
		inputXOffset = 20;
		inputYOffset = gc.getHeight() - browserYShift / 2 - 30;
		int inputWidth = 600;
		int inputHeight = 30;
		TextField input = new TextField(gc, renderFont, inputXOffset, inputYOffset,
				inputWidth, inputHeight);
		inputTextField = input;
		gc.getGraphics().setFont(renderFont);

		inputBarOutline = new Rectangle(0, gc.getHeight() - browserYShift,
				gc.getWidth(), browserYShift);
		toolBrowserBar = new Rectangle(0, 0, gc.getWidth(), browserYShift);

		initButtons();
	}

	private void initButtons() {
		Image loadImage = null;
		try {
			loadImage = new Image("res/gui/buttons/button_load.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		load = new MouseOverArea(gc, loadImage,
				gc.getWidth() / 2 - loadImage.getWidth() / 2, gc.getHeight() / 2 - 150,
				this);
		load.setMouseOverColor(mouseOverColor);

		Image saveImage = null;
		try {
			saveImage = new Image("res/gui/buttons/button_save.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		save = new MouseOverArea(gc, saveImage,
				gc.getWidth() / 2 - saveImage.getWidth() / 2, gc.getHeight() / 2 - 75,
				this);
		save.setMouseOverColor(mouseOverColor);

		Image newMapImage = null;
		try {
			newMapImage = new Image("res/gui/buttons/button_new-map.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		newMap = new MouseOverArea(gc, newMapImage,
				gc.getWidth() / 2 - newMapImage.getWidth() / 2, gc.getHeight() / 2,
				this);
		newMap.setMouseOverColor(mouseOverColor);

		Image backImage = null;
		try {
			backImage = new Image("res/gui/buttons/button_back.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		back = new MouseOverArea(gc, backImage,
				gc.getWidth() / 2 - backImage.getWidth() / 2, gc.getHeight() / 2 - 75,
				this);
		back.setMouseOverColor(mouseOverColor);

		Image submitImage = null;
		try {
			submitImage = new Image("res/gui/buttons/button_submit.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		submitNewMap = new MouseOverArea(gc, submitImage,
				gc.getWidth() / 2 - submitImage.getWidth() / 2, gc.getHeight() / 2,
				this);
		submitNewMap.setMouseOverColor(mouseOverColor);

		submitSize = new MouseOverArea(gc, submitImage,
				gc.getWidth() / 2 - submitImage.getWidth() / 2,
				gc.getHeight() - browserYShift / 2, this);
	}

	public void renderButtons() {
		switch (mms.getState()) {
		case MakerState:
			submitSize.render(gc, g);
			break;
		case MenuState:
			load.render(gc, g);
			save.render(gc, g);
			newMap.render(gc, g);
			break;
		case NewMapMakerState:
			back.render(gc, g);
			submitNewMap.render(gc, g);
			break;
		}
	}

	public void renderInput() {
		inputTextField.render(gc, g);

	}

	public void renderAesthetics() {
		/* GUI */
		g.setColor(PURPLE);
		g.fill(toolBrowserBar);
		g.fill(inputBarOutline);

		g.setColor(Color.white);

	}

	public void componentActivated(AbstractComponent source) {
		switch (mms.getState()) {
		case MakerState:
			if (source == submitSize) {
				mms.submitLayerSizeForNewMapSize();
			}
			break;
		case MenuState:
			if (source == load) {

			} else if (source == save) {

			} else if (source == newMap) {
				mms.setState(MakerStates.NewMapMakerState);
				System.out.println("newMap");
			}
			break;
		case NewMapMakerState:
			if (source == back) {
				mms.setState(MakerStates.MenuState);
			} else if (source == submitNewMap) {
				mms.createNewMap(1, 1);
				mms.setState(MakerStates.MakerState);
			}
			break;
		}
	}

	public void switchState(MakerStates toState) {
		switch (toState) {
		case MakerState:
			break;
		case MenuState:
			break;
		case NewMapMakerState:
			break;
		}
	}
}
