package com.sim.subSystems.world;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

import com.artemis.Entity;
import com.sim.subSystems.entity.components.Visible;
import com.sim.itemData.material.MaterialName;

public class Tile {

	private int x, y;
	private boolean isSolid; // If this tile is pass-able or not.
	private Color terrainColor;

	// For Entity tracking
	private Entity currentEntity;

	// Material and Environmental conditions and other world generator
	// information
	private MaterialName material;
	private double height;

	public Tile(int x, int y, boolean isSolid, Color terrainColor) {
		this.x = x;
		this.y = y;
		this.isSolid = isSolid;
		this.terrainColor = terrainColor;
		currentEntity = null;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
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

	public MaterialName getMaterial() {
		return material;
	}

	public void setMaterial(MaterialName material) {
		this.material = material;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}
}