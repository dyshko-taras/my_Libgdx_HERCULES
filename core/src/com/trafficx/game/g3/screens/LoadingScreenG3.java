package com.trafficx.game.g3.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.trafficx.game.Main;
import com.trafficx.game.g1.tools.GameState;

public class LoadingScreenG3 extends ScreenAdapter {

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


    public LoadingScreenG3(Main main) {
        this.main = main;
        GameState.setState(GameState.MENU);
    }


    public void show() {
        showCameraAndStage();

        skin = new Skin(Gdx.files.internal("g3/skin.json"));

        Table table = new Table();
        table.setFillParent(true);

        Table table1 = new Table();
        table1.setBackground(skin.getDrawable("back_ls"));

        exitButton = new Image(skin, "b_exit_button");
        table1.add(exitButton).padLeft(22.0f).padTop(19.0f).align(Align.topLeft);

        table1.row();
        startButton = new Image(skin, "b_start_button");
        table1.add(startButton).padBottom(71.0f).expand().align(Align.bottom);
        table.add(table1).minWidth(1920.0f).minHeight(1080.0f).maxWidth(1920.0f).maxHeight(1080.0f);
        stage.addActor(table);

        setClickListeners();
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
                main.setScreen(new GameScreenG3(main));
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
