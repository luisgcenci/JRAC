package com.mygdx.jrac.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class Resources {

    private String name;
    private String state;
    private float positionX, positionY;

    public Resources (String name, String state, float positionX, float positionY){

        this.name = name;
        this.state = state;
        this.positionX = positionX;
        this.positionY = positionY;

    }

    //GETTERS

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    //SETTERS

    public void setName(String name) {
        this.name = name;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }
}
