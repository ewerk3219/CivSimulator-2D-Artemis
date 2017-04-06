package com.sim.subSystems.entity.entitySystems;

import java.util.Random;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.IteratingSystem;
import com.sim.Simulator;
import com.sim.subSystems.entity.components.CollisionType;
import com.sim.subSystems.entity.components.TestMind;
import com.sim.subSystems.entity.components.Visible;
import com.sim.subSystems.world.WorldManager;

public class TestMindAI extends IteratingSystem {

	ComponentMapper<TestMind> mTestMind;
	ComponentMapper<Visible> mVisible;
	ComponentMapper<CollisionType> mCollisionType;
	
	public TestMindAI() {
		super(Aspect.all(TestMind.class, Visible.class, CollisionType.class));
	}

	@Override
	protected void process(int entityId) {
		WorldManager worldManager = Simulator.simManager.simState.getWorldManager();
		int xLimit = worldManager.getArea().getLayerContainer().get(0)
				.getLayerGrid().length;
		int yLimit = worldManager.getArea().getLayerContainer().get(0)
				.getLayerGrid()[0].length;
		Random random = new Random();
		int x = random.nextInt(xLimit);
		int y = random.nextInt(yLimit);
		while(worldManager.getArea().getLayerContainer().get(0).getTile(x, y).isSolid()) {
			x = random.nextInt(xLimit);
			y = random.nextInt(yLimit);
		}
		//DON'T UPDATE THE VISIBLE COMPONENT IN HERE!!!!
		Entity currentEntity = worldManager.getEntity(entityId);
		worldManager.getArea().getLayerContainer().get(0).moveEntityTo(currentEntity, x, y);
	}

}
