package com.trafficx.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.trafficx.game.g2.screens.GameOverScreenG2;
import com.trafficx.game.g2.screens.LoadingScreenG2;
import com.trafficx.game.g3.screens.GameOverScreenG3;
import com.trafficx.game.g3.screens.GameScreenG3;
import com.trafficx.game.g3.screens.LoadingScreenG3;


public class Main extends Game {

	public static float SCREEN_WIDTH = 1920;
	public static float SCREEN_HEIGHT = 1080;

	public SpriteBatch batch;

	public void create() {
		initAssets();
		setSettings();
		this.setScreen(new LoadingScreenG2(this));
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
