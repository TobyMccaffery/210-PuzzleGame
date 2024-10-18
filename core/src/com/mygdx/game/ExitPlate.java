package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * This class represents the exit plate which the character interacts with
 * upon completion of the current level
 */

public class ExitPlate extends PressurePlate{
    private int plateNumber;

    /**
     * Constructor for an exit plate. Its sets its location, its image and its plate number
     * @param x x-axis position of plate
     * @param y y-axis position of plate
     * @param img image of plate
     * @param plateNumber plate num value
     */

    public ExitPlate(int x, int y, TextureRegion img, int plateNumber) {
        super(x, y, img);
        this.plateNumber = plateNumber;
    }


    /**
     * Override of changePNG() method - empty as
     * image does not need to be changed
     */
    @Override
    public void changePNG(){

    }
}
