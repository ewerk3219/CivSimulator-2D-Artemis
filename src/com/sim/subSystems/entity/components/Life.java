package com.sim.subSystems.entity.components;

import com.artemis.Component;

public class Life extends Component {

	private int maxHealth;
	private int damageTaken;
	
	public Life() {
		super();
	}

	public Life(int maxHealth) {
		super();
		this.maxHealth = maxHealth;
		this.damageTaken = 0;
	}

	public Life(int maxHealth, int damageTaken) {
		super();
		this.maxHealth = maxHealth;
		this.damageTaken = damageTaken;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public int getDamageTaken() {
		return damageTaken;
	}

	public void setDamageTaken(int damageTaken) {
		this.damageTaken = damageTaken;
	}

	/*
	 * Returns true or false based on whether the damage taken has exceeded the
	 * maximum health.
	 */
	public boolean isDead() {
		return damageTaken >= maxHealth;
	}

	public int getHealth() {
		return this.maxHealth - this.damageTaken;
	}

	public boolean dealDamage(int deltaDamageTaken) {
		this.damageTaken += damageTaken;
		return this.isDead();
	}

}
