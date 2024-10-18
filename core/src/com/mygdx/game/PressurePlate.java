package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.Date;

import java.awt.*;

/**
 * This class includes all the variables and methods of a pressure plate
 * which is used to open/close doors depending on whether a character
 * is standing on it or not
 */

public class PressurePlate extends Interactable{
    private boolean state;
    private ArrayList<Interactable> interactables = new ArrayList<Interactable>();
    private TextureRegion pressed = textureAtlas.findRegion("PressurePlateDown");
    private TextureRegion notPressed = textureAtlas.findRegion("PressurePlateUp");
    private boolean characterOneState = false;
    private boolean characterTwoState = false;

    /**
     * Constructor of a pressure plate object where we set its location
     * and its image
     * @param x x-axis location of the pressure plate
     * @param y y-axis location of the pressure plate
     * @param img Image of the pressure plate
     */
    public PressurePlate(int x, int y, TextureRegion img){
        super(x, y, "plate", img ,new TextureAtlas(Gdx.files.internal("TxAtlas.atlas")), false);
        state = false;
    }

    /**
     * Process to carry out upon a character stepping on the pressure plate
     * @param character is the character currently interacting with the pressure plate
     */
    public void toggle(String character){
        if(character.equals("character1")){
            characterOneState = true;
        } else if (character.equals("character2")){
            characterTwoState = true;
        } else {
            //print error
            System.out.println("Error");
        }
        if(!state){
            changeInteractables();
            state = true;
            changePNG();
        }
    }

    /**
     * Processes to carry out when a character has stopped interacting with a pressure plate
     * @param character is the character that has stopped interacting with the pressure plate
     */
    public void unToggle(String character){
        if(character.equals("character1")){
            characterOneState = false;
        } else if (character.equals("character2")){
            characterTwoState = false;
        } else {
            //print error
            System.out.println("Error");
        }

        if(!characterOneState && !characterTwoState && state){
            state = false;
            changeInteractables();
            changePNG();
            //print "unToggled"
            System.out.println("unToggled");
        }

    }

    /**
     * Method to change the image of the pressure plate
     * after stepping on or off
     */
    public void changePNG(){
        if(state){
            this.setImg(pressed);
        } else {
            this.setImg(notPressed);
        }
    }

    /**
     * To return the state of the pressure plate
     * @return state of the plate
     */
    public boolean getState()
    {
        return state;
    }

    /**
     * To change the state of the interactables the pressure plate is
     * associated with
     */
    public void changeInteractables(){
        for(Interactable i : this.interactables){
            i.changeState();
            i.interact();
        }
    }

    /**
     * To add interactable to the list of interactables
     * @param object interactable to add to the list
     */
    public void addInteractable(Interactable object) {
        this.interactables.add(object);
    }

}