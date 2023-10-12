package com.trafficx.game.g3.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.trafficx.game.Main;

public class SliderActor extends Actor {

    private Image image;
    private Rectangle rect;
    public float speed;

    public float width;
    public float height;

    private boolean isLeft = false;
    private boolean isRight = false;

    public SliderActor(Image image, float x, float y, float speed) {
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.speed = speed;

        rect = new Rectangle();

        setBounds(x, y, width, height);
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
        if (isLeft && getX() > 336) {
            moveBy(-speed * delta, 0);
        }
        if (isRight && getX() < 1415) {
            moveBy(speed * delta, 0);
        }
    }

    public Rectangle getRect() {
        rect.x = getX();
        rect.y = getY();
        rect.width = getWidth();
        rect.height = getHeight();
        return rect;
    }

    public void moveLeft(boolean isLeft) {
        this.isLeft = isLeft;
    }

    public void moveRight(boolean isRight) {
        this.isRight = isRight;
    }
}
