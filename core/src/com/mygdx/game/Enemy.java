package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import com.badlogic.gdx.graphics.g2d.Animation;

import java.util.ArrayList;
import java.util.Date;

/**
 * This class represents the chasing enemy hindering the characters
 * by depleting their health
 */

public class Enemy extends Entity {
    float attackCooldown;
    float lastAttack;
    int gasCooldown;
    long lastGas;
    float attackDmg;

    private Texture enemyTexture = new Texture("new-assets/monster-right-walk.png");
    Animation<Texture> animation;

    /**
     * Constructor for our enemy object setting its location, image and collision layer
     * @param x x-axis position
     * @param y y-axis position
     * @param collisionLayer collision layer
     */
    public Enemy(int x, int y,TiledMapTileLayer collisionLayer) {
        super(new TextureRegion(LevelObject.textureAtlass.findRegion("monster-right-walk")), x, y, "enemy", collisionLayer);
        this.setSpeed(0.5f);
        this.attackCooldown = 100;
        gasCooldown = 100;
        this.attackDmg = 20;
        this.lastAttack = new Date().getTime();
        lastGas = new Date().getTime();
    }

    /**
     * Calculate and get distance from characters
     * @param target character object
     * @return returns the calculated distance
     */

    double distance(LevelObject target) {
        return Math.sqrt(Math.pow((target.getX() - this.getX()), 2) + Math.pow((target.getY() - this.getY()), 2));
    }

    /**
     * Path finding method to identify who and where the enemy should chase
     * @param char1 first character
     * @param char2 second character
     */
    public void pathfind(Character char1, Character char2){
        Character target;
        if (distance(char1) > distance(char2)){
            target = char2;
        }
        else {
            target = char1;
        }
        if (this.getBody().overlaps(target.getBody())){
            if ((new Date().getTime() - this.lastAttack) > this.attackCooldown) {
                target.decreaseHealth(10);
            }
            return;
        }
        float speed = this.getSpeed() /2;
        if (target.getX() > this.getX()){
            if (target.getX() - this.getX() > speed) {
                this.setX(this.getX() + speed);
            }
            else{
                this.setX(target.getX());
            }
        }
        if (target.getX() < this.getX()){
            if (this.getX() - target.getX() > speed) {
                this.setX(this.getX() - speed);
            }
            else{
                this.setX(target.getX());
            }
        }
        if (target.getY() > this.getY()){
            if (target.getY() - this.getY() > speed) {
                this.setY(this.getY() + speed);
            }
            else{
                this.setY(target.getY());
            }
        }
        if (target.getY() < this.getY()){
            if (this.getY() - target.getY() > speed) {
                this.setY(this.getY() - speed);
            }
            else{
                this.setY(target.getY());
            }
        }
    }

    /**
     * Leave a trail of gas behind the enemy's recent path
     */

    public void placeGas(){
        if ((new Date().getTime() - lastGas) > gasCooldown) {
            Gas g = new Gas((int) this.getX(), (int) this.getY());
            lastGas = new Date().getTime();
        }
    }

    /**
     * Return the monster animation
     * @return animation
     */

    public Animation<Texture> getAnimation(){
        return animation;
    }
}
