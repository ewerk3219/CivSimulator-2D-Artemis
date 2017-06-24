package com.sim.simStates;

import java.util.List;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import com.sim.subSystems.world.generators.VoronoiWorldGen;
import com.sim.util.voronoi.Line;
import com.sim.util.voronoi.Site;
import com.sim.util.voronoi.Voronoi;

public class VoronoiViewerState extends BasicGameState {

	private long startTime = Sys.getTime();
	private long currentTime = Sys.getTime();
	private long currentTic;

	private Voronoi vd;

	private boolean doneOnce;

	private boolean renderPoints;
	private boolean renderLines;
	private boolean renderCircles;
	private boolean renderMidPoints;
	private boolean renderSecondaryMidPoint;
	private boolean renderIntersectionLines;
	private boolean renderIntersectionPoints;

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		int siteNum = 50;
		VoronoiWorldGen vdWorldGen = new VoronoiWorldGen(siteNum);
		vd = vdWorldGen.getVoronoi();

		renderPoints = true;
		renderLines = true;
		renderCircles = true;
		renderMidPoints = true;
		renderSecondaryMidPoint = true;
		renderIntersectionLines = true;
		renderIntersectionPoints = true;

		this.doneOnce = false;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.flush();
		g.setBackground(Color.black);
		renderGrid(container, g, 50);
		if (this.renderPoints)
			renderPoints(g);
		if (this.renderLines)
			renderLines(g);
		if (this.renderCircles)
			renderCircles(g);
		if (this.renderMidPoints)
			renderMidPoints(g);
		if (this.renderSecondaryMidPoint)
			renderSecondaryMidPoints(g);
		if (this.renderIntersectionLines)
			renderInterectionLines(g);
		if (this.renderIntersectionPoints)
			renderIntersectionPoints(g);
	}

	private void renderIntersectionPoints(Graphics g) {
		g.pushTransform();
		g.setColor(Color.lightGray);
		for (Site site : vd.getIntersectionPoints()) {
			g.draw(new Circle(site.getX(), site.getY(), 5));
		}
		g.popTransform();
	}

	private void renderInterectionLines(Graphics g) {
		g.pushTransform();
		g.setColor(Color.pink);
		for (Line line : this.vd.getIntersectionLines()) {
			g.drawLine(line.getP1().getX(), line.getP1().getY(), line.getP2().getX(), line.getP2().getY());
		}
		g.popTransform();
	}

	private void renderSecondaryMidPoints(Graphics g) {
		g.pushTransform();
		g.setColor(Color.cyan);
		for (Site site : this.vd.getSecondaryMidPointList()) {
			g.fill(new Circle(site.getX(), site.getY(), 5));
		}
		g.popTransform();
	}

	private void renderMidPoints(Graphics g) {
		g.pushTransform();
		g.setColor(Color.yellow);
		for (Site site : this.vd.getMidPointSiteList()) {
			g.fill(new Circle(site.getX(), site.getY(), 5));
		}
		g.popTransform();
	}

	private void renderCircles(Graphics g) {
		g.pushTransform();
		g.setColor(Color.green);
		for (int circleIndex = 0; circleIndex < vd.getDelauneyTriangulation().size(); circleIndex++) {
			g.draw(vd.getDelauneyTriangulation().get(circleIndex).getCircle());
		}
		g.popTransform();
	}

	private void renderLines(Graphics g) {
		g.pushTransform();
		List<Line> lines = this.vd.getDelauneyTriangulationLines();
		for (int i = 0; i < lines.size(); i++) {
			Site p1 = lines.get(i).getP1();
			Site p2 = lines.get(i).getP2();
			g.setColor(Color.magenta);
			g.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
		}
		g.popTransform();
	}

	private void renderPoints(Graphics g) {
		g.pushTransform();
		g.setColor(Color.blue);
		for (int i = 0; i < vd.getSites().size(); i++) {
			Site site = vd.getSites().get(i);
			g.fill(new Circle(site.getX(), site.getY(), 10));
		}
		g.popTransform();
	}

	private void renderGrid(GameContainer container, Graphics g, int size) {
		g.pushTransform();
		g.scale(1.0f, 1.0f);
		g.setColor(Color.darkGray);
		int resolution = container.getScreenWidth();
		for (int i = 0; i < resolution; i += size) {
			g.drawLine(i, 0, i, resolution);
			g.drawLine(0, i, resolution, i);
		}
		g.popTransform();
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		checkAndUpdateKeys(container, game);
		updateSimTimingAndSleep(delta);
		if (!this.doneOnce) {
			for (int i = 0; i < this.vd.getSecondaryMidPointList().size(); i++) {
				System.out.println(this.vd.getSecondaryMidPointList().get(i));
			}
			this.doneOnce = true;
		}
	}

	private void checkAndUpdateKeys(GameContainer container, StateBasedGame game) {
		if (container.getInput().isKeyPressed(Keyboard.KEY_0)) {
			game.enterState(0);
		}
		if (container.getInput().isKeyPressed(Keyboard.KEY_1)) {
			game.enterState(1);
		}
		if (container.getInput().isKeyPressed(Keyboard.KEY_P)) {
			this.renderPoints = !this.renderPoints;
		}
		if (container.getInput().isKeyPressed(Keyboard.KEY_L)) {
			this.renderLines = !this.renderLines;
		}
		if (container.getInput().isKeyPressed(Keyboard.KEY_C)) {
			this.renderCircles = !this.renderCircles;
		}
		if (container.getInput().isKeyPressed(Keyboard.KEY_O)) {
			this.renderMidPoints = !this.renderMidPoints;
		}
		if (container.getInput().isKeyPressed(Keyboard.KEY_I)) {
			this.renderSecondaryMidPoint = !this.renderSecondaryMidPoint;
		}
		if (container.getInput().isKeyPressed(Keyboard.KEY_U)) {
			this.renderIntersectionPoints = !this.renderIntersectionPoints;
		}
		if (container.getInput().isKeyPressed(Keyboard.KEY_K)) {
			this.renderIntersectionLines = !this.renderIntersectionLines;
		}
	}

	public static final long FPS_TIME_TO_SLEEP = 28;

	private void updateSimTimingAndSleep(int delta) {
		currentTime = Sys.getTime();
		if (delta < FPS_TIME_TO_SLEEP) {
			long timeToSleep = FPS_TIME_TO_SLEEP - delta;
			try {
				Thread.sleep(timeToSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.currentTic++;
	}

	@Override
	public int getID() {
		return 2;
	}
}