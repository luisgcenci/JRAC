package com.mygdx.jrac.features;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.jrac.Objects.Resources;
import com.mygdx.jrac.hero.Heroes;
import com.mygdx.jrac.maps.Maps;

import java.util.ArrayList;

public class ObjectsInteractions {

    private ArrayList<?> interactableObjects;
    private Heroes hero;
    private boolean isDestructing;

    public ObjectsInteractions(ArrayList<?> interactableObjects, Heroes hero) {

        this.interactableObjects = interactableObjects;
        this.hero = hero;

    }


    //DEALS WITH COLLISIONS

    public boolean checkForCollision() {

        //checks if there is any collision
        for (int i = 0; i < this.interactableObjects.size(); i++) {

            if (this.interactableObjects.get(i) instanceof RectangleMapObject) {
                //get ellipses objects to be rendered
                Rectangle rec = ((RectangleMapObject) this.interactableObjects.get(i)).getRectangle();
                if (hero.getHero().overlaps(rec)) {

                    return true;
                }
                ;
            }
        }

        return false;
    }




    //DEALS WITH DESTRUCTION

    public Resources checkForDestruction(Maps map) {
        //checks if there's destruction
        for (int i = 0; i < interactableObjects.size(); i++) {

            Rectangle objAreaOfDestruction = (Rectangle) interactableObjects.get(i);
            Resources resourceBeingAttacked;

            if (hero.getHero().overlaps(objAreaOfDestruction)) {

                //get resource being destroyed
                resourceBeingAttacked = RecRelationships.allRelationships.get(objAreaOfDestruction);

                //attack resource
                resourceBeingAttacked.attackResource(this.hero.getDamagePower());

                //check if resource is destroyed already
                if (resourceBeingAttacked.getLife() > 0) {

                    //do nothing
                    //System.out.println("Getting Resource..." + "(" + resourceBeingAttacked.getLife() + ")");
                } else {

                    //check what type of object should be removed from the foreground
                    checkObjectTypeToBeRemovedFromForeground(map, resourceBeingAttacked);

                    //delete resource and destructible area around it and relationship between them
                    Resources.allResources.remove(resourceBeingAttacked.getObject());
                    DestructibleRecArea.allDestructibleRecAreas.remove(objAreaOfDestruction);
                    RecRelationships.allRelationships.remove(objAreaOfDestruction);

                    System.out.println("Resource Destroyed");
                }


                return resourceBeingAttacked;
            }


        }

        return null;
    }


    private void checkObjectTypeToBeRemovedFromForeground(Maps map, Resources res) {

        String resName = res.getName();
        Rectangle resourceRec;

        if (res.getObject() instanceof RectangleMapObject) {

            //remove cells from foreground
            resourceRec = ((RectangleMapObject) res.getObject()).getRectangle();
            removeRecObjectCellsFromForeground(resourceRec, resName, map);
        }

    }

    private void removeRecObjectCellsFromForeground(Rectangle recObject, String resName, Maps map) {

        //save the layer's name of where the resources are in
        String resLayerName = "";
        if (resName.equals("wood")){
            resLayerName = "trees";
        }

        TiledMapTileLayer resourceLayer = (TiledMapTileLayer) map.getForegroundMapLayers().get(resLayerName);

        int mainCellX = (int) (recObject.x / resourceLayer.getTileWidth());
        int mainCellY = (int) (resourceLayer.getHeight() - (((map.getMAP_HEIGHT() - recObject.y) / resourceLayer.getTileHeight())));

        //Remove Cells From Foreground

        //if trees, follow this algorithm
        if (resLayerName.equals("trees")) {

            //tree height in cells units
            int treeHeightInCells = 9;

            //run until the top of the tree (last y position)
            for (int i = mainCellY; i < mainCellY + treeHeightInCells; i++) {

                //clear middle cells
                resourceLayer.setCell(mainCellX, i, null);

                //clear side cells
                //get the cells around the mainCellY (root/tree trunk) that needs to disappear
                int closeLeftCellX = mainCellX - 1;
                int farLeftCellX = mainCellX - 2;
                int closeRightCellX = mainCellX + 1;
                int farRightCellX = mainCellX + 2;

                int[] xCellsToClear = {closeLeftCellX, farLeftCellX, closeRightCellX, farRightCellX};

                for (int position : xCellsToClear) {
                    //clear side cells
                    resourceLayer.setCell(position, i, null);
                }
            }
        }


    }
}
