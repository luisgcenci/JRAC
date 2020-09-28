package com.mygdx.jrac.Inventory;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class InventoryItem {

    private final int ITEM_WIDTH = 40, ITEM_HEIGHT = 35;
    private Rectangle itemRec;
    private Texture tex;

    private boolean isEmpty;

    public InventoryItem (boolean isEmpty, float positionX, float positionY){

        itemRec = new Rectangle(positionX, positionY, ITEM_WIDTH, ITEM_HEIGHT);
        this.isEmpty = isEmpty;

    }

    //GETTERS

    public Rectangle getItemRec() {
        return itemRec;
    }

    public boolean getIsEmpty() {
        return isEmpty;
    }

    public int getITEM_WIDTH() {
        return ITEM_WIDTH;
    }

    public int getITEM_HEIGHT() {
        return ITEM_HEIGHT;
    }


    public float getPositionX() {
        return getItemRec().x;
    }

    public float getPositionY() {
        return getItemRec().y;
    }

    public Texture getTex(){
        return this.tex;
    }

    //SETTERS


    public void setTex(FileHandle internalPath){
        this.tex = new Texture (internalPath);
    }

    public void setIsEmpty(boolean isEmpty){
        this.isEmpty = isEmpty;
    }
}
