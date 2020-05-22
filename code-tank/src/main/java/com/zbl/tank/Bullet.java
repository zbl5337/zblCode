package com.zbl.tank;

import java.awt.*;

/**
 * @Author: zbl
 * @Date: Created in 10:25 2020/5/20
 * @Description:
 * @Version: $
 */
public class Bullet {
    private static final int SPEED = 10;

    public static int WIDTH = ResourceManager.bulletD.getWidth(), HEIGHT = ResourceManager.bulletD.getHeight();

    private int x, y;

    private Dir dir;

    private boolean living = true;

    private TankFrame tf = null;

    private Group group = Group.BAD;

    public Bullet(int x, int y, Dir dir, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
    }

    public Bullet(int x, int y, Dir dir, Group group, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tf = tf;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void paint(Graphics g) {
        //不删除会有内存泄露问题
        if (!living) {
            tf.bullets.remove(this);
        }
//        Color c = g.getColor();
//        g.setColor(Color.RED);
//        g.fillOval(x, y, WIDTH, HEIGHT);
//        g.setColor(c);
        Image image = null;
        switch (dir) {
            case LEFT:
                image = ResourceManager.bulletL;
                break;
            case RIGHT:
                image = ResourceManager.bulletR;
                break;
            case UP:
                image = ResourceManager.bulletU;
                break;
            case DOWN:
                image = ResourceManager.bulletD;
                break;
        }
        g.drawImage(image, x, y, null);

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
        }

        if (x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) living = false;
    }

    /**
     *
     * @param tank
     */
    public void collideWith(Tank tank) {
        if (this.group == tank.getGroup())
            return;
        //TODO Rectangle太多了, 用一个rect来记录子弹的位置
        Rectangle rect1 = new Rectangle(this.x, this.y, WIDTH, HEIGHT);
        Rectangle rect2 = new Rectangle(tank.getX(), tank.getY(), tank.WIDTH, tank.HEIGHT);
        //判断是否相交
        if (rect1.intersects(rect2)) {
            tank.die();
            this.die();
        }

    }

    private void die() {
        living = false;
    }
}