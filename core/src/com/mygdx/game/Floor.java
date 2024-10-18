package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

/**
 * Class for floor tiles including setting each flor tiles position and image
 */
public class Floor extends Tile{
    private int num = 0;

    /**
     * Floor constructor setting the position of the floor tile and its image
     * @param x x-axis location of floor tile
     * @param y y-axis location of floor tile
     */
    public Floor(int x, int y){
        super(x, y, "floor",new TextureRegion( LevelObject.textureAtlass.findRegion("ButtonNotPressed")) ,new TextureAtlas(Gdx.files.internal("TxAtlas.atlas")) );
        setFloor();
    }

    /**
     * Sets floor image asset based on randomly generated number
     */

    private void setFloor() {
        num = new Random().nextInt(8);
        switch (num) {
//            case 0:
//                setImg(new TextureRegion(textureAtlas.findRegion("dungeon1")));
//                break;
//            case 1:
//                setImg(new TextureRegion(textureAtlas.findRegion("dungeon3")));
//                break;
//            case 2:
//                setImg(new TextureRegion(textureAtlas.findRegion("dungeon2")));
//                break;
//            case 3:
//                setImg(new TextureRegion(textureAtlas.findRegion("dungeon1")));
//                break;
//            case 4:
//                setImg(new TextureRegion(textureAtlas.findRegion("dungeon1")));
//                break;
//            case 5:
//                setImg(new TextureRegion(textureAtlas.findRegion("dungeon1")));
//                break;
//            case 6:
//                setImg(new TextureRegion(textureAtlas.findRegion("dungeon1")));
//                break;
//            case 7:
//                setImg(new TextureRegion(textureAtlas.findRegion("dungeon1")));
//                break;
        }
    }
}
