package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Date;

/**
 * This class represents the third level. It is responsible for placing
 * all objects within the level at the beginning and changing their position
 * throughout
 */

public class Level3 extends BaseLevel implements InputProcessor {

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



    Door door1080_640;
    Door door980_460;
    Door door1220_120;
    Door door980_180;
    Door door920_40;
    Door door720_140;
    Door door380_40;
    Door door360_420;
    Door door80_140;
    Door door480_560;
    Door door540_560;
    
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
    FloorGas gas13;
    FloorGas gas14;
    FloorGas gas15;
    FloorGas gas16;
    FloorGas gas17;
    FloorGas gas18;
    FloorGas gas19;
    FloorGas gas20;
    FloorGas gas21;
    FloorGas gas22;
    FloorGas gas23;
    FloorGas gas24;
    FloorGas gas25;
    FloorGas gas26;
    FloorGas gas27;
    FloorGas gas28;
    FloorGas gas29;
    FloorGas gas30;
    FloorGas gas31;
    FloorGas gas32;

    FloorGas gas33;
    FloorGas gas34;
    FloorGas gas35;
    FloorGas gas36;
    FloorGas gas37;
    FloorGas gas38;
    FloorGas gas39;
    FloorGas gas40;
    FloorGas gas41;
    FloorGas gas42;
    FloorGas gas43;
    FloorGas gas44;
    FloorGas gas45;
    FloorGas gas46;
    FloorGas gas47;
    FloorGas gas48;
    FloorGas gas49;
    FloorGas gas50;
    FloorGas gas51;
    FloorGas gas52;
    FloorGas gas53;
    FloorGas gas54;
    FloorGas gas55;
    FloorGas gas56;
    
    GasMask mask1;
    GasMask mask2;
    SmallPotion small1;
    SmallGap gap1;

    Button btnQuadTwo;

    Lever lvr1000_500;
    Button btn860_160;
    Button btn680_0;
    Lever lvr680_160;
    Button btn500_160;
    Button btn400_0;
    Lever lvr0_535;
    Lever lvr0_630;
    Button btn300_450;

    Lever lvrCentre;
    ExitPlate exitPlate1;
    ExitPlate exitPlate2;

    Gas gasTest;
    SpeedPotion speedPotion1;
    SpeedPotion speedPotion2;
    SpeedPotion speedPotion3;
    InvincibilityPotion invincibilityPotion2;
    InvincibilityPotion invincibilityPotion3;
    InvincibilityPotion invincibilityPotion1;
    HealthPotion healthPotion1;
    HealthPotion healthPotion2;
    HealthPotion healthPotion3;
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

    private TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    private int[] background = new int[] {0,1,2};
    private int[] foreground = new int[] {3,4};

    public Level3(){
        MyGdxGame.curLevel = MyGdxGame.levels.Three;
    }

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

