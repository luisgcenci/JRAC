package com.mygdx.jrac.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public enum ResourcesAnimations {

    TREEDEXPLOSION(Gdx.files.internal("tree-destruction-animation.png"), 1, 7);

    private Texture spriteSheetTex;

    int frameRows;
    int columnRows;


    private TextureRegion[][] spriteSheetArray;
    private TextureRegion [] animationTexReg;
    private Animation<TextureRegion> animation;

    private final float ANIMATION_SPEED = 0.1f;

    private ResourcesAnimations(FileHandle spriteSheet, int frameRows, int frameColumns){

        this.frameRows = frameRows;
        this.columnRows = frameColumns;

        setAnimation(spriteSheet, frameRows, columnRows);
    }

    private void setAnimation(FileHandle spriteSheet, int frameRows, int frameColumns){

        this.spriteSheetTex = new Texture(spriteSheet);

        spriteSheetArray = TextureRegion.split(spriteSheetTex, spriteSheetTex.getWidth() / frameColumns, spriteSheetTex.getHeight() / frameRows);

        //set animation
        animationTexReg = new TextureRegion[frameColumns];

        for (int i = 0; i < frameColumns; i++){
            animationTexReg[i] = spriteSheetArray[0][i];
        }
        this.animation = new Animation<TextureRegion>(ANIMATION_SPEED, animationTexReg);

    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }

}
