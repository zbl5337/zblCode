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
    private int x, y;

    private Dir dir = Dir.DOWN;

    private static final int SPEED = 1;

    private boolean moving = false;

    private TankFrame tf = null;

    private boolean living = true;

    private Random random = new Random();

    private Group group = Group.BAD;

    public static int WIDTH = ResourceManager.tankD.getWidth(), HEIGHT = ResourceManager.tankD.getHeight();

    public Tank(int x, int y, Dir dir, Group group, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tf = tf;
    }

    public Tank(int x, int y, Dir dir, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
    }

    public void paint(Graphics g) {
        if (!living)
             tf.enemy.remove(this);
        //Color rawColor = g.getColor();
        Image image = null;
        switch (dir) {
            case LEFT:
                image = ResourceManager.tankL;
                break;
            case RIGHT:
                image = ResourceManager.tankR;
                break;
            case UP:
                image = ResourceManager.tankU;
                break;
            case DOWN:
                image = ResourceManager.tankD;
                break;
        }
        g.drawImage(image, x, y, null);
        //填充矩形  从左上角开始 向右X 向下Y
//        g.setColor(Color.ORANGE);
//        g.fillRect(x, y, 50, 50);
//        g.setColor(rawColor);

        if (this.group == Group.BAD) moving = true;
        if (!moving) {
            return;
        }
        move();
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
        }


        if (group == Group.BAD && random.nextInt(10) > 5) {
            this.fire(this.group);
        }

    }

    public void fire(Group group) {
        //计算子弹xy位置
        int bx = this.x + Tank.WIDTH/2 - Bullet.WIDTH/2;
        int by = this.y + Tank.WIDTH/2;
        tf.bullets.add(new Bullet(bx, by, this.dir, group, this.tf));

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