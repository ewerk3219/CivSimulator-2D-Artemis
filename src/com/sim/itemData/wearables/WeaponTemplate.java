package com.sim.itemData.wearables;

import com.sim.itemData.Item;
import com.sim.itemData.ItemType;

public class WeaponTemplate implements Item {

	private ItemType itemType = ItemType.Weapon;

	private WeaponName name;
	private int rollCount;
	private int rollMagnitude;
	private int critDamageMultiplier;
	private DmgType damageType;
	private int range;
	private WearableSlot wearableSlot;
	private int slotNumbersUsed;
	private Size weaponSize;
	private float weight;

	public WeaponName getName() {
		return name;
	}

	public void setName(WeaponName name) {
		this.name = name;
	}

	public int getRollCount() {
		return rollCount;
	}

	public void setRollCount(int rollCount) {
		this.rollCount = rollCount;
	}

	public int getRollMagnitude() {
		return rollMagnitude;
	}

	public void setRollMagnitude(int rollMagnitude) {
		this.rollMagnitude = rollMagnitude;
	}

	public int getCritDamageMultiplier() {
		return critDamageMultiplier;
	}

	public void setCritDamageMultiplier(int critDamageMultiplier) {
		this.critDamageMultiplier = critDamageMultiplier;
	}

	public DmgType getDamageType() {
		return damageType;
	}

	public void setDamageType(DmgType damageType) {
		this.damageType = damageType;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public WearableSlot getWearableSlot() {
		return wearableSlot;
	}

	public void setWearableSlot(WearableSlot wearableSlot) {
		this.wearableSlot = wearableSlot;
	}

	public int getSlotNumbersUsed() {
		return slotNumbersUsed;
	}

	public void setSlotNumbersUsed(int slotNumbersUsed) {
		this.slotNumbersUsed = slotNumbersUsed;
	}

	public Size getWeaponSize() {
		return weaponSize;
	}

	public void setWeaponSize(Size weaponSize) {
		this.weaponSize = weaponSize;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	@Override
	public float getItemWeight() {
		// TODO Auto-generated method stub
		return this.weight;
	}

	@Override
	public ItemType getItemType() {
		// TODO Auto-generated method stub
		return this.itemType;
	}

}
