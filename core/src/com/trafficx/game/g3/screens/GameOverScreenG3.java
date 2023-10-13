package com.trafficx.game.g3.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.trafficx.game.Main;
import com.trafficx.game.menu.GameModeScreen;

public class GameOverScreenG3 extends ScreenAdapter {

    public static final float SCREEN_WIDTH = Main.SCREEN_WIDTH;
    public static final float SCREEN_HEIGHT = Main.SCREEN_HEIGHT;

    //Viewports
    private final Main main;
    private Viewport viewport;
    private Skin skin;
    private Stage stage;


    //Table
    private Image exitButton;
    private Image startButton;
    private Image gameModeButton;


    //Actors


    //Game
    private final int score;
    private final boolean isWin;


    //Levels


    public GameOverScreenG3(Main main, boolean isWin, int score) {
        this.main = main;
        this.isWin = isWin;
        this.score = score;
    }


    public void show() {
        showCameraAndStage();

        skin = new Skin(Gdx.files.internal("g3/skin.json"));

        Table table = new Table();
        table.setFillParent(true);

        Table table1 = new Table();
        table1.setBackground(skin.getDrawable(isWin ? "back_gameover_win" : "back_gameover_lose"));

        exitButton = new Image(skin, "b_exit_button");
        table1.add(exitButton).padLeft(22.0f).padTop(19.0f).expandY().align(Align.topLeft).colspan(2);

        table1.row();
        Label scoreLabel = new Label("" + score, skin, "f2");
        scoreLabel.setAlignment(Align.top);
        table1.add(scoreLabel).padBottom(370.0f).align(Align.bottom).colspan(2);

        table1.row();
        startButton = new Image(skin, "b_start_button");
        table1.add(startButton).padLeft(495.0f).padBottom(71.0f).align(Align.bottomLeft);

        gameModeButton = new Image(skin, "b_game_mode_button");
        table1.add(gameModeButton).padLeft(113.0f).padBottom(71.0f).expandX().align(Align.bottomLeft);
        table.add(table1).minWidth(1920.0f).minHeight(1080.0f).maxWidth(1920.0f).maxHeight(1080.0f);
        stage.addActor(table);

        setClickListeners();

        scoreLabel.setVisible(isWin);
    }

    private void setClickListeners() {
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(new LoadingScreenG3(main));
            }
        });

        gameModeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(new GameModeScreen(main));
            }
        });
    }


    public void render(float delta) {
        renderCamera();
    }

    public void resize(int width, int height) {
        resizeCamera(width, height);
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    /////Camera
    private void showCameraAndStage() {
        viewport = new StretchViewport(SCREEN_WIDTH, SCREEN_HEIGHT);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
    }

    private void renderCamera() {
        ScreenUtils.clear(Color.OLIVE);

        viewport.apply();
        stage.act();
        stage.draw();
    }

    private void resizeCamera(int width, int height) {
        viewport.update(width, height, true);
    }
    ////////
}

