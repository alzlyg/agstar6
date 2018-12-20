package ru.zlygostev.utils;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.zlygostev.math.Rect;
import ru.zlygostev.math.Rnd;
import ru.zlygostev.pool.EnemyPool;
import ru.zlygostev.sprite.Enemy;

import java.util.Random;

import static ru.zlygostev.math.Rnd.nextInt;

public class EnemiesEmitter {
    private static final float ENEMY_SMALL_HEIGHT = 0.2f;
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.014f;
    private static final float ENEMY_SMALL_BULLET_VY = -0.5f;
    private static final int ENEMY_SMALL_BULLET_DAMAGE = 1;
    private static final float ENEMY_SMALL_RELOAD_INTERVAL = 2f;
    private static final int ENEMY_SMALL_SHIELD = 1;

    private static final float ENEMY_MIDDLE_HEIGHT = 0.25f;
    private static final float ENEMY_MIDDLE_BULLET_HEIGHT = 0.015f;
    private static final float ENEMY_MIDDLE_BULLET_VY = -0.6f;
    private static final int ENEMY_MIDDLE_BULLET_DAMAGE = 1;
    private static final float ENEMY_MIDDLE_RELOAD_INTERVAL = 1.8f;
    private static final int ENEMY_MIDDLE_SHIELD = 2;

    private static final float ENEMY_BIG_HEIGHT = 0.3f;
    private static final float ENEMY_BIG_BULLET_HEIGHT = 0.018f;
    private static final float ENEMY_BIG_BULLET_VY = -0.7f;
    private static final int ENEMY_BIG_BULLET_DAMAGE = 2;
    private static final float ENEMY_BIG_RELOAD_INTERVAL = 1.5f;
    private static final int ENEMY_BIG_SHIELD = 3;

    private Rect worldBounds;

    private float generateInterval = 4f;
    private float generateTimer;

    private TextureRegion[] enemySmallRegion;
    private TextureRegion[] enemyMiddleRegion;
    private TextureRegion[] enemyBigRegion;

    private final Vector2 enemySmallV = new Vector2(0f, -0.2f);
    private final Vector2 enemyMiddleV = new Vector2(0f, -0.22f);
    private final Vector2 enemyBigV = new Vector2(0f, -0.25f);

    private TextureRegion bulletRegion;
    protected Sound soundShoot;

    private EnemyPool enemyPool;

    public EnemiesEmitter(Rect worldBounds, EnemyPool enemyPool, TextureAtlas atlas, Sound soundShoot) {
        this.worldBounds = worldBounds;
        this.soundShoot = soundShoot;
        this.enemyPool = enemyPool;
        TextureRegion textureRegionSmall = atlas.findRegion("enemy0");
        this.enemySmallRegion = Regions.split(textureRegionSmall, 1, 2, 2);
        TextureRegion textureRegionMiddle = atlas.findRegion("enemy1");
        this.enemyMiddleRegion = Regions.split(textureRegionMiddle, 1, 2, 2);
        TextureRegion textureRegionBig = atlas.findRegion("enemy2");
        this.enemyBigRegion = Regions.split(textureRegionBig, 1, 2, 2);

        this.bulletRegion = atlas.findRegion("bulletEnemy");
    }

    public void generate(float delta) {
        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            Enemy enemy = enemyPool.obtain();
            int shipType = Rnd.nextInt(3);
            switch(shipType) {
                case 1:
                    enemy.set(
                            enemySmallRegion,
                            enemySmallV,
                            bulletRegion,
                            ENEMY_SMALL_BULLET_HEIGHT,
                            ENEMY_SMALL_BULLET_VY,
                            ENEMY_SMALL_BULLET_DAMAGE,
                            ENEMY_SMALL_RELOAD_INTERVAL,
                            ENEMY_SMALL_HEIGHT,
                            ENEMY_SMALL_SHIELD,
                            soundShoot
                    );
                    break;
                case 2:
                    enemy.set(
                            enemyMiddleRegion,
                            enemyMiddleV,
                            bulletRegion,
                            ENEMY_MIDDLE_BULLET_HEIGHT,
                            ENEMY_MIDDLE_BULLET_VY,
                            ENEMY_MIDDLE_BULLET_DAMAGE,
                            ENEMY_MIDDLE_RELOAD_INTERVAL,
                            ENEMY_MIDDLE_HEIGHT,
                            ENEMY_MIDDLE_SHIELD,
                            soundShoot
                    );
                    break;
                case 3:
                    enemy.set(
                            enemyBigRegion,
                            enemyBigV,
                            bulletRegion,
                            ENEMY_BIG_BULLET_HEIGHT,
                            ENEMY_BIG_BULLET_VY,
                            ENEMY_BIG_BULLET_DAMAGE,
                            ENEMY_BIG_RELOAD_INTERVAL,
                            ENEMY_BIG_HEIGHT,
                            ENEMY_BIG_SHIELD,
                            soundShoot
                    );
                    break;
            }


            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(), worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
        }
    }
}
