package com.mygdx.game; //gdx package which has classes to run the backend of a game

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * This launches the game by creating the appropriate level/screen
 */


public class MyGdxGame extends ApplicationAdapter { //Application adapter has methods to support the state of the game such as creating, pausing, resuming and rendering
    public static levels curLevel = levels.Four;
    public static BaseLevel currentLevel;

    enum levels{
        Menu,
        One,
        Two,
        Three,
        Four,
    }

    /**
     * This method launches the main menu screen
     * and begins the background music and looping roar
     */

    @Override
    public void create(){

        final Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music.wav"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.015f);
        backgroundMusic.play();

        final Music roar  = Gdx.audio.newMusic(Gdx.files.internal("roar2.wav"));
        roar.setLooping(true);
        roar.setVolume(0.02f);
        roar.play();

        CreateMainMenu();
    }

    /**
     * This method creates a main menu screen by instancing the main menu class
     * and then utilising the Create() method
     */

    public static void CreateMainMenu(){

        currentLevel = new MainMenu();
        currentLevel.Create();
        curLevel = levels.Menu;
    }

    /**
     * This method creates the first level by creating
     * an instance of the LevelOne class and utilising
     * the Create() method
     */
    public static void CreateLevelOne(){

        currentLevel = new LevelOne();
        currentLevel.Create();
        curLevel = levels.One;
    }

    /**
     * This method creates the second level by creating
     * an instance of the LevelTwo class and utilising
     * the Create() method
     */

    public static void CreateLevelTwo(){
        currentLevel = new LevelTwo();
        currentLevel.Create();
        curLevel = levels.Two;
    }

    /**
     * This method creates the three level by creating
     * an instance of the LevelThree class and utilising
     * the Create() method
     */

    public static void CreateLevelThree(){
        currentLevel = new Level3();
        currentLevel.Create();
        curLevel = levels.Three;
    }

    /**
     * This method creates the fourth level by creating
     * an instance of the LevelFour class and utilising
     * the Create() method
     */

    public static void CreateLevelFour(){
        currentLevel = new LevelFour();
        currentLevel.Create();
        curLevel = levels.Four;
    }

    /**
     * Runs the render() method in the current level
     * which is called 60 times per second
     */

    public void render(){
        currentLevel.Render();
    }

    /**
     * Clears the screen and switches/launches appropriate level
     * according to the selected level. Clearing works by clearing the
     * arrayList of objects in the levels
     */

    public static void clear(){
        currentLevel.ClearLevel();
        BaseLevel.deathFlag = false;
        switch(curLevel){
            case Menu:
                CreateMainMenu();
                break;
            case One:
                CreateLevelOne();
                break;
            case Two:
                CreateLevelTwo();
                break;
            case Three:
                CreateLevelThree();
                break;
            case Four:
                CreateLevelFour();
                break;
            default:
                break;
        }
    }

}
