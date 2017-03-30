package com.sim.subSystems.world;

import org.newdawn.slick.geom.Circle;

import com.artemis.Entity;
import com.artemis.EntityEdit;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.sim.simStates.SimulationState;
import com.sim.subSystems.entity.components.CollisionShape;
import com.sim.subSystems.entity.components.Velocity;
import com.sim.subSystems.entity.entitySystems.Movement;
import com.sim.subSystems.entity.util.Vector2D;
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
		WorldConfiguration config = new WorldConfigurationBuilder().with(new Movement())
				.build();
		world = new World(config);
	}

	public void process(float delta) {
		this.world.setDelta(delta / 1000.0f);
		this.world.process();
	}

	public void addEntity(float x, float y, float vx, float vy) {
		Entity e = world.createEntity();
		EntityEdit ed = world.edit(e.getId());
		ed.add(new CollisionShape(new Circle(x, y, SimulationState.STANDARD_UNIT / 2)));
		ed.add(new Velocity(new Vector2D(vx, vy)));
	}

	public Area getArea() {
		return area;
	}
}
