package cat.flx.sprite;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

class Game {
    private Context context;

    private BitmapSet bitmapSet;
    private Scene scene;
    private Bonk bonk;
    private Audio audio;
    private List<Coin> coins;
    private List<Enemy> enemies;
    private int screenOffsetX, screenOffsetY;
    private Bomb b1;
    private Bomb b2;
    private Bomb b3;
    private Bomb b4;
    private Bomb b5;
    private Bomb b6;
    private Bomb b7;
    private Bomb b8;
    private List<Bomb> bombs;

    Game(Activity activity) {
        this.context = activity;
        bitmapSet = new BitmapSet(context.getResources());
        audio = new Audio(activity);
        scene = new Scene(this);
        bonk = new Bonk(this);
        coins = new ArrayList<>();
        enemies = new ArrayList<>();
        bombs = new ArrayList<>();
        b1 = new Bomb(this);
        b2 = new Bomb(this);
        b3 = new Bomb(this);
        b4 = new Bomb(this);
        b5 = new Bomb(this);
        b6 = new Bomb(this);
        b7 = new Bomb(this);
        b8 = new Bomb(this);
        scene.loadFromFile(R.raw.mini);
        bonk.x = 16 * 10;
        bonk.y = 0;
        b1.x = 10 * 10;
        b1.y = 0;
        bombs.add(b1);
        b2.x = 15 * 10;
        b2.y = -50;
        bombs.add(b2);
        b3.x = 20 * 10;
        b3.y = -100;
        bombs.add(b3);
        b4.x = 25 * 10;
        b4.y = -150;
        bombs.add(b4);
        b5.x = 30 * 10;
        b5.y = -200;
        bombs.add(b5);
        b6.x = 35 * 10;
        b6.y = -250;
        bombs.add(b6);
        b7.x = 40 * 10;
        b7.y = -300;
        bombs.add(b7);
        b8.x = 45 * 10;
        b8.y = -350;
        bombs.add(b8);
    }

    Context getContext() { return context; }
    Resources getResources() { return context.getResources(); }

    BitmapSet getBitmapSet() { return bitmapSet; }
    Scene getScene() { return scene; }
    Audio getAudio() { return audio; }
    Bonk getBonk() { return bonk; }

    void addCoin(Coin coin) {
        coins.add(coin);
    }
    List<Coin> getCoins() { return coins; }

    void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }
    List<Enemy> getEnemies() { return enemies; }

    void addBomb(Bomb bomb){
        bombs.add(bomb);
    }

    void physics() {
        bonk.physics();
        for(Coin coin : coins) {
            coin.physics();
            if (coin.getCollisionRect().intersect(bonk.getCollisionRect())) {
                audio.coin();
                coin.x = -1000;
                coin.y = -1000;
            }
        }
        for(Enemy enemy : enemies) {
            enemy.physics();
            if (enemy.getCollisionRect().intersect(bonk.getCollisionRect())) {
                audio.die();
                bonk.x = -1000;
                bonk.y = -1000;
                for (Bomb bomb : bombs){
                    bomb.stop();
                }
            }
        }
        for(Bomb bomb : bombs) {
            bomb.physics();
            if (bomb.getCollisionRect().intersect(bonk.getCollisionRect())) {
                audio.die();
                bonk.x = -1000;
                bonk.y = -1000;
            }
        }
    }

    private float sc;
    private int scX, scY;

    void draw(Canvas canvas) {
        if (canvas.getWidth() == 0) return;
        if (sc == 0) {
            scY = 16 * 16;
            sc = canvas.getHeight() / (float) scY;
            scX = (int) (canvas.getWidth() / sc);
        }
        screenOffsetX = Math.min(screenOffsetX, bonk.x - 100);
        screenOffsetX = Math.max(screenOffsetX, bonk.x - scX + 100);
        screenOffsetX = Math.max(screenOffsetX, 0);
        screenOffsetX = Math.min(screenOffsetX, scene.getWidth() - scX - 1);
        screenOffsetY = Math.min(screenOffsetY, bonk.y - 50);
        screenOffsetY = Math.max(screenOffsetY, bonk.y - scY + 75);
        screenOffsetY = Math.max(screenOffsetY, 0);
        screenOffsetY = Math.min(screenOffsetY, scene.getHeight() - scY);
        canvas.scale(sc, sc);
        canvas.translate(-screenOffsetX, -screenOffsetY);
        scene.draw(canvas);
        bonk.draw(canvas);
        for(Coin coin : coins) {
            coin.draw(canvas);
        }
        for(Enemy enemy : enemies) {
            enemy.draw(canvas);
        }
        for(Bomb bomb : bombs) {
            bomb.draw(canvas);
        }
//        if (Math.random() > 0.95f) audio.coin();
//        if (Math.random() > 0.95f) audio.die();
//        if (Math.random() > 0.95f) audio.pause();
    }

    private int keyCounter = 0;
    private boolean keyLeft, keyRight, keyJump;
    void keyLeft(boolean down) { keyCounter = 0; if (down) keyLeft = true; }
    void keyRight(boolean down) { keyCounter = 0; if (down) keyRight = true; }
    void keyJump(boolean down) { keyCounter = 0; if (down) keyJump = true; }

    private boolean left, right, jump;
    void left(boolean down) {
        if (left && !down) left = false;
        else if (!left && down) left = true;
    }
    void right(boolean down) {
        if (right && !down) right = false;
        else if (!right && down) right = true;
    }
    void jump(boolean down) {
        if (jump && !down) jump = false;
        else if (!jump && down) jump = true;
    }

    void events() {
        if (++keyCounter > 2) {
            keyCounter = 0;
            keyLeft = keyRight = keyJump = false;
        }
        if (keyLeft || left) { bonk.left(); }
        if (keyRight || right) { bonk.right(); }
        if (keyJump || jump) { bonk.jump(); }
    }
}
