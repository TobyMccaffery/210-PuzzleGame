package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.ScreenUtils;
//import date
import java.util.Date;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
//badlogic package has classes to support graphical display of a game

import java.awt.*; //To support Java GUI
import java.util.ArrayList; //To support use of ArrayLists

/**
 * This class represents the third level. It is responsible for placing
 * all objects within the level at the beginning and changing their position
 * throughout
 */

public class LevelFour extends BaseLevel implements InputProcessor{

    //Application adapter has methods to support the state of the game such as creating, pausing, resuming and rendering
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


    Lever lever1;
    Lever lever2;
    Lever lever3;
    Lever lever4;
    Lever lever5;
    Lever lever6;
    Lever lever7;
    Lever lever8;
    Lever lever9;
    Lever lever10;
    Lever lever11;

    SmallGap gap1;
    SmallPotion small1;

    FloorGas gas1;
    FloorGas gas2;
    FloorGas gas3;
    FloorGas gas4;
    FloorGas gas5;
    FloorGas gas6;
    FloorGas gas7;
    FloorGas gas8;
    FloorGas gas9;
    FloorGas gas10;
    FloorGas gas11;
    FloorGas gas12;

    GasMask mask1;

    SpeedPotion speedPotion1;
    SpeedPotion speedPotion2;
    SpeedPotion speedPotion3;
    InvincibilityPotion invincibilityPotion1;
    InvincibilityPotion invincibilityPotion2;
    InvincibilityPotion invincibilityPotion3;
    HealthPotion healthPotion1;
    HealthPotion healthPotion2;

    Door door1;
    Door door2;
    Door door3;
    Door door4;
    Door door5;
    Door door6;
    Door door7;
    Door door8;
    Door door9;
    Door door10;
    Door door11;

    ExitPlate exitPlate1;
    ExitPlate exitPlate2;


    TileMap curLevel;

    float stateTime;

    boolean keyPressed1 = false;
    boolean keyPressed2 = false;

    //health bar for characters
    HealthBar healthBar1;
    HealthBar healthBar2;

    private int defaultWidth = 20;
    private int defaultHeight = 20;
    private double scale;
    private OrthographicCamera hudCamera;

    private Music walk;

    public static ArrayList<Door> doors = new ArrayList<>(); //List to store all doors
    public static ArrayList<Rectangle> ObjectsList = new ArrayList<>();//List of all the objects from the tilemap

    private TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    private int[] background = new int[] {0,1,2};
    private int[] foreground = new int[] {3,4};

    /**
     * Constructor for fourth level. Sets the current level to this level
     */

    public LevelFour(){
        MyGdxGame.curLevel = MyGdxGame.levels.Four;
    }

    //	private int[] background = new int[] {0};
    //	private int[] groundOverlay = new int[] {1};
    //	private int[] Objects = new int[] {2};
    //	private int[] Doors = new int[] {3};
    //	private int[] stuff = new int[] {4};

    private int lightWidth = 300;
    private int lightHeight = 300;
    private BitmapFont font;

    boolean gamePaused;
    long lastPause;

    /**
     * Creates current level by instancing with the appropriate position
     * for this particular level
     */

