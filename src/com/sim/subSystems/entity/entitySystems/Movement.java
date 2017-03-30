package com.sim.subSystems.entity.entitySystems;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.sim.Simulator;
import com.sim.simStates.SimulationState;
import com.sim.subSystems.entity.components.CollisionShape;
import com.sim.subSystems.entity.components.Velocity;
import com.sim.subSystems.entity.util.Vector2D;

public class Movement extends IteratingSystem {

	// Injected Automatically
	ComponentMapper<Velocity> mVelocity;
	ComponentMapper<CollisionShape> mCollisionShape;

	public static final float VELOCITY_DEGREDATION_FACTOR = 0.2f;

	public Movement() {
		super(Aspect.all(Velocity.class, CollisionShape.class));
	}

	@Override
	protected void process(int entityId) {
		CollisionShape collisionShape = mCollisionShape.get(entityId);

		float delta = super.world.delta;

		Shape oldShape = new Circle(
				collisionShape.getCenterX() + Simulator.simManager.simState.getDeltaX(),
				collisionShape.getCenterY() + Simulator.simManager.simState.getDeltaY(),
				SimulationState.STANDARD_UNIT / 3);

		System.out.println(oldShape.getCenterX());

		Velocity v = mVelocity.get(entityId);
		Vector2D vec = v.getVector();
		Shape newShape = new Circle(
				collisionShape.getCenterX() + Simulator.simManager.simState.getDeltaX()
						+ (vec.getX() * delta),
				collisionShape.getCenterY() + Simulator.simManager.simState.getDeltaY()
						+ (vec.getY() * delta),
				SimulationState.STANDARD_UNIT / 3);

		System.out.println(newShape.getCenterX());

		// If there is a collision
		if (isCollisionWithWorldZone(newShape) != null) {
			collisionShape.setShape(oldShape);
			vec.setVector(new Vector2D(0, 0));
		} else {
			collisionShape.setShape(newShape);
		}

		/*
		 * Send to SimulationState to be rendered
		 */
		Simulator.simManager.simState.renderShapes.add(collisionShape);
	}

	/*
	 * If a shape is returned, then a collision has occurred, if there isn't a
	 * collision, this method will return null.
	 */
	private Shape isCollisionWithWorldZone(Shape newShape) {
		ArrayList<Shape> shapes = Simulator.simManager.simState.getGrid();
		for (Shape shape : shapes) {
			if (shape.intersects(newShape))
				return shape;
		}
		return null;
	}
}
