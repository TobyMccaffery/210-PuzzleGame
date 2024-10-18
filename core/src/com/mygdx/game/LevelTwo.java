package com.mygdx.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Input;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

import com.badlogic.gdx.utils.ScreenUtils;
//import date
import java.util.Date;
//badlogic package has classes to support graphical display of a game


/**
 * This class represents the first level. It is responsible for placing
 * all objects within the level at the beginning and changing their position
 * throughout
 */

public class LevelTwo extends BaseLevel implements InputProcessor {

    private TextureAtlas playerAtlasRight;
    private TextureAtlas Textures;
    SpriteBatch batch; //Objects to draw rectangular shapes that reference a region

    TextureRegion img; //A region to draw within

    OrthographicCamera camera;
    Sprite sprite;
    //framebuffer
    FrameBuffer frameBuffer;
    FrameBuffer lightbuffer;
    TextureRegion lightBufferRegion;
    TextureRegion frameBufferRegion;
    Texture lightSprite;
    Texture darkLight;
    Texture black;
    Texture arrows;
    Texture wasd;

    //Our two characters/players
    Character character1;
    Character character2;

    Enemy monster;

    //interactables here
    Button button1;
    Door door1;
    Button button2;
    Door door2;
    Lever lever1;
    Door door3;
    Lever lever2;
    Door door4;
    PressurePlate plate1;
    Door door5;
    PressurePlate plate2;
    Lever lever3;
    Door door6;
    Lever lever4;
    Door door7;
    PressurePlate plate3;
    PressurePlate plate4;
    Door door8;
    Door door9;
    Lever lever5;
    Door door10;
    PressurePlate plate5;
    Door door11;
    Door door12;
    ExitPlate exitPlate1;
    ExitPlate exitPlate2;

    //potions here
    SpeedPotion speedPotion1;
    SpeedPotion speedPotion2;
    SpeedPotion speedPotion3;
    InvincibilityPotion invincibilityPotion1;
    InvincibilityPotion invincibilityPotion2;
    InvincibilityPotion invincibilityPotion3;
    HealthPotion healthPotion1;
    HealthPotion healthPotion2;
    HealthPotion healthPotion3;

    TileMap curLevel;
    float stateTime;

    boolean keyPressed1 = false;
    boolean keyPressed2 = false;

    boolean gamePaused = false;
    long lastPause;

    //health bar for characters
    HealthBar healthBar1;
    HealthBar healthBar2;

    private int defaultWidth = 20;
    private int defaultHeight = 20;
    private double scale;
    private OrthographicCamera hudCamera;

    private Music walk;

    private TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    private int[] background = new int[] {0,1,2};
    private int[] foreground = new int[] {3,4};

    /**
     * Constructor for level
     * Sets the current level to this instanced object
     */
    public LevelTwo(){
        MyGdxGame.curLevel = MyGdxGame.levels.One;
    }

