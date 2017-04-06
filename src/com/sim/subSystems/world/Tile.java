package com.sim.subSystems.world;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import com.artemis.Component;
import com.artemis.Entity;
import com.sim.Simulator;
import com.sim.simStates.SimulationState;
import com.sim.subSystems.entity.components.Visible;

public class Tile {

	private int x, y;
	private boolean isSolid; // If this tile is pass-able or not.
	private Color terrainColor;
	private Shape shape;

	// For Entity tracking
	private Entity currentEntity;

	public Tile(int x, int y, boolean isSolid, Color terrainColor) {
		this.x = x;
		this.y = y;
		this.isSolid = isSolid;
		this.terrainColor = terrainColor;
		resetTileShapeCoordinates();
		currentEntity = null;
	}

	public void resetTileShapeCoordinates() {
		int standardUnit = SimulationState.STANDARD_UNIT;
		this.shape = new Rectangle(
				Simulator.simManager.simState.getRenderX() + x * standardUnit,
				Simulator.simManager.simState.getRenderY() + y * standardUnit,
				standardUnit, standardUnit);
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

	public void setEntity(Entity e) {
		this.currentEntity = e;
	}

	public boolean hasEntity(Entity e) {
		return e.equals(this.currentEntity);
	}

	public boolean containAnEntity() {
		return this.currentEntity != null;
	}

	public Entity getEntity() {
		return this.currentEntity;
	}

	public Entity removeEntity(int index) {
		return this.removeEntity(index);
	}

	public Entity removeEntity() {
		Entity removedEntity = this.currentEntity;
		this.currentEntity = null;
		return removedEntity;
	}

	/*
	 * This will get the most entity to have most recently moved into the tile.
	 * If the tile has no entities within it, then this method will return null.
	 */
	public Image getEntityAppearance() {
		if (this.currentEntity != null) {
			Visible visible = this.currentEntity.getComponent(Visible.class);
			if (visible != null) {
				return visible.getAppearance();
			}
		}
		return null;
	}
}