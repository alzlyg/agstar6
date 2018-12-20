package ru.zlygostev.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.zlygostev.base.Ship;
import ru.zlygostev.math.Rect;
import ru.zlygostev.pool.BulletPool;

public class MainShip extends Ship {

    private static final int INVALID_POINTER = -1;

    private Vector2 v0 = new Vector2(0.5f, 0);

    // Для клавиатуры
    private boolean pressedLeft;
    private boolean pressedRight;

    // Для тачскрина
/*    private Rect regionButtonLeft = new Rect();
    private Rect regionButtonRight = new Rect();
    private Rect regionButtonShoot = new Rect();*/

    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    public MainShip(TextureAtlas atlas, BulletPool bulletPool, Sound soundShoot) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        setHeightProportion(0.3f);
        this.bulletPool = bulletPool;
        this.reloadInterval = 2.9f;
        this.soundShoot = soundShoot;

        this.bulletRegion = atlas.findRegion("bulletMainShip");
        this.bulletHeight = 0.01f;
        this.bulletV.set(0, 0.5f);
        this.bulletDamage = 1;
        this.shield = 100;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v,delta);
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }
        if (this.getLeft() < worldBounds.getLeft()) {
            this.setLeft(worldBounds.getLeft());
            stop();
        }
        if (this.getRight() > worldBounds.getRight()) {
            this.setRight(worldBounds.getRight());
            stop();
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setBottom(worldBounds.getBottom() + 0.15f);
/*        regionButtonLeft.setSize(worldBounds.getWidth()/3, worldBounds.getHeight());
        regionButtonLeft.setLeft(worldBounds.getLeft());
        regionButtonLeft.setBottom(worldBounds.getBottom());
        regionButtonRight.setSize(worldBounds.getWidth()/3, worldBounds.getHeight());
        regionButtonRight.setRight(worldBounds.getRight());
        regionButtonRight.setBottom(worldBounds.getBottom());
        regionButtonShoot.setSize(worldBounds.getWidth()/3, worldBounds.getHeight());
        regionButtonShoot.setLeft(worldBounds.getLeft()+worldBounds.getWidth()/3);
        regionButtonShoot.setBottom(worldBounds.getBottom());*/
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
/*        if (regionButtonLeft.isTouched(touch)) {
            pressedLeft = true;
            moveLeft();
        }
        if (regionButtonRight.isTouched(touch)) {
            pressedRight = true;
            moveRight();
        }
        if (regionButtonShoot.isTouched(touch)) {
            pressedShoot = true;
            frame = 1;
        }*/
        if (touch.x < worldBounds.pos.x) {
            if (leftPointer != INVALID_POINTER) return false;
            leftPointer = pointer;
            moveLeft();
        } else {
            if (rightPointer != INVALID_POINTER) return false;
            rightPointer = pointer;
            moveRight();
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
/*        if (pressedLeft) {
            pressedLeft = false;
            stop();
        }
        if (pressedRight) {
            pressedRight = false;
            stop();
        }
        if (pressedShoot) {
            pressedShoot = false;
            frame = 0;
            shoot();
        }*/
        if (pointer == leftPointer) {
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER) {
                moveRight();
            } else {
                stop();
            }
        } else if (pointer == rightPointer) {
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER) {
                moveLeft();
            } else {
                stop();
            }
        }
        return false;
    }

    public boolean keyDown(int keycode) {
        switch(keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
                break;
            case Input.Keys.UP:
                frame = 1;
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch(keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight) {
                    moveRight();
                } else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if (pressedLeft) {
                    moveLeft();
                } else {
                    stop();
                }
                break;
            case Input.Keys.UP:
                frame = 0;
                shoot();
                break;
        }
        return false;
    }

    private void moveRight() {
        v.set(v0);
    }

    private void moveLeft() {
        v.set(v0).rotate(180);
    }

    private void stop() {
        v.setZero();
    }


}
