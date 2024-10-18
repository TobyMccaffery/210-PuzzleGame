package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * This class represents an interactable which is primarily used
 * for the doors and door openers in the game
 */
public abstract class Interactable extends Tile {
    private boolean state;

    /**
     * Constructor for an interactable object including setting its x and y-axis location
     * its image, its texture atlas for where its image is extracted from and flag for whether
     * its in its default state or not
     * @param x x-axis location
     * @param y y-axis location
     * @param name Interactable string name
     * @param img Image of the interactable
     * @param textureAtlas Atlas pack of images
     * @param defaultState Boolean flag for whether its in its default state or not
     */

    public Interactable(int x, int y, String name, TextureRegion img, TextureAtlas textureAtlas, Boolean defaultState){
        super(x, y, name, img, textureAtlas);
        this.state = defaultState;
    }

    /**
     * Change state of the interactable ie
     * to no longer remain in default state
     */

    public void changeState(){
        this.state = !this.state;
        System.out.println("Changed Interactable");
    }

    /**
     * Method to return state of the interactable
     * @return the state of the object
     */
    public boolean getState(){
        return this.state;
    }

    /**
     * Abstract method to determine what happens
     * when the object is interacted with
     */
    public void interact(){}
}
