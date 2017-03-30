package com.sim.subSystems.entity.items;

public class Consumable implements Item {

	private ItemType itemType;
	private float weight;
	private ConsumableType consumableType;
	
	public Consumable(ItemType itemType, float weight, ConsumableType consumableType) {
		this.itemType = itemType;
		this.weight = weight;
		this.consumableType = consumableType;
	}

	@Override
	public float getItemWeight() {
		return this.weight;
	}

	@Override
	public ItemType getItemType() {
		return this.itemType;
	}

	public ConsumableType getConsumableType() {
		return this.consumableType;
	}

}