    /**
     * Creates current level by instancing with the appropriate position
     * for this particular level
     */
    @Override
    public void Create(){
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();
        tiledMap = new TmxMapLoader().load("Maps/LevelOne.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        Gdx.input.setInputProcessor(this);

        playerAtlasRight = new TextureAtlas("Player1.atlas");
        Textures = new TextureAtlas("TxAtlas.atlas");
//		walls = Wall.getWalls(); //Sets wall array list to the list stored in the wall classes
        batch = new SpriteBatch(); //Instance of a spritebatch(for drawing)
        scale = (double)Gdx.graphics.getWidth() / (double)1280;
//		scale = scale;

        TextureRegion character1img = new TextureRegion(playerAtlasRight.findRegion("Idle")); //Instance texture for our character1  image
        TextureRegion character2img =  new TextureRegion(playerAtlasRight.findRegion("Idle")); //Instance texture for character2 image

        character1 = new Character((character1img), 40, 340, '1', (TiledMapTileLayer) tiledMap.getLayers().get(2));
        character2 = new Character(character2img, 940, 500, '2', (TiledMapTileLayer) tiledMap.getLayers().get(2));
        monster = new Enemy(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2,(TiledMapTileLayer) tiledMap.getLayers().get(2));

        //create the health bars
        healthBar1 = new HealthBar(100, 100, character1);
        healthBar2 = new HealthBar(200, 200, character2);

        //create the interactables
        button1 = new Button(0, 340, Textures.findRegion("ButtonNotPressed"));
        door1 = new Door(860, 400, "door", Textures.findRegion("DoorClosed"));
        button2 = new Button(880, 440, Textures.findRegion("ButtonNotPressed"));
        door2 = new Door(160, 300, "door", Textures.findRegion("DoorClosed"));
        lever1 = new Lever(180, 340, Textures.findRegion("LeverOff"));
        door3 = new Door(380, 400, "door", Textures.findRegion("DoorClosed"));
        lever2 = new Lever(400, 500, Textures.findRegion("LeverOff"));
        door4 = new Door(260, 120, "door", Textures.findRegion("DoorClosed"));
        plate1 = new PressurePlate(120, 240, new TextureRegion( LevelObject.textureAtlas.findRegion("PressurePlateUp")));
        door5 = new Door(200, 680, "door", Textures.findRegion("DoorClosed"));
        plate2 = new PressurePlate(480, 220, new TextureRegion( LevelObject.textureAtlas.findRegion("PressurePlateUp")));
        lever3 = new Lever(20, 520, Textures.findRegion("LeverOff"));
        door6 = new Door(680, 320, "door", Textures.findRegion("DoorClosed"));
        lever4 = new Lever(580, 320, Textures.findRegion("LeverOff"));
        door7 = new Door(160, 600, "door", Textures.findRegion("DoorClosed"));
        plate3 = new PressurePlate(740, 500, new TextureRegion( LevelObject.textureAtlas.findRegion("PressurePlateUp")));
        plate4 = new PressurePlate(20, 400, new TextureRegion( LevelObject.textureAtlas.findRegion("PressurePlateUp")));
        door8 = new Door(1000, 40, "door", Textures.findRegion("DoorClosed"));
        door9 = new Door(1200, 40, "door", Textures.findRegion("DoorClosed"));
        lever5 = new Lever(1000, 140, Textures.findRegion("LeverOff"));
        door10 = new Door(1200, 400, "door", Textures.findRegion("DoorClosed"));
        plate5 = new PressurePlate(1180, 460, new TextureRegion( LevelObject.textureAtlas.findRegion("PressurePlateUp")));
        door11 = new Door(980, 320, "door", Textures.findRegion("DoorClosed"));
        door12 = new Door(1080, 240, "door", Textures.findRegion("DoorClosed"));
//        exitPlate1 = new ExitPlate(60, 0, new TextureRegion( LevelObject.textureAtlas.findRegion("SpiralFloor")), 1);
//        exitPlate2 = new ExitPlate(940, 680, new TextureRegion( LevelObject.textureAtlas.findRegion("SpiralFloor")), 2);
        exitPlate1 = new ExitPlate(1220, 220, new TextureRegion( LevelObject.textureAtlas.findRegion("SpiralFloor")), 1);
        exitPlate2 = new ExitPlate(1240, 680, new TextureRegion( LevelObject.textureAtlas.findRegion("SpiralFloor")), 2);

        //create the potions
        speedPotion1 = new SpeedPotion(0, 200, 20, 20, "SpeedPotion");
        speedPotion2 = new SpeedPotion(800, 500, 20, 20, "SpeedPotion");
        speedPotion3 = new SpeedPotion(240, 400, 20, 20, "SpeedPotion");
        invincibilityPotion1 = new InvincibilityPotion(760, 400, 20, 20, "InvincibilityPotion");
        invincibilityPotion2 = new InvincibilityPotion(540, 0, 20, 20, "InvincibilityPotion");
        invincibilityPotion3 = new InvincibilityPotion(1200, 160, 20, 20, "InvincibilityPotion");
        healthPotion1 = new HealthPotion(700, 220, 20, 20, "HealthPotion");
        healthPotion2 = new HealthPotion(360, 540, 20, 20, "HealthPotion");
        healthPotion3 = new HealthPotion(960, 200, 20, 20, "HealthPotion");

        walk = Gdx.audio.newMusic(Gdx.files.internal("walk.wav"));
        walk.setVolume(0.004f);

        //interactables linked
        button1.addInteractable(door1);
        button2.addInteractable(door2);
        lever1.addInteractable(door3);
        lever2.addInteractable(door4);
        plate1.addInteractable(door1);
        plate2.addInteractable(door5);
        lever3.addInteractable(door6);
        lever4.addInteractable(door7);
        lever4.addInteractable(door5);
        plate4.addInteractable(door8);
        plate4.addInteractable(door9);
        plate3.addInteractable(door8);
        plate3.addInteractable(door9);
        lever5.addInteractable(door10);
        lever4.addInteractable(door1);
        plate5.addInteractable(door11);
        plate5.addInteractable(door12);


        //potions here


        //Characters are added to the list of level objects
        levelObjects.add(character1);
        levelObjects.add(character2);
        levelObjects.add(monster);

        //Interactables are added to the list of level objects
        levelObjects.add(button1);
        levelObjects.add(door1);
        levelObjects.add(button2);
        levelObjects.add(door2);
        levelObjects.add(lever1);
        levelObjects.add(door3);
        levelObjects.add(lever2);
        levelObjects.add(door4);
        levelObjects.add(plate1);
        levelObjects.add(door5);
        levelObjects.add(plate2);
        levelObjects.add(lever3);
        levelObjects.add(door6);
        levelObjects.add(lever4);
        levelObjects.add(door7);
        levelObjects.add(plate3);
        levelObjects.add(plate4);
        levelObjects.add(door8);
        levelObjects.add(door9);
        levelObjects.add(lever5);
        levelObjects.add(door10);
        levelObjects.add(plate5);
        levelObjects.add(door11);
        levelObjects.add(door12);
        levelObjects.add(exitPlate1);
        levelObjects.add(exitPlate2);

        //potions here
        levelObjects.add(speedPotion1);
        levelObjects.add(speedPotion2);
        levelObjects.add(speedPotion3);
        levelObjects.add(invincibilityPotion1);
        levelObjects.add(invincibilityPotion2);
        levelObjects.add(invincibilityPotion3);
        levelObjects.add(healthPotion1);
        levelObjects.add(healthPotion2);
        levelObjects.add(healthPotion3);


        //drawing objects
        drawingObjects.add(monster);

        //add the interactables to drawing objects
        drawingObjects.add(button1);
        drawingObjects.add(door1);
        drawingObjects.add(button2);
        drawingObjects.add(door2);
        drawingObjects.add(lever1);
        drawingObjects.add(door3);
        drawingObjects.add(lever2);
        drawingObjects.add(door4);
        drawingObjects.add(plate1);
        drawingObjects.add(door5);
        drawingObjects.add(plate2);
        drawingObjects.add(lever3);
        drawingObjects.add(door6);
        drawingObjects.add(lever4);
        drawingObjects.add(door7);
        drawingObjects.add(plate3);
        drawingObjects.add(plate4);
        drawingObjects.add(door8);
        drawingObjects.add(door9);
        drawingObjects.add(lever5);
        drawingObjects.add(door10);
        drawingObjects.add(plate5);
        drawingObjects.add(door11);
        drawingObjects.add(door12);
        drawingObjects.add(exitPlate1);
        drawingObjects.add(exitPlate2);

        //potions here
        drawingObjects.add(speedPotion1);
        drawingObjects.add(speedPotion2);
        drawingObjects.add(speedPotion3);
        drawingObjects.add(invincibilityPotion1);
        drawingObjects.add(invincibilityPotion2);
        drawingObjects.add(invincibilityPotion3);
        drawingObjects.add(healthPotion1);
        drawingObjects.add(healthPotion2);
        drawingObjects.add(healthPotion3);



//		curLevel = new TileMap(64, 36, 640, 360, 20);
//		curLevel.createMap();
        parseMap();
        stateTime = 0f;

//		levelObjects.addAll(curLevel.tiles);

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //Instance of an orthographic camera
        camera.translate(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0); //Sets the position of the camera

        //creates a framebuffer
        frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        lightbuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        lightbuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        lightBufferRegion = new TextureRegion(lightbuffer.getColorBufferTexture(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        lightBufferRegion.flip(false, true);

        frameBufferRegion = new TextureRegion(frameBuffer.getColorBufferTexture(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        frameBufferRegion.flip(false, true);

        stateTime = 0f;
//		levelObjects.addAll(curLevel.tiles);

        lightSprite = new Texture("light3.png");
        sprite = new Sprite(lightSprite);

        gamePaused = false;
        lastPause = new Date().getTime();
        lightSprite = new Texture("light2.png");
        //font = new BitmapFont();
        darkLight = new Texture("light4.png");
        black = new Texture("black.png");

        arrows = new Texture("arrows.png");
        wasd = new Texture("wasd.png");

        hudCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        hudCamera.position.set(hudCamera.viewportWidth / 2.0f, hudCamera.viewportHeight / 2.0f, 1.0f);
    }


    /**
     * Render method to move objects or otherwise change the screen
     * according to actions of the player and if the move is legal
     */
    @Override
    public void Render(){
    //if escape key pressed, pause game
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && !gamePaused) {
            //if the time between the last pause is a second, pause
            if (new Date().getTime() - lastPause > 500) {
                gamePaused = true;
                lastPause = new Date().getTime();
            }
        }
		else if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && gamePaused) {
            //if the time between the last pause is a second, unpause
            if (new Date().getTime() - lastPause > 500) {
                gamePaused = false;
                lastPause = new Date().getTime();
            }
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.E) && gamePaused){
            //go back to the main menu
            MyGdxGame.curLevel = MyGdxGame.levels.Menu;
            MyGdxGame.clear();
        }
        if(!gamePaused) {
            stateTime += Gdx.graphics.getDeltaTime(); //Adds the time since the last frame to the stateTime variable
            camera.update(); //Updates the camera
            batch.setProjectionMatrix(camera.combined); //Sets the projection matrix to the camera
            //The above is to update both characters position upon appropriate input from both players
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                character2.moveUp();
                walk.play();
                TextureRegion playerTexture2 = character2.getUpAnimation2().getKeyFrame(stateTime, true);
                character2.setImg(playerTexture2);
                keyPressed2 = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                character2.moveDown();
                walk.play();
                TextureRegion playerTexture2 = character2.getDownAnimation2().getKeyFrame(stateTime, true);
                character2.setImg(playerTexture2);
                keyPressed2 = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                character2.moveLeft();
                walk.play();
                TextureRegion playerTexture2 = character2.getLeftAnimation2().getKeyFrame(stateTime, true);
                character2.setImg(playerTexture2);
                keyPressed2 = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                character2.moveRight();
                walk.play();
                TextureRegion playerTexture2 = character2.getRightAnimation2().getKeyFrame(stateTime, true);
                character2.setImg(playerTexture2);
                keyPressed2 = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                character1.moveUp();
                walk.play();
                TextureRegion playerTexture1 = character1.getUpAnimation1().getKeyFrame(stateTime, true);
                character1.setImg(playerTexture1);
                keyPressed1 = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                character1.moveDown();
                walk.play();
                TextureRegion playerTexture1 = character1.getDownAnimation1().getKeyFrame(stateTime, true);
                character1.setImg(playerTexture1);
                keyPressed1 = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                character1.moveLeft();
                walk.play();
                TextureRegion playerTexture1 = character1.getLeftAnimation1().getKeyFrame(stateTime, true);
                character1.setImg(playerTexture1);
                keyPressed1 = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                character1.moveRight();
                walk.play();
                TextureRegion playerTexture1 = character1.getRightAnimation1().getKeyFrame(stateTime, true);
                character1.setImg(playerTexture1);
                keyPressed1 = true;
            }

            if (keyPressed1 == false) {
                TextureRegion playerTexture1 = character1.getIdleAnimation1().getKeyFrame(stateTime, true);
                character1.setImg(playerTexture1);
            }
            if (keyPressed2 == false) {
                TextureRegion playerTexture2 = character2.getIdleAnimation2().getKeyFrame(stateTime, true);
                character2.setImg(playerTexture2);
            }

            //Move players according to speed and only if the move is legal
            character1.move();
            character2.move();
            //Move players according to speed and only if the move is legal

            character1.isOverlapping();
            character2.isOverlapping();

            monster.placeGas();

            for (int i = 0; i < levelObjects.size(); i++) {
                if (levelObjects.get(i) instanceof Button) {
                    Button button = (Button) levelObjects.get(i);
                    button.checkunToggle();
                }
            }

            ScreenUtils.clear(0, 0, 0, 1);
            //grab the enemy texture animation
            //			Texture enemyTexture = monster.getAnimation().getKeyFrame(stateTime, true);
            //			monster.setImg(enemyTexture);

            tiledMapRenderer.setView(camera);

            //grab the enemy texture animation
            //		Texture enemyTexture = monster.getAnimation().getKeyFrame(stateTime, true);
            //		monster.setImg(enemyTexture);
            monster.pathfind(character1, character2);

            //render the characters, tiles and items
        }

        float distanceToMoveFromCamX = camera.position.x - (float)Gdx.graphics.getWidth()/2;
        float distanceToMoveFromCamY = camera.position.y - (float)Gdx.graphics.getHeight()/2;

        int characterLightWidth = 300;
        int characterLightHeight = 300;
        int normalLightWidth = 200;
        int normalLightHeight = 200;

        Gdx.gl.glClearColor(0, 0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        batch.begin();
        ScreenUtils.clear(0, 0, 0, 1);
        //		//set the batch to draw darker
        //batch.setColor(0.2f, 0.2f, 0.2f, 1f);
        //batch.setColor(0.2f, 0.2f, 0.2f, 1f);
        //drawTiles();
        tiledMapRenderer.render(background);
        drawEntities(batch);
        drawPlayers();
        drawGas();
        drawEnemy(batch);
        healthBar1.render(batch);
        healthBar2.render(batch);
        tiledMapRenderer.render(foreground);
        batch.end();


        frameBuffer.begin();
        Gdx.gl.glClearColor(0.1f,0.1f,0.1f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //change the blending function
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        //		batch.setProjectionMatrix(camera.combined); //Sets the projection matrix to the camera
        batch.begin();
        //		batch.setProjectionMatrix(camera.combined);
        //render the lights
        //		batch.draw(lightSprite, 0, 0);
        for (LevelObject e : drawingObjects){
            //if it is not the enemy
            if(!(e instanceof Enemy)){
                if(e instanceof Item){
                    batch.draw(((Item) e).getLight(), ((e.getX()) - normalLightWidth/2 + 10 + distanceToMoveFromCamX) , ((e.getY()) - normalLightHeight/2 + 10 + distanceToMoveFromCamY) , normalLightWidth, normalLightHeight);
                } else {
                    batch.draw(darkLight, ((e.getX()) - normalLightWidth/2 + 10 + distanceToMoveFromCamX) , ((e.getY()) - normalLightHeight/2 + 10 + distanceToMoveFromCamY) , normalLightWidth, normalLightHeight);
                }
            }
        }
        //draw lights on each character

        batch.draw(lightSprite, (character1.getX()) - characterLightWidth/2 + 10 + distanceToMoveFromCamX, (character1.getY()) - characterLightHeight/2 + 10 + distanceToMoveFromCamY, characterLightWidth, characterLightHeight);
        batch.draw(lightSprite, (character2.getX()) - characterLightWidth/2 + 10 + distanceToMoveFromCamX, (character2.getY()) - characterLightHeight/2 + 10 + distanceToMoveFromCamY, characterLightWidth, characterLightHeight);
        batch.end();
        frameBuffer.end();

        //change the blending function
        batch.setBlendFunction(GL20.GL_DST_COLOR, GL20.GL_ZERO);
        batch.begin();
        batch.setColor(1, 1, 1, 1);
        batch.draw(frameBufferRegion, 0, 0);
        batch.end();

        keyPressed1 = false;
        keyPressed2 = false;

        hudCamera.update();
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();
        //font.draw(batch, "Upper left, FPS=" + Gdx.graphics.getFramesPerSecond(), 0, hudCamera.viewportHeight);
        //font.draw(batch, "Lower left", 0, font.getLineHeight());

        //draw UI here
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        renderUI(batch);

        batch.end();
        if(deathFlag){
            MyGdxGame.clear();
        }
        if(exitPlateOne && exitPlateTwo){
            //move to next level
            MyGdxGame.curLevel = MyGdxGame.levels.Three;
            MyGdxGame.clear();
        }

        if(gamePaused){
            //draw dark background
            batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            batch.begin();
            batch.setColor(1f, 1f, 1f, 0.7f);
            batch.draw(black, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            //render the pause menu
            renderPauseScreen(batch);
            batch.end();

        }
    }


    /**
     * Method to draw gas objects using our batch
     */
    public void drawGas(){
        Texture gasTexture = new Texture("new-assets/gas-new.png");
        for (Gas g : Gas.gasObjects){
            if(g.active) {
                batch.draw(gasTexture, (int) (g.getX()), (int) (g.getY()), (int) (defaultWidth), (int) (defaultHeight));
            }
        }
    }

    /**
     * Method to draw entity objects using our batch
     */

    public void drawEntities(){
        for (LevelObject e : this.drawingObjects){
            batch.draw(e.getImg(), (int)(e.getX()), (int)(e.getY()), (int)(defaultWidth), (int)(defaultHeight));
        }
    }

    /**
     * Method to draw player objects using our batch
     */

    public void drawPlayers(){
        batch.draw(character1.getImg(), (int)(character1.getX() * scale), (int)(character1.getY() * scale), (int)(defaultWidth * scale), (int)(defaultHeight * scale));
        batch.draw(character2.getImg(), (int)(character2.getX() * scale), (int)(character2.getY() * scale), (int)(defaultWidth * scale), (int)(defaultHeight * scale));
    }

    /**
     * Method to add rectangle or polygon objects to character players
     * depending on the type of map object they have collided with
     * @return error flag indicating no suitable object to collide with was identified
     */

    private boolean parseMap () {
        MapObjects collisions = tiledMap.getLayers().get("Collisions").getObjects();
        for (int i = 0; i < collisions.getCount(); i++) {
            MapObject mapObject = collisions.get(i);
            if (mapObject instanceof RectangleMapObject) {
                Rectangle rectangle =  ((RectangleMapObject) mapObject).getRectangle();
                character1.addRectangle(rectangle);

            }
            else if (mapObject instanceof PolygonMapObject) {
                float[] vertices = ((PolygonMapObject) mapObject).getPolygon().getTransformedVertices();
                Polygon polygon = new Polygon(vertices);
                character1.addPoly(polygon);

            }

        }
        return false;
    }

    /**
     * Override of key down abstract class in super class
     * @param i passed in int value
     * @return false flag
     */
    @Override
    public boolean keyDown(int i) {
        return false;
    }

    /**
     * Override of key up abstract class in super class
     * @param i passed in int value
     * @return false flag
     */

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    /**
     * Override of key typed abstract class in super class
     * @param c passed in char value
     * @return false flag
     */

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    /**
     * Override of touchdown abstract class in super class
     * @param i passed in integer
     * @param i1 num
     * @param i2 num
     * @param i3 num
     * @return false flag
     */

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    /**
     * Override of touchup abstract class in super class
     * @param i passed in integer
     * @param i1 num
     * @param i2 num
     * @param i3 num
     * @return false flag
     */

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    /**
     * Override of touch dragged abstract class in super class
     * @param i passed in integer
     * @param i1 num
     * @param i2 num
     * @return false flag
     */

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    /**
     * Override of mouse moved abstract class in super class
     * @param i passed in integer
     * @param i1 num
     * @return false flag
     */

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    /**
     * Override of scrolled abstract class in super class
     * @param v passed in float
     * @param v1 num
     * @return false flag
     */

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }
}
