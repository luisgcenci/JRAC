package com.mygdx.jrac.musics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public enum GameMusics {


    GAMESCREENMUSIC(Gdx.audio.newMusic(Gdx.files.internal("mainMusic.mp3")));

    private Music music;

    private GameMusics(Music music){

        this.music = music;

    }

    public Music getMusic() {
        return music;
    }
}

