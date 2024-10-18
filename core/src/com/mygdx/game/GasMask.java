package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * This class is representative of a gas mask object
 * that the character picks up to avoid damage from gas
 */

public class GasMask extends Item{
    private boolean using = false;

    /**
     * Constructor for gas mask including settings its location and image
     * @param x x-axis location of mask
     * @param y y-axis location of mask
     * @param width width dimension of  mask
     * @param height height dimension of mask
     * @param name name of mask object
     */

    private Music equipMask = Gdx.audio.newMusic(Gdx.files.internal("gasmask.wav"));
    public GasMask(int x, int y, int width, int height, String name) {
        super(x, y, width, height, name, new TextureRegion( LevelObject.textureAtlas.findRegion("GasMask")), new Texture("pinkLight.png"));
    }

    /**
     * Process to carry out upon use of the mask, including audio, image and state changes
     * @param character character using the mask
     */

    public void use(final Character character) {
        equipMask.setVolume(0.04f);
        if(!using){
            using = true;
            equipMask.play();
            character.setEquippedItem(this);
            setEquipped(true);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //print out that we are using the item
                        System.out.println("Using " + getName());
                        equipMask.play();
                        Thread.sleep(15000);
                        character.setEquippedItem(null);
                        setEquipped(false);
                        //print out that we have used up the item
                        System.out.println("Used gas mask");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    using = false;
                }
            }).start();
        }
    }
}
