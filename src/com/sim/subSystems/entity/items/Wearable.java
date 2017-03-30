package com.sim.subSystems.entity.items;

public class Wearable implements Item {

	private ItemType itemType;
	private float weight;
	private WearableTag tag; // item name

	public Wearable(ItemType itemType, float weight, WearableTag tag) {
		this.itemType = itemType;
		this.weight = weight;
		this.tag = tag;
	}

	@Override
	public ItemType getItemType() {
		return this.itemType;
	}

	@Override
	public float getItemWeight() {
		return this.weight;
	}

	public WearableTag getWearableTag() {
		return this.tag;
	}
}
