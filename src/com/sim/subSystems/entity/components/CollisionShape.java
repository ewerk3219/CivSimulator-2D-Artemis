package com.sim.subSystems.entity.components;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

import com.artemis.Component;
import com.sim.simStates.SimulationState;

public class CollisionShape extends Component {

	private Shape shape;

	public CollisionShape() {
		super();
		this.shape = new Circle(0, 0, SimulationState.STANDARD_UNIT / 3);
	}

	public CollisionShape(Shape shape) {
		super();
		this.shape = shape;
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public void setCenterX(float newX) {
		this.shape.setCenterX(newX);
	}

	public void setCenterY(float newY) {
		this.shape.setCenterY(newY);
	}

	public float getCenterX() {
		return this.shape.getCenterX();
	}

	public float getCenterY() {
		return this.shape.getCenterY();
	}

}
