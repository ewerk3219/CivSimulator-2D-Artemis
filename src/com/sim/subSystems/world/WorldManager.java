package com.sim.subSystems.world;

import java.util.Random;

import org.newdawn.slick.Image;

import com.artemis.Entity;
import com.artemis.EntityEdit;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.sim.Simulator;
import com.sim.subSystems.entity.components.CollisionType;
import com.sim.subSystems.entity.components.TestMind;
import com.sim.subSystems.entity.components.Visible;
import com.sim.subSystems.entity.entitySystems.TestMindAI;
import com.sim.subSystems.world.worldParser.TextFileWorldParser;

public class WorldManager {

	private World world;
	private Area area;

	public WorldManager(String path) {
		this();
		area = new Area();
		area.addLayer(TextFileWorldParser.parseLayer(path));
	}

	public WorldManager() {
		WorldConfiguration config = new WorldConfigurationBuilder()
				.with(new TestMindAI()).build();
		world = new World(config);
	}

	public void process(float delta) {
		this.world.setDelta(delta / 1000.0f);
		this.world.process();
	}

	public void addEntity(int x, int y) {
		Entity e = world.createEntity();
		EntityEdit ed = world.edit(e.getId());
		Random random = new Random();
		Layer layer = area.getLayerContainer().get(0);
		int entityXLocation = random.nextInt(layer.getLayerGrid().length);
		int entityYLocation = random.nextInt(layer.getLayerGrid()[0].length);
		while (layer.getTile(entityXLocation, entityYLocation).isSolid()) {
			entityXLocation = random.nextInt(layer.getLayerGrid().length);
			entityYLocation = random.nextInt(layer.getLayerGrid()[0].length);
		}
		int spriteX = 28;
		int spriteY = 1;
		checkForNullPointers(spriteX, spriteY);
		Image appearance = Simulator.simManager.map.resourceLoader.getSprites()
				.getSprite(spriteX, spriteY);
		ed.add(new CollisionType(true));
		ed.add(new Visible(entityXLocation, entityYLocation, true, appearance));
		ed.add(new TestMind());

		// add entities to world
		layer.getTile(entityXLocation, entityYLocation).setEntity(e);
	}

	private void checkForNullPointers(int spriteX, int spriteY) {
		if (Simulator.simManager == null) {
			System.out.println("simManager is null");
		}
		if (Simulator.simManager.map == null) {
			System.out.println("map is null");
		}
		if (Simulator.simManager.map.resourceLoader == null) {
			System.out.println("resourceLoader is null");
		}
		if (Simulator.simManager.map.resourceLoader.getSprites() == null) {
			System.out.println("sprites object is null");
		}
		if (Simulator.simManager.map.resourceLoader.getSprites().getSprite(spriteX,
				spriteY) == null) {
			System.out.println("Image is null");
		}
	}

	public Area getArea() {
		return area;
	}

	public Entity getEntity(int entityId) {
		return this.world.getEntity(entityId);
	}
}
