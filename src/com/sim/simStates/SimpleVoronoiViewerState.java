package com.sim.simStates;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.sim.util.MathUtil;
import com.sim.util.delaunay.Point;
import com.sim.util.delaunay.Voronoi;
import com.sim.util.delaunay.VoronoiEdge;

public class SimpleVoronoiViewerState extends BasicGameState {

	private Voronoi vd;
	public static final int DECIMAL_NUM = 1000000;

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		ArrayList<Point> siteList = new ArrayList<Point>();
		int minDrawBound = -5;
		int maxDrawBound = 5;
		int minBound = -10;
		int maxBound = 10;
		addNPoints(siteList, minBound, maxBound);
		vd = new Voronoi(siteList, minDrawBound, maxDrawBound, minBound, maxBound);
	}

	private Random random = new Random();
	private static final int SITE_NUM = 50000;

	private void addNPoints(ArrayList<Point> siteList, int minBound, int maxBound) {
		int rngUpperBound = maxBound + Math.abs(minBound);
		for (int i = 0; i < SITE_NUM; i++) {
			double x = (double) (random.nextInt(rngUpperBound * DECIMAL_NUM) + minBound * DECIMAL_NUM) / DECIMAL_NUM;
			double y = (double) (random.nextInt(rngUpperBound * DECIMAL_NUM) + minBound * DECIMAL_NUM) / DECIMAL_NUM;
			System.out.println((i + 1) + ": (" + x + ", " + y + ")");
			siteList.add(new Point(x, y));
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		renderGraphLines(g);
	}

	private void renderGraphLines(Graphics g) {
		g.pushTransform();
		g.setColor(Color.red);
		renderEdges(g);
		g.popTransform();
	}

	public static final float OFFSET_MULTIPLIER = 10000.0f;

	private void renderEdges(Graphics g) {
		ArrayList<VoronoiEdge> edges = vd.getEdgeList();
		g.pushTransform();
		g.setColor(Color.red);
		for (Point p : vd.getSites()) {
			float x = (float) p.x;
			float y = (float) p.y;
			x = MathUtil.percentDisOffset(x, vd.maxDim, OFFSET_MULTIPLIER);
			y = MathUtil.percentDisOffset(y, vd.maxDim, OFFSET_MULTIPLIER);
			g.fillOval(x, y, 5, 5);
		}
		g.setColor(Color.cyan);
		for (VoronoiEdge e : edges) {
			if (e.p1 != null && e.p2 != null) {
				double topY = (e.p1.y == Double.POSITIVE_INFINITY) ? vd.maxDim : e.p1.y; // HACK to draw from infinity
				topY = MathUtil.percentDisOffset((float) topY, vd.maxDim, OFFSET_MULTIPLIER);
				float p1X = MathUtil.percentDisOffset((float) e.p1.x, vd.maxDim, OFFSET_MULTIPLIER);
				float p2X = MathUtil.percentDisOffset((float) e.p2.x, vd.maxDim, OFFSET_MULTIPLIER);
				float p2Y = MathUtil.percentDisOffset((float) e.p2.y, vd.maxDim, OFFSET_MULTIPLIER);
				g.drawLine(p1X, (float) topY, p2X, p2Y);
			}
		}
		g.popTransform();
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		if (container.getInput().isKeyPressed(Keyboard.KEY_0)) {
			game.enterState(0);
		}
		if (container.getInput().isKeyPressed(Keyboard.KEY_1)) {
			game.enterState(1);
		}
		if (container.getInput().isKeyPressed(Keyboard.KEY_2)) {
			game.enterState(2);
		}
		if (container.getInput().isKeyPressed(Keyboard.KEY_9)) {
			container.getGraphics().scale(0.5f, 0.5f);
		}
	}

	@Override
	public int getID() {
		return 3;
	}

}
