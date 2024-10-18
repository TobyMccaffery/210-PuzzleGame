package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import java.util.ArrayList;

/**
 * A LevelObject class which is a Super class to all tile and
 * item objects. It includes variables and methods familiar in the
 * aforementioned objects
 */

public abstract class LevelObject extends Sprite {
    //the x, y coordinate of the object in the level.
    private Vector2 position = new Vector2();
    //Dimensions of the object.
    private int width;
    private int height;
    //Handles collision between objects.
    private Rectangle body;
    private Polygon polyBody;
    //How the object appears in the level.
    protected TextureRegion img;
    protected String name;
    protected static TextureAtlas textureAtlas = new TextureAtlas("TxAtlas.atlas");
    protected static TextureAtlas textureAtlass = new TextureAtlas("TxAtlas.atlas");
    //protected static TextureAtlas PlayerAtlas = new TextureAtlas("WalkRight.atlas");
    private static ArrayList<LevelObject> levelObjects = new ArrayList<LevelObject>(); //List for level objects



    /**
     * Constructor method for instancing a level object.
     * This sets the dimension, locations and has objects to observe collisions
     *
     * @param x x-axis co-ordinate of object
     * @param y y-axis co-ordinate of object
     * @param name name of the object
     * @param img Texture of the image
     * @param textureAtlas is the Atlas where the images are derived from
     */
    public LevelObject(int x, int y, String name, TextureRegion img, TextureAtlas textureAtlas) {
        position.x = x;
        position.y = y;
        this.width = 13;
        this.height = 13;
        this.name = name;
        this.img = img;
        this.body = new Rectangle(getX(), getY(), width, height);
        this.polyBody = new Polygon(new float[]{position.x,position.y, 13,position.y, 13,13, position.x,13});
    }

    /**
     * Checks if rectangles overlaps this levelObject
     */
    public Boolean isOverlapping(Rectangle rect){
        return rect.overlaps(this.getBody());
    }
    public Boolean isMapOverlapping(Rectangle rect){
        return rect.overlaps(this.getBody());
    }

    /**
     * Checks what the levelObject is overlapping with
     * and returns that overlapping object
     */
    public LevelObject checkOverlappingClass() {
        //looping through every object in the game
        for (int i = 0; i < MyGdxGame.currentLevel.levelObjects.size(); i++) {
            //if the one its looking at isn't itself
            if (MyGdxGame.currentLevel.levelObjects.get(i) != this) {
                //check if it's overlapping
                if (this.isOverlapping(MyGdxGame.currentLevel.levelObjects.get(i).getBody())) {
                    //return the object it is overlapping with
                    return MyGdxGame.currentLevel.levelObjects.get(i);
                }
            }
        }
        //if it isn't overlapping just return itself
        return null;
    }

    /**
     * Returns the x-axis position of the object
     * @return x-axis number
     */

    //Get and set methods for private variables below
    public float getX()  {
        return (float)position.x;
    }

    /**
     * Returns the y-axis position of the object
     * @return y-axis number
     */

    public float getY() {
        return (float)position.y;
    }

    /**
     * Returns the position of the object
     * @return position number
     */

    public Vector2 getPosition() {
        return position;
    }

    /**
     * Returns the body of the object
     * @return body
     */

    public Rectangle getBody(){
        return body;
    }

    /**
     * Returns the polygon body of the object
     * @return polygon
     */

    public Polygon getPolyBody(){
        return polyBody;
    }

    /**
     * Sets polygon body of the levelObject
     * @param x x dimension of polygon
     * @param y y dimension of polygon
     */

    public void setPolyBody(float x, float y){
        float[] vertices = new float[]{x,y, (x+13),y, (x+13),(y+13), x,(y+13)};
        getPolyBody().setVertices(vertices);

    }

    /**
     * Returns the name of the level object
     * @return name of object
     */
    public String getName(){
        return name;
    }


    /**
     * Sets x dimension of object
     * @param x x dimension
     */
    public void setX(float x){
        position.x = x;
        body.setX(x);
    }

    /**
     * Sets y dimension of object
     * @param y y dimension
     */

    public void setY(float y){
        position.y = y;
        body.setY(y);
    }

    /**
     * Sets x & y dimension of object
     * @param x x dimension
     * @param y y dimension
     */

    public void setPosition(float x, float y){
        position.x = x;
        position.y = y;
        body.setX(x);
        body.setY(y);
    }

    /**
     * Returns list of level objects
     * @return list of level objects
     */

    public ArrayList getLevelObjects(){
        return levelObjects;
    }

    /**
     * Returns the texture img of the object
     */
    public TextureRegion getImg() {

        return img;
    }

    /**
     * This method sets an image to an object
     * @param newImg is the image to assign to a Textured object
     */
    public void setImg(TextureRegion newImg) { img = newImg; }
}
