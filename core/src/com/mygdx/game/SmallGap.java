package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * This class represents small gaps in the maps that characters
 * can only pass after drinking small potion items
 */
public class SmallGap extends Tile {

    private boolean SmallPotOn = false;

    /**
     * Constructor for small gap including setting its position and image
     * @param x x-axis location of small gap
     * @param y y-axis location of small gap
     * @param name name of small gap object
     * @param img Image of small gap object
     */

    public SmallGap (int x, int y, String name, TextureRegion img) {
        super(x, y, name, new TextureRegion(LevelObject.textureAtlas.findRegion("SmallGap")), new TextureAtlas(Gdx.files.internal("TxAtlas.atlas")));
    }



}