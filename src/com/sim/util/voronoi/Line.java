package com.sim.util.voronoi;

import java.util.List;

public class Line {
	private Site p1;
	private Site p2;

	public Line(Site p1, Site p2) {
		this.p1 = p1;
		this.p2 = p2;
	}

	public Site getP1() {
		return p1;
	}

	public void setP1(Site p1) {
		this.p1 = p1;
	}

	public Site getP2() {
		return p2;
	}

	public void setP2(Site p2) {
		this.p2 = p2;
	}

	public Site intersection(Line ln2) {
		float x1 = p1.getX();
		float x2 = p2.getX();
		float x3 = ln2.p1.getX();
		float x4 = ln2.p2.getX();
		float y1 = p1.getY();
		float y2 = p2.getY();
		float y3 = ln2.p1.getY();
		float y4 = ln2.p2.getY();

		// Point of intersection
		float interX = ((x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2) * (x3 * y4 - y3 * x4))
				/ ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4));
		float interY = ((x1 * y2 - y1 * x2) * (y3 - y4) - (y1 - y2) * (x3 * y4 - y3 * x4))
				/ ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4));

		Site pointOfIntersection = new Site(interX, interY);
		return pointOfIntersection;
	}

	// Checks to see if lines are equivalent numerically.
	public boolean equal(Line ln2) {
		float x1 = p1.getX();
		float y1 = p1.getY();

		float x2 = p2.getX();
		float y2 = p2.getY();

		float x3 = ln2.p1.getX();
		float y3 = ln2.p1.getY();

		float x4 = ln2.p2.getX();
		float y4 = ln2.p2.getY();

		if (x1 == x3 && y1 == y3 && x2 == x4 && y2 == y4) {
			return true;
		} else if (x1 == x4 && y1 == y4 && x2 == x3 && y2 == y3) {
			return true;
		} else {
			return false;
		}
	}

	public static Line getBisectingLine(List<Site> midPointList, List<Site> secondaryMidPointSites, Site p1, Site p2) {
		float midX = 0;
		float midY = 0;
		midX = (p1.getX() + p2.getX()) / 2;
		midY = (p1.getY() + p2.getY()) / 2;
		Site midPointAB = new Site(midX, midY);
		midPointList.add(midPointAB); // add for display

		float dx = p1.getX() - p2.getX();
		float dy = p1.getY() - p2.getY();
		Site midPointABsecondary = new Site(midX + dy, midY - dx);
		secondaryMidPointSites.add(midPointABsecondary); // add for display
		return new Line(midPointAB, midPointABsecondary);
	}
}