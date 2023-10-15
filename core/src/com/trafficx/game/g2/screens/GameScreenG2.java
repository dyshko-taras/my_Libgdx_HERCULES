package com.trafficx.game.g2.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.trafficx.game.Main;
import com.trafficx.game.g2.actors.ItemActor;

public class GameScreenG2 extends ScreenAdapter {

    public static final float SCREEN_WIDTH = Main.SCREEN_WIDTH;
    public static final float SCREEN_HEIGHT = Main.SCREEN_HEIGHT;

    //Viewports
    private final Main main;
    private Viewport viewport;
    private Skin skin;
    private Stage stage;

    //Frame
    private Viewport viewportFrame;
    private Stage stageFrame;
    private final int frameX = 340;
    private final int frameY = 158;
    private final float frameWidth = 1237;
    private final float frameHeight = 749;


    //Table
    private Image exitButton;
    private Image leftButton;
    private Image rightButton;
    private Image downButton;
    private Label scoreLabel;
    private Label timeLeftLabel;

    //


    //Actors
    private final Array<ItemActor> items = new Array<>();
    private final Group itemsGroup = new Group();


    //Game
    private int score = 0;
    private int timeLeft = 60;
    private final Timer timerTimeGame = new Timer();
    private final Timer timerSpawnItems = new Timer();


    //Levels


    public GameScreenG2(Main main) {
        this.main = main;
    }


    public void show() {
        showCameraAndStage();

        skin = new Skin(Gdx.files.internal("g2/skin.json"));

        Table table = new Table();
        table.setFillParent(true);

        table.add();
        stage.addActor(table);

        table = new Table();
        table.setFillParent(true);

        Table table1 = new Table();
        table1.setBackground(skin.getDrawable("back_game"));

        exitButton = new Image(skin, "b_exit_button");
        table1.add(exitButton).padLeft(22.0f).padTop(19.0f).align(Align.topLeft);

        table1.row();
        leftButton = new Image(skin, "b_left_button");
        table1.add(leftButton).padLeft(63.0f).padTop(334.0f).expandY().align(Align.topLeft);

        table1.add();

        table1.add();

        table1.add();

        rightButton = new Image(skin, "b_right_button");
        table1.add(rightButton).padRight(63.0f).padTop(334.0f).align(Align.topLeft);

        table1.row();
        table1.add();

        timeLeftLabel = new Label("00:" + timeLeft, skin);
        timeLeftLabel.setAlignment(Align.center);
        table1.add(timeLeftLabel).padLeft(306.0f).padBottom(51.0f).align(Align.bottomLeft).minWidth(256.0f).minHeight(87.0f).maxWidth(256.0f).maxHeight(87.0f);

        downButton = new Image(skin, "b_down_button");
        table1.add(downButton).padLeft(109.0f).padBottom(22.0f).align(Align.bottomLeft);

        scoreLabel = new Label("0", skin);
        scoreLabel.setAlignment(Align.center);
        table1.add(scoreLabel).padLeft(96.0f).padBottom(51.0f).expandX().align(Align.bottomLeft).minWidth(256.0f).minHeight(87.0f).maxWidth(256.0f).maxHeight(87.0f);

        table1.add();
        table.add(table1).minWidth(1920.0f).minHeight(1080.0f).maxWidth(1920.0f).maxHeight(1080.0f);
        stage.addActor(table);


        addMyActors();
        addActorsInStage();
        setClickListeners();
        setGameTime();
        spawnItem();
    }

    private void setClickListeners() {
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        leftButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (items.size > 0) {
                    items.get(0).moveLeft(true);
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (items.size > 0) {
                    items.get(0).moveLeft(false);
                }
            }
        });

        rightButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (items.size > 0) {
                    items.get(0).moveRight(true);
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (items.size > 0) {
                    items.get(0).moveRight(false);
                }
            }
        });

        downButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (items.size > 0) {
                    items.get(0).moveDown(true);
                }
            }
        });
    }


    public void render(float delta) {
        renderCamera();
        update();
    }

    private void update() {
        checkGameOver();
        removeItem();
    }

    public void resize(int width, int height) {
        resizeCamera(width, height);
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
        stageFrame.dispose();
    }

    /////Camera
    private void showCameraAndStage() {
        viewport = new StretchViewport(SCREEN_WIDTH, SCREEN_HEIGHT);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        viewportFrame = new StretchViewport(frameWidth, frameHeight);
        viewportFrame.setScreenPosition(frameX, frameY);
        stageFrame = new Stage(viewportFrame);
    }

    private void renderCamera() {
        ScreenUtils.clear(Color.OLIVE);

        viewport.apply();
        stage.act();
        stage.draw();

        viewportFrame.apply();
        stageFrame.act();
        stageFrame.draw();
    }

    private void resizeCamera(int width, int height) {
        viewport.update(width, height, true);
        viewportFrame.update((int) (frameWidth * (width / SCREEN_WIDTH)),
                (int) (frameHeight * (height / SCREEN_HEIGHT)),
                true);
        viewportFrame.setScreenPosition((int) (frameX * (width / SCREEN_WIDTH)),
                (int) (frameY * (height / SCREEN_HEIGHT)));
        System.out.println(width / SCREEN_WIDTH);
    }
    ////////

    private void addMyActors() {
        itemsGroup.setBounds(0, 0, frameWidth, frameHeight);
    }

    private void addActorsInStage() {
        stageFrame.addActor(itemsGroup);
    }

    private void setGameTime() {
        timerTimeGame.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                timeLeftLabel.setText(String.format("00:%02d", timeLeft));
                timeLeft--;
            }
        }, 0, 1);
    }

    private void spawnItem() {
        timerSpawnItems.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                int randomImageItem = MathUtils.random(0, 9);
                Image image = new Image(skin, "" + randomImageItem);
                image.setName(randomImageItem <= 4 ? "left" : "right");
                ItemActor item = new ItemActor(image, 80);
                items.add(item);
                itemsGroup.addActor(item);
            }
        }, 0, 3);
    }

    private void removeItem() {
        if (items.size > 0 && items.get(0).getY() < 158) {
            items.get(0).moveDown(false);
            addScore(items.get(0));
            itemsGroup.removeActor(items.get(0));
            items.removeIndex(0);
        }
    }

    private void addScore(ItemActor item) {
        System.out.println(item.getName() + " " + (item.getX() + item.getWidth()/2));
        if (item.getX() + item.getWidth()/2 <= frameWidth/2 && item.getName().equals("left")) {
            score++;
            scoreLabel.setText(score);
        } else if (item.getX() + item.getWidth()/2 >= frameWidth/2 && item.getName().equals("right")) {
            score++;
            scoreLabel.setText(score);
        } else {
            if (score > 0) {
                score--;
                scoreLabel.setText(score);
            }
        }
    }

    private void checkGameOver() {
        if (timeLeft < 0) {
            timerTimeGame.clear();
            main.setScreen(new GameOverScreenG2(main, score));
        }
    }
}

