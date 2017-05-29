package com.sim.subSystems.world.worldParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.newdawn.slick.Color;
import com.sim.subSystems.world.Layer;
import com.sim.subSystems.world.Tile;

/*
 * This class' only job is to parse text documents with either the character 's' or the character 'o'.
 * the 's' meaning solid and the 'o' meaning open
 */
public class TextFileWorldParser {

	public static Layer parseLayer(String path) {
		try {
			Scanner lineScan = new Scanner(
					new FileReader("res/textMaps/" + path + ".txt"));
			ArrayList<boolean[]> array = new ArrayList<boolean[]>();
			while (lineScan.hasNext()) {
				String line = lineScan.nextLine();
				int length = line.length();
				boolean[] b = new boolean[length];
				for (int i = 0; i < length; i++) {
					char c = line.charAt(i);
					if (c == 's') {
						b[i] = true;
					} else {
						b[i] = false;
					}
				}
				array.add(b);
			}
			lineScan.close();

			Layer layer = new Layer(array.get(0).length, array.size());
			Tile[][] tileGrid = layer.getLayerGrid();
			for (int x = 0; x < layer.getLayerGrid().length; x++) {
				for (int y = 0; y < layer.getLayerGrid()[0].length; y++) {
					boolean isSolid = array.get(y)[x];
					Tile tile = new Tile(x, y, isSolid, Color.magenta);
					tileGrid[x][y] = tile;
					if (isSolid) { // Add some differences to the representation
						tile.setTerrainColor(Color.darkGray);
					} else {
						tile.setTerrainColor(Color.gray);
					}
				}
			}
			return layer;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; // failed to parse
	}
}