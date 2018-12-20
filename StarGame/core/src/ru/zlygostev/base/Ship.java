package ru.zlygostev.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.zlygostev.base.Sprite;
import ru.zlygostev.math.Rect;
import ru.zlygostev.pool.BulletPool;
import ru.zlygostev.sprite.Bullet;

public class Ship extends Sprite {
    protected Vector2 v = new Vector2();
    protected Rect worldBounds;

    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletV = new Vector2();
    protected float bulletHeight;
    protected int bulletDamage;
    protected int shield;

    protected Sound soundShoot;

    protected float reloadInterval;
    protected float reloadTimer;

    public Ship() {

    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
    }

    public void shoot() {
        Bullet bullet = bulletPool.obtain();
        long id = soundShoot.play(0.4f);
        bullet.set(
                this,
                bulletRegion,
                pos,
                bulletV,
                bulletHeight,
                worldBounds,
                bulletDamage);
    }
}
