package com.sim.util.voronoi;

public class Site implements Comparable<Site> {
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

	public String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}

	@Override
	public int compareTo(Site other) {
		int mag1 = (int) (this.x + this.y);
		int mag2 = (int) (other.x + other.y);
		if (mag1 > mag2) {
			return 1;
		} else if (mag1 < mag2) {
			return -1;
		} else {
			return 0;
		}
	}
}