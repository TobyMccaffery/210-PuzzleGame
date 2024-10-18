package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * This class represents the door allowing
 * a door object to be instanced and allows
 * its state of being opened or closed to be changed
 * by other objects.
 */

public class Door extends Interactable{


    /**
     * @param x is the x co ordinate of the door of the door
     * @param y is the y co ordinate of the door
     * @param name is the name of the object
     * Constructor for door object
     */
    private TextureRegion Open = textureAtlas.findRegion("DoorOpened");
    private TextureRegion Closed = textureAtlas.findRegion("DoorClosed");

    private Music openDoor = Gdx.audio.newMusic(Gdx.files.internal("opendoor.wav"));
    private Music closeDoor = Gdx.audio.newMusic(Gdx.files.internal("closedoor.wav"));

    /**
     * Constructor of a door object
     * @param x is the x co-ordinate of the door of the door
     * @param y is the y co-ordinate of the door
     * @param name is the name of the object
     * @param img the image of the door
     */
    public Door (int x, int y, String name, TextureRegion img) {
        super(x, y, name, new TextureRegion(LevelObject.textureAtlas.findRegion("DoorClosed")), new TextureAtlas(Gdx.files.internal("TxAtlas.atlas")), false);
    }

    /**
     * Method which returns a door object
     * @return the door object being returned
     */
    public Door getDoor(){
        return this;
    }

    /**
     * Method which returns whether a door is opened or not
     * @return the state being returned for the door
     */

    public boolean getOpened(){
        return this.getState();
    }

    /**
     * Process to be completed when a door is either being
     * opened or closed, including state, audio and image changes
     */
    @Override
    public void interact() {
        openDoor.setVolume(0.05f);
        closeDoor.setVolume(0.05f);
        System.out.println(getOpened());
        //change img
        if (getOpened()){
            this.img = Open;
            openDoor.play();
        }
        else {
            this.img = Closed;
            closeDoor.play();
        }
    }
}
