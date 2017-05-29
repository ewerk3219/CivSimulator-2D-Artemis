package com.sim.itemData.material;

import com.sim.itemData.Item;
import com.sim.itemData.ItemType;

public class Material implements Item {

	private float weight;
	private ItemType itemType;
	private MaterialName name;
	private float strength;
	private float conductivity;
	private float healthMod;

	public float getStrength() {
		return strength;
	}

	public void setStrength(float strength) {
		this.strength = strength;
	}

	public float getConductivity() {
		return conductivity;
	}

	public void setConductivity(float conductivity) {
		this.conductivity = conductivity;
	}

	public float getHealthMod() {
		return healthMod;
	}

	public void setHealthMod(float healthMod) {
		this.healthMod = healthMod;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public MaterialName getName() {
		return name;
	}

	public void setName(MaterialName name) {
		this.name = name;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	@Override
	public float getItemWeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ItemType getItemType() {
		// TODO Auto-generated method stub
		return null;
	}
}