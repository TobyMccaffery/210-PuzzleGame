package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.Date;

/**
 * Class representing a lever object used to open/close doors
 */
public class Lever extends Interactable {
    private boolean state;
    private long lastPressed = new Date().getTime();
    private int delay = 1000;

    private ArrayList<Interactable> interactables = new ArrayList<Interactable>();

    private TextureRegion on = textureAtlas.findRegion("LeverOn");
    private TextureRegion off = textureAtlas.findRegion("LeverOff");

    /**
     * The constructor of the lever object. Sets the location and image of the lever
     * @param x is the x-axis location of the lever
     * @param y is the y-axis location of the lever
     * @param img is the image of the lever
     */
    public Lever(int x, int y, TextureRegion img)
    {
        super(x, y, "lever", new TextureRegion( LevelObject.textureAtlas.findRegion("LeverOff")) ,new TextureAtlas(Gdx.files.internal("TxAtlas.atlas")), false);
        state = false;
        img = off;
    }

    /**
     * Process to carry out upon character interaction with the lever
     */
    public void toggle(){
        if ((new Date().getTime() - lastPressed) > delay) {
            state = !state;
            changePNG();
            changeInteractables();
            lastPressed = new Date().getTime();
        }
    }

    /**
     * Return the state of the lever
     * @return state of lever
     */
    public boolean getState()
    {
        return state;
    }

    /**
     * To change image of lever upon interaction
     */
    public void changePNG()
    {
        if(state)
        {
            this.setImg(on);
        }
        else
        {
            this.setImg(off);
        }
    }

    /**
     * To change the state of any associated interactable object
     */
    public void changeInteractables(){
        for (Interactable i : this.interactables){
            i.changeState();
            i.interact();
        }
    }

    /**
     * To add an interactable object to the list of interactables
     * @param object the interactable to add to the list
     */
    public void addInteractable(Interactable object){
        this.interactables.add(object);
    }
}
