package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.MapLayer;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Null;

//badlogic package has classes to support graphical display of a game



import java.util.ArrayList; //To support use of ArrayLists
import java.util.Date;

/**
 * This class includes variables and methods to
 * support the movement of relevant levelObjects
 */

public abstract class Entity extends LevelObject implements CollisionSubject{
    //the value added to position to move the entity.
    private Vector2 direction = new Vector2();
    //multiplier for the movement.
    private float speed = 4;
    //the last coordinates of the Entity
    private float prevx;
    private float prevy;
    //the time at the point Date().getTime is called.
    private long lastDecreased;
    //if reaches 0 the character dies
    private int health;
    private static double scale;
    private String blockedKey = "blocked";
    private TiledMapTileLayer collisionLayer;



    private ArrayList<CollisionObserver> collidableObjects  = new ArrayList<CollisionObserver>();

    /**
     * Constructor method for an entity which are moving objects
     *
     * @param x x-axis co-ordinate of object
     * @param y y-axis co-ordinate of object
     * @param name name of the object
     * @param img Texture of the image
     * @param collisionLayer layer to observe the entities collisions
     */

    public Entity (TextureRegion img, int x, int y, String name,TiledMapTileLayer collisionLayer) {
        super(x, y, name,  img, new TextureAtlas(Gdx.files.internal("TxAtlas.atlas")));
        this.collisionLayer = collisionLayer;
    }

    /**
     * Sets the scales of the entity
     * @param nscale scale number
     */

    public static void setScale(double nscale){
        scale = nscale;
    }

    /**
     * Add a collision observing layer to the entity
     * @param collidable layer for collision
     */

    @Override
    public void addCollisionObserver(CollisionObserver collidable) {
        collidableObjects.add(collidable);
    }

    /**
     * Remove a collision observing layer to the entity
     * @param collidable layer for collision
     */

    @Override
    public void removeCollisionObserver(CollisionObserver collidable) {
        collidableObjects.remove(collidable);
    }

    /**
     * Notify/print when a collision is observed
     */
    @Override
    public void notifyCollisionObservers() {
        for(int i = 0; i < collidableObjects.size(); i ++)
        {
            CollisionObserver collisionObserver = collidableObjects.get(i);
            collisionObserver.notifyCollision(name);
        }
    }

    //Set the vector value to positive or negative depending on direction of movement.

    /**
     * Change x-axis position to move left
     */
    public void moveLeft(){ direction.x = -1; }

    /**
     * Change x-axis position to move right
     */
    public void moveRight() { direction.x = 1; }

    /**
     * Change y-axis position to move up
     */
    public void moveUp(){ direction.y = 1;}

    /**
     * Change y-axis position to move down
     */
    public void moveDown(){ direction.y = -1; }


    /**
     * Resets the location of
     * an entity if it has moved out of the bounds
     * of the display
     */

    //Ensure players cannot escape level.
    public void checkBounds()
    {
        if(this.getY() <= 0){
            this.setY(0);
        }
        else if(this.getY() * 2 >= 3200 - (this.getHeight() * 2)){
            this.setY(1600 - (this.getHeight()));
        }
        if(this.getX() <= 0){
            this.setX(0);
        }
        else if(this.getX() * 2 >=3200 - (this.getWidth() * 2)){
            this.setX(1600 - (this.getWidth()));
        }
        direction.setZero();
    }


    /**
     * Causes character to move using the vector object(considering speed)
     */
    public void move(){
        prevx = this.getX();
        prevy = this.getY();

        //normalise vector(make sure vector is no greater than one).
        //direction.nor();
        //multiply direction by speed and add to position.
        this.getPosition().add(direction.scl(speed));

        //Characters cannot escape level
        checkBounds();

        //Move the entity to new position
        this.getBody().setX(this.getX());
        this.getBody().setY(this.getY());
        this.setPolyBody(this.getX(),this.getY());
        direction.setZero();
    }

    /**
     * Returns previous x-axis co-ordinate
     * @return is the x axis number
     */

    public float getPrevx(){
        return prevx;
    }

    /**
     * Returns previous y-axis number
     * @return is the y axis num
     */

    public float getPrevy(){
        return prevy;
    }

    /**
     * Sets speed of the entity using vector
     * @param newSpeed num relative to vector
     */

    public void setSpeed(float newSpeed){
        speed = newSpeed;
    }

    /**
     * Returns speed of enitity
     * @return is the y axis num
     */

    public float getSpeed(){
        return speed;
    }

    /**
     * Returns direction of entity
     * @return is the direction num
     */

    public Vector2 getDirection() {
        return direction;
    }

}
