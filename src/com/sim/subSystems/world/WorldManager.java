package com.sim.subSystems.world;

import java.awt.Point;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import com.artemis.Entity;
import com.artemis.EntityEdit;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.sim.Direction;
import com.sim.Simulator;
import com.sim.subSystems.entity.components.CharacterSheet;
import com.sim.subSystems.entity.components.CollisionType;
import com.sim.subSystems.entity.components.CombatState;
import com.sim.subSystems.entity.components.Life;
import com.sim.subSystems.entity.components.TestMind;
import com.sim.subSystems.entity.components.Visible;
import com.sim.subSystems.entity.entitySystems.Combat;
import com.sim.subSystems.entity.entitySystems.TestMindAI;
import com.sim.subSystems.world.generators.WorldGenerator;
import com.sim.subSystems.world.worldParser.TextFileWorldParser;

public class WorldManager {

	private String worldName;

	private World world;
	private Area area;

	/**
	 * Will initialize an empty world without an area.
	 */
	public WorldManager() {
		WorldConfiguration config = new WorldConfigurationBuilder()
				.with(new TestMindAI(), new Combat()).build();
		world = new World(config);
		worldName = "Unknown";
	}

	/**
	 * Loads a saved world from the given path.
	 */
	public WorldManager(String path) {
		this();
		area = new Area();
		area.addLayer(TextFileWorldParser.parseLayer(path));
	}

	/**
	 * 
	 * Creates an empty or auto generated world of the given size (length and
	 * width).
	 * 
	 * @param name
	 *            Name of the World.
	 * @param shouldAutoGenLayer
	 *            True if the first layer is auto generated.
	 * @param length
	 *            Length of the first layer.
	 * @param width
	 *            width of the first layer.
	 */
	public WorldManager(String name, boolean shouldAutoGenLayer, int length,
			int width) {
		this();
		worldName = name;
		area = new Area();
		if (shouldAutoGenLayer) {
			area.addLayer(new WorldGenerator().generateWorldSpace(length, width));
		} else {
			area.addLayer(new Layer(length, width));
		}
	}

	/**
	 * If the area has not been initialized yet (zero argument constructor) use
	 * this first before doing anything.
	 * 
	 * @throws IllegalStateException
	 *             If method called when area has already been initialized.
	 */
	public void initArea() {
		if (area != null) {
			throw new IllegalStateException(
					"Only use this method if 'area' has not been initialized yet.");
		}
		area = new Area();
	}

	public void process(float delta) {
		this.world.setDelta(delta / 1000.0f);
		this.world.process();
	}

	/**
	 * Currently a test method, not fully implemented yet.
	 */
	public void addEntity(int x, int y) {
		Entity e = world.createEntity();
		EntityEdit ed = world.edit(e.getId());
		int spriteX = 29;
		int spriteY = 1;
		checkForNullPointers(spriteX, spriteY);
		Image appearance = Simulator.simManager.map.resourceLoader.getSprites()
				.getSprite(spriteX, spriteY);
		ed.add(new CollisionType(true));
		ed.add(new Visible(x, y, true, appearance));
		ed.add(new TestMind());
		ed.add(new Life(10));
		ed.add(new CombatState());
		ed.add(new CharacterSheet(10, 10, 10, 10, 10, 10, 5, 10));

		// add entities to world
		area.getTile(x, y).setOccupantEntity(e);
	}

	private void checkForNullPointers(int spriteX, int spriteY) {
		if (Simulator.simManager == null) {
			System.out.println("simManager is null");
		}
		if (Simulator.simManager.map == null) {
			System.out.println("map is null");
		}
		if (Simulator.simManager.map.resourceLoader == null) {
			System.out.println("resourceLoader is null");
		}
		if (Simulator.simManager.map.resourceLoader.getSprites() == null) {
			System.out.println("sprites object is null");
		}
		if (Simulator.simManager.map.resourceLoader.getSprites().getSprite(spriteX,
				spriteY) == null) {
			System.out.println("Image is null");
		}
	}

	public Entity getEntity(int entityId) {
		return this.world.getEntity(entityId);
	}

	public boolean toggleMindOnOff() {
		return this.world.getSystem(TestMindAI.class).toggleMindOnOff();
	}

	public void generateNewLayer(int size) {
		this.area.addLayer(new WorldGenerator().generateWorldSpace(size, size));
	}

	public void setCurrentLayer(int layer) {
		this.area.setCurrentLayer(layer);
	}

	public int getGridWidth() {
		return area.getGridWidth();
	}

	public int getGridHeight() {
		return area.getGridHeight();
	}

	public Tile getTile(int x, int y) {
		return area.getTile(x, y);
	}

	public Tile getTile(int x, int y, Direction d) {
		return area.getTile(x, y, d);
	}

	public boolean isEntityInTile(Point p, Direction directionToLook) {
		return area.isEntityInTile(p, directionToLook);
	}

	public boolean isEntityInTile(int x, int y, Direction directionToLook) {
		return area.isEntityInTile(x, y, directionToLook);
	}

	public boolean isEntityInTile(int x, int y) {
		return area.isEntityInTile(x, y);
	}

	public void moveEntityTo(Entity e, Direction directionToGo) {
		area.moveEntityTo(e, directionToGo);
	}

	public void moveEntityTo(Entity e, int x, int y) {
		area.moveEntityTo(e, x, y);
	}

	public void renderBlock(Graphics g, int standardUnit, int startX, int startY,
			int endX, int endY) {
		area.renderBlock(g, standardUnit, startX, startY, endX, endY);
	}

	// --------------- Statics --------------- \\

	/**
	 * Translates grid-X coordinate to renderCoordinate-X for tile grid only.
	 * 
	 * @param gridX
	 *            x-axis coordinate value.
	 */
	public static int gridXToRenderCoordX(int gridX) {
		int renderX = gridX
				* Simulator.simManager.simState.getRenderManager().standardUnit
				+ Simulator.simManager.simState.getRenderManager().getRenderX();
		return renderX;
	}

	/**
	 * Translates grid-Y coordinate to renderCoordinate-Y for tile grid only.
	 * 
	 * @param gridY
	 *            y-axis coordinate value.
	 */
	public static int gridYToRenderCoordY(int gridY) {
		int renderY = gridY
				* Simulator.simManager.simState.getRenderManager().standardUnit
				+ Simulator.simManager.simState.getRenderManager().getRenderY();
		return renderY;
	}

	/**
	 * Translates render-X coordinate to grid-X coordinate for tile grid only.
	 * 
	 * @param renderX
	 *            x-axis coordinate value for rendering.
	 */
	public static int renderXToGridX(int renderX) {
		int gridX = (renderX
				- Simulator.simManager.simState.getRenderManager().getRenderX())
				/ Simulator.simManager.simState.getRenderManager().standardUnit;
		return gridX;
	}

	/**
	 * Translates render-Y coordinate to grid-Y coordinate for tile grid only.
	 * 
	 * @param renderY
	 *            y-axis coordinate value for rendering.
	 */
	public static int renderYToGridY(int renderY) {
		int gridY = (renderY
				- Simulator.simManager.simState.getRenderManager().getRenderY())
				/ Simulator.simManager.simState.getRenderManager().standardUnit;
		return gridY;
	}

	/**
	 * Returns the String name of the world.
	 */
	public String getWorldName() {
		return this.worldName;
	}
}