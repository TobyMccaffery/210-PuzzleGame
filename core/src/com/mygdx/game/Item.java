package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * This abstract class represents floor items which assists players in the game
 * including potions and gas masks. It includes the common instance variables and
 * methods of such in-game items.
 */

public abstract class Item extends  LevelObject {
    private Boolean equipped;
    private Texture light;

    /**
     * Constructor of an item includes setting dimensions, a location,
     * an image texture and a light texture
     * @param x Location on x-axis
     * @param y Location on y-axis
     * @param width Width of item
     * @param height Height of item
     * @param name Name of the item
     * @param img Image texture  of the item
     * @param light Light texture of the item
     */

    public Item(int x, int y, int width, int height, String name, TextureRegion img, Texture light) {
        super(x, y, name, img, new TextureAtlas(Gdx.files.internal("TxAtlas.atlas")));
        this.equipped = false;
        this.light = light;
    }

    /**
     * This method returns this item back to the method where its
     * called (when the character interacts  with the item)
     * @return is the item being interacted with
     */
    public Boolean getEquipped() {
        return this.equipped;
    }

    /**
     * Method to mark the item as equipped upon interaction
     * @param equipped is a boolean flag to mark item as equipped
     */

    public void setEquipped(Boolean equipped) {
        this.equipped = equipped;
    }

    /**
     * Method to return the light texture upon being called
     * @return is the light texture
     */

    public Texture getLight() {
        return this.light;
    }
}
