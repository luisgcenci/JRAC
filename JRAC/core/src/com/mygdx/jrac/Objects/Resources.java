package com.mygdx.jrac.Objects;

import com.badlogic.gdx.maps.MapObject;

import java.util.ArrayList;

public class Resources{

    private String name;
    private MapObject object;
    private float life;
    private boolean isAlive;
    public static ArrayList<MapObject> allResources = new ArrayList<>();

    public Resources(String name, MapObject object, boolean isAlive){

        this.name = name;
        this.object = object;
        this.isAlive = isAlive;
        this.life = 100;


        allResources.add(object);
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
        this.life -= damagePower;
        getLife();
    }
}
