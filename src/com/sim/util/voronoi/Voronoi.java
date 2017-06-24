package com.sim.util.voronoi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.newdawn.slick.geom.Circle;

public class Voronoi {

	private List<Site> sites;
	private List<TriangulationCircle> delauneyTriangulation;
	private List<Line> delauneyTriangulationLines;
	private List<Site> midPointSites;
	private List<Site> secondaryMidPointSites;
	private List<Line> intersectionLines;
	private List<Site> intersectionPoints;

	public Voronoi(float[][] points) {
		if (points.length < 3) {
			throw new IllegalArgumentException(
					"Voronoi Diagrams needs atleast 3 initial points. Given:" + Arrays.toString(points));
		}
		sortPointsIntoSites(points);
		midPointSites = new ArrayList<Site>();
		secondaryMidPointSites = new ArrayList<Site>();
		intersectionLines = new ArrayList<Line>();
		intersectionPoints = new ArrayList<Site>();
		doDelauneyTriangulation();
		assembleLines();
	}

	private void sortPointsIntoSites(float[][] points) {
		sites = new ArrayList<Site>();
		for (int x = 0; x < points.length; x++) {
			float siteX = points[x][0];
			float siteY = points[x][1];
			sites.add(new Site(siteX, siteY));
		}
	}

	// Assembles an arraylist of triangles that when drawn visually altogether,
	// represent the result of delaunay triangulation.
	private void doDelauneyTriangulation() {
		List<TriangulationCircle> circumferenceList = new ArrayList<TriangulationCircle>();
		getAllCircleCombinations(circumferenceList);
		filterOutIllegalCircles(circumferenceList);
		delauneyTriangulation = circumferenceList;
	}

	private void getAllCircleCombinations(List<TriangulationCircle> circumferenceList) {
		/*
		 * This gets every combination of three point pairs. Intended to not
		 * have duplicate points in each pair.
		 */
		List<TreeSet<Site>> circlePointSet = new ArrayList<TreeSet<Site>>();
		for (int i = 0; i < sites.size(); i++) {
			Site A = sites.get(i);
			// Will eliminate adding a duplicate point to the three point pair.
			for (int j = 0; j < sites.size(); j++) {
				if (j != i) {
					Site B = sites.get(j);
					for (int k = 0; k < sites.size(); k++) {
						if (k != j && k != i) {
							Site C = sites.get(k);
							// In case there was a duplicate point used.
							TreeSet<Site> pointPair = new TreeSet<Site>();
							pointPair.add(A);
							pointPair.add(B);
							pointPair.add(C);
							if (pointPair.size() == 3 && !isAlreadyExistantSet(circlePointSet, pointPair)) {
								circlePointSet.add(pointPair);
							}
						}
					}
				}
			}
		}

		// Get all circles from point pairs.
		for (int currentPair = 0; currentPair < circlePointSet.size(); currentPair++) {
			TreeSet<Site> pointPair = circlePointSet.get(currentPair);
			Iterator<Site> points = pointPair.iterator();
			circumferenceList.add(getCircleFromThreePoints(points.next(), points.next(), points.next()));
		}
	}

	private boolean isAlreadyExistantSet(List<TreeSet<Site>> circlePointSet, TreeSet<Site> pointPair) {
		Iterator<TreeSet<Site>> listIter = circlePointSet.iterator();
		while (listIter.hasNext()) {
			TreeSet<Site> confirmedPointPair = listIter.next();
			Iterator<Site> siteIter = pointPair.iterator();
			int numOfNonUniqueSites = 0;
			for (int siteIndex = 0; siteIndex < 3; siteIndex++) {
				Site currentSite = siteIter.next();
				if (confirmedPointPair.contains(currentSite)) {
					numOfNonUniqueSites++;
				}
			}
			if (numOfNonUniqueSites == 3) {
				return true;
			}
		}
		return false;
	}

