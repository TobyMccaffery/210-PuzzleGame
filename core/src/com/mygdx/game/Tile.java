package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * This class represents a tile. It is the super class to multiple classes/objects
 * that make up the setting/map including the walls, different floor objects etc
 */
public class Tile extends LevelObject {
    private int posX;
    private int posY;

    /**
     * This is the constructor of a tile where we set its location, its name, its
     * image and the textured atlas (where its image is extracted from)
     * @param x x-axis position
     * @param y y-axis position
     * @param name Name of the tile object
     * @param img Image of the tile
     * @param textureAtlas Atlas pack of images
     */

    public Tile(int x, int y, String name, TextureRegion img, TextureAtlas textureAtlas){
        super(x, y, name, img, textureAtlas);
        posX = x;
        posY = y;
    }
}
