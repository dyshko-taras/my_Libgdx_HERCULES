package com.trafficx.game.g1.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
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
import com.trafficx.game.g1.actors.SlotGroup;
import com.trafficx.game.g1.tools.GameState;
import com.trafficx.game.g1.utils.LabelN;

public class GameScreenG1 extends ScreenAdapter {

    public static final float SCREEN_WIDTH = Main.SCREEN_WIDTH;
    public static final float SCREEN_HEIGHT = Main.SCREEN_HEIGHT;

    //Viewports
    private final Main main;
    private Viewport viewport;
    private Skin skin;
    private Stage stage;


    //Table
    private Image exitButton;
    private Label balanceLabel;
    private Label betLabel;
    private Image minusButton;
    private Image plusButton;
    private Image maxBetButton;
    private Image spinButton;
    private Image autoSpinButton;
    private Label winLabel;
    private Label freeSpinLabel;


    //Actors
    private SlotGroup slotGroup;


    //Game
    private Array<Image> allImages = new Array<Image>();
    private int timesWon = 0;
    private Timer timerAutoSpin = new Timer();
    private boolean isTimerAutoSpin = false;


    //Levels


    public GameScreenG1(Main main) {
        this.main = main;
        GameState.setState(GameState.WAIT);
    }


    public void show() {
        showCameraAndStage();

        skin = new Skin(Gdx.files.internal("g1/skin.json"));

        Table table = new Table();
        table.setFillParent(true);

        Table table1 = new Table();
        table1.setBackground(skin.getDrawable("back"));

        exitButton = new Image(skin, "b_exit_button");
        table1.add(exitButton).padLeft(22.0f).padTop(19.0f).align(Align.topLeft);

        table1.row();
        balanceLabel = new Label("100", skin, "f25");
        balanceLabel.setAlignment(Align.center);
        table1.add(balanceLabel).padLeft(254.0f).padBottom(26.0f).align(Align.bottomLeft).minWidth(121.0f).minHeight(40.0f).maxWidth(121.0f).maxHeight(40.0f);

        minusButton = new Image(skin, "b_minus_button");
        table1.add(minusButton).padLeft(116.0f).padBottom(30.0f).align(Align.bottomLeft);

        betLabel = new Label("1", skin, "f25");
        betLabel.setAlignment(Align.center);
        table1.add(betLabel).padLeft(3.0f).padBottom(26.0f).align(Align.bottomLeft).minWidth(121.0f).minHeight(40.0f).maxWidth(121.0f).maxHeight(40.0f);

        plusButton = new Image(skin, "b_plus_button");
        table1.add(plusButton).padLeft(3.0f).padBottom(30.0f).align(Align.bottomLeft);

        maxBetButton = new Image(skin, "b_maz_bet_button");
        table1.add(maxBetButton).padLeft(76.0f).padBottom(13.0f).align(Align.bottomLeft);

        spinButton = new Image(skin, "b_spin_button");
        table1.add(spinButton).padLeft(-29.0f).align(Align.bottomLeft);

        autoSpinButton = new Image(skin, "b_autospin_button");
        table1.add(autoSpinButton).padLeft(-29.0f).padBottom(13.0f).align(Align.bottomLeft);

        winLabel = new Label("0", skin, "f25");
        winLabel.setAlignment(Align.center);
        table1.add(winLabel).padLeft(130.0f).padBottom(26.0f).align(Align.bottomLeft).minWidth(121.0f).minHeight(40.0f).maxWidth(121.0f).maxHeight(40.0f);

        freeSpinLabel = new Label("0", skin, "f25");
        freeSpinLabel.setAlignment(Align.center);
        table1.add(freeSpinLabel).padLeft(146.0f).padBottom(26.0f).expand().align(Align.bottomLeft).minWidth(121.0f).minHeight(40.0f).maxWidth(121.0f).maxHeight(40.0f);
        table.add(table1).minWidth(1920.0f).minHeight(1080.0f).maxWidth(1920.0f).maxHeight(1080.0f);
        stage.addActor(table);

        addMyActors();
        addActorsInStage();
        setClickListeners();
        createSlot();
    }

    private void setClickListeners() {
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        maxBetButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (GameState.getState() == GameState.WAIT) {
                    LabelN.setNum(betLabel, Math.min(LabelN.getNum(balanceLabel), 100));
                }
            }
        });

        spinButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (GameState.getState() == GameState.WAIT && isEnough()) {
                    spin();
                }
            }
        });

        autoSpinButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                autoSpin();
            }
        });

        minusButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (GameState.getState() == GameState.WAIT) {
                    LabelN.subtractNum(betLabel, 1);
                }
            }
        });

        plusButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (GameState.getState() == GameState.WAIT) {
                    if (LabelN.getNum(betLabel) < LabelN.getNum(balanceLabel)) {
                        LabelN.addNum(betLabel, 1);
                    }
                }
            }
        });
    }


    public void render(float delta) {
        renderCamera();
        slotGroup.render();
        update(delta);
    }

    private void update(float delta) {
        updateData();
        checkEndSpin();
    }

    public void resize(int width, int height) {
        resizeCamera(width, height);
        slotGroup.resize(width, height);
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
        slotGroup.dispose();
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

    private void createSlot() {
        for (int i = 0; i <= 11; i++) {
            allImages.add(new Image(skin, "s_" + i));
        }
        slotGroup = new SlotGroup(340, 160, 244, 244, 4, 4, 5, 3, allImages);
    }

    private boolean isEnough() {
        return (LabelN.getNum(balanceLabel) >= LabelN.getNum(betLabel)
                && LabelN.getNum(balanceLabel) > 0
                && LabelN.getNum(betLabel) > 0);
    }

    private void addBalance() {
        int win = LabelN.getNum(betLabel) * MathUtils.random(-1, 1);
        winLabel.setText(String.valueOf(win));
        LabelN.addNum(balanceLabel, win);
    }


    private void spin() {
        if (GameState.getState() == GameState.WAIT) {
            slotGroup.spin(2000);
            winLabel.setText("0");
            GameState.setState(GameState.SPINNING);
        }
    }

    private void autoSpin() {
        if (GameState.getState() == GameState.WAIT && isEnough()) {
            GameState.setState(GameState.AUTO_SPIN);
            autoSpinButton.setDrawable(skin.getDrawable("b_stop_autospin_button"));
            timerAutoSpin.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    slotGroup.spin(2000);
                    winLabel.setText("0");
                }
            }, 0.5f, 2f);
        } else {
            stopAutoSpin();
            timerAutoSpin.isEmpty();
        }
    }

    private void stopAutoSpin() {
        if (GameState.getState() == GameState.AUTO_SPIN) {
            GameState.setState(GameState.WAIT);
            autoSpinButton.setDrawable(skin.getDrawable("b_autospin_button"));
            timerAutoSpin.clear();
        }
    }


    private void checkEndSpin() {
        if ((GameState.getState() == GameState.SPINNING || GameState.getState() == GameState.AUTO_SPIN) && slotGroup.isEndSpin) {
            addBalance();
            if (GameState.getState() == GameState.SPINNING) {
                GameState.setState(GameState.WAIT);
            }
            if (GameState.getState() == GameState.AUTO_SPIN && !isEnough()) {
                stopAutoSpin();
            }
            slotGroup.isEndSpin = false;
        }
    }
}

