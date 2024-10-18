package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * This class represents the health bar above the character
 * which changes position as the character moves and changes image
 * as health depletes
 */

public class HealthBar {
    private int x;
    private int y;
    private Character character;
    private int width;
    private int height;
    private Texture img;

    /**
     * Constructor of health bar sets its location and associates it with 1 on the 2 characters
     * @param x x-axis location
     * @param y y-axis location
     * @param character character 1 or 2
     */
    public HealthBar(int x, int y, Character character) {
        this.x = x;
        this.y = y;
        this.character = character;
        this.width = 80;
        this.height = 8;
        this.img = new Texture("enemy.png");
    }

    /**
     * Draws the health bar according to character position and health
     * @param batch is the batch for drawing
     */

    public void render(Batch batch) {
        int health = character.getHealth();
        //create a new image from the health-bar folder with the correct health
        if (health > 0) {
            img = new Texture("health-bar/healthbar-" + health + ".png");

            batch.setColor(1, 1, 1, 1f);
            batch.draw(img, character.getX(), character.getY() + 15, 20, 20);
            batch.setColor(1, 1, 1, 1);
        }
    }
}
