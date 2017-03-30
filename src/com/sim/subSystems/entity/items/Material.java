package com.sim.subSystems.entity.items;

public class Material implements Item {

	private ItemType itemType;
	private float weight;
	private MaterialType materialType;
	private int stackNum;

	/*
	 * This item cannot be constructed with a stack number less than or equal to
	 * zero.
	 */
	public Material(ItemType itemType, float weight, MaterialType materialType,
			int stackNum) {
		if (stackNum <= 0) { // cannot have 0 of an item
			String error = "stackNum: " + stackNum
					+ ", cannot be less than or equal to zero.";
			throw new IllegalArgumentException(error);
		}
		this.itemType = itemType;
		this.weight = weight;
		this.materialType = materialType;
		this.stackNum = stackNum;
	}

	@Override
	public float getItemWeight() {
		return this.weight;
	}

	@Override
	public ItemType getItemType() {
		return this.itemType;
	}

	public MaterialType getMaterialType() {
		return this.materialType;
	}

	public int getStackNum() {
		return this.stackNum;
	}

	/*
	 * Increments the stack number and updates the weight of all the items
	 * combined. If the operation results in a stack of zero, then true is
	 * returned so other systems know to remove this item from the entities
	 * inventory.
	 */
	public boolean incrementStack(int amount) {
		if (amount + this.stackNum < 0) {
			String error = "amount: " + amount + ". cannot have less than 0 of an item";
			throw new IllegalArgumentException(error);
		}
		float weightOfOne = weight / stackNum;
		float deltaWeight = amount * weightOfOne;
		this.weight += deltaWeight;
		this.stackNum += amount;
		// If operation makes this stack zero, then send back true so the
		// inventory system knows to get rid of this item.
		if (this.stackNum == 0) {
			return true;
		} else {
			return false;
		}
	}
}
