package com.sim.subSystems.entity.entitySystems;

import java.awt.Point;
import java.util.Random;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.IteratingSystem;
import com.sim.Direction;
import com.sim.Simulator;
import com.sim.subSystems.entity.components.CharacterSheet;
import com.sim.subSystems.entity.components.CollisionType;
import com.sim.subSystems.entity.components.CombatState;
import com.sim.subSystems.entity.components.Life;
import com.sim.subSystems.entity.components.TestMind;
import com.sim.subSystems.entity.components.Visible;
import com.sim.subSystems.world.Layer;
import com.sim.subSystems.world.Tile;
import com.sim.subSystems.world.WorldManager;

public class TestMindAI extends IteratingSystem {

	ComponentMapper<TestMind> mTestMind;
	ComponentMapper<Visible> mVisible;
	ComponentMapper<CollisionType> mCollisionType;
	ComponentMapper<Life> mLife;
	ComponentMapper<CombatState> mCombatState;
	ComponentMapper<CharacterSheet> mCharacterSheet;

	private boolean isOperationAllowed;

	public TestMindAI() {
		super(Aspect.all(TestMind.class, Visible.class, CollisionType.class, Life.class,
				CombatState.class, CharacterSheet.class));
		this.isOperationAllowed = true;
	}

	@Override
	protected void process(int entityId) {
		if (isOperationAllowed) {
			// Movement Test
			WorldManager worldManager = Simulator.simManager.simState.getWorldManager();
			Direction directionToGo = this.getRandomDirection();
			Visible visible = mVisible.get(entityId);
			int counter = 0;
			Tile designatedTile = worldManager.getArea().getCurrentLayer()
					.getTile(visible.getX(), visible.getY(), directionToGo);
			boolean isMoving = true;
			while (designatedTile.isSolid() || designatedTile.containAnEntity()) {

				directionToGo = this.getRandomDirection();
				designatedTile = worldManager.getArea().getCurrentLayer()
						.getTile(visible.getX(), visible.getY(), directionToGo);

				// System.out.println("In loop counter = " + counter);
				if (counter > 24) {
					System.out.println("Unable to move entity.");
					isMoving = false;
					break;
				}
				counter++;
			}
			// DON'T UPDATE THE VISIBLE COMPONENT IN HERE!!!!
			Entity currentEntity = worldManager.getEntity(entityId);
			if (isMoving) {
				worldManager.getArea().getCurrentLayer().moveEntityTo(currentEntity,
						directionToGo);
			}

			// Attack Test
			Direction threatDirection = identifyThreatDirection(visible);
			CombatState combatState = mCombatState.get(entityId);
			if (threatDirection != null) {
				combatState.attack(threatDirection);
			}
		}
	}

	/*
	 * Returns whether the given entity is adjacent to another entity. Returns
	 * null if there no entity nearby.
	 */
	private Direction identifyThreatDirection(Visible visible) {
		Point currentPos = new Point(visible.getX(), visible.getY());
		Layer currentLayer = Simulator.simManager.simState.getWorldManager().getArea()
				.getCurrentLayer();
		if (currentLayer.isEntityInTile(currentPos, Direction.NORTH)) {
			return Direction.NORTH;
		} else if (currentLayer.isEntityInTile(currentPos, Direction.EAST)) {
			return Direction.EAST;
		} else if (currentLayer.isEntityInTile(currentPos, Direction.SOUTH)) {
			return Direction.SOUTH;
		} else if (currentLayer.isEntityInTile(currentPos, Direction.WEST)) {
			return Direction.WEST;
		} else if (currentLayer.isEntityInTile(currentPos, Direction.NORTHEAST)) {
			return Direction.NORTHEAST;
		} else if (currentLayer.isEntityInTile(currentPos, Direction.SOUTHEAST)) {
			return Direction.SOUTHEAST;
		} else if (currentLayer.isEntityInTile(currentPos, Direction.SOUTHWEST)) {
			return Direction.SOUTHWEST;
		} else if (currentLayer.isEntityInTile(currentPos, Direction.NORTHWEST)) {
			return Direction.NORTHWEST;
		}
		return null;
	}

	/*
	 * Returns a random direction.. like Whut?
	 */
	private Direction getRandomDirection() {
		int num = Simulator.diceRoller.roll8();
		if (num == 1) {
			return Direction.NORTH;
		} else if (num == 2) {
			return Direction.EAST;
		} else if (num == 3) {
			return Direction.SOUTH;
		} else if (num == 4) {
			return Direction.WEST;
		} else if (num == 5) {
			return Direction.NORTHEAST;
		} else if (num == 6) {
			return Direction.SOUTHEAST;
		} else if (num == 7) {
			return Direction.SOUTHWEST;
		} else if (num == 8) {
			return Direction.NORTHWEST;
		}
		// make compiler happy...
		return null;
	}

	public boolean toggleMindOnOff() {
		this.isOperationAllowed = !this.isOperationAllowed;
		return this.isOperationAllowed;
	}

	public void attack(Direction d) {

	}

}
