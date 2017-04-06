package com.sim.subSystems.entity.components;

import org.newdawn.slick.Image;
import com.artemis.Component;

public class Visible extends Component {
	private boolean isVisible;
	private Image appearance;
	private int x;
	private int y;

	public Visible() {
		super();
	}
	
	public Visible(int x, int y, boolean isVisible, Image appearance) {
		super();
		this.isVisible = isVisible;
		this.appearance = appearance;
		this.x = x;
		this.y = y;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisiblity(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public void changeAppearanceTo(Image appearance) {
		this.appearance = appearance;
	}

	public Image getAppearance() {
		return this.appearance;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
