package com.sim.util.voronoi;

import org.newdawn.slick.geom.Circle;

public class TriangulationCircle {
	private Circle circle;
	private Site A, B, C;

	public TriangulationCircle(Circle circle, Site A, Site B, Site C) {
		this.circle = circle;
		this.A = A;
		this.B = B;
		this.C = C;
	}

	public boolean usesSite(Site site) {
		return (site.equals(A) || site.equals(B) || site.equals(C));
	}

	public Circle getCircle() {
		return circle;
	}

	public Site getA() {
		return A;
	}

	public Site getB() {
		return B;
	}

	public Site getC() {
		return C;
	}
}