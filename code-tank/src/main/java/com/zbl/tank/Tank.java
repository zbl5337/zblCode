package com.zbl.tank;

import lombok.Data;

import java.awt.*;
import java.util.Random;

/**
 * @Author: zbl
 * @Date: Created in 18:16 2020/5/19
 * @Description:
 * @Version: $
 */
@Data
public class Tank {
    int x, y;

    Dir dir = Dir.DOWN;

    private static final int SPEED = 10;

    private boolean moving = false;

    TankFrame tf = null;

    private boolean living = true;

    private Random random = new Random();

    Group group = Group.BAD;

    public static int WIDTH = ResourceManager.tankD.getWidth(), HEIGHT = ResourceManager.tankD.getHeight();

    public Rectangle rect = new Rectangle();

    FireStrategy fs = new DefaultFireStrategy();

    public Tank(int x, int y, Dir dir, Group group, TankFrame tf) {
        setValue(x, y, dir, group, tf);
    }

    public Tank(int x, int y, Dir dir, TankFrame tf) {
      setValue(x, y, dir, null, tf);
    }

    private void setValue(int x, int y, Dir dir, Group group, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        if (group != null) {
            this.group = group;
        }
        this.tf = tf;
        this.rect.x = this.x;
        this.rect.y = this.y;
        this.rect.width = WIDTH;
        this.rect.height = HEIGHT;
        if (group == Group.GOOD) {
            try {
                fs = (FireStrategy) Class.forName((String) PropertyMgr.get("goodFs")).newInstance();
            } catch (Exception e) {
                fs = new DefaultFireStrategy();
            }
        }
    }

    public void paint(Graphics g) {
        if (!living) {
            tf.enemy.remove(this);
            if (group == Group.GOOD) {
                return;
            }
        }
        //Color rawColor = g.getColor();
        Image image = null;

        switch (dir) {
            case LEFT:
                image = this.group == Group.GOOD ? ResourceManager.tankL : ResourceManager.BadTankL;
                break;
            case RIGHT:
                image = this.group == Group.GOOD ? ResourceManager.tankR : ResourceManager.BadTankR;
                break;
            case UP:
                image = this.group == Group.GOOD ? ResourceManager.tankU : ResourceManager.BadTankU;
                break;
            case DOWN:
                image = this.group == Group.GOOD ? ResourceManager.tankD : ResourceManager.BadTankD;
                break;
        }

        g.drawImage(image, x, y, null);
        //填充矩形  从左上角开始 向右X 向下Y
//        g.setColor(Color.ORANGE);
//        g.fillRect(x, y, 50, 50);
//        g.setColor(rawColor);

        if (this.group == Group.BAD) {
            moving = true;
        }

        if (!moving) {
            return;
        }
        move();
    }

    int threshold = 7;

    int times = 0;

    private void randomDir() {
        randomDir(null);
    }

    private void randomDir(Dir d) {
        if (d != null) {
            overturn(d);
            return;
        }
        if (times < threshold) {
            times++;
            return;
        }
        dir = Dir.values()[random.nextInt(4)];
        times = 0;
    }

    private void overturn(Dir d) {
        switch (d) {
            case UP:
                dir = Dir.DOWN;
                break;
            case DOWN:
                dir = Dir.UP;
                break;
            case LEFT:
                dir = Dir.RIGHT;
                break;
            case RIGHT:
                dir = Dir.LEFT;
                break;
            default:
                break;
        }
    }

    private void move() {
        switch (dir) {
            case LEFT:
                x -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            case UP:
                y -= SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
            case LEFT_UP:
                x -= SPEED;
                y -= SPEED;
                break;
            case LEFT_DOWN:
                x -= SPEED;
                y += SPEED;
                break;
            case RIGHT_UP:
                x += SPEED;
                y -= SPEED;
                break;
            case RIGHT_DOWN:
                x += SPEED;
                y += SPEED;
                break;
        }

        //敌人子弹数量
        if (group == Group.BAD && random.nextInt(100) > 95) {
//            this.fire(this.group);
            this.fire();
        }

        if (group == Group.BAD && random.nextInt(100) > 70) {
            randomDir();
        }

        boundsCheck();

        //update rect
        rect.x = this.x;
        rect.y = this.y;

    }

    //碰撞检测 -2是为了边框
    private void boundsCheck() {
        boolean hook = false;
        if (this.x < 2) {
            x = 2;
            hook = true;
        }
        if (this.y < Tank.WIDTH / 2 - 2) {
            y = Tank.WIDTH / 2 - 2;
            hook = true;
        }
        if (this.x > TankFrame.GAME_WIDTH - Tank.WIDTH - 2) {
            x = TankFrame.GAME_WIDTH - Tank.WIDTH - 2;
            hook = true;
        }
        if (this.y > TankFrame.GAME_HEIGHT - Tank.HEIGHT - 2) {
            y = TankFrame.GAME_HEIGHT - Tank.HEIGHT - 2;
            hook = true;
        }
        //掉头
        if (this.group == Group.BAD && hook && random.nextInt(2) == 1) {
            randomDir(dir);
        }

    }

    public void fire() {
        //计算子弹xy位置
//        int bx = this.x + Tank.WIDTH/2 - Bullet.WIDTH/2;
//        int by = this.y + Tank.WIDTH/2;
//        tf.bullets.add(new Bullet(bx, by, this.dir, group, this.tf));
        fs.fire(this);
    }

    public void die() {
        living = false;
    }

//    public static void main(String[] args) {
//        Random random = new Random();
//        System.out.println(random.nextInt(4));
//        for (;;) {
//            if (random.nextInt(4) == 0) {
//                System.out.println(123);
//                return;
//            }
//        }
//    }
}
