package com.sim;

import com.sim.resourceLoaders.ItemDataLoader;
import com.sim.resourceLoaders.SimResourceLoader;

public class SimMap {
	
	public SimResourceLoader resourceLoader;
	public ItemDataLoader itemData;
	
	public SimMap() {
		this.resourceLoader = new SimResourceLoader();
		this.itemData = new ItemDataLoader();
	}
}
