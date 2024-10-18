package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.sun.source.tree.ForLoopTree;

import java.util.ArrayList;
//import jdk.internal.misc.FileSystemOption;

/**
 * This class supports specific features of a character and
 * also observes its collisions
 */

public class Character extends Entity{
    private Item equippedItem;

    private boolean overlappingPressurePlate;
    private int health;
    private boolean canBeDamaged = true;
    private Music takingDamage = Gdx.audio.newMusic(Gdx.files.internal("damage.wav"));
    private Music deathgroan = Gdx.audio.newMusic(Gdx.files.internal("death.wav"));
    private TextureAtlas player1Atlas;
    private TextureAtlas player2Atlas;
    Animation<TextureRegion> animationUp1;
    Animation<TextureRegion> animationDown1;
    Animation<TextureRegion> animationLeft1;
    Animation<TextureRegion> animationRight1;
    Animation<TextureRegion> animationIdle1;

    Animation<TextureRegion> animationUp2;
    Animation<TextureRegion> animationDown2;
    Animation<TextureRegion> animationLeft2;
    Animation<TextureRegion> animationRight2;
    Animation<TextureRegion> animationIdle2;

    private String blockedKey = "blocked";
    private TiledMapTileLayer collisionLayer;
    public static ArrayList<Rectangle> rectangleList = new ArrayList<>();
    public static ArrayList<Polygon> polygonList = new ArrayList<>();


    /**
     * Constructor for a character object.
     * @param img Image of the character
     * @param x x-axis position of character
     * @param y y-axis position of character
     * @param num Character 1 or character 2
     * @param collisionLayer Layer to observe collisions
     */

    public Character(TextureRegion img, int x, int y, char num,TiledMapTileLayer collisionLayer) {
        super(img, x, y,"character" + num, collisionLayer);
        this.collisionLayer = collisionLayer;
        health = 100;
        overlappingPressurePlate = false;
        player1Atlas = new TextureAtlas("Character1.atlas");
        player2Atlas = new TextureAtlas("Character2.atlas");
        animationUp1 = new Animation<TextureRegion>(0.3f, player1Atlas.findRegions("Up"));
        animationDown1 = new Animation<TextureRegion>(0.3f, player1Atlas.findRegions("Down"));
        animationLeft1 = new Animation<TextureRegion>(0.3f, player1Atlas.findRegions("Left"));
        animationRight1 = new Animation<TextureRegion>(0.3f, player1Atlas.findRegions("Right"));
        animationIdle1 = new Animation<TextureRegion>(0.4f, player1Atlas.findRegions("Idle"));

        animationUp2 = new Animation<TextureRegion>(0.3f, player2Atlas.findRegions("Up"));
        animationDown2 = new Animation<TextureRegion>(0.3f, player2Atlas.findRegions("Down"));
        animationLeft2 = new Animation<TextureRegion>(0.3f, player2Atlas.findRegions("Left"));
        animationRight2 = new Animation<TextureRegion>(0.3f, player2Atlas.findRegions("Right"));
        animationIdle2 = new Animation<TextureRegion>(0.4f, player2Atlas.findRegions("Idle"));

    }

    /**
     * Checks for the characters overlapping with any relevant object
     * and carries process appropriate to what the character has collided with
     */

