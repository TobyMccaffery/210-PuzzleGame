package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Represents the gas in place of usual tiles in the map
 */
public class FloorGas extends Tile {

    private boolean gasMaskOn = false;

    /**
     * Constructor for floor gas object including setting its location and image
     * @param x x-axis location of floor gas object
     * @param y y-axis location of floor gas object
     */

    public FloorGas(int x, int y) {
        super(x, y, "floorgas", new TextureRegion(LevelObject.textureAtlass.findRegion("GasFloor")), new TextureAtlas(Gdx.files.internal("TxAtlas.atlas")));
    }




    }