package com.mygdx.jrac.features;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.jrac.Objects.NaturalObjects;
import com.mygdx.jrac.Objects.Resources;
import com.mygdx.jrac.Objects.ResourcesAnimations;
import com.mygdx.jrac.Objects.Wood;
import com.mygdx.jrac.hero.Heroes;
import com.mygdx.jrac.maps.Maps;

import java.util.ArrayList;
import java.util.Random;

public class ObjectsInteractions {

    private ArrayList<?> interactableObjects;
    private Heroes hero;
    SpriteBatch batch;

    //Used to generate random numbers
    Random random = new Random();

    public ObjectsInteractions(ArrayList<?> interactableObjects, Heroes hero) {

        this.interactableObjects = interactableObjects;
        this.hero = hero;

    }

    public ObjectsInteractions(ArrayList<?> interactableObjects, Heroes hero, SpriteBatch batch) {

        this.interactableObjects = interactableObjects;
        this.hero = hero;
        this.batch = batch;

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

            }
        }

        return false;
    }




    //DEALS WITH DESTRUCTION

    public NaturalObjects checkForDestruction(Maps map) {
        //checks if there's destruction
        for (int i = 0; i < interactableObjects.size(); i++) {

            Rectangle objAreaOfDestruction = (Rectangle) interactableObjects.get(i);
            NaturalObjects naturalObjectBeingAttacked;

            if (hero.getHero().overlaps(objAreaOfDestruction)) {

                //get resource being destroyed
                naturalObjectBeingAttacked = RecRelationships.allRelationships.get(objAreaOfDestruction);

                //attack resource
                naturalObjectBeingAttacked.attackResource(this.hero.getDamagePower());

                //check if resource is destroyed already
                if (naturalObjectBeingAttacked.getLife() > 0) {

                    //do nothing
                    //System.out.println("Getting Resource..." + "(" + naturalObjectBeingAttacked.getLife() + ")");
                } else {

                    //save info about NaturalObject that resources will drop from
                    String resName = naturalObjectBeingAttacked.getName();

                    //this is actually positions of the area of destruction around the NaturalObject but they are very close, so it's all good
                    float positionX = objAreaOfDestruction.x;
                    float positionY = objAreaOfDestruction.y;

                    //check what type of object should be removed from the foreground
                    checkObjectTypeToBeRemovedFromForeground(map, naturalObjectBeingAttacked);

                    //delete resource and destructible area around it and relationship between them
                    NaturalObjects.allNaturalObjects.remove(naturalObjectBeingAttacked.getObject());
                    DestructibleRecArea.allDestructibleRecAreas.remove(objAreaOfDestruction);
                    RecRelationships.allRelationships.remove(objAreaOfDestruction);

                    System.out.println("Resource Destroyed");

                    //Save Resource Name

                    //Now that the Natural Object is destroyed, create resource object base on positionX and position Y of old tree-trunk object


                    createNewResourceObject(resName, positionX, positionY);

                }


                return naturalObjectBeingAttacked;
            }


        }

        return null;
    }


    private void checkObjectTypeToBeRemovedFromForeground(Maps map, NaturalObjects res) {

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

    private void createNewResourceObject(String resourceName, float positionX, float positionY){


        if (resourceName.equals("wood")){
            Wood newWood = new Wood(resourceName, "being-dropped", positionX, positionY);
        }
    }




    //DEALS WITH PICKING UP RESOURCES LAYING AROUND
    public void checkForResourcesPickUp(){

        ArrayList<Wood> toRemove = new ArrayList<>();
        String nameOfResourcePickedUp = "";
        boolean resourcePickedUp = false;

        //go through all the wood possibly laying around
        for (Wood wood : Wood.allWood){

            if (this.hero.getHero().overlaps(wood.getWoodRec()) && wood.getState().equals("dropped")){
                resourcePickedUp = this.hero.getHeroInventory().addResourceToInventory(wood.getName());
                if (resourcePickedUp){
                    toRemove.add(wood);
                }

            }
        }

        Wood.allWood.removeAll(toRemove);

    }
}
