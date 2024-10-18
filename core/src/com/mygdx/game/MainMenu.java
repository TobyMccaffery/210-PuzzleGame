package com.mygdx.game;

import com.badlogic.gdx.InputProcessor;
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
import com.badlogic.gdx.utils.compression.lzma.Base;
//badlogic package has classes to support graphical display of a game

import java.awt.*; //To support Java GUI
import java.util.ArrayList; //To support use of ArrayLists

/**
 * This class represents the menu screen. It is responsible for placing
 * all objects within the level at the beginning and changing their position
 * throughout
 */

public class MainMenu extends BaseLevel implements InputProcessor {

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

    private Music walk;

    //Our two characters/players
    Character character1;
    Character character2;

    Button levelOne;
    Button levelTwo;
    Button levelThree;
    Button levelFour;
    Button exit;

    Texture menuBG;
    Texture congrats;

    public static boolean won = false;

    TileMap curLevel;
    float stateTime;

    boolean keyPressed1 = false;
    boolean keyPressed2 = false;

    private int defaultWidth = 20;
    private int defaultHeight = 20;
    private double scale;
    private OrthographicCamera hudCamera;

    private TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    private int[] background = new int[] {0,1,2};
    private int[] foreground = new int[] {3,4};

    /**
     * Constructor for level
     * Sets the current level to this instanced object
     */

    public MainMenu(){
        MyGdxGame.curLevel = MyGdxGame.levels.Menu;
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
        tiledMap = new TmxMapLoader().load("Maps/MainMenu.tmx");
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

        character1 = new Character((character1img), (int)w/2 - 100, (int)h/2 + 40, '1', (TiledMapTileLayer) tiledMap.getLayers().get(2));
        character2 = new Character(character2img, (int)w/2 + 100, (int)h/2 + 40, '2', (TiledMapTileLayer) tiledMap.getLayers().get(2));

        levelOne = new Button(280, 280, Textures.findRegion("ButtonNotPressed"));
        levelTwo = new Button(520, 280, Textures.findRegion("ButtonNotPressed"));
        levelThree = new Button(760, 280, Textures.findRegion("ButtonNotPressed"));
        levelFour = new Button(1000, 280, Textures.findRegion("ButtonNotPressed"));
        exit = new Button(180, 520, Textures.findRegion("ButtonNotPressed"));

        menuBG = new Texture("menuBG.png");
        congrats = new Texture("congrats.png");

        walk = Gdx.audio.newMusic(Gdx.files.internal("walk.wav"));
        walk.setVolume(0.004f);

        levelOne.setLevel(MyGdxGame.levels.One);
        levelTwo.setLevel(MyGdxGame.levels.Two);
        levelThree.setLevel(MyGdxGame.levels.Three);
        levelFour.setLevel(MyGdxGame.levels.Four);

        levelOne.setImg(Textures.findRegion("SpiralFloor"));
        levelTwo.setImg(Textures.findRegion("SpiralFloor"));
        levelThree.setImg(Textures.findRegion("SpiralFloor"));
        levelFour.setImg(Textures.findRegion("SpiralFloor"));
        exit.setImg(Textures.findRegion("SpiralFloor"));

        exit.makeQuitButton();

        //Characters are added to the list of level objects
        levelObjects.add(character1);
        levelObjects.add(character2);
        levelObjects.add(levelOne);
        levelObjects.add(levelTwo);
        levelObjects.add(levelThree);
        levelObjects.add(levelFour);
        levelObjects.add(exit);

        drawingObjects.add(levelOne);
        drawingObjects.add(levelTwo);
        drawingObjects.add(levelThree);
        drawingObjects.add(levelFour);
        drawingObjects.add(exit);



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

        lightSprite = new Texture("light2.png");
        //font = new BitmapFont();
        darkLight = new Texture("light4.png");

        hudCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        hudCamera.position.set(hudCamera.viewportWidth / 2.0f, hudCamera.viewportHeight / 2.0f, 1.0f);
    }

    /**
     * Method to move objects or otherwise change the screen
     * according to actions of the player and if the move is legal
     */

    @Override
    public void Render(){
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

        //render the characters, tiles and items

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
        batch.draw(menuBG, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if(won){
            batch.draw(congrats, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        //		//set the batch to draw darker
        //batch.setColor(0.2f, 0.2f, 0.2f, 1f);
        //batch.setColor(0.2f, 0.2f, 0.2f, 1f);
        //drawTiles();
//        tiledMapRenderer.render(background);
//        drawGas();
        drawEntities();
        drawPlayers();
//        tiledMapRenderer.render(foreground);
        batch.end();


        frameBuffer.begin();
        Gdx.gl.glClearColor(1f,1f,1f,1);
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
                batch.draw(darkLight, ((e.getX()) - normalLightWidth/2 + 10 + distanceToMoveFromCamX) , ((e.getY()) - normalLightHeight/2 + 10 + distanceToMoveFromCamY) , normalLightWidth, normalLightHeight);
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
        renderUI(batch);
        batch.end();
        if(deathFlag){
            MyGdxGame.clear();
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
     * Override of touch up  abstract class in super class
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
