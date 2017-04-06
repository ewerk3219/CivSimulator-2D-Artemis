package com.sim;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.util.ResourceLoader;
import org.newdawn.slick.util.ResourceLocation;

public class SimResourceLoader {

	private SpriteSheet terrainTiles;
	private SpriteSheet sprites;
	private SpriteSheet icons;
	public Image sword;
	private String spriteFolderPath = "res/SpriteSheets/";

	public SimResourceLoader() {
		try {
			terrainTiles = new SpriteSheet(loadImage("minecraftCustomTextures"), 16,
					16);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		try {
			sword = loadImage("sword");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		try {
			sprites = new SpriteSheet(loadImage("tiles"), 32, 32);
		} catch (SlickException e) {
			System.out.println("Could not find the DungeonCrawTileSet.png");
			e.printStackTrace();
		}
		try {
			icons = new SpriteSheet(loadImage("icons"), 32, 32);
		} catch (SlickException e) {
			System.out.println("Could not find the IconSet.png");
			e.printStackTrace();
		}
	}

	public SpriteSheet getTerrainTiles() {
		return this.terrainTiles;
	}

	public SpriteSheet getSprites() {
		return this.sprites;
	}

	public SpriteSheet getIcons() {
		return this.icons;
	}

	public Image loadImage(String name) throws SlickException {
		return new Image(spriteFolderPath + name + ".png", false, Image.FILTER_NEAREST);
	}
}
