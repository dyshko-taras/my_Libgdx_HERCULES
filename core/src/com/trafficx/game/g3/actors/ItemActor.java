package com.trafficx.game.g3.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.trafficx.game.Main;

public class ItemActor extends Actor {

    private Image image;
    private Rectangle rect;
    public float speed;

    public float width;
    public float height;

    public ItemActor(Image image, float speed) {
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.speed = speed;

        rect = new Rectangle();

        setBounds(MathUtils.random(340, Main.SCREEN_WIDTH - image.getWidth()-340), 920 - image.getHeight(), width, height);

        this.setName(image.getName());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        image.setBounds(getX(), getY(), getWidth(), getHeight());
        image.setOrigin(getOriginX(), getOriginY());
        image.setRotation(getRotation());
        image.setScale(getScaleX(), getScaleY());
        image.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        moveBy(0, -speed * delta);
    }

    public Rectangle getRect() {
        rect.x = getX();
        rect.y = getY();
        rect.width = getWidth();
        rect.height = getHeight();
        return rect;
    }
}

