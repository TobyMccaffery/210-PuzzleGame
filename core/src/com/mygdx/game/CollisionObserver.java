package com.mygdx.game;

/**
 * Interface to print upon collision
 */
public interface CollisionObserver {

    /**
     * To notify/print of a collision
     * @param sourceName object that has collided
     */
    public void notifyCollision(String sourceName);
}
