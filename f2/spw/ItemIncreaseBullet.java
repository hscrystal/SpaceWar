package f2.spw;

import java.awt.Color;

/**
 *
 * @author Mildronize
 */
public class ItemIncreaseBullet extends Item {

    SpaceShip v;

    public ItemIncreaseBullet(int x, int y, SpaceShip v) {
        super(x, y, 15, 15, Color.CYAN);
        this.v = v;
    }
    @Override
    public void proceed(int timeOut) {
        v.increaseBullet();
    }
}
