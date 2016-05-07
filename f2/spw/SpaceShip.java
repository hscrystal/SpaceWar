package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class SpaceShip extends Sprite {

    private int step = 8;
    public static final int DELAY = 10;
    private boolean visible = true;
    private int timeOut = 0;
    private int nBullet = 1;
    
    public SpaceShip(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void draw(Graphics2D g) {
        if (visible) {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        } else {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
        }
        g.setColor(Color.GREEN);
        g.fillRect(x, y, width, height);
    }

    public void increaseBullet() {
        if(nBullet<3)
            nBullet++;
    }

    public void moveX(int direction) {
        x += step * direction;
        if (x < 0) {
            x = 0;
        } else if (x > 390 - width) {
            x = 390 - width;
        }
    }
    

    public void moveY(int direction) {
        y += step * direction;
        if (y < 0) {
            y = 0;
        } else if (y > 600 - height) {
            y = 600 - height;
        }
    }

    public void hide(int timeOut) {
        visible = false;
        this.timeOut = timeOut;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void show() {
        visible = true;
        timeOut = 0;
    }

    public boolean isVisible() {
        return visible;
    }
    
    public void resetBullet(){
    	nBullet = 1;
    }
    
    public ArrayList<Bullet> GenerateBullet() {
        ArrayList<Bullet> b = new ArrayList<Bullet>();
        int tmp = x + width / 2 - 2;
        tmp-=3*(nBullet-1);
        for (int i = 0; i < nBullet; i++) {
            b.add(new Bullet(tmp + i*7, y));
        }
        //System.out.println("Shoot!!");
        return b;
    }
    
    private static void wait(int milliseconds) {
        /* stop execution for milliseconds amount of time */
        try {
            Thread.sleep(milliseconds);
        } catch (Exception e) {
        }
    }  // end of wait()
}
