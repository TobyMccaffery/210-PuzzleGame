package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.ArrayList;

/**
 * This class represents the gas trailing behind the enemy's
 * recent path
 */

public class Gas extends Tile {
    public static ArrayList<Gas> gasObjects = new ArrayList<Gas>();
    public boolean active = true;

    /**
     * Constructs the gas and places/removes it after a few seconds
     * @param x x-axis location
     * @param y y-axis location
     */
    public Gas(int x, int y) {
        super(x,y,"gas",new TextureRegion( LevelObject.textureAtlass.findRegion("gas-new")), new TextureAtlas(Gdx.files.internal("TxAtlas.atlas")));
        gasObjects.add(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    active = false;
                    gasObjects.remove(this);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }



}
