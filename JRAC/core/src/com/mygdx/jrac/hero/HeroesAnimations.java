package com.mygdx.jrac.hero;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public enum HeroesAnimations {

    GREENHERO(1, Gdx.files.internal("guy_running_spritesheetUpdated.png"), 4, 4);

    //hero id
    private int heroId;

    //texture
    private Texture spriteSheetTexture;

    //2d spriteSheet array for texture regions
    private TextureRegion[][] spriteSheetArray;

    //quantity of rows and columns of the spriteSheet
    private int FRAME_ROWS;
    private int FRAME_COLS;

    /*running animation*/

    //animation speed
    private final float ANIMATION_SPEED = 0.1f;

    //movements
    private Animation<TextureRegion> forwardMovement;
    private Animation <TextureRegion> backMovement;
    private Animation <TextureRegion> rightMovement;
    private Animation <TextureRegion> leftMovement;

    //default positions
    private TextureRegion defaultPositionForward;
    private TextureRegion defaultPositionBack;
    private TextureRegion defaultPositionRight;
    private TextureRegion defaultPositionLeft;

    private HeroesAnimations(int id, FileHandle spriteSheetPath, int frameRows, int frameColumns){

        //set up id
        this.heroId = id;
        this.FRAME_ROWS = frameRows;
        this.FRAME_COLS = frameColumns;

        /* set up running animation and frames */
        setRunningAnimation(spriteSheetPath, frameRows, frameColumns);
    }


    /* SETTERS */
    public void setRunningAnimation(FileHandle spriteSheetPath, int frameRows, int frameColumns) {

        //get texture
        this.spriteSheetTexture = new Texture(spriteSheetPath);

        //split the region in a 2d array, where [rows][columns]
        spriteSheetArray = TextureRegion.split(spriteSheetTexture, spriteSheetTexture.getWidth() / frameColumns, spriteSheetTexture.getHeight() / frameRows);


        /* FORWARD MOVEMENTS*/

        //Set up the forwardMovement frames, they are in the first row [1]
        TextureRegion[] forwardMovementFrames = new TextureRegion[frameColumns];
        int indexF = 0;
        for (int i = 0; i < frameColumns; i++){
            forwardMovementFrames[indexF++] = spriteSheetArray[1][i];
        }

        //forward movement animation
        this.forwardMovement = new Animation<TextureRegion>(ANIMATION_SPEED, forwardMovementFrames);

        //default position when hero just moved forward
        this.defaultPositionForward = new TextureRegion(spriteSheetArray[1][0]);

        /*BACK MOVEMENTS*/

        //Set up the backMovement frames, they are in the first row [0]
        TextureRegion[] backMovementFrames = new TextureRegion[frameColumns];
        int indexB = 0;
        for (int i = 0; i < frameColumns; i++){
            backMovementFrames[indexB++] = spriteSheetArray[0][i];
        }

        //back movement animation
        this.backMovement = new Animation<TextureRegion>(ANIMATION_SPEED, backMovementFrames);
        //default position when hero just moved backwards
        this.defaultPositionBack = new TextureRegion(spriteSheetArray[0][0]);



        /*RIGHT MOVEMENTS*/

        //Set up the rightMovement frames, they are in the first row [2]
        TextureRegion[] rightMovementFrames = new TextureRegion[frameColumns];
        int indexR = 0;
        for (int i = 0; i < frameColumns; i++){
            rightMovementFrames[indexR++] = spriteSheetArray[2][i];
        }

        //right movement animation
        this.rightMovement = new Animation<TextureRegion>(ANIMATION_SPEED, rightMovementFrames);
        //default position when hero just moved to the right
        this.defaultPositionRight = new TextureRegion(spriteSheetArray[2][0]);



        /*LEFT AND BACKWARD MOVEMENTS*/

        //Set up the leftMovement animation frames, they are in the second row[3]
        TextureRegion[] leftMovementFrames = new TextureRegion[frameColumns];
        int indexL = 0;
        for (int i = 0; i < frameColumns; i++){
            leftMovementFrames[indexL++] = spriteSheetArray[3][i];
        }

        //left movement animation
        this.leftMovement = new Animation<TextureRegion>(ANIMATION_SPEED, leftMovementFrames);
        //default position when hero just moved to the left
        this.defaultPositionLeft = new TextureRegion(spriteSheetArray[3][3]);


    }

    /* END OF SETTERS*/

    /* GETTERS */

    public Animation<TextureRegion> getForwardMovement() { return forwardMovement; }

    public Animation<TextureRegion> getBackMovement() {
        return backMovement;
    }

    public Animation<TextureRegion> getRightMovement() {
        return rightMovement;
    }

    public Animation<TextureRegion> getLeftMovement() {
        return leftMovement;
    }

    public TextureRegion getDefaultPositionForward() {
        return defaultPositionForward;
    }

    public TextureRegion getDefaultPositionBack() {
        return defaultPositionBack;
    }

    public TextureRegion getDefaultPositionRight() {
        return defaultPositionRight;
    }

    public TextureRegion getDefaultPositionLeft() {
        return defaultPositionLeft;
    }

    /* END OF GETTERS */

    //figure it out a way of call this method from the FirstGame Class
    public void dispose(){
        spriteSheetTexture.dispose();
    }
}
