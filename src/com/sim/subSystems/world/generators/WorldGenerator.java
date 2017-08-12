package com.sim.subSystems.world.generators;

import org.newdawn.slick.Color;
import com.flowpowered.noise.Noise;
import com.flowpowered.noise.NoiseQuality;
import com.sim.Direction;
import com.sim.subSystems.world.Layer;
import com.sim.subSystems.world.Tile;

public class WorldGenerator {

	public static final int PERCENT_TO_MAKE_SOLID = 3;
	public static final int NOISE_SEED = 5;
	public static final int INTERPOLATION_LOOP_COUNT = 4;
	public static final double POWER = 1.51;

	/**
	 * 
	 * @param length
	 *            length of Layer
	 * @param width
	 *            width of Layer
	 * @return An auto generated Layer of the chosen size.
	 */
	public Layer generateWorldSpace(int length, int width) {
		Layer world = new Layer(length, width);
		world.testInitTiles();
		for (int x = 0; x < world.getLayerGrid().length; x++) {
			for (int y = 0; y < world.getLayerGrid()[0].length; y++) {
				Tile tile = world.getTile(x, y);
				double perlinX = x / 10.0;
				double perlinY = y / 10.0;
				double height = Noise.valueCoherentNoise3D(perlinX, perlinY, 1,
						NOISE_SEED, NoiseQuality.BEST)
						+ 0.5 * Noise.valueCoherentNoise3D(2 * perlinX, 2 * perlinY, 1,
								NOISE_SEED, NoiseQuality.BEST)
						+ 0.25 * Noise.valueCoherentNoise3D(4 * perlinX, 2 * perlinY, 1,
								NOISE_SEED, NoiseQuality.BEST);
				height = Math.pow(height + 2, POWER);
				tile.setHeight(height);
			}
		}
		for (int i = 0; i < INTERPOLATION_LOOP_COUNT; i++) {
			interpolateHeightNum(world);
			// interpolateHeightNumGradientNoise(grassField);
		}
		setColorOfTileAccordingToHeight(world);
		return world;
	}

	private void interpolateHeightNumGradientNoise(Layer world) {
		for (int l = 0; l < world.getLayerGrid().length; l++) {
			for (int w = 0; w < world.getLayerGrid()[0].length; w++) {
				Tile adjacentTile = world.getRandomAdjacentTile(world, l, w);
				if (adjacentTile == null) {
					System.out.println(
							"ERROR in GrassField Generator: adjacent tile should not be null!");
					throw new IllegalArgumentException(
							"Tile center at: (" + l + ", " + w + ")");
				}
				Tile centerTile = world.getTile(l, w);
				double interHeight = Noise.gradientNoise3D(l, w, centerTile.getHeight(),
						adjacentTile.getX(), adjacentTile.getY(),
						(int) Math.round(adjacentTile.getHeight() * 10), NOISE_SEED);
				interHeight = (interHeight + centerTile.getHeight()) / 2;
				centerTile.setHeight(interHeight);
			}
		}
	}

	private void interpolateHeightNum(Layer layerGrid) {
		for (int l = 0; l < layerGrid.getLayerGrid().length; l++) {
			for (int w = 0; w < layerGrid.getLayerGrid()[0].length; w++) {

				// Get the tiles surrounding the currentTile
				Tile[] allDirections = new Tile[9];
				allDirections[0] = layerGrid.getTile(l, w, Direction.NORTH);
				allDirections[1] = layerGrid.getTile(l, w, Direction.NORTHWEST);
				allDirections[2] = layerGrid.getTile(l, w, Direction.WEST);
				allDirections[3] = layerGrid.getTile(l, w, Direction.SOUTHWEST);
				allDirections[4] = layerGrid.getTile(l, w, Direction.SOUTH);
				allDirections[5] = layerGrid.getTile(l, w, Direction.SOUTHEAST);
				allDirections[6] = layerGrid.getTile(l, w, Direction.EAST);
				allDirections[7] = layerGrid.getTile(l, w, Direction.NORTHEAST);
				allDirections[8] = layerGrid.getTile(l, w); // current tile

				// Sum up total height numbers.
				double heightSum = 0.0;
				int totalExistantTiles = 1; // since the current tile counts
				for (Tile tile : allDirections) {
					if (tile != null) {
						totalExistantTiles++;
						heightSum += tile.getHeight();
					}
				}

				double average = heightSum / totalExistantTiles;
				layerGrid.getTile(l, w).setHeight(average);
			}
		}
	}

	private void setColorOfTileAccordingToHeight(Layer world) {

		double min = world.getMinHeight();
		double max = world.getMaxHeight();

		System.out.println("Min: " + min);
		System.out.println("max: " + max);
		System.out.println();

		double pseudoMax = Math.abs(min) + max;

		// System.out.println("Pseudo Min: " + pseudoMin);
		// System.out.println("Pseudo max: " + pseudoMax);

		for (int l = 0; l < world.getLayerGrid().length; l++) {
			for (int w = 0; w < world.getLayerGrid()[0].length; w++) {
				Tile tile = world.getTile(l, w);
				if (tile.isSolid()) {
					tile.setTerrainColor(Color.black);
				} else {
					double height = tile.getHeight();
					height += Math.abs(min);
					int percentHeight = (int) Math.round((height / pseudoMax) * 100);

					if (percentHeight < 40) {
						tile.setTerrainColor(Color.blue.darker(0.2f));
					} else if (percentHeight < 45) {
						tile.setTerrainColor(Color.blue);
					} else if (percentHeight < 50) {
						tile.setTerrainColor(Color.yellow);
					} else if (percentHeight < 75) {
						tile.setTerrainColor(Color.green.darker(0.1f));
					} else if (percentHeight < 80) {
						tile.setTerrainColor(Color.gray);
					} else if (percentHeight < 90) {
						tile.setTerrainColor(Color.darkGray);
					} else {
						tile.setTerrainColor(Color.white);
					}
					// Color tileColor = new Color(0, 0, 0);
					// tile.setTerrainColor(
					// new Color((int) Math.round(tile.getHeight() * 100000)));
				}
			}
		}
	}
}