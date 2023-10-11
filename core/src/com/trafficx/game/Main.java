package com.trafficx.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.trafficx.game.g1.screens.LoadingScreenG1;


public class Main extends Game {

	public static float SCREEN_WIDTH = 1920;
	public static float SCREEN_HEIGHT = 1080;

	public SpriteBatch batch;

	public void create() {
		initAssets();
		setSettings();
		this.setScreen(new LoadingScreenG1(this));
	}

	public void render() {
		super.render();
	}

	public void resize(int width, int height) {
		super.resize(width, height);
	}

	public void dispose() {
		batch.dispose();
	}

	public void setSettings() {
//		GameSettings.init();
	}

	public void initAssets() {
		batch = new SpriteBatch();
	}
}
