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
import com.sim.subSystems.world.Tile;
import com.sim.subSystems.world.WorldManager;

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
		WorldManager manager = Simulator.simManager.simState.getWorldManager();
		Visible visible = mVisible.get(entityId);
		CombatState combatState = mCombatState.get(entityId);
		if (combatState.isAttackOther()
				&& manager.isEntityInTile(visible.getX(), visible.getY(),
						combatState.getDirectionToAttack())) {

			// get character sheets
			CharacterSheet csAttacker = mCharacterSheet.get(entityId);

			Tile defenderTile = manager.getTile(visible.getX(), visible.getY(),
					combatState.getDirectionToAttack());
			Entity entityDefender = defenderTile.getOccupantEntity();
			CharacterSheet csDefender = mCharacterSheet.get(entityDefender);

			// get damage to entity
			int damageToOther = calculateDamage(csAttacker, csDefender);

			// do damage to other entity.
			Life defenderLife = defenderTile.getOccupantEntity()
					.getComponent(Life.class);
			defenderLife.dealDamage(damageToOther);
			System.out.println(
					"Defender Health Damage on hit = " + damageToOther);
			System.out.println(
					"Defender Damage Taken = " + defenderLife.getDamageTaken());
			System.out.println("Defender Health = " + defenderLife.getHealth());

			// check if dead
			if (defenderLife.isDead()) {
				entityDefender.deleteFromWorld();
				Visible defenderVisible = entityDefender
						.getComponent(Visible.class);

				// Important to remove entity from game world as well, otherwise
				// fatal error will occur
				manager.getTile(defenderVisible.getX(), defenderVisible.getY())
						.removeOccupantEntity();
				System.out.println("Death");
			}
		}
		combatState.reset();
	}

	private int calculateDamage(CharacterSheet csAttacker,
			CharacterSheet csDefender) {
		int attackerToHit = csAttacker.getBaseAttack()
				+ Simulator.diceRoller.roll20WithCrit(CRITICAL_HIT);
		int defenderAC = csDefender.getArmorClass();

		// If the attacker successfully hits
		if (attackerToHit > defenderAC) {
			// System.out.println("successful hit");
			int damage = Simulator.diceRoller.roll4()
					+ csAttacker.getStrengthMod();
			if (attackerToHit == CRITICAL_HIT) {
				damage *= 2;
				System.out.println("Critical!");
			}
			return damage;
		} else
			return 0;
	}
}
