package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.Date;

/**
 * This class includes the methods and variables common to all
 * button interactable objects (primarily used to open and close doors
 * but also used to set levels)
 */

public class Button extends Interactable{
    private boolean state;
    private TextureAtlas textureAtlass = new TextureAtlas(Gdx.files.internal("TxAtlas.atlas"));
    private long lastPressed = new Date().getTime();
    private int delay = 1000;
    private boolean timeRanOut;
    private MyGdxGame.levels forcedLevel = null;
    private boolean isQuit = false;

    private ArrayList<Interactable> interactables = new ArrayList<>();
    //textures TODO
    private TextureRegion pressed = textureAtlass.findRegion("ButtonPressed");
    private TextureRegion notPressed = textureAtlass.findRegion("ButtonNotPressed");

    /**
     * Constructor for a button object including setting its position and image
     * @param x x-axis location of the button
     * @param y y-axis location of the button
     * @param img Image of the button
     */
    public Button (int x, int y, TextureRegion img){
        super(x, y, "button", new TextureRegion(LevelObject.textureAtlas.findRegion("ButtonNotPressed")) ,new TextureAtlas(Gdx.files.internal("TxAtlas.atlas")), false);

        state = false;
        img = notPressed;
        timeRanOut = true;
    }

    /**
     * Process when the button is toggled ie turned on or turned off
     */
    public void toggle(){
        if((new Date().getTime() - lastPressed) > delay){
            if(!state){
                state = true;
                changePNG();
                changeInteractables();
                lastPressed = new Date().getTime();
                timeRanOut = false;
                if (forcedLevel != null){
                    MyGdxGame.curLevel = this.forcedLevel;
                    MyGdxGame.clear();
                }
                if (isQuit){
                    System.exit(0);
                }
            }
        }
    }

    /**
     * To turn the button back (ie toggle again) after 10 seconds
     */
    public void checkunToggle(){

        if((new Date().getTime() - lastPressed) > 10000 && !timeRanOut){
            state = !state;
            timeRanOut = true;
            changePNG();
            changeInteractables();
        }
    }

    /**
     * To return the state of the button
     * @return state flag
     */
    public boolean getState () {
        return state;
    }

    /**
     * Method to change the button image
     */
    public void changePNG(){
        if(state){
            this.setImg(pressed);
        } else {
            this.setImg(notPressed);
        }
    }

    /**
     * To change the state of the interactable the button
     * is associated with and to complete the method upon interaction
     */
    public void changeInteractables(){
        for(Interactable i : this.interactables){
            i.changeState();
            i.interact();
        }
    }

    /**
     * Add an interactable object to the list of interactables
     * @param object is the interactable object to add to the list
     */
    public void addInteractable(Interactable object) {
        this.interactables.add(object);
    }

    /**
     * To set the button to the one that allows a player to go to a particular level
     * @param level is the level to set to
     */

    public void setLevel(MyGdxGame.levels level){
        this.forcedLevel = level;
    }

    /**
     * To set the button to the one that allows a player to quit
     */

    public void makeQuitButton(){
        this.isQuit = true;
    }
}
