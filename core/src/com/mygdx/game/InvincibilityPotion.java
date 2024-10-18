package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * This class is representative of an invincibility potion object
 * that the character picks up to avoid taking damage
 */

public class InvincibilityPotion extends Item{
    private boolean using = false;
    private Music drinkPotion = Gdx.audio.newMusic(Gdx.files.internal("potionslurp.wav"));

    /**
     * Constructor for health potion including settings its location and image
     * @param x x-axis location of potion
     * @param y y-axis location of potion
     * @param width width dimension of  potion
     * @param height height dimension of potion
     * @param name name of potion object
     */

    public InvincibilityPotion(int x, int y, int width, int height, String name) {
        super(x, y, width, height, name, new TextureRegion( LevelObject.textureAtlas.findRegion("yellow-potion")), new Texture("yellowLight.png"));
    }

    /**
     * Process to carry out upon use of the potion, including audio, image and state changes
     * @param character character using the potion
     */

    public void use(final Character character) {
        drinkPotion.setVolume(0.01f);
        if(!using){
            using = true;
            drinkPotion.play();
            setImg(new TextureRegion( new Texture("potions/emptyyellowpotion.png")));
            character.setEquippedItem(this);
            setEquipped(true);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //print out that we are using the item
                        System.out.println("Using " + getName());
                        Thread.sleep(10000);
                        setImg(new TextureRegion( LevelObject.textureAtlas.findRegion("yellow-potion")));
                        character.setEquippedItem(null);
                        setEquipped(false);
                        //print out that we have used up the item
                        System.out.println("Used invincibility potion");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    using = false;
                }
            }).start();
        }
    }
}
