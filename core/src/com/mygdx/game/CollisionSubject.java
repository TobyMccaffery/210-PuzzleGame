package com.mygdx.game;


/**
 * Interface to add and remove CollisionObserver layers
 * and print the colliding object (upon observed collisions)
 */
public interface CollisionSubject {

    /**
     * Add a collision observer layer
     * @param collidable collision observer layer
     */
    public void addCollisionObserver(CollisionObserver collidable);

    /**
     * Remove a collision observer layer
     * @param collidable collision observer layer
     */
    public void removeCollisionObserver(CollisionObserver collidable);

    /**
     * Print/notify when source object has collided
     */
    public void notifyCollisionObservers();
}
