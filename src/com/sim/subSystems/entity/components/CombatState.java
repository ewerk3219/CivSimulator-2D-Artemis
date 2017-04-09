package com.sim.subSystems.entity.components;

import com.artemis.Component;
import com.sim.Direction;

/*
 * A combatState component keeps track of whether the entity is going to attack
 * and in what direction.
 */
public class CombatState extends Component {
	private boolean attackOther;
	private Direction directionToAttack;

	public CombatState() {
		super();
	}

	public CombatState(boolean attackOther, Direction directionToAttack) {
		this.attackOther = attackOther;
		this.directionToAttack = directionToAttack;
	}

	/*
	 * Used to keep track of where the entity is going to attack.
	 */
	public void attack(Direction directionToAttack) {
		this.directionToAttack = directionToAttack;
		this.attackOther = true;
	}

	/*
	 * Used if the entity is interrupted before the attack can be processed.
	 */
	public void interruptAttack() {
		this.attackOther = false;
		directionToAttack = null;
	}

	/*
	 * Resets this component after the attack has been processed. Must be done
	 * after each attack since this only keeps track over one attack per turn.
	 */
	public void reset() {
		this.attackOther = false;
		this.directionToAttack = null;
	}

	public boolean isAttackOther() {
		return this.attackOther;
	}

	public Direction getDirectionToAttack() {
		return this.directionToAttack;
	}
}
