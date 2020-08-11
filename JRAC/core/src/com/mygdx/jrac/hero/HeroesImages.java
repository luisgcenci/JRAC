package com.mygdx.jrac.hero;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

public enum HeroesImages {
    GREENHERO(1, Gdx.files.internal("biggerguy.png"));

    private int heroId;
    private Texture texture;

    private HeroesImages(int heroID, FileHandle img) {

        this.heroId = heroID;
        this.texture = new Texture(img);
    }

    /* SETTERS */


    /* GETTERS */

    public Texture getTexture() {
        return texture;
    }

}
