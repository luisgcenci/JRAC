package com.mygdx.jrac.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class Wood extends Resources{

    private Texture tex = new Texture (Gdx.files.internal("Draws/Resources/wood.png"));
    private int texWidth, texHeight;
    private Rectangle woodRec;
    public static ArrayList<Wood> allWood = new ArrayList<Wood>();

    public Wood(String name, String state, float positionX, float positionY) {
        super(name, state, positionX, positionY);
        this.texWidth = tex.getWidth();
        this.texHeight = tex.getHeight();
        this.woodRec = new Rectangle (super.getPositionX(),super.getPositionY(), this.texWidth, this.texHeight);
        allWood.add(this);
    }

    //GETTERS

    public Rectangle getWoodRec() {
        return woodRec;
    }

    public Texture getTex() {
        return tex;
    }

    public int getTexWidth() {
        return texWidth;
    }

    public int getTexHeight() {
        return texHeight;
    }

    public static ArrayList<Wood> getAllWood() {
        return allWood;
    }

}
