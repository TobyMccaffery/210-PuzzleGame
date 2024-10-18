package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * This class is representative of a speed potion object
 * that the character picks up to increase speed
 */

public class SpeedPotion extends Item{
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

    public SpeedPotion(int x, int y, int width, int height, String name) {
        super(x, y, width, height, name, new TextureRegion( LevelObject.textureAtlas.findRegion("red-potion")), new Texture("redLight.png"));
    }

    /**
     * Process to carry out upon use of the potion, including audio, image and state changes
     * @param character character using the potion
     */

    public void use(final Character character) {
        drinkPotion.setVolume(0.01f);
        if(!using){
            using = true;
            System.out.println("speed potion pickup");
            drinkPotion.play();
            setImg(new TextureRegion( new Texture("potions/emptyredpotion.png")));
            new Thread(new Runnable() {
                @Override
                public void run() {
                try {
                    System.out.println("using speed potion");
                    character.setSpeed(character.getSpeed() + 3);
                    Thread.sleep(10000);
                    setImg(new TextureRegion( LevelObject.textureAtlas.findRegion("red-potion")));
                    character.setSpeed(character.getSpeed() - 3);
                    using = false;
                    System.out.println("used speed potion up");
                } catch (InterruptedException e) {}
                }
            }).start();
        }
    }
}