package com.mygdx.jrac;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.jrac.screens.GameScreen;


public class Jrac extends Game {
	public SpriteBatch batch;
	public BitmapFont font;

	@Override
	public void create () {

		batch = new SpriteBatch();

		//Arial is the default font
		font = new BitmapFont();

		this.setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}
}