        tiledMap = new TmxMapLoader().load("Maps/level3Redux.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        Gdx.input.setInputProcessor(this);

        playerAtlasRight = new TextureAtlas("Player1.atlas");
        Textures = new TextureAtlas("TxAtlas.atlas");
        batch = new SpriteBatch(); //Instance of a spritebatch(for drawing)
        scale = (double)Gdx.graphics.getWidth() / (double)1280;



        door1080_640 = new Door(1080, 640, "door",Textures.findRegion("DoorClosed"));
        door980_460 = new Door(980, 460, "door", Textures.findRegion("DoorClosed"));
        door1220_120 = new Door(1220, 120, "door", Textures.findRegion("DoorClosed"));
        door980_180 = new Door(980, 120, "door", Textures.findRegion("DoorClosed"));
        door920_40 = new Door(920, 260, "door", Textures.findRegion("DoorClosed"));
        door720_140 = new Door(680, 220, "door", Textures.findRegion("DoorClosed"));
        door380_40 = new Door(380, 40, "door", Textures.findRegion("DoorClosed"));
        door360_420 = new Door(240, 420, "door", Textures.findRegion("DoorClosed"));
        door80_140 = new Door(80, 140, "door", Textures.findRegion("DoorClosed"));
        door480_560 = new Door(480, 560, "door", Textures.findRegion("DoorClosed"));
        door540_560 = new Door(540, 560, "door", Textures.findRegion("DoorClosed"));
        gap1 = new SmallGap(920, 40, "door", Textures.findRegion("DoorClosed"));
        small1 = new SmallPotion(980, 160, 20, 20, "smallPotion");


        btnQuadTwo = new Button(1140, 220,Textures.findRegion("ButtonNotPressed"));
        lvr1000_500 = new Lever(1260, 500, Textures.findRegion("ButtonNotPressed"));
        btn860_160 = new Button(860, 220, Textures.findRegion("ButtonNotPressed"));
        btn680_0 = new Button(720, 200, Textures.findRegion("ButtonNotPressed"));
        lvr680_160 = new Lever(0, 600, Textures.findRegion("ButtonNotPressed"));
        btn500_160 = new Button(460, 200, Textures.findRegion("ButtonNotPressed"));
        btn400_0 = new Button(380, 500, Textures.findRegion("ButtonNotPressed"));
        lvr0_535 = new Lever(0, 535, Textures.findRegion("ButtonNotPressed"));
        lvr0_630 = new Lever(0, 630, Textures.findRegion("ButtonNotPressed"));
        btn300_450 = new Button(360, 120, Textures.findRegion("ButtonNotPressed"));
        
        gas1 = new FloorGas(140, 220);
        gas2 = new FloorGas(140, 240);
        gas3 = new FloorGas(140, 260);
        gas4 = new FloorGas(140, 280);
        gas5 = new FloorGas(140, 300);
        gas6 = new FloorGas(140, 320);
        gas7 = new FloorGas(140, 340);
        gas8 = new FloorGas(140, 360);
        gas9 = new FloorGas(160, 220);
        gas10 = new FloorGas(160, 240);
        gas11 = new FloorGas(160, 260);
        gas12 = new FloorGas(160, 280);
        gas13 = new FloorGas(160, 300);
        gas14 = new FloorGas(160, 320);
        gas15 = new FloorGas(160, 340);
        gas16 = new FloorGas(160, 360);
        gas17 = new FloorGas(180, 220);
        gas18 = new FloorGas(180, 240);
        gas19 = new FloorGas(180, 260);
        gas20 = new FloorGas(180, 280);
        gas21 = new FloorGas(180, 300);
        gas22 = new FloorGas(180, 320);
        gas23 = new FloorGas(180, 340);
        gas24 = new FloorGas(180, 360);
        gas25 = new FloorGas(200, 220);
        gas26 = new FloorGas(200, 240);
        gas27 = new FloorGas(200, 260);
        gas28 = new FloorGas(200, 280);
        gas29 = new FloorGas(200, 300);
        gas30 = new FloorGas(200, 320);
        gas31 = new FloorGas(200, 340);
        gas32 = new FloorGas(200, 360);

        gas33 = new FloorGas(780, 400);
        gas34 = new FloorGas(800, 400);
        gas35 = new FloorGas(820, 400);
        gas36 = new FloorGas(840, 400);
        gas37 = new FloorGas(860, 400);
        gas38 = new FloorGas(880, 400);
        gas39 = new FloorGas(900, 400);
        gas40 = new FloorGas(920, 400);
        gas41 = new FloorGas(780, 380);
        gas42 = new FloorGas(800, 380);
        gas43 = new FloorGas(820, 380);
        gas44 = new FloorGas(840, 380);
        gas45 = new FloorGas(860, 380);
        gas46 = new FloorGas(880, 380);
        gas47 = new FloorGas(900, 380);
        gas48 = new FloorGas(920, 380);
        gas49 = new FloorGas(780, 360);
        gas50 = new FloorGas(800, 360);
        gas51 = new FloorGas(820, 360);
        gas52 = new FloorGas(840, 360);
        gas53 = new FloorGas(860, 360);
        gas54 = new FloorGas(880, 360);
        gas55 = new FloorGas(900, 360);
        gas56 = new FloorGas(920, 360);

        lvrCentre = new Lever(680, 380, Textures.findRegion("ButtonNotPressed"));


        gasTest = new Gas(100,50);

        exitPlate1 = new ExitPlate(580, 640, new TextureRegion( LevelObject.textureAtlas.findRegion("SpiralFloor")), 1);
        exitPlate2 = new ExitPlate(1220, 640, new TextureRegion( LevelObject.textureAtlas.findRegion("SpiralFloor")), 2);


        TextureRegion character1img = new TextureRegion(playerAtlasRight.findRegion("Idle")); //Instance texture for our character1  image
        TextureRegion character2img =  new TextureRegion(playerAtlasRight.findRegion("Idle")); //Instance texture for character2 image

        character1 = new Character((character1img), 300, 20, '1', (TiledMapTileLayer) tiledMap.getLayers().get(2));
        character2 = new Character(character2img, 1000, 20, '2', (TiledMapTileLayer) tiledMap.getLayers().get(2));

        //Instance of our 2 characters above

        monster = new Enemy(65, 60,(TiledMapTileLayer) tiledMap.getLayers().get(2));
        speedPotion1 = new SpeedPotion(300, 300, 20, 20, "speedPotion");
        speedPotion2 = new SpeedPotion(300, 680, 20, 20, "speedPotion");
        speedPotion3 = new SpeedPotion(1200, 340, 20, 20, "speedPotion");
        invincibilityPotion1 = new InvincibilityPotion(140, 500, 20, 20, "invincibilityPotion");
        invincibilityPotion2 = new InvincibilityPotion(460, 340, 20, 20, "invincibilityPotion");
        invincibilityPotion3 = new InvincibilityPotion(800, 160, 20, 20, "invincibilityPotion");
        healthPotion1 = new HealthPotion(360, 100, 20, 20, "healthPotion");
        healthPotion2 = new HealthPotion(600, 260, 20, 20, "healthPotion");
        healthPotion3 = new HealthPotion(1200, 100, 20, 20, "healthPotion");
        mask1 = new GasMask(300, 540, 20, 20, "gasMask");
        mask2 = new GasMask(740, 680, 20, 20, "gasMask");

        //create the health bars
        healthBar1 = new HealthBar(100, 100, character1);
        healthBar2 = new HealthBar(200, 200, character2);

        walk = Gdx.audio.newMusic(Gdx.files.internal("walk.wav"));
        walk.setVolume(0.004f);

        //Characters are added to the list of level objects
        levelObjects.add(character1);
        levelObjects.add(character2);

        levelObjects.add(monster);


        levelObjects.add(door1080_640);
        levelObjects.add(door980_460);
        levelObjects.add(door1220_120);
        levelObjects.add(door980_180);
        levelObjects.add(door920_40);
        levelObjects.add(door720_140);
        levelObjects.add(door380_40);
        levelObjects.add(door360_420);
        levelObjects.add(door80_140);
        levelObjects.add(door480_560);
        levelObjects.add(door540_560);
        levelObjects.add(gap1);

        levelObjects.add(btnQuadTwo);
        levelObjects.add(lvr1000_500);
        levelObjects.add(btn860_160);
        levelObjects.add(btn680_0);
        levelObjects.add(lvr680_160);
        levelObjects.add(btn500_160);
        levelObjects.add(btn400_0);
        levelObjects.add(lvr0_535);
        levelObjects.add(lvr0_630);
        levelObjects.add(btn300_450);
        levelObjects.add(lvrCentre);
        levelObjects.add(exitPlate1);
        levelObjects.add(exitPlate2);
        
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
        levelObjects.add(gas13);
        levelObjects.add(gas14);
        levelObjects.add(gas15);
        levelObjects.add(gas16);
        levelObjects.add(gas17);
        levelObjects.add(gas18);
        levelObjects.add(gas19);
        levelObjects.add(gas20);
        levelObjects.add(gas21);
        levelObjects.add(gas22);
        levelObjects.add(gas23);
        levelObjects.add(gas24);
        levelObjects.add(gas25);
        levelObjects.add(gas26);
        levelObjects.add(gas27);
        levelObjects.add(gas28);
        levelObjects.add(gas29);
        levelObjects.add(gas30);
        levelObjects.add(gas31);
        levelObjects.add(gas32);
        levelObjects.add(gas33);
        levelObjects.add(gas34);
        levelObjects.add(gas35);
        levelObjects.add(gas36);
        levelObjects.add(gas37);
        levelObjects.add(gas38);
        levelObjects.add(gas39);
        levelObjects.add(gas40);
        levelObjects.add(gas41);
        levelObjects.add(gas42);
        levelObjects.add(gas43);
        levelObjects.add(gas44);
        levelObjects.add(gas45);
        levelObjects.add(gas46);
        levelObjects.add(gas47);
        levelObjects.add(gas48);
        levelObjects.add(gas49);
        levelObjects.add(gas50);
        levelObjects.add(gas51);
        levelObjects.add(gas52);
        levelObjects.add(gas53);
        levelObjects.add(gas54);
        levelObjects.add(gas55);
        levelObjects.add(gas56);


        levelObjects.add(speedPotion1);
        levelObjects.add(speedPotion2);
        levelObjects.add(speedPotion3);
        levelObjects.add(invincibilityPotion1);
        levelObjects.add(invincibilityPotion2);
        levelObjects.add(invincibilityPotion3);
        levelObjects.add(healthPotion1);
        levelObjects.add(healthPotion2);
        levelObjects.add(healthPotion3);
        levelObjects.add(mask1);
        levelObjects.add(mask2);
        levelObjects.add(small1);



        drawingObjects.add(door1080_640);
        drawingObjects.add(door980_460);
        drawingObjects.add(door1220_120);
        drawingObjects.add(door980_180);
        drawingObjects.add(door920_40);
        drawingObjects.add(door720_140);
        drawingObjects.add(door380_40);
        drawingObjects.add(door360_420);
        drawingObjects.add(door80_140);
        drawingObjects.add(door480_560);
        drawingObjects.add(door540_560);

        drawingObjects.add(btnQuadTwo);
        drawingObjects.add(lvr1000_500);
        drawingObjects.add(btn860_160);
        drawingObjects.add(btn680_0);
        drawingObjects.add(lvr680_160);
        drawingObjects.add(btn500_160);
        drawingObjects.add(btn400_0);
        drawingObjects.add(lvr0_535);
        drawingObjects.add(lvr0_630);
        drawingObjects.add(btn300_450);
        drawingObjects.add(lvrCentre);
        drawingObjects.add(small1);
        drawingObjects.add(gap1);
        


        drawingObjects.add(speedPotion1);
        drawingObjects.add(speedPotion2);
        drawingObjects.add(speedPotion3);
        drawingObjects.add(invincibilityPotion1);
        drawingObjects.add(invincibilityPotion2);
        drawingObjects.add(invincibilityPotion3);
        drawingObjects.add(healthPotion1);
        drawingObjects.add(healthPotion2);
        drawingObjects.add(healthPotion3);
        drawingObjects.add(mask1);
        drawingObjects.add(mask2);
        drawingObjects.add(monster);
        drawingObjects.add(exitPlate1);
        drawingObjects.add(exitPlate2);
        
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
        drawingObjects.add(gas13);
        drawingObjects.add(gas14);
        drawingObjects.add(gas15);
        drawingObjects.add(gas16);
        drawingObjects.add(gas17);
        drawingObjects.add(gas18);
        drawingObjects.add(gas19);
        drawingObjects.add(gas20);
        drawingObjects.add(gas21);
        drawingObjects.add(gas22);
        drawingObjects.add(gas23);
        drawingObjects.add(gas24);
        drawingObjects.add(gas25);
        drawingObjects.add(gas26);
        drawingObjects.add(gas27);
        drawingObjects.add(gas28);
        drawingObjects.add(gas29);
        drawingObjects.add(gas30);
        drawingObjects.add(gas31);
        drawingObjects.add(gas32);
        drawingObjects.add(gas33);
        drawingObjects.add(gas34);
        drawingObjects.add(gas35);
        drawingObjects.add(gas36);
        drawingObjects.add(gas37);
        drawingObjects.add(gas38);
        drawingObjects.add(gas39);
        drawingObjects.add(gas40);
        drawingObjects.add(gas41);
        drawingObjects.add(gas42);
        drawingObjects.add(gas43);
        drawingObjects.add(gas44);
        drawingObjects.add(gas45);
        drawingObjects.add(gas46);
        drawingObjects.add(gas47);
        drawingObjects.add(gas48);
        drawingObjects.add(gas49);
        drawingObjects.add(gas50);
        drawingObjects.add(gas51);
        drawingObjects.add(gas52);
        drawingObjects.add(gas53);
        drawingObjects.add(gas54);
        drawingObjects.add(gas55);
        drawingObjects.add(gas56);



        btnQuadTwo.addInteractable(door360_420);
        lvr1000_500.addInteractable(door980_460);
        btn860_160.addInteractable(door80_140);
        btn680_0.addInteractable(door720_140);
        lvr680_160.addInteractable(door1080_640);
        btn500_160.addInteractable(door920_40);
        btn400_0.addInteractable(door1220_120);
        lvr0_535.addInteractable(door380_40);
        lvr0_630.addInteractable(door480_560);
        btn300_450.addInteractable(door980_180);
        lvrCentre.addInteractable(door540_560);

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
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && !gamePaused) {
            //if the time between the last pause is a second, pause
            if (new Date().getTime() - lastPause > 500) {
                gamePaused = true;
                lastPause = new Date().getTime();
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && gamePaused) {
            //if the time between the last pause is a second, unpause
            if (new Date().getTime() - lastPause > 500) {
                gamePaused = false;
                lastPause = new Date().getTime();
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.E) && gamePaused) {
            //go back to the main menu
            MyGdxGame.curLevel = MyGdxGame.levels.Menu;
            MyGdxGame.clear();
        }

        if (!gamePaused) {
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
            //Texture enemyTexture = monster.getAnimation().getKeyFrame(stateTime, true);
            //monster.setImg(enemyTexture);
            monster.pathfind(character1, character2);

            //render the characters, tiles and items
        }

        float distanceToMoveFromCamX = camera.position.x - (float) Gdx.graphics.getWidth() / 2;
        float distanceToMoveFromCamY = camera.position.y - (float) Gdx.graphics.getHeight() / 2;

        int characterLightWidth = 300;
        int characterLightHeight = 300;
        int normalLightWidth = 200;
        int normalLightHeight = 200;

        Gdx.gl.glClearColor(0, 0, 0, 1);
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

        if (deathFlag) {
            MyGdxGame.clear();
        }
        if (exitPlateOne && exitPlateTwo) {
            //move to next level
            MyGdxGame.curLevel = MyGdxGame.levels.Four;
            MyGdxGame.clear();
        }


        frameBuffer.begin();
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //change the blending function
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        //		batch.setProjectionMatrix(camera.combined); //Sets the projection matrix to the camera
        batch.begin();
        //		batch.setProjectionMatrix(camera.combined);
        //render the lights
        //		batch.draw(lightSprite, 0, 0);
        for (LevelObject e : drawingObjects) {
            //if it is not the enemy
            if (!(e instanceof Enemy)) {
                if (e instanceof Item) {
                    batch.draw(((Item) e).getLight(), ((e.getX()) - normalLightWidth / 2 + 10 + distanceToMoveFromCamX), ((e.getY()) - normalLightHeight / 2 + 10 + distanceToMoveFromCamY), normalLightWidth, normalLightHeight);
                } else {
                    batch.draw(darkLight, ((e.getX()) - normalLightWidth / 2 + 10 + distanceToMoveFromCamX), ((e.getY()) - normalLightHeight / 2 + 10 + distanceToMoveFromCamY), normalLightWidth, normalLightHeight);
                }
            }
        }

        //draw lights on each character

        batch.draw(lightSprite, (character1.getX()) - characterLightWidth / 2 + 10 + distanceToMoveFromCamX, (character1.getY()) - characterLightHeight / 2 + 10 + distanceToMoveFromCamY, characterLightWidth, characterLightHeight);
        batch.draw(lightSprite, (character2.getX()) - characterLightWidth / 2 + 10 + distanceToMoveFromCamX, (character2.getY()) - characterLightHeight / 2 + 10 + distanceToMoveFromCamY, characterLightWidth, characterLightHeight);
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

        //if game is paused then draw the pause menu
        if (gamePaused) {
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
     * Method to draw player  objects using our batch
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
     * @param keycode  passed in int value
     * @return false flag
     */

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    /**
     * Override of key up abstract class in super class
     * @param keycode passed in int value
     * @return false flag
     */

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    /**
     * Override of key typed abstract class in super class
     * @param character passed in character value
     * @return false flag
     */

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    /**
     * Override of touch down abstract class in super class
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
     * Override of touch up abstract class in super class
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
     * Override of mouse moved abstract class in super class
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
