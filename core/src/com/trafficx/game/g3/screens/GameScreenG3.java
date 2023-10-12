package com.trafficx.game.g3.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.trafficx.game.g3.actors.FireballActor;
import com.trafficx.game.g3.actors.ItemActor;
import com.trafficx.game.g3.actors.SliderActor;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GameScreenG3 extends ScreenAdapter {

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
    private Image strikeButton;
    private Label timeLeftLabel;
    private Label lifeLabel;


    //Actors
    private SliderActor slider;
    private Array<FireballActor> fireballs = new Array<>();
    private Group fireballsGroup = new Group();
    private Array<ItemActor> items = new Array<>();
    private Group itemsGroup = new Group();


    //Game
    private int score = 0;
    private int timeLeft = 61;
    private int life = 3;
    private Timer timerTimeGame = new Timer();
    private Timer timerSpawnItems = new Timer();

    //Levels


    public GameScreenG3(Main main) {
        this.main = main;
    }


    public void show() {
        showCameraAndStage();

        skin = new Skin(Gdx.files.internal("g3/skin.json"));

        Table table = new Table();
        table.setFillParent(true);

        Table table1 = new Table();
        table1.setBackground(skin.getDrawable("back"));

        exitButton = new Image(skin, "b_exit_button");
        table1.add(exitButton).padLeft(22.0f).padTop(19.0f).align(Align.topLeft);

        table1.row();
        leftButton = new Image(skin, "b_left_button");
        table1.add(leftButton).padLeft(66.0f).padTop(334.0f).expandX().align(Align.topLeft);

        table1.add();

        rightButton = new Image(skin, "b_right_button");
        table1.add(rightButton).padRight(66.0f).padTop(334.0f).expandY().align(Align.topRight);

        table1.row();
        timeLeftLabel = new Label("60", skin);
        timeLeftLabel.setAlignment(Align.center);
        table1.add(timeLeftLabel).padLeft(510.0f).padBottom(51.0f).align(Align.bottomLeft).minWidth(256.0f).minHeight(87.0f).maxWidth(256.0f).maxHeight(87.0f);

        lifeLabel = new Label("" + life, skin);
        lifeLabel.setAlignment(Align.center);
        table1.add(lifeLabel).padRight(279.0f).padBottom(51.0f).align(Align.bottomRight).minWidth(256.0f).minHeight(87.0f).maxWidth(256.0f).maxHeight(87.0f);

        strikeButton = new Image(skin, "b_strike_button");
        table1.add(strikeButton).padRight(36.0f).padBottom(43.0f).align(Align.bottomRight);
        table.add(table1).minWidth(1920.0f).minHeight(1080.0f).maxWidth(1920.0f).maxHeight(1080.0f);
        stage.addActor(table);

        addMyActors();
        addActorsInStage();
        setClickListeners();
        setGameTime();
        spawnItem();

//        stage.setDebugAll(true);
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
                slider.moveLeft(true);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                slider.moveLeft(false);
            }
        });

        rightButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                slider.moveRight(true);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                slider.moveRight(false);
            }
        });

        strikeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                spawnFireball();
            }
        });
    }



    public void render(float delta) {
        renderCamera();
        update(delta);
    }

    private void update(float delta) {
        updateData();
        checkGameOver();
//        checkGameOver();
        overlapsFireballAndItem();
        removeFireball();
        removeItem();
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
        System.out.println(timeLeft);
    }

    private void resizeCamera(int width, int height) {
        viewport.update(width, height, true);
    }
    ////////

    private void addMyActors() {
        Image image = new Image(skin, "o_slider_object");
        slider = new SliderActor(image,SCREEN_WIDTH / 2 - image.getWidth()/2, 106,1000);
    }

    private void addActorsInStage() {
        stage.addActor(slider);
        stage.addActor(fireballsGroup);
        stage.addActor(itemsGroup);
    }

    private void updateData() {
    }

    private void setGameTime() {
        timerTimeGame.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                timeLeft--;
                timeLeftLabel.setText(String.valueOf(timeLeft));
            }
        },0,1,60);
    }

    private void spawnFireball() {
        Image image = new Image(skin, "o_fireball_object");
        FireballActor fireball = new FireballActor(image, slider.getX() + slider.getWidth() / 2 - image.getWidth() / 2, slider.getY() + slider.getHeight(), 600);
        fireballs.add(fireball);
        fireballsGroup.addActor(fireball);
    }

    private void spawnItem() {
        timerSpawnItems.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                int randomImageItem = MathUtils.random(1,6);
                Image image = new Image(skin, "s_" + randomImageItem);
                image.setName("s" + randomImageItem);
                ItemActor item = new ItemActor(image, 100);

                items.add(item);
                itemsGroup.addActor(item);
            }
        }, 1f, 2f);
    }

    private void overlapsFireballAndItem() {
        for(Iterator<FireballActor> fireballIt = fireballs.iterator(); fireballIt.hasNext();) {
            FireballActor fireball = fireballIt.next();
            for(Iterator<ItemActor> itemIt = items.iterator(); itemIt.hasNext();) {
                ItemActor item = itemIt.next();
                if (fireball.getRect().overlaps(item.getRect())) {
                    System.out.println("overlaps!!!!!!!!!");
                    if (item.getName().equals("s1") || item.getName().equals("s2")) {
                        life--;
                        lifeLabel.setText(String.valueOf(life));
                    } else {
                        score++;
                    }
                    itemIt.remove();
                    fireballIt.remove();
                    fireballsGroup.removeActor(fireball);
                    itemsGroup.removeActor(item);
                    System.out.println("overlaps!!!!!!!!!");
                }
            }
        }
    }

    private void removeFireball() {
        if (fireballs.size > 0 && fireballs.get(0).getY() + fireballs.get(0).getHeight() > 906) {
            fireballsGroup.removeActor(fireballs.get(0));
            fireballs.removeIndex(0);
        }
    }

    private void removeItem() {
        if (items.size > 0 && items.get(0).getY() < 307) {
            if (!items.get(0).getName().equals("s1") && !items.get(0).getName().equals("s2")) {
                life--;
                lifeLabel.setText(String.valueOf(life));
            }
            itemsGroup.removeActor(items.get(0));
            items.removeIndex(0);
        }
    }


    private void checkGameOver() {
        if (timeLeft < 0 || life == 0) {
            System.out.println("game over" + (timeLeft <= 0) + " " + (life == 0));
            timerTimeGame.clear();
            main.setScreen(new GameOverScreenG3(main, life != 0, score));
        }
    }
}

