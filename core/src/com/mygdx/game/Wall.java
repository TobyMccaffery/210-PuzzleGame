package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.w3c.dom.css.Rect;
import java.awt.*;
import java.util.ArrayList;


/**
 * This class essentially allows new walls to be created,
 * added to appropriate lists and drawn
 */
public class Wall extends Tile {

    ShapeRenderer shapeRenderer; //Object to draw walls with specific colour and dimensions

    private static ArrayList<Wall> walls = new ArrayList<Wall>(); //Object to draw walls with specific colour and dimensions

    /**
     * Constructor for a wall object where we set is location and its shape renderer(to draw)
     * @param x x-axis location of a wall
     * @param y y-axis location of a wall
     */
    public Wall(int x, int y){
        super(x, y, "wall",new TextureRegion( LevelObject.textureAtlass.findRegion("floor1")) ,new TextureAtlas(Gdx.files.internal("TempTextures.atlas")));
        shapeRenderer = new ShapeRenderer();
    }

    /**
     * This method draws a wall according to RGBA colour values,
     * x & y co-ordinates and width and height
     * @param r is the r value for colour
     * @param g is the g value for colour
     * @param b is the b value for colour
     * @param a is the a value for colour
     */

    public void draw(float r, float g, float b, float a){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(r, g, b, a);
        shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());
        shapeRenderer.end();
    }

    /**
     * Instances a wall using passed in values and
     * adds them to wall and level objects arrayLists
     * @param x x-axis location of wall
     * @param y y-axis location of wall
     * @param width Width dimension of wall
     * @param height Height dimension of wall
     */
    public void createWall(int x, int y, int width, int height) {
        Wall wall = new Wall(x, y);
        walls.add(wall);
        ArrayList levelObjects = getLevelObjects();
        levelObjects.add(wall);
    }

    /**
     * Returns list of walls
     */
    public static ArrayList<Wall> getWalls() {
        return walls;
    }

    /**
     * Clears wall arrayList
     */
    public static void clearWalls(){
        walls.clear();
    }
}
