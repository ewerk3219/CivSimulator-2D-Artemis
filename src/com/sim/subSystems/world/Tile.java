package com.sim.subSystems.world;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

import com.artemis.Entity;
import com.sim.itemData.material.MaterialName;
import com.sim.subSystems.entity.components.Visible;

public class Tile {

	public static final int MAX_OCCUPIANCY = 100;

	private final int x, y;
	private boolean isSolid; // If this tile is pass-able or not.
	private Color terrainColor;

	// For Entity tracking
	private Entity occupantEntity;
	private Entity environmentalEntity;

	// Material and Environmental conditions and other world generator
	// information
	private MaterialName material;
	private double height;

	public Tile(int x, int y, boolean isSolid, Color terrainColor) {
		this.x = x;
		this.y = y;
		this.isSolid = isSolid;
		this.terrainColor = terrainColor;
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

	public void setOccupantEntity(Entity e) {
		this.occupantEntity = e;
	}

	public boolean hasEntity(Entity e) {
		if (e.equals(occupantEntity)) {
			return true;
		} else if (e.equals(environmentalEntity)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean containsAnEntity() {
		if (occupantEntity != null) {
			return true;
		} else if (environmentalEntity != null) {
			return true;
		} else {
			return false;
		}
	}

	public Entity getOccupantEntity() {
		return this.occupantEntity;
	}

	public boolean hasOccupant() {
		return occupantEntity != null;
	}

	public Entity removeEntity(int index) {
		return this.removeEntity(index);
	}

	public Entity removeOccupantEntity() {
		Entity removedEntity = this.occupantEntity;
		this.occupantEntity = null;
		return removedEntity;
	}

	/*
	 * This will get the most entity to have most recently moved into the tile.
	 * If the tile has no entities within it, then this method will return null.
	 */
	public Image getOccupantEntityAppearance() {
		if (this.occupantEntity != null) {
			Visible visible = this.occupantEntity.getComponent(Visible.class);
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

	public Entity getEnvironmentalEntity() {
		return this.environmentalEntity;
	}
}