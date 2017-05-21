package com.sim.itemData.material;

import com.sim.itemData.Item;
import com.sim.itemData.ItemType;

public class Material implements Item {

	private float weight;
	private ItemType itemType;
	private MaterialName name;

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