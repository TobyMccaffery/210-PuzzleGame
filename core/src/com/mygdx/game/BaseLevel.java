package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import javax.swing.*;
import java.util.ArrayList;


/**
 * BaseLevel class which includes all instance variables and
 * methods which are common in each level(many of these methods are overwritten)
 */

public class BaseLevel {
    public static boolean deathFlag = false;
    public static boolean exitPlateOne = false;
    public static boolean exitPlateTwo = false;

    public static ArrayList<LevelObject> levelObjects = new ArrayList<>(); //List to store all level Objects
    public static ArrayList<LevelObject> drawingObjects = new ArrayList<>(); //arrays for drawing objects

    private Texture arrows = new Texture("arrows.png");
    private Texture wasd = new Texture("wasd.png");
    protected Texture pause = new Texture("pause.png");

    /**
     * Base level empty constructor
     */
    BaseLevel() {

    }

    /**
     * Abstract create method ( to instance objects) for levels
     */

    public void Create() {
    }

    /**
     * Abstract render method (to draw and move objects) for levels
     */

    public void Render() {
    }

    /**
     * To clear/reset a level when current level number is changing
     */

    public void ClearLevel() {
        levelObjects.clear();
        drawingObjects.clear();
        Gas.gasObjects.clear();
        Wall.clearWalls();
        Character.polygonList.clear();
        Character.rectangleList.clear();
    }

    public void renderPauseScreen(Batch batch){
        batch.draw(new Texture("menu.png"), Gdx.graphics.getWidth()/2 - 250, Gdx.graphics.getHeight()/2 - 125, 500, 250);
    }

    public void drawEntities(SpriteBatch batch){
        for (LevelObject e : this.drawingObjects){
            //if it is not the enemy
            if(!(e instanceof Enemy)){
                batch.draw(e.getImg(), (int)(e.getX()), (int)(e.getY()), (int)(20), (int)(20));
            }
        }
    }

    public void drawEnemy(SpriteBatch batch){
        for (LevelObject e : this.drawingObjects){
            if(e instanceof Enemy){
                batch.draw(e.getImg(), (int)(e.getX()), (int)(e.getY()), (int)(20), (int)(20));
            }
        }
    }

    protected void renderUI(SpriteBatch batch){
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        batch.setColor(1f, 1f, 1f, 0.2f);
        batch.draw(wasd, 10, 10, 100, 100);
        batch.draw(arrows, Gdx.graphics.getWidth() - 100 - 10, 10, 100, 100);
        batch.draw(pause, 10, Gdx.graphics.getHeight() - 60, 200, 50);
        batch.setColor(1f, 1f, 1f, 1f);
    }

}
