package com.sim.subSystems.entity.components;

import java.util.ArrayList;

public class Inventory {

	// Item Inventory
	// private ArrayList<Consumable> consumbles;
	// private ArrayList<Material> materials;
	// private ArrayList<Wearable> wearables;
	// private ArrayList<Wearable> currentlyEquipped;

	// Equipped Inventory

	// Slot Counts

	// Main Slots:
	private int mainHand;
	private int offHand;
	private int armor;

	// Accessories:
	private int head;
	private int eyes;
	private int neck;
	private int shoulders;
	private int torso;
	private int arms;
	private int hands;
	private int rings;
	private int waist;
	private int feet;

	private float overallWeight;

	public Inventory() {
		// this.consumbles = new ArrayList<Consumable>();
		// this.materials = new ArrayList<Material>();
		// this.wearables = new ArrayList<Wearable>();
		// this.currentlyEquipped = new ArrayList<Wearable>();

	}

	// public Inventory(ArrayList<Consumable> consumbles, ArrayList<Material>
	// materials,
	// ArrayList<Wearable> wearables, ArrayList<Wearable> currentlyEquiped) {
	// super();
	// this.consumbles = consumbles;
	// this.materials = materials;
	// this.wearables = wearables;
	// this.currentlyEquipped = currentlyEquiped;
	// }
	//
	// public ArrayList<Wearable> getCurrentlyEquipped() {
	// return currentlyEquipped;
	// }
	//
	// public void setCurrentlyEquipped(ArrayList<Wearable> currentlyEquipped) {
	// this.currentlyEquipped = currentlyEquipped;
	// }

	public int getMainHand() {
		return mainHand;
	}

	public void setMainHand(int mainHand) {
		this.mainHand = mainHand;
	}

	public int getOffHand() {
		return offHand;
	}

	public void setOffHand(int offHand) {
		this.offHand = offHand;
	}

	public int getArmor() {
		return armor;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}

	public int getHead() {
		return head;
	}

	public void setHead(int head) {
		this.head = head;
	}

	public int getEyes() {
		return eyes;
	}

	public void setEyes(int eyes) {
		this.eyes = eyes;
	}

	public int getNeck() {
		return neck;
	}

	public void setNeck(int neck) {
		this.neck = neck;
	}

	public int getShoulders() {
		return shoulders;
	}

	public void setShoulders(int shoulders) {
		this.shoulders = shoulders;
	}

	public int getTorso() {
		return torso;
	}

	public void setTorso(int torso) {
		this.torso = torso;
	}

	public int getArms() {
		return arms;
	}

	public void setArms(int arms) {
		this.arms = arms;
	}

	public int getHands() {
		return hands;
	}

	public void setHands(int hands) {
		this.hands = hands;
	}

	public int getRings() {
		return rings;
	}

	public void setRings(int rings) {
		this.rings = rings;
	}

	public int getWaist() {
		return waist;
	}

	public void setWaist(int waist) {
		this.waist = waist;
	}

	public int getFeet() {
		return feet;
	}

	public void setFeet(int feet) {
		this.feet = feet;
	}

	public float getOverallWeight() {
		return overallWeight;
	}

	public void setOverallWeight(float overallWeight) {
		this.overallWeight = overallWeight;
	}
}
