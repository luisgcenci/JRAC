package com.mygdx.jrac.features;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.jrac.Objects.Resources;

import java.util.HashMap;

public class RecRelationships {

    Resources resource;
    Rectangle destructibleRec;

    //keeps track of all relationships
    public static HashMap<Rectangle, Resources> allRelationships = new HashMap<Rectangle, Resources>();


    public RecRelationships(Rectangle destructibleRec, Resources resource ){

        this.destructibleRec = destructibleRec;
        this.resource = resource;

        allRelationships.put(destructibleRec, resource);

    }
}
