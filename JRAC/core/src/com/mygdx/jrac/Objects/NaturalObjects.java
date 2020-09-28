package com.mygdx.jrac.Objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;

import java.util.ArrayList;

public class NaturalObjects {

    private String name;
    private MapObject object;
    private float life;
    private boolean isAlive;
    private boolean isBeingCarried;
    public static ArrayList<MapObject> allNaturalObjects = new ArrayList<>();

    public NaturalObjects(String name, MapObject object, boolean isAlive){

        this.name = name;
        this.object = object;
        this.isAlive = isAlive;
        this.life = 100;

        allNaturalObjects.add(object);
    }

    public String getName(){
        return this.name;
    }

    public float getLife(){
        if (this.life > 0){

            return this.life;
        }
        else{
            setLife(0);
            setIsAlive(false);
        }

        return this.life;
    }

    public MapObject getObject(){
        return this.object;
    }

    public boolean getIsAlive(){
        return this.isAlive;
    }

    //SETTERS

    public void setLife(float life){
        this.life = life;
    }

    public void setIsAlive(boolean isAlive){
        this.isAlive = isAlive;
    }

    //others

    public void attackResource(float damagePower){
        this.life -= (damagePower+10);
        getLife();
    }

}
