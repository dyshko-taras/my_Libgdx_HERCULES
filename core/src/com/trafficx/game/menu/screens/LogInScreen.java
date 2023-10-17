package com.trafficx.game.menu.screens;

import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.ScreenAdapter;
        import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
        import com.badlogic.gdx.scenes.scene2d.ui.Image;
        import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
        import com.badlogic.gdx.scenes.scene2d.ui.Stack;
        import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
        import com.badlogic.gdx.utils.viewport.Viewport;
        import com.trafficx.game.Main;
import com.trafficx.game.menu.tools.GameData;

public class LogInScreen extends ScreenAdapter {

    public static final float SCREEN_WIDTH = Main.SCREEN_WIDTH;
    public static final float SCREEN_HEIGHT = Main.SCREEN_HEIGHT;

    //Viewports
    private final Main main;
    private Viewport viewport;
    private Skin skin;
    private Stage stage;


    //Table
    private Image exitButton;
    private Image logInButton;
    private Image frame0;
    private Image frame1;
    private Image frame2;
    private Label text0;
    private Label text1;
    private Label text2;


    //Actors


    //Game
    private boolean isChosenLogin = false;


    //Levels



    public LogInScreen(Main main) {
        this.main = main;
    }


    public void show() {
        showCameraAndStage();

        skin = new Skin(Gdx.files.internal("menu/skin.json"));

        Table table = new Table();
        table.setFillParent(true);

        Table table1 = new Table();
        table1.setBackground(skin.getDrawable("back_log_in"));

        table1.row();
        exitButton = new Image(skin, "b_exit_button");
        table1.add(exitButton).padLeft(22.0f).padTop(19.0f).expandY().align(Align.topLeft);

        table1.row();
        Stack stack = new Stack();

        frame0 = new Image(skin, "o_frame_object");
        stack.addActor(frame0);

        text0 = new Label(GameData.getName0(), skin);
        text0.setAlignment(Align.center);
        stack.addActor(text0);
        table1.add(stack).padBottom(10.0f).align(Align.bottom).minSize(0.0f).maxSize(0.0f);

        table1.row();
        stack = new Stack();

        frame1 = new Image(skin, "o_frame_object");
        stack.addActor(frame1);

        text1 = new Label(GameData.getName1(), skin);
        text1.setAlignment(Align.center);
        stack.addActor(text1);
        table1.add(stack).padBottom(10.0f).align(Align.bottom).minSize(0.0f).maxSize(0.0f);

        table1.row();
        stack = new Stack();

        frame2 = new Image(skin, "o_frame_object");
        stack.addActor(frame2);

        text2 = new Label(GameData.getName2(), skin);
        text2.setAlignment(Align.center);
        stack.addActor(text2);
        table1.add(stack).padBottom(140.0f).align(Align.bottom).minSize(0.0f).maxSize(0.0f);

        table1.row();
        logInButton = new Image(skin, "b_log_in_button");
        table1.add(logInButton).padBottom(71.0f).expandX().align(Align.bottom);
        table.add(table1).minWidth(1920.0f).minHeight(1080.0f).maxWidth(1920.0f).maxHeight(1080.0f);
        stage.addActor(table);

        frame0.setVisible(false);
        frame1.setVisible(false);
        frame2.setVisible(false);


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

        logInButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (isChosenLogin) {
                    main.setScreen(new GameModeScreen(main));
                }
            }
        });

        text0.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (text0.getText().length() > 0) {
                    frame0.setVisible(true);
                    frame1.setVisible(false);
                    frame2.setVisible(false);
                    isChosenLogin = true;
                }
            }
        });

        text1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (text1.getText().length() > 0) {
                    frame0.setVisible(false);
                    frame1.setVisible(true);
                    frame2.setVisible(false);
                    isChosenLogin = true;
                }
            }
        });

        text2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (text2.getText().length() > 0) {
                    frame0.setVisible(false);
                    frame1.setVisible(false);
                    frame2.setVisible(true);
                    isChosenLogin = true;
                }
            }
        });


    }


    public void render(float delta) {
        renderCamera();
        update(delta);
    }

    private void update(float delta) {
        updateData();
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

    private void addMyActors() {
    }

    private void addActorsInStage() {
    }

    private void updateData() {
    }
}
