package com.sim.subSystems.world;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import com.sim.Simulator;
import com.sim.simStates.SimulationState;

public class Tile {

	private int x, y;
	private boolean isSolid; // If this tile is pass-able or not.
	private Color terrainColor;
	private Shape shape;

	public Tile(int x, int y, boolean isSolid, Color terrainColor) {
		this.x = x;
		this.y = y;
		this.isSolid = isSolid;
		this.terrainColor = terrainColor;
		resetTileShapeCoordinates();
	}

	public void resetTileShapeCoordinates() {
		int standardUnit = SimulationState.STANDARD_UNIT;
		this.shape = new Rectangle(
				Simulator.simManager.simState.getRenderX() + x * standardUnit,
				Simulator.simManager.simState.getRenderY() + y * standardUnit, standardUnit,
				standardUnit);
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

	public boolean isSolid() {
		return isSolid;
	}

	public void setSolid(boolean isSolid) {
		this.isSolid = isSolid;
	}

	public Color getTerrainColor() {
		return terrainColor;
	}

	public void setTerrainColor(Color terrainColor) {
		this.terrainColor = terrainColor;
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

}
