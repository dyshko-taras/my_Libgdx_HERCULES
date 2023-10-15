package com.trafficx.game.g2.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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

    private boolean isLeft = false;
    private boolean isRight = false;
    private boolean isDown = false;

    public ItemActor(Image image, float speed) {
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.speed = speed;
        rect = new Rectangle();

        setBounds(MathUtils.random(0, 1237 - image.getWidth()), 749, width, height);
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

    /// звичайний рух вниз
    @Override
    public void act(float delta) {
        super.act(delta);
        moveBy(0, -speed * delta);

        if (isLeft && getX() > 0) {
            moveBy(-speed * 10 * delta, 0);
        }
        if (isRight && getX() + getWidth() < 1237) {
            moveBy(speed * 10 * delta, 0);
        }

        if (isDown) {
            moveBy(0, -speed * 5 * delta);
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

    public void moveDown(boolean isDown) {
        this.isDown = isDown;
    }
}

