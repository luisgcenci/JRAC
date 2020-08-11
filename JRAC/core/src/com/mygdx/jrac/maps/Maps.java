package com.mygdx.jrac.maps;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Maps {

    private TiledMap backgroundMap;
    private TiledMap foregroundMap;
    public static int MAP_WIDTH = 3200;
    public static int MAP_HEIGHT = 3200;

    public Maps(){

        this.backgroundMap = new TmxMapLoader().load("Draws/Map/BackgroundMap.tmx");
        this.foregroundMap = new TmxMapLoader().load("Draws/Map/ForegroundMap.tmx");
    }

    //GETTERS
    public TiledMap getBackgroundMap(){

        return this.backgroundMap;
    }

    public TiledMap getForegroundMap(){

        return this.foregroundMap;
    }

    public MapLayers getBackgroundMapLayers(){
        return this.backgroundMap.getLayers();
    }

    public MapLayers getForegroundMapLayers(){
        return this.foregroundMap.getLayers();
    }

    public int getMAP_WIDTH(){

        return this.MAP_WIDTH;
    }

    public int getMAP_HEIGHT(){

        return this.MAP_HEIGHT;
    }
}
