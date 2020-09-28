package com.mygdx.jrac.Inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.jrac.Objects.Wood;
import com.mygdx.jrac.hero.Heroes;
import com.mygdx.jrac.screens.GameScreen;

import java.util.ArrayList;

public class HeroInventory {

    private Texture tex;
    private String name;
    private Rectangle inventoryRec;
    private int numberOfItems;

    public static ArrayList<InventoryItem> inventoryItems = new ArrayList<InventoryItem>();

    ArrayList <Integer> woodQyt = new ArrayList<Integer>();

    public HeroInventory(String name , Heroes hero){
        inventoryRec = new Rectangle();

        this.name = name;
        tex = new Texture(Gdx.files.internal("Draws/Screen/hero-inventory.png"));
        this.numberOfItems = 4;

        //set inventory positions accordingly with Hero's Position
        setPositionXAccordinglyWHeroAndScreen(hero);
        setPositionYAccordinglyWHeroAndScreen(hero, GameScreen.SCREEN_HEIGHT);

        inventoryRec.x = this.getPositionX();
        inventoryRec.y = this.getPositionY();
        inventoryRec.width = this.tex.getWidth();
        inventoryRec.height = this.tex.getHeight();

        createItemsRecForInventory();
        updateItemsRecPositions();

    }

    //GETTERS

    public Texture getTex() {
        return tex;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Integer> getWoodQyt() {
        return woodQyt;
    }

    public float getPositionX() {
        return this.inventoryRec.x;
    }

    public float getPositionY() {
        return this.inventoryRec.y;
    }

    public Rectangle getInventoryRec() {
        return inventoryRec;
    }

    //SETTERS



    public void setTex(Texture tex) {
        this.tex = tex;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPositionX(float positionX) {
        this.inventoryRec.x = positionX;
    }

    public void setPositionY(float positionY) {
        this.inventoryRec.y = positionY;
    }

    public void setWoodQyt(ArrayList<Integer> woodQyt) {
        this.woodQyt = woodQyt;
    }

    public void setPositionXAccordinglyWHeroAndScreen(Heroes hero){

        this.inventoryRec.x = hero.getHeroPositionX() -  (float) this.tex.getWidth() / 2;
        updateItemsRecPositions();
    }

    public void setPositionYAccordinglyWHeroAndScreen(Heroes hero, int screenHeight){

        this.inventoryRec.y = hero.getHeroPositionY() - ( (float) (screenHeight / 2) - 10);
        updateItemsRecPositions();
    }



    //OTHERS

    public boolean addResourceToInventory(String nameOfResource){
        boolean itemAdded = false;

        for (InventoryItem item: inventoryItems){


            if (!item.getIsEmpty()){
                //do nothing
            }

            else {
                if (nameOfResource.equals("wood")){
                    woodQyt.add(1);
                    item.setTex(Gdx.files.internal("Draws/Screen/hero-inventory-wood.png"));
                    item.setIsEmpty(false);
                    itemAdded = true;
                    return itemAdded;
                }
            }
        }

        return itemAdded;
    }

    public void createItemsRecForInventory(){

        InventoryItem item;
        float startingPoint = inventoryRec.x + 5;

        for (int i = 0; i < this.numberOfItems * 45; i+=45){

            item = new InventoryItem(true, startingPoint + i, inventoryRec.y);

            inventoryItems.add(item);
        }
    }


    //ITEMS RECs
    public void updateItemsRecPositions(){

        float spaceBetweenItems = 5;
        int i = 0;
        float startingPoint = inventoryRec.x + spaceBetweenItems;

        //items rectangles
        for (InventoryItem item : inventoryItems){

            item.getItemRec().setX(startingPoint + i);
            item.getItemRec().setY(inventoryRec.y + spaceBetweenItems);
            i+=45;
        }

    }
}