    public void isOverlapping(){
        float x = getPrevx(); //Gets previous x
        float y = getPrevy(); //Gets previous y

        Rectangle xBody = new Rectangle(getX(), getPrevy(), 13, 13);
        Rectangle yBody = new Rectangle(getPrevx(), getY(), 13,13);
        boolean overlappingPressurePlate = false;
        boolean overlappingExitPlate = false;
        if(checkOverlappingClass() != null){
            LevelObject e = checkOverlappingClass();
            if(e instanceof Character) { //Checks if collided with wall
                System.out.println("collided with character");
                //set the position back
                setPosition(x, y);
            }
            if(e instanceof Wall) { //Checks if collided with wall
                System.out.println("collided with wall");
                setPosition(x, y);
            }
            if (e instanceof Door){
                //if its a closed door
                Door tt = (Door) checkOverlappingClass();
                if (!(tt.getOpened())){
                    setPosition(x, y);
                }
            }
            if (e instanceof SmallGap){
                SmallGap gap = (SmallGap) checkOverlappingClass();
                if (this.getName().equals("character1")){
                    if (!(((Character) this).getEquippedItem() instanceof SmallPotion)) {
                        setPosition(x, y);
                    }

                }
                if (this.getName().equals("character2")){
                    if (!(((Character) this).getEquippedItem() instanceof SmallPotion)) {
                        setPosition(x, y);
                    }
                }

            }
            if (e instanceof Lever){
                //If character1 is using the lever.
                Lever lever = (Lever) checkOverlappingClass();
                if (this.getName().equals("character1")){
                    if(Gdx.input.isKeyPressed(Input.Keys.E)){
                        lever.toggle();
                    }
                }
                //If character2 is using the lever
                if (this.getName().equals("character2")){
                    if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)){
                        lever.toggle();
                    }
                }
            }
            if (e instanceof Button){
                //If character1 is using the lever.
                Button button = (Button) checkOverlappingClass();
                if (this.getName().equals("character1")){
                    if(Gdx.input.isKeyPressed(Input.Keys.E)){
                        button.toggle();
                    }
                }
                //If character2 is using the lever
                if (this.getName().equals("character2")){
                    if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)){
                        button.toggle();
                    }
                }
            }
            if (e instanceof Enemy){
                //print out the health
                this.decreaseHealth(10);
            }
            if (e instanceof Gas){
                if (this.getName().equals("character1")){
                    if (!(((Character) this).getEquippedItem() instanceof GasMask)) {
                        this.decreaseHealth(20);
                    }

                }
                if (this.getName().equals("character2")){
                    if (!(((Character) this).getEquippedItem() instanceof GasMask)) {
                        this.decreaseHealth(20);
                    }
                }
            }
            if (e instanceof FloorGas){
                if (this.getName().equals("character1")){
                    if (!(((Character) this).getEquippedItem() instanceof GasMask)) {
                        this.decreaseHealth(60);
                    }

                }
                if (this.getName().equals("character2")){
                    if (!(((Character) this).getEquippedItem() instanceof GasMask)) {
                        this.decreaseHealth(60);
                    }
                }

            }
            if (e instanceof PressurePlate) {
                overlappingPressurePlate = true;
                System.out.println("Touched pressure plate");
                PressurePlate pPlate = (PressurePlate) checkOverlappingClass();
                //if character1 is on the pressure plate.
                if (this.getName().equals("character1")) {
                    pPlate.toggle("character1");
                }
                //if character2 is on the pressure plater.
                if (this.getName().equals("character2")) {
                    pPlate.toggle("character2");
                }
            }
            if(e instanceof ExitPlate){
                overlappingExitPlate = true;
                System.out.println("touching exit plate");
                ExitPlate exitPlate = (ExitPlate) checkOverlappingClass();
                if (this.getName().equals("character1")){
                    BaseLevel.exitPlateOne = true;
                }
                if (this.getName().equals("character2")){
                    BaseLevel.exitPlateTwo = true;
                }
            }
            if (e instanceof GasMask){
                GasMask Mask = (GasMask) checkOverlappingClass();
                if (this.getName().equals("character1")){
                    if(Gdx.input.isKeyPressed(Input.Keys.E)){
                        Mask.use((Character)this);
                    }
                }
                if (this.getName().equals("character2")){
                    if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)){
                        Mask.use((Character)this);
                    }
                }
            }
            if (e instanceof SpeedPotion){
                SpeedPotion Potion = (SpeedPotion) checkOverlappingClass();
                if (this.getName().equals("character1")){
                    if(Gdx.input.isKeyPressed(Input.Keys.E)){
                        Potion.use((Character)this);
                    }
                }
                if (this.getName().equals("character2")){
                    if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)){
                        Potion.use((Character)this);
                    }
                }
            }
            if (e instanceof InvincibilityPotion){
                InvincibilityPotion Potion = (InvincibilityPotion) checkOverlappingClass();
                if (this.getName().equals("character1")){
                    if(Gdx.input.isKeyPressed(Input.Keys.E)){
                        Potion.use((Character)this);
                    }
                }
                if (this.getName().equals("character2")){
                    if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)){
                        Potion.use((Character)this);
                    }
                }
            }
            if (e instanceof HealthPotion){
                HealthPotion Potion = (HealthPotion) checkOverlappingClass();
                if (this.getName().equals("character1")){
                    if(Gdx.input.isKeyPressed(Input.Keys.E)){
                        Potion.use((Character)this);
                    }
                }
                if (this.getName().equals("character2")){
                    if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)){
                        Potion.use((Character)this);
                    }
                }
            }
            if (e instanceof SmallPotion){
                SmallPotion Potion = (SmallPotion) checkOverlappingClass();
                if (this.getName().equals("character1")){
                    if(Gdx.input.isKeyPressed(Input.Keys.E)){
                        Potion.use((Character)this);
                    }
                }
                if (this.getName().equals("character2")){
                    if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)){
                        Potion.use((Character)this);
                    }
                }
            }
        }


        for (int i = 0;i < rectangleList.size();i++){

            if (Intersector.overlaps(rectangleList.get(i), xBody)) {
                //this.setPosition(x,y);
                this.setX(getPrevx());
            }
            else if (Intersector.overlaps(rectangleList.get(i), yBody)) {
                //this.setPosition(x,y);
                this.setY(getPrevy());
            }
            else if ((Intersector.overlaps(rectangleList.get(i), yBody))&&(Intersector.overlaps(rectangleList.get(i), xBody))) {
                //this.setPosition(x,y);
                setPosition(x, y);
            }
        }
        for (int i = 0;i < polygonList.size();i++){

            if (Intersector.overlapConvexPolygons(polygonList.get(i), this.getPolyBody())) {
                this.setPosition(x,y);
            }
        }
        if(!overlappingPressurePlate){
            for(int i = 0; i < MyGdxGame.currentLevel.levelObjects.size(); i++){
                if(MyGdxGame.currentLevel.levelObjects.get(i) instanceof PressurePlate){
                    PressurePlate pPlate = (PressurePlate) MyGdxGame.currentLevel.levelObjects.get(i);
                    pPlate.unToggle(this.getName());
                }
            }
        }
        if(!overlappingExitPlate){
            if(this.getName().equals("character1")){
                BaseLevel.exitPlateOne = false;
            } else {
                BaseLevel.exitPlateTwo = false;
            }
        }
    }

    /**
     * Mark the character to have equipped an item
     * @param item is the item the character has picked up
     */

    public void setEquippedItem(Item item){
        equippedItem = item;
    }

    /**
     * To inform of the item the character has picked up
     * @return is the item the character has picked up
     */
    public Item getEquippedItem() {
        return equippedItem;
    }

    /**
     * Process to perform when a character is hurt or has died
     * including changing the health bar, playing audio and triggering level
     * reset upon character death
     * @param amount the amount to reduce the health by
     */
    //decrease health function
    public void decreaseHealth(final int amount){
        takingDamage.setVolume(0.035f);
        deathgroan.setVolume(0.08f);
        if(canBeDamaged && !(equippedItem instanceof InvincibilityPotion)){
            //print "decreasing health
            System.out.println("decreasing health");

            if(health > 0) {
                takingDamage.play();
            }
            else{
                deathgroan.play();
            }
            canBeDamaged = false;
            //spawn new thread to decrease health then wait 10 seconds
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (health > 0) {
                            health -= amount;
                            if(health <= 0){
                                BaseLevel.deathFlag = true;
                                deathgroan.play();
                            }
                            takingDamage.play();
                        } else {
                            //print dead
                            BaseLevel.deathFlag = true;
                            System.out.println("dead");
                            //trigger death
                        }
                        Thread.sleep(2000);

                        canBeDamaged = true;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    /**
     * Add a rectangle object to rectangle list
     * @param rectangle is the rectangle object
     */

    public void addRectangle(Rectangle rectangle){
        rectangleList.add(rectangle);

    }

    /**
     * Add a polygon object to polygon array list
     * @param polygon is the rectangle object
     */
    public void addPoly(Polygon polygon){
        polygonList.add(polygon);

    }

    /**
     * Method to increase a characters health
     * @param amount is the amount to increase the health by
     */
    public void increaseHealth(int amount) {
        health += amount;
        if(health > 100) {
            health = 100;
        }
    }

    /**
     * To return the current health of the character
     * @return is the integer value of the health
     */

    public int getHealth(){
        return health;
    }

    /**
     * To return the upwards walking animation of the character
     * @return the animation to walk up
     */

    public Animation<TextureRegion> getUpAnimation1(){
        return animationUp1;
    }

    /**
     * To return the downwards walking animation of the character
     * @return animation to walk down
     */
    public Animation<TextureRegion> getDownAnimation1(){
        return animationDown1;
    }

    /**
     * To return the left walking animation of the character
     * @return animation to walk left
     */
    public Animation<TextureRegion> getLeftAnimation1(){
        return animationLeft1;
    }

    /**
     * Return the animation to walk right for the character
     * @return animation to walk right
     */
    public Animation<TextureRegion> getRightAnimation1(){
        return animationRight1;
    }

    /**
     * Return the animation when the character is idle
     * @return animation when the character is idle
     */
    public Animation<TextureRegion> getIdleAnimation1(){
        return animationIdle1;
    }

    /**
     * To return the second upwards walking animation of the character
     * @return the second animation to walk up
     */


    public Animation<TextureRegion> getUpAnimation2() {
        return animationUp2;
    }

    /**
     * To return the second downwards walking animation of the character
     * @return second animation to walk down
     */

    public Animation<TextureRegion> getDownAnimation2(){
        return animationDown2;
    }

    /**
     * To return the second left walking animation of the character
     * @return second animation to walk left
     */

    public Animation<TextureRegion> getLeftAnimation2(){
        return animationLeft2;
    }

    /**
     * Return the second animation to walk right for the character
     * @return second  animation to walk right
     */
    public Animation<TextureRegion> getRightAnimation2(){
        return animationRight2;
    }

    /**
     * Return the second  animation when the character is idle
     * @return second  animation when the character is idle
     */
    public Animation<TextureRegion> getIdleAnimation2(){
        return animationIdle2;
    }
}
