package ru.zlygostev.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.zlygostev.base.Ship;
import ru.zlygostev.math.Rect;
import ru.zlygostev.math.Rnd;
import ru.zlygostev.pool.BulletPool;

public class Enemy extends Ship {

    private MainShip mainShip;
    private Vector2 v0 = new Vector2();
    private boolean shootIsPossible = false;

    public Enemy(BulletPool bulletPool, MainShip mainShip, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.soundShoot = soundShoot;
        this.mainShip = mainShip;
        this.worldBounds = worldBounds;
        this.v.set(v0);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        if (getBottom() <= worldBounds.getBottom()) {
            this.setDestroyed(true);
        }

        if (shootIsPossible) {
            reloadTimer += delta;
            if (reloadTimer >= reloadInterval) {
                reloadTimer = 0f;
                shoot();
            }
        } else if (this.getTop() < worldBounds.getTop()) {
            shootIsPossible = true;
            reloadTimer = Rnd.nextFloat(0, reloadInterval);
        }
    }

    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVY,
            int bulletDamage,
            float reloadInterval,
            float height,
            int shield,
            Sound soundShoot
    ) {
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0f, bulletVY);
        this.bulletDamage = bulletDamage;
        this.reloadInterval = reloadInterval;
        this.shield = shield;
        setHeightProportion(height);
        reloadTimer = reloadInterval;
        v.set(v0);
        this.soundShoot = soundShoot;
    }
}
