package com.sim.subSystems.entity.components;

import com.artemis.Component;

public class CollisionType extends Component {

	private boolean isPassable;

	public CollisionType() {

	}

	public CollisionType(boolean isPassable) {
		this.isPassable = isPassable;
	}

	private boolean isPassable() {
		return this.isPassable;
	}
}
