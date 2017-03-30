package com.sim.subSystems.entity.components;

import com.artemis.Component;
import com.sim.subSystems.entity.util.Vector2D;

public class Velocity extends Component {

	private Vector2D velocityVector;

	public Velocity() {
		super();
		this.velocityVector = new Vector2D(0, 0);
	}

	public Velocity(Vector2D vec) {
		super();
		this.velocityVector = vec;
	}

	public Vector2D getVector() {
		return this.velocityVector;
	}
}
