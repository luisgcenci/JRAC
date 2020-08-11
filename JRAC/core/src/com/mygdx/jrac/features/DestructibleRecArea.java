package com.mygdx.jrac.features;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class DestructibleRecArea {

    private Rectangle rec;
    public static ArrayList<Rectangle> allDestructibleRecAreas = new ArrayList<Rectangle>();

    public DestructibleRecArea(Rectangle rec){

        this.rec = rec;
        allDestructibleRecAreas.add(rec);
    }
}
