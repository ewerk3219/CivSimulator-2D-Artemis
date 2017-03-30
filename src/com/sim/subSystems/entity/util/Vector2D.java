package com.sim.subSystems.entity.util;

import org.newdawn.slick.geom.Point;

public class Vector2D {

	private float x;
	private float y;

	public Vector2D(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return this.x;
	}

	public float getY() {
		return this.y;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setVector(Vector2D vec) {
		this.x = vec.x;
		this.y = vec.y;
	}
}
