package f2.spw;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
/**
 *
 * @author Mildronize
 */
public abstract class Item extends Sprite {

    private Color color;
    public static final int Y_TO_DIE = 600;
    public static final int step = 2;
    private boolean alive = true;

    public Item(int x, int y, int width, int height, Color color) {
        super(x, y, width, height);
        this.color = color;

    }

    @Override
    public void draw(Graphics2D g) {
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    public void move() {
        y += step;
        if (y > Y_TO_DIE) {
            die();
        }
    }
    
    public abstract void proceed(int timeOut);

    public void die() {
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }
}
