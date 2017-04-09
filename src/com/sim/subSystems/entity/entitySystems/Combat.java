package com.sim.subSystems.entity.entitySystems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.IteratingSystem;
import com.sim.Simulator;
import com.sim.subSystems.entity.components.CharacterSheet;
import com.sim.subSystems.entity.components.CombatState;
import com.sim.subSystems.entity.components.Life;
import com.sim.subSystems.entity.components.Visible;
import com.sim.subSystems.world.Layer;
import com.sim.subSystems.world.Tile;

public class Combat extends IteratingSystem {

	private ComponentMapper<Life> mLife;
	private ComponentMapper<CombatState> mCombatState;
	private ComponentMapper<Visible> mVisible;
	private ComponentMapper<CharacterSheet> mCharacterSheet;

	public static final int CRITICAL_HIT = 20;

	public Combat() {
		super(Aspect.all(Life.class, CombatState.class, Visible.class,
				CharacterSheet.class));
	}

	@Override
	protected void process(int entityId) {
		Layer currentLayer = Simulator.simManager.simState.getWorldManager().getArea()
				.getCurrentLayer();
		Visible visible = mVisible.get(entityId);
		CombatState combatState = mCombatState.get(entityId);
		if (combatState.isAttackOther() && currentLayer.isEntityInTile(visible.getX(),
				visible.getY(), combatState.getDirectionToAttack())) {

			// get character sheets
			CharacterSheet csAttacker = mCharacterSheet.get(entityId);

			Tile defenderTile = currentLayer.getTile(visible.getX(), visible.getY(),
					combatState.getDirectionToAttack());
			Entity entityDefender = defenderTile.getEntity();
			CharacterSheet csDefender = mCharacterSheet.get(entityDefender);

			// get damage to entity
			int damageToOther = calculateDamage(csAttacker, csDefender);

			// do damage to other entity.
			Life defenderLife = defenderTile.getEntity().getComponent(Life.class);
			defenderLife.dealDamage(damageToOther);

			// check if dead
			if (defenderLife.getDamageTaken() >= defenderLife.getMaxHealth()) {
				entityDefender.deleteFromWorld();
				Visible defenderVisible = entityDefender.getComponent(Visible.class);
				currentLayer.getTile(defenderVisible.getX(), defenderVisible.getY())
						.removeEntity();
				System.out.println("Death");
			}
		}
		combatState.reset();
	}

	private int calculateDamage(CharacterSheet csAttacker, CharacterSheet csDefender) {
		int attackerToHit = csAttacker.getBaseAttack()
				+ Simulator.diceRoller.roll20WithCrit(CRITICAL_HIT);
		int defenderAC = csDefender.getArmorClass();

		// If the attacker successfully hits
		if (attackerToHit > defenderAC) {
			// System.out.println("successful hit");
			int damage = Simulator.diceRoller.roll4() + csAttacker.getStrengthMod();
			if (attackerToHit == CRITICAL_HIT) {
				damage *= 2;
				// System.out.println("Critical!");
			}
			return damage;
		} else
			return 0;
	}
}
