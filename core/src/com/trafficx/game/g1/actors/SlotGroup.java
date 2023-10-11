package com.trafficx.game.g1.actors;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.trafficx.game.Main;

public class SlotGroup extends Group {

    private final float SCREEN_WIDTH = Main.SCREEN_WIDTH;
    private final float SCREEN_HEIGHT = Main.SCREEN_HEIGHT;

    private Array<Image> slotImages = new Array<Image>();
    private Array<Image> allImages = new Array<Image>();

    private Viewport viewportSlot;
    private Stage stageSlot;

    private int countX;
    private int countY;
    private float widthE;
    private float heightE;
    private float slotWidth;
    private float slotHeight;
    private float slotX;
    private float slotY;
    private float dx;
    private float dy;

    float countYInvisible;
    float slotHeightInvisible;

    public boolean isEndSpin = false;


    public SlotGroup(float slotX, float slotY,float widthE, float heightE, float dx,float dy,int countX, int countY, Array<Image> allImages) {

        slotWidth = widthE * countX + dx * (countX - 1);
        slotHeight = heightE * countY + dy * (countY - 1);

        show(slotX, slotY, slotWidth, slotHeight);

        this.slotX = slotX;
        this.slotY = slotY;
        this.widthE = widthE;
        this.heightE = heightE;
        this.dx = dx;
        this.dy = dy;
        this.countX = countX;
        this.countY = countY;
        this.allImages.addAll(allImages);



        countYInvisible = countY * 4;
        slotHeightInvisible = heightE * countYInvisible + dy * (countYInvisible - 1);


        for (int i = 0; i < countX * countYInvisible; i++) {
            Image image = new Image(allImages.get(0).getDrawable());
            slotImages.add(image);
            addActor(image);
        }

        setBounds(0, 0, slotWidth, slotHeightInvisible);
        setPositionE();
    }

    /////Camera
    private void show(float x, float y, float width, float height) {
        viewportSlot = new StretchViewport(width, height);
        viewportSlot.setScreenPosition((int) x, (int) y);
        stageSlot = new Stage(viewportSlot);
        stageSlot.addActor(this);
    }

    public void render() {
        viewportSlot.apply();
        stageSlot.act();
        stageSlot.draw();
    }

    public void resize(int width, int height) {
        viewportSlot.update((int) (slotWidth * (width / SCREEN_WIDTH)), (int) (slotHeight * (height / SCREEN_HEIGHT)), true);
        viewportSlot.setScreenPosition((int) (slotX * (width / SCREEN_WIDTH)), (int) (slotY * (height / SCREEN_HEIGHT)));
    }

    public void dispose() {
        stageSlot.dispose();
    }


    private void setPositionE() {
        setImages();
        for (int j = 0; j < countYInvisible; j++) {
            for (int i = 0; i < countX; i++) {
                float posX = i * (widthE + dx) + (widthE - slotImages.get(j * countX + i).getWidth()) / 2;
                float posY = j * (heightE + dy) + (heightE - slotImages.get(j * countX + i).getHeight()) / 2;
                slotImages.get(j * countX + i).setPosition(posX, posY);
            }
        }
    }

    private void setImages() {
        for (int i = 0; i < slotImages.size; i++) {
            slotImages.get(i).setDrawable(allImages.get(MathUtils.random(0, allImages.size - 1)).getDrawable());
        }
    }

    public void spin(float speed) {
        isEndSpin = false;
        float distance = slotHeightInvisible - slotHeight;
        float duration = distance / speed;
        setPosition(0, 0);
        setPositionE();
        Action action = Actions.sequence(Actions.moveBy(0, -distance, duration), new Action() {
            @Override
            public boolean act(float delta) {
                isEndSpin = true;
                System.out.println("end");
                return true;
            }
        });
        addAction(action);
    }
}
