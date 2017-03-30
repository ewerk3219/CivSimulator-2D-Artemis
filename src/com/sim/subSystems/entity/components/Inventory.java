package com.sim.subSystems.entity.components;

import java.util.ArrayList;

import com.sim.subSystems.entity.items.Consumable;
import com.sim.subSystems.entity.items.Material;
import com.sim.subSystems.entity.items.Wearable;

public class Inventory {
	ArrayList<Consumable> consumbles;
	ArrayList<Material> materials;
	ArrayList<Wearable> wearables;
	ArrayList<Wearable> currentlyEquiped;

	public Inventory() {
		this.consumbles = new ArrayList<Consumable>();
		this.materials = new ArrayList<Material>();
		this.wearables = new ArrayList<Wearable>();
		this.currentlyEquiped = new ArrayList<Wearable>();

	}

	public Inventory(ArrayList<Consumable> consumbles, ArrayList<Material> materials,
			ArrayList<Wearable> wearables, ArrayList<Wearable> currentlyEquiped) {
		super();
		this.consumbles = consumbles;
		this.materials = materials;
		this.wearables = wearables;
		this.currentlyEquiped = currentlyEquiped;
	}

	public ArrayList<Consumable> getConsumbles() {
		return consumbles;
	}

	public void setConsumbles(ArrayList<Consumable> consumbles) {
		this.consumbles = consumbles;
	}

	public ArrayList<Material> getMaterials() {
		return materials;
	}

	public void setMaterials(ArrayList<Material> materials) {
		this.materials = materials;
	}

	public ArrayList<Wearable> getWearables() {
		return wearables;
	}

	public void setWearables(ArrayList<Wearable> wearables) {
		this.wearables = wearables;
	}

	public ArrayList<Wearable> getCurrentlyEquiped() {
		return currentlyEquiped;
	}

	public void setCurrentlyEquiped(ArrayList<Wearable> currentlyEquiped) {
		this.currentlyEquiped = currentlyEquiped;
	}
}
