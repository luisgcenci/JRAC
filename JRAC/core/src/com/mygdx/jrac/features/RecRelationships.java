package com.mygdx.jrac.features;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.jrac.Objects.NaturalObjects;

import java.util.HashMap;

public class RecRelationships {

    NaturalObjects resource;
    Rectangle destructibleRec;

    //keeps track of all relationships
    public static HashMap<Rectangle, NaturalObjects> allRelationships = new HashMap<Rectangle, NaturalObjects>();


    public RecRelationships(Rectangle destructibleRec, NaturalObjects resource ){

        this.destructibleRec = destructibleRec;
        this.resource = resource;

        allRelationships.put(destructibleRec, resource);

    }
}
