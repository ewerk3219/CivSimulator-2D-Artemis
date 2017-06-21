package com.sim.subSystems.world.generators;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.sim.Simulator;
import com.sim.util.voronoi.Voronoi;

public class VoronoiWorldGen {

	public static final float DEFAULT_FLOAT_HEIGHT_NUM = 1.0f;
	private Random random;

	private Voronoi vd;

	public VoronoiWorldGen(int pointNum) {
		random = new Random();
		float[][] points = getPointsArray(pointNum);
		vd = new Voronoi(points);
	}

	/*
	 * For debugging
	 */
	private float[][] getBasicPointsArray() {
		float[][] points = new float[3][2];

		points[0][0] = 120; // first point, x
		points[0][1] = 230; // first point, y
		points[1][0] = 150; // second point, x
		points[1][1] = 105; // second point, y
		points[2][0] = 320; // third point, x
		points[2][1] = 113; // third point, y

		return points;
	}

	/*
	 * gets an array of 2D points set randomly across the width and height of
	 * the game canvas
	 */
	private float[][] getPointsArray(int pointNum) {
		// Must have a secondary array of length two
		float[][] points = new float[pointNum][2];
		int width = Simulator.simManager.getContainer().getWidth();
		int height = Simulator.simManager.getContainer().getHeight();
		for (int currentPoint = 0; currentPoint < pointNum; currentPoint++) {
			points[currentPoint][0] = random.nextInt(width);
			points[currentPoint][1] = random.nextInt(height);
		}
		return points;
	}

	public Voronoi getVoronoi() {
		return this.vd;
	}
}