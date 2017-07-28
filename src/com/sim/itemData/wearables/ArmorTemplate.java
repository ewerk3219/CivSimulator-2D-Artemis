package com.sim.itemData.wearables;

import com.sim.itemData.Item;
import com.sim.itemData.ItemType;

public class ArmorTemplate implements Item {
	private ItemType itemType = ItemType.Armor;
	private ArmorName name;
	private ItemType materialTypeAllowed;
	private Size size;
	private int armorClass;
	private float weight;

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	@Override
	public float getItemWeight() {
		return weight;
	}

	public ArmorName getName() {
		return name;
	}

	public void setName(ArmorName name) {
		this.name = name;
	}

	public ItemType getMaterialTypeAllowed() {
		return materialTypeAllowed;
	}

	public void setMaterialTypeAllowed(ItemType materialTypeAllowed) {
		if (materialTypeAllowed == ItemType.Armor
				|| materialTypeAllowed == ItemType.Weapon) {
			throw new IllegalArgumentException(
					"Bad materialTypeAllowd variable: " + materialTypeAllowed);
		}
		this.materialTypeAllowed = materialTypeAllowed;
	}

	public int getArmorClass() {
		return armorClass;
	}

	public void setArmorClass(int armorClass) {
		this.armorClass = armorClass;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	@Override
	public ItemType getItemType() {
		return itemType;
	}
}