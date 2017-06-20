package com.sim.util.voronoi;

public class Site {
	private float x;
	private float y;

	public Site(float siteX, float siteY) {
		this.x = siteX;
		this.y = siteY;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float distanceTo(Site other) {
		return (float) Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
	}
}