    @Override
    public void Create () {

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();
        tiledMap = new TmxMapLoader().load("Maps/Level4.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        Gdx.input.setInputProcessor(this);

        playerAtlasRight = new TextureAtlas("Player1.atlas");
        Textures = new TextureAtlas("TxAtlas.atlas");
        //		walls = Wall.getWalls(); //Sets wall array list to the list stored in the wall classes
        batch = new SpriteBatch(); //Instance of a spritebatch(for drawing)
        scale = (double)Gdx.graphics.getWidth() / (double)1280;
        //		scale = scale;

        lever1 = new Lever(600, 680,Textures.findRegion("LeverOff"));
        lever2 = new Lever(20, 700, Textures.findRegion("LeverOff"));
        lever3 = new Lever(740, 560, Textures.findRegion("LeverOff"));
        lever4 = new Lever(460, 560, Textures.findRegion("LeverOff"));
        lever5 = new Lever(700, 440, Textures.findRegion("LeverOff"));
        lever6 = new Lever(660, 340, Textures.findRegion("LeverOff"));
        lever7 = new Lever(1240, 540, Textures.findRegion("LeverOff"));
        lever8 = new Lever(260, 80, Textures.findRegion("LeverOff"));
        lever9 = new Lever(800, 180, Textures.findRegion("LeverOff"));
        lever10 = new Lever(1180, 500, Textures.findRegion("LeverOff"));
        lever11 = new Lever(1020, 120, Textures.findRegion("LeverOff"));


        speedPotion1 = new SpeedPotion(0, 540, 20, 20, "SpeedPotion");
        speedPotion2 = new SpeedPotion(880, 280, 20, 20, "SpeedPotion");
        speedPotion3 = new SpeedPotion(840, 200, 20, 20, "SpeedPotion");
        invincibilityPotion1 = new InvincibilityPotion(760, 400, 20, 20, "InvincibilityPotion");
        invincibilityPotion2 = new InvincibilityPotion(340, 560, 20, 20, "InvincibilityPotion");
        invincibilityPotion3 = new InvincibilityPotion(40, 0, 20, 20, "InvincibilityPotion");
        healthPotion1 = new HealthPotion(1100, 20, 20, 20, "HealthPotion");
        healthPotion2 = new HealthPotion(580, 200, 20, 20, "HealthPotion");



        door1 = new Door(1220, 220, "door", Textures.findRegion("DoorClosed"));
        door2 = new Door(140, 520, "door",Textures.findRegion("DoorClosed"));

        door3 = new Door(500, 480, "door",Textures.findRegion("DoorClosed"));
        door4 = new Door(1140, 620, "door",Textures.findRegion("DoorClosed"));
        door5 = new Door(820, 380, "door",Textures.findRegion("DoorClosed"));
        door6 = new Door(60, 240, "door",Textures.findRegion("DoorClosed"));
        door7 = new Door(1180, 240, "door",Textures.findRegion("DoorClosed"));
        door8 = new Door(40, 140, "door",Textures.findRegion("DoorClosed"));

        gap1 = new SmallGap(300, 20, "gap",Textures.findRegion("SmallGap"));
        small1 = new SmallPotion(20, 240, 20, 20, "SmallPotion");

        mask1 = new GasMask(1100, 340, 20, 20, "gasMask");


        gas1 = new FloorGas(1080, 120);
        gas2 = new FloorGas(1100, 120);
        gas3 = new FloorGas(1120, 120);
        gas4 = new FloorGas(1140, 120);
        gas5 = new FloorGas(1160, 120);
        gas6 = new FloorGas(1180, 120);
        gas7 = new FloorGas(1080, 100);
        gas8 = new FloorGas(1100, 100);
        gas9 = new FloorGas(1120, 100);
        gas10 = new FloorGas(1140, 100);
        gas11 = new FloorGas(1160, 100);
        gas12 = new FloorGas(1180, 100);

        exitPlate1 = new ExitPlate(60, 60, new TextureRegion( LevelObject.textureAtlas.findRegion("SpiralFloor")), 1);
        exitPlate2 = new ExitPlate(1220, 40, new TextureRegion( LevelObject.textureAtlas.findRegion("SpiralFloor")), 2);

        //Door combination at end of char 2 level
        door9 = new Door(1000, 40, "door", Textures.findRegion("DoorClosed"));
        door10 = new Door(1080, 20, "door",Textures.findRegion("DoorClosed"));
        door11 = new Door(1140, 40, "door",Textures.findRegion("DoorClosed"));

        TextureRegion character1img = new TextureRegion(playerAtlasRight.findRegion("Idle")); //Instance texture for our character1  image
        TextureRegion character2img =  new TextureRegion(playerAtlasRight.findRegion("Idle")); //Instance texture for character2 image

        character1 = new Character((character1img), 380, 680, '1', (TiledMapTileLayer) tiledMap.getLayers().get(2));
        character2 = new Character(character2img, 1080, 680, '2', (TiledMapTileLayer) tiledMap.getLayers().get(2));


        //Instance of our 2 characters above

        monster = new Enemy(65, 60,(TiledMapTileLayer) tiledMap.getLayers().get(2));

        //create the health bars
        healthBar1 = new HealthBar(100, 100, character1);
        healthBar2 = new HealthBar(200, 200, character2);

        walk = Gdx.audio.newMusic(Gdx.files.internal("walk.wav"));
        walk.setVolume(0.004f);

        //Characters are added to the list of level objects
        levelObjects.add(character1);
        levelObjects.add(character2);

        levelObjects.add(monster);

        levelObjects.add(lever1);
        levelObjects.add(lever2);
        levelObjects.add(lever3);
        levelObjects.add(lever4);
        levelObjects.add(lever5);
        levelObjects.add(lever6);
        levelObjects.add(lever7);
        levelObjects.add(lever8);
        levelObjects.add(lever9);
        levelObjects.add(lever10);
        levelObjects.add(lever11);

        levelObjects.add(door1);
        levelObjects.add(door2);
        levelObjects.add(door3);
        levelObjects.add(door4);
        levelObjects.add(door5);
        levelObjects.add(door6);
        levelObjects.add(door7);
        levelObjects.add(door8);

        levelObjects.add(door9);
        levelObjects.add(door10);
        levelObjects.add(door11);
        levelObjects.add(gap1);
        levelObjects.add(small1);
        levelObjects.add(mask1);

        levelObjects.add(gas1);
        levelObjects.add(gas2);
        levelObjects.add(gas3);
        levelObjects.add(gas4);
        levelObjects.add(gas5);
        levelObjects.add(gas6);
        levelObjects.add(gas7);
        levelObjects.add(gas8);
        levelObjects.add(gas9);
        levelObjects.add(gas10);
        levelObjects.add(gas11);
        levelObjects.add(gas12);

        levelObjects.add(exitPlate1);
        levelObjects.add(exitPlate2);
        levelObjects.add(speedPotion1);
        levelObjects.add(speedPotion2);
        levelObjects.add(speedPotion3);
        levelObjects.add(invincibilityPotion1);
        levelObjects.add(invincibilityPotion2);
        levelObjects.add(invincibilityPotion3);
        levelObjects.add(healthPotion1);
        levelObjects.add(healthPotion2);


        drawingObjects.add(lever1);
        drawingObjects.add(lever2);
        drawingObjects.add(lever3);
        drawingObjects.add(lever4);
        drawingObjects.add(lever5);
        drawingObjects.add(lever6);
        drawingObjects.add(lever7);
        drawingObjects.add(lever8);
        drawingObjects.add(lever9);
        drawingObjects.add(lever10);
        drawingObjects.add(lever11);



        drawingObjects.add(door1);
        drawingObjects.add(door2);
        drawingObjects.add(door3);
        drawingObjects.add(door4);
        drawingObjects.add(door5);
        drawingObjects.add(door6);
        drawingObjects.add(door7);
        drawingObjects.add(door8);

        drawingObjects.add(door9);
        drawingObjects.add(door10);
        drawingObjects.add(door11);
        drawingObjects.add(gap1);
        drawingObjects.add(small1);
        drawingObjects.add(mask1);

        drawingObjects.add(gas1);
        drawingObjects.add(gas2);
        drawingObjects.add(gas3);
        drawingObjects.add(gas4);
        drawingObjects.add(gas5);
        drawingObjects.add(gas6);
        drawingObjects.add(gas7);
        drawingObjects.add(gas8);
        drawingObjects.add(gas9);
        drawingObjects.add(gas10);
        drawingObjects.add(gas11);
        drawingObjects.add(gas12);
        drawingObjects.add(exitPlate1);
        drawingObjects.add(exitPlate2);

        drawingObjects.add(monster);

        lever1.addInteractable(door1);
        lever2.addInteractable(door2);
        lever3.addInteractable(door3);
        lever4.addInteractable(door5);
        lever5.addInteractable(door4);
        lever6.addInteractable(door6);
        lever7.addInteractable(door7);
        lever8.addInteractable(door8);

        lever9.addInteractable(door9);
        lever9.addInteractable(door11);
        lever10.addInteractable(door10);
        lever10.addInteractable(door9);
        lever11.addInteractable(door9);
        drawingObjects.add(speedPotion1);
        drawingObjects.add(speedPotion2);
        drawingObjects.add(speedPotion3);
        drawingObjects.add(invincibilityPotion1);
        drawingObjects.add(invincibilityPotion2);
        drawingObjects.add(invincibilityPotion3);
        drawingObjects.add(healthPotion1);
        drawingObjects.add(healthPotion2);


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
        font = new BitmapFont();
        darkLight = new Texture("light4.png");
        black = new Texture("black.png");

        arrows = new Texture("arrows.png");
        wasd = new Texture("wasd.png");

        hudCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        hudCamera.position.set(hudCamera.viewportWidth / 2.0f, hudCamera.viewportHeight / 2.0f, 1.0f);
    }

    /**
     * Method to move objects or otherwise change the screen
     * according to actions of the player and if the move is legal
     */

    @Override
    public void Render () {
        //if escape key pressed, pause game
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && !gamePaused){
            //if the time between the last pause is a second, pause
            if(new Date().getTime() - lastPause > 500){
                gamePaused = true;
                lastPause = new Date().getTime();
            }
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && gamePaused){
            //if the time between the last pause is a second, unpause
            if(new Date().getTime() - lastPause > 500){
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
                TextureRegion playerTexture2 = character2.getUpAnimation2().getKeyFrame(stateTime, true);
                character2.setImg(playerTexture2);
                keyPressed1 = true;
                keyPressed2 = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                character2.moveDown();
                walk.play();
                TextureRegion playerTexture2 = character2.getDownAnimation2().getKeyFrame(stateTime, true);
                character2.setImg(playerTexture2);
                keyPressed1 = true;
                keyPressed2 = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                character2.moveLeft();
                walk.play();
                TextureRegion playerTexture2 = character2.getLeftAnimation2().getKeyFrame(stateTime, true);
                character2.setImg(playerTexture2);
                keyPressed1 = true;
                keyPressed2 = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                character2.moveRight();
                walk.play();
                TextureRegion playerTexture2 = character2.getRightAnimation2().getKeyFrame(stateTime, true);
                character2.setImg(playerTexture2);
                keyPressed1 = true;
                keyPressed2 = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                character1.moveUp();
                walk.play();
                TextureRegion playerTexture1 = character1.getUpAnimation1().getKeyFrame(stateTime, true);
                character1.setImg(playerTexture1);
                keyPressed1 = true;
                keyPressed2 = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                character1.moveDown();
                walk.play();
                TextureRegion playerTexture1 = character1.getDownAnimation1().getKeyFrame(stateTime, true);
                character1.setImg(playerTexture1);
                keyPressed1 = true;
                keyPressed2 = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                character1.moveLeft();
                walk.play();
                TextureRegion playerTexture1 = character1.getLeftAnimation1().getKeyFrame(stateTime, true);
                character1.setImg(playerTexture1);
                keyPressed1 = true;
                keyPressed2 = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                character1.moveRight();
                walk.play();
                TextureRegion playerTexture1 = character1.getRightAnimation1().getKeyFrame(stateTime, true);
                character1.setImg(playerTexture1);
                keyPressed1 = true;
                keyPressed2 = true;
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
        if(deathFlag){
            MyGdxGame.clear();
        }
        if(exitPlateOne && exitPlateTwo){
            //move to next level
            MyGdxGame.curLevel = MyGdxGame.levels.Menu;
            MainMenu.won = true;
            MyGdxGame.clear();
        }


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
//        font.draw(batch, "Upper left, FPS=" + Gdx.graphics.getFramesPerSecond(), 0, hudCamera.viewportHeight);
//        font.draw(batch, "Lower left", 0, font.getLineHeight());

        //draw UI here
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        batch.setColor(1, 1, 1, 0.2f);
        renderUI(batch);
        batch.setColor(1, 1, 1, 1);

        batch.end();

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
        for (Gas g : Gas.gasObjects){
            if(g.active) {
                batch.draw(g.getImg(), (int) (g.getX()), (int) (g.getY()), (int) (defaultWidth), (int) (defaultHeight));
            }
        }
    }

    /**
     * Method to draw tile objects using our batch
     */

    public void drawTiles(){
        for (Tile t : curLevel.tiles){
            batch.draw(t.getImg(), (int)(t.getX()), (int)(t.getY()), (int)(defaultWidth), (int)(defaultHeight));
        }
    }

    /**
     * Method to draw entity objects using our batch
     */

    public void drawEntities(){
        for (LevelObject e : drawingObjects){
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
     * Method to create instances of new doors
     */
    public void createDoor(int x, int y){
        Door door = new Door(x, y, "door",Textures.findRegion("DoorClosed"));
        levelObjects.add(door);
        doors.add(door);
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
     * @param keycode passed in int value
     * @return false flag
     */

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    /**
     * Override of key down abstract class in super class
     * @param keycode passed in int value
     * @return false flag
     */

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }


    /**
     * Override of key down abstract class in super class
     * @param character passed in char value
     * @return false flag
     */

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    /**
     * Override of touchdown abstract class in super class
     * @param screenX passed in integer
     * @param screenY num
     * @param pointer num
     * @param button num
     * @return false flag
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 clickCoordinates = new Vector3(screenX,screenY,0);
        Vector3 position = camera.unproject(clickCoordinates);
        System.out.println(position.x);
        System.out.println(position.y);
        return true;
    }

    /**
     * Override of touchup abstract class in super class
     * @param screenX passed in integer
     * @param screenY num
     * @param pointer num
     * @param button num
     * @return false flag
     */

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    /**
     * Override of touch dragged abstract class in super class
     * @param screenX passed in integer
     * @param screenY num
     * @param pointer num
     * @return false flag
     */

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }


    /**
     * Override of mouse moved  abstract class in super class
     * @param screenX passed in integer
     * @param screenY num
     * @return false flag
     */

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    /**
     * Override of scrolled abstract class in super class
     * @param amountX passed in float
     * @param amountY num
     * @return false flag
     */

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