	// Using three points of a triangle, this will return a TriangulationCircle
	// object representing a circle hitting each point on the triangle
	private TriangulationCircle getCircleFromThreePoints(Site A, Site B, Site C) {
		// get lines with opposite slopes of legs of a triangle centered at each
		// leg's midpoint.
		// ---
		Line ln1 = Line.getBisectingLine(this.midPointSites, this.secondaryMidPointSites, A, B);
		Line ln2 = Line.getBisectingLine(this.midPointSites, this.secondaryMidPointSites, B, C);
		// ----

		this.intersectionLines.add(ln1);
		this.intersectionLines.add(ln2);
		Site center = ln1.intersection(ln2);
		this.intersectionPoints.add(center);
		TriangulationCircle circle = new TriangulationCircle(
				new Circle(center.getX(), center.getY(), center.distanceTo(A)), A, B, C);
		return circle;
	}

	/*
	 * Gets rid of all triangulationCircles that contain Sites in addition to
	 * their own three points.
	 */
	private void filterOutIllegalCircles(List<TriangulationCircle> circumferenceList) {
		for (int circleIndex = 0; circleIndex < circumferenceList.size(); circleIndex++) {
			TriangulationCircle currentTriangulationCircle = circumferenceList.get(circleIndex);
			Circle circle = currentTriangulationCircle.getCircle();
			for (int siteIndex = 0; siteIndex < this.sites.size(); siteIndex++) {
				Site currentSite = this.sites.get(siteIndex);
				if (!currentTriangulationCircle.usesSite(currentSite)
						&& circle.contains(currentSite.getX(), currentSite.getY())) { // illegaltriangulationCircle
					circumferenceList.remove(circleIndex);
					circleIndex--;
					break;
				}
			}
		}
	}

	/*
	 * Assembles a list of all lines that are unique
	 */
	private void assembleLines() {
		this.delauneyTriangulationLines = new ArrayList<Line>();
		for (int i = 0; i < this.delauneyTriangulation.size(); i++) {
			// Get lines of a the triangle from the deluaneyTriagulation points.
			TriangulationCircle tc = this.delauneyTriangulation.get(i);
			Line ln1 = new Line(tc.getA(), tc.getB());
			Line ln2 = new Line(tc.getB(), tc.getC());
			Line ln3 = new Line(tc.getA(), tc.getC());
			Line[] lines = new Line[3];
			lines[0] = ln1;
			lines[1] = ln2;
			lines[2] = ln3;

			// Find all non-unique lines
			boolean[] uniqueLines = new boolean[3];
			uniqueLines[0] = true;
			uniqueLines[1] = true;
			uniqueLines[2] = true;
			for (int lineIndex = 0; lineIndex < 3; lineIndex++) {
				Line currentLine = lines[lineIndex];
				for (int arrayIndex = 0; arrayIndex < this.delauneyTriangulationLines.size(); arrayIndex++) {
					if (currentLine.equal(this.delauneyTriangulationLines.get(arrayIndex))) {
						uniqueLines[lineIndex] = false;
					}
				}
			}

			// Load unique lines into the line arrayList
			for (int loadIndex = 0; loadIndex < 3; loadIndex++) {
				if (uniqueLines[loadIndex])
					this.delauneyTriangulationLines.add(lines[loadIndex]);
			}
		}
	}

	public List<Line> getDelauneyTriangulationLines() {
		return this.delauneyTriangulationLines;
	}

	public List<Site> getSites() {
		return this.sites;
	}

	public List<TriangulationCircle> getDelauneyTriangulation() {
		return this.delauneyTriangulation;
	}

	public List<Site> getMidPointSiteList() {
		return this.midPointSites;
	}

	public List<Site> getSecondaryMidPointList() {
		return this.secondaryMidPointSites;
	}

	public List<Line> getIntersectionLines() {
		return this.intersectionLines;
	}

	public List<Site> getIntersectionPoints() {
		return this.intersectionPoints;
	}
}