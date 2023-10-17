package com.trafficx.game.menu.screens;

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
import com.trafficx.game.g1.screens.LoadingScreenG1;
import com.trafficx.game.g2.screens.LoadingScreenG2;
import com.trafficx.game.g3.screens.LoadingScreenG3;

public class GameModeScreen extends ScreenAdapter {

    public static final float SCREEN_WIDTH = Main.SCREEN_WIDTH;
    public static final float SCREEN_HEIGHT = Main.SCREEN_HEIGHT;

    //Viewports
    private final Main main;
    private Viewport viewport;
    private Skin skin;
    private Stage stage;


    //Table
    private Image exitButton;
    private Image leftButton;
    private Image rightButton;
    private Image startButton;


    //Actors
    private Image chosen_object;

    private int chosen_object_index = 0;


    public GameModeScreen(Main main) {
        this.main = main;
    }


    public void show() {
        showCameraAndStage();

        skin = new Skin(Gdx.files.internal("menu/skin.json"));

        Table table = new Table();
        table.setFillParent(true);

        Table table1 = new Table();
        table1.setBackground(skin.getDrawable("back_game_mode"));

        exitButton = new Image(skin, "b_exit_button");
        table1.add(exitButton).padLeft(22.0f).padTop(19.0f).align(Align.topLeft);

        table1.row();
        leftButton = new Image(skin, "b_left_button copy");
        table1.add(leftButton).padLeft(63.0f).padTop(334.0f).align(Align.topLeft);

        rightButton = new Image(skin, "b_right_button copy");
        table1.add(rightButton).padRight(63.0f).padTop(334.0f).align(Align.topRight);

        table1.row();
        startButton = new Image(skin, "b_start_button");
        table1.add(startButton).padBottom(71.0f).expand().align(Align.bottom).colspan(2);
        table.add(table1).minWidth(1920.0f).minHeight(1080.0f).maxWidth(1920.0f).maxHeight(1080.0f);
        stage.addActor(table);


        addMyActors();
        addActorsInStage();
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
                if (chosen_object_index == 0) {
                    main.setScreen(new LoadingScreenG1(main));
                } else if (chosen_object_index == 1) {
                    main.setScreen(new LoadingScreenG2(main));
                } else if (chosen_object_index == 2) {
                    main.setScreen(new LoadingScreenG3(main));
                }
            }
        });

        leftButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                chosen_object_index--;
                if (chosen_object_index < 0) {
                    chosen_object_index = 0;
                }
                if (chosen_object_index == 0) {
                    chosen_object.setPosition(540, 214);
                } else if (chosen_object_index == 1) {
                    chosen_object.setPosition(915, 351);
                }
            }
        });

        rightButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                chosen_object_index++;
                if (chosen_object_index > 2) {
                    chosen_object_index = 2;
                }
                if (chosen_object_index == 1) {
                    chosen_object.setPosition(915, 351);
                } else if (chosen_object_index == 2) {
                    chosen_object.setPosition(1297, 214);
                }
            }
        });

    }

        public void render ( float delta){
            renderCamera();
            update(delta);
        }

        private void update ( float delta){
            updateData();
        }

        public void resize ( int width, int height){
            resizeCamera(width, height);
        }

        public void dispose () {
            stage.dispose();
            skin.dispose();
        }

        /////Camera
        private void showCameraAndStage () {
            viewport = new StretchViewport(SCREEN_WIDTH, SCREEN_HEIGHT);
            stage = new Stage(viewport);
            Gdx.input.setInputProcessor(stage);
        }

        private void renderCamera () {
            ScreenUtils.clear(Color.OLIVE);

            viewport.apply();
            stage.act();
            stage.draw();
        }

        private void resizeCamera ( int width, int height){
            viewport.update(width, height, true);
        }
        ////////

        private void addMyActors () {
            chosen_object = new Image(skin, "o_chosen_object");
            chosen_object.setPosition(540, 214);
        }

        private void addActorsInStage () {
            stage.addActor(chosen_object);
        }

        private void updateData () {
        }
    }
