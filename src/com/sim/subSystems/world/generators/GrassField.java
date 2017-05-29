package com.sim.subSystems.world.generators;

import org.newdawn.slick.Color;

import com.flowpowered.noise.Noise;
import com.flowpowered.noise.NoiseQuality;
import com.sim.Direction;
import com.sim.Simulator;
import com.sim.subSystems.world.Layer;
import com.sim.subSystems.world.Tile;

public class GrassField {

	public static final int PERCENT_TO_MAKE_SOLID = 3;
	public static final int NOISE_SEED = 5;
	public static final int INTERPOLATION_LOOP_COUNT = 8;

	@SuppressWarnings("unused")
	public Layer generateGrassField(int length, int width) {
		Layer grassField = new Layer(length, width);
		grassField.testInitTiles();

		for (int l = 0; l < grassField.getLayerGrid().length; l++) {
			for (int w = 0; w < grassField.getLayerGrid()[0].length; w++) {
				Tile tile = grassField.getTile(l, w);
				tile.setHeight(Noise.valueCoherentNoise3D(l, w, 1, NOISE_SEED,
						NoiseQuality.BEST));
				if (Simulator.diceRoller.roll100() < PERCENT_TO_MAKE_SOLID) {
					tile.setSolid(true);
				}
			}
		}
		if (INTERPOLATION_LOOP_COUNT == 0) {
			for (int l = 0; l < grassField.getLayerGrid().length; l++) {
				for (int w = 0; w < grassField.getLayerGrid()[0].length; w++) {
					Tile tile = grassField.getTile(l, w);
					setColorOfTileAccordingToHeight(tile);
				}
			}
		}
		for (int i = 0; i < INTERPOLATION_LOOP_COUNT; i++) {
			interpolateHeightNum(grassField);
		}
		return grassField;
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
				setColorOfTileAccordingToHeight(layerGrid.getTile(l, w));
			}
		}
	}

	private void setColorOfTileAccordingToHeight(Tile tile) {
		if (tile.isSolid()) {
			tile.setTerrainColor(Color.black);
		} else {
			/*
			 * int height = (int) Math.round(tile.getHeight() * 100); if (height
			 * < 0) { tile.setTerrainColor(Color.blue); } else if (height < 10)
			 * { tile.setTerrainColor(Color.yellow); } else if (height < 60) {
			 * tile.setTerrainColor(Color.green); } else if (height < 75) {
			 * tile.setTerrainColor(Color.gray); } else if (height < 90) {
			 * tile.setTerrainColor(Color.darkGray); } else {
			 * tile.setTerrainColor(Color.white); }
			 */
			tile.setTerrainColor(new Color((int) Math.round(tile.getHeight() * 10000)));
		}
	}
}